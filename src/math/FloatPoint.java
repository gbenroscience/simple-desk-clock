/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package math;

/**
 *
 * @author JIBOYE, Oluwagbemiro Olaoluwa <gbenroscience@yahoo.com>
 */
 
 
import java.awt.Point;
import java.io.Serializable;
import static java.lang.Math.*;
/**
 * Objects of this class can be used to represent points in 3D space
 * as they use double numbers to represent coordinates unlike
 * objects of class Point. Objects of both classes are inter-convertible
 * through their <code/> toPoint methods.
 * @author GBEMIRO
 */
public class FloatPoint implements Serializable{
    
public double x;
public double y;
public double z;

    public FloatPoint() {
    }

    public FloatPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }
        public FloatPoint(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
        public FloatPoint(FloatPoint floatPoint){
            this.x = floatPoint.x;
            this.y = floatPoint.y;
            this.z = floatPoint.z;
        }
        public FloatPoint(Point point){
            this.x = point.x;
            this.y = point.y;
            this.z = 0;
        }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }
    
       
    /***
    *
    * @param pt the FloatPoint object whose distance to this Point object is required
    * @return the distance between the 2 FloatPoint objects.
    */

   public double calcDistanceTo(FloatPoint pt){
       return sqrt(  pow( (x-pt.x),2)+pow( (y-pt.y),2)+pow( (z-pt.z),2) );
   }
   /***
   *
   * @param pt the Point object whose distance to this FloatPoint object is required
   * @return the distance between the 2 objects.
   */

  public double calcDistanceTo(Point pt){
      return sqrt(  pow( (x-pt.x),2)+pow( (y-pt.y),2) );
  }
/**
 * 
 * @return a Point object from this FloatPoint.
 */
public Point toPoint(){
    return new Point((int)x,(int)y);
}
/**
 *
 * @param pt the point between which an imaginary line runs
 * @return the gradient of the projection of the line joining
 * these points on the XY plane
 */
public double findXYGrad(FloatPoint pt){
    return (y-pt.y)/(x-pt.x);
}

/**
 *
 * @param pt the point between which an imaginary line runs
 * @return the gradient of the projection of the line joining
 * these points on the XZ plane
 */
public double findXZGrad(FloatPoint pt){
    return (z-pt.z)/(x-pt.x);
}

/**
 *
 * @param pt the point between which an imaginary line runs
 * @return the gradient of the projection of the line joining
 * these points on the YZ plane
 */
public double findYZGrad(FloatPoint pt){
    return (z-pt.z)/(y-pt.y);
}





/**
 *
 * @param p1 The first Point object.
 * @param p2 The second Point object.
 * @return The Point object that contains the coordinates
 * of the midpoint of the line joining p1 and p2
 */
public static Point midPoint(Point p1,Point p2){
    return new Point((int) (0.5*(p1.x+p2.x)),(int)(0.5*(p1.y+p2.y))  );
}

/**
 *
 * @param p1
 * @param p2
 * @return true if the 2 points and this one
 * lie on the same straight line.
 */
public boolean isCollinearWith(FloatPoint p1,FloatPoint p2){
    Line line=new Line(p1,p2);
    return line.passesThroughPoint(this);
}


/**
 *
 * @param p1
 * @param p2
 * @return true if this Point object lies on the same
 * straight line with p1 and p2 and it lies in between them.
 */
public boolean liesBetween(FloatPoint p1,FloatPoint p2){
    boolean truly1 = ( (p1.x<=x&&p2.x>=x)||(p2.x<=x&&p1.x>=x)  );
boolean truly2 = ( (p1.y<=y&&p2.y>=y)||(p2.y<=y&&p1.y>=y)  );
boolean truly3 = ( (p1.z<=z&&p2.z>=z)||(p2.z<=z&&p1.z>=z)  );

return truly1&&truly2&&truly3&&isCollinearWith(p1, p2); 
}


    @Override
public String toString(){
    return "("+x+" "+y+" "+z+")" ;
}

    @Override
    public boolean equals(Object obj){
        return this.toString().equals(obj.toString());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.z) ^ (Double.doubleToLongBits(this.z) >>> 32));
        return hash;
    }
    
    
    
}
