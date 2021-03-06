package be.kuleuven.rega.phylogeotool.pplacer;

import java.io.File;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import be.kuleuven.rega.phylogeotool.settings.Settings;

public class Mail {
	private String username = "ue711728";
	private String password = "BioInformatics007";
	private Properties props;

	public Mail() {
		props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtps.kuleuven.be");
		props.put("mail.smtp.port", "587");
	}
	
	public void sendEmail(String recipient, String pplacerId, String resultMessage) {
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("phylogeotool@kuleuven.be"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
			if(resultMessage != null && resultMessage.contains("BLAST")) {
				message.setSubject("PPlacing failed");
				message.setText("Dear User,"
						+ "\n\nPPlacing could not be initiated, since the sequence did not meet the inclusion criteria.\n"
						+ "\nPlease check that the sequence is sufficiently long and matches the characteristics of the represented dataset.");
			} else if(resultMessage!= null && resultMessage.equals("PPlacer crashed.")) {
				message.setSubject("PPlacing failed");
				message.setText("Dear User,"
						+ "\n\nPPlacer exited with an error.\n"
						+ "\nPlease contact the Phylogeotool-team for further follow-up on the matter.");
			} else {
				message.setSubject("Your PPlacing job has finished");
				message.setText("Dear User,"
						+ "\n\nYour PPlacing job has recently finished and can be reviewed here: " + Settings.getInstance().getFull_url() + File.separator + "root/1?pplacer="
						+ pplacerId);
			}

			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args) {
		Mail mail = new Mail();
		mail.sendEmail("ewout.ve@gmail.com", "CRQdxm0J","");
	}
}