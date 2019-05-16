/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parsing;

/**
 *
 * @author JIBOYE, Oluwagbemiro Olaoluwa <gbenroscience@yahoo.com>
 */ 

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
 
import parsing.CustomScanner;
 

/**
 *
 * @author GBEMIRO
 */
public class StringOperations {
    
    
    
/**
 * @param text The text to split into lines of text.
 * The splitting algorithm ensures that each line does not
 * have more than <code>charsPerLine</code> characters.
 * @param charsPerLine The number of characters per line.
 * @return the text divided into lines.
 */
    public static ArrayList<String> getLinesByMaxCharsAlgorithm(String text,int charsPerLine){
     ArrayList<String> lines = new ArrayList();
     
        CustomScanner cs = new CustomScanner(text, true, " ","\n");
        java.util.List<String>list = cs.scan();
        int sz = list.size();
        
     for(int i=0;i<sz;i++){
       String line = "";
       
       while(line.length()<=charsPerLine && i<sz ){
           if(list.get(i).equals("\n")){
               break;
           }
           line = line.concat(list.get(i));
           if(line.length()>charsPerLine){
               
           }
           else{
           ++i;
           }
       }
       lines.add(line);
       
         
         
     }//end for loop
     
        return lines;
        
    }//end method
    
/**
 * @param text The text to split into lines of text.
 * @param lineWidth The maximum width of the line.
 * @param f The font used to display the text.
 * The splitting algorithm ensures 
 * @return the text divided into lines.
 */
    public static ArrayList<String> getLinesByMaxWidthAlgorithm(String text,int lineWidth,Font f){
     ArrayList<String> lines = new ArrayList<String>();
     
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        
        Graphics2D g = image.createGraphics();
        
     
        CustomScanner cs = new CustomScanner(text, true, " ","\n");
        java.util.List<String>list = cs.scan();
        int sz = list.size();
        
        FontMetrics fm = g.getFontMetrics(f);
        
     for(int i=0;i<sz;i++){
       String line = "";
       int wid = fm.stringWidth(line);
       while(wid<=lineWidth && i<sz ){
           if(list.get(i).equals("\n")){
               break;
           }
           line = line.concat(list.get(i));
           wid = fm.stringWidth(line);
           if(wid>=lineWidth){
               break;
           }
           else{
           ++i;
           }
           
       }//end while
       lines.add(line);
       
         
         
     }//end for loop
     
        return lines;
        
    }//end method
    
}
