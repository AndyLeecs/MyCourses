package com.andi.mycourses.service;

import com.andi.mycourses.entity.User;
import com.andi.mycourses.util.ConstUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author andi
 */
//todo 激活之后清空激活码
@Service
public class MailService {
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String from;

    public void veri(User user)
    {
        String subject = "来自mycourses的激活邮件";
        String code = user.getCode();
        String context = "<a href=\"http://localhost:8081/api/v1/checkCode?code="+code+"\">激活点我</a>";
        sendMail(user.getEmail(),subject,context);
    }

    private MimeMessageHelper getHelper(MimeMessage message) throws MessagingException
    {
        return new MimeMessageHelper(message, true);
    }

    private void send(MimeMessageHelper helper, String subject, String context, MimeMessage message) throws MessagingException{
        helper.setFrom(from);
        helper.setSubject(subject);
        helper.setText(context, true);
        mailSender.send(message);
    }

    public void sendMail(String[] to, String subject, String context)
    {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = getHelper(message);
            helper.setTo(to);
            send(helper, subject, context, message);
        }catch (MessagingException e){
            e.printStackTrace();
        }
    }

    public void sendMail(String to, String subject, String context)
    {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = getHelper(message);
            helper.setTo(to);
            send(helper, subject, context, message);
        }catch (MessagingException e){
            e.printStackTrace();
        }
    }
}
