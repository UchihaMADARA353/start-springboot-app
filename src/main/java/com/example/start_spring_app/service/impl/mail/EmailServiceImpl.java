package com.example.start_spring_app.service.impl.mail;

import com.example.start_spring_app.dto.MailInfo;
import com.example.start_spring_app.dto.request.SignInrequestDTO;
import com.example.start_spring_app.entities.PersonalAccessToken;
import com.example.start_spring_app.repository.PersonalAccessTokenRepository;
import com.example.start_spring_app.service.EmailService;
import com.example.start_spring_app.utils.TokenEncryptor;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;
    private final PersonalAccessTokenRepository personalAccessTokenRepository;

    @Override
    public void generate(MailInfo mailInfo) {
        try {
            MimeMessage messageMime = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(messageMime, true, "UTF-8");

            String codeGenerated = generateCode();
            String htmlContent;

            try {
                htmlContent = loadEmailTemplate(codeGenerated, mailInfo.getName());
            } catch (IOException e) {
                throw new MessagingException("Impossible de charger le modèle d'email", e);
            }

            helper.setTo(mailInfo.getSendTo());
            helper.setSubject(mailInfo.getSubject());
            helper.setText(htmlContent, true);
            helper.setFrom("yaoghis9@gmail.com");
            mailSender.send(messageMime);

            // ✅ Chiffrer le token avant stockage
            String encryptedToken = TokenEncryptor.encrypt(codeGenerated);

            PersonalAccessToken accessToken = PersonalAccessToken.builder()
                    .email(mailInfo.getSendTo())
                    .token(encryptedToken)
                    .build();
            personalAccessTokenRepository.save(accessToken);
        } catch (MessagingException exception) {
            throw new RuntimeException("Erreur");
        } catch (Exception exception) {
            throw new RuntimeException("Erreur de chiffrement", exception);
        }
    }

    @Override
    public void sendMailUserAccount(MailInfo mailInfo) {
        try {
            MimeMessage messageMime = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(messageMime, true, "UTF-8");

            helper.setTo(mailInfo.getSendTo());
            helper.setSubject(mailInfo.getSubject());
            helper.setFrom("yaoghis9@gmail.com");

            // 1️⃣ Charger le fichier HTML
            String template = Files.readString(
                    new ClassPathResource("templates/reset-password.html").getFile().toPath(),
                    StandardCharsets.UTF_8
            );

            // 2️⃣ Remplacer les variables (exemple simple)
            String htmlContent = template
                    .replace("[[USERNAME]]", mailInfo.getName())
                    .replace("[[EMAIL]]", mailInfo.getEmail())
                    .replace("[[PASSWORD]]", mailInfo.getPassword());

            // 3️⃣ Mettre le contenu HTML
            helper.setText(htmlContent, true);

            mailSender.send(messageMime);
        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateCode() {
        Random random = new Random();
        int code = 1000 + random.nextInt(9000); // Entre 1000 et 9000
        return String.valueOf(code);
    }

    private String loadEmailTemplate(String code, String name) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("templates/active-account.html");
        assert inputStream != null;
        String template = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        template =  template.replace("[[NAME]]", name);
        template = template.replace("[[CODE]]", code);
        return template;
    }
}
