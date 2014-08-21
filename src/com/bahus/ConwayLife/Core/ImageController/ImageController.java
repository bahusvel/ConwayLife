package com.bahus.ConwayLife.Core.ImageController;

import com.bahus.ConwayLife.Core.Storage.BitArray2D;
import com.bahus.ConwayLife.Core.Storage.Bounds;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by denislavrov on 8/21/14.
 */
public class ImageController {

    public static void save(BitArray2D cells, File file, String format){
        Bounds bounds = cells.getBounds();
        int width = (bounds.hx - bounds.lx)+1 , height = (bounds.hy-bounds.ly)+1;
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
        }

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

    public static void load(BitArray2D cells, File file){

    }

    public static String[] getFormats(){
        return ImageIO.getWriterFormatNames();
    }

}
