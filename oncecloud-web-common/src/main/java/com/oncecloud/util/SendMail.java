package com.oncecloud.util;

/*import java.util.Date;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public abstract class SendAlarmMail {

    private JavaMailSenderImpl mailSender;

    protected abstract SimpleMailMessage initMailMessage();

    public void send() {
        SimpleMailMessage message = initMailMessage();
        try {
            //message 状态操作
            message.setText(new Date().toString() + " : Hello World");
            //mailSender 状态操作
            mailSender.send(message);
        } catch (MailException e) {
            System.err.println(e.getMessage());
        }
    }

    public JavaMailSenderImpl getMailSender() {
        return mailSender;
    }

    public void setMailSender(JavaMailSenderImpl mailSender) {
        this.mailSender = mailSender;
    }
}*/
import  java.util.Properties;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/** 
* 本类测试简单邮件 
* 直接用邮件发送
*  @author  Administrator
*
 */ 
public   class  SendMail { 
	public ApplicationContext ctx = null; 
	public void sendMail(String text, String to ) {
		ctx = new ClassPathXmlApplicationContext("com/oncecloud/config/applicationContext-mail.xml");  
		JavaMailSenderImpl senderImpl  = (JavaMailSenderImpl) ctx.getBean("mailSender");  
		SimpleMailMessage mailMessage  =  (SimpleMailMessage)ctx.getBean("mailMessage");
		
        mailMessage.setTo( to ); 
        mailMessage.setText( text ); 
   
        Properties prop  =   new  Properties() ;
        prop.put( " mail.smtp.auth " ,  " true " ) ;  //  将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确 
        prop.put( " mail.smtp.timeout " ,  " 25000 " ) ; 
        senderImpl.setJavaMailProperties(prop);  
        // 发送邮件  
        senderImpl.send(mailMessage); 
    } 
 } 




