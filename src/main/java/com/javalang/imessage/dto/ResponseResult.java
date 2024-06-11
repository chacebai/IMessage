package com.javalang.imessage.dto;

public class ResponseResult {
    public static <T> ResultT<T> success(T data){
        return new ResultT<>(data);
    }

    public  static <T> ResultT<T> fail(Integer code,String message){
        return new ResultT<>(code,message);
    }

    public  static <T> ResultT<T> exception(String message){
        return new ResultT<>(ResultT.SYSTEM_EXCEPTION, message);
    }

}
