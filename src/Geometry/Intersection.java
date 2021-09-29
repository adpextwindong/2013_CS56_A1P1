package Geometry;

public class Intersection {
	Point2D pointOfIntersection;
	Intersectable elements[]=new Intersectable[2];
	public String intersectionType;
	Shape firstShapeReference, secondShapeReference;
	public Intersection(Shape firstShapeReference, Shape secondShapeReference, Point2D pointOfIntersection, Intersectable[] elements,String intersectionType) {
		this.firstShapeReference=firstShapeReference;
		this.secondShapeReference=secondShapeReference;
		this.pointOfIntersection = pointOfIntersection;
		this.elements = elements;
		this.intersectionType=intersectionType;
		//System.out.println(intersectionType);
	}
}
