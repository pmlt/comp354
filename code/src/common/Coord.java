package common;

public class Coord implements Cloneable {
	private int x;
	private int y;
	
	public Coord(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public int x() {
		return x;
	}
	public int y() {
		return y;
	}
	@Override
	public boolean equals(Object o) {
	    if (o == null) return false;
	    if (!(o instanceof Coord))return false;
	    Coord c = (Coord)o;
		return c.x == x && c.y == y;
	}
	@Override
	public Object clone() {
		return new Coord(x, y);
	}
	
	public boolean isInRange(Object lowerLimit, Object upperLimit){
		if (lowerLimit == null || upperLimit == null) return false;
	    if (!(lowerLimit instanceof Coord) || !(upperLimit instanceof Coord))return false;
	    
	    Coord lLimit = (Coord)lowerLimit;
	    Coord uLimit = (Coord) upperLimit;
	    
	    return x > lLimit.x && x < uLimit.x && y > lLimit.y && y < uLimit.y;
	}
}
