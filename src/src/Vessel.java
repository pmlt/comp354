
public class Vessel {
	private String vesselId;
	private int type;
	private double xPos;
	private double yPos;
	private double xVel;
	private double yVel;
//	private double speed;
//	private double iteration;
	
	Vessel(String id, int t, double cx, double cy, double vx, double vy){
		vesselId 	= id;
		type 		= t;
		xPos		= cx;
		yPos		= cy;
		xVel		= vx;
		yVel		= vy;
//		speed		= Math.sqrt(Math.pow(xVel, 2) + Math.pow(yVel, 2));
	}
	void setVelocity(double vx, double vy) {
		xVel		= vx;
		yVel		= vy;
	}
	
	void setXVelocity(double vx) {		xVel		= vx;	}
	void setYVelocity(double vy) {		yVel		= vy;	}

//	void setNextPosition() { iteration++;	}
	void setNextPosition(double cx, double cy) {
		xPos = cx;
		yPos = cy;
	}
	
	final String getVesselId() 		{	return vesselId;	}
	final int getType()				{	return type;		}
//	double getXPosition() 	{	return xPos + xVel * iteration;		}
//	double getYPosition() 	{	return yPos + yVel * iteration;		}
	final double getXPosition() 	{	return xPos;		}
	final double getYPosition() 	{	return yPos;		}
	final double getXVelocity()		{	return xVel;		}
	final double getYVelocity()		{	return yVel;		}
//	final double getSpeed()			{	return speed;		}
	
	
}
