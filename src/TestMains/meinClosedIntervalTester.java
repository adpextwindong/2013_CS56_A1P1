package TestMains;
import Geometry.Point2D;

import Math.ClosedInterval;


public class meinClosedIntervalTester {
	public static void main(String[] args) {
		//int testNums[]={-1,0,2,5,6};
		float testNums[]={-1f,0f,2f,5f,6f};
		ClosedInterval theInterval= new ClosedInterval(new Point2D(0.0,0),new Point2D(5.0,0), false);
		for(int i=0;i<5;i++){
			System.out.println(theInterval.containsThisX(testNums[i]));
		}//theInterval.checkBounds(testNums[i])+" "+
	}

}
