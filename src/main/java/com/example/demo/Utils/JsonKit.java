package com.example.demo.Utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonKit {

	private static SerializeConfig mapping = new SerializeConfig();
	public static String toJSONString(Object bean) {
		try {
			return JSON.toJSONString(bean, mapping, SerializerFeature.DisableCircularReferenceDetect);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

}
