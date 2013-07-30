package common;

public class Course implements Cloneable {
	private int velocityX;
	private int velocityY;
	
	public Course(int xVel, int yVel) {
		velocityX = xVel;
		velocityY = yVel;
	}
	public int xVel() {
		return velocityX;
	}
	public int yVel() {
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
