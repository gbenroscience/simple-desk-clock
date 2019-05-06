/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.awt.Graphics;

/**
 *
 * @author Gbemiro Jiboye
 */
public class DrawAdapter{
/**
 * Allows users to draw arcs relative to the center of its parent oval
 * instead of relative to the top left corner of its parent oval.
 *
 * @param g the Graphics object used to draw the arc
 * @param x the x coordinate of the center of the imaginary oval that the arc forms a part of.
 * @param y the y coordinate of the center of the imaginary oval that the arc forms a part of.
 * @param width the width of the imaginary oval that the arc forms a part of.
 * @param height the height of the imaginary oval that the arc forms a part of.
 * @param startAngle  the angle relative to 0 degs (at the 3 0'clock hand of the clock)
 * @param arcAngle  the angle that the arc will subtend at the center of its oval.
 */
    public void drawArc(Graphics g,int x,int y,int width,int height,int startAngle,int arcAngle) {
     g.drawArc(x-(width/2), y-(height/2), width, height, startAngle, arcAngle);
    }


/**
 * Allows users to draw arcs relative to the center of its parent oval
 * instead of relative to the top left corner of its parent oval.
 *
 * @param g the Graphics object used to draw the arc
 * @param x the x coordinate of the center of the imaginary oval that the arc forms a part of.
 * @param y the y coordinate of the center of the imaginary oval that the arc forms a part of.
 * @param width the width of the imaginary oval that the arc forms a part of.
 * @param height the height of the imaginary oval that the arc forms a part of.
 * @param startAngle  the angle relative to 0 degs (at the 3 0'clock hand of the clock)
 * @param arcAngle  the angle that the arc will subtend at the center of its oval.
 */
    public void fillArc(Graphics g,int x,int y,int width,int height,int startAngle,int arcAngle) {
     g.fillArc(x-(width/2), y-(height/2), width, height, startAngle, arcAngle);
    }


/**
 * Allows users to draw ovals relative to the center of its parent rectangle
 * instead of relative to the top left corner of its parent rectangle.
 * The oval drawn is aone that is filled with color.
 * @param g the Graphics object used to draw the arc
 * @param x the x coordinate of the center of the imaginary oval that the arc forms a part of.
 * @param y the y coordinate of the center of the imaginary oval that the arc forms a part of.
 * @param width the width of the imaginary oval that the arc forms a part of.
 * @param height the height of the imaginary oval that the arc forms a part of.
 */
    public void fillOval(Graphics g,int x,int y,int width,int height) {
     g.fillOval(x-(width/2), y-(height/2), width, height);
    }

  /**
 * Allows users to draw ovals relative to the center of its parent rectangle
 * instead of relative to the top left corner of its parent rectangle.
 *
 * @param g the Graphics object used to draw the arc
 * @param x the x coordinate of the center of the imaginary oval that the arc forms a part of.
 * @param y the y coordinate of the center of the imaginary oval that the arc forms a part of.
 * @param width the width of the imaginary oval that the arc forms a part of.
 * @param height the height of the imaginary oval that the arc forms a part of.
 */
    public void drawOval(Graphics g,int x,int y,int width,int height) {
     g.drawOval(x-(width/2), y-(height/2), width, height);
    }








}
