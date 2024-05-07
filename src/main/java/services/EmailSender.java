package services;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
public class EmailSender {

    // Read the image file and convert it to a base64 encoded string
    public static void sendEmail(String to, String descri, String subject) throws MessagingException {
        // Setup mail server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com"); // Change to your SMTP server
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.port", "587");
        // Get Session
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("inesgharbi366@gmail.com", "ysfw uspa gmsf pplm\n"); // Change to your email and password
            }
        });

        // Create message
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress("inesgharbi366@gmail.com")); // Change to your email
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

        String htmlMsg =descri
                + "<img src='../images/arteco.png'>";

        message.setSubject(subject);
        message.setContent(htmlMsg, "text/html");
        message.setText("Dear Client, \n Thank you for you reservation , We hope you would enjoy your event ");

        // Send message
        Transport.send(message);
    }
}

