package com.beyondsphere.controller.action;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.beyondsphere.entity.Area;
import com.beyondsphere.entity.User;
import com.beyondsphere.helper.AuthOperationHelper;
import com.beyondsphere.query.QueryList;
import com.beyondsphere.service.AreaService;
import com.beyondsphere.service.CaptchaService;
import com.beyondsphere.service.UserService;
import com.beyondsphere.usercore.LogCore;
import com.beyondsphere.util.Utilities;
import com.beyondsphere.utils.Dispatch;
import com.beyondsphere.model.CaptchaNumber;
import com.beyondsphere.model.LogOnModel;
import com.beyondsphere.model.PagedList;
import com.beyondsphere.vi.RoleActionView;
import com.beyondsphere.model.UserLevel;

@Controller
public class LoginAction {

	@Resource
	private UserService userService;
	@Resource
	private Dispatch dispatch;
	@Resource
	private QueryList queryList;
	@Resource
	private CaptchaService captchaService;
	@Resource
	private AreaService areaService;
	@Resource
	private LogCore logCore;

	@RequestMapping(value = "/login", method = { RequestMethod.POST })
	@ResponseBody
	public ModelAndView login(HttpServletRequest request,
			HttpServletResponse response, LogOnModel logOnRequestModel) {

		// 会话存在过期现象
		if (logOnRequestModel.getVercode() == null
				|| request.getSession().getAttribute("rand") == null) {
			return new ModelAndView(new RedirectView("/login"));
		} else {
			Date startTime = new Date();
			JSONObject loginJo = new JSONObject();
			int checkResult = userService.checkLogin(
					logOnRequestModel.getUser(),
					logOnRequestModel.getPassword());
			if (checkResult == 1) {
				loginJo.put("result", false);
				loginJo.put("startTime", startTime);
				User user = userService.getUser(logOnRequestModel.getUser());
				loginJo.put("userId", user == null ? 0:user.getUserId());
				loginJo.put("userName", user == null ? "":user.getUserName());
				Date endTime = new Date();
				loginJo.put("elapse", Utilities.timeElapse(startTime, endTime));
				logCore.checkLogin(loginJo);
				return new ModelAndView(new RedirectView("/login"));
			}
			String captcha = (String) request.getSession().getAttribute("rand");
			if (logOnRequestModel.getVercode().toLowerCase()
					.equals(captcha.toLowerCase())) {
				request.getSession().setAttribute("rand", null);
				JSONObject jo = new JSONObject();
				if (logOnRequestModel.getUser() != null) {
					jo.put("Handler", "checkLoginHandler");
					JSONArray ja = new JSONArray();
					JSONObject params = new JSONObject();
					params.put("userName", logOnRequestModel.getUser());
					params.put("userPass", logOnRequestModel.getPassword());
					ja.put(0, params);
					jo.put("Params", ja);
					dispatch.post(jo);
				}
				if (jo.length() > 0) {
					if (jo.has("Params")
							&& ((JSONObject) ((JSONArray) jo.get("Params"))
									.get(0)).has("userObj")) {
						User userlogin = (User) ((JSONObject) ((JSONArray) jo
								.get("Params")).get(0)).get("userObj");
						if (userlogin.getUserLevel() != UserLevel.ADMIN) {
							String path = request.getContextPath();
							String basePath = request.getScheme() + "://"
									+ request.getServerName() + ":"
									+ request.getServerPort() + path + "/";
							request.getSession().setAttribute("user", userlogin);
							request.getSession().setAttribute("basePath",basePath);
							
							// 权限相关
							PagedList<RoleActionView> auths = new PagedList<RoleActionView>();
							if (userlogin.getUserRoleId() != null
									&& userlogin.getUserRoleId() > 0) {
								auths = queryList.queryAuthorityList(1,Integer.MAX_VALUE,userlogin.getUserRoleId());
							}
							request.getSession().setAttribute("authoritylists",auths);
							request.getSession().setAttribute("userAuthHtmlId",AuthOperationHelper.getPageAuthString(auths));

							String enterAddress = request.getServerName();
							String str = null;
							String refAddress = null;
							try {
								str = String.valueOf(InetAddress.getByName(request.getServerName()));
								String a[]=str.split("/"); 
							    refAddress =a[1];
							} catch (Exception e) {
								e.printStackTrace();
							} 
							
		                   if(!enterAddress.equals(refAddress)&&!"localhost".equals(enterAddress)){

							//登录时获取用户的Session信息，保存在cookies里面。
							if(request.getSession() != null){
								//String sessionId = request.getSession().getId();
								Cookie c1 = new Cookie("userName",userlogin.getUserName());
								Cookie c2 = new Cookie("userPass",userlogin.getUserPass());
								
								//有效时间
								c1.setMaxAge(60*60);
								c2.setMaxAge(60*60);
								//获取域名，截取后放到cookie里
								String url = request.getServerName();  
								int index = url.indexOf(".");  
								String urlDomain = url.substring(index);  
								c1.setDomain(urlDomain);
								c1.setPath("/");
								c2.setDomain(urlDomain);
								c2.setPath("/");
								response.addCookie(c1);
								response.addCookie(c2);
							}
							
							// 获取该用户所在区域，查询区域信息，放到session
							String areaId = userlogin.getUserLocation();
							Area area = areaService.getArea(areaId);
							request.getSession().setAttribute("area", area);
							// 根据用户所在的区域，跳转到对应的区域网站
							String old_area = request.getServerName();
							if (!old_area.equals(area.getAreaDomain())) {
								String sendPath = request.getScheme() + "://"
										+ area.getAreaDomain() + ":"
										+ request.getServerPort();
								try {
									response.sendRedirect(sendPath);
									return null;
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
		                  }
							return new ModelAndView(new RedirectView("/overview"));
						}
					}

				}
			}
		}
		return new ModelAndView(new RedirectView("/login"));
	}

	@RequestMapping(value = "/captcha", method = { RequestMethod.GET })
	public ResponseEntity<byte[]> image(HttpServletRequest request)
			throws IOException {
		CaptchaNumber capnumber = captchaService.getCaptchaNumber();
		// 取得用户Session
		HttpSession session = request.getSession(true);
		// 将系统生成的图形验证码添加
		session.setAttribute("rand", capnumber.getTotal().toString());
		return new ResponseEntity<byte[]>(captchaService.getImage(
				capnumber.getFirst(), capnumber.getSecond()),
				captchaService.getCaptchaHeaders(), HttpStatus.OK);
	}
}
