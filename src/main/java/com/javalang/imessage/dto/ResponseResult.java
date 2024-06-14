package com.javalang.imessage.dto;

import com.fasterxml.jackson.databind.ObjectMapper;

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

    public  static String wsMessage(boolean isSystemMessage, String fromName, Object message){
        try {
            WSMessage result = new WSMessage();
            result.setIsSystem(isSystemMessage);
            result.setMessage(message);
            if(fromName != null) {
                result.setFromName(fromName);
            }
            ObjectMapper mapper = new ObjectMapper();

            return mapper.writeValueAsString(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
