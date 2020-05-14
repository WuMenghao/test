package com.brillilab.alioss;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer code;

    private String message;

    private Object data;
}
