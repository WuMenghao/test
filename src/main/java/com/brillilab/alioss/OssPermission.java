package com.brillilab.alioss;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class OssPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    private String accessid;

    private String policy;

    private String signature;

    private String dir;

    private String host;

    private Date expire;

    private AliSts aliSts;

    private String endpoint;

    private String region;

    private String callback;

    private String callbackVar;
}
