package job;

import java.util.ArrayList;

import interfaces.Pose;
import interfaces.Robot;
import lejos.geom.Point;
import routeplanning.AStar;
import routeplanning.Map;
import routeplanning.Route;

public class TSP {
	private AStar routeMaker = new AStar(Map.generateRealWarehouseMap());
	private ArrayList<Point> drops;
			
	public TSP(ArrayList<Point> _drops) {
		drops = _drops;
	}

	public ArrayList<Item> orderItems(ArrayList<Item> items, Robot robot) {
		Pose prePose = robot.getCurrentPose();
		ArrayList<Item> orderedItems = new ArrayList<>();
		Item closestItem = new Item(null, 0, 0, robot.getCurrentPosition(), 0);
		while (!items.isEmpty()) {
			closestItem = nearestItemToPoint(closestItem.getPOSITION(), items);
			items.remove(closestItem);
			orderedItems.add(closestItem);
		}
		robot.setCurrentPose(prePose);
		return orderedItems;
	}
	
	private ArrayList<Item> addDropPoints(ArrayList<Item> items) {
		int weightRunningTotal = 0;
		ArrayList<Item> withDrops = new ArrayList<Item>();
		for (Item item : items) {
			weightRunningTotal += item.getTOTAL_WEIGHT();
			if (weightRunningTotal >= 50) {
				Item dropPoint = new Item("droppoint", 0, 0, nearestDropPoint(item.getPOSITION(), Pose.POS_X), 0);
				withDrops.add(dropPoint);
				weightRunningTotal = 0;
			}
			withDrops.add(item);
		}
		return withDrops;
	}
	
	@SuppressWarnings("unused")
	private int calculateJobDistance(Job job) {
		ArrayList<Item> tempItems = new ArrayList<Item>(job.getITEMS());
		Robot tempR = new Robot("temp", "000", new Point(0,0));
		tempItems = orderItems(tempItems, tempR);
		tempItems = addDropPoints(tempItems);
		int distance = 0;
		for (int i = 1; i <= tempItems.size(); i++) {
			distance += routeMaker.generateRoute(tempItems.get(i-1).getPOSITION(), tempItems.get(i).getPOSITION(), Pose.POS_X, new Route[] {}, 0).getLength();
		}
		return distance;
	}
	
	private Item nearestItemToPoint(Point point,  ArrayList<Item> items) {
		Item nearestItemSoFar = items.get(0);
		int smallestDistance = Integer.MAX_VALUE;
		for (Item item : items) {
			int distance = routeMaker.generateRoute(point, item.getPOSITION(), Pose.POS_X,  new Route[] {}, 0).getLength();
			if (distance < smallestDistance) {
				nearestItemSoFar = item;
				smallestDistance = distance;
			}
		}
		return nearestItemSoFar;
	}
	
	public Point nearestDropPoint(Point currentLocation, Pose pose) {
		Point closestPoint = drops.get(0);
		int closestPointDistance = Integer.MAX_VALUE;
		for (Point point : drops) {
			int routeToDrop1 = routeMaker.generateRoute(currentLocation, point, pose, new Route[] {}, 0).getLength();
			if (routeToDrop1 < closestPointDistance) {
				closestPointDistance = routeToDrop1;
				closestPoint = point;
			}
		}
		return closestPoint;
	}
}
