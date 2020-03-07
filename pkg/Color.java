//HIDE
package pkg;
public class Color
{
    private int red,red1;
    private int green,green1;
    private int blue,blue1;

    // Color constants

    public static final Color RED = new Color(255, 0, 0);
    public static final Color GREEN = new Color(0, 255, 0);
    public static final Color BLUE = new Color(0, 0, 255);
    public static final Color WHITE = new Color(255, 255, 255);
    public static final Color LIGHT_GRAY = new Color(192, 192, 192);
    public static final Color GRAY = new Color(128, 128, 128);
    public static final Color DARK_GRAY = new Color(64, 64, 64);
    public static final Color BLACK = new Color(0, 0, 0);
    public static final Color CYAN = new Color(0, 255, 255);
    public static final Color MAGENTA = new Color(255, 0, 255);
    public static final Color YELLOW = new Color(255, 255, 0);
    public static final Color PINK = new Color(255, 175, 175);
    public static final Color ORANGE = new Color(255, 200, 0);
    
    /**
     * Constructs a new Color object.
     * @param red the red value of the color (between 0 and 255)
     * @param green the green value of the color (between 0 and 255)
     * @param blue the blue value of the color (between 0 and 255)
     */
    public Color(int red, int green, int blue)
    {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    /**
     * Gets the red value of this color.
     * @return the red value (between 0 and 255)
     */
    public int getRed()
    {
       return red;
    }

    /**
     * Gets the green value of this color.
     * @return the green value (between 0 and 255)
     */
    public int getGreen()
    {
       return green;
    }

    /**
     * Gets the blue value of this color.
     * @return the blue value (between 0 and 255)
     */
    public int getBlue()
    {
       return blue;
    }
	
	//Sets the red value
	public void setRed(int nRed)
	{
		this.red = nRed;
	}
	
	//Sets the green value
	public void setGreen(int nGreen)
	{
		this.green = nGreen;
	}
	
	//Sets the blue value
	public void setBlue(int nBlue)
	{
		this.blue = nBlue;
	}
	
	//Creates a random color
	public static Color rRGB()
	{
		Color c = new Color((int)(Math.random()*256),(int)(Math.random()*256),(int)(Math.random()*256));
		return c;
	}
	
	//Color from a double
	public static Color colorFromDouble(double d)
	{
		double[] vals = new double[3];
		for(int i = 0; i < vals.length; i++) {
			if(d * 6 > 2 * i + 2) {
				vals[i] = -1 * Math.abs(255 * (6 * d - (2 * i + 6))) + 2 * 255;
			}
			else 
				vals[i] = -1 * Math.abs(255 * (6 * d - 2 * i)) + 2 * 255;
			vals[i] = Math.round(vals[i]);
			if(vals[i] > 255) vals[i] = 255;
			if(vals[i] < 0) vals[i] = 0;
		}
		return new Color((int)vals[0], (int)vals[1], (int)vals[2]);
	}
	
	public String toString()
	{
		return "Red: " + getRed() + " | Green: " + getGreen() + " | Blue: " + getBlue();
	}
}
