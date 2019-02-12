package com.andi.mycourses.util;

import java.util.UUID;

/**
 * @author andi
 */
public class UUIDUtil {
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }
}