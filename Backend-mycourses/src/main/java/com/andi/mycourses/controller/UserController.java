package com.andi.mycourses.controller;

import com.andi.mycourses.entity.User;
import com.andi.mycourses.service.UserService;
import com.andi.mycourses.util.ConstUtil;
import com.andi.mycourses.util.RoleUtil;
import com.andi.mycourses.vo.LoginInfoVo;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author andi
 */
@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/username")
    public JSONObject changeName(HttpServletRequest request, @RequestBody JSONObject object)
    {
        String email = SessionUtil.getSessionEmail(request);
        String newName = object.getString("name");
        userService.changeName(email,newName);
        return new JSONObject();
    }

    @PostMapping("/sid")
    public JSONObject changeSID(HttpServletRequest request, @RequestBody JSONObject object)
    {
        String email = SessionUtil.getSessionEmail(request);
        Long sid = object.getLong("sid");
        userService.changeSID(email,sid);
        return new JSONObject();
    }



    @PostMapping("/logout")
    public JSONObject logout(HttpServletRequest request)
    {
        System.out.println("logout");
        String email = SessionUtil.getSessionEmail(request);
        userService.logout(email);
        return new JSONObject();
    }

    @PostMapping("/login")
    public JSONObject login(HttpServletRequest request, @RequestBody LoginInfoVo loginInfoVo) {
        System.out.println("login");
        boolean success = userService.login(loginInfoVo);
        JSONObject object = new JSONObject();
        object.put("valid",success);
        String role = RoleUtil.getRole(loginInfoVo.getEmail());
        object.put("role",role);
        if (success)
        {
            request.getSession().setAttribute("email",loginInfoVo.getEmail());
            request.getSession().setMaxInactiveInterval(-1);
            System.out.println("login success");
        }
        return object;
}
    @PostMapping("/verify")
    public JSONObject verify(@RequestBody JSONObject object)
    {
        System.out.println("verify");
        String email = (String)object.get("email");
        userService.register(email);
        return new JSONObject();
    }

    @RequestMapping(value="/checkCode")
    public void checkCode(HttpServletRequest request, HttpServletResponse response, String code)
    {
        User user = userService.checkCode(code);
        if (user != null) {
            String role = RoleUtil.getRole(user.getEmail());
            try {
                HttpSession session = request.getSession(true);
                session.setAttribute("email", user.getEmail());
                String url = response.encodeURL(ConstUtil.baseURL + role + "/register");
                System.out.println(url);
                response.sendRedirect(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
