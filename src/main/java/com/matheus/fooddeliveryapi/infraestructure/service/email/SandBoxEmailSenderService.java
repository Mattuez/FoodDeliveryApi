package com.matheus.fooddeliveryapi.infraestructure.service.email;

import com.matheus.fooddeliveryapi.core.email.EmailProperties;
import com.matheus.fooddeliveryapi.domain.exception.EmailException;
import com.matheus.fooddeliveryapi.domain.service.EmailSenderService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.Address;
import javax.mail.internet.MimeMessage;

@Slf4j
public class SandBoxEmailSenderService extends SmtpEmailSenderService implements EmailSenderService {
    public SandBoxEmailSenderService(JavaMailSender mailSender, EmailProperties emailProperties, Configuration configuration) {
        super(mailSender, emailProperties, configuration);
    }

    @Override
    public void send(Message message) {
        MimeMessage mimeMessage = getMimeMessage(message);

        mailSender.send(mimeMessage);
    }

    @Override
    protected MimeMessage getMimeMessage(Message message) {
        try {
            MimeMessage mimeMessage = super.getMimeMessage(message);
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

            helper.setTo("matheusmedeirosprogramacao@gmail.com");

            return mimeMessage;
        } catch (Exception e) {
            throw new EmailException("It was not possible to send email", e);
        }
    }
}
