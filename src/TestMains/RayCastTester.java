package TestMains;

import java.util.ArrayList;

import Geometry.Point2D;
import Geometry.Polygon;
import Math.PointMath;

public class RayCastTester {
	public static void main(String[] args) {
		Point2D Vert=new Point2D(1,1);
		ArrayList<Point2D> vertArrayList= new ArrayList<Point2D>();
		vertArrayList.add(new Point2D(0,0));
		vertArrayList.add(new Point2D(0,5));
		//System.out.println(vertArrayList.get(0).equals(vertArrayList.get(1)));
		//System.out.println(PointMath.verticalCheck(vertArrayList.get(0), vertArrayList.get(1)));
		vertArrayList.add(new Point2D(5,0));
		Polygon thePolygon=new Polygon(3,vertArrayList);
		//System.out.println("DEBUG STOP");
		for(int i=1;;i++){
			System.out.println(i+" "+PointMath.rayCast(Vert, thePolygon));
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
