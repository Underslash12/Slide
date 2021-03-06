//HIDE
package pkg;
import java.awt.Dimension;
import java.awt.Graphics2D;

import javax.swing.JLabel;

public class Text implements Shape
{
    private Color color = Color.BLACK;
    private JLabel label = new JLabel();
    private double x;
    private double y;
    private double xGrow = 1;
    private double yGrow = 1;

    /**
     * Constructs a text at a given location.
     * @param x the leftmost x-position of the shape
     * @param y the topmost y-position of the shape
     * @param message the text string
     */
    public Text(double x, double y, String message)
    {
        this.x = x;
        this.y = y;
        label.setText(message);
    }


    /**
     * Gets the leftmost x-position of the bounding box.
     * @return the leftmost x-position
     */
    public int getX()
    {
        return (int) Math.round(x) ;
    }

    /**
     * Gets the topmost y-position of the bounding box.
     * @return the topmost y-position
     */
    public int getY()
    {
        return (int) Math.round(y);
    }


    /**
     * Gets the width of the bounding box.
     * @return the width
     */
    public int getWidth()
    {
        return (int) Math.round(label.getPreferredSize().getWidth() * xGrow);
    }

    /**
     * Gets the height of the bounding box.
     * @return the height
     */
    public int getHeight()
    {
        return (int) Math.round(label.getPreferredSize().getHeight() * yGrow);
    }

    /**
     * Moves this text by a given amount.
     * @param dx the amount by which to move in x-direction
     * @param dy the amount by which to move in y-direction
     */
    public void translate(double dx, double dy)
    {
        x += dx;
        y += dy;
        Canvas.getInstance().repaint();
    }

    public void translateNoRe(double dx , double dy)
    {
        x += dx;
        y += dy;
    }

    /**
     * Resizes this text both horizontally and vertically.
     * @param dw the amount by which to resize the width on each side
     * @param dw the amount by which to resize the height on each side
     */
    public void grow(double dw, double dh)
    {
        xGrow *= dw;
        yGrow *= dh;
        Canvas.getInstance().repaint();
    }

    /**
     * Sets the color for drawing this text.
     * @param newColor the new color
     */
    public void setColor(Color newColor)
    {
        color = newColor;
        Canvas.getInstance().repaint();
    }

    /**
     * Shows this text on the canvas.
     */
    public void draw()
    {
        Canvas.getInstance().show(this);
    }

	public void undraw()
	{
		Canvas.getInstance().delete(this);
	}

    // new method added by Neato to support translating, changing Text Objects
    public void setText(String update)
	{
		label.setText(update);
		Canvas.getInstance().repaint();
	}

    public void center(Rectangle r)
    {
        translateNoRe(-getX(), -getY());
        translateNoRe(r.getX(), r.getY());
        translate((r.getWidth() - getWidth()) / 2, (r.getHeight() - getHeight()) / 2);
    }

    public String toString()
    {
        return "Text[x=" + getX() + ",y=" + getY() + ",message=" + label.getText() + "]";
    }

    public void paintShape(Graphics2D g2)
    {
        if (color != null)
        {
            label.setForeground(new java.awt.Color((int) color.getRed(), (int) color.getGreen(), (int) color.getBlue()));
            Dimension dim = label.getPreferredSize();
            if (dim.width > 0 && dim.height > 0)
            {
                label.setBounds(0, 0, dim.width, dim.height);
                g2.translate(getX(), getY());
                g2.scale(getWidth() / dim.width, getHeight() / dim.height);
                label.paint(g2);
            }
        }
    }
}
