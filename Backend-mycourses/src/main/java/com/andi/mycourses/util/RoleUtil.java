package com.andi.mycourses.util;

/**
 * @author andi
 */
public class RoleUtil {
    public static boolean isValidRole(String email)
    {
        String postFix = getPostFix(email);
        return  (postFix.equals("nju.edu.cn") || postFix.equals("smail.nju.edu.cn"));
    }
    public static String getRole(String email)
    {
        String postFix = getPostFix(email);
        if (postFix.equals("smail.nju.edu.cn"))
            return "student";
        else
            return "teacher";
    }

    private static String getPostFix(String email)
    {
        return email.split("@")[1];
    }
}
