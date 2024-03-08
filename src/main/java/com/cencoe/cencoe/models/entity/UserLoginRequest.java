package com.cencoe.cencoe.models.entity;

import lombok.*;


@Getter
@Setter
public class UserLoginRequest {
    private String numDoc;
    private String password;
}
