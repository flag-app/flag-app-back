package com.flag.flag_back.service;

import com.flag.flag_back.Dto.UserDto;
import com.flag.flag_back.Dto.UserInfo;
import com.flag.flag_back.Model.User;
import com.flag.flag_back.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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
        System.out.print("userDto.toEntity().getEmail())= " + userDto.toEntity().getEmail());
        System.out.print(".isEmpty()= " + userDto.toEntity().getEmail().isEmpty());


        return !userRepository.findUserEntityByEmail(userDto.toEntity().getEmail()).isEmpty();
    }

    @Transactional
    public boolean isExistName(UserDto userDto) {
        return !userRepository.findUserEntityByName(userDto.toEntity().getName()).isEmpty();
    }

    @Transactional
    public User login(UserDto userDto) { //DB에 검색하여 해당하는 회원정보가 있는지 조회
        System.out.println("email - " + userDto.getEmail() + ", pw - "+ userDto.getPassword());
        //validateLogin(userDto);
        return userRepository.findUserEntityByEmailAndPassword(userDto.toEntity().getEmail(), userDto.toEntity().getPassword());
    }

    @Transactional//
    public List<User> findListByName(String name) {
        System.out.println("id 2: " + name);
        return userRepository.findUserEntityByName(name);
    }

    @Transactional
    public Long join(User user) {

        //validateDuplicateMember(user); //중복 회원 검증
        validateEmail(user);
        userRepository.save(user);
        return user.getId();
    }

    private void validateDuplicateMember(User user) {
        List<User> findMembers = userRepository.findUserEntityByName(user.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    private void validateEmail(User user) {
        List<User> findMembers = userRepository.findUserEntityByEmail(user.getEmail());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }
    }

    private void validateLogin(UserDto user) {
        User user1 = userRepository.findUserEntityByEmailAndPassword(user.getEmail(), user.getPassword());
        if (user1 == null) {
            throw new IllegalStateException("잘못된 ID나 email");
        }
        /*System.out.print("isExistEmail(user) " + isExistEmail(user));

        if(!isExistEmail(user)) {
            throw new IllegalStateException("잘못된 ID나 email");
        }*/
    }

    public UserInfo findById(Long id) {
        System.out.println("id 2: " + id);
        return userRepository.findUserById(id);
    }
}
