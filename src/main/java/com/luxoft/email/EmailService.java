package com.luxoft.email;

import com.luxoft.exceptions.ChoiceCategoriesException;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.Properties;


/**
 * Created by iivaniv on 31.12.2015.
 */
@Service
public class EmailService {

    private String to = "ivanivivan993@gmail.com", from = "ivanivivan993@gmail.com", subject = "Staging Area Notification";

    public void sendMail() {

        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);

            MimeMessage message = new MimeMessage(getSession());
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);

            Date date = new Date();
            Multipart multipart = forDev("Gemini", "Main Data", "loading file", date, new ChoiceCategoriesException());
            message.setContent(multipart);
            Transport.send(message);

            MimeMessage message1 = new MimeMessage(getSession());
            message1.setFrom(new InternetAddress(from));
            message1.addRecipient(Message.RecipientType.TO, new InternetAddress("dasd.sdasd@kk.com"));
            message1.setSubject(subject);

            Date date1 = new Date();
            Multipart multipart1 = forDev("Gemini", "Main Data", "loading file", date1, new ChoiceCategoriesException());
            message1.setContent(multipart1);
            Transport.send(message1);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private Session getSession() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "false");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        System.out.println("properties....");
        return Session.getDefaultInstance(properties
                , new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("ivanivivan993@gmail.com", "Vbhjy1963");
                    }
                }
        );
    }

    private String addHtml(String appName, String location, String cause, Date date) {
        String html = "<html><h4 style=\"color:red\">Fail in " + appName + " application</h4>" +
                "<h3 style=\"color: red\">On screen : " + location + "</h3>" +
                "<p>Time : " + date.toString() + "</p>" +
                "<p>" + cause + "</p>" +
                "<br/></html>";
        return html;
    }

    private Multipart forDev(String appName, String location, String cause, Date time, Throwable exception) {
        Multipart multipart = new MimeMultipart();

        MimeBodyPart textPart = new MimeBodyPart();
        MimeBodyPart htmlPart = new MimeBodyPart();
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        try {
            String htmlContent = addHtml(appName, location, cause, time);
            htmlPart.setContent(htmlContent, "text/html");
            multipart.addBodyPart(htmlPart);

            exception.printStackTrace(pw);
            textPart.setText(sw.toString());
            multipart.addBodyPart(textPart);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return multipart;
    }
}
