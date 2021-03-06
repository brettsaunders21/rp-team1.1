package main;

import java.util.ArrayList;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import interfaces.Pose;
import interfaces.Robot;
import job.Job;
import job.JobAssignment;
import job.JobInput;
import job.JobList;
import job.JobSelection;
import lejos.geom.Point;
import routeplanning.AStar;
import warehouse_interface.JobsInterface;
import warehouse_interface.WarehouseInterface;

public class PCController {
	private static final Logger controllerLogger = Logger.getLogger(PCController.class);
	private static final Logger aStarLogger = Logger.getLogger(AStar.class);
	private static final Logger routeExeLogger = Logger.getLogger(RouteExecution.class);
	private static final Logger jobAssLogger = Logger.getLogger(JobAssignment.class);
	private static float totalReward;
	private static final Robot[] ROBOTS = { 
			new Robot("Spike", "0016530AA681", new Point(0, 0), totalReward, Pose.POS_X),
			new Robot("Jeremy", "00165308E37C", new Point(0, 7), totalReward, Pose.POS_X),
			new Robot("Marco", "001653115A7E", new Point(11, 7), totalReward, Pose.NEG_X) 
			};
	private static ArrayList<Job> completedJobs = new ArrayList<Job>();
	private static int numOfRobots = ROBOTS.length;
	private static RobotThread[] r = new RobotThread[numOfRobots];

	public PCController() {
		// sets the levels for the loggers in different classes
		controllerLogger.setLevel(Level.ALL);
		aStarLogger.setLevel(Level.FATAL);
		controllerLogger.setLevel(Level.ALL);
		aStarLogger.setLevel(Level.OFF);
		routeExeLogger.setLevel(Level.ALL);
		jobAssLogger.setLevel(Level.ALL);
		totalReward = 0;
	}

	public static void main(String[] args) {
		// creates new JobInput, JobSelection, JobList, PointHeld and JobAssignment
		JobInput jI = new JobInput();
		JobSelection jS = new JobSelection(jI.getBetaValues());
		JobList jobList = new JobList(jS);
		Counter counter = new Counter(ROBOTS);
		PointsHeld heldPoints = new PointsHeld();
		JobAssignment jA = new JobAssignment(jobList, counter, jI.getDrops(), ROBOTS, jS);

		// creates new interfaces for warehouse and jobs
		new WarehouseInterface(ROBOTS);
		new JobsInterface(ROBOTS, completedJobs, jobList, totalReward);

		// Start thread to manage and control each robot
		for (int i = 0; i < numOfRobots; i++) {
			r[i] = new RobotThread(ROBOTS[i], jA, counter, heldPoints, completedJobs, ROBOTS, jobList);
			r[i].setName(ROBOTS[i].getRobotName());
			r[i].start();
			controllerLogger.debug("Started robot thread: " + ROBOTS[i].getRobotName());
		}
	}

}
