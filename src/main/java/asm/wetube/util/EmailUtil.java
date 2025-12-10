package asm.wetube.util;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailUtil {
    // Để test, bạn cần tạo "App Password" (Mật khẩu ứng dụng) trong cài đặt Google Account
    private static final String HOST_NAME = "smtp.gmail.com";
    private static final int TSL_PORT = 587;
    private static final String APP_EMAIL = "hungprokj@gmail.com"; // Thay email của bạn
    private static final String APP_PASSWORD = "zadf kfxx jwpy ajge"; // Thay mật khẩu ứng dụng

    public static void sendEmail(String toAddress, String subject, String message) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", HOST_NAME);
        props.put("mail.smtp.port", TSL_PORT);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(APP_EMAIL, APP_PASSWORD);
            }
        });

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(APP_EMAIL));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));
        msg.setSubject(subject);
        msg.setText(message); // Hoặc dùng msg.setContent(htmlCode, "text/html; charset=utf-8");

        Transport.send(msg);
    }
}