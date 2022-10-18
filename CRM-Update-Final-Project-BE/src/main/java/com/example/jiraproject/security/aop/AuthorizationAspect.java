package com.example.jiraproject.security.aop;

import com.example.jiraproject.common.exception.JiraAuthorizationException;
import com.example.jiraproject.operation.model.Operation;
import com.example.jiraproject.operation.repository.OperationRepository;
import com.example.jiraproject.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AuthorizationAspect {
    private final OperationRepository operationRepository;
    private final JwtUtil jwtUtil;

    @Before("@annotation(authorized)")
    public void authorization(Authorized authorized) {
        String operationName = authorized.operation();
        Operation.Type type = authorized.type();
        String username = jwtUtil.getAuthenticatedUsername(); // get username from SecurityContextHolder
        log.info("AUTHORIZATION.....");
        log.info("Username : {}", username);
        log.info("OperationName : {}", operationName);
        log.info("Type: {}", type);
        Optional<Operation> operation = operationRepository
                .findByNameAndTypeAndUsername(operationName, type, username);
        if (operation.isEmpty()) {
            throw new JiraAuthorizationException("Bạn không có quyền truy cập API này");
        }
    }
}
