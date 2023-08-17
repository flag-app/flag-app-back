//package com.flag.flag_back.config;
//
//import com.flag.flag_back.Model.User;
//import com.flag.flag_back.service.UserService;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//public class UserDetailService implements UserDetailsService {
//    private UserService userService;
//
//    public UserDetailService(UserService userService) {
//        this.userService = userService;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String insertedUserId) throws UsernameNotFoundException {
//        User findOne = userService.findByName(insertedUserId);
//        //User member = findOne.orElseThrow(() -> new UsernameNotFoundException("없는 회원입니다 ㅠ"));
//
////        return User.builder()
////                .id(findOne.getUserId())
////                .name(findOne.getName())
////                .email(findOne.getEmail())
////                .password(findOne.getPassword())
////                .profile(findOne.getProfile())
////                .build();
//        return (UserDetails)findOne;
//    }
//}