
public class Vessel implements Comparable<Vessel> {
	private String vesselId;
	private int type;
	private double xPos;
	private double yPos;
	private double xVel;
	private double yVel;
	private double startTime;
	
	Vessel(String id, int t, double cx, double cy, double vx, double vy, double st) {
		vesselId 	= id;
		type 		= t;
		xPos		= cx;
		yPos		= cy;
		xVel		= vx;
		yVel		= vy;
		startTime	= st;
	}
	// Allows vessels to be processed in PriorityQueue
	public int compareTo(Vessel otherVessel) {
		if (startTime < otherVessel.startTime)
			return -1;
		else if (startTime > otherVessel.startTime)
			return 1;
		else
			return 0;
	}
	
	void setVelocity(double vx, double vy) {
		xVel		= vx;
		yVel		= vy;
	}
	void setXVelocity(double vx)	{	xVel = vx;	}
	void setYVelocity(double vy)	{	yVel = vy;	}

	void setNextPosition(double cx, double cy) {
		xPos = cx;
		yPos = cy;
	}
	
	final String getVesselId() 		{	return vesselId;	}
	final int getType()				{	return type;		}
	final double getXPosition() 	{	return xPos;		}
	final double getYPosition() 	{	return yPos;		}
	final double getXVelocity()		{	return xVel;		}
	final double getYVelocity()		{	return yVel;		}
	final double getStartTime()		{	return startTime;	}
	
}
