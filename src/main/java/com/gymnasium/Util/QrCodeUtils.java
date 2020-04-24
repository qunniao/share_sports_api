package com.gymnasium.Util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.gymnasium.file.FastDFSClient;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Hashtable;
import java.util.Random;

/**
 * @author 王志鹏
 * @title: QrCodeUtils
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/23 17:42
 */
public class QrCodeUtils {
    private static final String CHARSET = "utf-8";
    public static final String FORMAT = "JPG";

    private static final int QRCODE_SIZE = 300;
    private static final int LOGO_WIDTH = 60;
    private static final int LOGO_HEIGHT = 60;

    public static BufferedImage createImage(String content, String logoPath, boolean needCompress) throws Exception {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE,
                hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        if (logoPath == null || "".equals(logoPath)) {
            return image;
        }

        QrCodeUtils.insertImage(image, logoPath, needCompress);

        return image;
    }

    private static void insertImage(BufferedImage source, String logoPath, boolean needCompress) throws IOException {
        InputStream inputStream = null;
        try {
            File f = new File(logoPath);

            inputStream = new FileInputStream(f);
            Image src = ImageIO.read(inputStream);

            int width = src.getWidth(null);
            int height = src.getHeight(null);
            if (needCompress) { // 压缩LOGO
                if (width > LOGO_WIDTH) {
                    width = LOGO_WIDTH;
                }
                if (height > LOGO_HEIGHT) {
                    height = LOGO_HEIGHT;
                }
                Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics g = tag.getGraphics();
                g.drawImage(image, 0, 0, null); // 绘制缩小后的图
                g.dispose();
                src = image;
            }
            // 插入LOGO
            Graphics2D graph = source.createGraphics();
            int x = (QRCODE_SIZE - width) / 2;
            int y = (QRCODE_SIZE - height) / 2;
            graph.drawImage(src, x, y, width, height, null);
            Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
            graph.setStroke(new BasicStroke(3f));
            graph.draw(shape);
            graph.dispose();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    public static String encode(String content, String logoPath, String destPath, boolean needCompress) throws Exception {
        BufferedImage image = QrCodeUtils.createImage(content, logoPath, needCompress);
        mkdirs(destPath);
        String fileName = new Random().nextInt(99999999) + "." + FORMAT.toLowerCase();
        ImageIO.write(image, FORMAT, new File(destPath + "/" + fileName));
        return fileName;
    }

    public static String encode(String content, String logoPath, String destPath, String fileName, boolean needCompress) throws Exception {
        BufferedImage image = QrCodeUtils.createImage(content, logoPath, needCompress);
        mkdirs(destPath);
        fileName = fileName.substring(0, fileName.indexOf(".") > 0 ? fileName.indexOf(".") : fileName.length())
                + "." + FORMAT.toLowerCase();
        ImageIO.write(image, FORMAT, new File(destPath + "/" + fileName));
        return fileName;
    }

    public static void mkdirs(String destPath) {
        File file = new File(destPath);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
    }

    public static String encode(String content, String logoPath, String destPath) throws Exception {

        return QrCodeUtils.encode(content, logoPath, destPath, false);
    }

    public static String encode(String content, String destPath, boolean needCompress) throws Exception {
        return QrCodeUtils.encode(content, null, destPath, needCompress);
    }

    public static String encode(String content, String destPath) throws Exception {

        return QrCodeUtils.encode(content, null, destPath, false);
    }

    public static void encode(String content, String logoPath, OutputStream output, boolean needCompress) throws Exception {

        BufferedImage image = QrCodeUtils.createImage(content, logoPath, needCompress);
        ImageIO.write(image, FORMAT, output);
    }

    public static void encode(String content, OutputStream output) throws Exception {

        QrCodeUtils.encode(content, null, output, false);
    }


    public static String url_FastFDFS(String content, String logoPath, boolean needCompress) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedImage image = QrCodeUtils.createImage(content, logoPath, needCompress);

        ImageIO.write(image, FORMAT, baos);

        FastDFSClient fastDFSClient = new FastDFSClient();
        String url = fastDFSClient.uploadFile(baos.toByteArray(), FORMAT);

        return url;
    }

    public static void main(String[] args) throws Exception {

//        FileOutputStream output = new FileOutputStream("/home/bbb.jpg");
//        encode("userId", output);

        url_FastFDFS("aaaaa", "", false);
    }
}
