package com.dbi.shyam.email;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.dbi.shyam.entities.User;

@Service
@EnableAsync
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private ResourceLoader resourceLoader;

	@Bean(name = "threadPoolTaskExecutor")
	public Executor threadPoolTaskExecutor() {
		return new ThreadPoolTaskExecutor();
	}

	@Async("threadPoolTaskExecutor")
	public void send(User user, boolean isHtml) {
		new Thread(() -> {
			if (isHtml) {
				try {
					EmailTemplate template = new EmailTemplate("default-html", resourceLoader);

					Map<String, String> replacements = new HashMap<String, String>();
					replacements.put("user", user.getFirstName());
					replacements.put("today", String.valueOf(LocalDate.now()));

					String message = template.getTemplate(replacements);
					String subject = "THANK YOU FOR YOUR SIGNUP";

					Email email = new Email(user.getEmail(), subject, message, false);

					sendHtmlMail(email);
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			} else {
				EmailTemplate template = new EmailTemplate("default-plain", resourceLoader);

				Map<String, String> replacements = new HashMap<String, String>();
				replacements.put("user", user.getFirstName());
				replacements.put("today", String.valueOf(LocalDate.now()));

				String message = template.getTemplate(replacements);
				String subject = "THANK YOU FOR YOUR SIGNUP";

				Email email = new Email(user.getEmail(), subject, message, false);

				sendPlainTextMail(email);
			}
		}).start();
	}

	private void sendHtmlMail(Email email) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mailMessage = new MimeMessageHelper(mimeMessage);
		mailMessage.setTo(email.getTo());
		mailMessage.setSubject(email.getSubject());
		mailMessage.setText(email.getMessage());
		javaMailSender.send(mimeMessage);
	}

	private void sendPlainTextMail(Email email) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(email.getTo());
		mailMessage.setSubject(email.getSubject());
		mailMessage.setText(email.getMessage());
		javaMailSender.send(mailMessage);
	}

}