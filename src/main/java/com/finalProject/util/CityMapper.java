package com.finalProject.util;

import java.util.Map;

public class CityMapper {
	public static final Map<String, Integer> CITY_NAME_TO_ID = Map.ofEntries(Map.entry("臺北市", 1), Map.entry("新北市", 2),
			Map.entry("桃園市", 3), Map.entry("基隆市", 4), Map.entry("新竹市", 5), Map.entry("新竹縣", 6), Map.entry("苗栗縣", 7),
			Map.entry("臺中市", 8), Map.entry("彰化縣", 9), Map.entry("南投縣", 10), Map.entry("雲林縣", 11), Map.entry("嘉義市", 12),
			Map.entry("嘉義縣", 13), Map.entry("臺南市", 14), Map.entry("高雄市", 15), Map.entry("屏東縣", 16),
			Map.entry("宜蘭縣", 17), Map.entry("花蓮縣", 18), Map.entry("臺東縣", 19), Map.entry("澎湖縣", 20),
			Map.entry("金門縣", 21), Map.entry("連江縣", 22));

	public static Integer getCityIdByName(String cityName) {
		return CITY_NAME_TO_ID.get(cityName);
	}
}
