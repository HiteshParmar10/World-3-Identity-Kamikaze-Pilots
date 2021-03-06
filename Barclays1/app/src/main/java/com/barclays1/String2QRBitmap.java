package com.barclays1;

import java.util.EnumMap;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;


public class String2QRBitmap {
    public static Bitmap sting2QRBitmap(String text) {
        BarcodeFormat barcodeFormat = BarcodeFormat.QR_CODE;

        int width0 = 350;
        int height0 = 350;

        int colorBack = 0xFF000000;
        int colorFront = 0xFFFFFFFF;

        QRCodeWriter writer = new QRCodeWriter();
        try {
            EnumMap<EncodeHintType, Object> hint = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
            hint.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hint.put(EncodeHintType.MARGIN, 1);
            BitMatrix bitMatrix = writer.encode(text, barcodeFormat, width0, height0, hint);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                int offset = y * width;
                for (int x = 0; x < width; x++) {

                    pixels[offset + x] = bitMatrix.get(x, y) ? colorBack : colorFront;
                }
            }

            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        } catch (WriterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
