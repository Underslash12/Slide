import pkg.*;
import java.awt.event.MouseEvent;

public class Main implements InputControl, InputKeyControl{

	static SlidePuzzle sp;

	public static void main(String args[])
	{
		MouseController mC = new MouseController(Canvas.getInstance(), new Main());
		KeyController kC = new KeyController(Canvas.getInstance(), new Main());

		sp = new SlidePuzzle(4, 4);
		SlidePuzzleGraphics sg;
		// s.print();
		// s.move(new Point(0, -1));
		Canvas.pause(2000);
		// sp.shuffle(1000);
		// Canvas.pause(2000);
		// s.reset();
		// s.print();
		// s.shuffle(1000);
		// s.print();
	}

	public void onMousePress(double x, double y, MouseEvent e)
	{
		// if (s.getGraphics().contains(x, y)) {
		//
		// }
	}
	public void onMouseRelease(double x, double y, MouseEvent e){}
	public void onMouseDrag(double x, double y, MouseEvent e){}
	public void onMouseMove(double x, double y, MouseEvent e){}
	public void onMouseEnter(double x, double y, MouseEvent e){}
	public void onMouseExit(double x, double y, MouseEvent e){}
	public void onMouseClick(double x, double y, MouseEvent e){}
	public void keyPress(String s)
	{
		if (s.equals("[")) {
			sp.reset();
		} else if (s.equals("]")) {
			sp.shuffle(1000);
		}
	}
	public void keyRelease(String s){}
}
