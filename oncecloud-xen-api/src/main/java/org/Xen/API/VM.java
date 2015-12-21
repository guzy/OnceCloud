/*
 * Copyright (c) Citrix Systems, Inc.
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of version 2 of the GNU General Public License as published
 * by the Free Software Foundation, with the additional linking exception as
 * follows:
 * 
 *   Linking this library statically or dynamically with other modules is
 *   making a combined work based on this library. Thus, the terms and
 *   conditions of the GNU General Public License cover the whole combination.
 * 
 *   As a special exception, the copyright holders of this library give you
 *   permission to link this library with independent modules to produce an
 *   executable, regardless of the license terms of these independent modules,
 *   and to copy and distribute the resulting executable under terms of your
 *   choice, provided that you also meet, for each linked independent module,
 *   the terms and conditions of the license of that module. An independent
 *   module is a module which is not derived from or based on this library. If
 *   you modify this library, you may extend this exception to your version of
 *   the library, but you are not obligated to do so. If you do not wish to do
 *   so, delete this exception statement from your version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for
 * more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package org.Xen.API;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.apache.xmlrpc.XmlRpcException;
import org.Xen.API.Types.BadServerResponse;
import org.Xen.API.Types.XenAPIException;
import org.Xen.API.VMUtil.DiskInfo;

import org.Xen.API.Connection;
import org.Xen.API.Marshalling;
import org.Xen.API.VIF;

/**
 * A virtual machine (or 'guest').
 * 
 * 
 * Attention: As a RPC-Client, if the @param connection of functions is invalid,
 * then @throw java.lang.RuntimeException
 * 
 * @author Citrix Systems, Inc.
 */
public class VM extends XenAPIObject {
	/**
	 * The XenAPI reference to this object.
	 */
	protected final String ref;

	/**
	 * For internal use only.
	 */
	VM(String ref) {
		this.ref = ref;
	}

	public String toWireString() {
		return this.ref;
	}

	/**
	 * If obj is a VM, compares XenAPI references for equality.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof VM) {
			VM other = (VM) obj;
			return other.ref.equals(this.ref);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return ref.hashCode();
	}

	/**
	 * Represents all the fields in a VM
	 */
	public static class Record implements Types.Record {
		public long timestamp;

		public Record() {
			timestamp = System.currentTimeMillis();
		}

		public String toString() {
			StringWriter writer = new StringWriter();
			PrintWriter print = new PrintWriter(writer);
			print.printf("%1$20s: %2$s\n", "uuid", this.uuid);
			print.printf("%1$20s: %2$s\n", "allowedOperations",
					this.allowedOperations);
			print.printf("%1$20s: %2$s\n", "currentOperations",
					this.currentOperations);
			print.printf("%1$20s: %2$s\n", "powerState", this.powerState);
			print.printf("%1$20s: %2$s\n", "nameLabel", this.nameLabel);
			print.printf("%1$20s: %2$s\n", "nameDescription",
					this.nameDescription);
			print.printf("%1$20s: %2$s\n", "userVersion", this.userVersion);
			print.printf("%1$20s: %2$s\n", "isATemplate", this.isATemplate);
			print.printf("%1$20s: %2$s\n", "isLocalVM", this.isLocalVM);
			print.printf("%1$20s: %2$s\n", "ipaddr", this.ipaddr);
			print.printf("%1$20s: %2$s\n", "MAC", this.MAC);
			print.printf("%1$20s: %2$s\n", "suspendVDI", this.suspendVDI);
			print.printf("%1$20s: %2$s\n", "residentOn", this.residentOn);
			print.printf("%1$20s: %2$s\n", "affinity", this.affinity);
			print.printf("%1$20s: %2$s\n", "memoryOverhead",
					this.memoryOverhead);
			print.printf("%1$20s: %2$s\n", "memoryTarget", this.memoryTarget);
			print.printf("%1$20s: %2$s\n", "memoryStaticMax",
					this.memoryStaticMax);
			print.printf("%1$20s: %2$s\n", "memoryDynamicMax",
					this.memoryDynamicMax);
			print.printf("%1$20s: %2$s\n", "memoryDynamicMin",
					this.memoryDynamicMin);
			print.printf("%1$20s: %2$s\n", "memoryStaticMin",
					this.memoryStaticMin);
			print.printf("%1$20s: %2$s\n", "VCPUsParams", this.VCPUsParams);
			print.printf("%1$20s: %2$s\n", "VCPUsMax", this.VCPUsMax);
			print.printf("%1$20s: %2$s\n", "VCPUsAtStartup",
					this.VCPUsAtStartup);
			print.printf("%1$20s: %2$s\n", "actionsAfterShutdown",
					this.actionsAfterShutdown);
			print.printf("%1$20s: %2$s\n", "actionsAfterReboot",
					this.actionsAfterReboot);
			print.printf("%1$20s: %2$s\n", "actionsAfterCrash",
					this.actionsAfterCrash);
			print.printf("%1$20s: %2$s\n", "consoles", this.consoles);
			print.printf("%1$20s: %2$s\n", "VIFs", this.VIFs);
			print.printf("%1$20s: %2$s\n", "VBDs", this.VBDs);
			print.printf("%1$20s: %2$s\n", "PVBootloader", this.PVBootloader);
			print.printf("%1$20s: %2$s\n", "PVKernel", this.PVKernel);
			print.printf("%1$20s: %2$s\n", "PVRamdisk", this.PVRamdisk);
			print.printf("%1$20s: %2$s\n", "PVArgs", this.PVArgs);
			print.printf("%1$20s: %2$s\n", "PVBootloaderArgs",
					this.PVBootloaderArgs);
			print.printf("%1$20s: %2$s\n", "PVLegacyArgs", this.PVLegacyArgs);
			print.printf("%1$20s: %2$s\n", "HVMBootPolicy", this.HVMBootPolicy);
			print.printf("%1$20s: %2$s\n", "HVMBootParams", this.HVMBootParams);
			print.printf("%1$20s: %2$s\n", "HVMShadowMultiplier",
					this.HVMShadowMultiplier);
			print.printf("%1$20s: %2$s\n", "platform", this.platform);
			print.printf("%1$20s: %2$s\n", "PCIBus", this.PCIBus);
			print.printf("%1$20s: %2$s\n", "otherConfig", this.otherConfig);
			print.printf("%1$20s: %2$s\n", "domid", this.domid);
			print.printf("%1$20s: %2$s\n", "domarch", this.domarch);
			print.printf("%1$20s: %2$s\n", "lastBootCPUFlags",
					this.lastBootCPUFlags);
			print.printf("%1$20s: %2$s\n", "isControlDomain",
					this.isControlDomain);
			print.printf("%1$20s: %2$s\n", "metrics", this.metrics);
			print.printf("%1$20s: %2$s\n", "isASnapshot", this.isASnapshot);
			print.printf("%1$20s: %2$s\n", "tags", this.tags);
			print.printf("%1$20s: %2$s\n", "suspendSR", this.suspendSR);
			print.printf("%1$20s: %2$s\n", "connectedDiskSRs",
					this.connectedDiskSRs);
			print.printf("%1$20s: %2$s\n", "connectedIsoSRs",
					this.connectedIsoSRs);
			print.printf("%1$20s: %2$s\n", "version", this.version);
			print.printf("%1$20s: %2$s\n", "timestamp", this.timestamp);
			print.printf("%1$20s: %2$s\n", "setpasswd", this.setpasswd);
			return writer.toString();
		}

		/**
		 * Convert a VM.Record to a Map
		 */
		public Map<String, Object> toMap() {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("uuid", this.uuid == null ? "" : this.uuid);
			map.put("allowed_operations",
					this.allowedOperations == null ? new LinkedHashSet<Types.VmOperations>()
							: this.allowedOperations);
			map.put("current_operations",
					this.currentOperations == null ? new HashMap<String, Types.VmOperations>()
							: this.currentOperations);
			map.put("power_state",
					this.powerState == null ? Types.VmPowerState.UNRECOGNIZED
							: this.powerState);
			map.put("name_label", this.nameLabel == null ? "" : this.nameLabel);
			map.put("name_description", this.nameDescription == null ? ""
					: this.nameDescription);
			map.put("user_version", this.userVersion == null ? 0
					: this.userVersion);
			map.put("is_a_template", this.isATemplate == null ? false
					: this.isATemplate);
			map.put("is_local_vm", this.isLocalVM == null ? false
					: this.isLocalVM);
			map.put("ip_addr",
					this.ipaddr == null ? new LinkedHashSet<String>()
							: this.ipaddr);
			map.put("MAC", this.MAC == null ? "" : this.MAC);
			map.put("suspend_VDI", this.suspendVDI == null ? new VDI(
					"OpaqueRef:NULL") : this.suspendVDI);
			map.put("resident_on", this.residentOn == null ? new Host(
					"OpaqueRef:NULL") : this.residentOn);
			map.put("affinity", this.affinity == null ? new Host(
					"OpaqueRef:NULL") : this.affinity);
			map.put("memory_overhead", this.memoryOverhead == null ? 0
					: this.memoryOverhead);
			map.put("memory_target", this.memoryTarget == null ? 0
					: this.memoryTarget);
			map.put("memory_static_max", this.memoryStaticMax == null ? 0
					: this.memoryStaticMax);
			map.put("memory_dynamic_max", this.memoryDynamicMax == null ? 0
					: this.memoryDynamicMax);
			map.put("memory_dynamic_min", this.memoryDynamicMin == null ? 0
					: this.memoryDynamicMin);
			map.put("memory_static_min", this.memoryStaticMin == null ? 0
					: this.memoryStaticMin);
			map.put("VCPUs_params",
					this.VCPUsParams == null ? new HashMap<String, String>()
							: this.VCPUsParams);
			map.put("VCPUs_max", this.VCPUsMax == null ? 0 : this.VCPUsMax);
			map.put("VCPUs_at_startup", this.VCPUsAtStartup == null ? 0
					: this.VCPUsAtStartup);
			map.put("actions_after_shutdown",
					this.actionsAfterShutdown == null ? Types.OnNormalExit.UNRECOGNIZED
							: this.actionsAfterShutdown);
			map.put("actions_after_reboot",
					this.actionsAfterReboot == null ? Types.OnNormalExit.UNRECOGNIZED
							: this.actionsAfterReboot);
			map.put("actions_after_crash",
					this.actionsAfterCrash == null ? Types.OnCrashBehaviour.UNRECOGNIZED
							: this.actionsAfterCrash);
			map.put("consoles",
					this.consoles == null ? new LinkedHashSet<Console>()
							: this.consoles);
			map.put("VIFs", this.VIFs == null ? new LinkedHashSet<VIF>()
					: this.VIFs);
			map.put("VBDs", this.VBDs == null ? new LinkedHashSet<VBD>()
					: this.VBDs);

			map.put("PV_bootloader", this.PVBootloader == null ? ""
					: this.PVBootloader);
			map.put("PV_kernel", this.PVKernel == null ? "" : this.PVKernel);
			map.put("PV_ramdisk", this.PVRamdisk == null ? "" : this.PVRamdisk);
			map.put("PV_args", this.PVArgs == null ? "" : this.PVArgs);
			map.put("PV_bootloader_args", this.PVBootloaderArgs == null ? ""
					: this.PVBootloaderArgs);
			map.put("PV_legacy_args", this.PVLegacyArgs == null ? ""
					: this.PVLegacyArgs);
			map.put("HVM_boot_policy", this.HVMBootPolicy == null ? ""
					: this.HVMBootPolicy);
			map.put("HVM_boot_params",
					this.HVMBootParams == null ? new HashMap<String, String>()
							: this.HVMBootParams);
			map.put("HVM_shadow_multiplier",
					this.HVMShadowMultiplier == null ? 0.0
							: this.HVMShadowMultiplier);
			map.put("platform",
					this.platform == null ? new HashMap<String, String>()
							: this.platform);
			map.put("PCI_bus", this.PCIBus == null ? "" : this.PCIBus);
			map.put("other_config",
					this.otherConfig == null ? new HashMap<String, String>()
							: this.otherConfig);
			map.put("domid", this.domid == null ? 0 : this.domid);
			map.put("domarch", this.domarch == null ? "" : this.domarch);
			map.put("last_boot_CPU_flags",
					this.lastBootCPUFlags == null ? new HashMap<String, String>()
							: this.lastBootCPUFlags);
			map.put("is_control_domain", this.isControlDomain == null ? false
					: this.isControlDomain);
			map.put("metrics", this.metrics == null ? new VMMetrics(
					"OpaqueRef:NULL") : this.metrics);

			map.put("is_a_snapshot", this.isASnapshot == null ? false
					: this.isASnapshot);

			map.put("tags", this.tags == null ? new LinkedHashSet<String>()
					: this.tags);

			map.put("bios_strings",
					this.biosStrings == null ? new HashMap<String, String>()
							: this.biosStrings);

			map.put("suspend_SR", this.suspendSR == null ? new SR(
					"OpaqueRef:NULL") : this.suspendSR);
			map.put("connected_disk_SRs",
					this.connectedDiskSRs == null ? new LinkedHashSet<SR>()
							: this.connectedDiskSRs);
			map.put("connected_iso_SRs",
					this.connectedIsoSRs == null ? new LinkedHashSet<SR>()
							: this.connectedIsoSRs);
			map.put("version", this.version == null ? 0 : this.version);
			map.put("setpasswd", this.setpasswd == null ? null : this.setpasswd);
			return map;
		}

		/**
		 * Unique identifier/object reference
		 */
		public String uuid;
		/**
		 * list of the operations allowed in this state. This list is advisory
		 * only and the server state may have changed by the time this field is
		 * read by a client.
		 */
		public Set<Types.VmOperations> allowedOperations;
		/**
		 * links each of the running tasks using this object (by reference) to a
		 * current_operation enum which describes the nature of the task.
		 */
		public Map<String, Types.VmOperations> currentOperations;
		/**
		 * Current power state of the machine
		 */
		public Types.VmPowerState powerState;
		/**
		 * a human-readable name
		 */
		public String nameLabel;
		/**
		 * a notes field containg human-readable description
		 */
		public String nameDescription;
		/**
		 * a user version number for this machine
		 */
		public Long userVersion;
		/**
		 * true if this is a template. Template VMs can never be started, they
		 * are used only for cloning other VMs
		 */
		public Boolean isATemplate;
		/**
		 * true if this is a local domain.
		 */
		public Boolean isLocalVM;
		/**
		 * The VDI that a suspend image is stored on. (Only has meaning if VM is
		 * currently suspended)
		 */
		public VDI suspendVDI;
		/**
		 * the host the VM is currently resident on
		 */
		public Host residentOn;
		/**
		 * a host which the VM has some affinity for (or NULL). This is used as
		 * a hint to the start call when it decides where to run the VM.
		 * Implementations are free to ignore this field.
		 */
		public Host affinity;
		/**
		 * Virtualization memory overhead (bytes).
		 */
		public Long memoryOverhead;
		/**
		 * Dynamically-set memory target (bytes). The value of this field
		 * indicates the current target for memory available to this VM.
		 */
		public Long memoryTarget;
		/**
		 * Statically-set (i.e. absolute) maximum (bytes). The value of this
		 * field at VM start time acts as a hard limit of the amount of memory a
		 * guest can use. New values only take effect on reboot.
		 */
		public Long memoryStaticMax;
		/**
		 * Dynamic maximum (bytes)
		 */
		public Long memoryDynamicMax;
		/**
		 * Dynamic minimum (bytes)
		 */
		public Long memoryDynamicMin;
		/**
		 * Statically-set (i.e. absolute) mininum (bytes). The value of this
		 * field indicates the least amount of memory this VM can boot with
		 * without crashing.
		 */
		public Long memoryStaticMin;
		/**
		 * configuration parameters for the selected VCPU policy
		 */
		public Map<String, String> VCPUsParams;
		/**
		 * Max number of VCPUs
		 */
		public Long VCPUsMax;
		/**
		 * Boot number of VCPUs
		 */
		public Long VCPUsAtStartup;
		/**
		 * action to take after the guest has shutdown itself
		 */
		public Types.OnNormalExit actionsAfterShutdown;
		/**
		 * action to take after the guest has rebooted itself
		 */
		public Types.OnNormalExit actionsAfterReboot;
		/**
		 * action to take if the guest crashes
		 */
		public Types.OnCrashBehaviour actionsAfterCrash;
		/**
		 * virtual console devices
		 */
		public Set<Console> consoles;
		/**
		 * virtual network interfaces
		 */
		public Set<VIF> VIFs;
		/**
		 * virtual block devices
		 */
		public Set<VBD> VBDs;

		/**
		 * name of or path to bootloader
		 */
		public String PVBootloader;
		/**
		 * path to the kernel
		 */
		public String PVKernel;
		/**
		 * path to the initrd
		 */
		public String PVRamdisk;
		/**
		 * kernel command-line arguments
		 */
		public String PVArgs;
		/**
		 * miscellaneous arguments for the bootloader
		 */
		public String PVBootloaderArgs;
		/**
		 * to make Zurich guests boot
		 */
		public String PVLegacyArgs;
		/**
		 * HVM boot policy
		 */
		public String HVMBootPolicy;
		/**
		 * HVM boot params
		 */
		public Map<String, String> HVMBootParams;
		/**
		 * multiplier applied to the amount of shadow that will be made
		 * available to the guest
		 */
		public Double HVMShadowMultiplier;
		/**
		 * platform-specific configuration
		 */
		public Map<String, String> platform;
		/**
		 * PCI bus path for pass-through devices
		 */
		public String PCIBus;
		/**
		 * additional configuration
		 */
		public Map<String, String> otherConfig;
		/**
		 * domain ID (if available, -1 otherwise)
		 */
		public Long domid;
		/**
		 * Domain architecture (if available, null string otherwise)
		 */
		public String domarch;
		/**
		 * describes the CPU flags on which the VM was last booted
		 */
		public Map<String, String> lastBootCPUFlags;
		/**
		 * true if this is a control domain (domain 0 or a driver domain)
		 */
		public Boolean isControlDomain;
		/**
		 * metrics associated with this VM
		 */
		public VMMetrics metrics;

		/**
		 * true if this is a snapshot. Snapshotted VMs can never be started,
		 * they are used only for cloning other VMs
		 */
		public Boolean isASnapshot;

		/**
		 * user-specified tags for categorization purposes
		 */
		public Set<String> tags;

		/**
		 * BIOS strings
		 */
		public Map<String, String> biosStrings;

		/**
		 * The SR on which a suspend image is stored
		 */
		public SR suspendSR;

		public Set<SR> connectedDiskSRs;

		public Set<SR> connectedIsoSRs;
		/**
		 * The number of times this VM has been recovered
		 */
		public Long version;
		// frank
		public Set<String> ipaddr;
		// frank
		public Set<String> MAC;
		// wufan
		public Boolean setpasswd;
	}

	/**
	 * Get a record containing the current state of the given VM.
	 * 
	 * @return all fields from the object
	 */
	public VM.Record getRecord(Connection c) throws BadServerResponse,
			XenAPIException, XmlRpcException {
		String method_call = "VM.get_record";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toVMRecord(result);
	}

	/**
	 * Get a reference to the VM instance with the specified UUID.
	 * 
	 * @param uuid
	 *            UUID of object to return
	 * @return reference to the object
	 */
	public static VM getByUuid(Connection c, String uuid)
			throws BadServerResponse, XenAPIException, XmlRpcException {
		String method_call = "VM.get_by_uuid";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(uuid) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toVM(result);
	}

	/**
	 * The required parameters include(if don't have the creation will fail):
	 * memoryStaticMax,memoryDynamicMax,memoryDynamicMin,actionsAfterCrash,
	 * actionAfterReboot,actionAfterShutdown,
	 * 
	 * @param c
	 * @param record
	 * @param host
	 * @return
	 * @throws BadServerResponse
	 * @throws XenAPIException
	 * @throws XmlRpcException
	 */
	public static VM createOn(Connection c, VM.Record record, Host host)
			throws XenAPIException, XmlRpcException {
		String method_call = "VM.create_on";
		String session = c.getSessionReference();
		Map<String, Object> record_map = record.toMap();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(record_map), Marshalling.toXMLRPC(host) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toVM(result);
	}

	public static VM createFromSxp(Connection c, String path)
			throws XenAPIException, XmlRpcException {
		String method_call = "VM.create_from_sxp";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(path) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toVM(result);
	}

	/**
	 * Destroy the specified VM. The VM is completely removed from the system.
	 * This function can only be called when the VM is in the Halted State.
	 * 
	 */
	public void destroy(Connection c, Boolean del_vdi)
			throws BadServerResponse, XenAPIException, XmlRpcException {
		String method_call = "VM.destroy";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref), Marshalling.toXMLRPC(del_vdi) };
		c.dispatch(method_call, method_params);
		return;
	}

	public boolean destroySnapshot(Connection c, String snapName)
			throws BadServerResponse, XenAPIException, XmlRpcException {
		String method_call = "VM.destroy_snapshot";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref), Marshalling.toXMLRPC(snapName) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toBoolean(result);
	}

	public boolean destroyAllSnapshots(Connection c) throws BadServerResponse,
			XenAPIException, XmlRpcException {
		String method_call = "VM.destroy_all_snapshots";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toBoolean(result);
	}

	/**
	 * Destroy fiber device.
	 * 
	 */
	public void destroyFiber(Connection c, VBD vbd) throws BadServerResponse,
			XenAPIException, XmlRpcException {
		String method_call = "VM.destroy_fiber";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref), Marshalling.toXMLRPC(vbd) };
		c.dispatch(method_call, method_params);
		return;
	}

	/**
	 * Destroy usb scsi device.
	 * 
	 */
	public void destroyUsbScsi(Connection c, VBD vbd) throws BadServerResponse,
			XenAPIException, XmlRpcException {
		String method_call = "VM.destroy_usb_scsi";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref), Marshalling.toXMLRPC(vbd) };
		c.dispatch(method_call, method_params);
		return;
	}

	/**
	 * Destroy cd-rom device.
	 * 
	 */
	public void destroyMedia(Connection c, VBD vbd) throws BadServerResponse,
			XenAPIException, XmlRpcException {
		String method_call = "VM.destroy_media";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref), Marshalling.toXMLRPC(vbd) };
		c.dispatch(method_call, method_params);
		return;
	}

	/**
	 * Enable cd-rom device.
	 * 
	 */
	public void enableMedia(Connection c, VBD vbd) throws BadServerResponse,
			XenAPIException, XmlRpcException {
		String method_call = "VM.enable_media";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref), Marshalling.toXMLRPC(vbd) };
		c.dispatch(method_call, method_params);
		return;
	}

	/**
	 * Disable cd-rom device.
	 * 
	 */
	public void disableMedia(Connection c, VBD vbd) throws BadServerResponse,
			XenAPIException, XmlRpcException {
		String method_call = "VM.disable_media";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref), Marshalling.toXMLRPC(vbd) };
		c.dispatch(method_call, method_params);
		return;
	}

	/**
	 * Disable cd-rom device.
	 * 
	 */
	public void ejectMedia(Connection c, VBD vbd) throws BadServerResponse,
			XenAPIException, XmlRpcException {
		String method_call = "VM.eject_media";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref), Marshalling.toXMLRPC(vbd) };
		c.dispatch(method_call, method_params);
		return;
	}

	/**
	 * Destroy fiber device.
	 * 
	 */
	public void destroyVIF(Connection c, VIF vif) throws BadServerResponse,
			XenAPIException, XmlRpcException {
		String method_call = "VM.destroy_VIF";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref), Marshalling.toXMLRPC(vif) };
		c.dispatch(method_call, method_params);
		return;
	}

	/**
	 * Get all the VM instances with the given label.
	 * 
	 * @param label
	 *            label of object to return
	 * @return references to objects with matching names
	 */
	public static Set<VM> getByNameLabel(Connection c, String label)
			throws BadServerResponse, XenAPIException, XmlRpcException {
		String method_call = "VM.get_by_name_label";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(label) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toSetOfVM(result);
	}

	/**
	 * Get the uuid field of the given VM.
	 * 
	 * @return value of the field
	 */
	public String getUuid(Connection c) throws BadServerResponse,
			XenAPIException, XmlRpcException {
		String method_call = "VM.get_uuid";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toString(result);
	}

	/**
	 * Get the power_state field of the given VM.
	 * 
	 * @return value of the field
	 */
	public Types.VmPowerState getPowerState(Connection c)
			throws BadServerResponse, XenAPIException, XmlRpcException {
		String method_call = "VM.get_power_state";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toVmPowerState(result);
	}

	/**
	 * Get the name/label field of the given VM.
	 * 
	 * @return value of the field
	 */
	public String getNameLabel(Connection c) throws BadServerResponse,
			XenAPIException, XmlRpcException {
		String method_call = "VM.get_name_label";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toString(result);
	}

	/**
	 * Get the name/description field of the given VM.
	 * 
	 * @return value of the field
	 */
	public String getNameDescription(Connection c) throws BadServerResponse,
			XenAPIException, XmlRpcException {
		String method_call = "VM.get_name_description";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toString(result);
	}

	public String getVNCLocation(Connection c) throws BadServerResponse,
			XenAPIException, XmlRpcException {
		String method_call = "VM.get_vnc_location";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toString(result);
	}

	/**
	 * Get the is_a_template field of the given VM.
	 * 
	 * @return value of the field
	 */
	public Boolean getIsATemplate(Connection c) throws BadServerResponse,
			XenAPIException, XmlRpcException {
		String method_call = "VM.get_is_a_template";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toBoolean(result);
	}

	// frank
	public Set<String> getIpAddr(Connection c) throws BadServerResponse,
			XenAPIException, XmlRpcException {
		String method_call = "VM.get_ip_addr";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toSetOfString(result);
	}

	// frank
	public String getMAC(Connection c) throws BadServerResponse,
			XenAPIException, XmlRpcException {
		String method_call = "VM.get_MAC";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toString(result);
	}

	/**
	 * Get the resident_on field of the given VM.
	 * 
	 * @return value of the field
	 */
	public Host getResidentOn(Connection c) throws BadServerResponse,
			XenAPIException, XmlRpcException {
		String method_call = "VM.get_resident_on";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toHost(result);
	}

	/**
	 * Get the memory/static_max field of the given VM.
	 * 
	 * @return value of the field
	 */
	public Long getMemoryStaticMax(Connection c) throws BadServerResponse,
			XenAPIException, XmlRpcException {
		String method_call = "VM.get_memory_static_max";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toLong(result);
	}

	/**
	 * Get the memory/dynamic_max field of the given VM.
	 * 
	 * @return value of the field
	 */
	public Long getMemoryDynamicMax(Connection c) throws BadServerResponse,
			XenAPIException, XmlRpcException {
		String method_call = "VM.get_memory_dynamic_max";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toLong(result);
	}

	/**
	 * Get the memory/dynamic_min field of the given VM.
	 * 
	 * @return value of the field
	 */
	public Long getMemoryDynamicMin(Connection c) throws BadServerResponse,
			XenAPIException, XmlRpcException {
		String method_call = "VM.get_memory_dynamic_min";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toLong(result);
	}

	/**
	 * Get the memory/static_min field of the given VM.
	 * 
	 * @return value of the field
	 */
	public Long getMemoryStaticMin(Connection c) throws BadServerResponse,
			XenAPIException, XmlRpcException {
		String method_call = "VM.get_memory_static_min";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toLong(result);
	}

	/**
	 * Get the VCPUs/max field of the given VM.
	 * 
	 * @return value of the field
	 */
	public Long getVCPUsMax(Connection c) throws BadServerResponse,
			XenAPIException, XmlRpcException {
		String method_call = "VM.get_VCPUs_max";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toLong(result);
	}

	/**
	 * Get the consoles field of the given VM.
	 * 
	 * @return value of the field
	 */
	public Set<Console> getConsoles(Connection c) throws BadServerResponse,
			XenAPIException, XmlRpcException {
		String method_call = "VM.get_consoles";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toSetOfConsole(result);
	}

	/**
	 * Get the VIFs field of the given VM.
	 * 
	 * @return value of the field
	 */
	public Set<VIF> getVIFs(Connection c) throws BadServerResponse,
			XenAPIException, XmlRpcException {
		String method_call = "VM.get_VIFs";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toSetOfVIF(result);
	}

	public VIF.Record getVIFRecord(Connection c, VIF vif)
			throws BadServerResponse, XenAPIException, XmlRpcException {
		String method_call = "VM.get_VIF_record";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref), Marshalling.toXMLRPC(vif) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toVIFRecord(result);
	}

	/**
	 * Get the VBDs field of the given VM.
	 * 
	 * @return value of the field
	 */
	public Set<VBD> getVBDs(Connection c) throws BadServerResponse,
			XenAPIException, XmlRpcException {
		String method_call = "VM.get_VBDs";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toSetOfVBD(result);
	}

	public VBD.Record getVBDRecord(Connection c, VBD vbd)
			throws BadServerResponse, XenAPIException, XmlRpcException {
		String method_call = "VM.get_VBD_record";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref), Marshalling.toXMLRPC(vbd) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toVBDRecord(result);
	}

	/**
	 * Get the VBDs field of the given VM.
	 * 
	 * @return value of the field
	 */
	public String getAvailableVbdDevice(Connection c) throws BadServerResponse,
			XenAPIException, XmlRpcException {
		String method_call = "VM.get_available_vbd_device";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toString(result);
	}

	/**
	 * Get the Fibers field of the given VM.
	 * 
	 * @return value of the field
	 */
	public Set<VBD> getFibers(Connection c) throws BadServerResponse,
			XenAPIException, XmlRpcException {
		String method_call = "VM.get_fibers";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toSetOfVBD(result);
	}
	
	/**
	 * Get the USB field of the given VM.
	 * 
	 * @return value of the field
	 */
	public Set<VBD> getUsbScsi(Connection c) throws BadServerResponse,
			XenAPIException, XmlRpcException {
		String method_call = "VM.get_usb_scsi";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toSetOfVBD(result);
	}

	/**
	 * Get cd-rom field of the given VM.
	 * 
	 * @author Frankee
	 * 
	 */
	public VBD getMedia(Connection c) throws BadServerResponse,
			XenAPIException, XmlRpcException {
		String method_call = "VM.get_media";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toVBD(result);
	}

	/**
	 * Change cd-rom for given VM.
	 * 
	 * @author Frankee
	 * 
	 */
	public void mediaChange(Connection c, String vdi_name)
			throws BadServerResponse, XenAPIException, XmlRpcException {
		String method_call = "VM.media_change";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref), Marshalling.toXMLRPC(vdi_name) };
		c.dispatch(method_call, method_params);
		return;
	}

	/**
	 * Get the other_config field of the given VM.
	 * 
	 * @return value of the field
	 */
	public Map<String, String> getOtherConfig(Connection c)
			throws BadServerResponse, XenAPIException, XmlRpcException {
		String method_call = "VM.get_other_config";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toMapOfStringString(result);
	}

	/**
	 * Get vlan number of VM. add by wufan 20131015
	 * 
	 * @return value of the field
	 */
	public String getTag(Connection c, VIF vif_ref) throws BadServerResponse,
			XenAPIException, XmlRpcException {
		String method_call = "VM.get_tag";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref), Marshalling.toXMLRPC(vif_ref) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toString(result);
	}

	/**
	 * Get the domid field of the given VM.
	 * 
	 * @return value of the field
	 */
	public Long getDomid(Connection c) throws BadServerResponse,
			XenAPIException, XmlRpcException {
		String method_call = "VM.get_domid";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toLong(result);
	}

	/**
	 * Get the is_control_domain field of the given VM.
	 * 
	 * @return value of the field
	 */
	public Boolean getIsControlDomain(Connection c) throws BadServerResponse,
			XenAPIException, XmlRpcException {
		String method_call = "VM.get_is_control_domain";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toBoolean(result);
	}

	/**
	 * Get the snapshots field of the given VM.
	 * 
	 * @return value of the field
	 */
	public Set<String> getSnapshots(Connection c) throws BadServerResponse,
			XenAPIException, XmlRpcException {
		String method_call = "VM.get_snapshots";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toSetOfString(result);
	}

	/**
	 * Get the vm snapshot policy
	 * 
	 * @throws XmlRpcException
	 * @throws XenAPIException
	 * 
	 */
	public Set<String> getSnapshotPolicy(Connection c)
			throws BadServerResponse, XenAPIException, XmlRpcException {
		String method_call = "VM.get_snapshot_policy";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toSetOfString(result);
	}

	/**
	 * Set the vm snapshot policy
	 * 
	 * @throws XmlRpcException
	 * @throws XenAPIException
	 */

	public boolean setSnapshotPolicy(Connection c, String period,
			String reverseNumber) throws BadServerResponse, XenAPIException,
			XmlRpcException {
		String method_call = "VM.set_snapshot_policy";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref), Marshalling.toXMLRPC(period),
				Marshalling.toXMLRPC(reverseNumber) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toBoolean(result);
	}

	/**
	 * Set the name/label field of the given VM.
	 * 
	 * @param label
	 *            New value to set
	 */
	public void setNameLabel(Connection c, String label)
			throws BadServerResponse, XenAPIException, XmlRpcException {
		String method_call = "VM.set_name_label";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref), Marshalling.toXMLRPC(label) };
		c.dispatch(method_call, method_params);
		return;
	}

	/**
	 * Set the name/description field of the given VM.
	 * 
	 * @param description
	 *            New value to set
	 */
	public void setNameDescription(Connection c, String description)
			throws BadServerResponse, XenAPIException, XmlRpcException {
		String method_call = "VM.set_name_description";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref),
				Marshalling.toXMLRPC(description) };
		c.dispatch(method_call, method_params);
		return;
	}

	/**
	 * Set the is_a_template field of the given VM.
	 * 
	 * @param isATemplate
	 *            New value to set
	 */
	public void setIsATemplate(Connection c, Boolean isATemplate)
			throws BadServerResponse, XenAPIException, XmlRpcException {
		String method_call = "VM.set_is_a_template";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref),
				Marshalling.toXMLRPC(isATemplate) };
		c.dispatch(method_call, method_params);
		return;
	}

	/**
	 * Set the other_config field of the given VM.
	 * 
	 * @param otherConfig
	 *            New value to set
	 */
	public void setOtherConfig(Connection c, Map<String, String> otherConfig)
			throws BadServerResponse, XenAPIException, XmlRpcException {
		String method_call = "VM.set_other_config";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref),
				Marshalling.toXMLRPC(otherConfig) };
		c.dispatch(method_call, method_params);
		return;
	}

	/**
	 * Add the given key-value pair to the other_config field of the given VM.
	 * 
	 * @param key
	 *            Key to add
	 * @param value
	 *            Value to add
	 */
	public void addToOtherConfig(Connection c, String key, String value)
			throws BadServerResponse, XenAPIException, XmlRpcException {
		String method_call = "VM.add_to_other_config";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref), Marshalling.toXMLRPC(key),
				Marshalling.toXMLRPC(value) };
		c.dispatch(method_call, method_params);
		return;
	}

	/**
	 * Change vlan number of VM闂佽法鍠愰弸濠氬箯閿燂拷 add by wufan : 20131015
	 * 
	 */
	public void setTag(Connection c, VIF vif, String value)
			throws BadServerResponse, XenAPIException, XmlRpcException {
		String method_call = "VM.set_tag";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref), Marshalling.toXMLRPC(vif),
				Marshalling.toXMLRPC(value) };
		c.dispatch(method_call, method_params);
		return;
	}
	public void setTag(Connection c, VIF vif, String value, String ovs)
			throws BadServerResponse, XenAPIException, XmlRpcException {
		String method_call = "VM.set_tag";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref), Marshalling.toXMLRPC(vif),
				Marshalling.toXMLRPC(value), Marshalling.toXMLRPC(ovs) };
		c.dispatch(method_call, method_params);
		return;
	}

	/**
	 * Remove the given key and its corresponding value from the other_config
	 * field of the given VM. If the key is not in that Map, then do nothing.
	 * 
	 * @param key
	 *            Key to remove
	 */
	public void removeFromOtherConfig(Connection c, String key)
			throws BadServerResponse, XenAPIException, XmlRpcException {
		String method_call = "VM.remove_from_other_config";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref), Marshalling.toXMLRPC(key) };
		c.dispatch(method_call, method_params);
		return;
	}

	/**
	 * Snapshots the specified VM, making a new VM. Snapshot automatically
	 * exploits the capabilities of the underlying storage repository in which
	 * the VM's disk images are stored (e.g. Copy on Write).
	 * 
	 * @param newName
	 *            The name of the snapshotted VM
	 * @return The reference of the newly created VM.
	 */
	public Boolean snapshot(Connection c, String newName)
			throws BadServerResponse, XenAPIException, XmlRpcException,
			Types.VmBadPowerState, Types.SrFull, Types.OperationNotAllowed {
		String method_call = "VM.snapshot";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref), Marshalling.toXMLRPC(newName) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toBoolean(result);
	}

	public Boolean rollback(Connection c, String newName)
			throws BadServerResponse, XenAPIException, XmlRpcException,
			Types.VmBadPowerState, Types.SrFull, Types.OperationNotAllowed {
		String method_call = "VM.rollback";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref), Marshalling.toXMLRPC(newName) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toBoolean(result);
	}

	/**
	 * Clones the specified VM, making a new VM. Clone automatically exploits
	 * the capabilities of the underlying storage repository in which the VM's
	 * disk images are stored (e.g. Copy on Write). This function can only be
	 * called when the VM is in the Halted State.
	 * 
	 * @param newName
	 *            The name of the cloned VM
	 * @return The reference of the newly created VM.
	 */
	public VM createClone(Connection c, String newName)
			throws BadServerResponse, XenAPIException, XmlRpcException,
			Types.VmBadPowerState, Types.SrFull, Types.OperationNotAllowed {
		String method_call = "VM.clone";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref), Marshalling.toXMLRPC(newName) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toVM(result);
	}

	/**
	 * Start the specified VM. This function can only be called with the VM is
	 * in the Halted State.
	 * 
	 * @param startPaused
	 *            Instantiate VM in paused state if set to true.
	 * @param force
	 *            Attempt to force the VM to start. If this flag is false then
	 *            the VM may fail pre-boot safety checks (e.g. if the CPU the VM
	 *            last booted on looks substantially different to the current
	 *            one)
	 */
	public void start(Connection c, Boolean startPaused, Boolean force)
			throws BadServerResponse, XenAPIException, XmlRpcException,
			Types.VmBadPowerState, Types.VmHvmRequired, Types.VmIsTemplate,
			Types.OtherOperationInProgress, Types.OperationNotAllowed,
			Types.BootloaderFailed, Types.UnknownBootloader,
			Types.NoHostsAvailable, Types.LicenceRestriction {
		String method_call = "VM.start";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref),
				Marshalling.toXMLRPC(startPaused), Marshalling.toXMLRPC(force) };
		c.dispatch(method_call, method_params);
		return;
	}

	/**
	 * Start the specified VM on a particular host. This function can only be
	 * called with the VM is in the Halted State.
	 * 
	 * @param host
	 *            The Host on which to start the VM
	 * @param startPaused
	 *            Instantiate VM in paused state if set to true.
	 * @param force
	 *            Attempt to force the VM to start. If this flag is false then
	 *            the VM may fail pre-boot safety checks (e.g. if the CPU the VM
	 *            last booted on looks substantially different to the current
	 *            one)
	 */
	public void startOn(Connection c, Host host, Boolean startPaused,
			Boolean force) throws BadServerResponse, XenAPIException,
			XmlRpcException, Types.VmBadPowerState, Types.VmIsTemplate,
			Types.OtherOperationInProgress, Types.OperationNotAllowed,
			Types.BootloaderFailed, Types.UnknownBootloader {
		String method_call = "VM.start_on";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref), Marshalling.toXMLRPC(host),
				Marshalling.toXMLRPC(startPaused), Marshalling.toXMLRPC(force) };
		c.dispatch(method_call, method_params);
		return;
	}

	/**
	 * Pause the specified VM. This can only be called when the specified VM is
	 * in the Running state.
	 * 
	 */
	public void pause(Connection c) throws BadServerResponse, XenAPIException,
			XmlRpcException, Types.VmBadPowerState,
			Types.OtherOperationInProgress, Types.OperationNotAllowed,
			Types.VmIsTemplate {
		String method_call = "VM.pause";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref) };
		c.dispatch(method_call, method_params);
		return;
	}

	/**
	 * Resume the specified VM. This can only be called when the specified VM is
	 * in the Paused state.
	 * 
	 */
	public void unpause(Connection c) throws BadServerResponse,
			XenAPIException, XmlRpcException, Types.VmBadPowerState,
			Types.OperationNotAllowed, Types.VmIsTemplate {
		String method_call = "VM.unpause";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref) };
		c.dispatch(method_call, method_params);
		return;
	}

	/**
	 * Attempt to cleanly shutdown the specified VM. (Note: this may not be
	 * supported---e.g. if a guest agent is not installed). This can only be
	 * called when the specified VM is in the Running state.
	 * 
	 * If there's some error with the connection please
	 * 
	 * @see Connection class's comment
	 * @throws BadServerResponse
	 *             , @see BadServerResponse
	 * @throws XenAPIException
	 *             , Just the parent class of all the Xen Exception
	 * @throws XmlRpcException
	 * @throws Types.VmBadPowerState
	 *             , happen when the vm's power state is power-off
	 * @throws Types.OperationNotAllowed
	 *             , if vm is Domain-0, @see OperationNotAllowed
	 * @throws Types.VmIsTemplate
	 *             ,if vm is template
	 */
	public Boolean cleanShutdown(Connection c) throws BadServerResponse,
			XenAPIException, XmlRpcException, Types.VmBadPowerState,
			Types.OperationNotAllowed, Types.VmIsTemplate {
		String method_call = "VM.clean_shutdown";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toBoolean(result);
	}

	/**
	 * Attempt to cleanly shutdown the specified VM (Note: this may not be
	 * supported---e.g. if a guest agent is not installed). This can only be
	 * called when the specified VM is in the Running state.
	 * 
	 */
	public Boolean cleanReboot(Connection c) throws BadServerResponse,
			XenAPIException, XmlRpcException, Types.VmBadPowerState,
			Types.OtherOperationInProgress, Types.OperationNotAllowed,
			Types.VmIsTemplate {
		String method_call = "VM.clean_reboot";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toBoolean(result);
	}

	/**
	 * Stop executing the specified VM without attempting a clean shutdown.
	 * 
	 */
	public void hardShutdown(Connection c) throws BadServerResponse,
			XenAPIException, XmlRpcException, Types.VmBadPowerState,
			Types.OtherOperationInProgress, Types.OperationNotAllowed,
			Types.VmIsTemplate {
		String method_call = "VM.hard_shutdown";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref) };
		c.dispatch(method_call, method_params);
		return;
	}

	/**
	 * Stop executing the specified VM without attempting a clean shutdown and
	 * immediately restart the VM.
	 * 
	 */
	public void hardReboot(Connection c) throws BadServerResponse,
			XenAPIException, XmlRpcException, Types.VmBadPowerState,
			Types.OtherOperationInProgress, Types.OperationNotAllowed,
			Types.VmIsTemplate {
		String method_call = "VM.hard_reboot";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref) };
		c.dispatch(method_call, method_params);
		return;
	}

	/**
	 * Migrate a VM to another Host. This can only be called when the specified
	 * VM is in the Running state.
	 * 
	 * @param host
	 *            The target host
	 * @param options
	 *            Extra configuration operations
	 */
	public void poolMigrate(Connection c, Host host, Map<String, String> options)
			throws BadServerResponse, XenAPIException, XmlRpcException,
			Types.VmBadPowerState, Types.OtherOperationInProgress,
			Types.VmIsTemplate, Types.OperationNotAllowed,
			Types.VmMigrateFailed, Types.VmMissingPvDrivers {
		String method_call = "VM.pool_migrate";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref), Marshalling.toXMLRPC(host),
				Marshalling.toXMLRPC(options) };
		c.dispatch(method_call, method_params);
		return;
	}

	/**
	 * Set the number of VCPUs for a running VM
	 * 
	 * @param nvcpu
	 *            The number of VCPUs
	 */
	public void setVCPUsNumberLive(Connection c, Long nvcpu)
			throws BadServerResponse, XenAPIException, XmlRpcException {
		String method_call = "VM.set_VCPUs_number_live";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref), Marshalling.toXMLRPC(nvcpu) };
		c.dispatch(method_call, method_params);
		return;
	}

	/**
	 * Set the value of the memory_dynamic_max field
	 * 
	 * @param value
	 *            The new value of memory_dynamic_max
	 */
	public void setMemoryDynamicMax(Connection c, Long value)
			throws BadServerResponse, XenAPIException, XmlRpcException {
		String method_call = "VM.set_memory_dynamic_max";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref), Marshalling.toXMLRPC(value) };
		c.dispatch(method_call, method_params);
		return;
	}

	// frank
	public void setMemoryDynamicMaxLive(Connection c, Long value)
			throws BadServerResponse, XenAPIException, XmlRpcException {
		String method_call = "VM.set_memory_dynamic_max_live";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref), Marshalling.toXMLRPC(value) };
		c.dispatch(method_call, method_params);
		return;
	}

	/**
	 * Set the value of the memory_dynamic_min field
	 * 
	 * @param value
	 *            The new value of memory_dynamic_min
	 */
	public void setMemoryDynamicMin(Connection c, Long value)
			throws BadServerResponse, XenAPIException, XmlRpcException {
		String method_call = "VM.set_memory_dynamic_min";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref), Marshalling.toXMLRPC(value) };
		c.dispatch(method_call, method_params);
		return;
	}

	/**
	 * Set the value of the memory_static_max field
	 * 
	 * @param value
	 *            The new value of memory_static_max
	 */
	public void setMemoryStaticMax(Connection c, Long value)
			throws BadServerResponse, XenAPIException, XmlRpcException,
			Types.HaOperationWouldBreakFailoverPlan {
		String method_call = "VM.set_memory_static_max";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref), Marshalling.toXMLRPC(value) };
		c.dispatch(method_call, method_params);
		return;
	}

	/**
	 * Set the value of the memory_static_min field
	 * 
	 * @param value
	 *            The new value of memory_static_min
	 */
	public void setMemoryStaticMin(Connection c, Long value)
			throws BadServerResponse, XenAPIException, XmlRpcException {
		String method_call = "VM.set_memory_static_min";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref), Marshalling.toXMLRPC(value) };
		c.dispatch(method_call, method_params);
		return;
	}

	/**
	 * Set the maximum number of VCPUs for a halted VM
	 * 
	 * @param value
	 *            The new maximum number of VCPUs
	 */
	public void setVCPUsMax(Connection c, Long value) throws BadServerResponse,
			XenAPIException, XmlRpcException {
		String method_call = "VM.set_VCPUs_max";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref), Marshalling.toXMLRPC(value) };
		c.dispatch(method_call, method_params);
		return;
	}

	/**
	 * Set the number of startup VCPUs for a halted VM
	 * 
	 * @param value
	 *            The new maximum number of VCPUs
	 */
	public void setVCPUsAtStartup(Connection c, Long value)
			throws BadServerResponse, XenAPIException, XmlRpcException {
		String method_call = "VM.set_VCPUs_at_startup";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref), Marshalling.toXMLRPC(value) };
		c.dispatch(method_call, method_params);
		return;
	}

	/**
	 * Return a list of all the VMs known to the system.
	 * 
	 * @return references to all objects
	 */
	public static Set<VM> getAll(Connection c) throws BadServerResponse,
			XenAPIException, XmlRpcException {
		String method_call = "VM.get_all";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toSetOfVM(result);
	}

	/**
	 * Return a map of VM references to VM records for all VMs known to the
	 * system.
	 * 
	 * @return records of all objects
	 */
	public static Map<VM, VM.Record> getAllRecords(Connection c)
			throws BadServerResponse, XenAPIException, XmlRpcException {
		String method_call = "VM.get_all_records";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toMapOfVMVMRecord(result);
	}

	/**
	 * Return a map of VM sxp file path to VM name label for all VMs known to
	 * the system.
	 * 
	 * @return all lost vms
	 */
	public static Map<String, String> getLostVMByLabel(Connection c,
			String label, Boolean exactMatch) throws BadServerResponse,
			XenAPIException, XmlRpcException {
		String method_call = "VM.get_lost_vm_by_label";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(label), Marshalling.toXMLRPC(exactMatch) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toMapOfStringString(result);
	}

	/**
	 * Return a map of VM sxp file path to VM name label for all VMs known to
	 * the system.
	 * 
	 * @return all lost vms
	 */
	public static Map<String, String> getLostVMByDate(Connection c,
			String startDate, String endDate) throws BadServerResponse,
			XenAPIException, XmlRpcException {
		String method_call = "VM.get_lost_vm_by_date";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(startDate), Marshalling.toXMLRPC(endDate) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toMapOfStringString(result);
	}

	/*
	 * boot_order娑擄拷"dc"闁跨喐鏋婚幏椋庛仛闁跨喐甯撮惂鍛婂闁跨喐鏋婚幏鐑芥晸閺傘倖瀚归柨鐔告灮閹凤拷
	 * 闁跨喐鍩呮导娆愬閻樿埖锟戒線鏁撻柧鐗堟暭閹插瀚归柨鐔告灮閹风兘鏁撻弬銈嗗闁跨喎褰ㄩ敓锟�
	 */
	public void setBootOrder(Connection c, String boot_order)
			throws BadServerResponse, XenAPIException, XmlRpcException {
		String method_call = "VM.set_boot_order";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref),
				Marshalling.toXMLRPC(boot_order) };
		c.dispatch(method_call, method_params);
		return;
	}

	/**
	 * Change rate and burst of VM. add by wufan : 20140102 type: "rate": set
	 * rate "burst": set burst
	 */
	public void setNetworkIORate(Connection c, String type, VIF vif,
			String value) throws BadServerResponse, XenAPIException,
			XmlRpcException {
		String method_call = "VM.set_rate";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref), Marshalling.toXMLRPC(type),
				Marshalling.toXMLRPC(vif), Marshalling.toXMLRPC(value) };
		c.dispatch(method_call, method_params);
		return;
	}

	/**
	 * Get rate of VM. add by wufan 20131015
	 * 
	 * @return value of the field
	 */
	public String getNetworkIORate(Connection c, String type, VIF vif_ref)
			throws BadServerResponse, XenAPIException, XmlRpcException {
		String method_call = "VM.get_rate";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref), Marshalling.toXMLRPC(type),
				Marshalling.toXMLRPC(vif_ref) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toString(result);
	}

	/**
	 * Get ip map of VM. add by wuyuewen 20140228
	 * 
	 * @return value of the field
	 * 
	 */
	public String getIpMap(Connection c) throws BadServerResponse,
			XenAPIException, XmlRpcException {
		String method_call = "VM.get_ip_map";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toString(result);
	}

	/**
	 * Set ip map of VM. add by wuyuewen
	 */
	public void setIpMap(Connection c, VIF vif) throws BadServerResponse,
			XenAPIException, XmlRpcException {
		String method_call = "VM.set_ip_map";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref), Marshalling.toXMLRPC(vif) };
		c.dispatch(method_call, method_params);
		return;
	}

	/**
	 * Del ip map of VM.
	 * 
	 * @param c
	 * @param VIF
	 * @throws BadServerResponse
	 * @throws XenAPIException
	 * @throws XmlRpcException
	 */
	public void delIpMap(Connection c, VIF vif) throws BadServerResponse,
			XenAPIException, XmlRpcException {
		String method_call = "VM.del_ip_map";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref), Marshalling.toXMLRPC(vif) };
		c.dispatch(method_call, method_params);
		return;
	}

	/*
	 * Set VM disk IO rate by cgroup, can set both read/write rate(MBps)
	 */
	public void setDiskIORateLimit(Connection c, String type, String value,
			String ioType) throws BadServerResponse, XenAPIException,
			XmlRpcException {
		String method_call = "VM.set_IO_rate_limit";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref), Marshalling.toXMLRPC(type),
				Marshalling.toXMLRPC(value), Marshalling.toXMLRPC(ioType) };
		c.dispatch(method_call, method_params);
		return;
	}

	/*
	 * Get VM disk IO rate by cgroup, rate(MBps)
	 */
	public int getDiskIORateLimit(Connection c, String type, String ioType)
			throws BadServerResponse, XenAPIException, XmlRpcException {
		String method_call = "VM.get_IO_rate_limit";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref), Marshalling.toXMLRPC(type),
				Marshalling.toXMLRPC(ioType) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Integer.parseInt((String) (result));
	}

	/*
	 * 濠电偞鎸搁幊妯衡枍鎼淬劍鎯炴慨姗嗗幖閻濐垶鏌￠崼銉х窗妞ゆ梹鐗犲濠氼敊闁款垽鎷烽弽顓熷剮婵鎷锋俊鐐そ閺屽懘鎮ら崒婊咁槷 type闂佹寧鍐婚幏锟� read 闂佸憡绮ｉ幏锟� write
	 */
	public void clearIORateLimit(Connection c, String type, String ioType)
			throws BadServerResponse, XenAPIException, XmlRpcException {
		String method_call = "VM.clear_IO_rate_limit";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref), Marshalling.toXMLRPC(type),
				Marshalling.toXMLRPC(ioType) };
		c.dispatch(method_call, method_params);
		return;
	}

	/**
	 * Get inner_ip map of VM. add by wuyuewen 20140228
	 * 
	 * @return value of the field
	 * 
	 */
	public Map<String, String> getInnerIpMap(Connection c)
			throws BadServerResponse, XenAPIException, XmlRpcException {
		String method_call = "VM.get_inner_ip";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");

		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) result;
		return map;
	}

	/*
	 * 闁荤姳绀佹晶浠嬫偪閸℃稒鎯炴慨姗嗗幖閻濐垶鏌￠崼婵囨儓闁伙附鍨块幆宥夋晸閿燂拷
	 */
	public boolean setPasswd(Connection c, String vm_ip, String newPasswd,
			String oldPasswd, String type) throws BadServerResponse,
			XenAPIException, XmlRpcException {
		String method_call = "VM.set_passwd";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref), Marshalling.toXMLRPC(vm_ip),
				Marshalling.toXMLRPC(newPasswd),
				Marshalling.toXMLRPC(oldPasswd), Marshalling.toXMLRPC(type) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toBoolean(result);
	}

	/*
	 * if it's a pool, connection is master, and host is in the pool if not a
	 * pool, connection and host must be refer to the same host
	 */

	public static VM.Record createOnFromTemplate(Connection c, Host host,
			String templateUuid, String newName, Map<String, Object> conf,
			Boolean ping) throws XenAPIException, XmlRpcException {
			String method_call = "VM.create_on_from_template";
			String session = c.getSessionReference();
			Object[] method_params = { Marshalling.toXMLRPC(session),
					Marshalling.toXMLRPC(host),
					Marshalling.toXMLRPC(templateUuid),
					Marshalling.toXMLRPC(newName), Marshalling.toXMLRPC(conf),
					Marshalling.toXMLRPC(ping) };
			@SuppressWarnings("rawtypes")
			Map response = c.dispatch(method_call, method_params);
			Object result = response.get("Value");
			return Types.toVMRecord(result);
	}

	/*
	 * create a template from a VM no mater what state the VM is
	 */
	public Boolean createImage(Connection c, String templateUuid)
			throws XenAPIException, XmlRpcException {
		String templateName = "img-" + templateUuid.substring(0, 8);
		String method_call = "VM.create_image";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref),
				Marshalling.toXMLRPC(templateName),
				Marshalling.toXMLRPC(templateUuid) };
		c.dispatch(method_call, method_params);
		return true;

	}

	/**
	 * Create a data VBD link to a exists data VDI.
	 * 
	 * @return all lost vms
	 */
	public static Boolean createDataVBD(Connection c, String vm_uuid,
			String vdi_uuid) throws BadServerResponse, XenAPIException,
			XmlRpcException {
		String method_call = "VM.create_data_VBD";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(vm_uuid), Marshalling.toXMLRPC(vdi_uuid) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toBoolean(result);
	}

	/**
	 * Delete a data VBD and unlink data VDI.
	 * 
	 * @return all lost vms
	 */
	public static Boolean deleteDataVBD(Connection c, String vm_uuid,
			String vdi_uuid) throws BadServerResponse, XenAPIException,
			XmlRpcException {
		String method_call = "VM.delete_data_VBD";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(vm_uuid), Marshalling.toXMLRPC(vdi_uuid) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toBoolean(result);
	}

	/**
	 * Get system disk VDI.
	 * 
	 * @return vdi.
	 */
	public VDI getSystemVDI(Connection c) throws BadServerResponse,
			XenAPIException, XmlRpcException {
		String method_call = "VM.get_system_VDI";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toVDI(result);
	}

	/**
	 * Clone VM's system VDI with newuuid.
	 * 
	 */
	public static VDI cloneSystemVDI(Connection c, String template_uuid,
			String newuuid) throws BadServerResponse, XenAPIException,
			XmlRpcException {
		String method_call = "VM.clone_system_VDI";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(template_uuid),
				Marshalling.toXMLRPC(newuuid) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toVDI(result);
	}

	/**
	 * if it's a pool, connection is master, and host is in the pool if not a
	 * pool, connection and host must be refer to the same host
	 * 
	 * Create VM with VDI already exists.
	 * @throws XmlRpcException 
	 * @throws XenAPIException 
	 * 
	 */

	public static VM.Record createWithVDI(Connection c, Host host,
			String templateUuid, String newName, Map<String, Object> conf,
			Boolean ping) throws XenAPIException, XmlRpcException {
			String method_call = "VM.create_with_VDI";
			String session = c.getSessionReference();
			Object[] method_params = { Marshalling.toXMLRPC(session),
					Marshalling.toXMLRPC(host),
					Marshalling.toXMLRPC(templateUuid),
					Marshalling.toXMLRPC(newName), Marshalling.toXMLRPC(conf),
					Marshalling.toXMLRPC(ping) };
			@SuppressWarnings("rawtypes")
			Map response = c.dispatch(method_call, method_params);
			Object result = response.get("Value");
			return Types.toVMRecord(result);
	}

	/*
	 * 闁告帗鐟ょ槐鍫曞礂閵夈儱妫橀柡渚�顣︾拹锟�10 memory濞戞挸鎼崬瀵革拷娑櫭妵鍥╀焊韫囥儳绀夐柛妤佹磻缂嶅懏绋夌弧妗�,4G 闁告帗鐟ょ槐鍫曞礂閵夈儱妫橀柡渚�顣︾拹锟�4096
	 * vcpu濞戞挾顕玴u濞戞搩浜濋弳锟�
	 */

	public static VM.Record createVMFromISO(String vmUuid, String vmName,
			long vcpu, long memory, Connection connection, String hostUuid,
			String isoUuid, long storage, String diskUuid, String srType)
					throws XenAPIException, XmlRpcException  {
			Host host = Types.toHost(hostUuid);
			SR sr = Types.toSR(diskUuid);
			DiskInfo diskInfo = new DiskInfo(storage, sr, srType);
			ArrayList<DiskInfo> diskList = new ArrayList<DiskInfo>();
			diskList.add(diskInfo);
			VM.Record newVM = VMUtil.createWithUuid(vmUuid, vmName, vcpu,
					memory, connection, host, true, isoUuid, diskList);
			return newVM;
	}

	/*
	 * 闂佹儳绻戠喊宥囷拷姘懇瀵敻宕崟鍨緮闂佸憡姊绘慨宕囨啺閸℃稑鐭楅柨婵嗙箳缁�澶娒归敐鍫熺《闁轰降鍊濆鍫曞灳閸欏鍋ㄩ梺鎸庣♁娣囧搫鈻嶉崟顖氱闁绘柨鎲＄花姘舵煕韫囨柨鈻曟俊顐ｆ尦閺佸秶浠﹂懞銉ュ墾闂佸憡甯楅悷锕�
	 * 螞閵堝應鏋栭柡鍥╁У閺呮悂鏌℃笟濠冨
	 */
	public boolean setSerial(Connection c) throws XenAPIException, XmlRpcException {
			String method_call = "VM.set_platform_serial";
			String session = c.getSessionReference();
			Object[] method_params = { Marshalling.toXMLRPC(session),
					Marshalling.toXMLRPC(this.ref) };
			c.dispatch(method_call, method_params);
			return true;
	}

	/*
	 * 闂佸吋鍎抽崲鑼闁秵鎯炴慨姗嗗幖閻濐垰鈽夐幘鏉戠祷鐟滅増鐩弻鍛潩椤愶紕绠�
	 */
	public String getSerial(Connection c) throws XenAPIException, XmlRpcException {
			String method_call = "VM.get_platform_serial";
			String session = c.getSessionReference();
			Object[] method_params = { Marshalling.toXMLRPC(session),
					Marshalling.toXMLRPC(this.ref) };
			@SuppressWarnings("rawtypes")
			Map response = c.dispatch(method_call, method_params);
			Object result = response.get("Value");
			return Types.toString(result);
	}

	/**
	 * Add a new interface in VM via serial.
	 * 
	 */
	public boolean sendRequestViaSerial(Connection c, String json_obj,
			boolean rsync) throws BadServerResponse, XenAPIException,
			XmlRpcException {
		String method_call = "VM.send_request_via_serial";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref), Marshalling.toXMLRPC(json_obj),
				Marshalling.toXMLRPC(rsync) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toBoolean(result);

	}

	/**
	 * Get Network record by VIF.
	 * 
	 */
	public Network.Record getNetworkRecord(Connection c, VIF vif)
			throws BadServerResponse, XenAPIException, XmlRpcException {
		String method_call = "VM.get_network_record";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref), Marshalling.toXMLRPC(vif) };
		@SuppressWarnings("rawtypes")
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toNetworkRecord(result);

	}

}