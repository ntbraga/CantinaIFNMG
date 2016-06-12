/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cantina.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author ntbra
 */
public class Teste {
    public static void main(String[] args) throws IOException {
        File f = new File("/teste/ICONS");
        if(f.isDirectory()){
            for(File file: f.listFiles()){
                BufferedImage image = ImageIO.read(file);
                for(int i = 16; i<=64; i = i*2){
                    File to = new File("/teste/ICS/"+i+"/"+file.getName());
                    to.getParentFile().mkdirs();
                    ImageIO.write(getAsBuf(image.getScaledInstance(i, i, Image.SCALE_SMOOTH)), "png", to);
                }
            }
        }
    }
    
    
    public static BufferedImage getAsBuf(Image in){
        BufferedImage bImage      = new BufferedImage(in.getWidth(null), in.getHeight(null), BufferedImage.TYPE_INT_ARGB_PRE);

        //obtain it's graphics
        Graphics2D bImageGraphics = bImage.createGraphics();

        //draw the Image (image) into the BufferedImage (bImage)
        bImageGraphics.drawImage(in, null, null);

        // cast it to rendered image
        return bImage;
    }
    
    /*
    public static void main(String[] args) throws IOException {
        File root = new File(Teste.class.getResource("/br/com/cantina/icons/").getFile());
        recursiveColor(root);
    }
    
    
    public static void recursiveColor(File path) throws IOException{
        if(path.isDirectory()){
            for(File f: path.listFiles()){
                recursiveColor(f);
            }
        }else if(path.isFile()){
            if(!path.getParent().endsWith("logo")){
                String t = path.getParent();
                t = t.substring(t.lastIndexOf("\\")+1);
                
                BufferedImage image = Utils.colorImage(ImageIO.read(path), new Color(31, 51, 162));
                File f = new File("/teste/"+t+"/"+path.getName());
                f.getParentFile().mkdirs();
                ImageIO.write(image, "png", f);
            }
        }
    }
    */
}
