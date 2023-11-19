package com.chubb.QuickCars.services;


import com.chubb.QuickCars.models.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MailService {



    @Autowired
    RestTemplate restTemplate;

    public ResponseEntity<String> sendMail(Mail mail){
        System.out.println(1);
        String url="https://email-riwn.onrender.com/sendMail";
        System.out.println(url);
        System.out.println(mail.getEmail());
        System.out.println(mail.getSubject());
        System.out.println(mail.getText());
        ResponseEntity<String> result=restTemplate.postForEntity(url,mail, String.class);
        System.out.println(2);

        System.out.println(result);
        return  result;
    }
}
