package Geometry;

import Geometry.Point2D;
import java.util.ArrayList;

import Math.PointMath;

public class Polygon extends Shape{
	public int n;//The number of sides
	public ArrayList<Point2D> vertices=new ArrayList<Point2D>();
	public ArrayList<LineSegment> LineSegments=new ArrayList<LineSegment>();
	
	public Polygon(int n,ArrayList<Point2D> vertices){
		super(PointMath.findCheapCentre(vertices));
		this.n=n;
		this.vertices=vertices;
		LineSegments=PointMath.makeLineSegmentList(vertices);
	}
}
