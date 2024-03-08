package com.cencoe.cencoe.controller;

import com.cencoe.cencoe.models.entity.User;
import com.cencoe.cencoe.models.entity.UserLoginRequest;
import com.cencoe.cencoe.service.IUserService;
import com.cencoe.cencoe.util.MensajeResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/cencoe")
public class AuthController {
    private final IUserService userService;

    public AuthController(IUserService userService) {
        this.userService = userService;
    }

//    @PostMapping("/login")
//    public MensajeResponse login(@RequestBody UserLoginRequest loginRequest){
//        String numDoc = loginRequest.getNumDoc();
//        String password = loginRequest.getPassword();
//
//        MensajeResponse mensajeResponse = userService.authenticate();
//        return mensajeResponse;
//    }

}
