package com.beyondsphere.controller.forward;

import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;
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
import com.beyondsphere.service.UserService;
import com.beyondsphere.usercore.LogCore;
import com.beyondsphere.util.Utilities;
import com.beyondsphere.vi.RoleActionView;
import com.beyondsphere.model.PagedList;
import com.beyondsphere.model.UserLevel;

@Controller
public class AccountController {
	@Resource
	private UserService userService;
	@Resource
	private AreaService areaService;
	@Resource
	private QueryList queryList;
	@Resource
	private LogCore logCore;
	
	private static Logger logger = Logger.getLogger(AccountController.class);

	@RequestMapping(value = { "/", "/login" }, method = { RequestMethod.GET })
	@ResponseBody
	public ModelAndView login(HttpServletRequest request,HttpServletResponse response) {
		
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
		        //先判断是否有session信息，没有用户信息则通过cookie读取
				if(request.getSession().getAttribute("user") == null){
					User userlogin = new User();
					//先去取cookie
					Cookie[] cookies = request.getCookies();
					if(cookies!=null){
						if(cookies.length>1){
							for(Cookie cookie : cookies) {
							    if("userName".equals(cookie.getName())) {
							    	userlogin.setUserName(cookie.getValue());
							    	userlogin = userService.getUser(cookie.getValue());
							    }else if("userPass".equals(cookie.getName())){
							    }
							}
							 request.getSession().setAttribute("user", userlogin);
						}
					}
				}
           }
		if (request.getSession().getAttribute("user") != null) {
          if(!enterAddress.equals(refAddress)&&!"localhost".equals(enterAddress)){
			User userlogin = (User) request.getSession().getAttribute("user");
			// 权限相关
			PagedList<RoleActionView> auths = new PagedList<RoleActionView>();
			if (userlogin.getUserRoleId() != null
					&& userlogin.getUserRoleId() > 0) {
				auths = queryList.queryAuthorityList(1,Integer.MAX_VALUE,userlogin.getUserRoleId());
			}
			request.getSession().setAttribute("authoritylists",auths);
			request.getSession().setAttribute("userAuthHtmlId",AuthOperationHelper.getPageAuthString(auths));

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
					//
					request.getSession().removeAttribute("user");
					request.getSession().removeAttribute("authoritylists");
					request.getSession().removeAttribute("userAuthHtmlId");
					request.getSession().removeAttribute("area");
					
					response.sendRedirect(sendPath);
					return null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
           }
			return new ModelAndView(new RedirectView("/overview"));
		}
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + path + "/";
		Map<String, String> model = new HashMap<String, String>();
		model.put("basePath", basePath);
		request.getSession().setAttribute("basePath", basePath);
		return new ModelAndView("account/login", model);
	}

	@RequestMapping(value = "/logout", method = { RequestMethod.GET })
	@ResponseBody
	public ModelAndView logout(HttpServletRequest request,HttpServletResponse response) {
		Date startTime = new Date();
		int userId = 0;
		JSONObject userjo = new JSONObject();
		if (request.getSession().getAttribute("user") != null) {
			User user = (User) request.getSession().getAttribute("user");
			userId = user.getUserId();
			request.getSession().removeAttribute("user");
			request.getSession().removeAttribute("basePath");
			request.getSession().removeAttribute("authoritylists");
			
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
			 Cookie[] cookies = request.getCookies(); 
			 for(int i=0;i<cookies.length;i++)    
	           {  
	            Cookie cookie = new Cookie(cookies[i].getName(), null);  
	            cookie.setMaxAge(0);  
	            String url = request.getServerName();  
				int index = url.indexOf(".");  
				String urlDomain = url.substring(index);
				cookie.setDomain(urlDomain);
	            //cookie.setDomain(".bncloud.com");
	            cookie.setPath("/");
	            response.addCookie(cookie);  
	           }
			 if(request.getSession().getAttribute("area") != null){
					request.getSession().removeAttribute("area");
				}
           }
			logger.info("user: " + user.getUserName() + "log out.");
			userjo.put("result", true);
			userjo.put("startTime", startTime);
			userjo.put("userId", userId);
			Date endTime = new Date();
			userjo.put("elapse", Utilities.timeElapse(startTime, endTime));
			logCore.Logout(userjo);
			 if (user.getUserLevel() == UserLevel.USER) {
				return new ModelAndView(new RedirectView("/login"));
			} else {
				return new ModelAndView(new RedirectView("/backdoor"));
			}
		} else {
			return new ModelAndView(new RedirectView("/login"));
		}
	}

	@RequestMapping(value = "/404", method = { RequestMethod.GET })
	@ResponseBody
	public ModelAndView pageNotFound(HttpServletRequest request) {
		return new ModelAndView("account/404");
	}

	@RequestMapping(value = "/403", method = { RequestMethod.GET })
	@ResponseBody
	public ModelAndView pageNotAuth(HttpServletRequest request) {
		return new ModelAndView("account/403");
	}

}