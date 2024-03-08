package com.cencoe.cencoe.util;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
@Builder
public class MensajeResponse implements Serializable {

    private int code;
    private String message;
    private Object data;
    private Boolean success;

    public static MensajeResponse buildMensajeGeneral(HttpStatus httpStatus, String message, Boolean success, Object data) {
        return MensajeResponse.builder()
                .code(httpStatus.value())
                .message(message)
                .data(data)
                .success(success).build();
    }


}
