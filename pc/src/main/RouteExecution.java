package main;

import java.io.IOException;
import java.util.Queue;
import communication.CommunicationData;
import communication.PCNetworkHandler;
import interfaces.Robot;
import job.Job;

public class RouteExecution extends Thread {

	private Robot robot;
	private Job currentJob;
	private interfaces.Action currentCommand;
	//private ArrayList<Item> ITEMS;
	private Queue<interfaces.Action> currentDirections;
	private PCNetworkHandler network;

	public RouteExecution(Robot _robot, PCNetworkHandler _network) {
		this.robot = _robot;
		this.network = _network;
	}

	public void run() {
		currentJob = robot.getActiveJob();
		//ITEMS = currentJob.getITEMS();
		currentDirections = currentJob.getCurrentroute().getDirections();

		try {
			while (!currentDirections.isEmpty()) { // the method isEmpty() needs to be created in Route
				currentCommand = currentDirections.poll();
				network.sendObject(currentCommand);

				if (!network.receiveObject(CommunicationData.STRING).equals("ACTION COMPLETE")) {
					//Error
					robot.cancelJob();
					break;
				}
				if (currentJob.isCanceled()) {
					robot.cancelJob();
				}
				if (currentCommand == interfaces.Action.PICKUP) {
					robot.setWeight(robot.getWeight() + 0);
				} else if (currentCommand.equals(interfaces.Action.DROPOFF)) {
					robot.setWeight(robot.getWeight() - 0);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		robot.jobFinished();
	}
}