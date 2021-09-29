package Math;

import Geometry.Point2D;

public class LineFunction {
	public LineFunction(double m, double b) {
		this.m = m;
		this.b = b;
	}
	public LineFunction(Point2D point1, Point2D point2) {
		if(point1.equals(point2)){
			//System.out.println("DEBUG: creating yFunction with duplicate points. Please fix.");
		}else if(point1.x==point2.x){
			//System.out.println("DEBUG: trying to create vertical yFunction. Must pass horizontal line test, don't do this.");
		}else{
			this.m=((point2.y-point1.y)/(point2.x-point1.x));
			this.b=point1.y-(m*point1.x);
		}
	}
	//y=mx+b
	public Point2D originalReference;
	public double m;//slope
	public double b;//y interp
	public double evalAtX(double x){
		return (m*x)+b;
	}
}