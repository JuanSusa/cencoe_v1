package com.cencoe.cencoe.models.entity;

import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRequest {
    private String userNumDoc;
    private String userPassword;
}
