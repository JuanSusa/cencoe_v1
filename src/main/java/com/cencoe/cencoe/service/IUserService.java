package com.cencoe.cencoe.service;

import com.cencoe.cencoe.models.entity.User;
import com.cencoe.cencoe.util.MensajeResponse;

public interface IUserService {
    MensajeResponse listUsers();

    MensajeResponse findUser(Long userId);

    MensajeResponse saveUser(User user);

    MensajeResponse updateUser(User userUpdate);

    MensajeResponse deleteUser(Long userId);
}
