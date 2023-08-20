package com.flag.flag_back.service;

import com.flag.flag_back.Model.User;
import com.flag.flag_back.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email);

        if (!user.getEmail().equals(email)) {
            throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
        }

        return user;
    }
}
