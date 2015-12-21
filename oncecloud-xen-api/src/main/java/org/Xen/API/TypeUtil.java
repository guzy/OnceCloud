/**
 * CopyRight(2011- ) Insititute of Sofeware, Chinese Academy of Sciences
 */
package org.Xen.API;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wuheng
 * @email  wuheng09@gmail.com 
 * @date   2012-12-23
 *
 */ 
public class TypeUtil {

	public final static String nfsSign = "nfs";
	public final static String gpfsSign = "gpfs";
	
	public final static String haSign = "ha";
	public final static String isoSign = "iso";
	
	public final static String localSrType = "local";
	public final static String nfsZfsType = "nfs_zfs";
	public final static String nfsIsoType = "nfs_iso";
	public final static String nfsDiskType = "nfs_vhd";
	public final static String nfsHaType = "nfs_ha";
	public final static String gpfsDiskType = "gpfs";
	public final static String gpfsHaType = "gpfs_ha";
	public final static String gpfsIsoType = "gpfs_iso";
	public final static String mfsDiskType = "mfs";
	public final static String ocfs2DiskType = "ocfs2";
	public final static String localOcfs2DiskType = "local_ocfs2";
	public final static String cephDiskType = "ceph";
	
	public final static List<String> getAllSRTypes() {
		List<String> types = new ArrayList<String>();
		types.add(localSrType);
		types.add(nfsZfsType);
		types.add(nfsIsoType);
		types.add(nfsDiskType);
		types.add(nfsHaType);
		types.add(gpfsDiskType);
		types.add(gpfsHaType);
		types.add(gpfsIsoType);
		types.add(mfsDiskType);
		types.add(ocfs2DiskType);
		types.add(localOcfs2DiskType);
		types.add(cephDiskType);
		return types;
	}
	
	public final static List<String> getDiskSRTypes(){
		List<String> types = new ArrayList<String>();
		types.add(localSrType);
		types.add(nfsZfsType);
		types.add(nfsDiskType);
		types.add(gpfsDiskType);
		types.add(mfsDiskType);
		types.add(ocfs2DiskType);
		types.add(localOcfs2DiskType);
		types.add(cephDiskType);
		return types;
	}
	
	public final static List<String> getNfsSRTypes(){
		List<String> types = new ArrayList<String>();
		types.add(nfsZfsType);
		types.add(nfsIsoType);
		types.add(nfsDiskType);
		types.add(nfsHaType);
		return types;
	}
	
}
