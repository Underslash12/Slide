//HIDE
//OUT canvas.png
package pkg;

import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.RescaleOp;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Canvas
{
    private static Canvas canvas = new Canvas();

    private ArrayList<Shape> shapes = new ArrayList<Shape>();
	private ArrayList<Shape> shapesTemp = new ArrayList<Shape>();
    private BufferedImage background;
    public JFrame frame;
    private CanvasComponent component;

    private static final int MIN_SIZE = 100;
    private static final int MARGIN = 10;
    private static final int LOCATION_OFFSET = 120;

    //static JFrame frameC;

    class CanvasComponent extends JComponent
    {
        public void paintComponent(Graphics g)
        {
            g.setColor(new java.awt.Color(229,229,229));
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(java.awt.Color.BLACK);
            if (background != null)
            {
                g.drawImage(background, 0, 0, null);
            }
            for (Shape s : new ArrayList<Shape>(shapes))
            {
                Graphics2D g2 = (Graphics2D) g.create();
                s.paintShape(g2);
                g2.dispose();
            }
            // System.out.println("resize");
        }

        public Dimension getPreferredSize()
        {
            // int maxx = MIN_SIZE;
            // int maxy = MIN_SIZE;
            // if (background != null)
            // {
            //     maxx = Math.max(maxx, background.getWidth());
            //     maxy = Math.max(maxx, background.getHeight());
            // }
            // for (Shape s : shapes)
            // {
            //     maxx = (int) Math.max(maxx, s.getX() + s.getWidth());
            //     maxy = (int) Math.max(maxy, s.getY() + s.getHeight());
            // }
			// changed by Neato to make Canvas the same size (600 x 600)
			return new Dimension(640,640);
            //return new Dimension(maxx + MARGIN, maxy + MARGIN);
        }
    }

    private Canvas()
    {
        component = new CanvasComponent();

        // if (System.getProperty("com.horstmann.codecheck") == null)
        // {
        // JPanel panel = new JPanel() {
        // };


        // component.addComponentListener(new ComponentAdapter() {
        //     @Override
        //     public void componentResized(ComponentEvent e) {
        //         System.out.println("Resized to " + e.getComponent().getSize());
        //     }
        //     @Override
        //     public void componentMoved(ComponentEvent e) {
        //         System.out.println("Moved to " + e.getComponent().getLocation());
        //     }
        // });

        frame = new JFrame();
        if (!System.getProperty("java.class.path").contains("bluej"))
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.add(panel);
        // System.out.println(frame.getBounds().getSize());
        frame.add(component);
        // System.out.println(frame.getContentPane().getBounds().getSize());
        frame.setResizable(false);
        frame.pack();
        //frame.setLocation(LOCATION_OFFSET, LOCATION_OFFSET);
        frame.setVisible(true);

        // System.out.println(frame.getBounds().getSize());

        // }
        // else
        // {
        //     final String SAVEFILE ="canvas.png";
        //     final Thread currentThread = Thread.currentThread();
        //     Thread watcherThread = new Thread()
        //         {
        //             public void run()
        //             {
        //                 try
        //                 {
        //                     final int DELAY = 10;
        //
        //                     while (currentThread.getState() != Thread.State.TERMINATED)
        //                     {
        //                         Thread.sleep(DELAY);
        //                     }
        //                     saveToDisk(SAVEFILE);
        //                 }
        //                 catch (Exception ex)
        //                 {
        //                     ex.printStackTrace();
        //                 }
        //             }
        //         };
            // watcherThread.start();
        // }
    }

    public static Canvas getInstance()
    {
        return canvas;
    }

    public Dimension getSize ()
    {
        // System.out.println(frame.getContentPane().getPreferredSize());
        // System.out.println(component.getPreferredSize());
        // JComponent c = frame.getContentPane().getComponents()[0];
        // return new Dimension(c.getWidth(), c.getHeight());
        return frame.getContentPane().getPreferredSize();
    }

    public void show(Shape s)
    {
        if (!shapes.contains(s))
        {
            shapes.add(s);
			shapesTemp.add(s);
        }
        repaint();
    }

	//added by stew
	public void delete(Shape s)
	{
		if(shapes.contains(s))
		{
			shapes.remove(s);
			shapesTemp.remove(s);
		}
		repaint();
	}

	public void translateAll(double x, double y)
	{
		for(int s = 0; s < shapes.size(); s++){
			shapes.get(s).translate(x,y);
		}
		repaint();
	}

	//added by stew
	public void deleteCanvas()
	{
		shapes.clear();
		shapesTemp.clear();
		repaint();
	}

	//added by stew
	public void clearCanvas()
	{
		shapes.clear();
		System.out.println("" + shapes.size());
		System.out.println("" + shapesTemp.size());
		repaint();
	}

	//added by stew
	public void resetCanvas()
	{
		System.out.println("" + shapesTemp.size());
		for(int length = 0; length < shapesTemp.size(); length++){
			shapes.add(shapesTemp.get(length));
		}
		repaint();
	}

    public void repaint()
    {
        if (frame == null) return;
        Dimension dim = component.getPreferredSize();
        if (dim.getWidth() > component.getWidth()
                || dim.getHeight() > component.getHeight())
        {
            frame.pack();
        }
        else
        {
            frame.repaint();
        }
    }
    /**
     * Pauses so that the user can see the picture before it is transformed.
     */
	 // 10/21/18 Neato's wimpy pause is replaced by Professor Dylan's
	 // Dylan's critique:
	 // Using nested for loops takes up memory and cannot accurately
	 // pause time across different machines.
	// Also, it did not pause for td seconds as the readme suggests,
	// as it is not really feasible to accurately measure time with for loops.
	 // public static void pause(int td)
    // {
		// changed by Neato to just be a delay.
		// for(long i=0; i <td; i++)
		// {
			// for(long j=0; j<td; j++)
			// {
			// }
		// }
        // JFrame frame = getInstance().frame;
        // if (frame == null) return;
        // JOptionPane.showMessageDialog(frame, "Click Ok to continue");
    // }

		 public static void pause(int td) {
	  try {
		Thread.sleep(td);
	  } catch(InterruptedException ex) {
		Thread.currentThread().interrupt();
	  }
	}

    /**
     * Takes a snapshot of the screen, fades it, and sets it as the background.
     */
    public void snapshot()
    {
        Dimension dim = getInstance().component.getPreferredSize();
        java.awt.Rectangle rect = new java.awt.Rectangle(0, 0, dim.width, dim.height);
        BufferedImage image = new BufferedImage(rect.width, rect.height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setColor(new java.awt.Color(229,229,229));
        g.fillRect(0, 0, rect.width, rect.height);
        g.setColor(java.awt.Color.BLACK);
        getInstance().component.paintComponent(g);
        float factor = 0.8f;
        float base = 255f * (1f - factor);
        RescaleOp op = new RescaleOp(factor, base, null);
        BufferedImage filteredImage
           = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        op.filter(image, filteredImage);
        getInstance().background = filteredImage;
        getInstance().component.repaint();
    }

    public void saveToDisk(String fileName)
    {
        Dimension dim = component.getPreferredSize();
    	  java.awt.Rectangle rect = new java.awt.Rectangle(0, 0, dim.width, dim.height);
    	  BufferedImage image = new BufferedImage(rect.width, rect.height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setColor(java.awt.Color.WHITE);
        g.fill(rect);
        g.setColor(java.awt.Color.BLACK);
        component.paintComponent(g);
        String extension = fileName.substring(fileName.lastIndexOf('.') + 1);
        try
        {
            ImageIO.write(image, extension, new File(fileName));
        }
        catch(IOException e)
        {
            System.err.println("Was unable to save the image to " + fileName);
        }
        g.dispose();
    }
}
