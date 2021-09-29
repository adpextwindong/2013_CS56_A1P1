package Geometry;

import Geometry.Point2D;

import Math.ClosedInterval;
import Math.LineFunction;
import Math.PointMath;

public class LineSegment extends Intersectable{
	public LineFunction theEvalLine;//created from given points. Bounds are used to check if x is within bounds
	public ClosedInterval bounds;//always check if vertical interval or horizontal
	public boolean vertical=false;
	public Point2D originalPointReferences[]=new Point2D[2];
	double evalLineAngle=0.0;//used for checking chord/tangent cases
	double sideLength;
	public LineSegment(Point2D point1,Point2D point2) {
		originalPointReferences[0]=point1;
		originalPointReferences[1]=point2;
		sideLength=point1.distance(point2);
		if(PointMath.verticalCheck(point1, point2)){//vertical line case
			vertical=true;
			if(point1.y>point2.y){
				evalLineAngle=270;
			}else{
				evalLineAngle=90;
			}
		}else{//not vertical line case
			this.theEvalLine = new LineFunction(point1, point2);
			Double xDiff=Math.abs(point1.x-point2.x);
			Double yDiff=Math.abs(point1.y-point2.y);
			evalLineAngle=Math.toDegrees(Math.atan((yDiff)/xDiff));
			if(Double.compare(theEvalLine.m, 0)!=0){//evalLineAngle setting
				if(point2.x>point1.x){// +x diff
					if(point2.y>point1.y){//first Quadrant
					}else{//fourth Quadrant
						evalLineAngle+=270;
					}
				}else{// -x diff
					if(point2.y>point1.y){//second Quadrant
						evalLineAngle+=90;
					}else{//3rd Quadrant
						evalLineAngle+=180;
					}
				}
			}
		}
		//System.out.println("Point 1 "+point1.x+" "+point1.y+" Point 2 "+point2.x+" "+point2.y+" "+vertical);
		this.bounds = new ClosedInterval(point1, point2,vertical);
	}
}
//		