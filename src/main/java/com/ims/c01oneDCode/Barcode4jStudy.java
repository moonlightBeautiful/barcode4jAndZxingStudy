package com.ims.c01oneDCode;

import org.krysalis.barcode4j.HumanReadablePlacement;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

import java.awt.image.BufferedImage;
import java.io.*;

/**
 * 一维码生成
 */
public class Barcode4jStudy {

    public static void main(String[] args) throws IOException {
        /**
         * 绘图配置
         */
        String path = "barcode.png";
        File file = new File(path);
        OutputStream os = new FileOutputStream(file);   //条形码输出流
        String format = "image/png";    //条形码图片类型
        int dpi = 150;  // 分辨率：值越大条码越大（长、高）。
        BitmapCanvasProvider canvas = new BitmapCanvasProvider(os, format, dpi,
                BufferedImage.TYPE_BYTE_BINARY, false, 0);
        /**
         * 生成条形码
         */
        Code128Bean bean = new Code128Bean();  //条码类型bean，还有其他类型的bean，Code128Bean/Code39Bean...等等
        bean.setModuleWidth(UnitConv.in2mm(2.0f / dpi)); // 设置条码每一条的宽度，也就是可以通过此设置总宽度
        bean.setBarHeight(10);  // 设置条码的高度
        bean.doQuietZone(false); // 设置条码两侧是否加空白
        bean.setMsgPosition(HumanReadablePlacement.HRP_TOP); // 设置条形码存储的文本内容在条形码的位置（包括是否显示、上、下）
        String msg = "12345678910";  // 条码存储的文本内容
        bean.generateBarcode(canvas, msg);

        /**
         * 结束绘制，关闭输出流
         */
        canvas.finish();
        os.close();
    }
}
