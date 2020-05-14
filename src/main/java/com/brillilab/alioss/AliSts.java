package com.brillilab.alioss;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AliSts implements Serializable {

    private static final long serialVersionUID = 1L;

    private String accessKeyId;

    private String accessKeySecret;

    private String securityToken;

    private Date expireTime;
}
