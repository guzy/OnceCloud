/**
 * 
 */
package com.beyondsphere.model;

import java.util.ArrayList;
import java.util.List;



/**
 * @author 玉和
 * 用于封装分页的List对象
 * 封装查询的信息，和返回的信息
 * int page, int limit, String search，int allCount：
 */
public class PagedList<T> extends ArrayList<T>{
   
	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	//当前页索引
	private int pageIndex;
	
	// 每页的记录数
	private int limit;
	
	// 查询信息
	private Object seachObj;
	
	// 总的记录数
	private int totalCount;
	
	private List<T> list;

	public PagedList(int pageIndex, int limit, Object seachObj, int totalCount) {
		super();
		this.pageIndex = pageIndex;
		this.limit = limit;
		this.seachObj = seachObj;
		this.totalCount = totalCount;
	}

	public PagedList() {
		super();
	}

	public PagedList(int pageIndex, int limit) {
		super();
		this.pageIndex = pageIndex;
		this.limit = limit;
	}
	
	public PagedList(int pageIndex, int limit,Object seachObj) {
		super();
		this.pageIndex = pageIndex;
		this.limit = limit;
		this.seachObj =seachObj;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public Object getSeachObj() {
		return seachObj;
	}

	public void setSeachObj(Object seachObj) {
		this.seachObj = seachObj;
	}

	/// 返回总记录数
	public int getTotalCount() {
		return totalCount;
	}

	// 设置总记录数
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getStartPos() {
		return (pageIndex - 1) * limit;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

}
