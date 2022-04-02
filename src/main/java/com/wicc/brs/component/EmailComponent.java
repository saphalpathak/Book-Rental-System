package com.wicc.brs.component;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.stereotype.Component;

//sending email component
@Component
public class EmailComponent {
    public void send(String receiver,String subject,String message){
        try {
            Email email = new SimpleEmail();
            email.setHostName("smtp.googlemail.com");
            email.setSmtpPort(465);
            email.setAuthentication("pemapathak2000@gmail.com","prihovplawjkzfug");
            email.setSSL(true);
            email.setFrom("pemapathak2000@gmail.com");
            email.setSubject(subject);
            email.setMsg(message);
            email.addTo(receiver);
            email.send();
            System.out.println("Sent");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Failed");
        }
    }
}
