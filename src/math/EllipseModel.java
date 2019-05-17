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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import static java.lang.Math.*;
import util.DrawAdapter;
/**
 * Describes basic geometrical
 * features of an Ellipse.
 * @author GBEMIRO
 */
public class EllipseModel extends DrawAdapter{
/**
 * Stores:
 * The x coordinate of the elliptical center
 * The y coordinate of the elliptical center
 */
 private FloatPoint center;

 /**
  *  The Dimension object that stores the
  *  half-length of the major axis of the EllipseModel object and
  *  the half-length of the minor axis of the EllipseModel object.
  */
 private DimensionFloat size;


 /**
  *
  * The equation of an ellipse is ((x-h)/a)^2+((y-k)/b)^2=1
  *
  * Here a = half of the length of the major axis, b = half the length of the minor axis,
  * and h,k are the central coordinates of the ellipse
  *
  *
  * @param xCenter the x coordinate of the center of the EllipseModel object
  * @param yCenter the y coordinate of the center of the EllipseModel object
  * @param width the half length of the major axis
  * @param height the half length of the minor axis
  */
    public EllipseModel(double xCenter, double yCenter, double width, double height) {
        this.center = new FloatPoint(xCenter, yCenter);
        this.size = new DimensionFloat(width, height);
    }

 /**
  *
  * The equation of an ellipse is ((x-h)/a)^2+((y-k)/b)^2=1
  *
  * Here a = half of the length of the major axis, b = half the length of the minor axis,
  * and h,k are the central coordinates of the ellipse
  *
  *
  * Creates a new EllipseModel object centered about Point pt
  * @param center the Point object to assign to this EllipseModel object.
  * @param width the half length of the major axis
  * @param height the half length of the minor axis
  */
    public EllipseModel(FloatPoint center, double width, double height) {
        this.center=center;
        this.size = new DimensionFloat(width, height);
    }

 /**
  * Creates a new EllipseModel object that is an exact copy of the EllipseModel object
  * passed as an argument to this constructor.
  *@param model The EllipseModel object whose replica is needed.
  */
    public EllipseModel(EllipseModel model) {
        this.center=model.center;
        this.size = model.getSize();
    }
    
    
    /**
     * The largest rectangle that can be inscribed in an ellipse
     * is a rectangle of area 2.a.b where 'a' is the half-length of the major axis and
     * 'b' is the half-length of the minor axis.
     * @return the biggest rectangle that can be inscribed in the ellipse
     */
    public Rectangle getBiggestRectangle(){
        
        
        Rectangle r = new Rectangle();
        
        r.x = (int) (this.center.x - 0.5*this.size.width*sqrt(2));
        r.y = (int) (this.center.y - 0.5*this.size.height*sqrt(2));
        
        r.width = (int) (this.size.width*sqrt(2));
        r.height = (int) (this.size.height*sqrt(2));
        
        
        return r;
        
    }



/**
 * 
 * @param width sets the half-length of the major axis of the EllipseModel object
 */
    public void setWidth(double width) {
        size.width = width;
    }
/**
 *
 * @return  the half-length of the major axis of the EllipseModel object
 */
    public double getWidth() {
        return size.width;
    }
/**
 *
 * @param height sets half-length of the minor axis of the EllipseModel object
 */
    public void setHeight(double height) {
        size.height = height;
    }
/**
 *
 * @return the half-length of the minor axis of the EllipseModel object
 */
    public double getHeight() {
        return size.height;
    }
/**
 *
 * @param x sets the x-coordinate of trhe elliptical center
 */
    public void setXCenter(double x) {
        this.center.x = x;
    }
/**
 *
 * @return the x-coordinate of trhe elliptical center
 */
    public double getXCenter() {
        return this.center.x;
    }
/**
 *
 * @param y sets the y-coordinate of trhe elliptical center
 */
    public void setYCenter(double y) {
        this.center.y=y;
    }
/**
 *
 * @return the y-coordinate of trhe elliptical center
 */
    public double getYCenter() {
        return this.center.y;
    }

/**
 *
 * @return the EllipseModel object's eccentricity
 */
    public double getEccentricity(){
        return sqrt( pow(size.width,2)-pow(size.height,2) )/size.width;
    }

public double area(){
    return PI*size.width*size.height;
}

/**
 *
 * @param y the y coordinate of
 * a given point on an ellipse.
 * @return the 2 possible x coordinates of that point.
 */
public double[] getX(double y){
double x[]=new double[2];

double evalYPart= pow( (y-this.center.y)/size.height,2);

x[0]=this.center.x+size.width*sqrt(1-evalYPart );
x[1]=this.center.x-size.width*sqrt(1-evalYPart );
    return x;
}
/**
 *
 * @param x the x coordinate of
 * a given point on an ellipse.
 * @return the 2 possible y coordinates of that point.
 */
public double[] getY(double x){
double y[]=new double[2];

double evalXPart=pow((x-this.center.x)/size.width,2);

y[0]=this.center.y+size.height*sqrt(1-evalXPart );
y[1]=this.center.y-size.height*sqrt(1-evalXPart );
    return y;
}
/**
 *
 * @param p the Point to check if or not it lies on the EllipseModel
 * @return true if it lies on the EllipseModel object or deviates from its
 * equation by 1.0E-14 or lesser.
 */
public boolean isOnEllipse(FloatPoint p){
    double eval = pow(((p.x-this.center.x)/size.width),2)+pow(((p.y-this.center.y)/size.height),2);
return approxEquals(eval, 1);
}
/**
 * 
 * @param center sets the center of this EllipseModel object.
 */
    public void setCenter(FloatPoint center) {
        this.center = center;
    }
/**
 *
 * @return The center of this EllipseModel object
 */
    public FloatPoint getCenter() {
        return center;
    }
/**
 *
 * @param size sets the size of this EllipseModel object
 */
    public void setSize(DimensionFloat size) {
        this.size = size;
    }
/**
 *
 * @return the size of this EllipseModel object
 */
    public DimensionFloat getSize() {
        return size;
    }



/**
 * The theory behind this is that for a point
 * to be inside an ellipse, a line that passes through
 * the center of the ellipse and this point will cut the ellipse
 * at 2 points such that the point will lie in between both points.
 * @param p the Point object
 * @return true if the Point object is located inside this EllipseModel object.
 */
public boolean contains(FloatPoint p){
    Line line= new Line(p,this.center);
double x1=0;
double y1=0;
double x2=0;
double y2=0;
    String soln[] =lineIntersection(line);

try{
    x1=Double.valueOf( soln[0] );
    y1=Double.valueOf( soln[1] );
    x2=Double.valueOf( soln[2] );
    y2=Double.valueOf( soln[3] );
}//end try
catch(NumberFormatException numErr){
    return false;
}//end try
catch(NullPointerException numErr){
    return false;
}//end try
boolean truly1 = ( (x1<=p.x&&x2>=p.x)||(x2<=p.x&&x1>=p.x)  );
boolean truly2 = ( (y1<=p.y&&y2>=p.y)||(y2<=p.y&&y1>=p.y)  );





return truly1&&truly2;
}//end







/**
 *
 * @param line the Line object that cuts this EllipseModel
 * @return the possible coordinates of the points where the Line object cuts this EllipseModel object
 * The result is returned in the format:
 * Let the solution array be array[], then:
 *
 * array[0] = x coordinate of the first point;
 * array[1] = y coordinate of the first point;
 * array[2] = x coordinate of the second point;
 * array[3] = y coordinate of the second point;
 *
 * The coordinates are returned as strings
 * to account for complex solutions too.
 *
 * THIS METHOD WILL RETURN A NULL ARRAY AND THROW
 * A NullPointerException IF NO INTERSECTION
 * OCCURS.
 * @throws NullPointerException
 */
public String[] lineIntersection(Line line) throws NullPointerException{
    String str[]= new String[4];
    double m = line.getM();
    double c = line.getC();


    double A= pow(size.height,2)+pow(size.width*m,2);
    double B=2*( pow(size.width,2)*m*(c-this.center.y)-pow(size.height,2)*this.center.x );
    double C= pow(this.center.x*size.height,2)+pow(size.width,2)*( pow(c-this.center.y,2)-pow(size.height,2));
    Quadratic quad = new Quadratic(A,B,C);
    String str1[]=quad.soln();

try{
str[0]=str1[0];
str[1]=str1[1];
str[2]=String.valueOf(  m*Double.valueOf(str1[0])+c  );
str[3]=String.valueOf(  m*Double.valueOf(str1[1])+c  );

str1=new String[4];

str1[0]=str[0];
str1[1]=str[2];
str1[2]=str[1];
str1[3]=str[3];

}//end try
catch(NumberFormatException nolian){
str1=null;
str=null;
}//end catch
catch(NullPointerException nolian){
str1=null;
str=null;
}//end catch


return str1;
}//end method

/**
 *
 * @param line The Line object
 * @return true if the EllipseModel object
 * intersects with th Line object.
 */
public boolean intersectsWithLine(Line line){
    boolean intersects=false;
    try{
     String c[]=lineIntersection(line);
     for(int i=0;i<c.length;i++){
         String val=c[i];
         intersects=true;//if a null value is detected, then no intersection occurs.
     }
    }
    catch(NullPointerException nil){
        intersects=false;
    }
    return intersects;
}
/**
 * 
 * @param ellipse The EllipseModel object whose size is to
 * be compared with this one.
 * @return true if the parameter EllipseModel object is bigger than this EllipseModel object
 */
public boolean isBiggerThan(EllipseModel ellipse){
  return this.area()>ellipse.area();
}
/**
 *
 * @param ellipse The EllipseModel object whose size is to
 * be compared with this one.
 * @return true if the parameter EllipseModel object is smaller than this EllipseModel object
 */
public boolean isSmallerThan(EllipseModel ellipse){
  return this.area()<ellipse.area();
}
/**
 * Returns true if their areas deviate by 1.0E-14 or lesser.
 * @param ellipse the EllipseModel object whose size is to be compared with this EllipseModel object.
 * @return true if their sizes are the same or deviate by 1.0E-14 or lesser
 */
public boolean hasAlmostSameSizeAs(EllipseModel ellipse){
  return approxEquals(this.area(), ellipse.area());
}
/**
 * Returns true if their areas are exactly equal.
 * This method can be tricky at times and may produce slight errors if
 * truncation errors have occured or rounding errors.
 * YOU CAN USE METHOD hasAlmostSameSizeAs to reduce this likelihood.
 * That method will still return true for deviations of 1.0E-14 and lesser.
 * @param ellipse the EllipseModel object whose size is to be compared with this EllipseModel object.
 * @return true if their areas are exactly equal.
 */
public boolean hasSameSizeAs(EllipseModel ellipse){
  return this.area()==ellipse.area();
}





/**
 * Compares too numbers to see if they are close enough to be almost the same
 * It checks if the values deviate by 1.0E-14 or lesser.
 * @param val1 the first value to compare
 * @param val2 the second value to compare
 * @return true if the values deviate by 1.0E-14 or lesser.
 */

public boolean approxEquals(double val1,double val2){
 return abs(abs(val1)-abs(val2))<=1.0E-14;
}

/**
 * Compares too numbers to see if they are close enough to be almost the same
 * It checks if the values deviate by 1.0E-14 or lesser.
 * @param val1 the first value to compare
 * @param val2 the second value to compare
 * @param minDeviation the minimum difference they
 * must have to be acceptably equal.
 * @return true if the values deviate by 1.0E-14 or lesser.
 */

public boolean approxEquals(double val1,double val2,double minDeviation){
 return abs(abs(val1)-abs(val2))<=abs(minDeviation);
}



@Override
public String toString(){
    return    "((x-"+this.center.x+")/"+size.width+")²+ ((y-"+this.center.y+")/"+size.height+")²=1";
}//end method
/**
 * Draws this EllipseModel
 * @param g
 */
public void draw(Graphics g){
drawOval(g, (int)this.center.x, (int)this.center.y, (int)size.width, (int)size.height);
}
/**
 * Draws a filled version of this EllipseModel
 * @param g the Graphics object used to draw.
 */
public void fill(Graphics g){
fillOval(g, (int)this.center.x, (int)this.center.y, (int)size.width, (int)size.height);
}


/**
 *
 * @param rect The rectangle
 * @return an array of FloatPoint objects
 * constituting the points of intersection between this
 * EllipseModel object and the rectangle.
 */
public FloatPoint[] intersection(Rectangle rect){
    FloatPoint pt=new FloatPoint(rect.x,rect.y);
    DimensionFloat rectSize=new DimensionFloat(rect.width,rect.height);
  double A=pt.x;double B=pt.y;
double h=getXCenter();double k=getYCenter();
double W=rectSize.getWidth();
double H=rectSize.height;
double a=getWidth();
double b=getHeight();

ArrayList<FloatPoint>pts=new ArrayList<FloatPoint>();
  FloatPoint[] p1=new FloatPoint[]{new FloatPoint(),new FloatPoint()};//intersection of the top line of the rectangle with the ellipse
  FloatPoint[] p2=new FloatPoint[]{new FloatPoint(),new FloatPoint()};//intersection of the bottom line  of the rectangle with the ellipse
  FloatPoint[] p3=new FloatPoint[]{new FloatPoint(),new FloatPoint()};//intersection of the left line  of the rectangle with the ellipse
  FloatPoint[] p4=new FloatPoint[]{new FloatPoint(),new FloatPoint()}; //intersection of the right line  of the rectangle with the ellipse
double val=0;

try{
   val=a*sqrt(1-pow((B-k)/b,2));
    p1[0].setX( h+ val );p1[0].setY(B);
    p1[1].setX(h-val);p1[1].setY(B);
     if(p1[0]!=null&&!new Double(p1[0].x).isNaN()&&!new Double(p1[0].y).isNaN()){
       pts.add(p1[0]);
    }
      if(p1[1]!=null&&!new Double(p1[1].x).isNaN()&&!new Double(p1[1].y).isNaN()){
       pts.add(p1[1]);
    }
}
 catch(ArithmeticException arit){

  }
  catch(NumberFormatException num){

  }
try{
    val=a*sqrt(1-pow((B+H-k)/b,2));
    p2[0].setX( h+val );p2[0].setY(B+H);
    p2[1].setX(h-val);p2[1].setY(B+H);
     if(p2[0]!=null&&!new Double(p2[0].x).isNaN()&&!new Double(p2[0].y).isNaN()){
       pts.add(p2[0]);
    }
      if(p2[1]!=null&&!new Double(p2[1].x).isNaN()&&!new Double(p2[1].y).isNaN()){
       pts.add(p2[1]);
    }
}
 catch(ArithmeticException arit){

  }
  catch(NumberFormatException num){

  }
try{
    val=b*sqrt(1-pow((A-h)/a,2));
    p3[0].setX( A );p3[0].setY(k+val);
    p3[1].setX(A);p3[1].setY(k-val);
     if(p3[0]!=null&&!new Double(p3[0].x).isNaN()&&!new Double(p3[0].y).isNaN()){
       pts.add(p3[0]);
    }
      if(p3[1]!=null&&!new Double(p3[1].x).isNaN()&&!new Double(p3[1].y).isNaN()){
       pts.add(p3[1]);
    }
}
 catch(ArithmeticException arit){

  }
  catch(NumberFormatException num){

  }
try{
    val=b*sqrt(1-pow((A+W-h)/a,2));
    p4[0].setX( A+W );p4[0].setY(k+val);
    p4[1].setX(A+W);p4[1].setY(k-val);
     if(p4[0]!=null&&!new Double(p4[0].x).isNaN()&&!new Double(p4[0].y).isNaN()){
       pts.add(p4[0]);
    }
      if(p4[1]!=null&&!new Double(p4[1].x).isNaN()&&!new Double(p4[1].y).isNaN()){
       pts.add(p4[1]);
    }
}
 catch(ArithmeticException arit){

  }
  catch(NumberFormatException num){

  }



return   pts.toArray(new FloatPoint[]{});
}

/**
 *
 * @param rect The intersecting rectangle
 * @return true if the rectangle intersects with this Ellipse object.
 */
public boolean intersectsWith(Rectangle rect){
    FloatPoint pt=new FloatPoint(rect.x,rect.y );
    DimensionFloat rectSize=new DimensionFloat(rect.width,rect.height);
  double A=pt.x;double B=pt.y;
double h=getXCenter();double k=getYCenter();
double W=rectSize.getWidth();
double H=rectSize.height;
double a=getWidth();
double b=getHeight();


  FloatPoint[] p1=new FloatPoint[]{new FloatPoint(),new FloatPoint()};//intersection of the top line of the rectangle with the ellipse
  FloatPoint[] p2=new FloatPoint[]{new FloatPoint(),new FloatPoint()};//intersection of the bottom line  of the rectangle with the ellipse
  FloatPoint[] p3=new FloatPoint[]{new FloatPoint(),new FloatPoint()};//intersection of the left line  of the rectangle with the ellipse
  FloatPoint[] p4=new FloatPoint[]{new FloatPoint(),new FloatPoint()}; //intersection of the right line  of the rectangle with the ellipse
double val=0;
boolean intersects1=false;
boolean intersects2=false;
boolean intersects3=false;
boolean intersects4=false;

try{
   val=a*sqrt(1-pow((B-k)/b,2));
    p1[0].setX( h+ val );p1[0].setY(B); 
    p1[1].setX(h-val);p1[1].setY(B);
    if(  ( rect.contains(p1[0].toPoint())&&this.contains(p1[0]) )||
         ( rect.contains(p1[1].toPoint())&&this.contains(p1[1]) )        ){
        intersects1=true;
    }
}
 catch(ArithmeticException arit){

  }
  catch(NumberFormatException num){

  }
  catch(NullPointerException num){

  }
try{
    val=a*sqrt(1-pow((B+H-k)/b,2));
    p2[0].setX( h+val );p2[0].setY(B+H);
    p2[1].setX(h-val);p2[1].setY(B+H);
        if(  ( rect.contains(p2[0].toPoint())&&this.contains(p2[0]) )||
         ( rect.contains(p2[1].toPoint())&&this.contains(p2[1]) )        ){
        intersects2=true;
    }
}
 catch(ArithmeticException arit){

  }
  catch(NumberFormatException num){

  }
try{
    val=b*sqrt(1-pow((A-h)/a,2));
    p3[0].setX( A );p3[0].setY(k+val);
    p3[1].setX(A);p3[1].setY(k-val);
            if(  ( rect.contains( p3[0].toPoint())&&this.contains(p3[0]) )||
         ( rect.contains(p3[1].toPoint())&&this.contains(p3[1]) )        ){
        intersects3=true;
    }
}
 catch(ArithmeticException arit){

  }
  catch(NumberFormatException num){

  }
  catch(NullPointerException num){

  }
try{
    val=b*sqrt(1-pow((A+W-h)/a,2));
    p4[0].setX( A+W );p4[0].setY(k+val);
    p4[1].setX(A+W);p4[1].setY(k-val);
            if(  ( rect.contains(p4[0].toPoint())&&this.contains(p4[0]) )||
         ( rect.contains(p4[1].toPoint())&&this.contains(p4[1]) )        ){
        intersects4=true;
    }
}
 catch(ArithmeticException arit){

  }
  catch(NumberFormatException num){

  }


return intersects1||intersects2||intersects3||intersects4;
}



/**
 *
 * @param ellipse The EllipseModel object.
 * @return true if this object intersects with or is contained within the
 * given EllipseModel object and vice versa.
 */
public boolean intersectsWith(EllipseModel ellipse){

Line line = new Line(center, ellipse.center);

//The 2 centers coincide
if(center .equals(ellipse.center )){ 
    return true;
}

//String soln[] = this.lineIntersection(line);

String otherSoln[] = ellipse.lineIntersection(line);
try{
//Point p1 = new Point( Double.valueOf(soln[0]) , Double.valueOf(soln[1]) );
//Point p2 = new Point( Double.valueOf(soln[2]) , Double.valueOf(soln[3]) );

FloatPoint p3 = new FloatPoint( Double.valueOf(otherSoln[0]) , Double.valueOf(otherSoln[1]) );
FloatPoint p4 = new FloatPoint( Double.valueOf(otherSoln[2]) , Double.valueOf(otherSoln[3]) );

if(  (this.contains(p3)||this.contains(p4)) ){
    return true;
}//end if
else{
    return false;
}
}//end try
catch(NumberFormatException num){
    return false;
}//end catch
catch(NullPointerException nullErr){
 return false;
}//end catch
catch(IndexOutOfBoundsException indexErr){
 return false;
}//end catch


}//end method






 









}//end class EllipseModel
