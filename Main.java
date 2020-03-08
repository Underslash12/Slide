import pkg.*;

public class Main { // implements InputControl, InputKeyControl{

	public static void main(String args[])
	{
		// MouseController mC = new MouseController(Canvas.getInstance(), new Main());
		// KeyController kC = new KeyController(Canvas.getInstance(), new Main());

		SlidePuzzle s = new SlidePuzzle(4, 4);
		SlidePuzzleGraphics sg;
		// s.print();
		// s.move(new Point(0, -1));
		s.shuffle(1000);
		// s.print();
		// s.shuffle(1000);
		// s.print();
	}

	// public void onMousePress(double x, double y){}
	// public void onMouseRelease(double x, double y){}
	// public void onMouseDrag(double x, double y){}
	// public void onMouseMove(double x, double y){}
	// public void onMouseEnter(double x, double y){}
	// public void onMouseExit(double x, double y){}
	// public void onMouseClick(double x, double y){}
	// public void keyPress(String s){}
	// public void keyRelease(String s){}
}
