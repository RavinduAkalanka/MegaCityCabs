package com.bms.util;

import java.io.InputStream;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailUtil {

    private static String FROM_EMAIL;
    private static String PASSWORD;
    private static String HOST;
    private static String PORT;
    private static String AUTH;
    private static String STARTTLS_ENABLE;

    static {
        // Load email configurations from email.properties file
        try (InputStream inputStream = EmailUtil.class.getClassLoader().getResourceAsStream("email.properties")) {
            if (inputStream == null) {
                throw new RuntimeException("email.properties file not found in the classpath!");
            }

            Properties properties = new Properties();
            properties.load(inputStream);

            // Load email configurations
            FROM_EMAIL = properties.getProperty("email.from");
            PASSWORD = properties.getProperty("email.password");
            HOST = properties.getProperty("email.host");
            PORT = properties.getProperty("email.port");
            AUTH = properties.getProperty("email.auth");
            STARTTLS_ENABLE = properties.getProperty("email.starttls.enable");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load email configurations from email.properties", e);
        }
    }

    public static void sendEmail(String toEmail, String subject, String body) {
        // Set up mail server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", HOST);
        properties.put("mail.smtp.port", PORT);
        properties.put("mail.smtp.auth", AUTH);
        properties.put("mail.smtp.starttls.enable", STARTTLS_ENABLE);

        // Create a session with authentication
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, PASSWORD);
            }
        });

        try {
            // Create a default MimeMessage object
            MimeMessage message = new MimeMessage(session);

            // Set From: header field
            message.setFrom(new InternetAddress(FROM_EMAIL));

            // Set To: header field
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));

            // Set Subject: header field
            message.setSubject(subject);

            // Set the actual message
            message.setText(body);

            // Send message
            Transport.send(message);
            System.out.println("Email sent successfully to " + toEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to send email: " + e.getMessage());
        }
    }
}