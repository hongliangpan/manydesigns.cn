package com.manydesigns.mail.utils;

import java.util.Set;

import com.manydesigns.mail.queue.MailQueue;
import com.manydesigns.mail.queue.QueueException;
import com.manydesigns.mail.queue.model.Email;
import com.manydesigns.mail.queue.model.Recipient;
import com.manydesigns.portofino.PortofinoProperties;
import com.manydesigns.portofino.utils.BaseContextUtils;

public class MailUtils {
	private static MailQueue mailQueue;
	private static String from;

	public static void sendMail(String to, String subject, String body) {
		if (mailQueue == null) {
			throw new UnsupportedOperationException("Mail queue is not enabled");
		}

		Email email = new Email();
		email.getRecipients().add(new Recipient(Recipient.Type.TO, to));
		email.setFrom(from);
		email.setSubject(subject);
		email.setHtmlBody(body);
		try {
			mailQueue.enqueue(email);
		} catch (QueueException e) {
			throw new RuntimeException(e);
		}
	}

	public static void sendMail(Set<String> toMails, Set<String> ccMails, String subject, String body) {
		if (mailQueue == null) {
			throw new UnsupportedOperationException("Mail queue is not enabled");
		}

		Email email = new Email();
		for (String to : toMails) {
			email.getRecipients().add(new Recipient(Recipient.Type.TO, to));
		}
		for (String cc : ccMails) {
			email.getRecipients().add(new Recipient(Recipient.Type.CC, cc));
		}
		email.setFrom(from);
		email.setSubject(subject);
		email.setHtmlBody(body);
		try {
			mailQueue.enqueue(email);
		} catch (QueueException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @return mailQueue - {return content description}
	 */
	public static MailQueue getMailQueue() {
		return mailQueue;
	}

	/**
	 * @param mailQueue - {parameter description}.
	 */
	public static void setMailQueue(MailQueue mailQueue) {
		MailUtils.mailQueue = mailQueue;

		from = BaseContextUtils.getConfiguration().getString(PortofinoProperties.MAIL_FROM, "example@example.com");
	}

}
