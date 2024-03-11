package com.cencoe.cencoe.service;

import com.cencoe.cencoe.models.entity.UserLoginRequest;
import com.cencoe.cencoe.util.MensajeResponse;

public interface IAuthService {
    MensajeResponse login(UserLoginRequest loginRequest);
}
