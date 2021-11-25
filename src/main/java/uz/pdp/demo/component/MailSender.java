package uz.pdp.demo.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.UUID;

@Component
public class MailSender {

    @Autowired
    JavaMailSender mailSender;

    public boolean send(String to, String text) throws MessagingException {
        String from = "management@gmail.com";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setSubject("Information!");
        helper.setFrom(from);
        helper.setTo(to);
        helper.setText(text, true);
        mailSender.send(message);
        return true;
    }

    public boolean mailTextAdd(String email, String code, String pass) throws MessagingException {
        String link = "http:localhost:8080/api/user/verifyEmail?email=" + email +"&code="+code;
        String text =
                 "<a href=\""+link+"\" style=\"padding: 10px 15px; background-color: darkslateblue; color: white; text-decoration: none; border-radius: 4px; margin: 10px; display: flex; max-width: 120px;\">Emailni tasdiqlash</a>\n" +
                         "<br>\n" +
                         "<p>Parolingiz: <b> "+ pass +"</b></p>\n" +
                         "<br>\n" +
                         "<p style=\"color: red\"><b>Iltimos uni hech kimga bermang!</b></p>";

        return send(email,text);
    }

    public boolean mailTextAddTask(String email, String name, UUID id) throws MessagingException {
        String link = "http:localhost:8080/api/task/" + id;
        String text = "You have been given a task called " + name + "." + "<br>" + "<a href=\""+link+"\" style=\"padding: 10px 15px; background-color: darkslateblue; color: white; text-decoration: none; border-radius: 4px; margin: 10px; display: flex; max-width: 120px;\">View task</a>\n" +
                "<br>\n";
        return send(email, text);
    }

    public boolean mailTextEditTask(String email, String name, UUID id) throws MessagingException {
        String link = "http:localhost:8080/api/task/" + id;
        String text = "Sizning <b>" + name + "</b> nomli vazifangiz o'zgartirildi." + "<br>" + "<a href=\""+link+"\" style=\"padding: 10px 15px; background-color: darkslateblue; color: white; text-decoration: none; border-radius: 4px; margin: 10px; display: flex; max-width: 120px;\">View task</a>\n" +
                "<br>\n";
        return send(email, text);
    }

    public boolean mailTextTaskCompleted(String emailGiver, String emailTaker, String taskName) throws MessagingException {
        String text = "<b>" + emailTaker + "</b> - The <b>"+ taskName + "</b> task you attached to the user is complete.";

        return send(emailGiver,text);
    }

    public void mailTextTurniketStatus(String email, boolean enabled) throws MessagingException {
        String stat = "Disabled";
        if (enabled)
          stat = "Enabled!";
        String text = "Attention! Turniket status has changed. Current status: <b>" + stat + " </b>";

        send(email, text);
    }

    public boolean mailTextEdit(String email) {
        return true;
    }
}
