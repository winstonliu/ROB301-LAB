import lejos.hardware.motor.Motor;

public class lab2a {
	private static int theta = 0;
	private static int phi = 180;
	private static int clawangle = 0;
	private static int speed = 180;
	public static void main(String[] args) throws Exception{
		pickup(125,0);
		pickup(50,135);
		pickup(0,180); 
		
	}
	private static void movePhi(int pos){
		
		int move = pos - phi;
		int posC = (int)(move*1.05);

		if (Math.abs(posC) < 2){
			return;
		}
		Motor.C.setSpeed(speed);
		Motor.C.rotate(3*posC);
		phi = pos;
	}
	private static void moveTheta(int pos){
		int move = pos - theta;
		if (Math.abs(move) < 2){
			return;
		}
		Motor.B.setSpeed(speed);
		Motor.B.rotate(-5*move);
		theta = pos;
	}
	private static void pickup(int startDeg,int dropDeg){
		move(startDeg,15);
		move(startDeg,-15);
		moveClaw(90);
		move(startDeg,15);
		move(dropDeg,15);
		move(dropDeg,-15);
		moveClaw(-10);
		move(dropDeg,15);
		//move(180,15);
	}
	private static void move(int phi, int theta){
		moveTheta(theta);
		movePhi(phi);
	}
	private static void moveClaw(int deg){
		int move = deg - clawangle;
		if (Math.abs(move) < 2){
			return;
		}
		Motor.A.setSpeed(speed);
		Motor.A.rotate(move);
		clawangle = deg;
	}
}
