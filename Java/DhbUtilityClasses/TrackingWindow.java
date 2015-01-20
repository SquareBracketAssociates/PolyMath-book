package DhbUtilityClasses;


import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Window;
import java.util.StringTokenizer;
import DhbUtilityClasses.MultiLineLabel;
/**
 * A window used to display information tracked with the mouse.
 *
 * @version 	1.0, 21 Jul 1998
 * @author 	Didier H. Besset
 */

public class TrackingWindow extends Window
{
	/**
	 * The TextComponent contain the text to display.
	 */
	private MultiLineLabel textPane;
	/**
	 * The TextComponent contain the text to display.
	 */
	private Dimension offset = new Dimension( -10, -10);
	/**
	 * Left alignment
	 */
	public static final int LEFT = MultiLineLabel.LEFT;
	/**
	 * Center alignment
	 */
	public static final int CENTER = MultiLineLabel.CENTER;
	/**
	 * Right alignment
	 */
	public static final int RIGHT = MultiLineLabel.RIGHT;


	/**
	 * Constructs the window with a given parent
	 * @param parent the component requesting tracking display.
	 */
	public TrackingWindow( Frame parent)
	{
		super( parent);
		textPane = new MultiLineLabel();
		textPane.setBackground(Color.yellow);
		add("Center", textPane);
	}
   /** 
	 * Defines the alignment of the text.
	 */
	public void setAlignment( int align)
	{
		textPane.setAlignment( align);
	}
	/**
	 * Defines the offset applied to position when displaying the receiver.
	 * @param x offset in direction x.
	 * @param y offset in direction y.
	 */
	public void setOffset( int x, int y)
	{
		setOffset( new Dimension( x, y));
	}
	/**
	 * Defines the offset applied to position when displaying the receiver.
	 * @param size offset in both directions (a Dimension).
	 */
	public void setOffset( Dimension size)
	{
		offset = size;
	}
	/**
	 * Replaces the contents of the text pane with a new text
	 * and resize the window accordingly.
	 * @param t the new text
	 * @param x the x position of the bottom left corner of the window (if x offset is zero)
	 * @param y the y position of the bottom left corner of the window (if y offset is zero)
	 */
	public void setText(String  t, int x, int y)
	{
		invalidate();
		textPane.setText(t);
		Dimension size = getPreferredSize();
		setBounds( x + offset.width, y - size.height + offset.height, size.width, size.height);
		textPane.repaint();
	}
}