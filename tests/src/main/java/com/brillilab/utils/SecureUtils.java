package com.brillilab.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

/**
 *  信息加解密工具类
 *
 * @author wmh
 */
public class SecureUtils {
    private static final Integer SALT_LENGTH=12;
    private static final String UTF8="utf-8";
    private static final String MD5="MD5";
    private static final String DES="DES";
    private static final String AES="AES";
    private static final String RSA="RSA";
    private static final char[] HEX_CHARS =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private SecureUtils() {
    }
/**
 * ######################################################################
 * MD5 摘要认证 签名认证
 */
    /**
     * MD5加密
     * @param context
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String MD5Encrypt(String context) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md5 = MessageDigest.getInstance(MD5);
        byte[] digest = md5.digest(context.getBytes(UTF8));
        return encodeHex(digest);
    }

    private static byte[] getMD5(String context) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md5 = MessageDigest.getInstance(MD5);
        return md5.digest(context.getBytes(UTF8));
    }

    /**
     * MD5+salt加密
     *      用于实现摘要认证
     * @param cotext
     * @param salt
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String MD5WithSaltEncrypt(String cotext,String salt) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        //salt添加到首步进行MD5加密
        MessageDigest md5 = MessageDigest.getInstance(MD5);
        byte[] saltBytes = salt.getBytes(UTF8);
        byte[] cotextBytes = cotext.getBytes(UTF8);
        md5.update(saltBytes);
        md5.update(cotextBytes);
        byte[] digest = md5.digest();

        //salt添加到加密完成后的字节数组首部
        byte[] rsb=new byte[SALT_LENGTH+digest.length];
        System.arraycopy(saltBytes,0,rsb,0,SALT_LENGTH);
        System.arraycopy(digest,0,rsb,SALT_LENGTH,digest.length);

        return encodeHex(rsb);
    }

    /**
     * 生成摘要
     * @param params
     * @param salt
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public static String getDigest(Map params,String salt) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Set<Object> keySet = params.keySet();
        //使用TreeSet排序
        TreeSet<Object> sortSet = new TreeSet<>();
        sortSet.addAll(keySet);

        StringBuilder sb=new StringBuilder();
        sortSet.forEach( k -> sb.append(params.get(k)));
        sb.append(salt);

        return byte2Base64(getMD5(sb.toString()));
    }

    /**
     * 摘要认证
     * @param params
     * @param digest
     * @param salt
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public static boolean digestValidate(Map params , String digest ,String salt) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        //获取摘要
        String digestN = getDigest(params, salt);
        //比较摘要
        if (digestN.equals(digest)){
            return true;
        }
        return false;
    }

    /**
     * MD5WithRSA签名
     * @param context
     * @param base64PrivateKey
     * @return
     * @throws Exception
     */
    public static String MD5WithRSASign(String context,String base64PrivateKey) throws Exception {
        Signature signature=Signature.getInstance("MD5withRSA");
        signature.initSign(loadPrivateKey(base64PrivateKey));
        signature.update(context.getBytes(UTF8));
        return byte2Base64(signature.sign());
    }

    /**
     * MD5WithRSA认证
     * @param context
     * @param sign
     * @param base64PublicKey
     * @return
     * @throws Exception
     */
    public static boolean MD5WithRSASignVerify(String context,String sign,String base64PublicKey) throws Exception {
        Signature signature=Signature.getInstance("MD5withRSA");
        signature.initVerify(loadPublicKey(base64PublicKey));
        signature.update(context.getBytes(UTF8));
        return signature.verify(base642Byte(sign));
    }

/**
 * ###########################################################################
 * DES3加密
 */
    /**
     * DES3密钥生成Base64编码的密钥字符串
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String genKeyDES() throws NoSuchAlgorithmException {
        KeyGenerator keyGen=KeyGenerator.getInstance(DES);
        keyGen.init(56);
        SecretKey key = keyGen.generateKey();
        return byte2Base64(key.getEncoded());
    }

    /**
     * DES3密钥由Base64编码的密钥字符串载入密钥
     * @param base64Key
     * @return
     * @throws IOException
     */
    private static SecretKey loadKeyDES(String base64Key) throws IOException {
        byte[] bytes = base642Byte(base64Key);
        SecretKey key = new SecretKeySpec(bytes, DES);
        return key;
    }

    /**
     * DES3加密
     * @param context
     * @param base64SecretKey
     * @return
     * @throws Exception
     */
    public static String encryptDES(String context , String base64SecretKey) throws Exception {
        SecretKey key = loadKeyDES(base64SecretKey);
        Cipher cipher = Cipher.getInstance(DES);
        cipher.init(Cipher.ENCRYPT_MODE,key);
        byte[] result = cipher.doFinal(context.getBytes(UTF8));
        return byte2Base64(result);
    }

    /**
     * DES3解密
     * @param context
     * @param base64SecretKey
     * @return
     * @throws Exception
     */
    public static String decryptDES(String context , String base64SecretKey) throws Exception {
        SecretKey key = loadKeyDES(base64SecretKey);
        Cipher cipher = Cipher.getInstance(DES);
        cipher.init(Cipher.DECRYPT_MODE,key);
        byte[] bytes = cipher.doFinal(base642Byte(context));
        return new String(bytes,UTF8);
    }


/**
 * ######################################################################
 * AES加密
  */
    /**
     * 生成AES密钥
     * @return
     * @throws Exception
     */
    public static String genKeyAES() throws Exception{
        KeyGenerator keyGen = KeyGenerator.getInstance(AES);
        keyGen.init(128);
        SecretKey key = keyGen.generateKey();
        return byte2Base64(key.getEncoded());
    }

    /**
     * AES base64密钥转SecretKey
     * @param base64Key
     * @return
     * @throws Exception
     */
    private static SecretKey loadKeyAES(String base64Key) throws Exception{
        byte[] bytes = base642Byte(base64Key);
        SecretKey key = new SecretKeySpec(bytes, AES);
        return key;
    }

    /**
     * AES加密
     * @param context
     * @param base64Key
     * @return
     * @throws Exception
     */
    public static String encryptAES(String context , String base64Key) throws Exception{
        SecretKey key = loadKeyAES(base64Key);
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.ENCRYPT_MODE,key);
        byte[] bytes = cipher.doFinal(context.getBytes(UTF8));
        return byte2Base64(bytes);
    }

    /**
     * AES解密
     * @param context
     * @param base64Key
     * @return
     * @throws Exception
     */
    public static String decryptAES(String context , String base64Key) throws Exception{
        SecretKey key = loadKeyAES(base64Key);
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.DECRYPT_MODE,key);
        byte[] bytes = cipher.doFinal(base642Byte(context));
        return new String(bytes,UTF8);
    }


/**
 * ######################################################################
 * RSA加密
 */
    private static KeyPair getKeyPair() throws Exception{
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA);
        keyPairGenerator.initialize(512);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return keyPair;
    }

    private static String getPublicKey(KeyPair keyPair){
        PublicKey publicKey = keyPair.getPublic();
        return byte2Base64(publicKey.getEncoded());
    }

    private static String getPrivateKey(KeyPair keyPair){
        PrivateKey privateKey = keyPair.getPrivate();
        return byte2Base64(privateKey.getEncoded());
    }

    /**
     * RSA生成Base64编码的公私钥
     * @return
     * @throws Exception
     */
    public static Base64KeyPair genBase64RSAKeyPair() throws Exception {
        KeyPair keyPair = getKeyPair();
        String publicKey = getPublicKey(keyPair);
        String privateKey = getPrivateKey(keyPair);
        return new Base64KeyPair(publicKey,privateKey);
    }

    private static PublicKey loadPublicKey(String base64PublicKey) throws Exception{
        byte[] bytes = base642Byte(base64PublicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    private static PrivateKey loadPrivateKey(String base64PrivateKey) throws Exception{
        byte[] bytes = base642Byte(base64PrivateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    /**
     * 使用RSA公钥编码
     * @param context
     * @param base64PublicKey
     * @return
     * @throws Exception
     */
    public static String RSAPublicEncrypt(String context,String base64PublicKey)throws Exception{
        PublicKey publicKey = loadPublicKey(base64PublicKey);
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.ENCRYPT_MODE,publicKey);
        byte[] bytes = cipher.doFinal(context.getBytes(UTF8));
        return byte2Base64(bytes);
    }

    /**
     * 使用RSA公钥解码
     * @param context
     * @param base64PublicKey
     * @return
     * @throws Exception
     */
    public static String RSAPublicDecrypt(String context,String base64PublicKey) throws Exception {
        PublicKey publicKey=loadPublicKey(base64PublicKey);
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.DECRYPT_MODE,publicKey);
        byte[] bytes = cipher.doFinal(base642Byte(context));
        return new String(bytes,UTF8);
    }

    /**
     * 使用RSA私钥加密
     * @param context
     * @param base64PrivateKey
     * @return
     * @throws Exception
     */
    public static String RSAPrivateEncrypt(String context,String base64PrivateKey) throws Exception {
        PrivateKey privateKey = loadPrivateKey(base64PrivateKey);
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.ENCRYPT_MODE,privateKey);
        byte[] bytes=cipher.doFinal(context.getBytes(UTF8));
        return byte2Base64(bytes);
    }


    /**
     * 使用RSA私钥解码
     * @param context
     * @param base64PrivateKey
     * @return
     * @throws Exception
     */
    public static String RSAPrivateDecrypt(String context,String base64PrivateKey) throws Exception{
        PrivateKey privateKey = loadPrivateKey(base64PrivateKey);
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.DECRYPT_MODE,privateKey);
        byte[] bytes = cipher.doFinal(base642Byte(context));
        return new String(bytes,UTF8);
    }


/**
 * ###################################################################################
 * base64与十六进制编码
 */
    /**
     * Base64编码 默认由UTF8转换
     * @param context
     * @return
     */
    public static String base64Encode(String context) throws UnsupportedEncodingException {
        return byte2Base64(context.getBytes(UTF8));
    }

    /**
     * Base64解码 默认转换为UTF8
     * @param context
     * @return
     * @throws IOException
     */
    public static String base64Decode(String context) throws IOException {
        return new String(base642Byte(context),UTF8);
    }

    /**
     * 二进制转Base64
     * @param bytes
     * @return
     */
    public static String byte2Base64(byte[] bytes){
        BASE64Encoder base64Encoder = new BASE64Encoder();
        return base64Encoder.encode(bytes);
    }

    /**
     * Base64转二进制
     * @param base64
     * @return
     * @throws IOException
     */
    public static byte[] base642Byte(String base64) throws IOException {
        BASE64Decoder base64Decoder = new BASE64Decoder();
        return base64Decoder.decodeBuffer(base64);
    }

    /**
     * 字节数组转十六进制字符串
     *      利用位运算进行转换
     * @param bytes
     * @return
     */
    public static String encodeHex(byte[] bytes) {
        char chars[] = new char[32];
        for (int i = 0; i < chars.length; i = i + 2) {
            byte b = bytes[i / 2];
            chars[i] = HEX_CHARS[(b >>> 0x4) & 0xf];
            chars[i + 1] = HEX_CHARS[b & 0xf];
        }
        return new String(chars).toUpperCase();
    }

    /**
     * 十六进制字符串转字节数组
     * @param hex
     * @return
     */
    public static byte[] decodeHex(String hex){
        if (hex == null || hex.trim().equals("")) {
            return new byte[0];
        }

        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i < hex.length() / 2; i++) {
            String subStr = hex.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
        }

        return bytes;
    }

    public static class Base64KeyPair{

        public Base64KeyPair(String base64PublicKey, String base64PrivateKey) {
            this.base64PublicKey = base64PublicKey;
            this.base64PrivateKey = base64PrivateKey;
        }

        private String base64PublicKey;
        private String base64PrivateKey;

        public String getBase64PublicKey() {
            return base64PublicKey;
        }

        public void setBase64PublicKey(String base64PublicKey) {
            this.base64PublicKey = base64PublicKey;
        }

        public String getBase64PrivateKey() {
            return base64PrivateKey;
        }

        public void setBase64PrivateKey(String base64PrivateKey) {
            this.base64PrivateKey = base64PrivateKey;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Base64KeyPair that = (Base64KeyPair) o;
            return Objects.equals(base64PublicKey, that.base64PublicKey) &&
                    Objects.equals(base64PrivateKey, that.base64PrivateKey);
        }

        @Override
        public int hashCode() {

            return Objects.hash(base64PublicKey, base64PrivateKey);
        }
    }
}
