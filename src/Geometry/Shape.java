package Geometry;

import Geometry.Point2D;

public abstract class Shape extends Intersectable{
	public Point2D centre;
	public Shape(Point2D centre) {
		this.centre=centre;
	}
}