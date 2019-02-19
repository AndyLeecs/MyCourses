package com.andi.mycourses.util;

import com.andi.mycourses.entity.Comment;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author andi
 */
public class JsonUtil {
    public static JSONObject getValidObject(boolean valid)
    {
        JSONObject object = new JSONObject();
        object.put("valid",valid);
        return object;
    }

    public static JSONObject getJsonForComment(Comment comment)
    {
        JSONObject object = new JSONObject();
        object.put("id", comment.getId());
//            if (comment.getReplyTo()!=null)
//                object.put("reply_to_id",comment.getReplyTo().getId());
        object.put("label",getLabelForComment(comment));
        if (comment.getChildComments() != null && comment.getChildComments().size() > 0)
            object.put("children", getChildrenList(comment));
        return object;
    }

    public static JSONObject getLabelForComment(Comment comment)
    {
        JSONObject object = new JSONObject();
        object.put("writer_name", comment.getWriter().getName());
        object.put("content", comment.getContent());
        object.put("time", DateUtil.getTimeStringForShow(comment.getTime()));
        return object;
    }

    public static JSONArray getChildrenList(Comment comment)
    {
        JSONArray array = new JSONArray();
        for (Comment child : comment.getChildComments())
        {
            array.add(getJsonForComment(child));
        }
        return array;
    }
}
