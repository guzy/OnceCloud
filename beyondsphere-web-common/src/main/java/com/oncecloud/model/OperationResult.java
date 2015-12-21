package com.oncecloud.model;

/*
 * @author 玉和
 * @date 2014年12月4日
 * 说明：
 * 方法的返回值，只适用于整形返回值，0:false，1：true，2:发生异常，3 参数不合法
 * 对应着前台action的几种情况，有新的情况，可添加。 
*/

public enum OperationResult {
    FALSE,TRUE,EXCEPTION,LLLEGALPARAMETER
}
