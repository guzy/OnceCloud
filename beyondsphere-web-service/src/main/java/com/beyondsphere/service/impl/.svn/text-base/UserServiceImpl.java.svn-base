package com.beyondsphere.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.beyondsphere.constants.LoggerConfig;
import com.beyondsphere.dao.LogDAO;
import com.beyondsphere.dao.PoolDAO;
import com.beyondsphere.dao.RoleDAO;
import com.beyondsphere.dao.UserDAO;
import com.beyondsphere.dao.VMDAO;
import com.beyondsphere.entity.OCLog;
import com.beyondsphere.entity.OCVM;
import com.beyondsphere.entity.User;
import com.beyondsphere.helper.HashHelper;
import com.beyondsphere.log.LogAction;
import com.beyondsphere.log.LogObject;
import com.beyondsphere.log.LogRecord;
import com.beyondsphere.log.LogRole;
import com.beyondsphere.message.MessageUtil;
import com.beyondsphere.model.OperationResult;
import com.beyondsphere.model.PagedList;
import com.beyondsphere.model.UserStatus;
import com.beyondsphere.service.UserService;
import com.beyondsphere.util.TimeUtils;

@Component("UserService")
public class UserServiceImpl implements UserService {
	
	@Resource
	private UserDAO userDAO;
	@Resource
	private LogDAO logDAO;
	@Resource
	private PoolDAO poolDAO;
	@Resource
	private RoleDAO roleDAO;
	@Resource
	private MessageUtil message;
	@Resource
	private HashHelper hashHelper;
	@Resource
	private VMDAO vmDAO;
	@Resource
	private LogRecord logRecord;
	@Resource
	private LoggerConfig loggerConfig;
	
	public int checkLogin(String userName, String userPass) {
		int result = 1;
		try {
			User user = getUser(userName);
			if (user == null) {
				// User does not exist
				result = 1;
			} else {
				if (user.getUserStatus() == UserStatus.DELETED) {
					result = 1;
				} else {
					String pass = user.getUserPass();
					if (pass.equals(hashHelper.md5Hash(userPass))) {
						// Validated
						result = 0;
					} else {
						// Password is wrong
						result = 1;
					}
				}
			}
			loggerConfig.logInfo("Check Login: User [" + userName + "] Result ["
					+ result + "]");
		} catch (Exception e) {
			e.printStackTrace();
			result = -1;
		}
		return result;
	}

	public User getUser(String userName) {
		return userDAO.getUser(userName);
	}

	public JSONArray queryUser(String userName) {
		JSONArray ja = new JSONArray();
		User userResult = getUser(userName);
		JSONObject jo = new JSONObject();
		if (userResult != null) {
			jo.put("exist", true);
		} else {
			jo.put("exist", false);
		}
		ja.put(jo);
		return ja;
	}

	public JSONArray addUser(String userName, String userPassword,
			String userEmail, String userTelephone, String userCompany,
			String userLevel, int userid, String poolUuid, int roleUuid,String areaId) {
		JSONArray ja = new JSONArray();
		Date startTime = new Date();
		User result = userRegister(userName, userPassword, userEmail,
				userTelephone, userCompany, userLevel, poolUuid,roleUuid,areaId);
		Date endTime = new Date();
		if (result != null) {
			JSONObject jo = new JSONObject();
			jo.put("username", TimeUtils.encodeText(result.getUserName()));
			jo.put("userid", result.getUserId());
			jo.put("usercom", TimeUtils.encodeText(result.getUserCompany()));
			jo.put("userdate", TimeUtils.formatTime(result.getUserDate()));
			jo.put("userlevel", result.getUserLevel());
			jo.put("usermail", result.getUserMail());
			jo.put("userphone", result.getUserPhone());
			ja.put(jo);
		}
		// write log and push message
		JSONArray infoArray = new JSONArray();
		infoArray.put(TimeUtils.createLogInfo(
				LogRole.USER, userName));
		if (result != null) {
			OCLog log = logRecord.addSuccessLog(userid, LogObject.USER, 
					LogAction.CREATE, 
					infoArray.toString(), startTime, endTime);
			message.pushSuccess(userid, log.toString());
		} else {
			OCLog log = logRecord.addFailedLog(userid, LogObject.USER, 
					LogAction.CREATE, 
					infoArray.toString(), startTime, endTime);
			message.pushError(userid, log.toString());
		}
		return ja;
	}

	public JSONObject getOneUser(int userId) {
		User myuser = userDAO.getUser(userId);
		JSONObject jo = new JSONObject();
		jo.put("username", myuser.getUserName());
		jo.put("userid", myuser.getUserId());
		jo.put("usercom", myuser.getUserCompany());
		jo.put("userdate", TimeUtils.formatTime(myuser.getUserDate()));
		jo.put("userlevel", myuser.getUserLevel());
		jo.put("usermail", myuser.getUserMail());
		jo.put("userphone", myuser.getUserPhone());
		jo.put("balance", myuser.getUserBalance());
		jo.put("userroleid", myuser.getUserRoleId());
		if(myuser.getUserRoleId()!=null){
			jo.put("userrole", roleDAO.getRoleByID(myuser.getUserRoleId()).getRoleName());
		}
		else{
			jo.put("userrole","");
		}
		return jo;
	}
	
	public User getUser(int userId) {
		return userDAO.getUser(userId);
	}

	public void updateUser(int userId, int changeId, String userName,
			String userEmail, String userTel, String userCom, String userLevel,
			int roleUuid) {
		boolean result = userUpdate(changeId, userName, userEmail,
				userTel, userCom, userLevel,roleUuid);
		if (result) {
			message.pushSuccess(userId, "用户信息修改成功");
		} else {
			message.pushError(userId, "用户信息修改失败");
		}
	}

	public JSONObject deleteUser(int userId, int changeId, String userName) {
		JSONObject jo = new JSONObject();
		Date startTime = new Date();
		boolean result = false;
		int resourceNumofUser = 0;
		List<OCVM> vmList = vmDAO.getVMsByUser(changeId);
		if (vmList != null) {
			resourceNumofUser = vmList.size();
		}
		if (resourceNumofUser != 0) {
			message.pushError(userId, "用户存在已使用的资源，请先销毁资源再删除！");
		}else {
			result = userDAO.disableUser(changeId);
			// write log and push message
			Date endTime = new Date();
			JSONArray infoArray = new JSONArray();
			infoArray.put(TimeUtils.createLogInfo(
					LogRole.USER, userName));
			if (result) {
				OCLog log = logRecord.addSuccessLog(userId, LogObject.USER, 
						LogAction.DELETE, infoArray.toString(), startTime, endTime); 
				message.pushSuccess(userId, log.toString());
			} else {
				OCLog log = logRecord.addFailedLog(userId, LogObject.USER, 
						LogAction.DELETE, infoArray.toString(), startTime, endTime); 
				message.pushError(userId, log.toString());
			}
		}
		jo.put("result", result);
		return jo;
	}

	public JSONArray getUserList(int page, int limit, String search) {
		JSONArray ja = new JSONArray();
		int totalNum = userDAO.countAllUserList(search);
		ja.put(totalNum);
		List<User> userList = userDAO.getOnePageUserList(page, limit,
				search);
		if (userList != null) {
			for (User myuser : userList) {
				JSONObject jo = new JSONObject();
				int vmNumOfUser = vmDAO.getVMsByUser(myuser.getUserId()).size();
				jo.put("username", TimeUtils.encodeText(myuser.getUserName()));
				jo.put("userid", myuser.getUserId());
				jo.put("usercom",
						TimeUtils.encodeText(myuser.getUserCompany()));
				jo.put("userdate", TimeUtils.formatTime(myuser.getUserDate()));
				jo.put("userlevel", myuser.getUserLevel());
				jo.put("usermail", myuser.getUserMail());
				jo.put("userphone", myuser.getUserPhone());
				jo.put("vmNum", vmNumOfUser);
				if (myuser.getUserVoucher() == null) {
					jo.put("uservoucher", 0);
				} else {
					jo.put("uservoucher", myuser.getUserVoucher());
				}
				if(myuser.getUserRoleId()!=null){
					jo.put("userrole", roleDAO.getRoleByID(myuser.getUserRoleId()).getRoleName());
				}else {
					jo.put("userrole","");
				}
				ja.put(jo);
			}
		}
		return ja;
	}

	public JSONArray getUserList() {
		JSONArray ja = new JSONArray();
		List<User> list = userDAO.getUserList();
		for (User user : list) {
			JSONObject jo = new JSONObject();
			jo.put("userid", user.getUserId());
			jo.put("username", user.getUserName());
			jo.put("userlevel", user.getUserLevel());
			jo.put("userphone", user.getUserPhone());
			if(user.getUserRoleId()!=null){
				jo.put("userrole", roleDAO.getRoleByID(user.getUserRoleId()).getRoleName());
			}else {
				jo.put("userrole","");
			}
			ja.put(jo);
		}
		return ja;
	}
	
	private boolean userUpdate(Integer userid, String userName,
			String userMail, String userPhone, String userCompany, String uLevel,int roleUuid) {
		try {
			if (userName == null || null == userid) {
				loggerConfig.logError("User Update: User [" + userName
						+ "] Failed: Null Exception");
				return false;
			}
			int userLevel = Integer.valueOf(uLevel);
			User user = userDAO.getUser(userid);
			if (user == null) {
				loggerConfig.logError("User Update: User- [" + userid
						+ "] Failed: Not Exist");
				return false;
			}
			boolean result = userDAO.updateUser(userid, userName,
					userMail, userPhone, userCompany, userLevel,roleUuid);
			if (!result) {
				loggerConfig.logError("User Update: User- [" + userid
						+ "] Failed: Update Failed");
				return false;
			}
			loggerConfig.logInfo("User Update To: User [" + userid + "] UserName ["
					+ userName + "] Mail [" + userMail + "] Telephone ["
					+ userPhone + "] Company [" + userCompany + "] UserLevel ["
					+ userLevel + "] Successful");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private User userRegister(String userName, String userPassword,
			String userEmail, String userTelephone, String userCompany,
			String uLevel, String poolUuid,int roleUuid,String areaId) {
		try {
			if (userName == null || userPassword == null) {
				loggerConfig.logError("User Register: User [" + userName
						+ "] Failed: Null Exception");
				return null;
			}
			int userLevel = Integer.valueOf(uLevel);
			Date date = new Date();
			User user = userDAO.getUser(userName);
			if (user != null) {
				loggerConfig.logError("User Register: User [" + userName
						+ "] Failed: Exist Yet");
				return null;
			}
			if (poolUuid.equals("0")){
				poolUuid = poolDAO.getRandomPool();
			}
			if (userDAO.getUser(userName) == null) {
				userDAO.insertUser(userName, userPassword, userEmail,
						userTelephone, userCompany, userLevel, date, poolUuid,roleUuid,areaId);
			}
			User check = userDAO.getUser(userName);
			if (check == null) {
				loggerConfig.logError("User Register: User [" + userName
						+ "] Failed: Check Failed");
				return null;
			}
			loggerConfig.logInfo("User Register: User [" + userName + "] Password ["
					+ userPassword + "] Mail [" + userEmail + "] Telephone ["
					+ userTelephone + "] Company [" + userCompany
					+ "] UserLevel [" + userLevel + "] Successful");
			return check;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void updateAreaInfo(Integer userId, String userActiveLocation) {

		boolean result = userDAO.updateAreaInfo(userId, userActiveLocation);

		if (result) {
			message.pushSuccess(0, "用户活跃区域信息修改成功");
		} else {
			message.pushError(1, "用户活跃区域信息修改失败");
		}
	}
	
	public boolean updateAdmin(int userId,String userPwd,
			String userEmail, String userTel) {
		boolean result = adminUpdate(userId, userPwd, userEmail,
				userTel);
		if (result) {
			message.pushSuccess(userId, "用户信息修改成功");
		} else {
			message.pushError(userId, "用户信息修改失败");
		}
		
		return result;
	}
	private boolean adminUpdate(Integer userid, String userPwd,
			String userMail, String userPhone) {
		try {
			User user = userDAO.getUser(userid);
			if (user == null) {
				loggerConfig.logError("User Update: User- [" + userid
						+ "] Failed: Not Exist");
				return false;
			}
			boolean result = userDAO.updateAdmin(userid, userPwd,
					userMail, userPhone);
			if (!result) {
				loggerConfig.logError("User Update: User- [" + userid
						+ "] Failed: Update Failed");
				return false;
			}
			loggerConfig.logInfo("User Update To: User [" + userid + "] UserPwd ["
					+ userPwd + "] Mail [" + userMail + "] Telephone ["
					+ userPhone + "]  Successful");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Integer getEnterpriseCount(Date endDate) {
		return userDAO.getEnterpriseCount(endDate);
	}

	@Override
	public JSONArray getEnterpriseList(int num) {
		JSONArray ja = new JSONArray();
		List<User> list = userDAO.getEnterpriseList(num);
		for (User user : list) {
			JSONObject jo = new JSONObject();
			jo.put("userid", user.getUserId());
			jo.put("username", user.getUserName());
			jo.put("createTime", user.getUserDate());
			jo.put("usercom", user.getUserCompany());
			ja.put(jo);
		}
		return ja;
	}

	@Override
	public boolean createMap(String userid, String lng, String lat) {
		boolean result = userSetMap(userid, lng, lat);
		if (result) {
			message.pushSuccess(Integer.parseInt(userid), "用户地理位置设置成功");
		} else {
			message.pushError(Integer.parseInt(userid), "用户地理位置设置失败");
		}
		return result;
	}

	private boolean userSetMap(String userid, String lng, String lat) {
		try {
			User user = userDAO.getUser(Integer.parseInt(userid));
			if (user == null) {
				loggerConfig.logError("User Set Map: User- [" + userid
						+ "] Failed: Not Exist");
				return false;
			}
			boolean result = userDAO.userSetMap(userid, lng, lat);
			if (!result) {
				loggerConfig.logError("User Set Map: User- [" + userid
						+ "] Failed: Update Failed");
				return false;
			}
			loggerConfig.logInfo("User Update To: User [" + userid + "] Userlng ["
					+ lng + "] UserLat [" + lat + "]  Successful");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public JSONArray getUsermapByUserid(String userid) {
		User user = userDAO.getUser(Integer.parseInt(userid));
		JSONArray jsonArray=new JSONArray();
		jsonArray.put(user.getLng());
		jsonArray.put(user.getLat());
		return jsonArray;
	}
	
	public int addUser(User user) {
		OperationResult resultNumber = OperationResult.FALSE;
		try {
			if (userDAO.addUser(user)){
				resultNumber = OperationResult.TRUE;
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultNumber = OperationResult.TRUE;
		}
		return resultNumber.ordinal();
	}

	public int deleteUser(User user) {
		OperationResult resultNumber = OperationResult.FALSE;
		try {
			// TODO: 待考虑，删除用户之前是否需要删除相关资源, 目前采取的方式 是状态标示删除，不是真的删除
			if (user != null && user.getUserId() > 0) {
				user = userDAO.getUser(user.getUserId());
				user.setUserStatus(UserStatus.DELETED);
				if (userDAO.updateUser(user))
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

	public int updateUser(User user) {
		OperationResult resultNumber = OperationResult.FALSE;
		System.out.println(user.getUserPass());
		try {
			if (userDAO.updateUser(user))
				resultNumber = OperationResult.TRUE;
		} catch (Exception e) {
			e.printStackTrace();
			resultNumber = OperationResult.EXCEPTION;
		}
		return resultNumber.ordinal();
	}

	public int bindRole(int userId, int roleId) {
		OperationResult resultNumber = OperationResult.FALSE;
		try {
			if (userId > 0 && roleId > 0) {
				User user = userDAO.getUser(userId);
				user.setUserRoleId(roleId);
				if (userDAO.updateUser(user))
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

	public PagedList<User> getPagedUserList(int page, int limit, String search) {
		PagedList<User> list = null;
		try {
			int totalCount = userDAO.countAllUserList(search);
			list = new PagedList<User>(page, limit, search, totalCount);
			if (totalCount > 0) {
				list.addAll(userDAO.getOnePageUserList(page, limit,
						search));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}
}
