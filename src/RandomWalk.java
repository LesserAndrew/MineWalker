import java.util.Random;
import java.util.ArrayList;
import java.awt.Point;
public class RandomWalk implements RandomWalkInterface {
	private int size;
	private boolean done;
	private int x;
	private int y;
	private Point start = new Point();
	private Point end = new Point();
	private Point p = new Point();
	Random rand = new Random();
	String path;
	ArrayList<Point> points = new ArrayList<Point>();
	public RandomWalk(int gridSize)
	{
		this.x = gridSize-1;
		this.y = 0;
		this.done = false;
		this.size = gridSize;
		this.start.setLocation(this.x, this.y);
		this.p.setLocation(this.start);
		this.end.setLocation(this.y, this.x);
		this.points.add(this.p);
		this.path = null;
	}
	public RandomWalk(int gridSize, int seed)
	{
		rand.setSeed(seed);
		this.x = gridSize-1;
		this.y = 0;
		this.done = false;
		this.size = gridSize;
		this.start.setLocation(this.x, this.y);
		this.p.setLocation(this.start);
		this.end.setLocation(this.y, this.x);
		this.points.add(this.p);
		this.path = null;
	}
	double num = rand.nextDouble();
	@Override
	public void step() {
		if(rand.nextBoolean()) {
			this.y++;
		}
		else {
			this.x--;
		}
		p.move(this.x, this.y);
		Point point = new Point(p.x, p.y);
		this.points.add(point);
		if(this.x == 0 || this.y == this.size-1) {
			this.done = true;
		}
	}
	@Override
	public void stepEC() {
		//TODO Extra Credit not finished
	}
	@Override
	public void createWalk() {
		while(this.done == false) {
			this.step();
		}
	}
	@Override
	public void createWalkEC() {
		//TODO Extra Credit not finished
		
	}
	@Override
	public boolean isDone() {
		return this.done;
	}
	@Override
	public int getGridSize() {
		return this.size;
	}
	@Override
	public Point getStartPoint() {
		return this.start;
	}
	@Override
	public Point getEndPoint() {
		return this.end;
	}
	@Override
	public Point getCurrentPoint() {
		return this.p;
	}
	@Override
	public ArrayList<Point> getPath() {
		return this.points;
	}
	public String toString() {
		this.path = " ";
		for(Point d : points) {
			this.path += "[" + d.getX() + ", " + d.getY() + "] ";
		}
		return this.path;
	}
}