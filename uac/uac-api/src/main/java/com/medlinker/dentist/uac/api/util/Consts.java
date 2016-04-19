package com.medlinker.dentist.uac.api.util;


import java.util.regex.Pattern;

/**
 * 常量
 */
public interface Consts {


    Pattern PTN_ALL_NUMERIC = Pattern.compile("^[0-9]*$");
    Pattern PTN_FIVE_NUMERIC = Pattern.compile(".*\\d{5,}.*");
    Pattern PTN_RULE_NICK = Pattern.compile("^[a-zA-Z0-9\\x{4e00}-\\x{9fa5}_]+$");


    String tokenEncryptionKey = "medlinker^&%^&1991";

}
