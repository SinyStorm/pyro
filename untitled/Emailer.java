package controller.servlets;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;



/**
 * Created with IntelliJ IDEA.
 * User: Sid
 * Date: 03.12.13
 * Time: 17:20
 * To change this template use File | Settings | File Templates.
 */
public final class Emailer {

    public void sendTLS()
    {
        try {
            Email email = new SimpleEmail();
            email.setHostName("smtp.gmail.com");
            email.setSmtpPort(587);
            email.setAuthenticator(null);
            email.setTLS(true);
            email.setFrom("user@gmail.com");
            email.setSubject("TestMail");
            email.setMsg("This is a test mail ... :-)");
            email.addTo("user@mail.ru");
            email.send();
        } catch (EmailException ex) {
            ex.printStackTrace();
        }
    }


    public static void main(String[] args)
    {
        Emailer simpleMail = new Emailer();
        simpleMail.sendTLS();
    }
}
