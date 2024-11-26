package codebase.EmailSender;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {
    private static final String EMAIL_FROM = "thefreezeclothing@gmail.com";  // Địa chỉ email của bạn
    private static final String EMAIL_PASSWORD = "ehpk zwda tvtd ricc";     // Mật khẩu email của bạn

    public static boolean sendEmail(String to, String subject, String content) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP HOST
        props.put("mail.smtp.port", "587"); // TLS 587 SSL 465
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Tạo Authenticator
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_FROM, EMAIL_PASSWORD);
            }
        };

        // Tạo phiên làm việc
        Session session = Session.getInstance(props, auth);

        // Tạo một tin nhắn
        MimeMessage msg = new MimeMessage(session);

        try {
            // Kiểu nội dung
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");

            // Người gửi
            msg.setFrom(new InternetAddress(EMAIL_FROM));

            // Người nhận
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));

            // Tiêu đề email
            msg.setSubject(subject);

            // Ngày gửi
            msg.setSentDate(new Date());

            // Nội dung
            msg.setContent(content, "text/HTML; charset=UTF-8");

            // Gửi email
            Transport.send(msg);
            System.out.println("Gửi email thành công");
            return true;
        } catch (MessagingException e) {
            // Chỉ in thông điệp lỗi
            System.err.println("Gặp lỗi trong quá trình gửi email: " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1; i++) {
            boolean result = sendEmail("abc@gmail.com", 
                                       "Test Email " + System.currentTimeMillis(), 
                                       "Đây là phần nội dung thử nghiệm " + System.currentTimeMillis());
            if (result) {
                System.out.println("Email " + (i+1) + " đã được gửi thành công.");
            } else {
                System.out.println("Gửi email " + (i+1) + " không thành công.");
            }
        }
    }
}
