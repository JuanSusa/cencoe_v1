package com.cencoe.cencoe.controller;

import com.cencoe.cencoe.models.entity.User;
import com.cencoe.cencoe.models.entity.UserLoginRequest;
import com.cencoe.cencoe.service.IAuthService;
import com.cencoe.cencoe.service.IUserService;
import com.cencoe.cencoe.util.MensajeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v2/cencoe")

public class AuthController {
    private final IAuthService authService;
    @Autowired
    public AuthController(IAuthService authService) {
        this.authService = authService;

    }

    @PostMapping("/login")
    public ResponseEntity<MensajeResponse> login(@RequestBody UserLoginRequest loginRequest) {
        MensajeResponse mensajeResponse = authService.login(loginRequest);
        return new ResponseEntity<>(mensajeResponse, HttpStatusCode.valueOf(mensajeResponse.getCode()));
    }
    }


