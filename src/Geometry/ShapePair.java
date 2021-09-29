package Geometry;

import java.util.ArrayList;

import Math.PointMath;

public class ShapePair {
	public Shape firstShape;
	public Shape secondShape;
	public ArrayList<ArrayList<Intersection>> ListOfIntersections = new ArrayList<ArrayList<Intersection>>();//Reusability for collision engine and storing old intersections
	shapePairEnum pairType;
	public ShapePair(Shape firstShape,Shape secondShape){
		this.firstShape=firstShape;
		this.secondShape=secondShape;
		checkEnum();//checks what type of shapes (circle and polygon), so I don't have to check what sub class of shape is at the if statement
		updateForNewIntersections();//adds new list of intersections, extendibility into a game.
	}
	public ArrayList<Intersection> getLatestIntersections(){
		return ListOfIntersections.get(ListOfIntersections.size()-1);
	}

	private void checkEnum() {//checks the type of Shapes
		if(firstShape.getClass().getName().equals(Circle.class.getName())||secondShape.getClass().getName().equals(Circle.class.getName())){//check if one is a circle
			if(firstShape.getClass().getName().equals(Circle.class.getName())&&secondShape.getClass().getName().equals(Circle.class.getName())){//circleCircle case
				pairType=shapePairEnum.CircleCircle;
			}else{//circle Polygon case
				pairType=shapePairEnum.CirclePolygon;
			}
		}else{//polygon polygon case
			pairType=shapePairEnum.PolygonPolygon;
		}
	}

	private void updateForNewIntersections() {//used to find and update intersections
		//ArrayList<Intersection> theIntersections=new ArrayList<Intersection>();
		switch(pairType){
		case CircleCircle:
			ListOfIntersections.add(CircleCircleCase());
			break;
		case CirclePolygon:
			ListOfIntersections.add(CirclePolygonCase());
			break;
		case PolygonPolygon:
			ListOfIntersections.add(PolygonPolygonCase());
			break;
		}
		//ListOfIntersections.add(theIntersections);
	}

	private ArrayList<Intersection> PolygonPolygonCase() {
		Polygon thePolygons[]={(Polygon) firstShape,(Polygon) secondShape};
		ArrayList<Intersection> Intersections=new ArrayList<Intersection>();
		for(int i=0;i<2;i++){//vert vert equal checking and vert on line segment checking
			for(int j=0;j<thePolygons[i].n;j++){//1-i because I have 2 elements and don't want two identical for loops
				for(int k=0;k<thePolygons[1-i].n;k++){//1-i gets other other element
					if(thePolygons[i].vertices.get(j).equals(thePolygons[1-i].vertices.get(k))){
						Intersections.add(new Intersection(thePolygons[i],thePolygons[1-i], thePolygons[i].vertices.get(j), new Intersectable[]{thePolygons[i].vertices.get(j),thePolygons[1-i].vertices.get(k)}, "Equal Vertice Case"));
					}
					if(thePolygons[i].LineSegments.get(j).theEvalLine.evalAtX(thePolygons[1-i].vertices.get(k).x)==thePolygons[1-i].vertices.get(k).y){
						Intersections.add(new Intersection(thePolygons[i], thePolygons[1-i],thePolygons[1-i].vertices.get(k), new Intersectable[]{thePolygons[1-i].vertices.get(k),thePolygons[i].LineSegments.get(j)}, "Vertice on Line Segment Case"));
					}
				}
			}
		}
		for(int i=0;i<2;i++){//checking if Vert inside other polygon
			for(int j=0;j<thePolygons[i].n;j++){
				if(PointMath.rayCast(thePolygons[i].vertices.get(j), thePolygons[1-i])){
					Intersections.add(new Intersection(thePolygons[i], thePolygons[1-i], thePolygons[i].vertices.get(j),new Intersectable[]{thePolygons[i].vertices.get(j),thePolygons[1-i]}, "Polygon Vert Inside Other Polygon Case"));
				}
			}
		}
		for(int i=0;i<thePolygons[0].n;i++){//Line Segment intersection checking
			for(int j=0;j<thePolygons[1].n;j++){
				Point2D intersectPoint = PointMath.intersectSegments(thePolygons[0].LineSegments.get(i),thePolygons[1].LineSegments.get(j));
				if(intersectPoint!=null){
					Intersections.add(new Intersection(thePolygons[0], thePolygons[1], intersectPoint, new Intersectable[]{thePolygons[0].LineSegments.get(i),thePolygons[1].LineSegments.get(j)}, "Line Segment Intersect Case"));
				}
			}
		}
		System.out.println("DEBUG: PolygonPolygonCase");
		return Intersections;
	}

	private ArrayList<Intersection> CirclePolygonCase() {
		ArrayList<Intersection> Intersections=new ArrayList<Intersection>();
		System.out.println("DEBUG: CirclePolygonCase");
		Circle theCircle= (Circle) (firstShape.getClass().getName().equals(Circle.class.getName()) ? firstShape : secondShape);
		Polygon thePolygon=	(Polygon) (firstShape.getClass().getName().equals(Polygon.class.getName()) ? firstShape : secondShape); 
		//YO CHECK MUH TERNARIES, casts whichever shape the circle is and assigns to theCircle
		//same with the next line.
		int vertCountInsideCircle=0;
		for(int i=0;i<thePolygon.n;i++){
			double vertDistToCenter=thePolygon.vertices.get(i).distance(theCircle.centre);
			if(vertDistToCenter<=theCircle.radius){
				if(vertDistToCenter==theCircle.radius){//vert touching circle
					Intersections.add(new Intersection(theCircle, thePolygon, thePolygon.vertices.get(i),new Intersectable[]{theCircle,thePolygon.vertices.get(i)}, "Vertice Touching Circle Case"));
					vertCountInsideCircle++;
				}else{//vert inside circle
					Intersections.add(new Intersection(theCircle, thePolygon, thePolygon.vertices.get(i),new Intersectable[]{theCircle,thePolygon.vertices.get(i)}, "Vertice Inside Circle Case"));
					vertCountInsideCircle++;
				}
			}
			//TANGENT/CHORD CHECKING SECTION
			LineSegment firstVertToCircleCenterSegment=new LineSegment(thePolygon.LineSegments.get(i).originalPointReferences[0],theCircle.centre);
			double firstVertToCircleCentertheta=firstVertToCircleCenterSegment.evalLineAngle;
			Point2D angDiffPoint=new Point2D (theCircle.centre.x+theCircle.radius*Math.cos(firstVertToCircleCentertheta-90),theCircle.centre.y+theCircle.radius*Math.sin(firstVertToCircleCentertheta-90));
			double minChordLengthFromFirstVertDist=thePolygon.LineSegments.get(i).originalPointReferences[0].distance(angDiffPoint);//used to check if poly side is long enough to be angle checked
			if(thePolygon.LineSegments.get(i).sideLength>=minChordLengthFromFirstVertDist){//if line segment is long enough to chord at its angle
				LineSegment maxAngLine=new LineSegment(thePolygon.LineSegments.get(i).originalPointReferences[0],angDiffPoint);
				double maxAngDiff=Math.abs(maxAngLine.evalLineAngle-firstVertToCircleCentertheta);
				double vertToAngDiffPointAngDiff=Math.abs(firstVertToCircleCentertheta-thePolygon.LineSegments.get(i).evalLineAngle);
				if(vertToAngDiffPointAngDiff<=maxAngDiff){//chord case based on angle
					if(vertToAngDiffPointAngDiff==maxAngDiff){//tangent case
						Intersections.add(new Intersection(thePolygon, theCircle, thePolygon.LineSegments.get(i).originalPointReferences[1],new Intersectable[]{thePolygon.LineSegments.get(i),theCircle}, "Tangent Case"));
					}else{//chord case
						Intersections.add(new Intersection(thePolygon, theCircle, thePolygon.LineSegments.get(i).originalPointReferences[1],new Intersectable[]{thePolygon.LineSegments.get(i),theCircle}, "Chord Case"));
					}
				}
			}
		}
		if(PointMath.rayCast(theCircle.centre,thePolygon)){//check if circle center is inside polygon. Tangent/Chord cases will be found by this point
			Intersections.add(new Intersection(theCircle,thePolygon,theCircle.centre, new Intersectable[]{theCircle.centre,thePolygon},"Circle Inside Case"));
		}else{
			if(vertCountInsideCircle==thePolygon.n){
				// FIGURE OUT A BETTER WAY TO DO THE 4 LINES BELLOW. MUH TERSENESS
				Intersectable[] vertArray=new Intersectable[thePolygon.n];
				vertArray=thePolygon.vertices.toArray(vertArray);
				Intersectable theIntersectables[]=new Intersectable[]{theCircle.centre,(Intersectable) thePolygon.vertices.toArray()[thePolygon.n]};
				Intersections.add(new Intersection(theCircle,thePolygon,thePolygon.centre,theIntersectables,"Polygon Inside Circle Case"));
			}
		}
		return Intersections;
	}

	private ArrayList<Intersection> CircleCircleCase() {
		ArrayList<Intersection> Intersections=new ArrayList<Intersection>();
		Circle theCircles[]=new Circle[]{(Circle) firstShape,(Circle) secondShape};
		if(theCircles[0].equals(theCircles[1])){//Same Circle Case
			Intersections.add(new Intersection(firstShape,secondShape,firstShape.centre,new Intersectable[]{firstShape.centre,secondShape.centre}, "Same Circle Case"));
		}else if(theCircles[0].centre.distance(theCircles[1].centre).compareTo(theCircles[0].radius+theCircles[1].radius)<=0){//Touching case / Overlapping case
			Point2D contactPoint=PointMath.findMiddlePointOfCircleOverlap(theCircles[0],theCircles[1]);
			if(theCircles[0].centre.distance(theCircles[1].centre).compareTo(theCircles[0].radius+theCircles[1].radius)==0){//"Outside Touching Case"
				Intersections.add(new Intersection(firstShape, secondShape, contactPoint, new Point2D[]{contactPoint}, "Outside Touching Case"));
			}else if((theCircles[0].radius==theCircles[1].radius)&&(theCircles[0].radius==theCircles[0].centre.distance(theCircles[1].centre))){//Equal Radius Overlap on centers
				Intersections.add(new Intersection(firstShape, secondShape, contactPoint, new Point2D[]{contactPoint}, "Equal Radius Overlap on centers"));
			}else{//"Overlapping Case", includes inside each other
				Intersections.add(new Intersection(firstShape, secondShape, contactPoint, new Point2D[]{contactPoint}, "Overlapping Case"));
			}
		}
		//	Cases
		//		Outside
		//		Overlapping 
		//		Touching
		//		Equal Radius Overlap on centers
		//		Same Circle
		//		Null intersection, does not get recorded
		return Intersections;
	}

}