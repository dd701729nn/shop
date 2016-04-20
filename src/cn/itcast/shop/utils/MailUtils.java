package cn.itcast.shop.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

/**
 * 邮件发送工具类
 * @author Administrator
 *
 */
public class MailUtils {

	/**
	 * 发送邮件的方法
	 * @param to收件人
	 * @param code激活码
	 */
	public static void sendMail(String to,String code){
		/**
		 * 1.获得一个Session对象
		 * 2.创建一个代表邮件的对象Message
		 * 3.发送邮件Transport
		 */
		
		//1.获得链接对象
		Properties props = new Properties();
		props.setProperty("mail.host", "localhost");
		Session session = Session.getInstance(props, new Authenticator() {
			
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				
				return new PasswordAuthentication("service@shop.com","111");
			}
		});
		//创建一个邮件对象
		Message message = new MimeMessage(session);
		//设置发件人
		try {
			message.setFrom(new InternetAddress("service@shop.com"));
			//设置收件人
			message.addRecipient(RecipientType.TO, new InternetAddress(to));
			//抄送CC 密送BCC
			message.setSubject("来自购物天堂的官方激活邮件");
			//设置邮件的正文
			message.setContent("<h1>来自购物天堂的观法激活邮件！点击下面链接完成激活操作</h1><h3><a href='http://192.168.0.101/shop/user_active.action?code="+code+"'>http://192.168.0.101/shop/user_active.action?code="+code + "</a></h3>", "text/html;charset=UTF-8");
			//3.发送邮件
			Transport.send(message);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		sendMail("bbb@shop.com","1111111111111111");
	}
}
