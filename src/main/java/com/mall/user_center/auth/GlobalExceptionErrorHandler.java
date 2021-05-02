package com.mall.user_center.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionErrorHandler {
    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<ErrorBody> error(SecurityException e){
        log.warn("用户未登录或者或者权限不够");
        return new ResponseEntity<ErrorBody>(
                ErrorBody.builder()
                        .message(e.getMessage())
                        .status(HttpStatus.UNAUTHORIZED.value())
                .build(),
                HttpStatus.UNAUTHORIZED

        );
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class ErrorBody {
    private String message;
    private int status;
}


