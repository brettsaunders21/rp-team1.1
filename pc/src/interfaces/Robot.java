package interfaces;

import job.Job;
import lejos.geom.Point;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;

public class Robot {
	public static final int MAX_WEIGHT = 50;	//max weight any robot can carry
	private Point currentCoords;
	private float weight;
	private Job activeJob;
	private boolean routeSet;
	private String robotName;
	private boolean jobCancelled;
	private boolean jobFinished;
	private float reward;
	private int jobsCompleted;
	private NXTInfo nxtInfo;
	private Pose currentPose;
	private float totalReward;
	
	public Robot(String _robotName, String _btAddress, Point _startPostion, float _totalReward, Pose startPos){
		this.robotName = _robotName;
		this.nxtInfo = new NXTInfo(NXTCommFactory.BLUETOOTH,_robotName,_btAddress);
		this.routeSet = false;
		this.setWeight(0);
		this.jobCancelled = false;
		this.jobFinished = true;
		this.reward = 0;
		this.jobsCompleted = 0;
		this.currentCoords = _startPostion;
		this.setCurrentPose(startPos);
		this.activeJob = null;
		totalReward = _totalReward;
	}
	
	public void jobFinished() {
		jobFinished = true;
		reward += activeJob.getREWARD();
		jobsCompleted += 1;
		totalReward += activeJob.getREWARD();;
	}
	
	public NXTInfo getNXTInfo() {
		return nxtInfo;
	}
	
	public void cancelJob() {
		jobCancelled = true;
	}
	
	public void resetJobCanceled() {
		jobCancelled = false;
	}
	
	public boolean getJobCancelled() {
		return jobCancelled;
	}


	/*Returns current coordinates of robot*/
	public Point getCurrentPosition(){
		return currentCoords;
	}

	/*Returns all information relating to active job*/
	public Job getActiveJob(){
		return activeJob;
	}

	/*Returns true if the robot has directions set*/
	public boolean isRouteSet(){
		return routeSet;
	}


	/*Sets the current position of the robot*/
	public void setCurrentPosition(Point position){
		currentCoords = position;
		//need to check valid coordinate before assigning, not in wall, actually on map etc
	}


	/*Sets all information relating to the newly assigned job. */
	public void setActiveJob(Job job){
		activeJob = job;
		//sets dropOffCoords, pickUpCoords and determines if more than one trip is necessary. Requests routes to be made
	}


	public String getRobotName() {
		return robotName;
	}


	/**
	 * @return the weight
	 */
	public float getWeight() {
		return weight;
	}

	public void jobNotFinished() {
		jobFinished = false;
	}

	/**
	 * @param f the weight to set
	 */
	public void setWeight(float f) {
		this.weight = f;
		if (this.weight > 50) System.out.println("ERROR with weight " + robotName);
	}

	public boolean isJobFinished() {
		return jobFinished;
	}
	
	public float currentReward() {
		return reward;
	}
	
	public int jobsCompleted() {
		return jobsCompleted;
	}

	public Pose getCurrentPose() {
		return currentPose;
	}

	public void setCurrentPose(Pose currentPose) {
		this.currentPose = currentPose;
	}
 
}
