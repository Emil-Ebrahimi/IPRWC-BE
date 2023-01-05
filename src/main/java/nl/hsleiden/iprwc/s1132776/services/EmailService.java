package nl.hsleiden.iprwc.s1132776.services;

import nl.hsleiden.iprwc.s1132776.utils.EmailUtil;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.transaction.Transactional;
import java.util.Properties;

@Transactional
@Service
public class EmailService {

    private final String fromEmail = "iprwc@emil.nl";
    private final String password = "23,ATiRrydR,?";

    private void sendEmail(String to, String subject, String body) {


        Properties props = new Properties();
        props.put("mail.smtp.host", "mail.emil.nl");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };

        Session session = Session.getInstance(props, auth);

        EmailUtil.sendEmail(session, to,subject, body);

    }

    public void sendPasswordResetEmail(String to, String code) {
        String subject = "Reset your password";
        String body = "<h2>Reset password</h2>\n" +
                "\n" +
                "<p>Use this code to reset your password in the desktop application.</p>\n" +
                "<p><h1>" + code + "</h1></p>";
        sendEmail(to, subject, body);
    }

    public void sendReservationConfirmation(String to, String start, String end, String location) {
        String subject = "Reservation confirmation";
        String body = "<h2>Reservation confirmation</h2>\n" +
                "\n" +
                "<p>Location: <strong>"+ location+"</strong></p>\n" +
                "<p>U created an reservation for <strong>"+ start + " - " + end +"</strong>.</p>\n";

        sendEmail(to, subject, body);
    }
}
