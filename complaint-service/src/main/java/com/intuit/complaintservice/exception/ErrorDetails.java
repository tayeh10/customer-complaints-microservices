package com.intuit.complaintservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails {
    private LocalDateTime timestamp;
    private String message;
    private String path;
    private String errorCode;

    public String toString()
    {
        return "\ntimestamp=" + timestamp.toString() + "\nmessage=" + message + "\npath=" + path + "\nerrorCode=" + errorCode;
    }
}
