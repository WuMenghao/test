package com.brillilab.test;

import com.brillilab.utils.SecureUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class SecureUtilsTest {

    private final String SALT="0123456789ABCDEF";
    private final String CONTEXT="DES一共就有4个参数参与运作：明文、密文、密钥、向量。为了初学者容易理解，可以把4个参数的关系写成：密文=明文+密钥+向量；明文=密文-密钥-向量。为什么要向量这个参数呢？因为如果有一篇文章，有几个词重复，那么这个词加上密钥形成的密文，仍然会重复，这给破解者有机可乘，破解者可以根据重复的内容，猜出是什么词，然而一旦猜对这个词，那么，他就能算出密钥，整篇文章就被破解了！加上向量这个参数以后，每块文字段都会依次加上一段值，这样，即使相同的文字，加密出来的密文，也是不一样的，算法的安全性大大提高！";

    @Test
    public void MD5EncodeTest() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String s = SecureUtils.MD5Encrypt("Hello world");
        byte[] bytes = SecureUtils.decodeHex(s);
        String s1 = SecureUtils.encodeHex(bytes);
        System.out.println(s);
        System.out.println(s1);
    }

    @Test
    public void MD5WithSaltTest() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String s = SecureUtils.MD5WithSaltEncrypt("Hello world", SALT);
        System.out.println(s);
    }

    @Test
    public void Base64EncodeTest() throws UnsupportedEncodingException {
        String s = SecureUtils.base64Encode("Hello world");
        System.out.println(s);
    }

    @Test
    public void Base64Decode() throws IOException {
        String s = SecureUtils.base64Encode("Hello world");
        String s1 = SecureUtils.base64Decode(s);
        System.out.println(s1);
    }

    @Test
    public void DEStest() throws Exception {
        String context = CONTEXT;
        //生成密钥
        String keyDES = SecureUtils.genKeyDES();
        //加密
        String enc = SecureUtils.encryptDES(context, keyDES);
        //解密
        String dec = SecureUtils.decryptDES(enc,keyDES);

        System.out.println(keyDES);
        System.out.println(enc);
        System.out.println(dec);
    }

    @Test
    public void AEStest() throws Exception{
        String context=CONTEXT;
        //生成密钥
        String keyAES = SecureUtils.genKeyAES();
        //加密
        String enc = SecureUtils.encryptAES(context, keyAES);
        //解密
        String dec = SecureUtils.decryptAES(enc, keyAES);

        System.out.println(keyAES);
        System.out.println(enc);
        System.out.println(dec);
    }

    @Test
    public void RSAtest() throws Exception {
        String context= SecureUtils.genKeyDES();
        //生成密钥
        SecureUtils.Base64KeyPair base64KeyPair = SecureUtils.genBase64RSAKeyPair();
        //加密
        String enc = SecureUtils.RSAPublicEncrypt(context, base64KeyPair.getBase64PublicKey());
        //解密
        String dec = SecureUtils.RSAPrivateDecrypt(enc, base64KeyPair.getBase64PrivateKey());

        System.out.println("context: \n"+context);
        System.out.println("\n publicKey: \n"+base64KeyPair.getBase64PublicKey());
        System.out.println("\n privateKey: \n"+base64KeyPair.getBase64PrivateKey());
        System.out.println("\n enc: \n"+enc);
        System.out.println("\n dec: \n"+dec);
    }

    @Test
    public void digestTest() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name","wmh");
        map.put("age",13);
        map.put("adress","sdadsassf");

        HashMap<String, Object> map2 = new HashMap<>();
        map.put("name","wmh");
        map.put("age",14);
        map.put("adress","sdadsassf");

        //生成摘要
        String digest = SecureUtils.getDigest(map, SALT);
        //验证摘要
        boolean result1 = SecureUtils.digestValidate(map, digest, SALT);
        boolean result2 = SecureUtils.digestValidate(map2, digest, SALT);

        System.out.println(result1);
        System.out.println(result2);
    }
}
