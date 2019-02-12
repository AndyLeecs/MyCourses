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
    private void sendMail(String to, String subject, String context)
    {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setSubject(subject);
            helper.setTo(to);
            helper.setText(context, true);
            mailSender.send(message);
            System.out.println("mail send");
        }catch (MessagingException e)
        {
            e.printStackTrace();
        }
    }
}
