package com.example.lihini_electrical.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import lombok.Setter;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendMailController {
    @FXML
    private AnchorPane MainAnchorpane;

    @FXML
    private ImageView logo;

    @FXML
    private Label title;

    @FXML
    private TextField subjectTextfield;

    @FXML
    private TextArea bodyTextarea;

    @FXML
    private Button sendButton;

    @Setter
    private String customerEmail;

    @FXML
    void sendOnAction(ActionEvent event) {
        if (customerEmail == null) {
            return;
        }

        final String FROM = "tharusanduni33@gmail.com";

        String subject = subjectTextfield.getText();
        String body = bodyTextarea.getText();

        if (subject.isEmpty() || body.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Subject and body must not be empty!").show();
            return;
        }
        sendEmailWithGmail(FROM, customerEmail, subject, body);
    }

    private void sendEmailWithGmail(String from, String to, String subject, String messageBody) {
        String PASSWORD = "cjas jcjm rfjf uipp";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {

            // Replace with your email and app password
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(messageBody);
            Transport.send(message);

            new Alert(Alert.AlertType.INFORMATION, "Email sent successfully.").show();
        } catch (MessagingException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to send email....!!").show();
        }
    }
}
