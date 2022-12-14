package com.example.riraproject.file.resource;

import com.example.riraproject.common.dto.ResponseDto;
import com.example.riraproject.common.util.MessageUtil;
import com.example.riraproject.common.util.ResponseUtil;
import com.example.riraproject.file.dto.FileInfoDto;
import com.example.riraproject.file.service.FileService;
import com.example.riraproject.file.util.FileUtil;
import com.example.riraproject.role.util.RoleUtil;
import com.example.riraproject.security.aop.Authorized;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.MessageSource;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/v1/files")
@RequiredArgsConstructor
public class FileRestResource {
    private final FileService service;
    private final MessageSource messageSource;

    @EventListener(ApplicationReadyEvent.class) //call this method after the application is ready
    public void initService() {
        service.init();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> findById(@PathVariable("id") String id) {
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(service.load(id));
    }

    @Authorized(roles = {RoleUtil.MANAGER, RoleUtil.LEADER, RoleUtil.EMPLOYEE})
    @PostMapping
    public ResponseEntity<ResponseDto> uploadFile(@RequestParam("file") MultipartFile file) {
        return ResponseUtil.get(service.save(file), HttpStatus.OK);
    }

    @Authorized(roles = {RoleUtil.ADMIN})
    @DeleteMapping
    public ResponseEntity<ResponseDto> deleteAllFiles() {
        service.deleteAll();
        return ResponseUtil.get(MessageUtil.getMessage(messageSource, "file.deleted"), HttpStatus.OK);
    }
}
