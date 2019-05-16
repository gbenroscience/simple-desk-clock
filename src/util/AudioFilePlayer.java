/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.BufferedInputStream;
import java.io.File; 
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;


/**
 *
 * @author JIBOYE, Oluwagbemiro Olaoluwa <gbenroscience@yahoo.com>
 */
public class AudioFilePlayer {

    public static void main(String[] args) {
        final AudioFilePlayer player = new AudioFilePlayer();
        player.playFromStream("/Users/gbemirojiboye/Documents/StudioProjects/FoloFolo/app/src/main/res/raw/kirbylaser.ogg");
        //player.play("something.ogg");
    }

    public void playFromStream(BufferedInputStream stream) { 
        BasicPlayer player = new BasicPlayer();
        try {
            player.open(stream);
            player.play();
        } catch (BasicPlayerException e) {
            e.printStackTrace();
        }
    }
    
    
    public void playFromStream(String path) { 
        BasicPlayer player = new BasicPlayer();
        try {
            player.open(new File(path));
            player.play();
        } catch (BasicPlayerException e) {
            e.printStackTrace();
        }
    }
    
}
