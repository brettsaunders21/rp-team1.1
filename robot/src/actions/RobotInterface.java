package actions;

import lejos.nxt.Button;

//import job.Job;
//import interfaces.Robot;
//import lejos.nxt.Button;
//import lejos.util.Delay;
//import lejos.nxt.ButtonListener;
//import lejos.nxt.LCD;

public class RobotInterface {

	private int itemQuantity;
	//private Robot robotInfo;
	//private Job jobInfo;

	public RobotInterface() {
		this.itemQuantity = 0;

	}
/**
	public void sensorCalibrationMessage() {
		LCD.clear();
		LCD.drawString(robotInfo.getRobotName(), 1, 0);
		System.out.println("Press a button to calibrate the sensors.");
		Button.waitForAnyPress();

	}

	public void networkMessage(String message) {
		LCD.clear();
		LCD.drawString(robotInfo.getRobotName(), 1, 0);
		System.out.println(message);

	}

	public void waitingForOrdersMessage() {
		LCD.clear();
		System.out.println(robotInfo.getRobotName() + "is waiting for orders.");

	}

	public void movingMessage() {
		if (robotInfo.getActiveJob().getDropLocation() != null) {
			movingToDestinationMessage();
		} else {
			movingToItemMessage();
		}
	}

	public void movingToDestinationMessage() {
		LCD.clear();
		System.out.println(robotInfo.getRobotName() + " is moving to the drop point.");
		System.out.println("Job ID: " + jobInfo.getID());
		System.out.println("Destination Coordinates: " + jobInfo.getDropLocation());

	}

	public void movingToItemMessage() {
		LCD.clear();
		System.out.println(robotInfo.getRobotName() + " is moving to the collection point.");
		System.out.println("Job ID: " + jobInfo.getID());
		System.out.println("Destination Coordinates: "); // need to add coordinates for item pickup
	}

	public void destinationMessage() {
		if (robotInfo.getActiveJob().getDropLocation() != null) {
			unloadItemsMessage();
		} else {
			loadItemsMessage();
		}

	}
**/
	public void waitForLoadingMessage(int amount){
		System.out.println("Pick up: " + amount);
		while (itemQuantity != amount) {
	if (Button.waitForAnyPress() == Button.ID_LEFT){
		pickItems(itemQuantity);
		if (Button.waitForAnyPress() == Button.ID_RIGHT){
			dropItems(itemQuantity);
		}
		
		}
		}
	}

	public void waitForunLoadingMessage(int amount){
		System.out.println("Drop off: " + itemQuantity);
		while (itemQuantity != 0) {
	if (Button.waitForAnyPress() == Button.ID_LEFT){
		pickItems(itemQuantity);
		if (Button.waitForAnyPress() == Button.ID_RIGHT){
			dropItems(itemQuantity);
		}
		
		}
		}
	}
/**
	public void loadItemsMessage() {
		LCD.clear();
		//LCD.drawString(robotInfo.getRobotName(), 1, 0);
		//System.out.println("Job ID: " + jobInfo.getID());
		System.out.println("Press a button to load the items.");
		Button.waitForAnyPress();
	}

	public void unloadItemsMessage() {
		LCD.clear();
		//LCD.drawString(robotInfo.getRobotName(), 1, 0);
		//System.out.println("Job ID: " + jobInfo.getID());
		System.out.println("Press a button to unload the items.");
		Button.waitForAnyPress();
	}
**/
	public void pickItems(int noOfItems) {
		itemQuantity++;
		System.out.println("Now: " + itemQuantity);
	}

	public void dropItems(int noOfItems) {
		itemQuantity--;
		if (itemQuantity < 0) {
			itemQuantity = 0;
		}
		System.out.println("Now: " + itemQuantity);
	}

	public void resetQuantity() {
		itemQuantity = 0;

	}

	public int getQuantity() {
		return itemQuantity;
	}

}
