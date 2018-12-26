package com.haozhen.service.distribute.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtil {

	public static Gson getGson() {
		return new GsonBuilder().create();
	}
}
