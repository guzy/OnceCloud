package com.beyondsphere.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.beyondsphere.constants.LoggerConfig;
import com.beyondsphere.dao.ActionDAO;
import com.beyondsphere.dao.LogDAO;
import com.beyondsphere.dao.RoleActionDAO;
import com.beyondsphere.dao.RoleDAO;
import com.beyondsphere.dao.UserDAO;
import com.beyondsphere.entity.OCAction;
import com.beyondsphere.entity.OCLog;
import com.beyondsphere.entity.OCRole;
import com.beyondsphere.entity.RoleAction;
import com.beyondsphere.log.LogAction;
import com.beyondsphere.log.LogObject;
import com.beyondsphere.log.LogRecord;
import com.beyondsphere.log.LogRole;
import com.beyondsphere.message.MessageUtil;
import com.beyondsphere.model.AuthNode;
import com.beyondsphere.model.AuthorityType;
import com.beyondsphere.model.OperationResult;
import com.beyondsphere.model.PagedList;
import com.beyondsphere.service.RoleService;
import com.beyondsphere.util.TimeUtils;
import com.beyondsphere.vi.RoleActionView;

@Component("RoleService")
public class RoleServiceImpl implements RoleService {

	@Resource
	private UserDAO userDAO;
	@Resource
	private LogDAO logDAO;
	@Resource
	private RoleDAO roleDAO;
	@Resource
	private ActionDAO actionDAO;
	@Resource
	private RoleActionDAO roleActionDAO;
	@Resource
	private MessageUtil message;
	@Resource
	private LogRecord logRecord;
	@Resource
	private LoggerConfig loggerConfig;
	
	public PagedList<OCRole> getRoleList(int page, int limit, String search) {
		int totalCount = roleDAO.countAllRoleList(search);
		PagedList<OCRole> list = new PagedList<OCRole>(page, limit, search, totalCount);
		if (totalCount > 0) {
			list.setList(roleDAO.getPagedRoleList(page, limit, search));
		}
		return list;
	}

	public OCRole addRole(int userId, String roleName, String roleIntroduce,
			String roleRemarks) {
		Date startTime = new Date();
		OCRole result = roleRegister(roleName, roleIntroduce, roleRemarks);
		Date endTime = new Date();
		JSONArray infoArray = new JSONArray();
		infoArray.put(TimeUtils.createLogInfo(
				LogRole.ROLE, roleName));
		if (result != null) {
			OCLog log = logRecord.addSuccessLog(userId, LogObject.ROLE,
					LogAction.CREATE,
					infoArray.toString(), startTime, endTime); 
			message.pushSuccess(userId, log.toString());
		} else {
			OCLog log = logRecord.addFailedLog(userId, LogObject.ROLE,
					LogAction.CREATE,
					infoArray.toString(), startTime, endTime);
			message.pushError(userId, log.toString());
		}
		return result;
	}

	public OCRole getRole(String roleName) {
		return roleDAO.getRole(roleName);
	}

	public JSONObject deleteRole(int userId, int roleid, String roleName) {
		JSONObject jo = new JSONObject();
		Date startTime = new Date();
		boolean result =false;
		/// 删除之前要判断是否有用户还在使用该角色，
		if(userDAO.getCountInRole(roleid)==0)
		{
			result= roleDAO.removeOCRole(roleDAO.getRoleByID(roleid));
		}
		jo.put("result", result);
		// write log and push message
		Date endTime = new Date();
		JSONArray infoArray = new JSONArray();
		infoArray.put(TimeUtils.createLogInfo(
				LogRole.ROLE, roleName));
		if (result) {
			OCLog log = logRecord.addSuccessLog(userId,LogObject.ROLE,
					LogAction.DELETE,
					infoArray.toString(), startTime, endTime); 
			message.pushSuccess(userId, log.toString());
		} else {
			OCLog log = logRecord.addFailedLog(userId, LogObject.ROLE,
					LogAction.DELETE,
					infoArray.toString(), startTime, endTime);
			message.pushError(userId, log.toString());
		}
		return jo;
	}

	public List<AuthNode> getRoleData(int roleid) {
		List<AuthNode> listresult=new ArrayList<AuthNode>();
		try{
			List<RoleAction> list=roleActionDAO.getPagedRoleActionList(1, Integer.MAX_VALUE, roleid);
			List<OCAction> listAll = actionDAO.getPagedActionList(1, Integer.MAX_VALUE, "");
			for(OCAction action:listAll){
				AuthNode node = new AuthNode();
				node.setName(action.getActionName());
				node.setActionType(action.getActionType().ordinal());
				node.setId(action.getActionId());
				node.setpId(action.getParentId());
				for(RoleAction item :list){
					if(item.getActionId()==action.getActionId() && item.getAuthorityType()==AuthorityType.OPERATION){
						node.setChecked(true);
						break;
					}
				}
				listresult.add(node);
			}
			return listresult;
		}
		catch(Exception e){
			e.printStackTrace();
			return listresult;
		}
	}

	public String setAuth(int userId, int roleid, String actionids) {
		boolean result =false;
		if(actionids.length()>1){
			System.out.print(actionids);
			String[] ids = actionids.split(",");
			if(roleActionDAO.deleteByRoleId(roleid)){
				for(int i=0; i< ids.length;i++){
					if(ids[i]!=null && !ids[i].equals("")){
						RoleAction roleAction= new RoleAction(roleid, Integer.parseInt(ids[i]), AuthorityType.OPERATION);
						roleActionDAO.addRoleAction(roleAction);
					}
				}
				result=true;
			}
		}
		if (result) {
			message.pushSuccess(userId, "配置角色权限成功");
		} else {
			message.pushError(userId, "配置角色权限失败");
		}
      return String.valueOf(result);
	}
	
	private OCRole roleRegister(String roleName, String roleIntroduce,
			String roleRemarks) {
		try {
			if (roleName == null) {
				loggerConfig.logError("Role Register: Role [" + roleName
						+ "] Failed: Null Exception");
				return null;
			}
			OCRole role = roleDAO.getRole(roleName);
			if (role != null) {
				loggerConfig.logError("Role Register: Role [" + roleName
						+ "] Failed: Exist Yet");
				return null;
			}
			role= new OCRole(roleName, roleIntroduce,roleRemarks);
			roleDAO.addOCRole(role);
			role = roleDAO.getRole(roleName);
			if (role == null) {
				loggerConfig.logError("Role Register: Role [" + roleName
						+ "] Failed: Check Failed");
				return null;
			}
			loggerConfig.logInfo("Role Register: Role [" + roleName + "] roleIntroduce ["
					+ roleIntroduce + "] roleRemarks [" + roleRemarks + "] Successful");
			return role;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public int addRole(OCRole role) {
		OperationResult resultNumber = OperationResult.FALSE;
		try {
			if (roleDAO.addOCRole(role))
				resultNumber = OperationResult.TRUE;
		} catch (Exception e) {
			e.printStackTrace();
			resultNumber = OperationResult.TRUE;
		}

		return resultNumber.ordinal();
	}

	public int deleteRole(OCRole role) {
		OperationResult resultNumber = OperationResult.FALSE;
		try {
			// 删除之前，必要要先移除挂载在该权限下的用户， 放在Manager层去判断。

			if (role != null && role.getRoleId() > 0) {
				if (roleDAO.removeOCRole(role))
					resultNumber = OperationResult.TRUE;
			} else {
				// / 参数不合法！
				resultNumber = OperationResult.LLLEGALPARAMETER;
			}

		} catch (Exception e) {
			e.printStackTrace();
			resultNumber = OperationResult.EXCEPTION;
		}

		return resultNumber.ordinal();
	}

	public int addAction(OCAction action) {
		OperationResult resultNumber = OperationResult.FALSE;
		try {

			if (actionDAO.addOCAction(action))
				resultNumber = OperationResult.TRUE;

		} catch (Exception e) {
			e.printStackTrace();
			resultNumber = OperationResult.TRUE;
		}

		return resultNumber.ordinal();
	}

	public int deleteAction(int actionId) {
		OperationResult resultNumber = OperationResult.FALSE;
		try {
			// 删除之前，必须要删除RoleActio中的，相关记录，移除他的权限配置

			if (actionId > 0) {
				if (roleActionDAO.deleteByActionId(actionId)) {
					if (actionDAO.removeOCAction(
							actionDAO.getActionByID(actionId)))
						resultNumber = OperationResult.TRUE;
				}
			} else {
				// / 参数不合法！
				resultNumber = OperationResult.LLLEGALPARAMETER;
			}

		} catch (Exception e) {
			e.printStackTrace();
			resultNumber = OperationResult.EXCEPTION;
		}

		return resultNumber.ordinal();
	}

	public int updateAction(OCAction action) {
		OperationResult resultNumber = OperationResult.FALSE;
		try {

			if (actionDAO.editOCAction(action))
				resultNumber = OperationResult.TRUE;

		} catch (Exception e) {
			e.printStackTrace();
			resultNumber = OperationResult.TRUE;
		}

		return resultNumber.ordinal();
	}

	public int saveAuthority(List<RoleAction> roleActions) {
		OperationResult resultNumber = OperationResult.FALSE;
		try {
			for (RoleAction roleAction : roleActions) {
				roleActionDAO.addRoleAction(roleAction);
			}
			resultNumber = OperationResult.TRUE;
		} catch (Exception e) {
			e.printStackTrace();
			resultNumber = OperationResult.TRUE;
		}

		return resultNumber.ordinal();
	}

	public PagedList<OCRole> getPagedRoleList(int page, int limit, String search) {
		PagedList<OCRole> list = null;
		try {

			int totalCount = roleDAO.countAllRoleList(search);
			list = new PagedList<OCRole>(page, limit, search, totalCount);
			if (totalCount > 0) {
				list.addAll(roleDAO.getPagedRoleList(page, limit,
						search));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public PagedList<RoleActionView> getPagedRoleActionList(int page, int limit,
			int roleId) {
		PagedList<RoleActionView> list = null;
		try {
            /// 这边的查询方式有点龊，很不好，暂时先这样，后期要改掉，不然睡不着。。。
			int totalCount = roleActionDAO.countAllRoleActionList(roleId);
			list = new PagedList<RoleActionView>(page, limit, roleId, totalCount);
			if (totalCount > 0) {
				List<RoleAction> roleActions= roleActionDAO.getPagedRoleActionList(page, limit, roleId);
				for(RoleAction item :roleActions)
				{
					RoleActionView vi =new RoleActionView();
					vi.setRoleAction(item);
					vi.setOcaction(actionDAO.getActionByID(item.getActionId()));
					list.add(vi);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
