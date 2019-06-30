package utils;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class EmailSender {
    public static void sendEmail(String message) throws EmailException {
        Email email = new SimpleEmail();
        email.setHostName("smtp.googlemail.com");
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator("lex12moto", "2@Qwerty"));
        email.setSSLOnConnect(true);
        email.setFrom("lex12moto@gmail.com");
        email.setSubject("Automation Test By Leonid Borkin");
        email.setMsg(message + "Ignore this mail it's by Leonid Borkin for Automation");
        email.addTo("Leonidborkin@gmail.com");
        email.send();
    }
}
