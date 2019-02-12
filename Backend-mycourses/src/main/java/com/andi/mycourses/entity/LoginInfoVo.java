package com.andi.mycourses.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import net.sf.json.JSONObject;

/**
 * @author andi
 */
@Getter
@Setter
@AllArgsConstructor
public class LoginInfoVo {
    private String email;
    private String password;
}


