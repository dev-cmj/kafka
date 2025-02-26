package com.example.producer.user;

import com.example.common.entity.UserMessage;
import com.example.common.entity.user.InvalidUserException;
import com.example.common.entity.user.User;
import com.example.common.entity.user.UserRepository;
import com.example.common.vo.LoginRequest;
import com.example.producer.message.MessageProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final MessageProducer messageProducer;

    public void processUser(UserMessage userMessage) {
        // 사용자 데이터 가공
        UserMessage processedUser = processUserMessage(userMessage);

        // 가공된 데이터를 메시지로 전송
        messageProducer.sendMessage(processedUser, false);
    }

    private UserMessage processUserMessage(UserMessage user) {
        // 데이터 가공 로직
        user.setEmail(user.getEmail().toLowerCase());
        user.setUsername(user.getUsername().trim());
        user.setPhone(user.getPhone().replaceAll("[^0-9]", ""));
        user.setUserId(user.getUserId().toUpperCase());
        // ... 기타 가공 로직
        return user;
    }
}
