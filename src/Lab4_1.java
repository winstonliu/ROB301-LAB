import org.jfree.chart.renderer.category.StatisticalBarRenderer;

import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import lejos.hardware.lcd.*;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.motor.Motor;


public class Lab4_1 {
	public static double L = 11.75;
	public static double D = 5.5;
	static EV3UltrasonicSensor sonic = new EV3UltrasonicSensor(SensorPort.S1);

	public static int prevLangle=0;
	public static int prevRangle=0;
	public static int BASESPEED = 50;
	public static float currentAngle = 0;
	public static float P = (float) 0.1;
	
	public static void main(String[] args) throws Exception{
		
		Button.ESCAPE.addKeyListener(new KeyListener() {
			   
			@Override
			public void keyPressed(Key k) {
				// TODO Auto-generated method stub
				System.exit(0);
			}

			@Override
			public void keyReleased(Key k) {
				// TODO Auto-generated method stub
				
			}
			});
		
		getDistanceTravelled();
		initWallFollow(100.0,20.0);
		initWallFollow(100.0,30.0);
		initWallFollow(100.0,20.0);
	}
	public static void turn(double degrees){
		double r = L * degrees/(2*180.0*D);
		int angle = (int)(r*360);
		Motor.A.rotate(angle,true);
		Motor.B.rotate(-angle,true);
		
	}
	public static void moveForward(double meters){
		double rotation = 360*meters/(Math.PI*D);
		Motor.A.rotate((int)rotation,true);
		Motor.B.rotate((int)rotation,true);
	}
	public static float getWallDistance(){
		int sampleSize = sonic.sampleSize();
		float[] sonicsample = new float[sampleSize]; 
		sonic.fetchSample(sonicsample, 0);
		LCD.clear();
		//System.out.println(sonicsample[0]*100);
		return sonicsample[0]*100;
	}
    public static void initWallFollow(double dist, double targetWallDist){
		double remDist = dist;
		while (remDist > 0){
			 double error = getWallDistance() - targetWallDist;
			 Motor.A.forward();
			 Motor.B.forward();
			 Motor.A.setSpeed(BASESPEED-(int)(error*P*BASESPEED));
			 Motor.B.setSpeed(BASESPEED+(int)(error*P*BASESPEED));
			 remDist -= getDistanceTravelled();

			 float delta_angle = (float) Math.atan((prevLangle-prevRangle)/L);
			 currentAngle += delta_angle;
			 System.out.println(String.format("%.4f",currentAngle));
			 
			 if (Math.abs(currentAngle) > Math.PI/4) {
				 turn(currentAngle * 180/Math.PI);
				 currentAngle = 0;
			 }
		}
	}
	public static double getDistanceTravelled(){
		int Langle = Motor.A.getTachoCount();
		int Rangle = Motor.B.getTachoCount();
		int l = Langle - prevLangle;
		int r = Rangle - prevRangle;
		

		int avg = (r + l) /2;
		double dist = (avg * D/2)*Math.PI/180.0;
		prevLangle = Langle;
		prevRangle = Rangle;
		//System.out.println(String.format("%.4f",dist));
		return dist;
	}
}