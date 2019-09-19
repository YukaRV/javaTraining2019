package jpl.src.ch01.ex08;

class Point {
	// 1.8 add set method
	public double x,y;

	public void clear() {
		x = 0.0;
		y = 0.0;
	}

	public double distance(Point that) {
		double xdiff = x - that.x;
		double ydiff = y - that.y;
		return Math.sqrt(xdiff * xdiff + ydiff * ydiff);
	}

	public void setPoint(double x,double y) {
		this.x = x;
		this.y = y;
	}
	public void setPoint(Point p) {
		setPoint(p.x,p.y);
	}

	public static void main(String[] args) {
		Point lowerLeft = new Point();
		Point upperRight = new Point();
		Point middlePoint = new Point();

		lowerLeft.setPoint(0.0,0.0);
		System.out.println("lowerLeft: (x,y)=" + lowerLeft.x + "," + lowerLeft.y +")");
		upperRight.setPoint(1280.0,1024.0);
		System.out.println("upperRight: (x,y)=" + upperRight.x + "," + upperRight.y +")");
		middlePoint.setPoint(640.0,512.0);
		System.out.println("middlePoint: (x,y)=" + middlePoint.x + "," + middlePoint.y +")");
	}
}
