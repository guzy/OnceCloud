package org.Xen.API;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.xmlrpc.XmlRpcException;
import org.Xen.API.Types.BadServerResponse;
import org.Xen.API.Types.XenAPIException;

public class VMCPUQoS extends XenAPIObject {

	protected final String ref;

	VMCPUQoS(String ref) {
		this.ref = ref;
	}

	@Override
	public String toWireString() {
		return this.ref;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof VMCPUQoS) {
			VMCPUQoS other = (VMCPUQoS) obj;
			return other.ref.equals(this.ref);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return ref.hashCode();
	}

	public static class Record implements Types.Record {
		public String toString() {
			StringWriter writer = new StringWriter();
			PrintWriter print = new PrintWriter(writer);
			print.printf("%1$20s: %2$s\n", "uuid", this.uuid);
			print.printf("%1$20s: %2$s\n", "VCPUsNumber", this.VCPUsNumber);
			print.printf("%1$20s: %2$s\n", "VCPUsCPU", this.VCPUsCPU);
			print.printf("%1$20s: %2$s\n", "CPUAffinity", this.CPUAffinity);
			return writer.toString();
		}

		public Map<String, Object> toMap() {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("uuid", this.uuid == null ? "" : this.uuid);
			map.put("VCPUs_number", this.VCPUsNumber == null ? 0
					: this.VCPUsNumber);
			map.put("VCPUs_CPU",
					this.VCPUsCPU == null ? new HashMap<Long, Long>()
							: this.VCPUsCPU);
			map.put("CPU_Affinity",
					this.CPUAffinity == null ? new HashMap<String, String>()
							: this.CPUAffinity);
			return map;
		}

		/**
		 * Unique identifier/object reference
		 */
		public String uuid;
		/**
		 * Current number of VCPUs
		 */
		public Long VCPUsNumber;
		/**
		 * VCPU to PCPU map
		 */
		public Map<Long, Long> VCPUsCPU;
		/**
		 * The live equivalent to VM.VCPUs_params
		 */
		public Map<String, String> CPUAffinity;
	}

	@SuppressWarnings("rawtypes")
	public VMCPUQoS.Record getRecord(Connection c) throws BadServerResponse,
			XenAPIException, XmlRpcException {
		String method_call = "VM_cpu_qos.get_record";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref) };
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toVMCPUQoSRecord(result);
	}

	@SuppressWarnings("rawtypes")
	public static VMCPUQoS getByUuid(Connection c, String uuid)
			throws BadServerResponse, XenAPIException, XmlRpcException {
		String method_call = "VM_cpu_qos.get_by_uuid";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(uuid) };
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toVMCPUQoS(result);
	}

	@SuppressWarnings("rawtypes")
	public String getUuid(Connection c) throws BadServerResponse,
			XenAPIException, XmlRpcException {
		String method_call = "VM_cpu_qos.get_uuid";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref) };
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toString(result);
	}

	@SuppressWarnings("rawtypes")
	public Long getVCPUsNumber(Connection c) throws BadServerResponse,
			XenAPIException, XmlRpcException {
		String method_call = "VM_cpu_qos.get_VCPUs_number";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref) };
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toLong(result);
	}

	@SuppressWarnings("rawtypes")
	public Map<Long, Long> getVCPUsCPU(Connection c) throws BadServerResponse,
			XenAPIException, XmlRpcException {
		String method_call = "VM_cpu_qos.get_VCPUs_CPU";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref) };
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toMapOfLongLong(result);
	}

	@SuppressWarnings("rawtypes")
	public Map<String, String> getVCPUsParams(Connection c)
			throws BadServerResponse, XenAPIException, XmlRpcException {
		String method_call = "VM_cpu_qos.get_CPU_Affinity";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref) };
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return Types.toMapOfStringString(result);
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	public void setVCPUsAffinity(Connection c, String vcpu,
			String cpus) throws BadServerResponse, XenAPIException,
			XmlRpcException {
		String method_call = "VM_cpu_qos.set_CPU_Affinity";
		String session = c.getSessionReference();
		Object[] method_params = { Marshalling.toXMLRPC(session),
				Marshalling.toXMLRPC(this.ref), Marshalling.toXMLRPC(vcpu),
				Marshalling.toXMLRPC(cpus) };
		Map response = c.dispatch(method_call, method_params);
		Object result = response.get("Value");
		return;
	}
}