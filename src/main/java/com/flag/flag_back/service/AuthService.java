//ackage com.flag.flag_back.service;
//
//import com.flag.flag_back.Dto.UserDto;
//import com.flag.flag_back.Dto.UserRes;
//import org.springframework.http.ResponseEntity;
//
//public interface AuthService {
//    ResponseEntity<UserRes> join(UserDto userDto);
//
//    ResponseEntity<?> validateDuplicateMember(UserSignUpEmailDuplicationRequestDto userSignUpEmailDuplicationRequestDto) throws Exception;  // 바뀐 부분
//
//    ResponseEntity<?> verificationEmail(String inputCode);
//
//    ResponseEntity<?> login(String email, String password);  /
//
//    ResponseEntity<?> resetPassword(UserResetPasswordRequestDto userResetPasswordRequestDto) throws Exception;  // 바뀐 부분
//}