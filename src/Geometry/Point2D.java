package Geometry;

public class Point2D extends Intersectable{
	public Point2D(double X, double Y) {
		this.x=X;
		this.y=Y;
	}
	public Double x;
	public Double y;
	public boolean equals(Point2D otherPoint){//checked for correctness, not a nested ternary thanks to James' great idea of using logical AND.
		//Java double primitives require .compareTo, which returns int. wtfff
//		System.out.println(x + " " + otherPoint.x + " " + (this.x.compareTo(otherPoint.x)));
//		System.out.println(y+" "+otherPoint.y+" "+(this.y.compareTo(otherPoint.y)));
		//System.out.println((((this.x.compareTo(otherPoint.x))==0)&&((this.y.compareTo(otherPoint.y)==0))));
		return (((this.x.compareTo(otherPoint.x))==0)&&((this.y.compareTo(otherPoint.y)==0)));
	}
	public Double distance(Point2D otherPoint){
		return Math.sqrt(Math.pow(this.x-otherPoint.x, 2)+Math.pow(this.y-otherPoint.y, 2));
	}
	//a clone of Java awt geom Point2D so I can extend Intersectable for "Intersectable elements" of shapes
	//such as Vertices and Line Segments without having to clone Point2D
}
