package Math;

import Geometry.Point2D;

public class ClosedInterval {
	double leftEndpoint;
	double rightEndpoint;
	boolean yPlaneBounds=false;//typicaly you'll be using an interval on the xPlane however vertical lines require yPlane for intersection checks
	public ClosedInterval(Point2D point1, Point2D point2, boolean vertical) {
		this.yPlaneBounds=vertical;
		if(yPlaneBounds){//vertical
			if(point1.y>point2.y){
				Point2D temp=point1;
				point1=point2;
				point2=temp;
			}
			if(point1.y<point2.y){
				this.leftEndpoint = point1.y;
				this.rightEndpoint = point2.y;
			}
		}else{
			if(point1.x>point2.x){
				Point2D temp=point1;
				point1=point2;
				point2=temp;
			}
			this.leftEndpoint = point1.x;
			this.rightEndpoint = point2.x;

		}
	}
//	public boolean checkBounds(double x){
//		return (x>leftEndpoint||x==leftEndpoint) ? (x<rightEndpoint||x==rightEndpoint ? true: false): false;
//	}
	public double getMidPointOfInterval(){
		return rightEndpoint-leftEndpoint;	
	}
	//TEST CASES
	//[0,5]
	//[-1,0,2,5,6]
	//false, true,true,true, false
	public boolean containsThisX(double x) {
		//System.out.println((x>=leftEndpoint) ? (x<=rightEndpoint) ? true : false : false);
		//System.out.println(x>leftEndpoint);
		return (x>=leftEndpoint) ? (x<=rightEndpoint) ? true : false : false;
	}
}
