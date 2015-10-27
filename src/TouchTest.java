import lejos.hardware.Button;
import lejos.hardware.lcd.*;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;


public class TouchTest {
	public static void main(String[] args) throws Exception{
		EV3TouchSensor touch = new EV3TouchSensor(SensorPort.S1);
		LCD.clear();
		while(!Button.ENTER.isDown()) {
			int sampleSize = touch.sampleSize();
			float[] touchSample = new float[sampleSize];
			touch.fetchSample(touchSample, 0);
			LCD.clear();
			System.out.println(touchSample[0]);
		}
		touch.close();
			
		
	}
}
