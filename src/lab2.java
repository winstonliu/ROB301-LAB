import lejos.hardware.motor.Motor;

public class lab2 {
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		
		//Motor.C.setSpeed(180);
		//Motor.C.rotateTo(180, true);  //positive means rotating clockwise
		//gear ratio for C
		//small 12 -> big 36

		/*
		Motor.B.setSpeed(180);
		//Motor.A.rotate(720);
		Motor.B.rotate(720, true);   //positive means decreasing theta
		//gear ratio for B\
		//small 8 -> 40
		
	
		
		//gear ratio for A: 1
		Motor.A.setSpeed(10);
		Motor.A.rotate(180);   //positive
		*/
		int angleA = Motor.A.getTachoCount();
		int angleB = Motor.B.getTachoCount();
		int angleC = Motor.C.getTachoCount();
		int i = 0;
		while (i<1000) {
			System.out.println((Integer.toString(angleA)));
			System.out.println((Integer.toString(angleB)));
			System.out.println((Integer.toString(angleC)));
			i += 1;
		}
	}
}
