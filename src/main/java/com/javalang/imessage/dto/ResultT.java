package com.javalang.imessage.dto;

/**
 * 通用的返回结果
 *
 * @param <T>
 */
public class ResultT<T> {

    private Boolean success;

    private Integer code;

    private String message;

    private T data;

    public static final Integer SUCCESS_CODE = 200;
    public static final Integer SYSTEM_EXCEPTION = -10000;
    public static final String SUCCESS_MESSAGE = "成功";

    public ResultT(Integer code, String message) {
        this.success = false;
        this.code = code;
        this.message = message;
    }

    public ResultT(T data) {
        this.success = true;
        this.code = SUCCESS_CODE;
        this.message = SUCCESS_MESSAGE;
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultT{" +
                "success=" + success +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
