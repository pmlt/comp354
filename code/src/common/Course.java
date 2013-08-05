package common;

public class Course implements Cloneable {
	private double velocityX;
	private double velocityY;
	
	public Course(double xVel, double yVel) {
		velocityX = xVel;
		velocityY = yVel;
	}
	public double xVel() {
		return velocityX;
	}
	public double yVel() {
		return velocityY;
	}
	@Override
	public boolean equals(Object o) {
	    if (o == null) return false;
	    if (!(o instanceof Course))return false;
	    Course c = (Course)o;
		return c.velocityX == velocityX && c.velocityY == velocityY;
	}
	@Override
	public Object clone() {
		return new Course(velocityX, velocityY);
	}
}
