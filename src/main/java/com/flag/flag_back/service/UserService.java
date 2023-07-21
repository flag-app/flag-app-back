package com.flag.flag_back.service;

import com.flag.flag_back.Dto.UserDto;
import com.flag.flag_back.Dto.UserInfo;
import com.flag.flag_back.Model.User;
import com.flag.flag_back.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@AllArgsConstructor
@Service
public class UserService {
    private UserRepository userRepository;

    @Transactional
    public Long saveUser(UserDto userDto) {
        return userRepository.save(userDto.toEntity()).getId();
    }

    @Transactional
    public boolean isExistEmail(UserDto userDto) {
        return !userRepository.findUserEntityByEmail(userDto.toEntity().getEmail()).isEmpty();
    }

    @Transactional
    public boolean isExistName(UserDto userDto) {
        return !userRepository.findUserEntityByName(userDto.toEntity().getName()).isEmpty();
    }

    @Transactional
    public User login(UserDto userDto) { //B에 검색하여 해당하는 회원정보가 있는지 조회
        return userRepository.findUserEntityByEmailAndPassword(userDto.toEntity().getEmail(), userDto.toEntity().getPassword());
    }

    @Transactional
    public Long join(User user) {

        validateDuplicateMember(user); //중복 회원 검증
        userRepository.save(user);
        return user.getId();
    }

    private void validateDuplicateMember(User user) {
        List<User> findMembers = userRepository.findUserEntityByName(user.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public UserInfo findById(Long id) {
        System.out.println("id 2: " + id);
        return userRepository.findUserById(id);
    }
}
