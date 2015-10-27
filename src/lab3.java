

import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import lejos.hardware.lcd.*;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;

public class lab3 {
	
	private static float p = 500;
	private static float i = 100;
	private static float d = 100;
	
	private static float lastError = 0;
	private static float totalError = 0;
	
	private static float minReading = 0;
	private static float maxReading = 1;
	private static float target = 0.5f;

	private static int maxSpeed = 900;
	private static int baseSpeed = 500;
	private static EV3ColorSensor lightSensor;
	
	public static void main(String[] args) throws Exception{
		
		// Bind the escape button to escaping the program
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
		
		lightSensor = new EV3ColorSensor(SensorPort.S1);
		//lightSensor.setFloodlight(Color.RED);
		calibrate();
		loop();
	}
	private static void calibrate(){
		LCD.clear();
		System.out.println("Place sensor on white");
		Button.ENTER.waitForPressAndRelease();
		int sampleSize = lightSensor.sampleSize();
		float[] sample = new float[sampleSize];
		lightSensor.getRedMode().fetchSample(sample,0);
		maxReading = sample[0];
		System.out.println("Got value: " + Float.toString(sample[0]));
		//lightSensor.setFloodlight(Color.RED);
		System.out.println("Place sensor on black");
		Button.ENTER.waitForPressAndRelease();

		sample = new float[sampleSize];
		lightSensor.getRedMode().fetchSample(sample,0);
		System.out.println("Got value: " + Float.toString(sample[0]));
		minReading = sample[0];
		target = (maxReading + minReading) /2.0f;
		//lightSensor.setFloodlight(Color.RED);
		System.out.println("Ready to start");
		Button.ENTER.waitForPressAndRelease();
	}
	
	private static void loop(){
		
		while(true){
			int sampleSize = lightSensor.sampleSize();
			float[] sample = new float[sampleSize];
			lightSensor.getRedMode().fetchSample(sample,0);
			float error = sample[0]- target;
			System.out.println("total error: " + Float.toString(totalError));
			totalError += error;
			totalError *= 0.95;
			float correction = p*error + i*totalError - d*(error-lastError);
			/*
			if (error>0){
				Motor.A.setSpeed(baseSpeed);
				Motor.B.setSpeed(0);
				Motor.A.forward();
				Motor.B.stop();
			}else{
				Motor.B.setSpeed(baseSpeed);
				Motor.A.setSpeed(0);
				Motor.A.stop();
				Motor.B.forward();
			}
			*/
			Motor.A.forward();
			Motor.B.forward();
			Motor.A.setSpeed(baseSpeed + (int)correction);
			Motor.B.setSpeed(baseSpeed - (int)correction);
			
			
			lastError = error;
		}
	}
}