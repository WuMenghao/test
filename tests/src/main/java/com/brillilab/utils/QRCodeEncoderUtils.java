package com.brillilab.utils;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;


public class QRCodeEncoderUtils {
    public static void encoder(String url) throws Exception{
        int width = 300;
        int height = 300;
        String format = "png";
        String content = "https://www.csdn.net";

        //定义二维码参数
        Map<Object,Object> map = new HashMap<>();
        map.put(EncodeHintType.CHARACTER_SET,"utf-8");
        map.put(EncodeHintType.ERROR_CORRECTION,ErrorCorrectionLevel.M);
        map.put(EncodeHintType.MARGIN,2);

        try {
            BitMatrix encode = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height);

            Path file = new File("C:/code/img.png").toPath();

            MatrixToImageWriter.writeToPath(encode,format,file);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
