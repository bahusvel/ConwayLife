package com.bahus.ConwayLife.Core.ExternalData;

import com.bahus.ConwayLife.Core.Storage.Bounds;
import com.bahus.ConwayLife.Core.Storage.NoHashBitMap.BitArray2D;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;

/**
 * Created by denislavrov on 8/21/14.
 */
public class Export_Import_Controller {

    public static boolean save(BitArray2D cells, File file, String format){
        if (format.equalsIgnoreCase("rle")){
            Bounds lbounds = cells.getBounds();
            int[][] data = new int[lbounds.hx-lbounds.lx][lbounds.hy-lbounds.ly];

            for (int x = 0; x < data.length; x++) {
                for (int y = 0; y < data[0].length; y++) {
                    //ide will complain but its fine, they should be swaped for inflection
                    data[x][y] = cells.get(y,x) ? 1 : 0;
                }
            }

            RLE.write(file, data);

        }

        Bounds bounds = cells.getBounds();
        int width = (bounds.width)+1 , height = (bounds.height)+1;
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
        Graphics gg = bi.getGraphics();
        gg.setColor(Color.white);
        gg.fillRect(0,0,bi.getWidth(), bi.getHeight());

        for (int y : cells.yValues()){
            for (int x : cells.xValues(y)) {
                if(cells.get(x,y)) bi.setRGB(x-bounds.lx,y-bounds.ly,colorToRGB(0,0,0));
            }
        }


        try {
            // retrieve image
            ImageIO.write(bi, format, file);
        } catch (IOException ignored) {
            System.out.println("Something bad happened when writing the image");
            return false;
        }
        return true;
    }

    public static boolean load(BitArray2D cells, File file, String format){
        if (format.equalsIgnoreCase("rle")){
            try {
                int[][] data = RLE.read(file);
                cells.clear();
                for (int x = 0; x < data.length; x++) {
                    for (int y = 0; y < data[0].length; y++) {
                        //ide will complain but its fine, they should be swaped for inflection
                        cells.set(y, x, data[x][y] > 0);
                    }
                }
                return true;
            } catch (Exception ignored){
                return false;
            }

        }
        BufferedImage bi;
        try {
            bi = ImageIO.read(file);
        } catch (IOException exc) {
            System.out.println("Something bad happened when reading the image");
            return false;
        }
        cells.clear();
        for (int x = 0; x < bi.getWidth(); x++ ){
            for (int y = 0; y < bi.getHeight(); y++){
                cells.set(x,y,rgbToBoolean(bi.getRGB(x,y)));
            }
        }
        return true;

    }

    private static boolean rgbToBoolean(int rgbInt){
        final int THRESH = 50;

        return !(((rgbInt & 255) > THRESH) && (((rgbInt >> 8) & 255) > THRESH) && (((rgbInt >> 16) & 255) > THRESH));

    }

    private static int colorToRGB(int red, int green, int blue) {
        int newPixel = 0;
        newPixel += 255;
        newPixel = newPixel << 8;
        newPixel += red; newPixel = newPixel << 8;
        newPixel += green; newPixel = newPixel << 8;
        newPixel += blue;

        return newPixel;
    }

    public static String[] getFormats(){
        HashSet<String> ret = new HashSet<>();
        for (String tmp : ImageIO.getWriterFormatNames()) ret.add(tmp.toLowerCase());
        ret.add("rle");
        return ret.toArray(new String[ret.size()]);
    }

    public static boolean validFormat(String extension){
        String[] validFormats = ImageIO.getWriterFormatNames();
        for (String format : validFormats){
            if (format.equals(extension)) return true;
        }
        return extension.equalsIgnoreCase("rle");
    }

}
