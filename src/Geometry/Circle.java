package Geometry;

import Geometry.Point2D;

public class Circle extends Shape{
	double radius=0;
	public Circle(double tempRadius,double tempX,double tempY){
		super(new Point2D(tempX,tempY));//sets Centre
		radius=tempRadius;
	}
	public boolean equals(Circle otherCircle){
		boolean theReturn=false;
		if(this.radius==otherCircle.radius){
			if(this.centre.equals(otherCircle.centre)){
				theReturn=true;
			}
		}
		return theReturn;
	}
}
