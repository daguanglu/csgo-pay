package com.yangguanglu.sptest.utile;

import javax.mail.*;

import javax.mail.internet.InternetAddress;

import javax.mail.internet.MimeMessage;

import javax.mail.internet.MimeMessage.RecipientType;

import java.util.Properties;

public class SendEmailUtile {

    public static void send(String email, String title,String msg) {

// 创建Properties 类用于记录邮箱的一些属性

        Properties properties = new Properties();

// 表示SMTP发送邮件，必须进行身份验证

        properties.put("mail.smtp.auth", "true");

//此处填写SMTP服务器

        properties.put("mail.smtp.host", "smtp.qq.com");

//端口号，QQ邮箱端口587

        properties.put("mail.smtp.port", "587");

// 此处填写，写信人的账号

        properties.put("mail.user", "353633589@qq.com");

// qq邮箱产生--授权码

        properties.put("mail.password", "bamemnviervzbggi");

// 构建授权信息，用于进行SMTP进行身份验证

        Authenticator authenticator = new Authenticator() {

            @Override

            protected PasswordAuthentication getPasswordAuthentication() {

// 用户名、密码

                String userName = properties.getProperty("mail.user");

                String password = properties.getProperty("mail.password");

                return new PasswordAuthentication(userName, password);

            }

        };

// 使用环境属性和授权信息，创建邮件会话

        Session session = Session.getInstance(properties, authenticator);

// 创建邮件消息

        MimeMessage message = new MimeMessage(session);


        try {
            // 设置发件人

            InternetAddress from = new InternetAddress(properties.getProperty("mail.user"));

            message.setFrom(from);

//收件人

            InternetAddress to = new InternetAddress(email);

            message.setRecipient(RecipientType.TO, to);

// 设置邮件标题

            message.setSubject(title);

// 设置邮件的内容体

            message.setContent(msg, "text/html;charset=UTF-8");

//发送邮件

            Transport.send(message);
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("邮件发送失败");
        }

    }

}