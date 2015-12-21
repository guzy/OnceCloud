package com.beyondsphere.helper;

import com.beyondsphere.model.ActionType;
import com.beyondsphere.model.AuthorityType;
import com.beyondsphere.model.PagedList;
import com.beyondsphere.vi.RoleActionView;

/**
 * @author 玉和 权限的 帮助类，主要是处理一些查询匹配逻辑
 */
public class AuthOperationHelper {

	/**
	 * 从权限列表中 获取拥有的页面的权限URL的String拼接
	 * 
	 * @param auths
	 * @return
	 */
	public static String getPageAuthString(PagedList<RoleActionView> auths) {
		// / 拼接页面权限htmId
		StringBuilder strs = new StringBuilder("");
		for (RoleActionView viewmodel : auths) {
			if (viewmodel.getOcaction().getActionType().equals(ActionType.PAGE)
					&& viewmodel.getRoleAction().getAuthorityType().equals(AuthorityType.OPERATION)) {
				strs.append(viewmodel.getOcaction().getActionHtmlId() + ",");
			}
		}
		return strs.toString();
	}

	/**
	 * 从权限列表中 获取拥有的页面的权限URL的String拼接
	 * 
	 * @param pageUrl
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getOperationOfPageAuthString(String pageUrl,
			Object objauth) {
		//拼接某个页面里的操作的权限htmId
		StringBuilder strs = new StringBuilder("");
		if (objauth != null) {
			PagedList<RoleActionView> auths = (PagedList<RoleActionView>) objauth;
			int pageId = 0;

			// 先根据URL 查找到 当前页面的id
			for (RoleActionView viewmodel : auths) {
				if (viewmodel.getOcaction().getActionType().equals(ActionType.PAGE)
						&& viewmodel.getRoleAction().getAuthorityType().equals(AuthorityType.OPERATION)
						&& viewmodel.getOcaction().getActionRelativeUrl()
								.equals(pageUrl)) {
					pageId = viewmodel.getOcaction().getActionId();
					break;
				}
			}

			// 查找拼接 到当前页面的操作对象
			for (RoleActionView viewmodel : auths) {
				if (!viewmodel.getOcaction().getActionType().equals(ActionType.PAGE)
						&& viewmodel.getRoleAction().getAuthorityType().equals(AuthorityType.OPERATION)
						&& viewmodel.getOcaction().getParentId() == pageId) {
					strs.append(viewmodel.getOcaction().getActionHtmlId() + ",");
				}
			}
		}
		return strs.toString();
	}
	
}
