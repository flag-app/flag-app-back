package com.flag.flag_back.Controller;

import com.flag.flag_back.Model.User;
import com.flag.flag_back.Repository.UserRepository;
import com.flag.flag_back.config.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.mail.javamail.JavaMailSender;

import java.security.SecureRandom;

import static com.flag.flag_back.config.BaseResponseStatus.*;

@Api(description = "비밀번호 재발급 구현한 Mail Controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class MailController {
    // 비밀번호 생성에 필요한 문자열들
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_=+[]{}|;:,.<>?";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender javaMailSender; // JavaMailSender Bean 주입

    public MailController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @PostMapping("/reset-password")
    @Operation(summary = "임시 비밀번호 전송", description = "이메일로 임시 비밀번호를 보내는 API")
    public BaseResponse<String> sendPasswordResetLink(@RequestParam(value = "email", required = false) String email) {
        System.out.print(email);
        User user = userRepository.findUserByEmail(email);

        if (user != null) {
            // 임시 비밀번호 생성 및  이메일 전송
            sendPasswordResetEmail(user);
            return new BaseResponse<>(TEMP_PASSWORD_SENT);
        } else {
            return new BaseResponse<>(INVALID_USER);

        }
    }

    private String generateTemporaryPassword() {
        String allCharacters = LOWERCASE + UPPERCASE + DIGITS + SPECIAL_CHARACTERS;

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        int passwordLength = random.nextInt(9) + 8; // 8~16 중 랜덤 길이
        for (int i = 0; i < passwordLength; i++) {
            int randomIndex = random.nextInt(allCharacters.length());
            password.append(allCharacters.charAt(randomIndex));
        }

        return password.toString();
    }

    // 임시 비밀번호 전송 메서드
    private void sendPasswordResetEmail(User user) {
        String temporaryPassword = generateTemporaryPassword();

        user.setPassword(temporaryPassword);
        userRepository.save(user);

        // 이메일 전송 설정
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        //mailMessage.setFrom("0@naver.com"); //Flag팀 계정으로 수정
        mailMessage.setFrom("team.flagapp@gmail.com");  //Flag팀 계정으로 수정
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Flag 임시비밀번호가 발급되었습니다.");
        mailMessage.setText("Flag 임시 비밀번호:\n" + temporaryPassword);

        // 이메일 전송
        javaMailSender.send(mailMessage);
    }

}
