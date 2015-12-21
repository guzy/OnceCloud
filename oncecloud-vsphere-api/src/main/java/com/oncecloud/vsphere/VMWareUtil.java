package com.oncecloud.vsphere;

import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Map.Entry;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.soap.SOAPFaultException;

import com.vmware.vim25.*;

public class VMWareUtil {

	private static ManagedObjectReference SVC_INST_REF = new ManagedObjectReference();

	private static ManagedObjectReference propCollectorRef;
	private static ManagedObjectReference hostProfileRef;
	private static ManagedObjectReference rootRef;

	private static VimService vimService;

	private static VimPortType vimPort;

	private static ServiceContent serviceContent;

	private static String SVC_INST_NAME = "ServiceInstance";

	private static boolean isConnected = false;

	private static String dataCenterName;

	private static String vmPathName;


	private static Map<?, ?> headers = new HashMap<Object, Object>();
	private static String cookieValue = "";

	// private static String datastoreName = "datastore1";

	private static void trustAllHttpsCertificates() throws Exception {
		// Create a trust manager that does not validate certificate chains:
		javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
		javax.net.ssl.TrustManager tm = new TrustAllTrustManager();
		trustAllCerts[0] = tm;
		javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext
				.getInstance("SSL");
		javax.net.ssl.SSLSessionContext sslsc = sc.getServerSessionContext();
		sslsc.setSessionTimeout(0);
		sc.init(null, trustAllCerts, null);
		javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc
				.getSocketFactory());
	}
	private static class TrustAllTrustManager implements
		javax.net.ssl.TrustManager, javax.net.ssl.X509TrustManager {
	
	public java.security.cert.X509Certificate[] getAcceptedIssuers() {
		return null;
	}
	
	public void checkServerTrusted(
			java.security.cert.X509Certificate[] certs, String authType)
			throws java.security.cert.CertificateException {
		return;
	}
	
	public void checkClientTrusted(
			java.security.cert.X509Certificate[] certs, String authType)
			throws java.security.cert.CertificateException {
		return;
	}
	}

	/**
	 *	与vcenter主机创建连接.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public static boolean connect(String url,String username,String pwd) throws Exception {
		if (!isConnected) {
			try {
				HostnameVerifier hv = new HostnameVerifier() {
					public boolean verify(String urlHostName, SSLSession session) {
						return true;
					}
				};
				trustAllHttpsCertificates();
				HttpsURLConnection.setDefaultHostnameVerifier(hv);

				SVC_INST_REF.setType(SVC_INST_NAME);
				SVC_INST_REF.setValue(SVC_INST_NAME);
				if(vimService==null){
					vimService = new VimService();
				}
				if(vimPort==null){
					vimPort = vimService.getVimPort();
				}
				Map<String, Object> ctxt = ((BindingProvider) vimPort)
						.getRequestContext();

				ctxt.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url);
				ctxt.put(BindingProvider.SESSION_MAINTAIN_PROPERTY, true);
				if(serviceContent==null){
					serviceContent = vimPort.retrieveServiceContent(SVC_INST_REF);
				}
				headers = (Map<?, ?>) ((BindingProvider) vimPort)
						.getResponseContext().get(
								MessageContext.HTTP_RESPONSE_HEADERS);
				System.out.println("username=="+username+"----pwd=="+pwd);
				vimPort.login(serviceContent.getSessionManager(), username,
						pwd, null);
				isConnected = true;
				if(propCollectorRef==null){
					propCollectorRef = serviceContent.getPropertyCollector();
				}
				
				if(hostProfileRef==null){
					hostProfileRef = serviceContent.getHostProfileManager();
				}
				if(rootRef==null){
					rootRef = serviceContent.getRootFolder();
				}
			} catch (Exception e) {
				e.printStackTrace();
				isConnected = false;
				System.out.println(e.getMessage());
			}
		}
		return isConnected;
//		System.out.println("isConnected : "+isConnected);
	}

	/**
	 * 关闭连接.
	 * 
	 * @throws Exception
	 */
	public static void disconnect() throws Exception {
		if (isConnected) {
			vimPort.logout(serviceContent.getSessionManager());
		}
		isConnected = false;
	}

	/**
	 * Retrieve contents for a single object based on the property collector
	 * registered with the service.
	 * 
	 * @param collector
	 *            Property collector registered with service
	 * @param mobjf
	 *            Managed Object Reference to get contents for
	 * @param properties
	 *            names of properties of object to retrieve
	 * 
	 * @return retrieved object contents
	 */
	private static ObjectContent[] getObjectProperties(
			ManagedObjectReference mobj, String[] properties) throws Exception {
		if (mobj == null) {
			return null;
		}

		PropertyFilterSpec spec = new PropertyFilterSpec();
		spec.getPropSet().add(new PropertySpec());
		if ((properties == null || properties.length == 0)) {
			spec.getPropSet().get(0).setAll(Boolean.TRUE);
		} else {
			spec.getPropSet().get(0).setAll(Boolean.FALSE);
		}
		spec.getPropSet().get(0).setType(mobj.getType());
		spec.getPropSet().get(0).getPathSet().addAll(Arrays.asList(properties));
		spec.getObjectSet().add(new ObjectSpec());
		spec.getObjectSet().get(0).setObj(mobj);
		spec.getObjectSet().get(0).setSkip(Boolean.FALSE);
		List<PropertyFilterSpec> listpfs = new ArrayList<PropertyFilterSpec>(1);
		listpfs.add(spec);
		List<ObjectContent> listobjcont = retrievePropertiesAllObjects(listpfs);
		return listobjcont.toArray(new ObjectContent[listobjcont.size()]);
	}

	/**
	 * Uses the new RetrievePropertiesEx method to emulate the now deprecated
	 * RetrieveProperties method
	 * 
	 * @param listpfs
	 * @return list of object content
	 * @throws Exception
	 */
	private static List<ObjectContent> retrievePropertiesAllObjects(
			List<PropertyFilterSpec> listpfs) {

		RetrieveOptions propObjectRetrieveOpts = new RetrieveOptions();

		List<ObjectContent> listobjcontent = new ArrayList<ObjectContent>();

		try {
			RetrieveResult rslts = vimPort.retrievePropertiesEx(
					propCollectorRef, listpfs, propObjectRetrieveOpts);
			if (rslts != null && rslts.getObjects() != null
					&& !rslts.getObjects().isEmpty()) {
				listobjcontent.addAll(rslts.getObjects());
			}
			String token = null;
			if (rslts != null && rslts.getToken() != null) {
				token = rslts.getToken();
			}
			while (token != null && !token.isEmpty()) {
				rslts = vimPort.continueRetrievePropertiesEx(propCollectorRef,
						token);
				token = null;
				if (rslts != null) {
					token = rslts.getToken();
					if (rslts.getObjects() != null
							&& !rslts.getObjects().isEmpty()) {
						listobjcontent.addAll(rslts.getObjects());
					}
				}
			}
		} catch (SOAPFaultException sfe) {
			printSoapFaultException(sfe);
		} catch (Exception e) {
			System.out.println(" : Failed Getting Contents");
			e.printStackTrace();
		}

		return listobjcontent;
	}

	private static void printSoapFaultException(SOAPFaultException sfe) {
		System.out.println("SOAP Fault -");
		if (sfe.getFault().hasDetail()) {
			System.out.println(sfe.getFault().getDetail().getFirstChild()
					.getLocalName());
		}
		if (sfe.getFault().getFaultString() != null) {
			System.out
					.println("\n Message: " + sfe.getFault().getFaultString());
		}
	}

	/**
	 * Determines of a method 'methodName' exists for the Object 'obj'.
	 * 
	 * @param obj
	 *            The Object to check
	 * @param methodName
	 *            The method name
	 * @param parameterTypes
	 *            Array of Class objects for the parameter types
	 * @return true if the method exists, false otherwise
	 */
	@SuppressWarnings("rawtypes")
	private static boolean methodExists(Object obj, String methodName,
			Class[] parameterTypes) {
		boolean exists = false;
		try {
			Method method = obj.getClass()
					.getMethod(methodName, parameterTypes);
			if (method != null) {
				exists = true;
			}
		} catch (SOAPFaultException sfe) {
			printSoapFaultException(sfe);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return exists;
	}

	public static Object getDynamicProperty(ManagedObjectReference mor,
			String propertyName) throws Exception {
		ObjectContent[] objContent = getObjectProperties(mor,
				new String[] { propertyName });

		Object propertyValue = null;
		if (objContent != null) {
			List<DynamicProperty> listdp = objContent[0].getPropSet();
			if (listdp != null) {
				/*
				 * Check the dynamic property for ArrayOfXXX object
				 */
				Object dynamicPropertyVal = listdp.get(0).getVal();
				String dynamicPropertyName = dynamicPropertyVal.getClass()
						.getName();
				if (dynamicPropertyName.indexOf("ArrayOf") != -1) {
					String methodName = dynamicPropertyName.substring(
							dynamicPropertyName.indexOf("ArrayOf")
									+ "ArrayOf".length(), dynamicPropertyName
									.length());
					/*
					 * If object is ArrayOfXXX object, then get the XXX[] by
					 * invoking getXXX() on the object. For Ex:
					 * ArrayOfManagedObjectReference.getManagedObjectReference()
					 * returns ManagedObjectReference[] array.
					 */
					if (methodExists(dynamicPropertyVal, "get" + methodName,
							null)) {
						methodName = "get" + methodName;
					} else {
						/*
						 * Construct methodName for ArrayOf primitive types Ex:
						 * For ArrayOfInt, methodName is get_int
						 */
						methodName = "get_" + methodName.toLowerCase();
					}
					Method getMorMethod = dynamicPropertyVal.getClass()
							.getDeclaredMethod(methodName, (Class[]) null);
					propertyValue = getMorMethod.invoke(dynamicPropertyVal,
							(Object[]) null);
				} else if (dynamicPropertyVal.getClass().isArray()) {
					/*
					 * Handle the case of an unwrapped array being deserialized.
					 */
					propertyValue = dynamicPropertyVal;
				} else {
					propertyValue = dynamicPropertyVal;
				}
			}
		}
		return propertyValue;
	}

	private static void updateValues(String[] props, Object[] vals,
			PropertyChange propchg) {
		for (int findi = 0; findi < props.length; findi++) {
			if (propchg.getName().lastIndexOf(props[findi]) >= 0) {
				if (propchg.getOp() == PropertyChangeOp.REMOVE) {
					vals[findi] = "";
				} else {
					vals[findi] = propchg.getVal();
				}
			}
		}
	}

	/**
	 * Handle Updates for a single object. waits till expected values of
	 * properties to check are reached Destroys the ObjectFilter when done.
	 * 
	 * @param objmor
	 *            MOR of the Object to wait for</param>
	 * @param filterProps
	 *            Properties list to filter
	 * @param endWaitProps
	 *            Properties list to check for expected values these be
	 *            properties of a property in the filter properties list
	 * @param expectedVals
	 *            values for properties to end the wait
	 * @return true indicating expected values were met, and false otherwise
	 * @throws RuntimeFaultFaultMsg
	 * @throws InvalidPropertyFaultMsg
	 * @throws InvalidCollectorVersionFaultMsg
	 */
	private static Object[] waitForValues(ManagedObjectReference objmor,
			String[] filterProps, String[] endWaitProps, Object[][] expectedVals)
			throws InvalidPropertyFaultMsg, RuntimeFaultFaultMsg,
			InvalidCollectorVersionFaultMsg {
		// version string is initially null
		String version = "";
		Object[] endVals = new Object[endWaitProps.length];
		Object[] filterVals = new Object[filterProps.length];
		PropertyFilterSpec spec = propertyFilterSpec(objmor, filterProps);

		ManagedObjectReference filterSpecRef = vimPort.createFilter(
				serviceContent.getPropertyCollector(), spec, true);

		boolean reached = false;

        UpdateSet updateset = null;
        List<PropertyFilterUpdate> filtupary = null;
        List<ObjectUpdate> objupary = null;
        List<PropertyChange> propchgary = null;
        while (!reached) {
            updateset =
                    vimPort.waitForUpdatesEx(serviceContent.getPropertyCollector(),
                            version, new WaitOptions());
            if (updateset == null || updateset.getFilterSet() == null) {
                continue;
            }
            version = updateset.getVersion();

            // Make this code more general purpose when PropCol changes later.
            filtupary = updateset.getFilterSet();

            for (PropertyFilterUpdate filtup : filtupary) {
                objupary = filtup.getObjectSet();
                for (ObjectUpdate objup : objupary) {
                    // TODO: Handle all "kind"s of updates.
                    if (objup.getKind() == ObjectUpdateKind.MODIFY
                            || objup.getKind() == ObjectUpdateKind.ENTER
                            || objup.getKind() == ObjectUpdateKind.LEAVE) {
                        propchgary = objup.getChangeSet();
                        for (PropertyChange propchg : propchgary) {
                            updateValues(endWaitProps, endVals, propchg);
                            updateValues(filterProps, filterVals, propchg);
                        }
                    }
                }
            }

            Object expctdval = null;
            // Check if the expected values have been reached and exit the loop
            // if done.
            // Also exit the WaitForUpdates loop if this is the case.
            for (int chgi = 0; chgi < endVals.length && !reached; chgi++) {
                for (int vali = 0; vali < expectedVals[chgi].length && !reached; vali++) {
                    expctdval = expectedVals[chgi][vali];

                    reached = expctdval.equals(endVals[chgi]) || reached;
                }
            }
        }

        // Destroy the filter when we are done.
        vimPort.destroyPropertyFilter(filterSpecRef);
		return filterVals;
	}
	public static PropertyFilterSpec propertyFilterSpec(ManagedObjectReference objmor, String[] filterProps) {
        PropertyFilterSpec spec = new PropertyFilterSpec();
        ObjectSpec oSpec = new ObjectSpec();
        oSpec.setObj(objmor);
        oSpec.setSkip(Boolean.FALSE);
        spec.getObjectSet().add(oSpec);

        PropertySpec pSpec = new PropertySpec();
        pSpec.getPathSet().addAll(Arrays.asList(filterProps));
        pSpec.setType(objmor.getType());
        spec.getPropSet().add(pSpec);
        return spec;
    }
	/**
	 * This method returns a boolean value specifying whether the Task is
	 * succeeded or failed.
	 * 
	 * @param task
	 *            ManagedObjectReference representing the Task.
	 * 
	 * @return boolean value representing the Task result.
	 * @throws InvalidCollectorVersionFaultMsg
	 * @throws RuntimeFaultFaultMsg
	 * @throws InvalidPropertyFaultMsg
	 */
	private static boolean getTaskResultAfterDone(ManagedObjectReference task)
			throws InvalidPropertyFaultMsg, RuntimeFaultFaultMsg,
			InvalidCollectorVersionFaultMsg {

		boolean retVal = false;

		// info has a property - state for state of the task
		Object[] result = waitForValues(task, new String[] { "info.state",
				"info.error" }, new String[] { "state" },
				new Object[][] { new Object[] { TaskInfoState.SUCCESS,
						TaskInfoState.ERROR } });

		if (result[0].equals(TaskInfoState.SUCCESS)) {
			retVal = true;
		}
		if (result[1] instanceof LocalizedMethodFault) {
			throw new RuntimeException(((LocalizedMethodFault) result[1])
					.getLocalizedMessage());
		}
		return retVal;
	}

	private static Map<String, ManagedObjectReference> getMOREFsInContainerByType(
			ManagedObjectReference folder, String morefType)
			throws InvalidPropertyFaultMsg, RuntimeFaultFaultMsg {
		String PROP_ME_NAME = "name";
		ManagedObjectReference viewManager = serviceContent.getViewManager();
		ManagedObjectReference containerView = vimPort.createContainerView(
				viewManager, folder, Arrays.asList(morefType), true);

		Map<String, ManagedObjectReference> tgtMoref = new HashMap<String, ManagedObjectReference>();

		// Create Property Spec
		PropertySpec propertySpec = new PropertySpec();
		propertySpec.setAll(Boolean.FALSE);
		propertySpec.setType(morefType);
		propertySpec.getPathSet().add(PROP_ME_NAME);

		TraversalSpec ts = new TraversalSpec();
		ts.setName("view");
		ts.setPath("view");
		ts.setSkip(false);
		ts.setType("ContainerView");

		// Now create Object Spec
		ObjectSpec objectSpec = new ObjectSpec();
		objectSpec.setObj(containerView);
		objectSpec.setSkip(Boolean.TRUE);
		objectSpec.getSelectSet().add(ts);

		// Create PropertyFilterSpec using the PropertySpec and ObjectPec
		// created above.
		PropertyFilterSpec propertyFilterSpec = new PropertyFilterSpec();
		propertyFilterSpec.getPropSet().add(propertySpec);
		propertyFilterSpec.getObjectSet().add(objectSpec);

		List<PropertyFilterSpec> propertyFilterSpecs = new ArrayList<PropertyFilterSpec>();
		propertyFilterSpecs.add(propertyFilterSpec);

		RetrieveResult rslts = vimPort.retrievePropertiesEx(serviceContent
				.getPropertyCollector(), propertyFilterSpecs,
				new RetrieveOptions());
		List<ObjectContent> listobjcontent = new ArrayList<ObjectContent>();
		if (rslts != null && rslts.getObjects() != null
				&& !rslts.getObjects().isEmpty()) {
			listobjcontent.addAll(rslts.getObjects());
		}
		String token = null;
		if (rslts != null && rslts.getToken() != null) {
			token = rslts.getToken();
		}
		while (token != null && !token.isEmpty()) {
			rslts = vimPort.continueRetrievePropertiesEx(serviceContent
					.getPropertyCollector(), token);
			token = null;
			if (rslts != null) {
				token = rslts.getToken();
				if (rslts.getObjects() != null && !rslts.getObjects().isEmpty()) {
					listobjcontent.addAll(rslts.getObjects());
				}
			}
		}
		for (ObjectContent oc : listobjcontent) {
			ManagedObjectReference mr = oc.getObj();
			String entityNm = null;
			List<DynamicProperty> dps = oc.getPropSet();
			if (dps != null) {
				for (DynamicProperty dp : dps) {
					entityNm = (String) dp.getVal();
				}
			}
			tgtMoref.put(entityNm, mr);
		}
		return tgtMoref;
	}
	public static Map<String, Object> getEntityProps(
			ManagedObjectReference entityMor, String[] props)
			throws InvalidPropertyFaultMsg, RuntimeFaultFaultMsg {

		HashMap<String, Object> retVal = new HashMap<String, Object>();

		// Create Property Spec
		PropertySpec propertySpec = new PropertySpec();
		propertySpec.setAll(Boolean.FALSE);
		propertySpec.setType(entityMor.getType());
		propertySpec.getPathSet().addAll(Arrays.asList(props));

		// Now create Object Spec
		ObjectSpec objectSpec = new ObjectSpec();
		objectSpec.setObj(entityMor);

		// Create PropertyFilterSpec using the PropertySpec and ObjectPec
		// created above.
		PropertyFilterSpec propertyFilterSpec = new PropertyFilterSpec();
		propertyFilterSpec.getPropSet().add(propertySpec);
		propertyFilterSpec.getObjectSet().add(objectSpec);

		List<PropertyFilterSpec> propertyFilterSpecs = new ArrayList<PropertyFilterSpec>();
		propertyFilterSpecs.add(propertyFilterSpec);

		RetrieveResult rslts = vimPort.retrievePropertiesEx(serviceContent
				.getPropertyCollector(), propertyFilterSpecs,
				new RetrieveOptions());
		List<ObjectContent> listobjcontent = new ArrayList<ObjectContent>();
		if (rslts != null && rslts.getObjects() != null
				&& !rslts.getObjects().isEmpty()) {
			listobjcontent.addAll(rslts.getObjects());
		}
		String token = null;
		if (rslts != null && rslts.getToken() != null) {
			token = rslts.getToken();
		}
		while (token != null && !token.isEmpty()) {
			rslts = vimPort.continueRetrievePropertiesEx(serviceContent
					.getPropertyCollector(), token);
			token = null;
			if (rslts != null) {
				token = rslts.getToken();
				if (rslts.getObjects() != null && !rslts.getObjects().isEmpty()) {
					listobjcontent.addAll(rslts.getObjects());
				}
			}
		}
		for (ObjectContent oc : listobjcontent) {
			List<DynamicProperty> dps = oc.getPropSet();
			if (dps != null) {
				for (DynamicProperty dp : dps) {
					retVal.put(dp.getName(), dp.getVal());
				}
			}
		}
		return retVal;
	}

	private static List<Integer> getControllerKey(ManagedObjectReference vmMor)
			throws InvalidPropertyFaultMsg, RuntimeFaultFaultMsg {
		List<Integer> retVal = new ArrayList<Integer>();

		List<VirtualDevice> listvd = ((ArrayOfVirtualDevice) getEntityProps(
				vmMor, new String[] { "config.hardware.device" }).get(
				"config.hardware.device")).getVirtualDevice();

		Map<Integer, VirtualDevice> deviceMap = new HashMap<Integer, VirtualDevice>();
		for (VirtualDevice virtualDevice : listvd) {
			deviceMap.put(virtualDevice.getKey(), virtualDevice);
		}
		boolean found = false;
		for (VirtualDevice virtualDevice : listvd) {
			if (virtualDevice instanceof VirtualSCSIController) {
				VirtualSCSIController vscsic = (VirtualSCSIController) virtualDevice;
				int[] slots = new int[16];
				slots[7] = 1;
				List<Integer> devicelist = vscsic.getDevice();
				for (Integer deviceKey : devicelist) {
					if (deviceMap.get(deviceKey).getUnitNumber() != null) {
						slots[deviceMap.get(deviceKey).getUnitNumber()] = 1;
					}
				}
				for (int i = 0; i < slots.length; i++) {
					if (slots[i] != 1) {
						retVal.add(vscsic.getKey());
						retVal.add(i);
						found = true;
						break;
					}
				}
				if (found) {
					break;
				}
			}
		}

		if (!found) {
			throw new RuntimeException(
					"The SCSI controller on the vm has maxed out its "
							+ "capacity. Please add an additional SCSI controller");
		}
		return retVal;
	}

	private static String getDatastoreNameWithFreeSpace(int minFreeSpace,
			ManagedObjectReference virtualMachine)
			throws InvalidPropertyFaultMsg, RuntimeFaultFaultMsg {
		String dsName = null;
		List<ManagedObjectReference> datastores = ((ArrayOfManagedObjectReference) getEntityProps(
				virtualMachine, new String[] { "datastore" }).get("datastore"))
				.getManagedObjectReference();
		for (ManagedObjectReference datastore : datastores) {
			DatastoreSummary ds = (DatastoreSummary) getEntityProps(datastore,
					new String[] { "summary" }).get("summary");
			if (ds.getFreeSpace() > minFreeSpace) {
				dsName = ds.getName();
				break;
			}
		}
		return dsName;
	}

	private static VirtualDeviceConfigSpec getDiskDeviceConfigSpec(
			ManagedObjectReference virtualMachine, String operation,
			String disksize, String vmName, String value, String diskmode)
			throws Exception {
		String ops = operation;
		VirtualDeviceConfigSpec diskSpec = new VirtualDeviceConfigSpec();

		if (ops.equalsIgnoreCase("Add")) {
			VirtualDisk disk = new VirtualDisk();
			VirtualDiskFlatVer2BackingInfo diskfileBacking = new VirtualDiskFlatVer2BackingInfo();
			String dsName = getDatastoreNameWithFreeSpace(Integer
					.parseInt(disksize), virtualMachine);

			int ckey = 0;
			int unitNumber = 0;
			List<Integer> getControllerKeyReturnArr = getControllerKey(virtualMachine);
			if (!getControllerKeyReturnArr.isEmpty()) {
				ckey = getControllerKeyReturnArr.get(0);
				unitNumber = getControllerKeyReturnArr.get(1);
			}
			String fileName = "[" + dsName + "] " + vmName + "/" + value
					+ ".vmdk";
			diskfileBacking.setFileName(fileName);
			diskfileBacking.setDiskMode(diskmode);

			disk.setControllerKey(ckey);
			disk.setUnitNumber(unitNumber);
			disk.setBacking(diskfileBacking);
			int size = 1024 * 1024 * (Integer.parseInt(disksize));
			disk.setCapacityInKB(size);
			disk.setKey(-1);
			diskSpec.setOperation(VirtualDeviceConfigSpecOperation.ADD);
			diskSpec
					.setFileOperation(VirtualDeviceConfigSpecFileOperation.CREATE);
			diskSpec.setDevice(disk);
		} else if (ops.equalsIgnoreCase("Remove")) {
			VirtualDisk disk = null;
			List<VirtualDevice> deviceList = ((ArrayOfVirtualDevice) getEntityProps(
					virtualMachine, new String[] { "config.hardware.device" })
					.get("config.hardware.device")).getVirtualDevice();
			for (VirtualDevice device : deviceList) {
				if (device instanceof VirtualDisk) {
					if (value.equalsIgnoreCase(device.getDeviceInfo()
							.getLabel())) {
						disk = (VirtualDisk) device;
						break;
					}
				}
			}
			if (disk != null) {
				diskSpec.setOperation(VirtualDeviceConfigSpecOperation.REMOVE);
				diskSpec
						.setFileOperation(VirtualDeviceConfigSpecFileOperation.DESTROY);
				diskSpec.setDevice(disk);
			} else {
				System.out.println("No device found " + value);
				return null;
			}
		} else if (ops.equalsIgnoreCase("Edit")) {
			VirtualDisk disk = null;
			List<VirtualDevice> deviceList = ((ArrayOfVirtualDevice) getEntityProps(
					virtualMachine, new String[] { "config.hardware.device" })
					.get("config.hardware.device")).getVirtualDevice();
			for (VirtualDevice device : deviceList) {
				if (device instanceof VirtualDisk) {
					if (value.equalsIgnoreCase(device.getDeviceInfo()
							.getLabel())) {
						disk = (VirtualDisk) device;
						break;
					}
				}
			}
			if (disk != null) {
				int size = 1024 * 1024 * (Integer.parseInt(disksize));
				disk.setCapacityInKB(size);
				diskSpec.setOperation(VirtualDeviceConfigSpecOperation.EDIT);
				diskSpec.setDevice(disk);
			} else {
				System.out.println("No device found " + value);
				return null;
			}
		}
		return diskSpec;
	}
	

	public static GuestInfo getGuestInfoByVName(String vname) {
		try {
			PropertySpec propertySpec = new PropertySpec();
			propertySpec.setAll(Boolean.FALSE);
			propertySpec.getPathSet().add("name");
			propertySpec.getPathSet().add("guest");
			propertySpec.setType("VirtualMachine");

			ObjectSpec objectSpec = new ObjectSpec();
			objectSpec.setObj(serviceContent.getRootFolder());
			objectSpec.setSkip(Boolean.TRUE);
			objectSpec.getSelectSet().add(getVMTraversalSpec());

			PropertyFilterSpec propertyFilterSpec = new PropertyFilterSpec();
			propertyFilterSpec.getPropSet().add(propertySpec);
			propertyFilterSpec.getObjectSet().add(objectSpec);
			List<PropertyFilterSpec> l = new ArrayList<PropertyFilterSpec>();
			l.add(propertyFilterSpec);

			List<ObjectContent> oCont = vimPort.retrieveProperties(
					serviceContent.getPropertyCollector(), l);
			HashMap<String, GuestInfo> giMap = new HashMap<String, GuestInfo>();
			GuestInfo retVal = null;

			if (oCont != null) {
				for (ObjectContent oc : oCont) {
					List<DynamicProperty> dps = oc.getPropSet();
					if (dps != null) {
						String name = null;
						GuestInfo gi = null;
						for (DynamicProperty dp : dps) {
							if (dp.getName().equalsIgnoreCase("name")) {
								name = (String) dp.getVal();
							} else if (dp.getName().equalsIgnoreCase("guest")) {
								gi = (GuestInfo) dp.getVal();
							}
						}
						if ((name != null) && (gi != null)) {
							giMap.put(name, gi);
						}
					}
				}

				retVal = giMap.get(vname);
			}

			return retVal;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	

	/*
	 * @return An array of SelectionSpec covering all the entities that provide
	 * performance statistics. The entities that provide performance statistics
	 * are VM, Host, Resource pool, Cluster Compute Resource and Datastore.
	 */
	public static List<SelectionSpec> buildFullTraversal() {
		// Terminal traversal specs
		// RP -> VM
		TraversalSpec rpToVm = new TraversalSpec();
		rpToVm.setName("rpToVm");
		rpToVm.setType("ResourcePool");
		rpToVm.setPath("vm");
		rpToVm.setSkip(Boolean.FALSE);

		// vApp -> VM
		TraversalSpec vAppToVM = new TraversalSpec();
		vAppToVM.setName("vAppToVM");
		vAppToVM.setType("VirtualApp");
		vAppToVM.setPath("vm");

		// HostSystem -> VM
		TraversalSpec hToVm = new TraversalSpec();
		hToVm.setType("HostSystem");
		hToVm.setPath("vm");
		hToVm.setName("HToVm");
		hToVm.setSkip(Boolean.FALSE);

		// DC -> DS
		TraversalSpec dcToDs = new TraversalSpec();
		dcToDs.setType("Datacenter");
		dcToDs.setPath("datastore");
		dcToDs.setName("dcToDs");
		dcToDs.setSkip(Boolean.FALSE);

		// For RP -> RP recursion
		SelectionSpec rpToRpSpec = new SelectionSpec();
		rpToRpSpec.setName("rpToRp");

		// Recurse through all ResourcePools
		TraversalSpec rpToRp = new TraversalSpec();
		rpToRp.setType("ResourcePool");
		rpToRp.setPath("resourcePool");
		rpToRp.setSkip(Boolean.FALSE);
		rpToRp.setName("rpToRp");
		List<SelectionSpec> sspecs = new ArrayList<SelectionSpec>();
		sspecs.add(rpToRpSpec);
		rpToRp.getSelectSet().addAll(sspecs);

		TraversalSpec crToRp = new TraversalSpec();
		crToRp.setType("ComputeResource");
		crToRp.setPath("resourcePool");
		crToRp.setSkip(Boolean.FALSE);
		crToRp.setName("crToRp");
		List<SelectionSpec> sspecarrayrptorprtptovm = new ArrayList<SelectionSpec>();
		sspecarrayrptorprtptovm.add(rpToRp);
		crToRp.getSelectSet().addAll(sspecarrayrptorprtptovm);

		TraversalSpec crToH = new TraversalSpec();
		crToH.setSkip(Boolean.FALSE);
		crToH.setType("ComputeResource");
		crToH.setPath("host");
		crToH.setName("crToH");
		crToH.getSelectSet().add(hToVm);

		// For Folder -> Folder recursion
		SelectionSpec sspecvfolders = new SelectionSpec();
		sspecvfolders.setName("VisitFolders");

		TraversalSpec dcToHf = new TraversalSpec();
		dcToHf.setSkip(Boolean.FALSE);
		dcToHf.setType("Datacenter");
		dcToHf.setPath("hostFolder");
		dcToHf.setName("dcToHf");
		dcToHf.getSelectSet().add(sspecvfolders);

		TraversalSpec vAppToRp = new TraversalSpec();
		vAppToRp.setName("vAppToRp");
		vAppToRp.setType("VirtualApp");
		vAppToRp.setPath("resourcePool");
		List<SelectionSpec> vAppToVMSS = new ArrayList<SelectionSpec>();
		vAppToVMSS.add(rpToRpSpec);
		vAppToRp.getSelectSet().addAll(vAppToVMSS);

		TraversalSpec dcToVmf = new TraversalSpec();
		dcToVmf.setType("Datacenter");
		dcToVmf.setSkip(Boolean.FALSE);
		dcToVmf.setPath("vmFolder");
		dcToVmf.setName("dcToVmf");
		dcToVmf.getSelectSet().add(sspecvfolders);

		TraversalSpec visitFolders = new TraversalSpec();
		visitFolders.setType("Folder");
		visitFolders.setPath("childEntity");
		visitFolders.setSkip(Boolean.FALSE);
		visitFolders.setName("VisitFolders");
		List<SelectionSpec> sspecarrvf = new ArrayList<SelectionSpec>();
		sspecarrvf.add(crToRp);
		sspecarrvf.add(crToH);
		sspecarrvf.add(dcToVmf);
		sspecarrvf.add(dcToHf);
		sspecarrvf.add(vAppToRp);
		sspecarrvf.add(vAppToVM);
		sspecarrvf.add(dcToDs);
		sspecarrvf.add(rpToVm);
		sspecarrvf.add(sspecvfolders);
		visitFolders.getSelectSet().addAll(sspecarrvf);

		List<SelectionSpec> resultspec = new ArrayList<SelectionSpec>();
		resultspec.add(visitFolders);

		return resultspec;
	}

	@SuppressWarnings({ "unchecked"})
	public static Map<String, Object> printInventory() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// PropertySpec propertySpec = new PropertySpec();
			// propertySpec.setAll(Boolean.FALSE);
			// // VERIFY
			// propertySpec.setType("ManagedEntity");
			// propertySpec.getPathSet().add("name");
			PropertySpec propertySpec2 = new PropertySpec();
			propertySpec2.setAll(Boolean.FALSE);
			propertySpec2.setType("VirtualMachine");
			propertySpec2.getPathSet().add("name");
			propertySpec2.getPathSet().add("runtime");
			// Now create Object Spec
			ObjectSpec objectSpec = new ObjectSpec();
			objectSpec.setObj(rootRef);
			objectSpec.setSkip(Boolean.FALSE);
			objectSpec.getSelectSet().addAll(buildFullTraversal());
			// Create PropertyFilterSpec using the PropertySpec and ObjectPec
			// created above.
			PropertyFilterSpec propertyFilterSpec = new PropertyFilterSpec();
			propertyFilterSpec.getPropSet().add(propertySpec2);
			// propertyFilterSpec.getPropSet().add(propertySpec);
			propertyFilterSpec.getObjectSet().add(objectSpec);
			List<PropertyFilterSpec> listfps = new ArrayList<PropertyFilterSpec>(
					1);
			listfps.add(propertyFilterSpec);

			List<ObjectContent> listobcont = retrievePropertiesAllObjects(listfps);

			ObjectContent oc = null;
			DynamicProperty pc = null;
			if (listobcont != null) {
				new ArrayList<Map<String, Object>>();
				for (int oci = 0; oci < listobcont.size(); oci++) {
					Map<String, Object> pcmap = new HashMap<String, Object>();
					oc = listobcont.get(oci);
					oc.getObj();
					List<DynamicProperty> listgps = oc.getPropSet();
					// System.out.println("Object Type : " + mor.getType());
					// System.out.println("Reference Value : " +
					// mor.getValue());
					if (listgps != null) {
						for (int pci = 0; pci < listgps.size(); pci++) {
							pc = listgps.get(pci);
							if (pc != null) {
								// System.out.println("   Property Name : " +
								// pc.getName());
								if (!pc.getVal().getClass().isArray()) {
									// System.out.println("   Property Value : "
									// + pc.getVal());
									pcmap.put(pc.getName(), pc.getVal());
									if (pc.getName()
											.equalsIgnoreCase("runtime")) {
										pc
												.getVal();
										// System.out
										// .println(wmri
										// .getMaxMemoryUsage() != null ? wmri
										// .getMaxMemoryUsage() / 1024
										// : 0);
										pcmap.put(pc.getName(), pc.getVal());
									}
								} else {
									List<Object> ipcary = (List<Object>) pc
											.getVal();
									System.out.println("Val : " + pc.getVal());
									for (int ii = 0; ii < ipcary.size(); ii++) {
										Object oval = ipcary.get(ii);
										if (oval.getClass().getName().indexOf(
												"ManagedObjectReference") >= 0) {
										} else {
											// System.out
											// .println("Inner Property Value : "
											// + oval);
										}
									}
								}
							}
						}
					}
					map.put(pcmap.get("name").toString(), pcmap.get("runtime"));
				}
			}
			// System.out.println("Done Printing Inventory");
		} catch (SOAPFaultException sfe) {
			printSoapFaultException(sfe);
		} catch (Exception e) {
			// System.out.println(" : Failed Getting Contents");
			e.printStackTrace();
		}
		return map;
	}

	public static String callWaitForUpdatesEx(String maxWaitSeconds,
			String maxOjectUpdates, String VMName) throws Exception {
		String resultStr = "";
		ManagedObjectReference vmMor = getVmByName(VMName);

		String[][] typeInfo = { new String[] { "VirtualMachine", "name",
				"summary.quickStats", "runtime" } };

		List<PropertySpec> pSpecs = buildPropertySpecArray(typeInfo);
		List<ObjectSpec> oSpecs = new ArrayList<ObjectSpec>();
		boolean oneOnly = vmMor != null;
		ObjectSpec oSpec = new ObjectSpec();
		oSpec.setObj(oneOnly ? vmMor : rootRef);
		oSpec.setSkip(new Boolean(!oneOnly));
		if (!oneOnly) {
			oSpec.getSelectSet().addAll(buildFullTraversal());
		}
		oSpecs.add(oSpec);
		PropertyFilterSpec pSpec = new PropertyFilterSpec();

		pSpec.getPropSet().addAll(pSpecs);
		pSpec.getObjectSet().addAll(oSpecs);
		pSpec.setReportMissingObjectsInResults(false);
		ManagedObjectReference propColl = propCollectorRef;

		ManagedObjectReference propFilter = vimPort.createFilter(propColl,
				pSpec, false);

		WaitOptions wOptions = new WaitOptions();
		if (maxWaitSeconds == null && maxOjectUpdates == null) {
			// DO Nothing
		} else if (maxWaitSeconds != null) {
			wOptions.setMaxWaitSeconds(Integer.parseInt(maxWaitSeconds));
		} else if (maxOjectUpdates != null) {
			wOptions.setMaxObjectUpdates(Integer.parseInt(maxOjectUpdates));
		}
		/*****************************
		// Start from the root folder
		ManagedObjectReference container = serviceContent.getRootFolder();
		Map<String, ManagedObjectReference> hosts = getMOREFsInContainerByType(
				container, "HostSystem");
		ManagedObjectReference reference = new ManagedObjectReference();
		if (VMName != null) {
			if (hosts.containsKey(VMName)) {
				hostList.put(VMName, hosts.get(VMName));
				reference =  hosts.get(VMName);
			}
		}
		
		
		UpdateSet update1 = vimPort.waitForUpdatesEx(reference, null);*****************************/
		UpdateSet update = vimPort.waitForUpdatesEx(propCollectorRef, null,
				wOptions);
		// if(update.isTruncated() != null && update.isTruncated()) {
		// callWaitForUpdatesEx(maxWaitSeconds, maxOjectUpdates,VMName);
		// } else {
		if (update != null && update.getFilterSet() != null) {
			ArrayList<ObjectUpdate> vmUpdates = new ArrayList<ObjectUpdate>();
			ArrayList<ObjectUpdate> hostUpdates = new ArrayList<ObjectUpdate>();
			List<PropertyFilterUpdate> pfus = update.getFilterSet();
			for (int pfui = 0; pfui < pfus.size(); ++pfui) {
				List<ObjectUpdate> ous = pfus.get(pfui).getObjectSet();
				for (int oui = 0; oui < ous.size(); ++oui) {
					if (ous.get(oui).getObj().getType()
							.equals("VirtualMachine")) {
						vmUpdates.add(ous.get(oui));
					} else if (ous.get(oui).getObj().getType().equals(
							"HostSystem")) {
						hostUpdates.add(ous.get(oui));
					}
				}
			}

			if (vmUpdates.size() > 0) {
				//System.out.println("Virtual Machine updates:");
				for (Iterator<ObjectUpdate> vmi = vmUpdates.iterator(); vmi
						.hasNext();) {
					resultStr += handleObjectUpdate((ObjectUpdate) vmi.next());
				}
			}
			if (hostUpdates.size() > 0) {
				//System.out.println("Host updates:");
				for (Iterator<ObjectUpdate> vmi = hostUpdates.iterator(); vmi
						.hasNext();) {
					resultStr += handleObjectUpdate((ObjectUpdate) vmi.next());
				}
			}
		} else {
			System.out.println("No update is present!");
		}
		// }
		vimPort.destroyPropertyFilter(propFilter);
		return resultStr.substring(0, resultStr.length() - 1);
	}

	private static String handleObjectUpdate(ObjectUpdate oUpdate) {
		String cpu = "";
		String memory = "";
		String concatStr = "";
		List<PropertyChange> pc = oUpdate.getChangeSet();
		if (oUpdate.getKind() == ObjectUpdateKind.ENTER) {
			//System.out.println(" New Data:");
			for (int pci = 0; pci < pc.size(); ++pci) {
				String name = pc.get(pci).getName();
				Object value = pc.get(pci).getVal();
				PropertyChangeOp op = pc.get(pci).getOp();
				if (op != PropertyChangeOp.REMOVE) {
					//System.out.println("  Property Name: " + name);
					if ("summary.quickStats".equals(name)) {
						if (value instanceof VirtualMachineQuickStats) {
							VirtualMachineQuickStats vmqs = (VirtualMachineQuickStats) value;
							cpu = vmqs.getOverallCpuUsage() == null ? "unavailable"
									: vmqs.getOverallCpuUsage().toString();
//							memory = vmqs.getHostMemoryUsage() == null ? "unavailable"
//									: vmqs.getHostMemoryUsage().toString();
							memory = vmqs.getGuestMemoryUsage() == null ? "unavailable"
									: vmqs.getGuestMemoryUsage().toString();
//							String memory1= vmqs.getCompressedMemory().toString();
//							String memory2 = vmqs.getDistributedMemoryEntitlement().toString();
//							String memory3 = vmqs.getBalloonedMemory().toString();
//							String memory4= vmqs.getPrivateMemory().toString();
//							String memory5= vmqs.getSharedMemory().toString();
//							String memory6= vmqs.getSsdSwappedMemory().toString();
//							String memory7= vmqs.getStaticMemoryEntitlement().toString();
							concatStr += cpu + ":" + memory + ",";
						} else if (value instanceof HostListSummaryQuickStats) {
							HostListSummaryQuickStats hsqs = (HostListSummaryQuickStats) value;
							cpu = hsqs.getOverallCpuUsage() == null ? "unavailable"
									: hsqs.getOverallCpuUsage().toString();
							memory = hsqs.getOverallMemoryUsage() == null ? "unavailable"
									: hsqs.getOverallMemoryUsage().toString();
//							String memory1 = hsqs.getDistributedMemoryFairness().toString();
							concatStr += cpu + ":" + memory + ",";
						}
					} else if ("runtime".equals(name)) {
						if (value instanceof VirtualMachineRuntimeInfo) {
							VirtualMachineRuntimeInfo vmri = (VirtualMachineRuntimeInfo) value;
							XMLGregorianCalendar bTime = vmri.getBootTime();
							if (bTime != null) {
							}
							Long mOverhead = vmri.getMemoryOverhead();
							if (mOverhead != null) {
							}
							// CPU,内存的最大使用量
							String maxCpuUsage = vmri.getMaxCpuUsage() == null ? "0"
									: vmri.getMaxCpuUsage().toString();
							String maxMemoryUsage = vmri.getMaxMemoryUsage() == null ? "0"
									: vmri.getMaxMemoryUsage().toString();
							concatStr += vmri.getPowerState().toString() + ":"
									+ maxCpuUsage + ":" + maxMemoryUsage + ":";
						} else if (value instanceof HostRuntimeInfo) {
							HostRuntimeInfo hri = (HostRuntimeInfo) value;
							XMLGregorianCalendar bTime = hri.getBootTime();
							if (bTime != null) {
							}
						}
					} else if ("name".equals(name)) {
						concatStr += value + ":";
					} else {
					}
				} else {
				}
			}
		}
		return concatStr;
	}

	private static List<PropertySpec> buildPropertySpecArray(String[][] typeinfo) {
		// Eliminate duplicates
		HashMap<String, Set<String>> tInfo = new HashMap<String, Set<String>>();
		for (int ti = 0; ti < typeinfo.length; ++ti) {
			Set<String> props = (Set<String>) tInfo.get(typeinfo[ti][0]);
			if (props == null) {
				props = new HashSet<String>();
				tInfo.put(typeinfo[ti][0], props);
			}
			boolean typeSkipped = false;
			for (int pi = 0; pi < typeinfo[ti].length; ++pi) {
				String prop = typeinfo[ti][pi];
				if (typeSkipped) {
					props.add(prop);
				} else {
					typeSkipped = true;
				}
			}
		}
		// Create PropertySpecs
		ArrayList<PropertySpec> pSpecs = new ArrayList<PropertySpec>();
		for (Iterator<String> ki = tInfo.keySet().iterator(); ki.hasNext();) {
			String type = (String) ki.next();
			PropertySpec pSpec = new PropertySpec();
			Set<?> props = (Set<?>) tInfo.get(type);
			pSpec.setType(type);
			pSpec.setAll(props.isEmpty() ? Boolean.TRUE : Boolean.FALSE);
			// pSpec.setPathSet(new String[props.size()]);
			int index = 0;
			for (Iterator<?> pi = props.iterator(); pi.hasNext();) {
				String prop = (String) pi.next();
				pSpec.getPathSet().add(index++, prop);
			}
			pSpecs.add(pSpec);
		}
		List<PropertySpec> res = new ArrayList<PropertySpec>();
		res.addAll(pSpecs);
		return res;
	}

	

	/*---------------------------------------------------------------------虚拟机迁移开始-------------------------------------------------*/
	/**
	 * 
	 * 虚拟机迁移
	 * 
	 * @param VMName
	 *            虚拟机名称 datastoreName 存储名字
	 */
	public static String relocate(String VMName, String datastoreName)
			throws Exception {
		String status = "";
		// cookie logic
		List<?> cookies = (List<?>) headers.get("Set-cookie");
		cookieValue = (String) cookies.get(0);
		StringTokenizer tokenizer = new StringTokenizer(cookieValue, ";");
		cookieValue = tokenizer.nextToken();
		String path = "$" + tokenizer.nextToken();
		String cookie = "$Version=\"1\"; " + cookieValue + "; " + path;

		// set the cookie in the new request header
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("Cookie", Collections.singletonList(cookie));
		((BindingProvider) vimPort).getRequestContext().put(
				MessageContext.HTTP_REQUEST_HEADERS, map);

		// get vm by vmname
		ManagedObjectReference vmMOR = getVmByVMname(VMName);

		ManagedObjectReference dsMOR = null;
		ArrayList<ManagedObjectReference> dcmors = getDecendentMoRefs(null, "Datacenter");
		Iterator<ManagedObjectReference> it = dcmors.iterator();
		while (it.hasNext()) {
			List<DynamicProperty> datastoresDPList = getDynamicProarray(
					(ManagedObjectReference) it.next(), "Datacenter",
					"datastore");
			ArrayOfManagedObjectReference dsMOArray = (ArrayOfManagedObjectReference) datastoresDPList
					.get(0).getVal();
			List<ManagedObjectReference> datastores = dsMOArray
					.getManagedObjectReference();
			boolean found = false;
			if (datastores != null) {
				for (int j = 0; j < datastores.size(); j++) {
					DatastoreSummary ds = getDataStoreSummary(datastores.get(j));
					String name = ds.getName();
					if (name.equalsIgnoreCase(datastoreName)) {
						dsMOR = datastores.get(j);
						found = true;
						j = datastores.size() + 1;
					}
				}
			}
			if (found) {
				break;
			}
		}

		if (dsMOR == null) {
			System.out.println("Datastore " + datastoreName + " Not Found");
			return "";
		}

		if (vmMOR != null) {
			VirtualMachineRelocateSpec rSpec = new VirtualMachineRelocateSpec();
			// String moveType = diskMoveType;
			// if(moveType.equalsIgnoreCase("moveChildMostDiskBacking")) {
			// rSpec.setDiskMoveType("moveChildMostDiskBacking");
			// } else
			// if(moveType.equalsIgnoreCase("moveAllDiskBackingsAndAllowSharing"))
			// {
			// rSpec.setDiskMoveType("moveAllDiskBackingsAndAllowSharing");
			// }
			rSpec.setDatastore(dsMOR);
			try {
				ManagedObjectReference taskMOR = vimPort.relocateVMTask(vmMOR,
						rSpec, null);
				status = waitForTask(taskMOR);
				if (status.equalsIgnoreCase("failure")) {
					System.out.println("Failure -: Linked clone "
							+ "cannot be relocated");
				}
				if (status.equalsIgnoreCase("sucess")) {
					System.out.println("Linked Clone relocated successfully.");
				}
			} catch (SOAPFaultException sfe) {
				printSoapFaultException(sfe);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Virtual Machine " + VMName + " doesn't exist");
		}
		return status;
	}

	
	private static ArrayList<ManagedObjectReference> getDecendentMoRefs(ManagedObjectReference root,
			String type) throws Exception {
		ArrayList<ManagedObjectReference> mors = getDecendentMoRefs(root, type, null);
		return mors;
	}

	private static ArrayList<ManagedObjectReference> getDecendentMoRefs(ManagedObjectReference root,
			String type, String[][] filter) throws Exception {
		String[][] typeinfo = new String[][] { new String[] { type, "name" }, };

		List<ObjectContent> ocary = getContentsRecursively(null, root,
				typeinfo, true);

		ArrayList<ManagedObjectReference> refs = new ArrayList<ManagedObjectReference>();

		if (ocary == null || ocary.size() == 0) {
			return refs;
		}

		for (int oci = 0; oci < ocary.size(); oci++) {
			refs.add(ocary.get(oci).getObj());
		}

		if (filter != null) {
			ArrayList<ManagedObjectReference> filtermors = filterMOR(refs, filter);
			return filtermors;
		} else {
			return refs;
		}
	}

	private static List<ObjectContent> getContentsRecursively(
			ManagedObjectReference collector, ManagedObjectReference root,
			String[][] typeinfo, boolean recurse) throws Exception {
		if (typeinfo == null || typeinfo.length == 0) {
			return null;
		}
		ManagedObjectReference usecoll = collector;
		if (usecoll == null) {
			usecoll = serviceContent.getPropertyCollector();
		}

		ManagedObjectReference useroot = root;
		if (useroot == null) {
			useroot = serviceContent.getRootFolder();
		}

		List<SelectionSpec> selectionSpecs = null;
		if (recurse) {
			selectionSpecs = buildFullTraversal();
		}

		List<PropertySpec> propspecary = buildPropertySpecArray(typeinfo);
		ObjectSpec objSpec = new ObjectSpec();
		objSpec.setObj(useroot);
		objSpec.setSkip(Boolean.FALSE);
		objSpec.getSelectSet().addAll(selectionSpecs);
		List<ObjectSpec> objSpecList = new ArrayList<ObjectSpec>();
		objSpecList.add(objSpec);
		PropertyFilterSpec spec = new PropertyFilterSpec();
		spec.getPropSet().addAll(propspecary);
		spec.getObjectSet().addAll(objSpecList);
		List<PropertyFilterSpec> propertyFilterSpecList = new ArrayList<PropertyFilterSpec>();
		propertyFilterSpecList.add(spec);
		List<ObjectContent> retoc = retrievePropertiesAllObjects(propertyFilterSpecList);

		return retoc;
	}

	@SuppressWarnings("unused")
	private static ArrayList<ManagedObjectReference> filterMOR(ArrayList<ManagedObjectReference> mors, String[][] filter)
			throws Exception {
		ArrayList<ManagedObjectReference> filteredmors = new ArrayList<ManagedObjectReference>();
		for (int i = 0; i < mors.size(); i++) {
			for (int k = 0; k < filter.length; k++) {
				String prop = filter[k][0];
				String reqVal = filter[k][1];
				String value = getProp(((ManagedObjectReference) mors.get(i)),
						prop);
				if (reqVal == null) {
					continue;
				} else if (value == null && reqVal == null) {
					continue;
				} else if (value == null && reqVal != null) {
					k = filter.length + 1;
				} else if (value != null && value.equalsIgnoreCase(reqVal)) {
					filteredmors.add(mors.get(i));
				} else {
					k = filter.length + 1;
				}
			}
		}
		return filteredmors;
	}

	private static String getProp(ManagedObjectReference obj, String prop) {
		String propVal = null;
		try {
			List<DynamicProperty> dynaProArray = getDynamicProarray(obj, obj
					.getType().toString(), prop);
			propVal = (String) dynaProArray.get(0).getVal();
		} catch (SOAPFaultException sfe) {
			printSoapFaultException(sfe);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return propVal;
	}

	private static List<DynamicProperty> getDynamicProarray(
			ManagedObjectReference ref, String type, String propertyString)
			throws Exception {
		PropertySpec propertySpec = new PropertySpec();
		propertySpec.setAll(Boolean.FALSE);
		propertySpec.getPathSet().add(propertyString);
		propertySpec.setType(type);

		// Now create Object Spec
		ObjectSpec objectSpec = new ObjectSpec();
		objectSpec.setObj(ref);
		objectSpec.setSkip(Boolean.FALSE);
		objectSpec.getSelectSet().addAll(buildFullTraversal());
		// Create PropertyFilterSpec using the PropertySpec and ObjectPec
		// created above.
		PropertyFilterSpec propertyFilterSpec = new PropertyFilterSpec();
		propertyFilterSpec.getPropSet().add(propertySpec);
		propertyFilterSpec.getObjectSet().add(objectSpec);
		List<PropertyFilterSpec> listPfs = new ArrayList<PropertyFilterSpec>(1);
		listPfs.add(propertyFilterSpec);
		List<ObjectContent> oContList = retrievePropertiesAllObjects(listPfs);
		ObjectContent contentObj = oContList.get(0);
		List<DynamicProperty> objList = contentObj.getPropSet();
		return objList;
	}

	private static DatastoreSummary getDataStoreSummary(
			ManagedObjectReference dataStore) throws InvalidPropertyFaultMsg,
			RuntimeFaultFaultMsg, Exception {
		DatastoreSummary dataStoreSummary = new DatastoreSummary();
		PropertySpec propertySpec = new PropertySpec();
		propertySpec.setAll(Boolean.FALSE);
		propertySpec.getPathSet().add("summary");
		propertySpec.setType("Datastore");

		// Now create Object Spec
		ObjectSpec objectSpec = new ObjectSpec();
		objectSpec.setObj(dataStore);
		objectSpec.setSkip(Boolean.FALSE);
		objectSpec.getSelectSet().addAll(buildFullTraversal());
		// Create PropertyFilterSpec using the PropertySpec and ObjectPec
		// created above.
		PropertyFilterSpec propertyFilterSpec = new PropertyFilterSpec();
		propertyFilterSpec.getPropSet().add(propertySpec);
		propertyFilterSpec.getObjectSet().add(objectSpec);
		List<PropertyFilterSpec> listPfs = new ArrayList<PropertyFilterSpec>(1);
		listPfs.add(propertyFilterSpec);
		List<ObjectContent> oContList = retrievePropertiesAllObjects(listPfs);
		for (int j = 0; j < oContList.size(); j++) {
			List<DynamicProperty> propSetList = oContList.get(j).getPropSet();
			for (int k = 0; k < propSetList.size(); k++) {
				dataStoreSummary = (DatastoreSummary) propSetList.get(k)
						.getVal();
			}
		}
		return dataStoreSummary;
	}

	private static String waitForTask(ManagedObjectReference taskmor)
			throws Exception {
		List<String> infoList = new ArrayList<String>();
		infoList.add("info.state");
		infoList.add("info.error");
		List<String> stateList = new ArrayList<String>();
		stateList.add("state");
		Object[] result = waitingForValues(taskmor, infoList, stateList,
				new Object[][] { new Object[] { TaskInfoState.SUCCESS,
						TaskInfoState.ERROR } });
		if (result[0].equals(TaskInfoState.SUCCESS)) {
			return "sucess";
		} else {
			List<DynamicProperty> tinfoProps = new ArrayList<DynamicProperty>();
			tinfoProps = getDynamicProarray(taskmor, "Task", "info");
			TaskInfo tinfo = (TaskInfo) tinfoProps.get(0).getVal();
			LocalizedMethodFault fault = tinfo.getError();
			String error = "Error Occured";
			if (fault != null) {
				error = fault.getLocalizedMessage();
				System.out.println("Message " + fault.getLocalizedMessage());
			}
			return error;
		}
	}

	private static Object[] waitingForValues(ManagedObjectReference objref,
			List<String> filterProps, List<String> endWaitProps,
			Object[][] expectedVals) throws Exception {
		// version string is initially null
		String version = "";
		Object[] endVals = new Object[endWaitProps.size()];
		Object[] filterVals = new Object[filterProps.size()];
		ObjectSpec objSpec = new ObjectSpec();
		objSpec.setObj(objref);
		objSpec.setSkip(Boolean.FALSE);
		PropertyFilterSpec spec = new PropertyFilterSpec();
		spec.getObjectSet().add(objSpec);
		PropertySpec propSpec = new PropertySpec();
		propSpec.getPathSet().addAll(filterProps);
		propSpec.setType(objref.getType());
		spec.getPropSet().add(propSpec);

		ManagedObjectReference filterSpecRef = vimPort.createFilter(
				propCollectorRef, spec, true);

		boolean reached = false;

		UpdateSet updateset = null;
		List<PropertyFilterUpdate> filtupary = null;
		PropertyFilterUpdate filtup = null;
		List<ObjectUpdate> objupary = null;
		ObjectUpdate objup = null;
		List<PropertyChange> propchgary = null;
		PropertyChange propchg = null;
		while (!reached) {
			boolean retry = true;
			while (retry) {
				try {
					updateset = vimPort.waitForUpdates(propCollectorRef,
							version);
					retry = false;
				} catch (SOAPFaultException sfe) {
					printSoapFaultException(sfe);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (updateset != null) {
				version = updateset.getVersion();
			}
			if (updateset == null || updateset.getFilterSet() == null) {
				continue;
			}

			// Make this code more general purpose when PropCol changes later.
			filtupary = updateset.getFilterSet();
			filtup = null;
			for (int fi = 0; fi < filtupary.size(); fi++) {
				filtup = filtupary.get(fi);
				objupary = filtup.getObjectSet();
				objup = null;
				propchgary = null;
				for (int oi = 0; oi < objupary.size(); oi++) {
					objup = objupary.get(oi);

					if (objup.getKind() == ObjectUpdateKind.MODIFY
							|| objup.getKind() == ObjectUpdateKind.ENTER
							|| objup.getKind() == ObjectUpdateKind.LEAVE) {
						propchgary = objup.getChangeSet();
						for (int ci = 0; ci < propchgary.size(); ci++) {
							propchg = propchgary.get(ci);
							updateValues(endWaitProps, endVals, propchg);
							updateValues(filterProps, filterVals, propchg);
						}
					}
				}
			}

			Object expctdval = null;
			// Check if the expected values have been reached and exit the loop
			// if done.
			// Also exit the WaitForUpdates loop if this is the case.
			for (int chgi = 0; chgi < endVals.length && !reached; chgi++) {
				for (int vali = 0; vali < expectedVals[chgi].length && !reached; vali++) {
					expctdval = expectedVals[chgi][vali];
					reached = expctdval.equals(endVals[chgi]) || reached;
				}
			}
		}

		// Destroy the filter when we are done.
		vimPort.destroyPropertyFilter(filterSpecRef);

		return filterVals;
	}

	private static void updateValues(List<String> props, Object[] vals,
			PropertyChange propchg) {
		for (int findi = 0; findi < props.size(); findi++) {
			if (propchg.getName().lastIndexOf(props.get(findi)) >= 0) {
				if (propchg.getOp() == PropertyChangeOp.REMOVE) {
					vals[findi] = "";
				} else {
					vals[findi] = propchg.getVal();
				}
			}
		}
	}

	/*---------------------------------------------------------------------虚拟机迁移结束-------------------------------------------------*/

	// 取得所有datastore,供迁移用
	public static List<String> getDataStores() throws Exception {
		ArrayList<ManagedObjectReference> dcmors = getDecendentMoRefs(null, "Datacenter");
		Iterator<ManagedObjectReference> it = dcmors.iterator();
		List<String> datastoreList = new ArrayList<String>();
		while (it.hasNext()) {
			List<DynamicProperty> datastoresDPList = getDynamicProarray(
					(ManagedObjectReference) it.next(), "Datacenter",
					"datastore");
			ArrayOfManagedObjectReference dsMOArray = (ArrayOfManagedObjectReference) datastoresDPList
					.get(0).getVal();
			List<ManagedObjectReference> datastores = dsMOArray
					.getManagedObjectReference();
			if (datastores != null) {
				for (int j = 0; j < datastores.size(); j++) {
					DatastoreSummary ds = getDataStoreSummary(datastores.get(j));
					datastoreList.add(ds.getName());
				}
			}
		}
		return datastoreList;
	}

	/**
	 * Returns all the MOREFs of the specified type that are present under the
	 * folder
	 * 
	 * @param folder
	 *            {@link ManagedObjectReference} of the folder to begin the
	 *            search from
	 * @param morefType
	 *            Type of the managed entity that needs to be searched
	 * 
	 * @return Map of name and MOREF of the managed objects present. If none
	 *         exist then empty Map is returned
	 * 
	 * @throws InvalidPropertyFaultMsg
	 * @throws RuntimeFaultFaultMsg
	 */
	private static Map<String, ManagedObjectReference> getMOREFsInFolderByType(
			ManagedObjectReference folder, String morefType)
			throws InvalidPropertyFaultMsg, RuntimeFaultFaultMsg {
		String PROP_ME_NAME = "name";
		ManagedObjectReference viewManager = serviceContent.getViewManager();
		ManagedObjectReference containerView = vimPort.createContainerView(
				viewManager, folder, Arrays.asList(morefType), true);

		Map<String, ManagedObjectReference> tgtMoref = new HashMap<String, ManagedObjectReference>();

		// Create Property Spec
		PropertySpec propertySpec = new PropertySpec();
		propertySpec.setAll(Boolean.FALSE);
		propertySpec.setType(morefType);
		propertySpec.getPathSet().add(PROP_ME_NAME);

		TraversalSpec ts = new TraversalSpec();
		ts.setName("view");
		ts.setPath("view");
		ts.setSkip(false);
		ts.setType("ContainerView");

		// Now create Object Spec
		ObjectSpec objectSpec = new ObjectSpec();
		objectSpec.setObj(containerView);
		objectSpec.setSkip(Boolean.TRUE);
		objectSpec.getSelectSet().add(ts);

		// Create PropertyFilterSpec using the PropertySpec and ObjectPec
		// created above.
		PropertyFilterSpec propertyFilterSpec = new PropertyFilterSpec();
		propertyFilterSpec.getPropSet().add(propertySpec);
		propertyFilterSpec.getObjectSet().add(objectSpec);

		List<PropertyFilterSpec> propertyFilterSpecs = new ArrayList<PropertyFilterSpec>();
		propertyFilterSpecs.add(propertyFilterSpec);

		List<ObjectContent> oCont = vimPort.retrieveProperties(serviceContent
				.getPropertyCollector(), propertyFilterSpecs);
		if (oCont != null) {
			for (ObjectContent oc : oCont) {
				ManagedObjectReference mr = oc.getObj();
				String entityNm = null;
				List<DynamicProperty> dps = oc.getPropSet();
				if (dps != null) {
					for (DynamicProperty dp : dps) {
						entityNm = (String) dp.getVal();
					}
				}
				tgtMoref.put(entityNm, mr);
			}
		}
		return tgtMoref;
	}

	
	@SuppressWarnings("unused")
	private static Map<String, ManagedObjectReference> getMOREFsInClusterProfileManager(
			ManagedObjectReference clusterProfileManager, String morefType)
			throws InvalidPropertyFaultMsg, RuntimeFaultFaultMsg {
		String PROP_ME_NAME = "name";
		ManagedObjectReference viewManager = serviceContent.getClusterProfileManager();
		ManagedObjectReference containerView = vimPort.createContainerView(
				viewManager, clusterProfileManager, Arrays.asList(morefType), true);

		Map<String, ManagedObjectReference> tgtMoref = new HashMap<String, ManagedObjectReference>();

		// Create Property Spec
		PropertySpec propertySpec = new PropertySpec();
		propertySpec.setAll(Boolean.FALSE);
		propertySpec.setType(morefType);
		propertySpec.getPathSet().add(PROP_ME_NAME);

		TraversalSpec ts = new TraversalSpec();
		ts.setName("view");
		ts.setPath("view");
		ts.setSkip(false);
		ts.setType("ClusterProfileManager");

		// Now create Object Spec
		ObjectSpec objectSpec = new ObjectSpec();
		objectSpec.setObj(containerView);
		objectSpec.setSkip(Boolean.TRUE);
		objectSpec.getSelectSet().add(ts);

		// Create PropertyFilterSpec using the PropertySpec and ObjectPec
		// created above.
		PropertyFilterSpec propertyFilterSpec = new PropertyFilterSpec();
		propertyFilterSpec.getPropSet().add(propertySpec);
		propertyFilterSpec.getObjectSet().add(objectSpec);

		List<PropertyFilterSpec> propertyFilterSpecs = new ArrayList<PropertyFilterSpec>();
		propertyFilterSpecs.add(propertyFilterSpec);

		List<ObjectContent> oCont = vimPort.retrieveProperties(serviceContent
				.getPropertyCollector(), propertyFilterSpecs);
		if (oCont != null) {
			for (ObjectContent oc : oCont) {
				ManagedObjectReference mr = oc.getObj();
				String entityNm = null;
				List<DynamicProperty> dps = oc.getPropSet();
				if (dps != null) {
					for (DynamicProperty dp : dps) {
						entityNm = (String) dp.getVal();
					}
				}
				tgtMoref.put(entityNm, mr);
			}
		}
		return tgtMoref;
	}

	

	/**
	 * Get a MORef from the property returned.
	 * 
	 * @param objMor
	 *            Object to get a reference property from
	 * @param propName
	 *            name of the property that is the MORef
	 * @return the ManagedObjectReference for that property.
	 */
	private static ManagedObjectReference getMorFromPropertyName(
			ManagedObjectReference objMor, String propName) throws Exception {
		Object props = getDynamicProperty(objMor, propName);
		ManagedObjectReference propmor = null;
		if (!props.getClass().isArray()) {
			propmor = (ManagedObjectReference) props;
		}
		return propmor;
	}

	private static String getEntityName(ManagedObjectReference obj,
			String entityType) {
		String retVal = null;
		try {
			// Create Property Spec
			PropertySpec propertySpec = new PropertySpec();
			propertySpec.setAll(Boolean.FALSE);
			propertySpec.getPathSet().add("name");
			propertySpec.setType(entityType);

			// Now create Object Spec
			ObjectSpec objectSpec = new ObjectSpec();
			objectSpec.setObj(obj);

			// Create PropertyFilterSpec using the PropertySpec and ObjectPec
			// created above.
			PropertyFilterSpec propertyFilterSpec = new PropertyFilterSpec();
			propertyFilterSpec.getPropSet().add(propertySpec);
			propertyFilterSpec.getObjectSet().add(objectSpec);

			List<PropertyFilterSpec> listpfs = new ArrayList<PropertyFilterSpec>(
					1);
			listpfs.add(propertyFilterSpec);
			List<ObjectContent> listobjcont = retrievePropertiesAllObjects(listpfs);
			if (listobjcont != null) {
				for (ObjectContent oc : listobjcont) {
					List<DynamicProperty> dps = oc.getPropSet();
					if (dps != null) {
						for (DynamicProperty dp : dps) {
							retVal = (String) dp.getVal();
							return retVal;
						}
					}
				}
			}
		} catch (SOAPFaultException sfe) {
			printSoapFaultException(sfe);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retVal;
	}

	/**
	 * Getting the MOREF of the entity.
	 */
	private static ManagedObjectReference getEntityByName(
			ManagedObjectReference mor, String entityName, String entityType) {
		ManagedObjectReference retVal = null;

		try {
			// Create Property Spec
			PropertySpec propertySpec = new PropertySpec();
			propertySpec.setAll(Boolean.FALSE);
			propertySpec.setType(entityType);
			propertySpec.getPathSet().add("name");

			// Now create Object Spec
			ObjectSpec objectSpec = new ObjectSpec();
			if (mor == null) {
				objectSpec.setObj(rootRef);
			} else {
				objectSpec.setObj(mor);
			}
			objectSpec.setSkip(Boolean.TRUE);
			objectSpec.getSelectSet().addAll(buildFullTraversal());

			// Create PropertyFilterSpec using the PropertySpec and ObjectPec
			// created above.
			PropertyFilterSpec propertyFilterSpec = new PropertyFilterSpec();
			propertyFilterSpec.getPropSet().add(propertySpec);
			propertyFilterSpec.getObjectSet().add(objectSpec);

			List<PropertyFilterSpec> listpfs = new ArrayList<PropertyFilterSpec>(
					1);
			listpfs.add(propertyFilterSpec);
			List<ObjectContent> listobjcont = retrievePropertiesAllObjects(listpfs);
			if (listobjcont != null) {
				for (ObjectContent oc : listobjcont) {
					if (getEntityName(oc.getObj(), entityType).equals(
							entityName)) {
						retVal = oc.getObj();
						break;
					}
				}
			}
		} catch (SOAPFaultException sfe) {
			printSoapFaultException(sfe);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retVal;
	}

	/**
	 * Getting the MOREF of the entity.
	 */
	private static List<ManagedObjectReference> getMorList(
			ManagedObjectReference mor, String entityType) {
		List<ManagedObjectReference> retVal = new ArrayList<ManagedObjectReference>();

		try {
			// Create Property Spec
			PropertySpec propertySpec = new PropertySpec();
			propertySpec.setAll(Boolean.FALSE);
			propertySpec.setType(entityType);
			propertySpec.getPathSet().add("name");

			// Now create Object Spec
			ObjectSpec objectSpec = new ObjectSpec();
			if (mor == null) {
				objectSpec.setObj(rootRef);
			} else {
				objectSpec.setObj(mor);
			}
			objectSpec.setSkip(Boolean.TRUE);
			objectSpec.getSelectSet().addAll(buildFullTraversal());

			// Create PropertyFilterSpec using the PropertySpec and ObjectPec
			// created above.
			PropertyFilterSpec propertyFilterSpec = new PropertyFilterSpec();
			propertyFilterSpec.getPropSet().add(propertySpec);
			propertyFilterSpec.getObjectSet().add(objectSpec);

			List<PropertyFilterSpec> listpfs = new ArrayList<PropertyFilterSpec>(
					1);
			listpfs.add(propertyFilterSpec);
			List<ObjectContent> listobjcont = retrievePropertiesAllObjects(listpfs);
			if (listobjcont != null) {
				for (ObjectContent oc : listobjcont) {
					retVal.add(oc.getObj());
				}
			}
		} catch (SOAPFaultException sfe) {
			printSoapFaultException(sfe);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retVal;
	}

	/**
	 * Getting the MOREF of the entity.
	 */
	private static ManagedObjectReference getFirstEntityByMOR(
			ManagedObjectReference mor, String entityType) {
		List<ManagedObjectReference> listmors = getMorList(mor, entityType);
		ManagedObjectReference retval = null;
		if (listmors.size() > 0) {
			return listmors.get(0);
		}
		return retval;
	}
	/*-----------------------------------------------------------------主机开始---------------------------------------------------------------------------------------*/
	// 获取主机配置开始
		public static List<Map<String,Object>> printHostProductDetails() {
			List<Map<String,Object>> maps = new ArrayList<Map<String,Object>>();
			try {
				setHostSystemAttributesList();
				TraversalSpec tSpec = getHostSystemTraversalSpec();
				// Create Property Spec
				PropertySpec propertySpec = new PropertySpec();
				propertySpec.setAll(Boolean.FALSE);
				propertySpec.getPathSet().addAll(hostSystemAttributesArr);
				propertySpec.setType("HostSystem");

				// Now create Object Spec
				ObjectSpec objectSpec = new ObjectSpec();
				objectSpec.setObj(rootRef);
				objectSpec.setSkip(Boolean.TRUE);
				objectSpec.getSelectSet().add(tSpec);

				// Create PropertyFilterSpec using the PropertySpec and ObjectPec
				// created above.
				PropertyFilterSpec propertyFilterSpec = new PropertyFilterSpec();
				propertyFilterSpec.getPropSet().add(propertySpec);
				propertyFilterSpec.getObjectSet().add(objectSpec);
				List<PropertyFilterSpec> pfSpecList = new ArrayList<PropertyFilterSpec>(
						1);
				pfSpecList.add(propertyFilterSpec);

				List<ObjectContent> listobjcont = retrievePropertiesAllObjects(pfSpecList);

				if (listobjcont != null) {
					for (ObjectContent oc : listobjcont) {
						List<DynamicProperty> dpList = oc.getPropSet();
						if (dpList != null) {
							Map<String,Object> hostKey2Value = new HashMap<String, Object>();
							for (DynamicProperty dp : dpList) {
								hostKey2Value.put(dp.getName(), dp.getVal());
							}
							maps.add(hostKey2Value);
						}
					}
				}
			} catch (SOAPFaultException sfe) {
				printSoapFaultException(sfe);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return maps;
		}

		private static final List<String> hostSystemAttributesArr = new ArrayList<String>();
		//hostsystem的属性列表
		private static void setHostSystemAttributesList() {
			hostSystemAttributesArr.add("name");
			hostSystemAttributesArr.add("config.product.productLineId");
			hostSystemAttributesArr.add("summary.hardware.cpuMhz");
			hostSystemAttributesArr.add("summary.hardware.numCpuCores");
			hostSystemAttributesArr.add("summary.hardware.cpuModel");
			hostSystemAttributesArr.add("summary.hardware.uuid");
			hostSystemAttributesArr.add("summary.hardware.vendor");
			hostSystemAttributesArr.add("summary.hardware.model");
			hostSystemAttributesArr.add("summary.hardware.memorySize");
			hostSystemAttributesArr.add("summary.hardware.numNics");
			hostSystemAttributesArr.add("summary.config.name");
			hostSystemAttributesArr.add("summary.config.product.osType");
			hostSystemAttributesArr.add("summary.config.vmotionEnabled");
			hostSystemAttributesArr.add("summary.quickStats.overallCpuUsage");
			hostSystemAttributesArr.add("summary.quickStats.overallMemoryUsage");
//			hostSystemAttributesArr.add("summary.config.sslThumbprint");//指纹证书
		}

		private static TraversalSpec getHostSystemTraversalSpec() {
			// Create a traversal spec that starts from the 'root' objects
			// and traverses the inventory tree to get to the Host system.
			// Build the traversal specs bottoms up
			SelectionSpec ss = new SelectionSpec();
			ss.setName("VisitFolders");

			// Traversal to get to the host from ComputeResource
			TraversalSpec computeResourceToHostSystem = new TraversalSpec();
			computeResourceToHostSystem.setName("computeResourceToHostSystem");
			computeResourceToHostSystem.setType("ComputeResource");
			computeResourceToHostSystem.setPath("host");
			computeResourceToHostSystem.setSkip(false);
			computeResourceToHostSystem.getSelectSet().add(ss);

			// Traversal to get to the ComputeResource from hostFolder
			TraversalSpec hostFolderToComputeResource = new TraversalSpec();
			hostFolderToComputeResource.setName("hostFolderToComputeResource");
			hostFolderToComputeResource.setType("Folder");
			hostFolderToComputeResource.setPath("childEntity");
			hostFolderToComputeResource.setSkip(false);
			hostFolderToComputeResource.getSelectSet().add(ss);

			// Traversal to get to the hostFolder from DataCenter
			TraversalSpec dataCenterToHostFolder = new TraversalSpec();
			dataCenterToHostFolder.setName("DataCenterToHostFolder");
			dataCenterToHostFolder.setType("Datacenter");
			dataCenterToHostFolder.setPath("hostFolder");
			dataCenterToHostFolder.setSkip(false);
			dataCenterToHostFolder.getSelectSet().add(ss);

			// TraversalSpec to get to the DataCenter from rootFolder
			TraversalSpec traversalSpec = new TraversalSpec();
			traversalSpec.setName("VisitFolders");
			traversalSpec.setType("Folder");
			traversalSpec.setPath("childEntity");
			traversalSpec.setSkip(false);
			List<SelectionSpec> sSpecArr = new ArrayList<SelectionSpec>();
			sSpecArr.add(ss);
			sSpecArr.add(dataCenterToHostFolder);
			sSpecArr.add(hostFolderToComputeResource);
			sSpecArr.add(computeResourceToHostSystem);
			traversalSpec.getSelectSet().addAll(sSpecArr);
			return traversalSpec;
		}

		// 获取主机配置结束
	/**
	 * This method returns a MoRef to the HostSystem with the supplied name
	 * under the supplied Folder. If hostname is null, it returns the first
	 * HostSystem found under the supplied Folder
	 * 
	 * @param hostFolderMor
	 *            MoRef to the Folder to look in
	 * @param hostName
	 *            Name of the HostSystem you are looking for
	 * @return MoRef to the HostSystem or null if not found
	 * @throws Exception
	 */

	private static ManagedObjectReference getHost(
			ManagedObjectReference hostFolderMor, String hostName)
			throws Exception {
		ManagedObjectReference hostmor = null;
		if (hostName != null) {
			hostmor = getEntityByName(hostFolderMor, hostName, "HostSystem");
		} else {
			hostmor = getFirstEntityByMOR(hostFolderMor, "HostSystem");
		}

		return hostmor;
	}

	private static HostVirtualNicSpec createVirtualNicSpecification(
			String ipaddress) {
		HostIpConfig hipconfig = new HostIpConfig();
		if (ipaddress != null && !ipaddress.isEmpty()) {
			hipconfig.setDhcp(Boolean.FALSE);
			hipconfig.setIpAddress(ipaddress);
			hipconfig.setSubnetMask("255.255.255.0");
		} else {
			hipconfig.setDhcp(Boolean.TRUE);
		}
		HostVirtualNicSpec hvnicspec = new HostVirtualNicSpec();
		hvnicspec.setIp(hipconfig);
		return hvnicspec;
	}

	/**
	 * @param vmName
	 *            Name of the virtual machine
	 * @return ManagedObjectReference spec for the virtual machine
	 */
	private static Object getHostConfigManagerrByHostMor(
			ManagedObjectReference hostmor) throws Exception {
		return getDynamicProperty(hostmor, "configManager");
	}

	/**
	 * Retrieves the MOREF of the host.
	 * 
	 * @param hostName
	 *            :
	 * @return
	 */
	public static ManagedObjectReference getHostByHostName(String hostName) {
		ManagedObjectReference retVal = null;

		try {
			TraversalSpec tSpec = getHostSystemTraversalSpec();
			// Create Property Spec
			PropertySpec propertySpec = new PropertySpec();
			propertySpec.setAll(Boolean.FALSE);
			propertySpec.getPathSet().add("name");
			propertySpec.setType("HostSystem");

			// Now create Object Spec
			ObjectSpec objectSpec = new ObjectSpec();
			objectSpec.setObj(rootRef);
			objectSpec.setSkip(Boolean.TRUE);
			objectSpec.getSelectSet().add(tSpec);

			// Create PropertyFilterSpec using the PropertySpec and ObjectPec
			// created above.
			PropertyFilterSpec propertyFilterSpec = new PropertyFilterSpec();
			propertyFilterSpec.getPropSet().add(propertySpec);
			propertyFilterSpec.getObjectSet().add(objectSpec);
			List<PropertyFilterSpec> listpfs = new ArrayList<PropertyFilterSpec>(
					1);
			listpfs.add(propertyFilterSpec);
			List<ObjectContent> listobjcont = retrievePropertiesAllObjects(listpfs);

			if (listobjcont != null) {
				for (ObjectContent oc : listobjcont) {
					ManagedObjectReference mr = oc.getObj();
					String hostnm = null;
					List<DynamicProperty> listDynamicProps = oc.getPropSet();
					DynamicProperty[] dps = listDynamicProps
							.toArray(new DynamicProperty[listDynamicProps
									.size()]);
					if (dps != null) {
						for (DynamicProperty dp : dps) {
							hostnm = (String) dp.getVal();
						}
					}
					if (hostnm != null && hostnm.equals(hostName)) {
						retVal = mr;
						break;
					}
				}
			} else {
				System.out.println("The Object Content is Null");
			}
		} catch (SOAPFaultException sfe) {
			printSoapFaultException(sfe);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retVal;
	}

	public static void addVirtualNIC(String hostname, String portgroupname,
			String ipaddress) throws Exception {
		ManagedObjectReference dcmor;
		ManagedObjectReference hostfoldermor;
		ManagedObjectReference hostmor = null;

		if (((dataCenterName != null) && (hostname != null))
				|| ((dataCenterName != null) && (hostname == null))) {
			dcmor = getDatacenterByName(dataCenterName);
			if (dcmor == null) {
				System.out.println("Datacenter not found");
				return;
			}
			hostfoldermor = getMorFromPropertyName(dcmor, "hostFolder");
			hostmor = getHost(hostfoldermor, hostname);
		} else if ((dataCenterName == null) && (hostname != null)) {
			hostmor = getHostByHostName(hostname);
		}
		if (hostmor != null) {
			Object cmobj = getHostConfigManagerrByHostMor(hostmor);
			HostConfigManager configMgr = (HostConfigManager) cmobj;
			ManagedObjectReference nwSystem = configMgr.getNetworkSystem();
			HostPortGroupSpec portgrp = new HostPortGroupSpec();
			portgrp.setName(portgroupname);

			HostVirtualNicSpec vNicSpec = createVirtualNicSpecification(ipaddress);
			String nic = vimPort.addVirtualNic(nwSystem, portgroupname,
					vNicSpec);

			System.out.println("Successful in creating nic : " + nic
					+ " with PortGroup :" + portgroupname);
		} else {
			System.out.println("Host not found");
		}
	}

	@SuppressWarnings("unchecked")
	public static void removeVirtualNic(String host, String portgroupname) {
		ManagedObjectReference dcmor;
		ManagedObjectReference hostfoldermor;
		ManagedObjectReference hostmor = null;

		try {
			if (((dataCenterName != null) && (host != null))
					|| ((dataCenterName != null) && (host == null))) {
				dcmor = getDatacenterByName(dataCenterName);
				if (dcmor == null) {
					System.out.println("Datacenter not found");
					return;
				}
				hostfoldermor = getMorFromPropertyName(dcmor, "hostFolder");
				hostmor = getHost(hostfoldermor, host);
			} else if ((dataCenterName == null) && (host != null)) {
				hostmor = getHost(null, host);
			}

			if (hostmor != null) {
				Object cmobj = getHostConfigManagerrByHostMor(hostmor);
				HostConfigManager configMgr = (HostConfigManager) cmobj;
				ManagedObjectReference nwSystem = configMgr.getNetworkSystem();

				List<HostVirtualNic> hvncArr = (List<HostVirtualNic>) getDynamicProperty(
						nwSystem, "networkInfo.vnic");
				boolean foundOne = false;
				for (HostVirtualNic nic : hvncArr) {
					String portGroup = nic.getPortgroup();
					if (portGroup.equals(portgroupname)) {
						vimPort.removeVirtualNic(nwSystem, nic.getDevice());
						foundOne = true;
					}
				}
				if (foundOne) {
					System.out
							.println("Successfully removed virtual nic from portgroup : "
									+ portgroupname);
				} else {
					System.out.println("No virtual nic found on portgroup : "
							+ portgroupname);
				}
			} else {
				System.out.println("Host not found");
			}
		} catch (SOAPFaultException sfe) {
			printSoapFaultException(sfe);
		} catch (HostConfigFaultFaultMsg ex) {
			System.out.println("Failed : Configuration falilures. ");
		} catch (NotFoundFaultMsg ex) {
			System.out.println("Failed : " + ex);
		} catch (RuntimeFaultFaultMsg ex) {
			System.out.println("Failed : " + ex);
		} catch (Exception e) {
			System.out.println("Failed : " + e);
		}

	}

	public static void addVirtualSwitchPortGroup(String host,
			String virtualswitchid, String portgroupname, int vlanId)
			throws InvalidPropertyFaultMsg, RuntimeFaultFaultMsg {
		Map<String, ManagedObjectReference> hostList = getMOREFsInFolderByType(
				serviceContent.getRootFolder(), "HostSystem");
		ManagedObjectReference hostmor = hostList.get(host);
		try {
			if (hostmor != null) {
				HostConfigManager configMgr = (HostConfigManager) getEntityProps(
						hostmor, new String[] { "configManager" }).get(
						"configManager");
				ManagedObjectReference nwSystem = configMgr.getNetworkSystem();

				HostPortGroupSpec portgrp = new HostPortGroupSpec();
				portgrp.setName(portgroupname);
				portgrp.setVswitchName(virtualswitchid);
				portgrp.setPolicy(new HostNetworkPolicy());
				portgrp.setVlanId(vlanId);
				vimPort.addPortGroup(nwSystem, portgrp);

				System.out.println("Successfully created : " + virtualswitchid
						+ "/" + portgroupname);
			} else {
				System.out.println("Host not found");
			}
		} catch (AlreadyExistsFaultMsg ex) {
			System.out.println("Failed creating : " + virtualswitchid + "/"
					+ portgroupname);
			System.out.println("Portgroup name already exists");
		} catch (HostConfigFaultFaultMsg ex) {
			System.out.println("Failed : Configuration failures. "
					+ " Reason : " + ex.getMessage());
		} catch (RuntimeFaultFaultMsg ex) {
			System.out.println("Failed creating : " + virtualswitchid + "/"
					+ portgroupname);
		} catch (SOAPFaultException sfe) {
			printSoapFaultException(sfe);
		} catch (Exception ex) {
			System.out.println("Failed creating : " + virtualswitchid + "/"
					+ portgroupname);
		}
	}

	public static void removeVirtualSwitchPortGroup(String host,
			String portgroupname) throws InvalidPropertyFaultMsg,
			RuntimeFaultFaultMsg {
		Map<String, ManagedObjectReference> hostList = getMOREFsInFolderByType(
				serviceContent.getRootFolder(), "HostSystem");
		ManagedObjectReference hostmor = hostList.get(host);

		if (hostmor != null) {
			try {
				HostConfigManager configMgr = (HostConfigManager) getEntityProps(
						hostmor, new String[] { "configManager" }).get(
						"configManager");
				vimPort.removePortGroup(configMgr.getNetworkSystem(),
						portgroupname);
				System.out.println("Successfully removed portgroup "
						+ portgroupname);
			} catch (HostConfigFaultFaultMsg ex) {
				System.out.println("Failed removing " + portgroupname);
				System.out.println("Datacenter or Host may be invalid \n");
			} catch (NotFoundFaultMsg ex) {
				System.out.println("Failed removing " + portgroupname);
				System.out.println(" portgroup not found.\n");
			} catch (ResourceInUseFaultMsg ex) {
				System.out
						.println("Failed removing portgroup " + portgroupname);
				System.out
						.println("port group can not be removed because "
								+ "there are virtual network adapters associated with it.");
			} catch (RuntimeFaultFaultMsg ex) {
				System.out.println("Failed removing " + portgroupname
						+ ex.getMessage());
			} catch (SOAPFaultException sfe) {
				printSoapFaultException(sfe);
			}
		} else {
			System.out.println("Host not found");
		}
	}

	

	/*
	 * 添加虚拟交换机 参数为主机名称，虚拟交换机名称，端口数
	 */
	public static void addVirtualSwitch(String host, String virtualswitchid,
			int portNum) throws InvalidPropertyFaultMsg, RuntimeFaultFaultMsg {
		Map<String, ManagedObjectReference> hostList = getMOREFsInFolderByType(
				serviceContent.getRootFolder(), "HostSystem");
		ManagedObjectReference hostmor = hostList.get(host);
		if (hostmor != null) {
			try {
				HostConfigManager configMgr = (HostConfigManager) getEntityProps(
						hostmor, new String[] { "configManager" }).get(
						"configManager");

				ManagedObjectReference nwSystem = configMgr.getNetworkSystem();
				HostVirtualSwitchSpec spec = new HostVirtualSwitchSpec();
				spec.setNumPorts(portNum + 8);

				vimPort.addVirtualSwitch(nwSystem, virtualswitchid, spec);
			} catch (AlreadyExistsFaultMsg ex) {
				System.out.println("Failed : Switch already exists "
						+ " Reason : " + ex.getMessage());
			} catch (HostConfigFaultFaultMsg ex) {
				System.out.println("Failed : Configuration failures. ");
			} catch (ResourceInUseFaultMsg ex) {
				System.out.println("Failed adding switch: " + virtualswitchid
						+ " Reason : " + ex.getMessage());
			} catch (RuntimeFaultFaultMsg ex) {
				System.out.println("Failed adding switch: " + virtualswitchid
						+ " Reason : " + ex.getMessage());
			} catch (SOAPFaultException sfe) {
				printSoapFaultException(sfe);
			} catch (Exception ex) {
				System.out.println("Failed adding switch: " + virtualswitchid
						+ " Reason : " + ex.getMessage());
			}
		} else {
			System.out.println("Host not found");
		}
	}

	

	/*
	 * 更新虚拟交换机物理网络适配器 参数为主机名称，虚拟交换机索引，物理适配器名称，添加还是删除
	 */
	public void updateVirtualSwitchPNIC(String host, int virtualSwitchIndex,
			String pnicName, boolean isAdd) throws InvalidPropertyFaultMsg,
			RuntimeFaultFaultMsg {
		Map<String, ManagedObjectReference> hostList = getMOREFsInFolderByType(
				serviceContent.getRootFolder(), "HostSystem");
		ManagedObjectReference hostmor = hostList.get(host);
		if (hostmor != null) {

			HostConfigManager configMgr = (HostConfigManager) getEntityProps(
					hostmor, new String[] { "configManager" }).get(
					"configManager");
			ManagedObjectReference nwSystem = configMgr.getNetworkSystem();

			HostNetworkInfo dd = new HostNetworkInfo();
			dd = (HostNetworkInfo) getEntityProps(nwSystem,
					new String[] { "networkInfo" }).get("networkInfo");
			HostVirtualSwitch lv = dd.getVswitch().get(virtualSwitchIndex);
			String vsnmae = lv.getName();

			HostVirtualSwitchSpec spec = lv.getSpec();

			HostVirtualSwitchBondBridge sss = (HostVirtualSwitchBondBridge) spec
					.getBridge();
			if (sss == null)
				sss = new HostVirtualSwitchBondBridge();
			if (isAdd)
				sss.getNicDevice().add(pnicName);

			else {
				if (sss.getNicDevice().contains(pnicName))
					sss.getNicDevice().remove(pnicName);
			}

			spec.setBridge(sss);
			try {
				vimPort.updateVirtualSwitch(nwSystem, vsnmae, spec);
			} catch (HostConfigFaultFaultMsg e) {
				System.out.println("Failed update switch: " + vsnmae
						+ " Reason : " + e.getMessage());
			} catch (NotFoundFaultMsg e) {
				System.out.println("Failed update switch: " + vsnmae
						+ " Reason : " + e.getMessage());
			} catch (ResourceInUseFaultMsg e) {
				System.out.println("Failed update switch: " + vsnmae
						+ " Reason : " + e.getMessage());
			}
		}

	}

	

	/*
	 * 移除虚拟交换机 参数为主机名称，虚拟交换机ID
	 */
	public static void removeVirtualSwitch(String host, String virtualswitchid)
			throws InvalidPropertyFaultMsg, RuntimeFaultFaultMsg {
		Map<String, ManagedObjectReference> hostList = getMOREFsInFolderByType(
				serviceContent.getRootFolder(), "HostSystem");
		ManagedObjectReference hostmor = hostList.get(host);
		if (hostmor != null) {
			try {
				HostConfigManager configMgr = (HostConfigManager) getEntityProps(
						hostmor, new String[] { "configManager" }).get(
						"configManager");
				ManagedObjectReference nwSystem = configMgr.getNetworkSystem();
				vimPort.removeVirtualSwitch(nwSystem, virtualswitchid);
				System.out.println(" : Successful removing : "
						+ virtualswitchid);
			} catch (HostConfigFaultFaultMsg ex) {
				System.out.println(" : Failed : Configuration falilures. ");
			} catch (NotFoundFaultMsg ex) {
				System.out.println("Failed : " + ex);
			} catch (ResourceInUseFaultMsg ex) {
				System.out.println(" : Failed removing switch "
						+ virtualswitchid);
				System.out.println("There are virtual network adapters "
						+ "associated with the virtual switch.");
			} catch (RuntimeFaultFaultMsg ex) {
				System.out.println(" : Failed removing switch: "
						+ virtualswitchid);
			} catch (SOAPFaultException sfe) {
				printSoapFaultException(sfe);
			}
		} else {
			System.out.println("Host not found");
		}
	}

	/*
	 * 获取某主机下的物理网络适配器列表 参数为主机名称
	 */
	public static List<PhysicalNic> getPNIC(String host)
			throws InvalidPropertyFaultMsg, RuntimeFaultFaultMsg {
		Map<String, ManagedObjectReference> hostList = getMOREFsInFolderByType(
				serviceContent.getRootFolder(), "HostSystem");
		ManagedObjectReference hostmor = hostList.get(host);

		HostNetworkInfo dd = new HostNetworkInfo();

		if (hostmor != null) {
			try {

				HostConfigManager configMgr = (HostConfigManager) getEntityProps(
						hostmor, new String[] { "configManager" }).get(
						"configManager");
				ManagedObjectReference nwSystem = configMgr.getNetworkSystem();
				dd = (HostNetworkInfo) getEntityProps(nwSystem,
						new String[] { "networkInfo" }).get("networkInfo");
			} catch (SOAPFaultException sfe) {
				printSoapFaultException(sfe);
			} catch (InvalidPropertyFaultMsg e) {
				e.printStackTrace();
			} catch (RuntimeFaultFaultMsg e) {
				e.printStackTrace();
			}
		}

		return dd.getPnic();
	}

	/*
	 * 获取某主机下的端口组 参数为主机名称
	 */
	public static List<HostPortGroup> getHostPortGroup(String host)
			throws InvalidPropertyFaultMsg, RuntimeFaultFaultMsg {
		Map<String, ManagedObjectReference> hostList = getMOREFsInFolderByType(
				serviceContent.getRootFolder(), "HostSystem");
		ManagedObjectReference hostmor = hostList.get(host);
		HostNetworkInfo dd = new HostNetworkInfo();

		if (hostmor != null) {
			try {
				HostConfigManager configMgr = (HostConfigManager) getEntityProps(
						hostmor, new String[] { "configManager" }).get(
						"configManager");
				ManagedObjectReference nwSystem = configMgr.getNetworkSystem();
				dd = (HostNetworkInfo) getEntityProps(nwSystem,
						new String[] { "networkInfo" }).get("networkInfo");
			} catch (SOAPFaultException sfe) {
				printSoapFaultException(sfe);
			} catch (InvalidPropertyFaultMsg e) {
				e.printStackTrace();
			} catch (RuntimeFaultFaultMsg e) {
				e.printStackTrace();
			}
		}

		return dd.getPortgroup();
	}
	/*
	 * 获取某主机下的分布式端口组 
	 * 参数为主机名称
	 */
	public static List<Map<String, String>> getHostDVSPortGroup(String host)
			throws InvalidPropertyFaultMsg, RuntimeFaultFaultMsg {
		Map<String, ManagedObjectReference> dvs = getMOREFsInFolderByType(
				serviceContent.getRootFolder(), "DistributedVirtualPortgroup");
		
		List<Map<String, String>> list= new ArrayList<Map<String, String>>();
		Set<String> set=dvs.keySet();
		
		for(int i=0;i<set.size();i++){
			ManagedObjectReference managedObjectReference=dvs.get(set.toArray()[i].toString());
			String key=managedObjectReference.getValue();
			set.iterator();
			System.out.println(set.toArray()[i].toString());
			String value = set.toArray()[i].toString();
			Map<String, String> map = new HashMap<String, String>();
			map.put(key, value);
			
			list.add(map);
		}
		
		return list;
	}
	/*
	 * 获取某主机下的虚拟交换机 
	 * 参数为主机名称
	 */
	public static List<HostVirtualSwitch> getHostVirtualSwitch(String host)
			throws InvalidPropertyFaultMsg, RuntimeFaultFaultMsg {
		Map<String, ManagedObjectReference> hostList = getMOREFsInFolderByType(
				serviceContent.getRootFolder(), "HostSystem");
		ManagedObjectReference hostmor = hostList.get(host);

		HostNetworkInfo dd = new HostNetworkInfo();

		if (hostmor != null) {
			try {

				HostConfigManager configMgr = (HostConfigManager) getEntityProps(
						hostmor, new String[] { "configManager" }).get(
						"configManager");
				ManagedObjectReference nwSystem = configMgr.getNetworkSystem();
				dd = (HostNetworkInfo) getEntityProps(nwSystem,
						new String[] { "networkInfo" }).get("networkInfo");
			} catch (SOAPFaultException sfe) {
				printSoapFaultException(sfe);
			} catch (InvalidPropertyFaultMsg e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RuntimeFaultFaultMsg e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return dd.getVswitch();
	}
	/*
	 * 获取某主机下的虚拟交换机 
	 * 参数为主机名称
	 */
	public static List<HostProxySwitch> getProxyVirtualSwitch(String host)
			throws InvalidPropertyFaultMsg, RuntimeFaultFaultMsg {
		Map<String, ManagedObjectReference> hostList = getMOREFsInFolderByType(
				serviceContent.getRootFolder(), "HostSystem");
		ManagedObjectReference hostmor = hostList.get(host);

		HostNetworkInfo dd = new HostNetworkInfo();

		if (hostmor != null) {
			try {

				HostConfigManager configMgr = (HostConfigManager) getEntityProps(
						hostmor, new String[] { "configManager" }).get(
						"configManager");
				ManagedObjectReference nwSystem = configMgr.getNetworkSystem();
				dd = (HostNetworkInfo) getEntityProps(nwSystem,
						new String[] { "networkInfo" }).get("networkInfo");
			} catch (SOAPFaultException sfe) {
				printSoapFaultException(sfe);
			} catch (InvalidPropertyFaultMsg e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RuntimeFaultFaultMsg e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return dd.getProxySwitch();
	}
	public static List<Map<String, Object>> getHostNetInfo(String host)
			throws InvalidPropertyFaultMsg, RuntimeFaultFaultMsg {

		List<HostVirtualSwitch> vs = getHostVirtualSwitch(host);
		List<HostPortGroup> pg = getHostPortGroup(host);
		List<PhysicalNic> pnicl = getPNIC(host);
		// List<ManagedObjectReference> hvm = getHostVirtualMachine(host);
		List<Map<String, Object>> vsmap = new ArrayList<Map<String, Object>>();
		for (HostVirtualSwitch hvs : vs) {
			if (hvs != null) {
				Map<String, Object> vmap = new HashMap<String, Object>();
				List<String> vspnic = hvs.getPnic();
				List<Map<String, String>> vspnicList = new ArrayList<Map<String, String>>();
				for (PhysicalNic pnic : pnicl) {
					Map<String, String> vspnicInfo = new HashMap<String, String>();
					if (vspnic.contains(pnic.getKey())) {
						vspnicInfo.put("pnicname", pnic.getDevice());
						PhysicalNicLinkInfo phil = pnic.getLinkSpeed();
						vspnicInfo.put("pnicspeed", String.valueOf(phil
								.getSpeedMb()));
						vspnicList.add(vspnicInfo);
					}

				}
				vmap.put("pniclist", vspnicList);
				vmap.put("vsname", hvs.getName());

				List<Map<String, String>> lp = new ArrayList<Map<String, String>>();
				for (HostPortGroup hpg : pg) {
					if (hpg != null) {
						Map<String, String> vspg = new HashMap<String, String>();
						HostPortGroupSpec hpgs = hpg.getSpec();

						if (hvs.getName().equals(hpgs.getVswitchName())) {
							vspg.put("portgroupname", hpgs.getName());
							vspg
									.put("vlanid", String.valueOf(hpgs
											.getVlanId()));
							lp.add(vspg);
						}
					}
				}
				vmap.put("vport", lp);
				vsmap.add(vmap);
			}
		}
		return vsmap;
	}
	public static void listNetworkResourcePool(String dvSwitchName) throws RuntimeFaultFaultMsg, InvalidPropertyFaultMsg {
	        ManagedObjectReference dvsMor =
	        	getMOREFsInFolderByType(serviceContent.getRootFolder(),
	                        "VmwareDistributedVirtualSwitch").get("dvSwitch");
	        if (dvsMor != null) {
	            List<DVSNetworkResourcePool> nrpList =
	                    ((ArrayOfDVSNetworkResourcePool)getEntityProps(dvsMor,
	                            new String[]{"networkResourcePool"}).get(
	                            "networkResourcePool")).getDVSNetworkResourcePool();
	            if (nrpList != null) {
	                System.out.println("Existing DVSNetworkResourcePool:");
	                for (DVSNetworkResourcePool dvsNrp : nrpList) {
	                    String nrp = "System defined DVSNetworkResourcePool";
	                    if (dvsNrp.getKey().startsWith("NRP")) {
	                        nrp = "User defined DVSNetworkResourcePool";
	                    }
	                    System.out.println(dvsNrp.getName()
	                            + " : networkResourcePool[\"" + dvsNrp.getKey() + "\"] : "
	                            + nrp);
	                }
	            } else {
	                System.out.println("No NetworkResourcePool found for DVS Switch "
	                        + dvSwitchName);
	                return;
	            }
	        } else {
	            System.out.println("DVS Switch " + dvSwitchName + " Not Found");
	        }
	    }
	public static DistributedVirtualSwitchProductSpec getDVSProductSpec(String version) throws RuntimeFaultFaultMsg {
	        List<DistributedVirtualSwitchProductSpec> dvsProdSpec =
	                vimPort.queryAvailableDvsSpec(serviceContent.getDvSwitchManager());
	        DistributedVirtualSwitchProductSpec dvsSpec = null;
	        if (version != null) {
	            for (DistributedVirtualSwitchProductSpec prodSpec : dvsProdSpec) {
	                if (version.equalsIgnoreCase(prodSpec.getVersion())) {
	                    dvsSpec = prodSpec;
	                }
	            }
	            if (dvsSpec == null) {
	                throw new IllegalArgumentException("DVS Version " + version
	                        + " not supported.");
	            }
	        } else {
	            dvsSpec = dvsProdSpec.get(dvsProdSpec.size() - 1);
	        }
	        return dvsSpec;
	    }
	public static List<Map<String, String>> getUnUsePNIC(String host)
			throws InvalidPropertyFaultMsg, RuntimeFaultFaultMsg {

		List<HostVirtualSwitch> vs = getHostVirtualSwitch(host);
		List<PhysicalNic> pnicl = getPNIC(host);

		List<Map<String, String>> vsmap = new ArrayList<Map<String, String>>();

		List<String> vspniclist = new ArrayList<String>();

		for (HostVirtualSwitch hvs : vs) {
			if (hvs != null) {
				List<String> vspnic = hvs.getPnic();
				for (String vpnic : vspnic) {
					if (!vspniclist.contains(vpnic))
						vspniclist.add(vpnic);
				}
			}
		}

		for (PhysicalNic pnic : pnicl) {
			if (!vspniclist.contains(pnic.getKey())) {
				Map<String, String> vspnicInfo = new HashMap<String, String>();
				vspnicInfo.put("pnicspeed", String.valueOf(pnic.getLinkSpeed()
						.getSpeedMb()));
				vspnicInfo.put("pnicname", pnic.getDevice());
				vsmap.add(vspnicInfo);
			}
		}
		return vsmap;
	}

	/*
	 * 更新某主机下的物理网络适配器链接速度 参数为主机名称和物理网络适配器Id，更新的速度，是否全双工
	 */
	public static boolean updatePNICSpeed(String host, String pnicId,
			int speedMB, boolean isDuplex) throws InvalidPropertyFaultMsg,
			RuntimeFaultFaultMsg {
		Map<String, ManagedObjectReference> hostList = getMOREFsInFolderByType(
				serviceContent.getRootFolder(), "HostSystem");
		ManagedObjectReference hostmor = hostList.get(host);

		HostNetworkInfo dd = new HostNetworkInfo();

		if (hostmor != null) {
			try {

				HostConfigManager configMgr = (HostConfigManager) getEntityProps(
						hostmor, new String[] { "configManager" }).get(
						"configManager");
				ManagedObjectReference nwSystem = configMgr.getNetworkSystem();
				dd = (HostNetworkInfo) getEntityProps(nwSystem,
						new String[] { "networkInfo" }).get("networkInfo");

				List<PhysicalNic> pnicList = dd.getPnic();

				for (PhysicalNic p : pnicList) {
					PhysicalNicLinkInfo phil = p.getLinkSpeed();

					if (p.getDevice().equals(pnicId)) {
						if (phil == null)
							phil = new PhysicalNicLinkInfo();

						phil.setSpeedMb(speedMB);
						phil.setDuplex(isDuplex);
						if (speedMB == 1000)// 1000MB只能全双工
							phil.setDuplex(true);
						try {
							vimPort.updatePhysicalNicLinkSpeed(nwSystem,
									pnicId, phil);
							return true;
						} catch (HostConfigFaultFaultMsg e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (NotFoundFaultMsg e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return false;
					}
				}
			} catch (SOAPFaultException sfe) {
				printSoapFaultException(sfe);
			} catch (InvalidPropertyFaultMsg e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RuntimeFaultFaultMsg e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return false;
	}

	public static Map<String, ManagedObjectReference> getResourcePoolList()
			throws InvalidPropertyFaultMsg, RuntimeFaultFaultMsg {
		Map<String, ManagedObjectReference> respoolList = getMOREFsInFolderByType(
				serviceContent.getRootFolder(), "ResourcePool");
		return respoolList;
	}

	public static void addResourcePool(String rpName)
			throws InvalidPropertyFaultMsg, RuntimeFaultFaultMsg {
		ManagedObjectReference cpl = null;
		Map<String, ManagedObjectReference> respoolList = VMWareUtil
				.getResourcePoolList();

		for (Map.Entry<String, ManagedObjectReference> entry : respoolList
				.entrySet()) {

			ManagedObjectReference rp = entry.getValue();
			if (rp != null && entry.getKey().equals("Resources")) {
				cpl = rp;
			}
		}

		if (cpl != null) {
			ResourceConfigSpec spec = new ResourceConfigSpec();
			ResourceAllocationInfo c = new ResourceAllocationInfo();
			c.setLimit(1000L);
			c.setExpandableReservation(true);
			spec.setCpuAllocation(c);
			spec.setMemoryAllocation(c);
			try {
				vimPort.createResourcePool(cpl, rpName, spec);
			} catch (DuplicateNameFaultMsg e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InsufficientResourcesFaultFaultMsg e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidNameFaultMsg e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 针对主机的一系列操作
	 * 
	 * @param operation
	 *            poweron(开机),poweroff(断电),shutdown(关机),suspend(挂起),reboot(重启),reset
	 *            (重置)
	 * @param vmName
	 *            虚拟机的名称
	 * @throws Exception
	 */
	public static void runHostOperation(String operation, String host)
			throws Exception {
		Map<String, ManagedObjectReference> vmMap = getHosts(host);

		if (vmMap == null || vmMap.isEmpty()) {
			System.out.println("No Virtual Machine found matching "
					+ "the specified criteria");
			return;
		} else {
			if (operation.equalsIgnoreCase("poweron")) {
				powerOnVM(vmMap);
			} else if (operation.equalsIgnoreCase("poweroff")) {
				powerOffVM(vmMap);
			} else if (operation.equalsIgnoreCase("reset")) {
				resetVM(vmMap);
			} else if (operation.equalsIgnoreCase("shutdown")) {
				shutdownVM(vmMap);
			} else if (operation.equalsIgnoreCase("suspend")) {
				suspendVM(vmMap);
			} else if (operation.equalsIgnoreCase("reboot")) {
				rebootVM(vmMap);
			} else if (operation.equalsIgnoreCase("reset")) {
				resetVM(vmMap);
			} else if (operation.equalsIgnoreCase("standby")) {
				standbyVM(vmMap);
			}
		}
	}
	/**
	 * 获取集群中指定的主机
	 * @param vmName
	 * @return
	 * @throws Exception
	 */
	public static Map<String, ManagedObjectReference> getHosts(String host)
			throws Exception {
		Map<String, ManagedObjectReference> hostList = new HashMap<String, ManagedObjectReference>();

		// Start from the root folder
		ManagedObjectReference container = serviceContent.getRootFolder();

		Map<String, ManagedObjectReference> hosts = getMOREFsInContainerByType(
				container, "HostSystem");

		if (host != null) {
			if (hosts.containsKey(host)) {
				hostList.put(host, hosts.get(host));
			}
			return hostList;
		}

		// If no filters are there then just the container based containment is
		// used.
		hostList = hosts;
		
//		HealthSystemRuntime info = new HealthSystemRuntime();
//		HostSystemHealthInfo a = info.getSystemHealthInfo();
//		 
//		a.getDynamicProperty();
		return hostList;
	}
	// 通过主机得到主机管理对象
		public static ManagedObjectReference getHostByName(String host) {
			ManagedObjectReference obj = null;
			try {
				obj = getMOREFsInContainerByType(serviceContent.getRootFolder(),
						"HostSystem").get(host);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return obj;
		}
	//主机连接
	public static HostConnectInfo queryConnectionInfo(String hostIp,String userName,String password) throws Exception{
		int port = 0;
		String sslThumbprint = null;
		
		
		 HostConnectInfo hostConnectInfo = null;
		try {
			ManagedObjectReference hostmor = getHost(null, "ESXi_HOST1");
			hostConnectInfo = vimPort.queryConnectionInfo(hostmor,hostIp, port, userName, password, sslThumbprint);
//			vimPort.queryHostConnectionInfo(arg0);
		} catch (HostConnectFaultFaultMsg e) {
			e.printStackTrace();
		} catch (InvalidLoginFaultMsg e) {
			e.printStackTrace();
		} catch (RuntimeFaultFaultMsg e) {
			e.printStackTrace();
		}
		 return hostConnectInfo;
	}
	/**
	 * 删除主机
	 * @param hostname
	 * @return
	 * @throws Exception 
	 */
	public static boolean removeHost(String hostname) throws Exception{
		try {
			ManagedObjectReference hostmor = getHostByHostName(hostname);
			if (hostmor!=null) {
 				vimPort.destroyTask(hostmor);
 			}else{
 				System.out.println("Host is not found");
 				return false;
 			}
		} catch (RuntimeFaultFaultMsg e) {
			e.printStackTrace();
			return false;
		}
		
         return true;
	}
	/*-----------------------------------------------------------------主机结束---------------------------------------------------------------------------------------*/
	
	/*-----------------------------------------------------------------数据中心开始---------------------------------------------------------------------------------------*/
	/**
	 * 获取指定的数据中心
	 * @param vmName
	 * @return
	 * @throws Exception
	 */
	public static Map<String, ManagedObjectReference> getDatacenters(String datacenterName)
			throws Exception {
		Map<String, ManagedObjectReference> vmList = new HashMap<String, ManagedObjectReference>();

		// Start from the root folder
		ManagedObjectReference container = serviceContent.getRootFolder();

		Map<String, ManagedObjectReference> datacenters = getMOREFsInContainerByType(
				container, "Datacenter");

		if (datacenterName != null) {
			if (datacenters.containsKey(datacenterName)) {
				vmList.put(datacenterName, datacenters.get(datacenterName));
			}
			return vmList;
		}

		// If no filters are there then just the container based containment is
		// used.
		vmList = datacenters;

		return vmList;
	}
	/**
	 * 创建数据中心
	 * @param datacenterName
	 * @return
	 */
	public static boolean createDataCenter(String datacenterName){
		ManagedObjectReference mor = serviceContent.getRootFolder();
		//判断数据中心名字是否存在
			
		try {
			vimPort.createDatacenter(mor, datacenterName);
		} catch (DuplicateNameFaultMsg e) {
			System.out.println("数据中心"+datacenterName+"名称已存在");
			e.printStackTrace();
			return false;
		} catch (InvalidNameFaultMsg e) {
			System.out.println("数据中心"+datacenterName+"名称校验失败");
			e.printStackTrace();
			return false;
		} catch (RuntimeFaultFaultMsg e) {
			System.out.println("数据中心"+datacenterName+"创建失败");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	/**
	 * 数据中心重命名
	 * @param oldName	原名称
	 * @param newrName	新名称
	 * @return
	 */
	public static boolean renameDatacenter(String oldName,String newrName){
		ManagedObjectReference datacenterMor = getDatacenterByName(oldName);
		//判断数据中心名字是否存在
		if(datacenterMor==null){
			System.out.println("数据中心"+oldName+"不存在");
			return false;
		}
		try {
			vimPort.renameTask(datacenterMor, newrName);
		} catch (DuplicateNameFaultMsg e) {
			System.out.println("数据中心"+newrName+"名称已存在");
			e.printStackTrace();
			return false;
		} catch (InvalidNameFaultMsg e) {
			System.out.println("数据中心"+newrName+"名称校验失败");
			e.printStackTrace();
			return false;
		} catch (RuntimeFaultFaultMsg e) {
			System.out.println("数据中心"+newrName+"创建失败");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	/**
	 * 删除数据中心
	 * @param datacentername
	 * @return
	 */
	public static boolean removeDatacenter(String datacentername){
		ManagedObjectReference datacenterMor = getDatacenterByName(datacentername);
		//判断数据中心名字是否存在
		if(datacenterMor==null){
			System.out.println("数据中心"+datacentername+"不存在");
			return false;
		}
		try {
			ManagedObjectReference taskmor =vimPort.destroyTask(datacenterMor);
			try {
				if (getTaskResultAfterDone(taskmor)) {
				    System.out.println("ManagedEntity '" + datacentername
				            + "' remove successfully.");
				} else {
				    System.out.println("Failure -: Managed Entity Cannot Be remove");
				}
			} catch (InvalidPropertyFaultMsg | InvalidCollectorVersionFaultMsg e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (RuntimeFaultFaultMsg e) {
			e.printStackTrace();
			return false;
		} catch (VimFaultFaultMsg e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	/**
	 * @param datacenterName
	 *            The name of the Datacenter
	 * @return ManagedObjectReference to the Datacenter
	 */
	private static ManagedObjectReference getDatacenterByName(
			String datacenterName) {
		ManagedObjectReference retVal = null;
		try {
			TraversalSpec tSpec = getDatacenterTraversalSpec();
			// Create Property Spec
			PropertySpec propertySpec = new PropertySpec();
			propertySpec.setAll(Boolean.FALSE);
			propertySpec.getPathSet().add("name");
			propertySpec.setType("Datacenter");

			// Now create Object Spec
			ObjectSpec objectSpec = new ObjectSpec();
			objectSpec.setObj(rootRef);
			objectSpec.setSkip(Boolean.TRUE);
			objectSpec.getSelectSet().add(tSpec);

			// Create PropertyFilterSpec using the PropertySpec and ObjectPec
			// created above.
			PropertyFilterSpec propertyFilterSpec = new PropertyFilterSpec();
			propertyFilterSpec.getPropSet().add(propertySpec);
			propertyFilterSpec.getObjectSet().add(objectSpec);

			List<PropertyFilterSpec> listpfs = new ArrayList<PropertyFilterSpec>(
					1);
			listpfs.add(propertyFilterSpec);
			List<ObjectContent> listobjcont = retrievePropertiesAllObjects(listpfs);

			if (listobjcont != null) {
				for (ObjectContent oc : listobjcont) {
					ManagedObjectReference mr = oc.getObj();
					String dcnm = null;
					List<DynamicProperty> dps = oc.getPropSet();
					if (dps != null) {
						// Since there is only one property PropertySpec pathset
						// this array contains only one value
						for (DynamicProperty dp : dps) {
							dcnm = (String) dp.getVal();
						}
					}
					// This is done outside of the previous for loop to break
					// out of the loop as soon as the required datacenter is
					// found.
					if (dcnm != null && dcnm.equals(datacenterName)) {
						retVal = mr;
						break;
					}
				}
			}
		} catch (SOAPFaultException sfe) {
			printSoapFaultException(sfe);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retVal;
	}
	/**
	 * 
	 * @return TraversalSpec specification to get to the Datacenter managed
	 *         object.
	 */
	private static TraversalSpec getDatacenterTraversalSpec() {
		// Create a traversal spec that starts from the 'root' objects
		SelectionSpec sSpec = new SelectionSpec();
		sSpec.setName("VisitFolders");

		// TraversalSpec to get to the DataCenter from rootFolder
		TraversalSpec traversalSpec = new TraversalSpec();
		traversalSpec.setName("VisitFolders");
		traversalSpec.setType("Folder");
		traversalSpec.setPath("childEntity");
		traversalSpec.setSkip(false);
		traversalSpec.getSelectSet().add(sSpec);
		return traversalSpec;
	}
	/**
	 * 添加主机到集群
	 * @return
	 * @throws Exception 
	 */
	public static boolean addHost2Cluster(String clustername,String hostIP,String username,String password) throws Exception{
		
		HostConnectSpec hostConnectSpec = new HostConnectSpec();
		hostConnectSpec.setHostName(hostIP);
		hostConnectSpec.setUserName(username);
		hostConnectSpec.setPassword(password);
		
		ManagedObjectReference dcmor;
		ManagedObjectReference hostfoldermor;

		String license = "C2:DB:7E:D6:8E:15:77:F8:7C:E7:0E:25:15:D2:47:74:1D:55:B4:A0";
		
		Map<String, ManagedObjectReference> list =getResourcePoolList();
		ManagedObjectReference a = list.get("Resources");
		Map<String, ManagedObjectReference> dcmormap = getClusterComputeResource(clustername);
		dcmor = dcmormap.get(clustername);
         try {
        	 if ((clustername != null) || ((clustername != null))) {
 				
 				if (dcmor == null) {
 					System.out.println("Datacenter not found");
 				}
 				hostfoldermor = getMorFromPropertyName(dcmor, "hostFolder");
 				
 				ManagedObjectReference addTask =vimPort.addHostTask(hostfoldermor, hostConnectSpec, true, a, license);
 				if(getTaskResultAfterDone(addTask)){
 					System.out.printf(
							"Successfully add Host [%s] to Cluster name [%s] %n",
							hostIP, clustername);	
 				}
 			}
		}catch(DuplicateNameFaultMsg msg){
			msg.printStackTrace();
			return false;
		}
		return true;
	}
	/**
	 * 添加主机到数据中心
	 * datacenter
	 * hostIP
	 * username
	 * password
	 * license
	 * force
	 * @return
	 * @throws Exception 
	 */
	public static boolean addHost2Datacenter(String datacenter,String hostIP,String username,String password,String license,boolean force) throws Exception{
//		String license = "C2:DB:7E:D6:8E:15:77:F8:7C:E7:0E:25:15:D2:47:74:1D:55:B4:A0";
		
		HostConnectSpec hostConnectSpec = new HostConnectSpec();
		hostConnectSpec.setHostName(hostIP);
		hostConnectSpec.setUserName(username);
		hostConnectSpec.setPassword(password);
		hostConnectSpec.setSslThumbprint(license);
		hostConnectSpec.setForce(force);
		
		ManagedObjectReference dcmor;
		ManagedObjectReference hostfoldermor;
		Map<String, ManagedObjectReference> list =getResourcePoolList();
		list.get("Resources");
		
		ManagedObjectReference addTask = null;
         try {
        	 if ((datacenter != null) || ((datacenter != null))) {
 				dcmor = getDatacenterByName(datacenter);
 				if (dcmor == null) {
 					System.out.println("Datacenter not found");
 				}
 				hostfoldermor = getMorFromPropertyName(dcmor, "hostFolder");
 				addTask =vimPort.addStandaloneHostTask(hostfoldermor, hostConnectSpec, null, true, null);
 				if(getTaskResultAfterDone(addTask)){
 					System.out.printf(
							"Successfully add Host [%s] to Datacenter name [%s] %n",
							hostIP, datacenter);	
 				}
 			}
		}catch(DuplicateNameFaultMsg msg){
			msg.printStackTrace();
			return false;
		}
		return true;
	}
	/**
	 * 获取集群
	 * @param cluster
	 * @return
	 * @throws Exception
	 */
	public static Map<String, ManagedObjectReference> getClusterComputeResource(String cluster)
			throws Exception {
		Map<String, ManagedObjectReference> vmList = new HashMap<String, ManagedObjectReference>();

		// Start from the root folder
		ManagedObjectReference container = serviceContent.getRootFolder();

		Map<String, ManagedObjectReference> clusters = getMOREFsInContainerByType(
				container, "ClusterComputeResource");

		if (cluster != null) {
			if (clusters.containsKey(cluster)) {
				vmList.put(cluster, clusters.get(cluster));
			}
			return vmList;
		}

		// If no filters are there then just the container based containment is
		// used.
		vmList = clusters;

		return vmList;
	}
	/**
	 * 新建cluster
	 * @param datacenter
	 * @param clusterName
	 * @return
	 * @throws Exception 
	 */
	public static boolean creatCluster(String datacenter,String clusterName) throws Exception{
		try {
			 ClusterDrsConfigInfo clusterDrsConfigInfo = new ClusterDrsConfigInfo();
			 clusterDrsConfigInfo.setEnabled(true);//是否启用该服务
			 
			 ClusterConfigSpec clusterSpec = new ClusterConfigSpec();
			 clusterSpec.setDrsConfig(clusterDrsConfigInfo);
			
			ManagedObjectReference dcmor;
			ManagedObjectReference hostfoldermor;

			
	         try {
	        	 if ((datacenter != null) || ((datacenter != null))) {
	 				dcmor = getDatacenterByName(datacenter);
	 				if (dcmor == null) {
	 					System.out.println("Datacenter not found");
	 				}
	 				hostfoldermor = getMorFromPropertyName(dcmor, "hostFolder");
	 				
	 				ManagedObjectReference createTask = vimPort.createCluster(hostfoldermor, clusterName, clusterSpec);
	 				if(getTaskResultAfterDone(createTask)){
	 					System.out.printf(
								"Successfully create Cluster [%s] into Datacenter name [%s] %n",
								clusterName, datacenter);	
	 				}
	 			}
			} catch (DuplicateNameFaultMsg e) {
				e.printStackTrace();
				 return false;
			} catch (InvalidNameFaultMsg e) {
				e.printStackTrace();
				return false;
			}
		} catch (RuntimeFaultFaultMsg e) {
			e.printStackTrace();
			return false;
		}
		
         return true;
	}
	/**
	 * 删除cluster
	 * @param clusterName
	 * @return
	 * @throws Exception 
	 */
	public static boolean removeCluster(String clusterName) throws Exception{
		try {
			Map<String, ManagedObjectReference> clustermors = getClusterComputeResource(clusterName);
			if (clustermors!=null&&clustermors.get(clusterName)!=null) {
        		ManagedObjectReference mor =  clustermors.get(clusterName);
        		ManagedObjectReference deleteTask = vimPort.destroyTask(mor);
 				if(getTaskResultAfterDone(deleteTask)){
 					System.out.printf(
							"Successfully delete Cluster [%s]  %n",
							clusterName);	
 				}
 			}else{
 				System.out.println("Cluster is not found");
 				return false;
 			}
		} catch (RuntimeFaultFaultMsg e) {
			e.printStackTrace();
			return false;
		}
		
         return true;
	}
	
	/*-----------------------------------------------------------------数据中心结束---------------------------------------------------------------------------------------*/
	
	/*-----------------------------------------------------------------虚拟机开始---------------------------------------------------------------------------------------*/
	/**
	 * 获取集群中指定的虚拟机
	 * @param vmName
	 * @return
	 * @throws Exception
	 */
	public static Map<String, ManagedObjectReference> getVms(String vmName)
			throws Exception {
		Map<String, ManagedObjectReference> vmList = new HashMap<String, ManagedObjectReference>();

		// Start from the root folder
		ManagedObjectReference container = serviceContent.getRootFolder();

		Map<String, ManagedObjectReference> vms = getMOREFsInContainerByType(
				container, "VirtualMachine");

		if (vmName != null) {
			if (vms.containsKey(vmName)) {
				vmList.put(vmName, vms.get(vmName));
			}
			return vmList;
		}

		// If no filters are there then just the container based containment is
		// used.
		vmList = vms;

		return vmList;
	}

	/**
	 * 获取集群中所有虚拟机
	 * @return
	 * @throws Exception
	 */
	public static Map<String, ManagedObjectReference> getAllVms()
			throws Exception {

		// Start from the root folder
		ManagedObjectReference container = serviceContent.getRootFolder();

		Map<String, ManagedObjectReference> vms = getMOREFsInContainerByType(
				container, "VirtualMachine");

		return vms;
	}
	
	private static TraversalSpec getVMTraversalSpec() {
		// Create a traversal spec that starts from the 'root' objects
		// and traverses the inventory tree to get to the VirtualMachines.
		// Build the traversal specs bottoms up

		// Traversal to get to the VM in a VApp
		TraversalSpec vAppToVM = new TraversalSpec();
		vAppToVM.setName("vAppToVM");
		vAppToVM.setType("VirtualApp");
		vAppToVM.setPath("vm");
		// Traversal spec for VApp to VApp
		TraversalSpec vAppToVApp = new TraversalSpec();
		vAppToVApp.setName("vAppToVApp");
		vAppToVApp.setType("VirtualApp");
		vAppToVApp.setPath("resourcePool");
		// SelectionSpec for VApp to VApp recursion
		SelectionSpec vAppRecursion = new SelectionSpec();
		vAppRecursion.setName("vAppToVApp");
		// SelectionSpec to get to a VM in the VApp
		SelectionSpec vmInVApp = new SelectionSpec();
		vmInVApp.setName("vAppToVM");
		// SelectionSpec for both VApp to VApp and VApp to VM
		List<SelectionSpec> vAppToVMSS = new ArrayList<SelectionSpec>();
		vAppToVMSS.add(vAppRecursion);
		vAppToVMSS.add(vmInVApp);
		vAppToVApp.getSelectSet().addAll(vAppToVMSS);

		// This SelectionSpec is used for recursion for Folder recursion
		SelectionSpec sSpec = new SelectionSpec();
		sSpec.setName("VisitFolders");

		// Traversal to get to the vmFolder from DataCenter
		TraversalSpec dataCenterToVMFolder = new TraversalSpec();
		dataCenterToVMFolder.setName("DataCenterToVMFolder");
		dataCenterToVMFolder.setType("Datacenter");
		dataCenterToVMFolder.setPath("vmFolder");
		dataCenterToVMFolder.setSkip(false);
		dataCenterToVMFolder.getSelectSet().add(sSpec);

		// TraversalSpec to get to the DataCenter from rootFolder
		TraversalSpec traversalSpec = new TraversalSpec();
		traversalSpec.setName("VisitFolders");
		traversalSpec.setType("Folder");
		traversalSpec.setPath("childEntity");
		traversalSpec.setSkip(false);
		List<SelectionSpec> sSpecArr = new ArrayList<SelectionSpec>();
		sSpecArr.add(sSpec);
		sSpecArr.add(dataCenterToVMFolder);
		sSpecArr.add(vAppToVM);
		sSpecArr.add(vAppToVApp);
		traversalSpec.getSelectSet().addAll(sSpecArr);
		return traversalSpec;
	}
	// clone前的检查
	private static String checkBussPars(String clonename, String vmpath,
			String datacentername) {
		if (clonename == null || clonename.isEmpty()) {
			return "clone时,缺少必要参数:cloneName";
		}

		if (vmpath == null || vmpath.isEmpty()) {
			return "clone时,缺少必要参数:vmpath";
		}

		if (datacentername == null || datacentername.isEmpty()) {
			return "clone时,缺少必要参数:datacentername";
		}
		vmPathName = vmpath;
		dataCenterName = datacentername;

		return "";
	}

	// clone虚拟机
	public static String cloneVM(String clonename, String vmpath,
			String datacentername, String vMIp,String pool) throws Exception {
		// 参数检查
		String checkResult = checkBussPars(clonename, vmpath, datacentername);
		if (!checkResult.isEmpty()) {
			return checkResult;
		}

		// Find the Datacenter reference by using findByInventoryPath().
		ManagedObjectReference datacenterRef = vimPort.findByInventoryPath(
				serviceContent.getSearchIndex(), dataCenterName);
		if (datacenterRef == null) {
			System.out.printf("The specified datacenter [ %s ]is not found %n",
					dataCenterName);
			return "datacenter " + dataCenterName + " is not found";
		}
		// Find the virtual machine folder for this datacenter.
		ManagedObjectReference vmFolderRef = (ManagedObjectReference) getDynamicProperty(
				datacenterRef, "vmFolder");
		if (vmFolderRef == null) {
			System.out.println("The virtual machine is not found");
			return "The virtual machine is not found";
		}

		ManagedObjectReference vmRef = vimPort.findByInventoryPath(
				serviceContent.getSearchIndex(), vmPathName);

		if (vmRef == null) {
			System.out.printf("The VMPath specified [ %s ] is not found %n",
					vmPathName);
			return "VMPath " + vmPathName + " is not found";
		}
//		String hostHref = ConstantUtil.getPara("hostHref");
//		if(vMIp!=null&&!vMIp.isEmpty()&&vMIp!=""){
//			hostHref = ConstantUtil.getPara("hostHref")+"/"+vMIp;
//		}
//		ManagedObjectReference hostRef = vimPort.findByInventoryPath(
//				serviceContent.getSearchIndex(), hostHref);
//		ManagedObjectReference poolRef = vimPort.findByInventoryPath(
//				serviceContent.getSearchIndex(),  ConstantUtil.getPara("poolRef"));
		ManagedObjectReference poolRef = vimPort.findByInventoryPath(
				serviceContent.getSearchIndex(),ConstantUtil.getPara("InventoryPath"));;
		if(pool!=null&&!pool.isEmpty()){
			poolRef = vimPort.findByInventoryPath(
					serviceContent.getSearchIndex(),ConstantUtil.getPara("InventoryPath")+"/"+pool);
		}
		VirtualMachineCloneSpec cloneSpec = new VirtualMachineCloneSpec();
		VirtualMachineRelocateSpec relocSpec = new VirtualMachineRelocateSpec();
		relocSpec.setTransform(VirtualMachineRelocateTransformation.SPARSE);
		relocSpec.setPool(poolRef);
//		if(vMIp!=null&&!vMIp.isEmpty()&&vMIp!=""){
//			relocSpec.setHost(hostRef);
//		}
		cloneSpec.setLocation(relocSpec);
		cloneSpec.setPowerOn(false);
		cloneSpec.setTemplate(false);
		// cloneSpec.setCustomization(cusSpec);
		
		System.out.printf("Cloning Virtual Machine [%s] to clone name [%s] %n",
				vmPathName.substring(vmPathName.lastIndexOf("/") + 1),
				clonename);

		try {
			ManagedObjectReference cloneTask = vimPort.cloneVMTask(vmRef,
					vmFolderRef, clonename, cloneSpec);

			// vimPort.mountToolsInstaller(cloneTask);
			if (getTaskResultAfterDone(cloneTask)) {
				System.out
						.printf(
								"Successfully cloned Virtual Machine [%s] to clone name [%s] %n",
								vmPathName.substring(vmPathName
										.lastIndexOf("/") + 1), clonename);
			} else {
				System.out
						.printf(
								"Failure Cloning Virtual Machine [%s] to clone name [%s] %n",
								vmPathName.substring(vmPathName
										.lastIndexOf("/") + 1), clonename);
			}
		} catch (SOAPFaultException sfe) {
			printSoapFaultException(sfe);
			return sfe.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}

		return "";
	}

	/**
	 * 针对虚机的一系列操作
	 * 
	 * @param operation
	 *            poweron(开机),poweroff(断电),shutdown(关机),suspend(挂起),reboot(重启),reset
	 *            (重置)
	 * @param vmName
	 *            虚拟机的名称
	 * @throws Exception
	 */
	public static boolean runOperation(String operation, String vmName)
			throws Exception {
		Map<String, ManagedObjectReference> vmMap = getVms(vmName);
		boolean b = false;
		if (vmMap == null || vmMap.isEmpty()) {
			System.out.println("No Virtual Machine found matching "
					+ "the specified criteria");
			return b;
		} else {
			if (operation.equalsIgnoreCase("poweron")) {
				b = powerOnVM(vmMap);
			} else if (operation.equalsIgnoreCase("poweroff")) {
				b = powerOffVM(vmMap);
			} else if (operation.equalsIgnoreCase("reset")) {
				b = resetVM(vmMap);
			} else if (operation.equalsIgnoreCase("shutdown")) {
				b = shutdownVM(vmMap);
			} else if (operation.equalsIgnoreCase("suspend")) {
				b = suspendVM(vmMap);
			} else if (operation.equalsIgnoreCase("reboot")) {
				b = rebootVM(vmMap);
			} else if (operation.equalsIgnoreCase("reset")) {
				b = resetVM(vmMap);
			} else if (operation.equalsIgnoreCase("standby")) {
				b = standbyVM(vmMap);
			}
		}
		return b;
	}

	/**
	 * 开机
	 * 
	 * @param vmMap
	 * 20140626
	 * mayh
	 * 修改使能抛出异常
	 * @throws VmConfigFaultFaultMsg 
	 * @throws TaskInProgressFaultMsg 
	 * @throws RuntimeFaultFaultMsg 
	 * @throws InvalidStateFaultMsg 
	 * @throws InsufficientResourcesFaultFaultMsg 
	 * @throws FileFaultFaultMsg 
	 * @throws InvalidCollectorVersionFaultMsg 
	 * @throws InvalidPropertyFaultMsg 
	 */
	private static boolean powerOnVM(Map<String, ManagedObjectReference> vmMap) {
		boolean b = false;
		for (String vmname : vmMap.keySet()) {
			ManagedObjectReference vmMor = vmMap.get(vmname);
//			try {
//				System.out.println("Powering on virtual machine : " + vmname
//						+ "[" + vmMor.getValue() + "]");
//				ManagedObjectReference taskmor = vimPort.powerOnVMTask(vmMor,
//						null);
//
//				if (getTaskResultAfterDone(taskmor)) {
//					System.out.println(vmname + "[" + vmMor.getValue()
//							+ "] powered on successfully");
//				}
//			} catch (Exception e) {
//				System.out.println("Unable to poweron vm : " + vmname + "["
//						+ vmMor.getValue() + "]");
//				System.err.println("Reason :" + e.getLocalizedMessage());
//			}
			System.out.println("Powering on virtual machine : " + vmname
					+ "[" + vmMor.getValue() + "]");
			ManagedObjectReference taskmor;
			try {
				taskmor = vimPort.powerOnVMTask(vmMor,
						null);
				if (getTaskResultAfterDone(taskmor)) {
					System.out.println(vmname + "[" + vmMor.getValue()
							+ "] powered on successfully");
				}
				b= true;
				String propertyStr = ""  ;
				while(propertyStr.contains("POWERON")){
					try {
						propertyStr = VMWareUtil.callWaitForUpdatesEx("60", "1",vmname);
					} catch (Exception e) {
						e.printStackTrace();
						b= false;
					}
				}
			} catch (FileFaultFaultMsg e) {
				b = false;
				e.printStackTrace();
			} catch (InsufficientResourcesFaultFaultMsg e) {
				b = false;
				e.printStackTrace();
			} catch (InvalidStateFaultMsg e) {
				b = false;
				e.printStackTrace();
			} catch (RuntimeFaultFaultMsg e) {
				b = false;
				e.printStackTrace();
			} catch (TaskInProgressFaultMsg e) {
				b = false;
				e.printStackTrace();
			} catch (VmConfigFaultFaultMsg e) {
				b = false;
				e.printStackTrace();
			} catch (InvalidPropertyFaultMsg e) {
				b = false;
				e.printStackTrace();
			} catch (InvalidCollectorVersionFaultMsg e) {
				b = false;
				e.printStackTrace();
			}
		}
		return b;
	}

	/**
	 * 直接切断虚机电源
	 * 
	 * @param vmMap
	 * 20140626
	 * mayh
	 * 修改使能抛出异常
	 * @throws TaskInProgressFaultMsg 
	 * @throws RuntimeFaultFaultMsg 
	 * @throws InvalidStateFaultMsg 
	 * @throws InvalidCollectorVersionFaultMsg 
	 * @throws InvalidPropertyFaultMsg 
	 */
	private static boolean powerOffVM(Map<String, ManagedObjectReference> vmMap)  {
		boolean b = false;
		for (String vmname : vmMap.keySet()) {
			ManagedObjectReference vmMor = vmMap.get(vmname);
//			try {
//				System.out.println("Powering off virtual machine : " + vmname
//						+ "[" + vmMor.getValue() + "]");
//				ManagedObjectReference taskmor = vimPort.powerOffVMTask(vmMor);
//				if (getTaskResultAfterDone(taskmor)) {
//					System.out.println(vmname + "[" + vmMor.getValue()
//							+ "] powered off successfully");
//				}
//			} catch (Exception e) {
//				System.out.println("Unable to poweroff vm : " + vmname + "["
//						+ vmMor.getValue() + "]");
//				System.err.println("Reason :" + e.getLocalizedMessage());
//			}
			System.out.println("Powering off virtual machine : " + vmname
					+ "[" + vmMor.getValue() + "]");
			ManagedObjectReference taskmor;
			try {
				taskmor = vimPort.powerOffVMTask(vmMor);
				if (getTaskResultAfterDone(taskmor)) {
					System.out.println(vmname + "[" + vmMor.getValue()
							+ "] powered off successfully");
				}
				b = true;
				String propertyStr = ""  ;
				while(propertyStr.contains("POWEROFF")){
					try {
						propertyStr = VMWareUtil.callWaitForUpdatesEx("60", "1",vmname);
					} catch (Exception e) {
						e.printStackTrace();
						b= false;
					}
				}
			} catch (InvalidStateFaultMsg e) {
				b = false;
				e.printStackTrace();
			} catch (RuntimeFaultFaultMsg e) {
				b = false;
				e.printStackTrace();
			} catch (TaskInProgressFaultMsg e) {
				b = false;
				e.printStackTrace();
			} catch (InvalidPropertyFaultMsg e) {
				b = false;
				e.printStackTrace();
			} catch (InvalidCollectorVersionFaultMsg e) {
				b = false;
				e.printStackTrace();
			}
			
		}
		return b;
	}

	/**
	 * 重置虚机
	 * 
	 * @param vmMap
	 * 20140626
	 * mayh
	 * 修改使能抛出异常
	 * @return 
	 * @throws TaskInProgressFaultMsg 
	 * @throws RuntimeFaultFaultMsg 
	 * @throws InvalidStateFaultMsg 
	 * @throws InvalidCollectorVersionFaultMsg 
	 * @throws InvalidPropertyFaultMsg 
	 */
	private static boolean resetVM(Map<String, ManagedObjectReference> vmMap) {
		boolean b = false;
		for (String vmname : vmMap.keySet()) {
			ManagedObjectReference vmMor = vmMap.get(vmname);
//			try {
//				System.out.println("Reseting virtual machine : " + vmname + "["
//						+ vmMor.getValue() + "]");
//				ManagedObjectReference taskmor = vimPort.resetVMTask(vmMor);
//				if (getTaskResultAfterDone(taskmor)) {
//					System.out.println(vmname + "[" + vmMor.getValue()
//							+ "] reset successfully");
//				}
//			} catch (Exception e) {
//				System.out.println("Unable to reset vm : " + vmname + "["
//						+ vmMor.getValue() + "]");
//				System.err.println("Reason :" + e.getLocalizedMessage());
//			}
			System.out.println("Reseting virtual machine : " + vmname + "["
					+ vmMor.getValue() + "]");
			ManagedObjectReference taskmor;
			try {
				taskmor = vimPort.resetVMTask(vmMor);
				if (getTaskResultAfterDone(taskmor)) {
					System.out.println(vmname + "[" + vmMor.getValue()
							+ "] reset successfully");
				}
				b = true;
			} catch (InvalidStateFaultMsg e) {
				b = false;
				e.printStackTrace();
			} catch (RuntimeFaultFaultMsg e) {
				b = false;
				e.printStackTrace();
			} catch (TaskInProgressFaultMsg e) {
				b = false;
				e.printStackTrace();
			} catch (InvalidPropertyFaultMsg e) {
				b = false;
				e.printStackTrace();
			} catch (InvalidCollectorVersionFaultMsg e) {
				b = false;
				e.printStackTrace();
			}
			
		}
		return b;
	}

	/**
	 * 关机
	 * 
	 * @param vmMap
	 * @return 
	 * @throws Exception
	 * * 20140626
	 * mayh
	 * 修改使能抛出异常
	 */
	private static boolean shutdownVM(Map<String, ManagedObjectReference> vmMap) {
		boolean b = false;
		for (String vmname : vmMap.keySet()) {
			ManagedObjectReference vmMor = vmMap.get(vmname);
//			try {
//				System.out.println("shutdown virtual machine : " + vmname + "["
//						+ vmMor.getValue() + "]");
//				vimPort.shutdownGuest(vmMor);
//				System.out.println("Guest os in vm '" + vmname + "' shutdown");
//			} catch (Exception e) {
//				System.out.println("Unable to shutdown vm : " + vmname + "["
//						+ vmMor.getValue() + "]");
//				System.err.println("Reason :" + e.getLocalizedMessage());
//			}
			System.out.println("shutdown virtual machine : " + vmname + "["
					+ vmMor.getValue() + "]");
			try {
				vimPort.shutdownGuest(vmMor);
				b = true;
			} catch (InvalidStateFaultMsg e) {
				b = false;
				e.printStackTrace();
			} catch (RuntimeFaultFaultMsg e) {
				b = false;
				e.printStackTrace();
			} catch (TaskInProgressFaultMsg e) {
				b = false;
				e.printStackTrace();
			} catch (ToolsUnavailableFaultMsg e) {
				b = false;
				e.printStackTrace();
			}
			System.out.println("Guest os in vm '" + vmname + "' shutdown");
		}
		return b;
	}

	/**
	 * 虚机挂起
	 * 
	 * @param vmMap
	 * @return 
	 * @throws Exception
	 * * 20140626
	 * mayh
	 * 修改使能抛出异常
	 */
	private static boolean suspendVM(Map<String, ManagedObjectReference> vmMap){
		boolean b = false;
		for (String vmname : vmMap.keySet()) {
			ManagedObjectReference vmMor = vmMap.get(vmname);
//			try {
//				System.out.println("suspending virtual machine : " + vmname
//						+ "[" + vmMor.getValue() + "]");
//				ManagedObjectReference taskmor = vimPort.suspendVMTask(vmMor);
//				if (getTaskResultAfterDone(taskmor)) {
//					System.out.println(vmname + "[" + vmMor.getValue()
//							+ "] reset successfully");
//				}
//			} catch (Exception e) {
//				System.out.println("Unable to reset vm : " + vmname + "["
//						+ vmMor.getValue() + "]");
//				System.err.println("Reason :" + e.getLocalizedMessage());
//			}
			System.out.println("suspending virtual machine : " + vmname
					+ "[" + vmMor.getValue() + "]");
			ManagedObjectReference taskmor;
			try {
				taskmor = vimPort.suspendVMTask(vmMor);
				if (getTaskResultAfterDone(taskmor)) {
					System.out.println(vmname + "[" + vmMor.getValue()
							+ "] reset successfully");
				}
				b = true;
			} catch (InvalidStateFaultMsg e) {
				b = false;
				e.printStackTrace();
			} catch (RuntimeFaultFaultMsg e) {
				b = false;
				e.printStackTrace();
			} catch (TaskInProgressFaultMsg e) {
				b = false;
				e.printStackTrace();
			} catch (InvalidPropertyFaultMsg e) {
				b = false;
				e.printStackTrace();
			} catch (InvalidCollectorVersionFaultMsg e) {
				b = false;
				e.printStackTrace();
			}
			
		}
		return b;
	}

	/**
	 * 虚机重启
	 * 
	 * @param vmMap
	 * @return 
	 * @throws Exception
	 * 20140626
	 * mayh
	 * 修改使能抛出异常
	 */
	public static boolean rebootVM(Map<String, ManagedObjectReference> vmMap) {
		boolean b = false;
		for (String vmname : vmMap.keySet()) {
			ManagedObjectReference vmMor = vmMap.get(vmname);
//			try {
//				System.out.println("Rebooting virtualmachine '" + vmname + "["
//						+ vmMor.getValue() + "]");
//				vimPort.rebootGuest(vmMor);
//				System.out.println("Guest os in vm '" + vmname + "' rebooted");
//			} catch (Exception e) {
//				System.out.println("Unable to reboot vm : " + vmname + "["
//						+ vmMor.getValue() + "]");
//				System.err.println("Reason :" + e.getLocalizedMessage());
//			}
			System.out.println("Rebooting virtualmachine '" + vmname + "["
					+ vmMor.getValue() + "]");
			try {
				vimPort.rebootGuest(vmMor);
				b = true;
			} catch (InvalidStateFaultMsg e) {
				b = false;
				e.printStackTrace();
			} catch (RuntimeFaultFaultMsg e) {
				b = false;
				e.printStackTrace();
			} catch (TaskInProgressFaultMsg e) {
				b = false;
				e.printStackTrace();
			} catch (ToolsUnavailableFaultMsg e) {
				b = false;
				e.printStackTrace();
			}
			System.out.println("Guest os in vm '" + vmname + "' rebooted");
		}
		return b;
	}
	/**
	 * 待机
	 * @param vmMap
	 * @return 
	 * @throws Exception
	 */
	public static boolean standbyVM(Map<String, ManagedObjectReference> vmMap){
		boolean b = false;
		for (String vmname : vmMap.keySet()) {
			ManagedObjectReference vmMor = vmMap.get(vmname);
			try {
				System.out.println("Putting the guestOs of vm '" + vmname
						+ "' in standby mode");
				vimPort.standbyGuest(vmMor);
				System.out.println("GuestOs of vm '" + vmname
						+ "' put in standby mode");
				b = true;
			} catch (Exception e) {
				System.out.println("Unable to reboot vm : " + vmname + "["
						+ vmMor.getValue() + "]");
				System.err.println("Reason :" + e.getLocalizedMessage());
				b = false;
			}
		}
		return b;
	}

	/**
	 * 创建快照
	 * 
	 * @param vmMap
	 * @param snapshotname
	 *            快照名称
	 * @param description
	 *            描述
	 * @return
	 * @throws Exception
	 */
	public static boolean createVMSnapshot(String vmName, String snapshotname,
			String description, boolean ismemory, boolean isquiesce)
			throws Exception {

		String desc = description;
		Map<String, ManagedObjectReference> vmMap = getVms(vmName);
		if (vmMap == null || vmMap.isEmpty()) {
			System.out.println("No Virtual Machine found matching "
					+ "the specified criteria");
			return false;
		} else {
			for (String vmname : vmMap.keySet()) {
				ManagedObjectReference vmMor = vmMap.get(vmname);
				ManagedObjectReference taskMor = vimPort.createSnapshotTask(
						vmMor, snapshotname, desc, ismemory, isquiesce);
				if (taskMor != null) {
					String[] opts = new String[] { "info.state", "info.error",
							"info.progress" };
					String[] opt = new String[] { "state" };
					Object[] results = waitForValues(
							taskMor,
							opts,
							opt,
							new Object[][] { new Object[] {
									TaskInfoState.SUCCESS, TaskInfoState.ERROR } });

					// Wait till the task completes.
					if (results[0].equals(TaskInfoState.SUCCESS)) {
						System.out.printf(
								" Creating Snapshot - [ %s ] Successful %n",
								snapshotname);
						return Boolean.TRUE;
					} else {
						System.out.printf(
								" Creating Snapshot - [ %s ] Failure %n",
								snapshotname);
						return Boolean.FALSE;
					}
				}
			}
		}
		return false;
	}

	public static String listSnapshot(String vmName) throws Exception {
		StringBuilder fileContent = new StringBuilder();
		Map<String, ManagedObjectReference> vmMap = getVms(vmName);
		if (vmMap == null || vmMap.isEmpty()) {
			System.out.println("No Virtual Machine found matching "
					+ "the specified criteria");
			return fileContent.toString();
		} else {
			for (String vmname : vmMap.keySet()) {
				ManagedObjectReference vmMor = vmMap.get(vmname);
				List<ObjectContent> snaps = getSnapShotVMMor(vmMor);
				VirtualMachineSnapshotInfo snapInfo = null;
				if (snaps != null && snaps.size() > 0) {
					ObjectContent snapobj = snaps.get(0);
					List<DynamicProperty> listdp = snapobj.getPropSet();
					if (listdp != null) {
						if (!listdp.isEmpty()) {
							snapInfo = ((VirtualMachineSnapshotInfo) (listdp
									.get(0)).getVal());
						}
						if (snapInfo == null) {
							System.out.println("No Snapshots found");
						} else {
							List<VirtualMachineSnapshotTree> listvmsht = snapInfo
									.getRootSnapshotList();
							traverseSnapshotInTree(listvmsht, null, true, 1,
									fileContent);
						}
					} else {
						System.out.println("No Snapshots found");
					}
				}
			}
		}
		if (fileContent != null && !"".equals(fileContent)) {
			return fileContent.toString();
		}
		return null;
	}

	public static List<ObjectContent> getSnapShotVMMor(
			ManagedObjectReference vmmor) {
		List<ObjectContent> retVal = null;
		try {
			// Create Property Spec
			PropertySpec propertySpec = new PropertySpec();
			propertySpec.setAll(Boolean.FALSE);
			propertySpec.getPathSet().add("snapshot");
			propertySpec.setType("VirtualMachine");

			// Now create Object Spec
			ObjectSpec objectSpec = new ObjectSpec();
			objectSpec.setObj(vmmor);
			// Create PropertyFilterSpec using the PropertySpec and ObjectPec
			// created above.
			PropertyFilterSpec propertyFilterSpec = new PropertyFilterSpec();
			propertyFilterSpec.getPropSet().add(propertySpec);
			propertyFilterSpec.getObjectSet().add(objectSpec);

			List<PropertyFilterSpec> listpfs = new ArrayList<PropertyFilterSpec>(
					1);
			listpfs.add(propertyFilterSpec);
			List<ObjectContent> listobjcont = retrievePropertiesAllObjects(listpfs);
			retVal = listobjcont;
		} catch (SOAPFaultException sfe) {
			printSoapFaultException(sfe);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retVal;
	}		
	/**
	 * Get the MOR of the Virtual Machine by its name.
	 * 根据虚拟机名称获取虚拟机
	 * @param vmName
	 *            The name of the Virtual Machine
	 * @return The Managed Object reference for this VM
	 */
	public static ManagedObjectReference getVmByVMname(String vmname) {
		ManagedObjectReference retVal = null;
		try {
			TraversalSpec tSpec = getVMTraversalSpec();
			// Create Property Spec
			PropertySpec propertySpec = new PropertySpec();
			propertySpec.setAll(Boolean.FALSE);
			propertySpec.getPathSet().add("name");
			propertySpec.setType("VirtualMachine");

			// Now create Object Spec
			ObjectSpec objectSpec = new ObjectSpec();
			objectSpec.setObj(rootRef);
			objectSpec.setSkip(Boolean.TRUE);
			objectSpec.getSelectSet().add(tSpec);

			// Create PropertyFilterSpec using the PropertySpec and ObjectPec
			// created above.
			PropertyFilterSpec propertyFilterSpec = new PropertyFilterSpec();
			propertyFilterSpec.getPropSet().add(propertySpec);
			propertyFilterSpec.getObjectSet().add(objectSpec);

			List<PropertyFilterSpec> listpfs = new ArrayList<PropertyFilterSpec>(
					1);
			listpfs.add(propertyFilterSpec);
			List<ObjectContent> listobcont = retrievePropertiesAllObjects(listpfs);

			if (listobcont != null) {
				for (ObjectContent oc : listobcont) {
					ManagedObjectReference mr = oc.getObj();
					String vmnm = null;
					List<DynamicProperty> dps = oc.getPropSet();
					if (dps != null) {
						for (DynamicProperty dp : dps) {
							vmnm = (String) dp.getVal();
						}
					}
					if (vmnm != null && vmnm.equals(vmname)) {
						retVal = mr;
						break;
					}
				}
			}
		} catch (SOAPFaultException sfe) {
			printSoapFaultException(sfe);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retVal;
	}
	//所有虚拟机及其硬件配置
		public static List<Map<String,Object>> vmHardwareinfo(){
			List<Map<String,Object>> data0 = new ArrayList<Map<String,Object>>();
			try {
				Map<String, ManagedObjectReference>  vmlist = VMWareUtil.getAllVms();
				String vmuuids="";
				for (Entry<String, ManagedObjectReference> entry : vmlist.entrySet()) {
					ManagedObjectReference vm = entry.getValue();
				
					Map<String,Object> vmi = new HashMap<String, Object>();
					VirtualMachineConfigInfo vminfo = new VirtualMachineConfigInfo();
					try
					{
						vminfo = (VirtualMachineConfigInfo) VMWareUtil.getDynamicProperty(vm, "config");
					}
					catch(Exception e)
					{
						continue;
					}
					if(vminfo.isTemplate())
						continue;
					else
					{
						vmuuids =  vminfo.getUuid();//id
						vminfo.getHardware().getDevice();//硬盘
						int mem =  vminfo.getHardware().getMemoryMB();//
						int cpu = vminfo.getHardware().getNumCPU();//cpu
						vminfo.getHardware().getNumCoresPerSocket();
						int vmdisck = 0;
						String vmname = vminfo.getName();//VM名称
						vminfo.getDatastoreUrl().get(0).getName();
						vminfo.getDatastoreUrl().get(0).getUrl();
						String mac = null;
						List<VirtualDevice> devices= vminfo.getHardware().getDevice();
						//虚拟机所在vlan的uuid
						for(VirtualDevice device:devices){
							if(device instanceof VirtualIDEController){//IDE0
							}
							if(device instanceof VirtualIDEController){//IDE1
							}
							if(device instanceof VirtualPS2Controller){//PS2控制器
							}
							if(device instanceof VirtualPCIController){//PCI控制器
							}
							if(device instanceof VirtualSIOController){//SIO控制器
							}
							if(device instanceof VirtualKeyboard){//键盘
							}
							if(device instanceof VirtualPointingDevice){//定点设备
							}
							if(device instanceof VirtualMachineVideoCard){//显卡
							}
							if(device instanceof VirtualMachineVMCIDevice){//VMCI设备
							}
							if(device instanceof VirtualLsiLogicSASController){//SCSI控制器
							}
							if(device instanceof VirtualCdrom){//CD/DVD驱动器
							}
							if(device instanceof VirtualDisk){//硬盘
								VirtualDisk disk = (VirtualDisk) device;
								//获取硬盘大小(单位KB)
								String disksize = disk.getDeviceInfo().getSummary();

								if (disksize != null) {

									String s1 = disksize.substring(0, disksize.length() - 3);
									
									//将单位为KB转换为GB
									vmdisck = Integer.parseInt(s1.replace(",", ""))/ (1024 * 1024);

								}

							}
							if(device instanceof VirtualFloppy){//软驱
							}
							if(device instanceof VirtualEthernetCard){//适配器类型
								VirtualEthernetCard e1000= (VirtualEthernetCard) device;
								mac = e1000.getMacAddress();
							}
							
						}
						vmi.put("vmname", vmname);
						vmi.put("vmmemry", mem);
						vmi.put("vmcpu", cpu);
						vmi.put("vmdisck", vmdisck);
						vmi.put("mac", mac);
						vmi.put("vmuuids", vmuuids);
						data0.add(vmi);
					}
					
				}
				

//				for(String name:list){
//					for(Map<String,Object> apply : data){
//			      		VirtualMachineConfigInfo vmri = (VirtualMachineConfigInfo)apply.get("vminfo");
//			      		if(name.equals(vmri.getName())){
//							data0.add(apply);
//			      		}
//					}
//				}
//				vmuuids =vmuuids.substring(0,vmuuids.length()-1);
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
			}
			return data0;
		}
		/*
		 * 设置虚机网络适配器
		 * 参数为操作类型（Add新增、Remove移除、Edit编辑），虚机对象，设备名称，新的网络连接标签，Mac地址类型（手动，自动），Mac地址
		 */
		private static VirtualDeviceConfigSpec getNICDeviceConfigSpec(String ops,
				ManagedObjectReference virtualMachine, String deviceName,
				String newNetTag, String AddressType, String MacAddress,List<HostProxySwitch> hostProxySwitchs)
				throws Exception {
			VirtualDeviceConfigSpec nicSpec = new VirtualDeviceConfigSpec();
			if (ops.equalsIgnoreCase("Add")) {
				nicSpec.setOperation(VirtualDeviceConfigSpecOperation.ADD);
				VirtualEthernetCard nic = new VirtualPCNet32();
				//标准网络适配器
				VirtualEthernetCardNetworkBackingInfo nicBacking = new VirtualEthernetCardNetworkBackingInfo();
				//分布式网络适配器
				VirtualEthernetCardDistributedVirtualPortBackingInfo distributedVirtualPortBackingInfo 
						= new VirtualEthernetCardDistributedVirtualPortBackingInfo();
				DistributedVirtualSwitchPortConnection portConnection = new DistributedVirtualSwitchPortConnection();
				portConnection.setPortgroupKey(newNetTag);
				
				if(hostProxySwitchs.size()>0){
					
					portConnection.setSwitchUuid(hostProxySwitchs.get(0).getDvsUuid());

				}
				distributedVirtualPortBackingInfo.setPort(portConnection);
				
				nicBacking.setDeviceName(newNetTag);
				VirtualDeviceConnectInfo vci = new VirtualDeviceConnectInfo();
				vci.setStartConnected(true);
				vci.setConnected(true);
				vci.setAllowGuestControl(true);
				nic.setConnectable(vci);
				if (AddressType != null)
					nic.setAddressType(AddressType);
				if (MacAddress != null)
					nic.setMacAddress(MacAddress);

				nic.setWakeOnLanEnabled(true);
				nic.setBacking(distributedVirtualPortBackingInfo);
				nicSpec.setDevice(nic);
			} else if (ops.equalsIgnoreCase("Remove")) {
				VirtualEthernetCard nic = null;
				nicSpec.setOperation(VirtualDeviceConfigSpecOperation.REMOVE);
				List<VirtualDevice> listvd = ((ArrayOfVirtualDevice) getEntityProps(
						virtualMachine, new String[] { "config.hardware.device" })
						.get("config.hardware.device")).getVirtualDevice();
				for (VirtualDevice device : listvd) {
					if (device instanceof VirtualEthernetCard) {
						if (deviceName.equalsIgnoreCase(device.getDeviceInfo()
								.getLabel())) {
							nic = (VirtualEthernetCard) device;
							break;
						}
					}
				}
				if (nic != null) {
					nicSpec.setDevice(nic);
				} else {
					return null;
				}
			} else if (ops.equalsIgnoreCase("Edit")) {
				VirtualEthernetCard nic = null;
				nicSpec.setOperation(VirtualDeviceConfigSpecOperation.EDIT);
				List<VirtualDevice> listvd = ((ArrayOfVirtualDevice) getEntityProps(
						virtualMachine, new String[] { "config.hardware.device" })
						.get("config.hardware.device")).getVirtualDevice();
				for (VirtualDevice device : listvd) {
					if (device instanceof VirtualEthernetCard) {
						if (deviceName.equalsIgnoreCase(device.getDeviceInfo()
								.getLabel())) {
							nic = (VirtualEthernetCard) device;
							VirtualEthernetCardNetworkBackingInfo nicBacking = (VirtualEthernetCardNetworkBackingInfo) nic
									.getBacking();
							nicBacking.setDeviceName(newNetTag);

							VirtualDeviceConnectInfo vci = nic.getConnectable();
							vci.setStartConnected(true);
							vci.setConnected(true);
							nic.setConnectable(vci);
							if (AddressType != null)
								nic.setAddressType(AddressType);
							if (MacAddress != null)
								nic.setMacAddress(MacAddress);
							nic.setWakeOnLanEnabled(true);
							nic.setBacking(nicBacking);
							break;
						}
					}
				}
				if (nic != null) {
					nicSpec.setDevice(nic);
				} else {
					return null;
				}
			}
			return nicSpec;
		}

		/*
		 * 获取虚机网络适配器 参数为虚机对象
		 */
		public static List<VirtualDevice> getVMNICDeviceList(
				ManagedObjectReference virtualMachine) throws Exception {
			List<VirtualDevice> data = new ArrayList<VirtualDevice>();
			List<VirtualDevice> listvd = ((ArrayOfVirtualDevice) getEntityProps(
					virtualMachine, new String[] { "config.hardware.device" }).get(
					"config.hardware.device")).getVirtualDevice();
			for (VirtualDevice device : listvd) {
				if (device instanceof VirtualEthernetCard) {
					data.add(device);
				}
			}

			return data;
		}
		/*
		 * 更新虚拟交换机物理适配器 参数为主机名称，虚拟交换机名称，新适配器名称，新增/移除(Add,Remove)
		 */
		public static void updateVirtualSwitch(String host, String vsnmae,
				String pnicName, String ops) throws InvalidPropertyFaultMsg,
				RuntimeFaultFaultMsg {
			Map<String, ManagedObjectReference> hostList = getMOREFsInFolderByType(
					serviceContent.getRootFolder(), "HostSystem");
			ManagedObjectReference hostmor = hostList.get(host);
			if (hostmor != null) {

				HostConfigManager configMgr = (HostConfigManager) getEntityProps(
						hostmor, new String[] { "configManager" }).get(
						"configManager");
				ManagedObjectReference nwSystem = configMgr.getNetworkSystem();

				HostNetworkInfo dd = new HostNetworkInfo();
				dd = (HostNetworkInfo) getEntityProps(nwSystem,
						new String[] { "networkInfo" }).get("networkInfo");
				List<HostVirtualSwitch> lv = dd.getVswitch();

				for (HostVirtualSwitch hvs : lv) {
					if (hvs.getName().equals(vsnmae)) {
						HostVirtualSwitchSpec spec = hvs.getSpec();

						HostVirtualSwitchBondBridge hsbb = (HostVirtualSwitchBondBridge) spec
								.getBridge();

						if (ops.equalsIgnoreCase("Add")) {
							hsbb.getNicDevice().add(pnicName);
						} else {
							hsbb.getNicDevice().remove(pnicName);
						}
						spec.setBridge(hsbb);

						try {
							vimPort.updateVirtualSwitch(nwSystem, vsnmae, spec);
						} catch (HostConfigFaultFaultMsg e) {
							System.out.println("Failed update switch: " + vsnmae
									+ " Reason : " + e.getMessage());
						} catch (NotFoundFaultMsg e) {
							System.out.println("Failed update switch: " + vsnmae
									+ " Reason : " + e.getMessage());
						} catch (ResourceInUseFaultMsg e) {
							System.out.println("Failed update switch: " + vsnmae
									+ " Reason : " + e.getMessage());
						}
					}
				}

			}
		}
		/*
		 * 获取主机下的虚拟机 参数为主机名称
		 */
		public static List<ManagedObjectReference> getHostVirtualMachine(String host)
				throws InvalidPropertyFaultMsg, RuntimeFaultFaultMsg {
			Map<String, ManagedObjectReference> hostList = getMOREFsInFolderByType(
					serviceContent.getRootFolder(), "HostSystem");
			ManagedObjectReference hostmor = hostList.get(host);

			ArrayOfManagedObjectReference vml = (ArrayOfManagedObjectReference) getEntityProps(
					hostmor, new String[] { "vm" }).get("vm");

			// ArrayOfManagedObjectReference vml2 = (ArrayOfManagedObjectReference)
			// getEntityProps(
			// serviceContent.getRootFolder(), new String[] { "template"
			// }).get("template");

			return vml.getManagedObjectReference();
		}
		
		
		public static VirtualMachineConfigInfo getVirtualMachineConfigInfoByVName(
				String vname) {
			try {
				PropertySpec propertySpec = new PropertySpec();
				propertySpec.setAll(Boolean.FALSE);
				propertySpec.getPathSet().add("name");
				propertySpec.getPathSet().add("config");
				propertySpec.setType("VirtualMachine");

				ObjectSpec objectSpec = new ObjectSpec();
				objectSpec.setObj(serviceContent.getRootFolder());
				objectSpec.setSkip(Boolean.TRUE);
				objectSpec.getSelectSet().add(getVMTraversalSpec());

				PropertyFilterSpec propertyFilterSpec = new PropertyFilterSpec();
				propertyFilterSpec.getPropSet().add(propertySpec);
				propertyFilterSpec.getObjectSet().add(objectSpec);
				List<PropertyFilterSpec> l = new ArrayList<PropertyFilterSpec>();
				l.add(propertyFilterSpec);

				List<ObjectContent> oCont = vimPort.retrieveProperties(
						serviceContent.getPropertyCollector(), l);
				HashMap<String, VirtualMachineConfigInfo> configMap = new HashMap<String, VirtualMachineConfigInfo>();
				VirtualMachineConfigInfo configInfo = null;

				if (oCont != null) {
					for (ObjectContent oc : oCont) {
						List<DynamicProperty> dps = oc.getPropSet();
						if (dps != null) {
							String name = null;
							VirtualMachineConfigInfo config = null;
							for (DynamicProperty dp : dps) {
								if (dp.getName().equalsIgnoreCase("name")) {
									name = (String) dp.getVal();
								} else if (dp.getName().equalsIgnoreCase("config")) {
									config = (VirtualMachineConfigInfo) dp.getVal();
								}
							}
							if ((name != null) && (config != null)) {
								configMap.put(name, config);
							}
						}
					}

					configInfo = configMap.get(vname);
				}
				return configInfo;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		// 删除虚机
		public static boolean deleteManagedEntity(String vname)
				throws RuntimeFaultFaultMsg, RemoteException, Exception {
			boolean b = false;
			Map<String, ManagedObjectReference> entities = getMOREFsInContainerByType(
					serviceContent.getRootFolder(), "ManagedEntity");

			ManagedObjectReference manobjref = entities.get(vname);

			if (manobjref == null) {
				System.out.printf(
						" Unable to find a Managed Entity By name [ %s ]", vname);
				return b;
			} else {
				ManagedObjectReference taskmor = vimPort.destroyTask(manobjref);
				if (getTaskResultAfterDone(taskmor)) {
					System.out.printf(
							"Successful delete of Managed Entity Name - [ %s ]"
									+ " and Entity Type - [ %s ]%n", vname,
							manobjref.getType());
					b = true;
				}
			}
			return b;
		}

		// 通过虚机得到虚机的实体
		public static ManagedObjectReference getVmByName(String vname) {
			ManagedObjectReference obj = null;
			try {
				obj = getMOREFsInContainerByType(serviceContent.getRootFolder(),
						"VirtualMachine").get(vname);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return obj;
		}

		//客户化虚机
		public static void customVM(String old_vmname) throws Exception{
			//SET IP ADDRESS
			
			CustomizationSpec cust_spec = new CustomizationSpec();
			CustomizationFixedIp fixedIP = new CustomizationFixedIp();
			fixedIP.setIpAddress("192.168.9.99");
			CustomizationIPSettings cust_IP_settings = new CustomizationIPSettings();
			cust_IP_settings.setIp(fixedIP);
			cust_IP_settings.setSubnetMask("255.255.255.0");

			CustomizationAdapterMapping cust_adapter_mapping = new CustomizationAdapterMapping();
			cust_adapter_mapping.setAdapter(cust_IP_settings);
			
			CustomizationGlobalIPSettings cgp=new CustomizationGlobalIPSettings();
			cust_spec.setGlobalIPSettings(cgp);
			
			CustomizationSysprep cp=new CustomizationSysprep();
			//CustomizationIdentitySettings cp = new CustomizationIdentitySettings();
			CustomizationIdentification ci=new CustomizationIdentification();
			//CustomizationPassword cps=new CustomizationPassword();
			//cps.setValue("123@abcd"); ci.setDomainAdmin("chenhy");
			//ci.setDomainAdminPassword(cps);
			
			CustomizationGuiUnattended cu=new CustomizationGuiUnattended();
			cu.setAutoLogon(true);
			cu.setAutoLogonCount(1); 
			cu.setTimeZone(190);
			
			CustomizationUserData cd=new CustomizationUserData();
			CustomizationFixedName cfn=new CustomizationFixedName();
			cfn.setName("chenhy-PC"); cd.setComputerName(cfn);
			cd.setFullName(cfn.getName()+".foxti.cn"); cd.setOrgName("foxti.cn");
			cd.setProductId("00426-OEM-8992662-00173");
			
			cp.setUserData(cd);
			cp.setGuiUnattended(cu);
			cp.setIdentification(ci);
			cust_spec.setIdentity(cp);
			
			cust_spec.getNicSettingMap().add(cust_adapter_mapping);
			
			ManagedObjectReference virtualMachine = getVmByName(old_vmname);
			
			ManagedObjectReference tmor = vimPort.customizeVMTask(virtualMachine,
					cust_spec);
			if (getTaskResultAfterDone(tmor)) {
				System.out.println("Virtual Machine reconfigured successfully");
			} else {
				System.out.println("Virtual Machine reconfigur failed");
			}
			
		}
		
		// 重置虚机
		public static void reConfig(ManagedObjectReference virtualMachine)
				throws Exception {
			if (virtualMachine != null) {
				VirtualMachineConfigSpec vmConfigSpec = new VirtualMachineConfigSpec();
				ManagedObjectReference tmor = vimPort.reconfigVMTask(
						virtualMachine, vmConfigSpec);
				if (getTaskResultAfterDone(tmor)) {
					System.out.println("Virtual Machine reconfigured successfully");
				} else {
					System.out.println("Virtual Machine reconfigur failed");
				}
			}
		}

		public static void reConfig(String vmName, Map<String, String> operMap,
				String diskOper) throws Exception {
			ManagedObjectReference virtualMachine = getMOREFsInContainerByType(
					serviceContent.getRootFolder(), "VirtualMachine").get(vmName);
			
			VirtualMachineConfigSpec vmConfigSpec = new VirtualMachineConfigSpec();
			
			if (operMap.containsKey("memory")) {
				try {
					// vmConfigSpec.setMemoryAllocation(getShares());
					String size = operMap.get("memory");
					vmConfigSpec
							.setMemoryMB((long) (Integer.parseInt(size) * 1024));
				} catch (java.lang.NumberFormatException nfe) {
					System.out.println("Value of Memory update must "
							+ "be one of high|low|normal|[numeric value]");
					return;
				}
			}
			if (operMap.containsKey("cpu")) {
				try {
					// vmConfigSpec.setCpuAllocation(getShares());
					String size = operMap.get("cpu");
					vmConfigSpec.setNumCPUs(Integer.parseInt(size));
				} catch (SOAPFaultException sfe) {
					printSoapFaultException(sfe);
				} catch (java.lang.NumberFormatException nfe) {
					System.out.println("Value of CPU update must "
							+ "be one of high|low|normal|[numeric value]");
					return;
				}
			}
			if (operMap.containsKey("disk")) {
				String size = operMap.get("disk");
				Map<String, ManagedObjectReference> vms=getVms(vmName);
				ManagedObjectReference mor = vms.get(vmName);
				mor.getValue();
				VirtualDeviceConfigSpec vdiskSpec = getDiskDeviceConfigSpec(
						virtualMachine, diskOper, size, vmName, "硬盘 1", null);
				if (vdiskSpec != null) {
					List<VirtualDeviceConfigSpec> vdiskSpecArray = new ArrayList<VirtualDeviceConfigSpec>();
					vdiskSpecArray.add(vdiskSpec);
					vmConfigSpec.getDeviceChange().addAll(vdiskSpecArray);
				} else {
					return;
				}
			}

			if (operMap.containsKey("name")) {
				String newName = operMap.get("name");
				vmConfigSpec.setName(newName);
			}
			ManagedObjectReference tmor = vimPort.reconfigVMTask(virtualMachine,
					vmConfigSpec);
			if (getTaskResultAfterDone(tmor)) {
				System.out.println("Virtual Machine reconfigured successfully");
			} else {
				System.out.println("Virtual Machine reconfigur failed");
			}
		}
		/**
		 * 网卡
		 * @param vmName
		 * @param deviceName
		 * @throws Exception
		 */
		public static void removeVMPNIC(String vmName, String deviceName)
				throws Exception {
			ManagedObjectReference virtualMachine = getMOREFsInContainerByType(
					serviceContent.getRootFolder(), "VirtualMachine").get(vmName);

			VirtualMachineConfigSpec vmConfigSpec = new VirtualMachineConfigSpec();

			VirtualDeviceConfigSpec nicSpec = getNICDeviceConfigSpec("Remove",
					virtualMachine, deviceName, null, null, null,null);
			
			if (nicSpec != null) {
				List<VirtualDeviceConfigSpec> nicSpecArray = new ArrayList<VirtualDeviceConfigSpec>();
				nicSpecArray.add(nicSpec);
				vmConfigSpec.getDeviceChange().addAll(nicSpecArray);
			}
			ManagedObjectReference tmor = vimPort.reconfigVMTask(virtualMachine,
					vmConfigSpec);
			if (getTaskResultAfterDone(tmor)) {
				System.out.println("Virtual Machine reconfigured successfully");
			} else {
				System.out.println("Virtual Machine reconfigur failed");
			}
		}

		public static void addVMPNIC(String vmName, String deviceName,String host)
				throws Exception {
			ManagedObjectReference virtualMachine = getMOREFsInContainerByType(
					serviceContent.getRootFolder(), "VirtualMachine").get(vmName);

			VirtualMachineConfigSpec vmConfigSpec = new VirtualMachineConfigSpec();
			List<HostProxySwitch> hostProxySwitchs =  getProxyVirtualSwitch(host);
			VirtualDeviceConfigSpec nicSpec = getNICDeviceConfigSpec("Add",
					virtualMachine, null, deviceName, null, null,hostProxySwitchs);
			if (nicSpec != null) {
				List<VirtualDeviceConfigSpec> nicSpecArray = new ArrayList<VirtualDeviceConfigSpec>();
				nicSpecArray.add(nicSpec);
				vmConfigSpec.getDeviceChange().addAll(nicSpecArray);
			}
			ManagedObjectReference tmor = vimPort.reconfigVMTask(virtualMachine,
					vmConfigSpec);
			if (getTaskResultAfterDone(tmor)) {
				System.out.println("Virtual Machine reconfigured successfully");
			} else {
				System.out.println("Virtual Machine reconfigur failed");
			}
		}
		
		/**
		 * 遍历快照 tree
		 * 
		 * @param snapTree
		 * @param findName
		 * @param print
		 * @return
		 */
		public static StringBuilder traverseSnapshotInTree(
				List<VirtualMachineSnapshotTree> snapTree, String findName,
				boolean print, int index, StringBuilder fileContent) {
			ManagedObjectReference snapmor = null;
			if (index == 1) {
				fileContent.append("<br><hr><ul>");
			}
			if (snapTree == null) {
				return null;
			}
			for (int i = 0; i < snapTree.size() && snapmor == null; i++) {
				VirtualMachineSnapshotTree node = snapTree.get(i);
				if (index == 1) {
					fileContent
							.append("<li><div style='float:left;'>"
									+ node.getName() + "</div><div style='float:right;'><a onclick=\"delSnapShot('"+node.getId()+"','"+node.getName()+"')\"  href=\"javascript:;\">删除</a></div>");
				} else {
					fileContent
							.append("<br><hr><ul><li><div style='float:left;'>"
									+ node.getName() + "</div><div style='float:right;'><a onclick=\"delSnapShot('"+node.getId()+"','"+node.getName()+"')\"  href=\"javascript:;\">删除</a></div>");
				}
				if (print) {
					System.out.println("Snapshot Name : " + node.getName());
				}
				if (findName != null && node.getName().equals(findName)) {
					snapmor = node.getSnapshot();
				} else {
					List<VirtualMachineSnapshotTree> listvmst = node
							.getChildSnapshotList();
					List<VirtualMachineSnapshotTree> childTree = listvmst;
					traverseSnapshotInTree(childTree, findName, print, index + 1,
							fileContent);
				}
			}
			fileContent.append("</li></ul>");
			System.out.println(fileContent);
			return fileContent;
		}

		public static boolean revertSnapshot(String vmName, String snapshotname)
				throws Exception {
			Map<String, ManagedObjectReference> vmMap = getVms(vmName);
			if (vmMap == null || vmMap.isEmpty()) {
				System.out.println("No Virtual Machine found matching "
						+ "the specified criteria");
				return false;
			} else {
				for (String vmname : vmMap.keySet()) {
					ManagedObjectReference vmMor = vmMap.get(vmname);
					ManagedObjectReference snapmor = getSnapshotReference(vmMor,
							vmName, snapshotname);
					if (snapmor != null) {
						ManagedObjectReference taskMor = vimPort
								.revertToSnapshotTask(snapmor, null, Boolean.TRUE);

						if (taskMor != null) {
							String[] opts = new String[] { "info.state",
									"info.error", "info.progress" };
							String[] opt = new String[] { "state" };
							Object[] results = waitForValues(taskMor, opts, opt,
									new Object[][] { new Object[] {
											TaskInfoState.SUCCESS,
											TaskInfoState.ERROR } });

							// Wait till the task completes.
							if (results[0].equals(TaskInfoState.SUCCESS)) {
								System.out
										.printf(
												" Reverting Snapshot - [ %s ] Successful %n",
												snapshotname);
								return Boolean.TRUE;
							} else {
								System.out.printf(
										" Reverting Snapshot - [ %s ] Failure %n",
										snapshotname);
								return Boolean.FALSE;
							}
						}
					} else {
						System.out.println("Snapshot not found");
					}
				}
			}
			return false;
		}

		private static ManagedObjectReference getSnapshotReference(
				ManagedObjectReference vmmor, String vmName, String snapName)
				throws Exception {
			VirtualMachineSnapshotInfo snapInfo = getSnapshotInfo(vmmor, vmName);
			ManagedObjectReference snapmor = null;
			if (snapInfo != null) {
				List<VirtualMachineSnapshotTree> listvmst = snapInfo
						.getRootSnapshotList();
				List<VirtualMachineSnapshotTree> snapTree = listvmst;
				snapmor = traverseSnapshotInTree1(snapTree, snapName, false);
			} else {
				System.out.println("No Snapshot named : " + snapName
						+ " found for VirtualMachine : " + vmName);
			}
			return snapmor;
		}

		private static VirtualMachineSnapshotInfo getSnapshotInfo(
				ManagedObjectReference vmmor, String vmName) throws Exception {
			List<ObjectContent> snaps = getSnapShotVMMor(vmmor);
			VirtualMachineSnapshotInfo snapInfo = null;
			if (snaps != null && snaps.size() > 0) {
				ObjectContent snapobj = snaps.get(0);
				List<DynamicProperty> listdp = snapobj.getPropSet();
				DynamicProperty[] snapary = listdp
						.toArray(new DynamicProperty[listdp.size()]);
				if (snapary != null && snapary.length > 0) {
					snapInfo = ((VirtualMachineSnapshotInfo) (snapary[0]).getVal());
				}
			} else {
				System.out.println("No Snapshots found for VirtualMachine : "
						+ vmName);
			}
			return snapInfo;
		}

		private static ManagedObjectReference traverseSnapshotInTree1(
				List<VirtualMachineSnapshotTree> snapTree, String findName,
				boolean print) {
			ManagedObjectReference snapmor = null;
			if (snapTree == null) {
				return snapmor;
			}
			for (int i = 0; i < snapTree.size() && snapmor == null; i++) {
				VirtualMachineSnapshotTree node = snapTree.get(i);
				if (print) {
					System.out.println("Snapshot Name : " + node.getName());
				}
				if (findName != null && node.getName().equals(findName)) {
					snapmor = node.getSnapshot();
				} else {
					List<VirtualMachineSnapshotTree> listvmst = node
							.getChildSnapshotList();
					List<VirtualMachineSnapshotTree> childTree = listvmst;
					snapmor = traverseSnapshotInTree1(childTree, findName, print);
				}
			}
			return snapmor;
		}

		public static boolean removeAllSnapshot(String vmName) throws Exception {
			Map<String, ManagedObjectReference> vmMap = getVms(vmName);
			if (vmMap == null || vmMap.isEmpty()) {
				System.out.println("No Virtual Machine found matching "
						+ "the specified criteria");
				return false;
			} else {
				for (String vmname : vmMap.keySet()) {
					ManagedObjectReference vmMor = vmMap.get(vmname);
					ManagedObjectReference taskMor = vimPort
							.removeAllSnapshotsTask(vmMor, true);
					if (taskMor != null) {
						String[] opts = new String[] { "info.state", "info.error",
								"info.progress" };
						String[] opt = new String[] { "state" };
						Object[] results = waitForValues(
								taskMor,
								opts,
								opt,
								new Object[][] { new Object[] {
										TaskInfoState.SUCCESS, TaskInfoState.ERROR } });

						// Wait till the task completes.
						if (results[0].equals(TaskInfoState.SUCCESS)) {
							System.out
									.printf(
											" Removing All Snapshots on - [ %s ] Successful %n",
											vmname);
							return Boolean.TRUE;
						} else {
							System.out
									.printf(
											" Removing All Snapshots on - [ %s ] Failure %n",
											vmname);
							return Boolean.FALSE;
						}
					} else {
						return Boolean.FALSE;
					}
				}
			}
			return Boolean.TRUE;
		}

		public static boolean removeSnapshot(String vmName,String snapshotname,String removeChild)
				throws Exception {
			int rem = Integer.parseInt(removeChild);
			boolean flag = true;
			if (rem == 0) {
				flag = false;
			}
			Map<String, ManagedObjectReference> vmMap = getVms(vmName);
			if (vmMap == null || vmMap.isEmpty()) {
				System.out.println("No Virtual Machine found matching "
						+ "the specified criteria");
				return false;
			} else {
				for (String vmname : vmMap.keySet()) {
					ManagedObjectReference vmMor = vmMap.get(vmname);
					ManagedObjectReference snapmor = getSnapshotReference(vmMor,
							vmname, snapshotname);
					if (snapmor != null) {
						ManagedObjectReference taskMor = vimPort.removeSnapshotTask(
								snapmor, flag, true);
						if (taskMor != null) {
							String[] opts = new String[] { "info.state", "info.error",
									"info.progress" };
							String[] opt = new String[] { "state" };
							Object[] results = waitForValues(taskMor, opts, opt,
									new Object[][] { new Object[] { TaskInfoState.SUCCESS,
											TaskInfoState.ERROR } });
			
							// Wait till the task completes.
							if (results[0].equals(TaskInfoState.SUCCESS)) {
								System.out.printf(
										" Removing Snapshot - [ %s ] Successful %n",
										snapshotname);
								return Boolean.TRUE;
							} else {
								System.out.printf(" Removing Snapshot - [ %s ] Failure %n",
										snapshotname);
								return Boolean.FALSE;
							}
						}
					} else {
						System.out.println("Snapshot not found");
					}
				}
			}
			return false;
		}
	/*-----------------------------------------------------------------虚拟机结束---------------------------------------------------------------------------------------*/
		/*
		 * 获取目录
		 */
		public static List<ManagedObjectReference> getFolder()
				throws InvalidPropertyFaultMsg, RuntimeFaultFaultMsg {
			getMOREFsInFolderByType(
					serviceContent.getRootFolder(), "Folder");
			new ArrayList<ManagedObjectReference>();
			
			return null;
		}
}