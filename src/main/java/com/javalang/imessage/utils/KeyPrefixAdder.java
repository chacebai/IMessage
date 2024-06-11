package com.javalang.imessage.utils;

public class KeyPrefixAdder {
    private static final String UID_PREFIX = "uid:";
    private static final String PHONE_PREFIX = "phone:";
    private static final String CHATROOM_PREFIX = "chatroom:";

    public static String addPrefix(String value, String prefix) {
        return prefix + value;
    }

    public static String addUserPrefix(String uid) {
        return "user:" + addPrefix(uid, UID_PREFIX);
    }

    public static String addUserApplyPrefix(String uid) {
        return "user_apply:" + addPrefix(uid, UID_PREFIX);
    }

    public static String addPhonePrefix(String phone) {
        return addPrefix(phone, PHONE_PREFIX);
    }

    public static String addChatroomPrefix(String chatroomId) {
        return addPrefix(chatroomId, CHATROOM_PREFIX);
    }
}
