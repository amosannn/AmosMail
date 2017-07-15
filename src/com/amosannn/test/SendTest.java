package com.amosannn.test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JButton;
import javax.swing.JFrame;

import com.sun.mail.util.MailSSLSocketFactory;

public class SendTest {
	public static void main(String args[]){
		JFrame frame = new JFrame();
		frame.setTitle("AmosMail");
		frame.setSize(300, 200);
		frame.setLocation(400, 300);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton send = new JButton("Send Email");
		
		frame.add(send);
		
		frame.setVisible(true);
		
		send.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				sendEmail("xxx@qq.com", "xxx@qq.com", "", "This is AmosEmal test", "This is AmosEmal version 0.1. Have fun", "");
			}
		});
	}
	
	public static void sendEmail(String from, String to, String cc, String subject, String content, String fileUrl){
		Properties properties = System.getProperties();
		
		properties.put("mail.debug", "true");
		
		MailSSLSocketFactory mssf = null;
		try {
			mssf = new MailSSLSocketFactory();
			mssf.setTrustAllHosts(true);
			properties.put("mail.smtp.ssl.enable", "true");
			properties.put("mail.smtp.ssl.socketFactory", mssf);
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		
		String host = "smtp.qq.com";
		properties.put("mail.smtp.host", host);
		
		properties.put("mail.smtp.auth", "true");
		
		Session session = Session.getDefaultInstance(properties, new Authenticator(){
			public PasswordAuthentication getPasswordAuthentication(){
				return new PasswordAuthentication("xxx@qq.com", "xxxx");
			}
		});
		
		try{
			MimeMessage mimeMessage = new MimeMessage(session);
			mimeMessage.setFrom(new InternetAddress(from));
			mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			mimeMessage.setSubject(subject);
			mimeMessage.setText(content);
			
			Transport.send(mimeMessage);
			System.out.println("Send email success!!");
		} catch(MessagingException e){
			System.out.println("send failed... the exception is "+e);
		}
	}
}
