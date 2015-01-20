package DhbUtilityClasses;


import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.StringTokenizer;
import java.util.Vector;
/**
 * A window used to display multiline text without editing possibilities
 * and no scroll bars..
 *
 * @version 	1.0, 21 Jul 1998
 * @author 	Didier H. Besset
 */

public class MultiLineLabel extends Canvas
{
	/**
	 * The lines containing the text to display. Each element is a string.
	 */
	private Vector lines;
	/**
	 * The dimension of the receiver (space needed to display all lines).
	 */
	private Dimension size;
	/**
	 * The margin on either side of the text in both direction
	 */
	private Dimension margin = new Dimension( 4, 4);
	/**
	 * The margin on either side of the text in both direction
	 */
	private int alignment = LEFT;
	/**
	 * Left alignment
	 */
	public static final int LEFT = 0;
	/**
	 * Center alignment
	 */
	public static final int CENTER = 1;
	/**
	 * Right alignment
	 */
	public static final int RIGHT = 2;


	/**
	 * Constructs the label with no lines.
	 */
	public MultiLineLabel()
	{
		resetText();
	}
	/**
	 * Appends a singe line at the end of the label
	 * @param text the text displayed on the new line (a String).
	 */
	public void appendLine( String text)
	{
		FontMetrics fm = getFontMetrics( getFont());
		int w = fm.stringWidth( text) + 2 * margin.width;
		size = new Dimension( Math.max( size.width, w), size.height + fm.getHeight());
		lines.addElement( text);
	}
   /** 
	 * Returns the minimumSize size of this component.
	 */
	public Dimension minimumSize()
	{
		return size;
	}
   /**
	 * Displays all lines in the label.
	 */
	public void paint( Graphics g)
	{
		switch ( alignment)
		{
		case CENTER:
			paintCenter( g);
			break;
		case RIGHT:
			paintRight( g);
			break;
		default:
			paintLeft( g);
			break;
		}
	}
   /**
	 * Displays all lines centered in the label.
	 */
	private void paintCenter( Graphics g)
	{
		FontMetrics fm = g.getFontMetrics();
		int x = margin.width;
		int y = fm.getAscent() + margin.height;
		for( int n = 0; n < lines.size(); n++)
		{
			String s = (String) lines.elementAt(n);
			g.drawString( s, size.width - fm.stringWidth(s) / 2, y);
			y += fm.getHeight();
		}
	}
   /**
	 * Displays all lines left aligned in the label.
	 */
	private void paintLeft( Graphics g)
	{
		FontMetrics fm = g.getFontMetrics();
		int x = margin.width;
		int y = fm.getAscent() + margin.height;
		for( int n = 0; n < lines.size(); n++)
		{
			g.drawString( (String) lines.elementAt(n), x, y);
			y += fm.getHeight();
		}
	}
   /**
	 * Displays all lines right aligned in the label.
	 */
	private void paintRight( Graphics g)
	{
		FontMetrics fm = g.getFontMetrics();
		int x = margin.width;
		int y = fm.getAscent() + margin.height;
		for( int n = 0; n < lines.size(); n++)
		{
			String s = (String) lines.elementAt(n);
			g.drawString( s, size.width - fm.stringWidth(s), y);
			y += fm.getHeight();
		}
	}
   /** 
	 * Returns the preferred size of this component.
	 */
	public Dimension preferredSize()
	{
		return size;
	}
	/**
	 * Remove all lines from the label.
	 */
	public void resetText()
	{
		lines = new Vector();
		size = new Dimension( 4, 4);
	}
   /** 
	 * Defines the alignment of the text.
	 */
	public void setAlignment( int align)
	{
		alignment = align;
	}
	/**
	 * Defines the text displayed in the label
	 * @param text the text to display (a String).
	 */
	public void setText( String text)
	{
		resetText();
		StringTokenizer parse = new StringTokenizer( text, "\n");
		while ( parse.hasMoreTokens())
			appendLine( parse.nextToken());
	}
}