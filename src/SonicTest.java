import lejos.hardware.Button;
import lejos.hardware.lcd.*;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;

public class SonicTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EV3UltrasonicSensor sonic = new EV3UltrasonicSensor(SensorPort.S1);
		LCD.clear();
		while(!Button.ENTER.isDown()) {
			int sampleSize = ((SampleProvider) sonic).sampleSize();
			float[] sonicSample = new float[sampleSize];
			((SampleProvider) sonic).fetchSample(sonicSample, 0);
			LCD.clear();
			System.out.println(sonicSample[0]*100);
		}
		sonic.close();
	}

}
