package Math;

import Geometry.Circle;
import Geometry.Point2D;
import Geometry.Polygon;
import Geometry.Ray;
import Geometry.LineSegment;

import java.util.ArrayList;


public class PointMath {
	public static Point2D findCheapCentre(ArrayList<Point2D> Points){//ESTIMATES CENTROID WITH ARITHMETIC MEAN, VERY CHEAP AND EASY.
		if(Points.isEmpty()){//why would you give me an empty list, jesus christ
			return null;
		}
		Double averageX=0.0,averageY=0.0;//This all might look like tons of bullshit but I don't want duplicate cords
		ArrayList<Double> Xs=new ArrayList<Double>();
		ArrayList<Double> Ys=new ArrayList<Double>();
		for(int i=0;i<Points.size();i++){
			if(!Xs.contains(Points.get(i).x)){
				Xs.add(Points.get(i).x);
			}
			if(!Ys.contains(Points.get(i).y)){
				Ys.add(Points.get(i).y);
			}
		}
		for(int i=0;i<Xs.size();i++){
			averageX+=Xs.get(i);
		}
		for(int i=0;i<Ys.size();i++){
			averageY+=Ys.get(i);
		}
		averageX=averageX/Xs.size();
		averageY=averageY/Ys.size();
		return new Point2D(averageX,averageY);
	}

	public static ArrayList<LineSegment> makeLineSegmentList(ArrayList<Point2D> Points) {
		if(Points.isEmpty()){//why would you give me an empty list, jesus christ
			return null;
		}
		ArrayList<LineSegment> SegmentList = new ArrayList<LineSegment>();
		for(int i=0;i<Points.size();i++){
			if(i==Points.size()-1){//last point case
				SegmentList.add(new LineSegment(Points.get(i),Points.get(0)));
			}else{
				SegmentList.add(new LineSegment(Points.get(i),Points.get(i+1)));
			}
		}

		return SegmentList;
	}

	public static boolean verticalCheck(Point2D point1, Point2D point2) {//checks if two points are on a yplane number line together
		return point1.equals(point2)?false : ((point1.x.compareTo(point2.x)==0)?true : false);//see verticalCheck in testMains to see test cases
	}
	public static Point2D findMiddlePointOfCircleOverlap(Circle circle1, Circle circle2) {//check Notebook
		LineSegment theMidLine = new LineSegment(circle1.centre,circle2.centre);
		//		Point2D theReturn;
		//		if(theMidLine.vertical){// Find the middle of a vertical Line Segment
		//			theReturn=new Point2D(circle1.centre.x,theMidLine.bounds.getMidPointOfInterval());
		//		}else{//finds the middle point of a line between two circles centers
		//			double midPoint=theMidLine.bounds.getMidPointOfInterval();
		//			theReturn=new Point2D(midPoint,theMidLine.theEvalLine.evalAtX(midPoint));
		//		}
		//		return theReturn;
		return theMidLine.vertical?new Point2D(circle1.centre.x,theMidLine.bounds.getMidPointOfInterval()) : new Point2D(theMidLine.bounds.getMidPointOfInterval(),theMidLine.theEvalLine.evalAtX(theMidLine.bounds.getMidPointOfInterval()));
		//ternaries make me cool right?
		//wow so terse
	}

	public static boolean rayCast(Point2D vert, Polygon thePolygon) {
		Ray theRay=new Ray(vert);//because I'm too lazy to make a real ray
		//System.out.println(theRay.xSignPositive+" "+theRay.ySignPositive);
		int intersectCount=0;
		for(int i=0;i<thePolygon.n;i++){
			if(thePolygon.LineSegments.get(i).vertical){//vertical case
				if(theRay.isVertical){//I hope you never get a natural 7.2057594e+16 roll breh because I'm never going to test this 1/2^56 case
					if(thePolygon.LineSegments.get(i).originalPointReferences[0].x.compareTo(theRay.originPoint.x)==0){// if they land on the same x cord
						if(theRay.checkSignEqualityOfAnotherPoint(thePolygon.LineSegments.get(i).originalPointReferences[0])||theRay.checkSignEqualityOfAnotherPoint(thePolygon.LineSegments.get(i).originalPointReferences[1])){
							intersectCount++;// if either original points of the line segment are on the right side of the ray
						}
					}
				}else{
					double verticalLineSegmentX=thePolygon.LineSegments.get(i).originalPointReferences[0].x;
					Point2D pointOfIntersection=new Point2D(verticalLineSegmentX,theRay.evalAtX(verticalLineSegmentX));
					if(theRay.checkSignEqualityOfAnotherPoint(pointOfIntersection)){//if on the ray, not behind
						if(thePolygon.LineSegments.get(i).bounds.containsThisX(pointOfIntersection.y)){
							intersectCount++;
						}
					}
				}
			}else{//nonVertical Line Segment Case
				if(theRay.isVertical){
					Point2D intersectionPoint=new Point2D(theRay.originPoint.x,thePolygon.LineSegments.get(i).theEvalLine.evalAtX(theRay.originPoint.x));
					//System.out.println(intersectionPoint.x+" "+intersectionPoint.y);
					if(theRay.checkSignEqualityOfAnotherPoint(intersectionPoint)){
						intersectCount++;
					}
				}else{
					if(theRay.slope!=thePolygon.LineSegments.get(i).theEvalLine.m){//non Parralel case
						double intersectionX=(thePolygon.LineSegments.get(i).theEvalLine.b-theRay.yInterp)/( theRay.slope-thePolygon.LineSegments.get(i).theEvalLine.m);
						//System.out.println(i+" "+intersectionX);
						Point2D intersectionPoint=new Point2D(intersectionX,theRay.evalAtX(intersectionX)); //(b2-b1)/(m1x-m2x)=x of intersection
						//System.out.println(intersectionPoint.x+" "+intersectionPoint.y);
						if(thePolygon.LineSegments.get(i).bounds.containsThisX(intersectionPoint.x)){
							if(theRay.checkSignEqualityOfAnotherPoint(intersectionPoint)){
								intersectCount++;
							}
						}
					}else{//parralel case
						if(thePolygon.LineSegments.get(i).theEvalLine.b==theRay.yInterp){// if the lines are equal and has one of the two points on the right side of the ray
							if(theRay.checkSignEqualityOfAnotherPoint(thePolygon.LineSegments.get(i).originalPointReferences[0])||theRay.checkSignEqualityOfAnotherPoint(thePolygon.LineSegments.get(i).originalPointReferences[1])){
								intersectCount++;
							}
						}
					}
				}
			}
		}
		//System.out.println(intersectCount);
		//System.out.println("HALT");
		return (intersectCount%2!=0) ? true : false;//returns true if ray intersects odd times, aka point inside the polygon
	}

	public static Point2D intersectSegments(LineSegment firstSegment,LineSegment secondSegment) {
		Point2D theReturn=null;
		if(!firstSegment.vertical){//no verticals
			if(firstSegment.theEvalLine.m!=secondSegment.theEvalLine.m){
				double interesectionX = (firstSegment.theEvalLine.b-secondSegment.theEvalLine.b)/(secondSegment.theEvalLine.m-firstSegment.theEvalLine.m);
				if(firstSegment.bounds.containsThisX(interesectionX) && secondSegment.bounds.containsThisX(interesectionX)){
					theReturn=new Point2D(interesectionX,firstSegment.theEvalLine.evalAtX(interesectionX));
				}
			}
		}else{
			if(!secondSegment.vertical){//single vertical
				if(firstSegment.bounds.containsThisX(secondSegment.theEvalLine.evalAtX(firstSegment.originalPointReferences[0].x))){
					theReturn=new Point2D(firstSegment.originalPointReferences[0].x,secondSegment.theEvalLine.evalAtX(firstSegment.originalPointReferences[0].x));
				}
			}else{//double vertical
				if(firstSegment.originalPointReferences[0].x.compareTo(secondSegment.originalPointReferences[0].x)==0){
					if(firstSegment.bounds.containsThisX(secondSegment.originalPointReferences[0].y)){
						theReturn=new Point2D(firstSegment.originalPointReferences[0].x,secondSegment.originalPointReferences[0].y);
					}else if(firstSegment.bounds.containsThisX(secondSegment.originalPointReferences[1].y)){
						theReturn=new Point2D(firstSegment.originalPointReferences[0].x,secondSegment.originalPointReferences[1].y);
					}else if(secondSegment.bounds.containsThisX(firstSegment.originalPointReferences[0].y)){
						theReturn=new Point2D(firstSegment.originalPointReferences[0].x,firstSegment.originalPointReferences[0].y);
					}else if(secondSegment.bounds.containsThisX(firstSegment.originalPointReferences[1].y)){
						theReturn=new Point2D(firstSegment.originalPointReferences[0].x,firstSegment.originalPointReferences[1].y);
					}
				}
			}
		}
		return theReturn;
	}
}
