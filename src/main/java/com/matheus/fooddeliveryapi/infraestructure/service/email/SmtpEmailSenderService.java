package com.matheus.fooddeliveryapi.infraestructure.service.email;

import com.matheus.fooddeliveryapi.core.email.EmailProperties;
import com.matheus.fooddeliveryapi.domain.exception.EmailException;
import com.matheus.fooddeliveryapi.domain.service.EmailSenderService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;

public class SmtpEmailSenderService implements EmailSenderService {

    protected Configuration configuration;
    protected JavaMailSender mailSender;
    protected EmailProperties emailProperties;

    public SmtpEmailSenderService(JavaMailSender mailSender, EmailProperties emailProperties,
                                  Configuration configuration) {
        this.mailSender = mailSender;
        this.emailProperties = emailProperties;
        this.configuration = configuration;
    }

    @Override
    public void send(Message message) {
        MimeMessage mimeMessage = getMimeMessage(message);

        mailSender.send(mimeMessage);
    }

    protected MimeMessage getMimeMessage(Message message) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

            helper.setFrom(emailProperties.getSender());
            helper.setTo(message.getRecipients().toArray(new String[0]));
            helper.setSubject(message.getSubject());
            helper.setText(processTemplate(message), true);

            return mimeMessage;
        } catch (Exception e) {
            throw new EmailException("It was not possible to send email", e);
        }
    }

    protected String processTemplate(Message message) {
        try {
            Template template = configuration.getTemplate(message.getBody());

            return FreeMarkerTemplateUtils.processTemplateIntoString(template, message.getVariables());
        } catch (Exception e) {
            throw new EmailException("Não foi possivel montar corpo do email", e);
        }
    }
}
