/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

/**
 *
 * @author JIBOYE, Oluwagbemiro Olaoluwa <gbenroscience@yahoo.com>
 */
   
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable; 
import util.Clock;

public interface Sprite extends Serializable{


  public void setLocation( int x , int y);
  public Point getLocation();

  public void setWidth( int width );
  public int getWidth();

  void setHeight( int height );
  public int getHeight();

  public double distanceTo( Sprite s );
  public boolean contains( Point p );
  
/**
 * @param clock The clock that owns this graphics.
 * @param g The graphics(from the clock) used to draw on the clock
 * Renders the image representing the current
 * state of this Sprite.
 */
  public void draw(Clock clock, Graphics2D g);

  public void setVisible( boolean visible );
  public boolean isVisible();

  public Rectangle getBoundingRect();

  public int getBottom();

	public int getRight();

  public boolean intersects(Sprite s);
  /**
   * Scales the size of this Sprite to the values
   * currently specified.
   * @param w The new width of this Sprite.
   * @param h The new height of this Sprite.
   */
  public void scale(int w, int h);


  
  
}