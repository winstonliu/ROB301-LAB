import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import lejos.hardware.lcd.*;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.motor.Motor;


public class Lab4 {
	public static double L = 11.75;
	public static double D = 5.5;
	static EV3UltrasonicSensor sonic = new EV3UltrasonicSensor(SensorPort.S1);
	public static int prevLangle=0;
	public static int prevRangle=0;
	public static int BASESPEED = 100;
	public static int MAX = 120;
	public static int MIN = 80;
	public static float P = 30;
	public static double currangle = 0;
	
	public static float x=0;
	public static float y=0;
	
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
		//turn(90);
		setXY(200,20,5);
		//setXY(100,100,180);
		//setXY(0,100,-90);
		//setXY(0,0,0);
		/*getDistanceTravelled();
		initWallFollow(100.0,20.0);
		initWallFollow(100.0,30.0);
		initWallFollow(100.0,20.0);*/
		turn2(0);
	}
	public static void setXY(float xf, float yf,float thetaf){
		
		double turnangle = 180/Math.PI *Math.atan2(yf-y,xf-x);
		turn(turnangle-currangle);
		double r = Math.pow(Math.pow(yf-y,2) + Math.pow(xf-x, 2),0.5);
		System.out.println(Double.toString(r));
		moveForward(r);
		y=yf;
		x=xf;
		turn(thetaf-currangle);
	}
	public static void turn(double degrees){
		currangle += degrees;
		double r = L * degrees/D;
		System.out.println(Double.toString(r));
		int angle = (int)(r);
		Motor.A.setSpeed(100);
		Motor.B.setSpeed(100);
		Motor.A.rotate(-angle,true);
		Motor.B.rotate(angle,true);
		while(Motor.A.isMoving()){
			Thread.yield();
		}
	}
	public static void turn2(double degrees){
		currangle += degrees;
		double r = L * degrees/D;
		System.out.println(Double.toString(r));
		int angle = 5*360;
		Motor.A.setSpeed(100);
		Motor.B.setSpeed(147);
		Motor.A.rotate(angle,true);
		Motor.B.rotate((int) (1.47*angle),true);
		while(Motor.A.isMoving()){
			Thread.yield();
		}
	}
	public static void moveForward(double meters){
		Motor.A.setSpeed(360);
		Motor.B.setSpeed(360);
		double rotation = 360*meters/(Math.PI*D);
		Motor.A.rotate((int)rotation,true);
		Motor.B.rotate((int)rotation,true);
		while(Motor.A.isMoving()){
			Thread.yield();
		}
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
			 error *=P;
			 if (error>20){
				 error = 20;
			 }else if(error < -20){
				 error = -20;
			 }
			 Motor.A.forward();
			 Motor.B.forward();
			 Motor.A.setSpeed(BASESPEED-(int)(error));
			 Motor.B.setSpeed(BASESPEED+(int)(error));
			 remDist -= getDistanceTravelled();
		}
	}
	public static double getDistanceTravelled(){
		int Langle = Motor.A.getTachoCount();
		int Rangle = Motor.B.getTachoCount();
		int l =Langle - prevLangle;
		int r = Rangle - prevRangle;

		int avg = (r + l) /2;
		double dist = (avg * D/2)*Math.PI/180.0;
		prevLangle = Langle;
		prevRangle = Rangle;
		System.out.println(Double.toString(dist));
		return dist;
	}
}