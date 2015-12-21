/**
 * @author hty
 * @time 下午3:50:54
 * @date 2014年12月6日
 */
package com.oncecloud.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.oncecloud.config.ManagerConfig;
import com.oncecloud.model.Register;

@Component
public class Dispatch {

	@Resource
	private ManagerConfig mc;

	@SuppressWarnings("static-access")
	public void post(JSONObject jo) {
		String type = jo.getString("Handler");
		List<Register> registerList = mc.getConfigMap().get(type);
		for (int register_i = 0; register_i < registerList.size(); register_i++) {
			Method method;
			try {
				method = registerList.get(register_i).getObject().getClass().getMethod(
								registerList.get(register_i).getMetodName(),
								registerList.get(register_i).getParamsList().toArray(
									new Class<?>[registerList.get(register_i).getParamsList().size()]));
			} catch (NoSuchMethodException | SecurityException e1) {
				e1.printStackTrace();
				continue;
			}
			JSONArray json_array = (JSONArray) jo.get("Params");
			Object[] obj_array = new Object[json_array.length()];
			for (int json_array_j = 0; json_array_j < json_array.length(); json_array_j++) {
				obj_array[json_array_j] = registerList.get(register_i)
						.getParamsList().get(json_array_j)
						.cast(json_array.get(json_array_j));
			}
			JSONArray temp = new JSONArray();
			try {
				temp.put(method.invoke(registerList.get(register_i).getObject(), obj_array));
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
				continue;
			}
			jo.put("Params", temp);
		}
	}

}
