import java.util.*;
import javax.swing.*;

//-----Wana-----
public class Piece {
	public Point currentPoint;
	public Point newPoint;
	String imageName;

	public static void main(String[] args) {
		Triangle triangle1 = new Triangle(new Point(4, 3));
		System.out.print("Current tile at (" + triangle1.getPoint() + ")");
		Point p = new Point(0, 0);
		Scanner in = new Scanner(System.in);
		do {
			System.out.println("\nEnter new Tile coordinate to move (x,y)[-1 to end]: ");
			p.setX(in.nextInt());
			p.setY(in.nextInt());
			System.out.println("Moveable: " + triangle1.moveable(p));
			triangle1.betTile(p);
			Point tilesin = triangle1.betTilePoint(p);
			System.out.println("between tile:" + tilesin);
			System.out.println("PRESS 0 TO CONTINUE, -1 TO END");

		} while (in.nextInt() != -1);

	}

	public boolean moveable(Point currentPoint) {
		System.out.println("No piece is selected");
		return false;
	}

	Piece() {

	}

	public Point getPoint() {
		System.out.println("[Piece] getPoint() x : " + currentPoint.getX() + " y: " + currentPoint.getY());
		return currentPoint;
	}

	public void setCurrentPoint(Point currentPoint) {
		this.currentPoint = currentPoint;
	}

	public boolean betTile(Point newPoint) {
		return false;
	}

	public Point betTilePoint(Point newPoint) {
		return newPoint;
	}

}

// -----Wana-----
class Triangle extends Piece {
	Point tilesinbetween;

	Triangle() {
	}

	Triangle(Point currentPoint) {
		this.currentPoint = currentPoint;
		setGuiIcon();
	}

	public void setGuiIcon() {
		this.imageName = "triangle_red.png";
	}

	@Override
	public boolean moveable(Point newPoint) { // validating the current Point and Destination Point
		boolean ret = false;
		if (newPoint.getY() > 7 || newPoint.getX() > 6) {
			System.out.println("invalid tile location (x < 7 & y < 6)");
			ret = false;
		} else {
			// The movement formula
			int nx = newPoint.getX(), ny = newPoint.getY(), cx = currentPoint.getX(), cy = currentPoint.getY();
			if (nx == cx + 2 || nx == cx - 2) {
				if (ny == cy + 2 || ny == cy + -2) {
					ret = true;
				} else {
					ret = false;
				}
			} else if (nx == cx + 1 || nx == cx - 1) {
				if (ny == cy + 1 || ny == cy + -1) {
					ret = true;
				} else {
					ret = false;
				}
			}

		}

		return ret;
	}

	@Override
	public boolean betTile(Point newPoint) { // if there is any space in between the currentTile(start) to
												// newPoint(destination)
		boolean ret = false;
		int diffx = currentPoint.getX() - newPoint.getX();
		int diffy = currentPoint.getY() - newPoint.getY();
		int x = newPoint.getX();
		int y = newPoint.getY();
		if (diffx == 2 || diffx == -2 || diffy == 2 || diffy == -2) {
			ret = true;
		}

		else {
			ret = false;
		}
		return ret;
	}

	@Override
	public Point betTilePoint(Point newPoint) {// the point of tile in between the currentTile(start) to
												// newPoint(destination)
		int difx = currentPoint.getX() - newPoint.getX();
		int dify = currentPoint.getY() - newPoint.getY();
		int x = newPoint.getX();
		int y = newPoint.getY();
		int a = 0, b = 0;
		if (betTile(newPoint) == true) {
			if (difx == 2) {
				a = x + 1;
				if (dify == 2) {
					b = y + 1;
				} else {
					b = y - 1;
				}
			} else if (difx == -2) { // Diffx == -2
				a = x - 1;
				if (dify == 2) {
					b = y + 1;
				} else {
					b = y - 1;
				}
			}
		} else {
			System.out.println("There is no tile in between");
			a = 0;
			b = 0;
		}
		tilesinbetween = new Point(a, b);
		return tilesinbetween;
	}

}

// -----Wana-----
class Chevron extends Piece {
	Chevron() {
	}

	// Chevron does not have betTile() betTilePoint() because it can move even if
	// there is a piece on its way
	Chevron(Point currentPoint) {
		this.currentPoint = currentPoint;
	}

	public boolean moveable(Point newPoint) {
		boolean ret = false;
		if (newPoint.getY() > 7 || newPoint.getX() > 6) {
			System.out.println("invalid tile location (x < 7 & y < 6)");
			ret = false;
		} else {
			int nx = newPoint.getX(), ny = newPoint.getY(), cx = currentPoint.getX(), cy = currentPoint.getY();
			if (nx == cx - 2 || nx == cx + 2) {
				if (ny == cy - 1 || ny == cy + 1) {
					ret = true;
				}
			}
		}
		return ret;

	}

}

// -----Fattah-----
class Sun extends Piece {
	Sun() {
	}

	Sun(Point currentPoint) {
		this.currentPoint = currentPoint;
	}

	@Override
	public boolean moveable(Point newPoint) {
		boolean ret = false;
		if (newPoint.getY() > 7 || newPoint.getX() > 6) {
			System.out.println("invalid tile location (x < 7 & y < 6)");
			ret = false;
		} else {
			int nx = newPoint.getX(), ny = newPoint.getY(), cx = currentPoint.getX(), cy = currentPoint.getY();
			if (nx == cx - 1 && ny == cy || nx == cx + 1 && ny == cy || nx == cx && ny == cy - 1
					|| nx == cx && ny == cy + 1 || nx == cx + 1 && ny == cy + 1 || nx == cx + 1 && ny == cy - 1
					|| nx == cx - 1 && ny == cy + 1 || nx == cx - 1 && ny == cy - 1) {
				ret = true;
			}
		}
		return ret;
	}

}

// -----Wana / Fattah-----
class Plus extends Piece {
	Point tilesinbetween;

	Plus() {
	}

	Plus(Point currentPoint) {
		this.currentPoint = currentPoint;
	}

	@Override
	public boolean moveable(Point newPoint) { // the movement formula for Plus
		System.out.println("PLUS currentPoint : " + currentPoint + " , new point :" + newPoint);
		boolean ret = false;
		if (newPoint.getY() > 7 || newPoint.getX() > 6) {
			System.out.println("invalid tile location (x < 7 & y < 6)");
			ret = false;
		} else {
			int nx = newPoint.getX(), ny = newPoint.getY(), cx = currentPoint.getX(), cy = currentPoint.getY();
			if (nx == cx) {
				if (ny == cy - 2 || ny == cy - 1 || ny == cy + 1 || ny == cy + 2) {
					ret = true;
				}
			}

			else if (ny == cy) {
				if (nx == cx - 2 || nx == cx - 1 || nx == cx + 1 || nx == cx + 2) {
					ret = true;
				}
			}
		}
		return ret;
	}

	@Override
	public boolean betTile(Point newPoint) { // if there is any space in between the currentTile(start) to
												// newPoint(destination)
		boolean ret = false;
		int diffx = currentPoint.getX() - newPoint.getX();
		int diffy = currentPoint.getY() - newPoint.getY();
		if (diffx == 2 || diffx == -2) {
			ret = true;
		}

		else {
			ret = false;
		}
		return ret;
	}

	@Override
	public Point betTilePoint(Point newPoint) {// Point of tile in between the currentTile(start) to
												// newPoint(destination)
		int difx = currentPoint.getX() - newPoint.getX();
		int dify = currentPoint.getY() - newPoint.getY();
		int x = newPoint.getX();
		int y = newPoint.getY();
		int nx = 0, ny = 0;
		if (betTile(newPoint) == true) {
			if (difx == 0) {
				nx = x + 0; // 2
				if (dify == 2) {
					ny = y + 1;
				} else {
					ny = y - 1;
				}
			} else if (dify > 2 || dify < -2) {
				nx = 0;
				ny = 0;
			} else {
				ny = y + 0;
				if (difx == 2) {
					nx = x + 1;
				} else {
					nx = x - 1;
				}
			}

			tilesinbetween = new Point(nx, ny);
		} else {
			System.out.println("There is no tile in between");
		}

		return tilesinbetween;
	}
}
