package Geometry;
import Math.PointMath;
public class Ray {
	public Ray(Point2D originPoint) {
		this.originPoint = originPoint;
		int xSign=randomSign();
		int ySign=randomSign();
		xSignPositive= xSign>0 ? true : false;
		ySignPositive= ySign>0 ? true : false;
		randomRayDirectionPoint= new Point2D(xSign*Math.random()+originPoint.x,ySign*Math.random()+originPoint.y);
		Point2D randomPoint=new Point2D(randomRayDirectionPoint.x,randomRayDirectionPoint.y);
		if(PointMath.verticalCheck(originPoint, randomPoint)){
			isVertical=true;
		}else{
			slope=((randomPoint.y-originPoint.y)/(randomPoint.x-originPoint.x));
			yInterp=originPoint.y-(slope*originPoint.x);
		}

	}
	public double slope;
	public double yInterp;
	public Point2D originPoint;
	public Point2D randomRayDirectionPoint;
	public boolean xSignPositive;//false if ray angle is between 90  and 180, aka Ray goes into 2nd/3rd quadrant
	public boolean ySignPositive;
	public boolean isVertical=false;

	private int randomSign() {//returns a random sign + or -
		return Math.random()>=0.5? 1: -1;
	}
	public double evalAtX(double x){
		return (slope*x)+yInterp;
	}
	public boolean checkSignEqualityOfAnotherPoint(Point2D pointOfIntersection) {//checks if on the right side of the ray evalline
		boolean xSignCorrect=false;
		boolean ySignCorrect=false;
		if(isVertical){
			xSignCorrect=true;
			if(ySignPositive){
				if(pointOfIntersection.y-originPoint.y>=0){
					ySignCorrect=true;
				}
			}else{
				if(pointOfIntersection.y-originPoint.y<=0){
					ySignCorrect=true;
				}
			}
		}else{
			if(xSignPositive){
				if(pointOfIntersection.x-originPoint.x>=0){
					xSignCorrect=true;
				}
			}else{
				if(pointOfIntersection.x-originPoint.x<=0){
					xSignCorrect=true;
				}
			}
			if(ySignPositive){
				if(pointOfIntersection.y-originPoint.y>=0){
					ySignCorrect=true;
				}
			}else{
				if(pointOfIntersection.y-originPoint.y<=0){
					ySignCorrect=true;
				}
			}
		}
		
		return (xSignCorrect && ySignCorrect)? true : false;
	}
}
// [feeling INTENSIFIES]
//	m1x-m2x=b2-b1
//	b1-b2= (m2-m1)