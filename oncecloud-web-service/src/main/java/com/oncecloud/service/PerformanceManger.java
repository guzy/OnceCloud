package com.oncecloud.service;

import org.json.JSONArray;
import org.json.JSONObject;

public interface PerformanceManger {

	public abstract JSONObject getCpu(String uuid, String type);

	public abstract JSONArray getMemory(String uuid, String type);

	public abstract JSONObject getVbd(String uuid, String type);

	public abstract JSONObject getVif(String uuid, String type);

	public abstract JSONObject getPif(String uuid, String type);
}
