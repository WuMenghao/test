package com.brillilab.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import com.swetake.util.Qrcode;

public class QRCodeEncoderUtils {
    public static void encoder(String url) throws Exception{
        Qrcode qrcode = new Qrcode();
        qrcode.setQrcodeErrorCorrect('M');
        qrcode.setQrcodeEncodeMode('B');
        qrcode.setQrcodeVersion(9);

		/*FileImageInputStream fileOut = new FileImageInputStream(new File("/home/fy_iipay/a.jpg"));
		byte[] buf = new byte[1024];
	      int numBytesRead = 0;
	      ByteArrayOutputStream output = new ByteArrayOutputStream();
	      while ((numBytesRead = fileOut.read(buf)) != -1) {
	      output.write(buf, 0, numBytesRead);
	      }*/
        byte[] d = url.getBytes("utf-8");
        BufferedImage bi = new BufferedImage(165, 165, BufferedImage.TYPE_INT_RGB);
        // createGraphics
        Graphics2D g = bi.createGraphics();
        // set background
        g.setBackground(Color.WHITE);
        g.clearRect(0, 0, 165, 165);
        g.setColor(Color.BLACK);

        if (d.length > 0 && d.length < 500) {
            boolean[][] b = qrcode.calQrcode(d);
            for (int i = 0; i < b.length; i++) {
                for (int j = 0; j < b.length; j++) {
                    if (b[j][i]) {
                        g.fillRect(j * 3 + 2, i * 3 + 2, 3, 3);
                    }
                }
            }
        }

        g.dispose();
        bi.flush();
        String FilePath = "D:/fy_insure/weitu.jpg";
        File f = new File(FilePath);
        if (!f.exists()){
            f.mkdirs();
        }
        ImageIO.write(bi, "jpg", f);
    }

}
