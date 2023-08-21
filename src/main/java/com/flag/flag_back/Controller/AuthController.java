//package com.flag.flag_back.Controller;
//테스트
//import com.flag.flag_back.Dto.*;
//import com.flag.flag_back.Model.User;  // 바뀐 부분
//import com.flag.flag_back.Repository.UserRepository;
//import com.flag.flag_back.service.UserService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.Parameter;
//import io.swagger.v3.oas.annotations.media.Content;
//import io.swagger.v3.oas.annotations.media.Schema;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.responses.ApiResponses;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpSession;
//import javax.validation.Valid;
//
//@Tag(name = "Auth Controller", description = "회원가입 및 로그인 기능 구현한 Auth Controller")
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/auth")
//public class AuthController {
//
//    private final AuthService authService;
//
//    @PostMapping("/signup")
//    public ResponseEntity<UserRes> signup(@RequestBody @Valid UserRequestDto userRequestDto) {  // 바뀐 부분
//        try {
//            UserRes response = authService.join(userRequestDto);  // 바뀐 부분
//            return ResponseEntity.ok(response);
//        } catch (IllegalArgumentException illegalArgumentException) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(new BaseErrorResponse(HttpStatus.BAD_REQUEST.value(), illegalArgumentException.getMessage()));
//        }
//    }
//
//    @PostMapping("/email-duplication")
//    public ResponseEntity<?> duplicateEmail(@RequestBody @Valid UserSignUpEmailDuplicationRequestDto userSignUpEmailDuplicationRequestDto) throws Exception {  // 바뀐 부분
//        return authService.validateDuplicateMember(userSignUpEmailDuplicationRequestDto);  // 바뀐 부분
//    }
//
//    @PostMapping("/email-verification")
//    public ResponseEntity<?> verificationEmail(@RequestBody @Valid UserVerificationEmailRequestDto userVerificationEmailRequestDto) {  // 바뀐 부분
//        return authService.verificationEmail(userVerificationEmailRequestDto.getAuthentication());  // 바뀐 부분
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody @Valid UserLoginRequestDto userLoginRequestDto) {  // 바뀐 부분
//        try {
//            ResponseEntity<?> response = authService.login(userLoginRequestDto.getEmail(), userLoginRequestDto.getPassword());  // 바뀐 부분
//            return response;
//        } catch (IllegalArgumentException | AuthenticationException illegalArgumentException) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN)
//                    .body(new BaseErrorResponse(HttpStatus.FORBIDDEN.value(), "잘못된 email 혹은 password 입니다."));
//        }
//    }
//
//    @PatchMapping("/reset-password")
//    public ResponseEntity<?> resetPassword(@RequestBody @Valid UserResetPasswordRequestDto userResetPasswordRequestDto) throws Exception {  // 바뀐 부분
//        return authService.resetPassword(userResetPasswordRequestDto);  // 바뀐 부분
//    }
//}
