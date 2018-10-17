package sunshine.g7;

import java.util.Queue;
import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collection;
import java.lang.Math;

import sunshine.sim.Point;

class PointUtils {

	public static PriorityQueue<Point> reorderElements(Collection<? extends Point> points, Comparator comp) {
		PriorityQueue<Point> ret = new PriorityQueue<Point>(points.size(), comp);
		ret.addAll(points);
		return ret;
	}

	public static List<Point> pollNElements(PriorityQueue<? extends Point> points, int n) {
		if(points.size() < n) {
			throw new IllegalArgumentException("Too few elements in given queue");
		}

		List<Point> ret = new ArrayList<Point>(n);
		for(int i = 0; i < n; ++i) {
			ret.add(points.poll());
		}

		return ret;
	}

	public static int numTracker(Point farthest, double fieldSize, int numBales){
		double radiusStep = Math.sqrt(fieldSize*fieldSize/numBales*11);
		double farthestRadius = Math.sqrt(farthest.x*farthest.x+farthest.y*farthest.y);
		double nearestRadius = farthestRadius - radiusStep;
		if (nearestRadius<fieldSize && nearestRadius>fieldSize-radiusStep/5){
			nearestRadius=fieldSize;
		}
		double farVolume = getVolume(farthestRadius,fieldSize);
		double nearVolume = getVolume(nearestRadius,fieldSize);
		//System.err.println(farthestRadius+"-"+(farVolume-nearVolume));
		return (int)((farVolume-nearVolume)/(radiusStep*radiusStep));

	}

	public static double getVolume(double radius, double fieldSize){
		if (radius <= fieldSize){
			return 0.25 * Math.PI * radius * radius;
		}
		else{
			double angle = Math.PI/2 - 2*Math.acos(fieldSize/radius);
			double side = Math.sqrt(radius*radius-fieldSize*fieldSize);
			double arcVolume = angle/(Math.PI*2)* Math.PI * radius * radius;
			return side*fieldSize+arcVolume;
		}

	}

	public static Point getClosebyPoint(Point to, Point from) {
		Point direction = new Point(from.x - to.x, from.y - to.y);
		double mag = Math.hypot(direction.x, direction.y);
		Point meter = new Point(direction.x/(mag+0.000000001), direction.y/(mag+0.000000001));
		Point target = new Point(to.x + meter.x, to.y + meter.y);
		if (Math.hypot(to.x-from.x,to.y-from.y) > Math.hypot(target.x-from.x,target.y-from.y)) {
			return target;
		} else {

			return from;
		}
	}
}