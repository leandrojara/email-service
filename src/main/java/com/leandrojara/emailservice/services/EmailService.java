package com.leandrojara.emailservice.services;

import com.leandrojara.emailservice.models.EmailRequest;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
public class EmailService {

    public void sendEmail(EmailRequest emailRequest) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("your-email@gmail.com", "your-password");
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("your-email@gmail.com", emailRequest.getSenderName()));
            List<InternetAddress> list = new ArrayList<>();
            for (String s : emailRequest.getRecipients()) {
                InternetAddress internetAddress = new InternetAddress(s);
                list.add(internetAddress);
            }
            message.setRecipients(
                    Message.RecipientType.TO,
                    list.toArray(new InternetAddress[0])
            );
            message.setSubject(emailRequest.getSubject());

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(emailRequest.getBody(), "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            if (emailRequest.getAttachments() != null) {
                for (String filePath : emailRequest.getAttachments()) {
                    MimeBodyPart attachmentPart = new MimeBodyPart();
                    DataSource source = new FileDataSource(new File(filePath));
                    attachmentPart.setDataHandler(new DataHandler(source));
                    attachmentPart.setFileName(new File(filePath).getName());
                    multipart.addBodyPart(attachmentPart);
                }
            }

            message.setContent(multipart);

            Transport.send(message);

            System.out.println("Email sent successfully to: " + emailRequest.getRecipients());
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
