package com.example.jiraproject.notification.resource;

import com.example.jiraproject.common.dto.ResponseDto;
import com.example.jiraproject.common.util.MessageUtil;
import com.example.jiraproject.common.util.ResponseUtil;
import com.example.jiraproject.common.validation.annotation.UUIDConstraint;
import com.example.jiraproject.notification.model.Notification;
import com.example.jiraproject.notification.service.NotificationService;
import com.example.jiraproject.role.util.RoleUtil;
import com.example.jiraproject.security.aop.Authorized;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.UUID;

@RestController
@RequestMapping("/v1/notifications")
@RequiredArgsConstructor
@Validated
@SecurityRequirement(name = "bearerAuth")
public class NotificationRestResource {
    private final NotificationService service;
    private final MessageSource messageSource;

    @Authorized(roles = {RoleUtil.MANAGER, RoleUtil.LEADER, RoleUtil.EMPLOYEE})
    @GetMapping("/with-info/sent/by-receiver/{receiverId}")
    public ResponseEntity<ResponseDto> findAllSentByReceiverId(@PathVariable("receiverId") @UUIDConstraint String receiverId) {
        return ResponseUtil.get(service.findAllWithReceiverAndStatus(UUID.fromString(receiverId), Notification.Status.SENT), HttpStatus.OK);
    }

    @Authorized(roles = {RoleUtil.MANAGER, RoleUtil.LEADER, RoleUtil.EMPLOYEE})
    @GetMapping("/with-info/read/by-receiver/{receiverId}")
    public ResponseEntity<ResponseDto> findAllReadByReceiver(@PathVariable("receiverId") @UUIDConstraint String receiverId) {
        return ResponseUtil.get(service.findAllWithReceiverAndStatus(UUID.fromString(receiverId), Notification.Status.READ), HttpStatus.OK);
    }

    @GetMapping(value = "/subscribe/{token}", consumes = MediaType.ALL_VALUE)
    public SseEmitter subscribe(@PathVariable("token") String token) {
        return service.subscribe(token);
    }

    @Authorized(roles = {RoleUtil.MANAGER, RoleUtil.LEADER, RoleUtil.EMPLOYEE})
    @PostMapping("/read-all/by-receiver/{receiverId}")
    public ResponseEntity<ResponseDto> readAllByReceiver(@PathVariable("receiverId") @UUIDConstraint String receiverId) {
        service.readAllByReceiver(UUID.fromString(receiverId));
        return ResponseUtil.get(MessageUtil.getMessage(messageSource, "notification.read"), HttpStatus.OK);
    }

    @Authorized(roles = {RoleUtil.MANAGER, RoleUtil.LEADER, RoleUtil.EMPLOYEE})
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteById(@PathVariable("id") @UUIDConstraint String id) {
        service.deleteById(UUID.fromString(id));
        return ResponseUtil.get(MessageUtil.getMessage(messageSource, "notification.deleted"), HttpStatus.OK);
    }
}
