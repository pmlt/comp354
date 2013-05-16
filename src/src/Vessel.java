
public class Vessel {
	private int vesselId;
	private String type;
	private double xPos;
	private double yPos;
	private double xVel;
	private double yVel;
	private double speed;
	
	Vessel(int id, String t, double cx, double cy, double vx, double vy){
		vesselId 	= id;
		type 		= t;
		xPos		= cx;
		yPos		= cy;
		xVel		= vx;
		yVel		= vy;
		speed		= Math.sqrt(Math.pow(xVel, 2) + Math.pow(yVel, 2));
	}
	
	void setVelocity(double vx, double vy) {
		xVel		= vx;
		yVel		= vy;
	}
	
	void setXVelocity(double vx) {		xVel		= vx;	}
	void setYVelocity(double vy) {		yVel		= vy;	}

	void setNextPosition() {
		xPos += xVel;
		yPos += yVel;
	}
	
	int getVesselId() 		{	return vesselId;	}
	String getType()		{	return type;		}
	double getXPosition() 	{	return xPos;		}
	double getYPosition() 	{	return yPos;		}
	double getXVelocity()	{	return xVel;		}
	double getYVelocity()	{	return yVel;		}
	double getSpeed()		{	return speed;		}
	
	
}
