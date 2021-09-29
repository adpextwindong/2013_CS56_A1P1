package TestMains;

import Geometry.Point2D;
import Math.PointMath;

public class VerticalCheckTester {
	public static void main(String[] args) {
		//test cases
		//{two equal points, vertical points,horizontal normal point}
		//false true false
		Point2D points[]=new Point2D[2];
		points[0]=new Point2D(0.0,0.0);
		points[1]=new Point2D(0.0,0.0);
		System.out.println(PointMath.verticalCheck(points[0],points[1]));
		
		points[0]=new Point2D(0.0,0.0);
		points[1]=new Point2D(0.0,5.0);
		System.out.println(PointMath.verticalCheck(points[0],points[1]));
		
		points[0]=new Point2D(2.0,5.0);
		points[1]=new Point2D(1.0,7.0);
		System.out.println(PointMath.verticalCheck(points[0],points[1]));
		System.out.println("HALT DEBUG");
	}

}
