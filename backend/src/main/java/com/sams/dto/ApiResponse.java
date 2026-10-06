package com.sams.dto;

public class ApiResponse {
    private boolean success;
    private String message;
    private Object data;

    public ApiResponse() {}

    public ApiResponse(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static ApiResponse success(String message) {
        return new ApiResponse(true, message, null);
    }

    public static ApiResponse success(String message, Object data) {
        return new ApiResponse(true, message, data);
    }

    public static ApiResponse error(String message) {
        return new ApiResponse(false, message, null);
    }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Object getData() { return data; }
    public void setData(Object data) { this.data = data; }

    public static ApiResponseBuilder builder() { return new ApiResponseBuilder(); }

    public static class ApiResponseBuilder {
        private boolean success;
        private String message;
        private Object data;

        public ApiResponseBuilder success(boolean success) { this.success = success; return this; }
        public ApiResponseBuilder message(String message) { this.message = message; return this; }
        public ApiResponseBuilder data(Object data) { this.data = data; return this; }

        public ApiResponse build() {
            return new ApiResponse(success, message, data);
        }
    }
}
