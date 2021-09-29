package MainPackage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import Geometry.Circle;
import Geometry.Polygon;
import Geometry.Shape;
import Geometry.Point2D;
import Geometry.ShapePair;



public class rayCastingMain {
	//Excluding Java threads and sockets this project will probably contain everything I know.
	//FIELD VARIABLS MUST BE CHECKED IN THE DEBUG VIEW.
	private static BufferedReader BR= new BufferedReader(new InputStreamReader(System.in));
	private static int NumberOfShapes=2;
	private static Shape userShapes[]= new Shape[NumberOfShapes];
	public static void main(String[] args) {
		//	Fun with Geometry write a program that intersects polygons, with raycasting.
		String userInput="";
		
		for(int i=0;i<NumberOfShapes;i++){
			System.out.println("Input two shapes.");
			System.out.println("Avalible shapes: \"Circle\" \"Polygon\" ");
			System.out.println("Input shape number "+(i+1));
			try {
				userInput=BR.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(userInput.equalsIgnoreCase("circle")){
				userShapes[i]=makeCircle();
			}else if(userInput.equalsIgnoreCase("Polygon")){
				userShapes[i]=makePolygon();
			}else{
				i--;
				System.out.println("Unavalible Shape Error");
			}
			System.out.println("Shape Number "+(i+1)+" input completed...");
		}
		System.out.println("Lets check for intersections.");
		ArrayList<ShapePair> thePairs = new ArrayList<ShapePair>();
		for(int i=0;i<NumberOfShapes-1;i++){
			thePairs.add(new ShapePair(userShapes[0],userShapes[1]));
		}
		System.out.println("The intersections:");
		for(int i=0;i<thePairs.size();i++){
			for(int j=0;j<thePairs.get(i).getLatestIntersections().size();j++){
				System.out.println(thePairs.get(i).getLatestIntersections().get(j).intersectionType);
			}
		}
	}

	private static Polygon makePolygon() {
		int n=0;
		ArrayList<Point2D> vertices=new ArrayList<Point2D>();
		listFill:
			for(;;){
				inputN:
					for(;;){
						System.out.println("Input Number of Sides: ");
						try {
							n=Integer.parseInt(BR.readLine());
							break inputN;
						} catch (NumberFormatException e) {
							e.printStackTrace();
							System.out.println("NUMBER FORMAT ERROR");
							continue;
						} catch (IOException e) {
							e.printStackTrace();
						}
					}

		if(n<3){
			System.out.println("Proper Euclidian Polgyons only please...");
//		}else if(n!=3&&n!=4){
//			System.out.println("Unsupported at the moment.");
		}else{
			for(int j=0;j<n;j++){
				System.out.println("Input Point "+(j+1));
				double tempX = 0,tempY = 0;
				for(;;){
					try {
						System.out.println("Point "+(j+1)+" X: ");
						tempX=Double.parseDouble(BR.readLine());
						System.out.println("Point "+(j+1)+" Y: ");
						tempY=Double.parseDouble(BR.readLine());
						break;
					} catch (NumberFormatException e) {
						System.out.println("NUMBER FORMAT ERROR");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				vertices.add(new Point2D(tempX, tempY));
			}
			break listFill;
		}
			}
		return new Polygon(n, vertices);
	}

	private static Circle makeCircle() {
		double tempRadius = 0,tempX = 0,tempY = 0;
		for(;;){
			try {
				System.out.println("Radius: ");
				tempRadius=Double.parseDouble(BR.readLine());
				System.out.println("Centre X: ");
				tempX=Double.parseDouble(BR.readLine());
				System.out.println("Centre Y: ");
				tempY=Double.parseDouble(BR.readLine());
				break;
			} catch (NumberFormatException e) {
				System.out.println("NUMBER FORMAT ERROR");
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new Circle(tempRadius, tempX, tempY);
	}

}
