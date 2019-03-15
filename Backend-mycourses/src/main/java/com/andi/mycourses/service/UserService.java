package com.andi.mycourses.service;

import com.andi.mycourses.entity.BaseUser;
import com.andi.mycourses.entity.User;
import com.andi.mycourses.repo.BaseUserRepo;
import com.andi.mycourses.repo.StudentRepo;
import com.andi.mycourses.repo.UserRepo;
import com.andi.mycourses.util.UUIDUtil;
import com.andi.mycourses.vo.LoginInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @author andi
 */
@Service
public class UserService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    MailService mailService;
    @Autowired
    BaseUserRepo baseUserRepo;
    @Autowired
    StudentRepo studentRepo;

    public boolean logout(String email)
    {
        try {
            studentRepo.logout(email);
            BaseUser user = baseUserRepo.findById(email).get();
            user.setPassword(UUIDUtil.getUUID());
            baseUserRepo.save(user);
        }catch (Exception e)
        {
            return false;
        }
        return true;
    }
    public boolean changeSID(String email, Long sid)
    {
        try {
            studentRepo.setSID(email, sid);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public boolean changeName(String email, String newName)
    {
        try {
            baseUserRepo.setName(email, newName);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public boolean login(LoginInfoVo loginInfoVo)
    {
        String email = loginInfoVo.getEmail();
        String password;
        try {
                BaseUser baseUser = baseUserRepo.findById(email).get();
                password = baseUser.getPassword();

        }catch (Exception e)
        {
            return false;
        }
        return password.equals(loginInfoVo.getPassword());
    }
    public void register(String email)
    {
        User user = new User();
        user.setEmail(email);
        user.setCode(UUIDUtil.getUUID());
        try {
            userRepo.save(user);
        }catch (Exception e)
        {
            System.out.println("duplicate register");
            user = userRepo.findByEmail(email);
            user.setCode(UUIDUtil.getUUID());
            userRepo.save(user);
        }
        System.out.println("code " + user.getCode());
        mailService.veri(user);
    }


    public User checkCode(String code)
    {
        User user = userRepo.findByCode(code);
        if (user != null)
        {
            userRepo.save(user);
        }
        return user;
    }
}
