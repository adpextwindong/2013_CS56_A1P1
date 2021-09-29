package TestMains;
import Geometry.Point2D;

import Math.LineFunction;


public class meinLineFunction {
	public static void main(String[] args) {
		Point2D point1=new Point2D(0,1);
		Point2D point2=new Point2D(0,1);
		LineFunction theLine = new LineFunction(point1,point2);
		System.out.println(theLine);
		System.out.println("HALT");
//		theLine = new yFunctionLine(point2,point1);
//		System.out.println(theLine);
//		System.out.println("HALT");
	}

}
