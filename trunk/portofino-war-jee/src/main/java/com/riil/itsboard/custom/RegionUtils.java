package com.riil.itsboard.custom;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RegionUtils {

	public static List<String> getRegion1(List<Map<String, Object>> list) {
		List<String> regionList = new ArrayList<String>();
		String t_region_field = "c_region1";
		for (Map map : list) {
			if (!regionList.contains(map.get(t_region_field).toString())) {
				regionList.add(map.get(t_region_field).toString());
			}
		}
		return regionList;
	}

	public static List<String> getRegion2(List<Map<String, Object>> list) {
		List<String> regionList = new ArrayList<String>();
		String t_region_field = "c_region2";
		for (Map<String, Object> map : list) {
			if (!regionList.contains(map.get(t_region_field).toString())) {
				regionList.add(map.get(t_region_field).toString());
			}
		}
		return regionList;
	}

	public static List<Map<String, Object>> getRegion3(List<Map<String, Object>> list, String region2) {
		List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> m : list) {
			if (region2.equals(m.get("c_region2").toString())) {
				maps.add(m);
			}
		}
		return maps;
	}
}
