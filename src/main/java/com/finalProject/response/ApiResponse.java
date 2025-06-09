package com.finalProject.response;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
	private int status; // 狀態 例如: 200, 400
	private String message; // 訊息 例如: 查詢成功, 新增成功, 請求錯誤
	private T data; // payload 實際資料

	// 成功回應
	public static <T> ApiResponse<T> success(String message, T data) {
		return new ApiResponse<T>(HttpStatus.OK.value(), message, data);
	}

	// 失敗回應
	public static <T> ApiResponse<T> error(HttpStatus status, String message) {
		return new ApiResponse<T>(status.value(), message, null);
	}
}
