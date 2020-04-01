package com.ims.c02twoDCode;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.CharacterSetECI;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Hashtable;

/**
 * 生成二维码
 */
public class ZXingStudy {

    /**
     * 生成二维码图片
     *
     * @param content 二维码内容
     * @param width   宽
     * @param height  高
     * @param margin  二维码外边距，0到4
     */
    public void createQRCode(String content, int width, int height, int margin) throws WriterException, IOException {
        /**
         * 二维码参数设定
         */
        Hashtable<EncodeHintType, Object> hintTypes = new Hashtable<EncodeHintType, Object>();
        hintTypes.put(EncodeHintType.CHARACTER_SET, CharacterSetECI.UTF8);  //编码方式
        hintTypes.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H); //容错级别，共4级，级别越高，遮挡面积越大，也能被识别
        hintTypes.put(EncodeHintType.MARGIN, margin);//二维码外边距
        /**
         * 生成二维码，本质为二位数组
         */
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hintTypes);
        /**
         * 将二维码写入图片
         **/
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                bufferedImage.setRGB(i, j, bitMatrix.get(i, j) ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
            }
        }
        /**
         * 将二维码图片输出到输出流
         **/
        File imageFile = new File("QRCode.png");
        ImageIO.write(bufferedImage, "png", imageFile);
    }

    /**
     * 生成二维码图片字节数组
     *
     * @param content 二维码内容
     * @param width   宽
     * @param height  高
     * @param margin  外边距
     * @return 二维码对应的字节数组
     * @throws Exception
     */
    public byte[] getQRCodeBytes(String content, int width, int height, int margin) throws Exception {
        /**
         * 二维码参数设定
         */
        Hashtable<EncodeHintType, Object> hintTypes = new Hashtable<EncodeHintType, Object>();
        hintTypes.put(EncodeHintType.CHARACTER_SET, CharacterSetECI.UTF8);//设置编码
        hintTypes.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);//设置容错级别
        hintTypes.put(EncodeHintType.MARGIN, margin);//设置外边距
        /**
         * 生成二维码，本质为二位数组
         */
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hintTypes);
        /**
         * 将二维码写入图片，红色
         **/
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                bufferedImage.setRGB(i, j, bitMatrix.get(i, j) ? Color.RED.getRGB() : Color.WHITE.getRGB());
            }
        }
        /**
         * 将二维码图片转为字节数组
         **/
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", outputStream);
        byte[] bytes = outputStream.toByteArray();
        outputStream.close();
        return bytes;
    }

    public static void main(String[] args) throws Exception {
        ZXingStudy zXingStudy = new ZXingStudy();
        //zXingStudy.createQRCode("hello", 300, 300, 4);
        byte[] bytes = zXingStudy.getQRCodeBytes("你好", 500, 500, 1);
        FileUtils.writeByteArrayToFile(new File("我的二维码.png"), bytes, true);
    }
}
