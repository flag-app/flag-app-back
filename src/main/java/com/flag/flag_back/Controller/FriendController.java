
package com.flag.flag_back.Controller;

import com.flag.flag_back.Dto.ResponseDto;
import com.flag.flag_back.Dto.UserRes;
import com.flag.flag_back.Dto.UserResponse;
import com.flag.flag_back.Model.Friend;
import com.flag.flag_back.Model.User;
import com.flag.flag_back.Repository.UserRepository;
import com.flag.flag_back.config.BaseResponse;
import com.flag.flag_back.config.BaseResponseStatus;
import com.flag.flag_back.jwt.JwtTokenProvider;
import com.flag.flag_back.service.FriendService;
import com.flag.flag_back.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.flag.flag_back.config.BaseResponseStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("friends")
public class FriendController {

    private final FriendService friendService;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @PostMapping("/List") //닉네임으로 리스트 조회
    @Operation(summary = "닉네임 검색", description = "닉네임으로 유저 검색")
    public BaseResponse<String> getUsersList(@RequestHeader(value = "Authorization", required = false) String token, @RequestBody @Valid String name) {
        try {
            String email = jwtTokenProvider.getUserPk(token);
            User user = userRepository.findUserByEmail(email);
            boolean exist = checkUser(token, name);
            UserResponse users = userService.findListByName(name);
            users.setExistFriend(exist);

            String valueOfuser = String.valueOf(users);
            return new BaseResponse<>(valueOfuser);

            //return users;
        } catch (Exception e) {
            return new BaseResponse<>(NICKNAME_SEARCH_ERROR);
        }
    }
    //예외처리했는데 반환값 걸리는 부분 주석
//    @PostMapping("/List") //닉네임으로 리스트 조회
//    @Operation(summary = "닉네임 검색", description = "닉네임으로 유저 검색")
//    public UserResponse getUsersList(@RequestHeader(value = "Authorization", required = false) String token,  @RequestBody @Valid String name) {
//        try {
//            String email = jwtTokenProvider.getUserPk(token);
//            User user = userRepository.findUserByEmail(email);
//            boolean exist = checkUser(token, name);
//            UserResponse users = userService.findListByName(name);
//            users.setExistFriend(exist);
//            return users;
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

    @PostMapping("/add")
    @Operation(summary = "친구 추가", description = "닉네임으로 친구 추가")
    public BaseResponse<String> addFriend(@RequestHeader(value = "Authorization", required = false) String token, @RequestBody @Valid String friendName) {
        try {
            String email = jwtTokenProvider.getUserPk(token);
            User user = userRepository.findUserByEmail(email);
            User friend = userRepository.findUserByName(friendName);

            if(checkUser(token, friend.getName()) == true){
                return new BaseResponse<>(ALREADY_FRIEND);
            }
            Friend friendInfo = new Friend();
            friendInfo.setUserId(user.getUserId());
            friendInfo.setUserId2(friend.getUserId());

            Long id = friendService.add(friendInfo);
            System.out.println("친구 추가 완료 - " + id);

            return new BaseResponse<>(ADD_FRIEND);
        } catch (Exception e) {
            return new BaseResponse<>(ADD_FRIEND_ERROR);
        }
    }

    //친구인지 아닌지 검사
    @PostMapping("/checkFriendId") //닉네임으로 리스트 조회
    @Operation(summary = "친구 중복 검사", description = "친구인지 아닌지 검사")
    public boolean checkUser(@RequestHeader(value = "Authorization", required = false) String token, @RequestBody @Valid String friendName) {
        try {
            if (token == null || !jwtTokenProvider.validateToken(token)) {// 토큰이 없거나 유효하지 않은 경우 처리
                return false;
            }

            String email = jwtTokenProvider.getUserPk(token);
            User user = userRepository.findUserByEmail(email);
            User friend = userRepository.findUserByName(friendName);

            return friendService.checkFriendById2(user.getUserId(), friend.getUserId());
        } catch (Exception e) {
            e.printStackTrace(); // 예외 정보를 로그에 출력
            return false;
        }
    }

    //친구 리스트 보여줌.
    @GetMapping("/friendList")
    @Operation(summary = "친구 list 조회", description = "내 친구 목록 조회.")
    public List<UserResponse> getFriendsList(@RequestHeader(value = "Authorization", required = false) String token) {
        try {
            if (token == null || !jwtTokenProvider.validateToken(token)) {
                // 토큰이 없거나 유효하지 않은 경우 처리
                return Collections.emptyList();
            }

            String email = jwtTokenProvider.getUserPk(token);
            User user = userRepository.findUserByEmail(email);

            if (user != null) {
                return friendService.friendsListById(user.getUserId());
            } else {
                return Collections.emptyList(); // 사용자 정보를 찾을 수 없는 경우 처리
            }
        } catch (Exception e) {
            e.printStackTrace(); // 예외 정보를 로그에 출력

            return Collections.emptyList();// 예외에 따라 적절한 동작을 수행
        }
    }

    //내 친구 내에서 검색 - select문을 - where userid = user2Id where userid1 =  myId
    @PostMapping("/friendList/name") //닉네임으로 리스트 조회
    @Operation(summary = "친구 내에서 닉네임 검색", description = "내 친구 리스트에서 닉네임으로 친구 검색")
    public UserResponse searchFriendsList(@RequestHeader(value = "Authorization", required = false) String token, @RequestBody @Valid String name) {
        try {
            if (token == null || !jwtTokenProvider.validateToken(token)) { // 토큰이 없거나 유효하지 않은 경우 처리
                return UserResponse.builder()
                        .existFriend(false)
                        .name("토큰 오류")
                        .email("유효하지 않은 토큰이거나 토큰이 없습니다.")
                        .build();
            }

            String email = jwtTokenProvider.getUserPk(token);
            User user = userRepository.findUserByEmail(email);

            return friendService.friendsListByNickName(user.getUserId(), name);
        }catch (NullPointerException e) {
            // 친구 검색 중에 NullPointerException이 발생한 경우 처리
            e.printStackTrace();
            return UserResponse.builder()
                    .existFriend(false)
                    .name("친구 없음")
                    .email("해당 닉네임의 친구를 찾을 수 없습니다.")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return UserResponse.builder()
                    .existFriend(false)
                    .name("오류")
                    .email("친구 리스트에서 닉네임으로 친구 검색 중 오류가 발생했습니다.")
                    .build();
        }
    }

    //친구 삭제
    @DeleteMapping("/delete")
    @Operation(summary = "친구 삭제", description = "친구 삭제 API")
    public Map<String, Object> delete(@RequestHeader(value = "Authorization", required = false) String token,  @RequestBody @Valid Long fid) {

        if (token == null || !jwtTokenProvider.validateToken(token)) {
            Map<String, Object> invalidTokenResponse = new HashMap<>();
            invalidTokenResponse.put("result", "FAIL");
            invalidTokenResponse.put("reason", "유효하지 않은 토큰이거나 토큰이 없습니다.");
            return invalidTokenResponse;
        }

        String email = jwtTokenProvider.getUserPk(token);
        User user = userRepository.findUserByEmail(email);
        Map<String, Object> response = new HashMap<>();

        if(friendService.delete(user.getUserId(),fid) > 0) {
            response.put("result", "SUCCESS");
        } else {
            response.put("result", "FAIL");
            response.put("reason", "일치하는 정보가 없습니다. id를 확인해주세요.");
        }

        return response;
    }
}
