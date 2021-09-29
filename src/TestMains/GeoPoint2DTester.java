package TestMains;

import Geometry.Point2D;

//import java.awt.geom.Point2D;

public class meinGeoPoint2DTester {
	public static void main(String[] args) {
		Point2D points[]={new Point2D(0,0),new Point2D(0,0)}; //Geometry.Point2D;
		//Point2D.Double points[]={new Point2D.Double(0,0),new Point2D.Double(4,3)};//java.awt.geom.Point2D;
		System.out.println(points[0].equals(points[1]));
	}

}
