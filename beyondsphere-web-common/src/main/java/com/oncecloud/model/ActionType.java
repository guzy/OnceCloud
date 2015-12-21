package com.oncecloud.model;

/*
 * @author 玉和
 * @date 2014年12月4日
 * 说明：
 * 代表action的行为类型，0:页面，1：操作/按钮，2:子页面，3弹出层
 * 对应着前台action的几种情况，有新的情况，可添加。 
*/

public enum ActionType {
    PAGE,OPERATION,SUBPAGE,MODALPAGE
}
