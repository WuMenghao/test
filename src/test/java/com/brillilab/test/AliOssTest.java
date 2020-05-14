package com.brillilab.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.Callback;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.brillilab.alioss.OssCallbackParams;
import com.brillilab.alioss.OssPermission;
import com.brillilab.alioss.ResponseVo;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class AliOssTest {

    @Test
    public void signatureCallback() throws URISyntaxException, IOException {

        CloseableHttpClient client = HttpClientBuilder.create().build();
        //param
        URI uri =
                new URIBuilder("http://47.110.33.183:8081/api/fileManage/uploadParam")
                        .setParameter("attachType", "800")
                        .build();

        //head
        HttpGet httpGet = new HttpGet(uri);
        httpGet.setHeader("token","PC_fc9627f68ed24e7296a5d3d371d0c03c");

        CloseableHttpResponse response = client.execute(httpGet);
        String rs = EntityUtils.toString(response.getEntity());
        ResponseVo result = JSONObject.parseObject(rs, ResponseVo.class);
        OssCallbackParams ossPermission = ((JSONObject) result.getData()).toJavaObject(OssCallbackParams.class);

        String endpoint = ossPermission.getEndpoint();
        String accessKeyId = ossPermission.getAliSts().getAccessKeyId();
        String accessKeySecret = ossPermission.getAliSts().getAccessKeySecret();
        String securityToken = ossPermission.getAliSts().getSecurityToken();
        String bucketName = ossPermission.getBucketName();
        String objectName = ossPermission.getBasePath() + "test20200407.txt";
        String callbackUrl = ossPermission.getCallbackUrl();
        String callbackBody = ossPermission.getCallbackBody();
        String callbackVar = ossPermission.getCallbackVar();


        //上传回调
        OSS ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret, securityToken);

        String content = "Hello OSS";
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName,new ByteArrayInputStream(content.getBytes()));

        Callback callback = new Callback();
        callback.setCallbackUrl(callbackUrl);
        callback.setCallbackBody(callbackBody);
        callback.setCalbackBodyType(Callback.CalbackBodyType.JSON);
        List<String> vars = JSONObject.parseArray(callbackVar, String.class);
        vars.forEach(var -> {
            if ("x:filename".equals(var)){
                callback.addCallbackVar(var,"test20200407.txt");
            }
        });
        putObjectRequest.setCallback(callback);

        PutObjectResult putObjectResult = ossClient.putObject(putObjectRequest);

        // 读取上传回调返回的消息内容。
        byte[] buffer = new byte[1024];
        putObjectResult.getResponse().getContent().read(buffer);
        // 数据读取完成后，获取的流必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
        putObjectResult.getResponse().getContent().close();
        System.out.println(new String(buffer,"utf8"));

        // 关闭OSSClient。
        ossClient.shutdown();

    }
}
