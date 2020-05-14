package com.brillilab.alioss;

import lombok.Data;

import java.io.Serializable;

@Data
public class OssCallbackParams implements Serializable {

    private static final long serialVersionUID = 1L;

    private String bucketName;

    private String basePath;

    private String host;

    private AliSts aliSts;

    private String callbackUrl;

    private String callbackBodyType;

    private String callbackVar;

    private String callbackBody;

    private String endpoint;
}
