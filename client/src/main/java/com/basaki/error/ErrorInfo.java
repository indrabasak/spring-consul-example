package com.basaki.error;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ErrorInfo {

    private String id;

    private int code;

    private String type;

    private String message;
}
