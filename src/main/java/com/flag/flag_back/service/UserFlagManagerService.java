package com.flag.flag_back.service;

import com.flag.flag_back.Repository.FlagRepository;
import com.flag.flag_back.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserFlagManagerService {

    private final UserRepository userRepository;
    private final FlagRepository flagRepository;
}
