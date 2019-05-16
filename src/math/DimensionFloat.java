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
 

import java.awt.Dimension;
import java.io.Serializable;

/**
 *
 * @author GBEMIRO
 */
public class DimensionFloat implements Serializable{
 
public double width;
public double height;
/**
 * Creates a new object of this class.
 * @param width Stores the width.
 * @param height Stores the height. 
 */
    public DimensionFloat(double width, double height) {
        this.width = width;
        this.height = height;
    }//end constructor


/**
 * Creates a new Dimension object from
 * a given Dimension object.
 * The created object is exactly similar in value to the
 * given one, but is not the same as that one.It is a sort of twin.
 * @param size The given Dimension object.
 */
public DimensionFloat(DimensionFloat size){
    this.width=size.width;
    this.height=size.height;
}

    public DimensionFloat() {
    }
    /**
     * 
     * @return A Dimension object from this one.
     */
public Dimension toDimension(){
    return new Dimension((int) width,(int) height);
}
/**
 *
 * @return The height
 */
    public double getHeight() {
        return height;
    }//end method
/**
 *
 * @param height sets the height
 */
    public void setHeight(int height) {
        this.height = height;
    }//end method
/**
 *
 * @return the width
 */
    public double getWidth() {
        return width;
    }//end method
/**
 *
 * @param width sets the height
 */
    public void setWidth(int width) {
        this.width = width;
    }//end method
    /**
     *
     * @param width sets the width attribute of this object
     * @param height sets the height attribute of this object
     */
public void setSize(double width,double height){
    this.width=width;
    this.height=height;
}//end method

/**
 *
 * @param size sets the size of object of this class
 * using an already known one.
 *
 */
public void setSize(DimensionFloat size){
    this.width=size.width;
    this.height=size.height;
}//end method


/**
 *
 * @return a new instance of Dimension containing
 * information about the size of this object
 */
public DimensionFloat getSize(){
    return new DimensionFloat(width,height);
}//end method

/**
 *
 * @param scaleFactor the factor by which this Dimension object is to be scaled.
 */
public void scale(double scaleFactor){
    setSize(scaleFactor*width, scaleFactor*height);
}

/**
 *returns a new Dimension object scaled to the value given by scaleFactor
 * @param scaleFactor the factor by which this Dimension object is to be scaled.
 */
public DimensionFloat getScaledInstance(double scaleFactor){
    return new DimensionFloat(scaleFactor*width, scaleFactor*height);
}
/**
 *returns a new Dimension object scaled to the value given by scaleWidth
 * and scaleHeight, both being multipliers for the width and the
 * height.
 * @param scaleWidth  the factor by which the width of this Dimension object is to be scaled.
 * @param scaleHeight    the factor by which the height of this Dimension object is to be scaled.
 */
public DimensionFloat getScaledInstance(double scaleWidth,double scaleHeight){
    return new DimensionFloat(scaleWidth*width, scaleHeight*height);
} 


    @Override
    public String toString() {
     return "( width = "+width+  ",height = "+height+" ).";

    }










   
}
