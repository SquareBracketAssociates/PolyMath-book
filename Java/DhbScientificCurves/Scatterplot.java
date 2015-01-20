\begin{verbatim}
package DhbScientificCurves;


import java.awt.Canvas;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.AWTEvent;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import DhbUtilityClasses.TrackingWindow;
/**
 * Scatterplot is a subclass of the class Canvas.
 *
 * A Scatterplot manages all aspect related to the drawing of scientific
 * data. It handles scaling issues and delegates the responsibility of
 * drawing the curve (or histogram) to a "curve" object. The "curve" object
 * must implement the interface HistogramOrCurveDefinition.
 * @see HistogramOrCurveDefinition
 * A Scatterplot also manages mouse events as follows:
 * - Left mouse click on y axis (primary or secondary):
 *				changes the corresponding axis to the next in list.
 * - Left mouse click: the coordinates are passed to the "curve" object.
 * - Right mouse click: a pop-up window shows the coordinates of the point.
 * - Right mouse drag: the pop-up window showing the coordinates of the point
 *   is updated continuously.
 *
 * @version 1.0 23 Jul 1998
 * @author Didier H. Besset
 */
public class Scatterplot extends Canvas implements MouseListener, MouseMotionListener
{
	/**
	 * The axes used to draw the curves. The key is the label of the Y axis.
	 * @see AxisSystem
	 */
	private Hashtable axisSystems;
	/**
	 * The list of the curves.
	 * @see CurveDefinition
	 */
	private Vector curves;
	/**
	 * Orientation of the x axis: LEFT, RIGHT, UP or DOWN.
	 * Default is RIGHT.
	 */
	private int xAxisOrientation;
	/**
	 * Label of the x axis.
	 */
	private String xAxisLabel;
	/**
	 * Orientation of the y axis relative to the x axis.
	 * If true, the y axis makes a positive angle with the x axis.
	 * Default is true.
	 */
	private boolean cartesianAxes;
	/**
	 * Origin of the axes (crossing of x and y axes) in pixel coordinates.
	 */
	private Point axisOrigin;
	/**
	 * Size of the tickmarks on both axes in pixels.
	 */
	private int tickmarkSize;
	/**
	 * Width of the window (used to detect resize).
	 */
	private int width = 0;
	/**
	 * Height of the window (used to detect resize).
	 */
	private int height = 0;
	/**
	 * If true, a secondary y axis is drawn at the end of the x axis if there
	 * are more than 1 curve.
	 * Default is false.
	 */
	private boolean secondaryAxis;
	/**
	 * Rectangle delimited by the x and y axis in pixel coordinates.
	 */
	private Rectangle clipRectangle;
	/**
	 * Name of the primary y axis.
	 * Default is the name of the first defined curve.
	 */
	private String selectedAxis = null;
	/**
	 * Name of the secondary y axis (only if secondaryAxis is true).
	 * Default is the name of the second defined curve with a name different
	 * from that of the primary y axis.
	 */
	private String selectedSecondaryAxis = null;
	/**
	 * Font used to draw the axis labels.
	 * Default is the current font.
	 */
	private Font axisLabelFont;
	/**
	 * Font used to draw the tickmark labels.
	 * Default is the current font.
	 */
	private Font tickmarkLabelFont;
	/**
	 * Width or height of the sentitive area around the y axes (primary or secondary).
	 */
	private int clickTolerance;
	/**
	 * Tracking window.
	 */
	private TrackingWindow trackingWindow = null;
	/**
	 * Tracking window.
	 */
	private Cursor currentCursor;	
	/**
	 * Orientation of the x axis toward the right of the screen.
	 */
	public static final int RIGHT = 1;
	/**
	 * Orientation of the x axis toward the left of the screen.
	 */
	public static final int LEFT = 2;
	/**
	 * Orientation of the x axis toward the top of the screen.
	 */
	public static final int UP = 3;
	/**
	 * Orientation of the x axis toward the bottom of the screen.
	 */
	public static final int DOWN = 4;


	/**
	 * Constructs an empty scatterplot.
	 *
	 */
	public Scatterplot()
	{
		curves = new Vector();
		setDefaultSettings();
	}
	/**
	 * Constructs an empty scatterplot with given estimated number of curves.
	 * The number specified is not limiting. It is used to allocate space for
	 * the vector containing the curves.
	 * @see Vector
	 *
	 * @param size the estimated number of curves
	 *
	 */
	public Scatterplot( int size)
	{
		curves = new Vector(size);
		setDefaultSettings();
	}
	/**
	 * Returns a point in absolute coordinates (screen coordinate system).
	 * @param x local x coordinate
	 * @param y local y coordinate
	 */
	private Point absolutePoint( int x, int y)
	{
		int aX = x;
		int aY = y;
		Component window = this;
		while ( window != null)
		{
			aX+= window.getBounds().x;
			aY+= window.getBounds().y;
			window = window.getParent();
		}
		return new Point( aX, aY);
	}
	/**
	 * Adds a new curve (or histogram) on the scatterplot attached to the
	 * specified axis system. If the axis system does not exist, it is created.
	 * The name of the axis system is the label drawn near the extremity of
	 * the y axis. The scatterplot painted anew.
	 * @param curve a curve or an histogram.
	 * @param name the name of the axis system.
	 */
	public void addCurve( HistogramOrCurveDefinition curve, String name)
	{
		addCurveNoUpdate( curve, name, false, false);
		repaint();
	}
	/**
	 * Adds a new curve (or histogram) on the scatterplot attached to the
	 * specified axis system. If the axis system does not exist, it is created.
	 * The name of the axis system is the label drawn near the extremity of
	 * the y axis. The scatterplot display is not updated.
	 * @param curve a curve or an histogram.
	 * @param name the name of the axis system.
	 * @param xAxisInteger flag indicating whether tick marks of the x axis should be rounded to integer values.
	 * @param yAxisInteger flag indicating whether tick marks of the y axis should be rounded to integer values.
	 */
	public void addCurve( HistogramOrCurveDefinition curve, String name, boolean xAxisInteger, boolean yAxisInteger)
	{
		addCurveNoUpdate( curve, name, xAxisInteger, yAxisInteger);
		repaint();
	}
	/**
	 * Adds a new curve (or histogram) on the scatterplot attached to the
	 * specified axis system. If the axis system does not exist, it is created.
	 * The name of the axis system is the label drawn near the extremity of
	 * the y axis. The scatterplot display is not updated.
	 * @param curve a curve or an histogram.
	 * @param name the name of the axis system.
	 */
	public void addCurveNoUpdate( HistogramOrCurveDefinition curve, String name)
	{
		addCurveNoUpdate( curve, name, false, false);
	}
	/**
	 * Adds a new curve (or histogram) on the scatterplot attached to the
	 * specified axis system. If the axis system does not exist, it is created.
	 * The name of the axis system is the label drawn near the extremity of
	 * the y axis. The scatterplot display is not updated.
	 * @param curve a curve or an histogram.
	 * @param name the name of the axis system.
	 * @param xAxisInteger flag indicating whether tick marks of the x axis should be rounded to integer values.
	 * @param yAxisInteger flag indicating whether tick marks of the y axis should be rounded to integer values.
	 */
	public void addCurveNoUpdate( HistogramOrCurveDefinition curve, String name, boolean xAxisInteger, boolean yAxisInteger) throws IllegalArgumentException
	{
		PlottingScale xScale;
		double[] range = curve.getRange();
		ScaledCurve newCurve = new ScaledCurve( curve, name);
		curves.addElement( newCurve);
		if ( !axisSystems. containsKey( name) )
		{
			if ( range == null )
				throw new IllegalArgumentException( "Cannot add curve definition without defined range on new axis system");
			if ( axisSystems.isEmpty() )
			{
				xScale =  new PlottingScale();
				xScale.setIntegerScale( xAxisInteger);
				switch ( xAxisOrientation )
				{
				case LEFT:
				case UP:
					xScale.setInvertedAxis();
					break;
				}
				xScale.setRange( range[0], range[1]);
			}
			else
			{
				xScale = xScale();
				xScale.addRange( range[0], range[1]);
			}
			PlottingScale yScale = new PlottingScale();
			yScale.setIntegerScale( yAxisInteger);
			switch ( xAxisOrientation )
			{
			case LEFT:
			case DOWN:
				if ( !cartesianAxes )
					yScale.setInvertedAxis();
				break;
			case RIGHT:
			case UP:
				if ( cartesianAxes )
					yScale.setInvertedAxis();
				break;
			}
			yScale.setRange( range[2], range[3]);
			axisSystems.put( name, new AxisSystem( xScale, yScale, xAxisOrientation == LEFT || xAxisOrientation == RIGHT));
			if ( selectedAxis == null )
				selectedAxis = name;
			else if ( secondaryAxis && selectedSecondaryAxis == null )
				selectedSecondaryAxis = name;
		}
		else if ( range != null )
		{
			AxisSystem axis = (AxisSystem) axisSystems.get( name);
			axis.xScale.addRange( range[0], range[1]);
			axis.yScale.addRange( range[2], range[3]);
		}
	}
	/**
	 * Processes a mouse click on one of the y axes (primary or secondary).
	 * @return whether or not the event was processed.
	 */
	private boolean clickOnAxis( int x, int y)
	{
		Rectangle axisRect = null;
		Rectangle secondaryAxisRect = null;
		if ( cartesianAxes )
		{
			switch ( xAxisOrientation )
			{
			case RIGHT:
				axisRect = new Rectangle( clipRectangle.x - clickTolerance, clipRectangle.y,
										  2 * clickTolerance + 1, clipRectangle.height);
				secondaryAxisRect = new Rectangle( clipRectangle.x + clipRectangle.width - clickTolerance, clipRectangle.y,
										  2 * clickTolerance + 1, clipRectangle.height);
				break;
			case LEFT:
				axisRect = new Rectangle( clipRectangle.x + clipRectangle.width - clickTolerance, clipRectangle.y,
										  2 * clickTolerance + 1, clipRectangle.height);
				secondaryAxisRect = new Rectangle( clipRectangle.x - clickTolerance, clipRectangle.y,
										  2 * clickTolerance + 1, clipRectangle.height);
				break;
			case UP:
				axisRect = new Rectangle( clipRectangle.x, clipRectangle.y + clipRectangle.height - clickTolerance,
										  clipRectangle.width, 2 * clickTolerance + 1);
				secondaryAxisRect = new Rectangle( clipRectangle.x, clipRectangle.y - clickTolerance,
										  clipRectangle.width, 2 * clickTolerance + 1);
				break;
			case DOWN:
				axisRect = new Rectangle( clipRectangle.x, clipRectangle.y - clickTolerance,
										  clipRectangle.width, 2 * clickTolerance + 1);
				secondaryAxisRect = new Rectangle( clipRectangle.x, clipRectangle.y + clipRectangle.height - clickTolerance,
										  clipRectangle.width, 2 * clickTolerance + 1);
				break;
			}
		}
		else
		{
			switch ( xAxisOrientation )
			{
			case RIGHT:
				axisRect = new Rectangle( clipRectangle.x + clipRectangle.width - clickTolerance, clipRectangle.y,
										  2 * clickTolerance + 1, clipRectangle.height);
				secondaryAxisRect = new Rectangle( clipRectangle.x - clickTolerance, clipRectangle.y,
										  2 * clickTolerance + 1, clipRectangle.height);
				break;
			case LEFT:
				axisRect = new Rectangle( clipRectangle.x - clickTolerance, clipRectangle.y,
										  2 * clickTolerance + 1, clipRectangle.height);
				secondaryAxisRect = new Rectangle( clipRectangle.x + clipRectangle.width - clickTolerance, clipRectangle.y,
										  2 * clickTolerance + 1, clipRectangle.height);
				break;
			case UP:
				axisRect = new Rectangle( clipRectangle.x, clipRectangle.y - clickTolerance,
										  clipRectangle.width, 2 * clickTolerance + 1);
				secondaryAxisRect = new Rectangle( clipRectangle.x, clipRectangle.y + clipRectangle.height - clickTolerance,
										  clipRectangle.width, 2 * clickTolerance + 1);
				break;
			case DOWN:
				axisRect = new Rectangle( clipRectangle.x, clipRectangle.y + clipRectangle.height - clickTolerance,
										  clipRectangle.width, 2 * clickTolerance + 1);
				secondaryAxisRect = new Rectangle( clipRectangle.x, clipRectangle.y - clickTolerance,
										  clipRectangle.width, 2 * clickTolerance + 1);
				break;
			}
		}
		if ( axisRect.contains( x, y) )
		{
			toggleAxis();
			return true;
		}
		if ( secondaryAxis && secondaryAxisRect.contains( x, y) )
		{
			toggleSecondaryAxis();
			return true;
		}
		return false;
	}
	/**
	 * Finds out which curve was clicked on (event processing is left to the curve).
	 * @param x local x coordinate of mouse event
	 * @param y local y coordinate of mouse event
	 * @return whether or not the event was processed.
	 */
	private boolean clickOnCurve( int x, int y)
	{
		for( int n = 0; n < curves.size(); n++)
		{
			ScaledCurve c = (ScaledCurve) curves.elementAt( n);
			if ( c.curve.handleMouseClick( x, y, (AxisSystem) axisSystems.get( c.scaleName)))
				return true;
		}
		return false;
	}
	/**
	 * Computes the clip rectangle (axis system rectangle).
	 * @param g the graphic context used to draw the scatterplot.
	 */
	private void computeDimensions(Graphics g)
	{
		FontMetrics fma = axisLabelFont == null ? g.getFontMetrics( ) : g.getFontMetrics( axisLabelFont);
		FontMetrics fmt = tickmarkLabelFont == null ? g.getFontMetrics( ) : g.getFontMetrics( tickmarkLabelFont);
		int margin = fma.getHeight() + fmt.getHeight();
		Point axisOrigin = new Point( 0, 0);
		Point origin = getOriginOffset( g);
		Rectangle boundingBox = getBounds();
		int x1 = 0;
		int x2 = 0;
		int y1 = 0;
		int y2 = 0;
		if ( cartesianAxes )
		{
			switch ( xAxisOrientation )
			{
			case RIGHT:
				x1 = origin.x;
				x2 = boundingBox.width - ( secondaryAxis ? origin.x : margin);
				y1 = margin;
				y2 = boundingBox.height - origin.y;
				axisOrigin = new Point( x1, y2);
				break;
			case LEFT:
				x1 = ( secondaryAxis ? origin.x : margin);
				x2 = boundingBox.width - origin.x;
				y1 = origin.y;
				y2 = boundingBox.height - margin;
				axisOrigin = new Point( x2, y1);
				break;
			case UP:
				x1 = margin;
				x2 = boundingBox.width - origin.x;
				y1 = ( secondaryAxis ? origin.y : margin);
				y2 = boundingBox.height - origin.y;
				axisOrigin = new Point( x2, y2);
				break;
			case DOWN:
				x1 = origin.x;
				x2 = boundingBox.width - margin;
				y1 = origin.y;
				y2 = boundingBox.height - ( secondaryAxis ? origin.y : margin);
				axisOrigin = new Point( x1, y1);
				break;
			}
		}
		else
		{
			switch ( xAxisOrientation )
			{
			case RIGHT:
				x1 = origin.x;
				x2 = boundingBox.width - ( secondaryAxis ? origin.x : margin);
				y1 = origin.y;
				y2 = boundingBox.height - margin;
				axisOrigin = new Point( x1, y1);
				break;
			case LEFT:
				x1 = ( secondaryAxis ? origin.x : margin);
				x2 = boundingBox.width - origin.x;
				y1 = margin;
				y2 = boundingBox.height - origin.y;
				axisOrigin = new Point( x2, y2);
				break;
			case UP:
				x1 = origin.x;
				x2 = boundingBox.width - margin;
				y1 = ( secondaryAxis ? origin.y : margin);
				y2 = boundingBox.height - origin.y;
				axisOrigin = new Point( x1, y2);
				break;
			case DOWN:
				x1 = margin;
				x2 = boundingBox.width - origin.x;
				y1 = origin.y;
				y2 = boundingBox.height - ( secondaryAxis ? origin.y : margin);
				axisOrigin = new Point( x2, y1);
				break;
			}
		}
		x2 -= x1;
		y2 -= y1;
		clipRectangle = new Rectangle( x1, y1, x2, y2);
		for ( Enumeration e = axisSystems.elements(); e.hasMoreElements();)
		{
			AxisSystem axis = (AxisSystem) e.nextElement();
			axis.setOrigin( axisOrigin);
			axis.setLengths( x2, y2);
		}
	}
	/**
	 * Finds the origin of the axis system (axes cross point).
	 * @param g the graphic context used to draw the scatterplot.
	 */
	private Point getOriginOffset( Graphics g)
	{
		FontMetrics fma = axisLabelFont == null ? g.getFontMetrics( ) : g.getFontMetrics( axisLabelFont);
		FontMetrics fmt = tickmarkLabelFont == null ? g.getFontMetrics( ) : g.getFontMetrics( tickmarkLabelFont);
		int y = 0;
		int x = 0;
		int w;
		switch ( xAxisOrientation )
		{
		case RIGHT:
		case LEFT:
			x = 0;
			for  (Enumeration e = axisSystems.elements(); e.hasMoreElements();)
			{
				AxisSystem axis = (AxisSystem) e.nextElement();
				w = axis.yScale.labelWidth( fmt);
				if ( w > x )
					x = w;
			}
			x += fma.getHeight() + tickmarkSize;
			y = fma.getHeight() + fmt.getHeight() + tickmarkSize;
			break;
		case UP:
		case DOWN:
			x = fma.getHeight() + tickmarkSize + xScale().labelWidth( fmt);
			y = fma.getHeight() + fmt.getHeight() + tickmarkSize;
			break;
		}
		return new Point( x, y);
	}
	/**
	 * Constructs the tracking window used to display mouse coordinates.
	 * @see TrackingWindow
	 */
	private void initializeTracking()
	{
		trackingWindow = new TrackingWindow( parentFrame());
		currentCursor = getCursor();
		setCursor( Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
	}
/**
 * (c) Copyrights Didier BESSET, 1999, all rights reserved.
 * @param e java.awt.event.MouseEvent
 */
private boolean isLeftButtonEvent( MouseEvent e)
{
	return ( e.getModifiers() & ( InputEvent.BUTTON1_MASK | InputEvent.BUTTON2_MASK | InputEvent.BUTTON3_MASK)) == InputEvent.BUTTON1_MASK;
}
/**
 * (c) Copyrights Didier BESSET, 1999, all rights reserved.
 * @param e java.awt.event.MouseEvent
 */
private boolean isRightButtonEvent( MouseEvent e)
{
	return ( e.getModifiers() & (InputEvent.BUTTON1_MASK | InputEvent.BUTTON2_MASK | InputEvent.BUTTON3_MASK)) == InputEvent.BUTTON3_MASK;
}
/**
 * mouseClicked method comment.
 */
public void mouseClicked(MouseEvent e)
{
	if ( isLeftButtonEvent( e) )
	{
		int x = e.getX();
		int y = e.getY();
		if ( !clickOnAxis( x, y) )
			clickOnCurve( x, y);
	}
}
/**
 * (c) Copyrights Didier BESSET, 1999, all rights reserved.
 * @param e MouseEvent
 */
public void mouseDragged(MouseEvent e)
{
	if ( isRightButtonEvent( e) )
	{
			if ( trackingWindow != null )
				trackCoordinates( e.getX(), e.getY());
	}
}
/**
 * mouseEntered method comment.
 */
public void mouseEntered(MouseEvent e) {
}
/**
 * mouseExited method comment.
 */
public void mouseExited(MouseEvent e) {
}
/**
 * mouseMoved method comment.
 */
public void mouseMoved(MouseEvent e) {
}
/**
 * (c) Copyrights Didier BESSET, 1999, all rights reserved.
 * @param e java.awt.event.MouseEvent
 */
public void mousePressed(MouseEvent e)
{
	if ( isRightButtonEvent( e) )
	{
		initializeTracking();
		trackCoordinates( e.getX(), e.getY());
		trackingWindow.setVisible( true);
	}
}
/**
 * mouseReleased method comment.
 */
public void mouseReleased(MouseEvent e)
{
	if ( trackingWindow != null )
		terminateTracking();
}
	/**
	 * Returns the number of curves defined in the scatterplot.
	 * @return the number of curves defined in the scatterplot.
	 */
	public int numberOfCurves()
	{
		return curves.size();
	}
	/**
	 * Draws the contents of the scatterplot (main display method called by Canvas).
	 * @see Canvas
	 * @param g graphics context used to perform the drawing.
	 */
	public void paint( Graphics g)
	{
		if ( axisSystems.isEmpty() )
		{
			String message = "No curves defined";
			Rectangle boundingBox = getBounds();
			FontMetrics fm = g.getFontMetrics();
			int x = ( boundingBox.width - fm.stringWidth(message)) / 2;
			g.drawString( message, x, boundingBox.height / 2);
		}
		else
			paintCurves( g);
	}
	/**
	 * Draws the labels of the selected axes
	 * @param g graphics context used to perform the drawing.
	 */
	private void paintAxisLabels( Graphics g)
	{
		if ( cartesianAxes )
		{
			switch ( xAxisOrientation )
			{
			case RIGHT:
				if ( xAxisLabel != null )
					paintRightBottomLabel( g, xAxisLabel);
				if ( selectedAxis != null )
					paintLeftTopLabel( g, selectedAxis);
				if ( selectedSecondaryAxis != null )
					paintRightTopLabel( g, selectedSecondaryAxis);
				break;
			case LEFT:
				if ( xAxisLabel != null )
					paintLeftTopLabel( g, xAxisLabel);
				if ( selectedAxis != null )
					paintRightBottomLabel( g, selectedAxis);
				if ( selectedSecondaryAxis != null )
					paintLeftBottomLabel( g, selectedSecondaryAxis);
				break;
			case UP:
				if ( xAxisLabel != null )
					paintRightTopLabel( g, xAxisLabel);
				if ( selectedAxis != null )
					paintLeftBottomLabel( g, selectedAxis);
				if ( selectedSecondaryAxis != null )
					paintLeftTopLabel( g, selectedSecondaryAxis);
				break;
			case DOWN:
				if ( xAxisLabel != null )
					paintLeftBottomLabel( g, xAxisLabel);
				if ( selectedAxis != null )
					paintRightTopLabel( g, selectedAxis);
				if ( selectedSecondaryAxis != null )
					paintRightBottomLabel( g, selectedSecondaryAxis);
				break;
			}
		}
		else
		{
			switch ( xAxisOrientation )
			{
			case RIGHT:
				if ( xAxisLabel != null )
					paintRightTopLabel( g, xAxisLabel);
				if ( selectedAxis != null )
					paintLeftBottomLabel( g, selectedAxis);
				if ( selectedSecondaryAxis != null )
					paintRightBottomLabel( g, selectedSecondaryAxis);
				break;
			case LEFT:
				if ( xAxisLabel != null )
					paintLeftBottomLabel( g, xAxisLabel);
				if ( selectedAxis != null )
					paintRightTopLabel( g, selectedAxis);
				if ( selectedSecondaryAxis != null )
					paintLeftTopLabel( g, selectedSecondaryAxis);
				break;
			case UP:
				if ( xAxisLabel != null )
					paintLeftTopLabel( g, xAxisLabel);
				if ( selectedAxis != null )
					paintRightBottomLabel( g, selectedAxis);
				if ( selectedSecondaryAxis != null )
					paintRightTopLabel( g, selectedSecondaryAxis);
				break;
			case DOWN:
				if ( xAxisLabel != null )
					paintRightBottomLabel( g, xAxisLabel);
				if ( selectedAxis != null )
					paintLeftTopLabel( g, selectedAxis);
				if ( selectedSecondaryAxis != null )
					paintLeftBottomLabel( g, selectedSecondaryAxis);
				break;
			}
		}
	}
	/**
	 * Draws each curve of the scatterplot
	 * @param g graphics context used to perform the drawing.
	 */
	private void paintCurves( Graphics g)
	{
		Rectangle boundingBox = getBounds();
		if ( ( width != boundingBox.width) || (height != boundingBox.height) )
		{
			width = boundingBox.width;
			height = boundingBox.height;
			computeDimensions( g);
		}
		g.clearRect( 0, 0, width, height);
		AxisSystem axis = (AxisSystem) axisSystems.get( selectedAxis);
		if ( secondaryAxis && selectedSecondaryAxis != null )
		{
			axis = (AxisSystem) axisSystems.get( selectedSecondaryAxis);
		}
		paintAxisLabels( g);
//		g.clipRect( clipRectangle.x, clipRectangle.y, clipRectangle.width, clipRectangle.height);
		for( int n = 0; n < curves.size(); n++)
		{
			ScaledCurve c = (ScaledCurve) curves.elementAt( n);
			c.curve.plotCurve( g, (AxisSystem) axisSystems.get( c.scaleName));
		}
//		g.clipRect( 0, 0, width, height);
		axis.drawAxes( g, tickmarkSize, true);
		if ( secondaryAxis && selectedSecondaryAxis != null )
		{
			axis.drawSecondaryAxis( g, tickmarkSize, true);
		}
	}
	/**
	 * Draws the axis label located near the bottom left corner (if any)
	 * @param g graphics context used to perform the drawing.
	 * @param label text of the axis label
	 */
	private void paintLeftBottomLabel( Graphics g, String label)
	{
		Rectangle boundingBox = getBounds();
		FontMetrics fm = g.getFontMetrics();
		int x = Math.max( clipRectangle.x - fm.stringWidth( label), fm.getHeight());
		g.drawString( label, x, boundingBox.height - fm.getDescent());
	}
	/**
	 * Draws the axis label located near the top left corner (if any)
	 * @param g graphics context used to perform the drawing.
	 * @param label text of the axis label
	 */
	private void paintLeftTopLabel( Graphics g, String label)
	{
		FontMetrics fm = g.getFontMetrics();
		int x = Math.max( clipRectangle.x - fm.stringWidth( label), fm.getHeight());
		g.drawString( label, x, clipRectangle.y - fm.getDescent());
	}
	/**
	 * Draws the axis label located near the bottom right corner (if any)
	 * @param g graphics context used to perform the drawing.
	 * @param label text of the axis label
	 */
	private void paintRightBottomLabel( Graphics g, String label)
	{
		Rectangle boundingBox = getBounds();
		FontMetrics fm = g.getFontMetrics();
		int x = Math.min( boundingBox.width - fm.stringWidth( label) - fm.getHeight(), clipRectangle.x + clipRectangle.width);
		g.drawString( label, x, boundingBox.height - fm.getDescent());
	}
	/**
	 * Draws the axis label located near the top right corner (if any)
	 * @param g graphics context used to perform the drawing.
	 * @param label text of the axis label
	 */
	private void paintRightTopLabel( Graphics g, String label)
	{
		Rectangle boundingBox = getBounds();
		FontMetrics fm = g.getFontMetrics();
		int x = Math.min( boundingBox.width - fm.stringWidth( label) - fm.getHeight(), clipRectangle.x + clipRectangle.width);
		g.drawString( label, x, clipRectangle.y - fm.getDescent());
	}
	/**
	 * Finds the frame containing the scatterplot.
	 */
	private Frame parentFrame()
	{
		Component parent = getParent();
		while ( parent.getParent() != null)
			parent = parent.getParent();
		return (Frame) parent;
	}
	/**
	 * Removes a curve from the scatterplot. Curves are indexed in order
	 * of insertion starting from 0. The scatterplot is painted anew.
	 * @param index index of the curve to be removed.
	 */
	public void removeCurve( int index) throws ArrayIndexOutOfBoundsException
	{
		removeCurveNoUpdate( index);
		reset();
	}
/**
 * Removes a given curve definition from the receiver and repaint it.
 * If the curve is not part of the receiver, nothing is done.
 * @param curve HistogramOrCurveDefinition		curve to be removed.
 */
public void removeCurve( HistogramOrCurveDefinition curve)
{
	if ( removeCurveNoUpdate( curve))
		reset();
	return;
}
	/**
	 * Removes a curve from the scatterplot. Curves are indexed in order
	 * of insertion starting from 0. The scatterplot display is not updated.
	 * @param index index of the curve to be removed.
	 */
	public void removeCurveNoUpdate( int index) throws ArrayIndexOutOfBoundsException
	{
		ScaledCurve removedCurve = (ScaledCurve) curves.elementAt( index);
		curves.removeElementAt( index);
		boolean obsoleteAxes = true;
		for ( int n = 0; n < curves.size(); n++ )
		{
			if ( ( (ScaledCurve) curves.elementAt( n)).scaleName.equals( removedCurve.scaleName) )
				obsoleteAxes = false;
		}
		if ( obsoleteAxes )
			axisSystems.remove( removedCurve.scaleName);
	}
/**
 * Removes a curve definition from the receiver.
 * @param curve DhbScientificCurves.HistogramOrCurveDefinition
 */
public boolean removeCurveNoUpdate( HistogramOrCurveDefinition curve)
{
	for( int n = 0; n < curves.size(); n++)
	{
		ScaledCurve c = (ScaledCurve) curves.elementAt( n);
		if ( c.curve == curve )
		{
			removeCurveNoUpdate( n);
			return true;
		}	
	}
	return false;
}
	/**
	 * Froces a redraw of the system. The clip rectangle is also recomputed.
	 */
	public void reset()
	{
		width = 0;
		repaint();
	}
/**
 * Answers an array containing a sampling range to obtain enough points across the X axis.
 * @param sampling distance in pixels between the sampling points.
 * @return range an array of 3 doubles; range[0] minimum x value, range[1] maximum x value, range[2] x step.
 */
public double[] samplingRange ( int sampling)
{
	return xScale().samplingRange ( sampling);
}
	/**
	 * Defines the font used to draw the axis labels.
	 * @param font the new font.
	 */
	public void setAxisLabelFont( Font font)
	{
		axisLabelFont = font;
	}
	/**
	 * Defines the width or height of the sentitive area around the y axes
	 * (primary or secondary).
	 * @param n width or height in pixels.
	 */
	public void setClickTolerance( int n)  throws IllegalArgumentException 
	{
		if ( n <= 0 )
			throw new IllegalArgumentException( "Non-positive click tolerance: "+n);
		clickTolerance = n;
	}
/**
 * Default parameter setting. This method is called by the constructor methods.
 */
private void setDefaultSettings()
{
	addMouseListener( this);
	addMouseMotionListener( this);
	setOrientation( RIGHT, true);
	setTickmarkSize( 5);
	setClickTolerance( 2);
	setSecondaryAxis( false);
	axisSystems = new Hashtable();
}
	/**
	 * Defines the orientation of the axis system.
	 * @param xAxis orientation of the x axis: LEFT, RIGHT, UP or DOWN.
	 * @param cartesian true if the y axis makes a positive angle with the x axis.
	 */
	public void setOrientation( int xAxis, boolean cartesian)
	{
		xAxisOrientation = xAxis;
		cartesianAxes = cartesian;
	}
	/**
	 * Defines whether or not a secondary y axis is drawn at the end of the x axis.
	 * @param secondary	true if a secondary must be drawn.
	 */
	public void setSecondaryAxis( boolean secondary)
	{
		secondaryAxis = secondary;
		reset();
	}
	/**
	 * Defines the font used to draw the tickmark labels.
	 * @param font the new font.
	 */
	public void setTickmarkLabelFont( Font font)
	{
		tickmarkLabelFont = font;
	}
	/**
	 * Defines the size of the tickmarks.
	 * @param size the tickmark size in pixels.
	 */
	public void setTickmarkSize( int size)
	{
		tickmarkSize = size;
		reset();
	}
	/**
	 * Defines the label of the x axis.
	 * @param label	the new label.
	 */
	public void setXAxisLabel( String label)
	{
		xAxisLabel = label;
	}
	/**
	 * Destroys the tracking window used to display mouse coordinates.
	 * @see TrackingWindow
	 */
	private void terminateTracking()
	{
		trackingWindow.dispose();
		trackingWindow = null;
		setCursor( currentCursor);
		currentCursor = null;
	}
	/**
	 * Finds the next axis suitable for primary axis and set the primary axis to it.
	 */
	private void toggleAxis()
	{
		boolean startPointMet = false;
		String newAxis = null;
		for( Enumeration keys = axisSystems.keys(); keys.hasMoreElements(); )
		{
			String nextName = (String) keys.nextElement();
			if ( selectedAxis.equals( nextName) )
				startPointMet = true;
			else if ( !selectedSecondaryAxis.equals( nextName) )
			{
				if ( startPointMet )
				{
					newAxis = nextName;
					startPointMet = false;
				}
				else if ( newAxis == null)
					newAxis = nextName;
			}
		}
		if ( newAxis != null )
		{
			selectedAxis = newAxis;
			repaint();
		}
	}
	/**
	 * Finds the next axis suitable for secondary axis and set the secondary axis to it.
	 */
	private void toggleSecondaryAxis()
	{
		boolean startPointMet = false;
		String newAxis = null;
		for( Enumeration keys = axisSystems.keys(); keys.hasMoreElements(); )
		{
			String nextName = (String) keys.nextElement();
			if ( selectedSecondaryAxis.equals( nextName) )
				startPointMet = true;
			else if ( !selectedAxis.equals( nextName) )
			{
				if ( startPointMet )
				{
					newAxis = nextName;
					startPointMet = false;
				}
				else if ( newAxis == null)
					newAxis = nextName;
			}
		}
		if ( newAxis != null )
		{
			selectedSecondaryAxis = newAxis;
			repaint();
		}
	}
	/**
	 * Displays the contents of the tracking window.
	 * @param x pixel x position of the mouse.
	 * @param y pixel y position of the mouse.
	 */
	private void trackCoordinates( int x, int y)
	{
		Point pt = absolutePoint( x, y);
		trackingWindow.setText( trackingWindowText( x, y), pt.x, pt.y);
	}
	/**
	 * Constructs the contents of the tracking window.
	 * Each curve is given a chance to return its own text, then a default text
	 * showing the coordinate of the mouse point using the primary axis system is returned.
	 * @param x pixel x position of the mouse.
	 * @param y pixel y position of the mouse.
	 * @return a text to be displayed in the tracking window.
	 */
	private String trackingWindowText( int x, int y)
	{
		String answer;
		for( int n = 0; n < curves.size(); n++)
		{
			ScaledCurve c = (ScaledCurve) curves.elementAt( n);
			answer = c.curve.trackingWindowText(x, y, (AxisSystem) axisSystems.get( c.scaleName));
			if ( answer != null)
				return answer;
		}
		AxisSystem axis = (AxisSystem) axisSystems.get( selectedAxis);
		double[] v = axis.pointToCoordinates( x, y);
		return "x: "+v[0]+"\ny: "+v[1];
	}
	/**
	 * Forces a new computation of the scales of all axis systems of the
	 * scatterplot and repaint it anew.
	 */
	public void updateScale()
	{
		for ( int n = 0; n < curves.size(); n++)
		{
			ScaledCurve c = (ScaledCurve) curves.elementAt( n);
			AxisSystem axis = (AxisSystem) axisSystems.get( c.scaleName);
			double[] range = c.curve.getRange();
			if ( range != null )
			{
				axis.xScale.addRange( range[0], range[1]);
				axis.yScale.addRange( range[2], range[3]);
			}	
		}
		reset();
	}
	/**
	 * Returns the scale for the x axis. Since all axis systems have a common x axis,
	 * the x axis of the first axis system is used.
	 * Note: there is no protection against empty axis system because this method is never
	 * called if the axis system is empty.
	 * @see #paintCurves
	 * @return scale of the x axis
	 */
	private PlottingScale xScale()
	{
		Enumeration e = axisSystems.elements();
		AxisSystem firstAxis = (AxisSystem) e.nextElement();
		return firstAxis.xScale;
	}
}
\end{verbatim}