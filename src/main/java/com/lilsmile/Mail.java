package com.lilsmile;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by Smile on 05.11.15.
 */
public class Mail {

    public void  sendEmail(String body)
    {
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        final String FROM = "igorvviha@mail.ru";
        // Get a Properties object
        Properties props = System.getProperties();
        props.setProperty("mail.smtps.host", "smtp.mail.ru");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.setProperty("mail.smtps.auth", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("igorvviha@mail.ru","146146i");
            }
        });

        // -- Create a new message --
        final MimeMessage msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(FROM));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress("i.got.that.flower@gmail.com"));
            msg.setSubject("Answers");
            msg.setText(body);

            Transport.send(msg);
        } catch (MessagingException e) {
            e.printStackTrace();
        }


    }

}
