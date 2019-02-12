package com.andi.mycourses.service;

import com.andi.mycourses.entity.*;
import com.andi.mycourses.repo.BaseUserRepo;
import com.andi.mycourses.repo.StudentRepo;
import com.andi.mycourses.repo.TeacherRepo;
import com.andi.mycourses.repo.UserRepo;
import com.andi.mycourses.util.RoleUtil;
import com.andi.mycourses.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        }catch (Exception e)
        {
            return false;
        }
        return true;
    }
    public boolean changeSID(String email, Long sid)
    {
        //todo optional
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
                //todo 怎么检查optional
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
        System.out.println("code"+user.getCode());
        mailService.veri(user);
    }


    public User checkCode(String code)
    {
        User user = userRepo.findByCode(code);
        if (user != null)
        {
            //todo 重复点击链接了怎么办
            //userRepo.save(user);
        }
        return user;
    }
}
