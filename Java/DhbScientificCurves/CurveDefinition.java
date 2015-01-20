\begin{verbatim}
package DhbScientificCurves;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import DhbInterfaces.CurveMouseClickListener;
import DhbInterfaces.PointSeries;
/**
 * A CurveDefinition defines the graphical representation of a series of points.
 * It defines the graphical rendition of the points on a scatterplot.
 * It handles the drawing of the points on behalf of the scatterplot.
 * An optional symbol is drawn at the location of each point, using a given color.
 * An optional line is drawn between each point using a given color.
 * An object implementing the CurveMouseClickListener interface can be defined to handle
 * mouse click on each point.
 * @see Scatterplot
 * @see CurveMouseClickListener
 *
  *
 * @version 1.0 23 Jul 1998
 * @author Didier H. Besset
*/
public class CurveDefinition implements HistogramOrCurveDefinition
{
	/**
	 * Points to be plotted.
	 */
	private PointSeries points;
	/**
	 * Color used to draw each point.
	 * Default is the current color of the graphic context.
	 */
	private Color pointColor;
	/**
	 * Symbol used to draw each point.
	 * Default is CROSS.
	 */
	private AbstractSymbolDrawer symbolDrawer;
	/**
	 * Color used to draw the line between points.
	 * Default is the color of the symbols if drawn or the current color of the graphic context..
	 */
	private Color lineColor;
	/**
	 * Type of line drawn between each point.
	 * Default is NO_LINE.
	 */
	private int lineType;
	/**
	 * Size of the symbol drawn at each point in pixels.
	 * Default is 5.
	 */
	private int symbolSize;
	/**
	 * Object in charge of processing mouse clicks on a point (mouse listener).
	 * Default is null (no defined object).
	 */
	private CurveMouseClickListener listener = null;
	/**
	 * Parameter passed to the mouse listener when processing mouse clicks on a point.
	 * This can be used to distinguish between different curves having the same mouse listener.
	 */
	private Object listenerParameter;
	/**
	 * Symbol identifier for no symbols.
	 */
	public static final int NO_SYMBOL = 0;
	/**
	 * Symbol identifier for a vertical cross.
	 */
	public static final int CROSS = 1;
	/**
	 * Symbol identifier for an oblique cross.
	 */
	public static final int XCROSS = 2;
	/**
	 * Symbol identifier for a circle.
	 */
	public static final int CIRCLE = 3;
	/**
	 * Symbol identifier for a filled circle.
	 */
	public static final int FILLED_CIRCLE = 4;
	/**
	 * Symbol identifier for a square.
	 */
	public static final int SQUARE = 5;
	/**
	 * Symbol identifier for a filled square.
	 */
	public static final int FILLED_SQUARE = 6;
	/**
	 * Symbol identifier for a diamond.
	 */
	public static final int DIAMOND = 7;
	/**
	 * Symbol identifier for a filled diamond.
	 */
	public static final int FILLED_DIAMOND = 8;
	/**
	 * Symbol identifier for a triangle.
	 */
	public static final int TRIANGLE = 9;
	/**
	 * Symbol identifier for a filled triangle.
	 */
	public static final int FILLED_TRIANGLE = 10;
	/**
	 * Symbol identifier for an upside down triangle.
	 */
	public static final int DOWN_TRIANGLE = 11;
	/**
	 * Symbol identifier for a filled upside down triangle.
	 */
	public static final int FILLED_DOWN_TRIANGLE = 12;
	/**
	 * Line identifier for no line.
	 */
	public static final int NO_LINE = 0;
	/**
	 * Line identifier for full line.
	 */
	public static final int FULL_LINE = 1;


	/**
	 * Constructor method.
	 * @param pts the series of points to be plotted.
	 * @see PointSeries
	 */
	public CurveDefinition( PointSeries pts)
	{
		points = pts;
		symbolDrawer = new CrossSymbolDrawer();
		lineType = NO_LINE;
		setSymbolSize( 5);
	}
	/**
	 * Returns the range of values to be plotted.
	 * @return An array of 4 double values as follows
	 * index 0: minimum of X range
	 *       1: maximum of X range
	 *       2: minimum of Y range
	 *       3: maximum of Y range
	 */
	public double[] getRange( )
	{
		double x;
		double y;
		double[] range = new double[4];
		range[0] = points.xValueAt(0);
		range[1] = range[0];
		range[2] = points.yValueAt(0);
		range[3] = range[2];
		for( int n = 1; n < points.size(); n++ )
		{
			x = points.xValueAt(n);
			if ( x < range[0] )
				range[0] = x;
			else if ( x > range[1] )
				range[1] = x;
			x = points.yValueAt(n);
			if ( x < range[2] )
				range[2] = x;
			else if ( x > range[3] )
				range[3] = x;
		}
		return range;
	}
	/**
	 * Processes the mouse clicks receives from the scatterplot.
	 * If a mouse listener has been defined, each point is tested for mouse click.
	 * If the mouse click falls within the symbol size of a point, the index of
	 * that point is passed to the mouse listener, along with the defined parameter.
	 * @see #setMouseListener
	 * @param x pixel x location of the mouse click.
	 * @param y pixel y location of the mouse click.
	 * @param axes axis system used to draw the curve.
	 */
	public boolean handleMouseClick( int x, int y, AxisSystem axes)
	{
		if ( listener != null )
		{
			Point p;
			for ( int n = 0; n < points.size(); n++)
			{
				p = axes.coordinatesToPoint( points.xValueAt(n), points.yValueAt(n));
				if ( (new Rectangle( p.x - symbolSize / 2, p.y - symbolSize / 2,
								symbolSize, symbolSize)).contains( x, y) )
				{
					return listener.handleMouseClick( n, listenerParameter);
				}
			}
		}
		return false;
	}
	/**
	 * Draws the curve.
	 * @param g graphics context used to perform the drawing.
	 * @param axes axis system used to draw the curve.
	 */
	public void plotCurve( Graphics g, AxisSystem axes)
	{
		Color oldColor = g.getColor();
		plotPoints( g, axes);
		plotLine( g, axes);
		if ( !oldColor.equals( g.getColor()) )
			g.setColor( oldColor);
	}
	/**
	 * Draws a line between each point of the curve.
	 * @param g graphics context used to perform the drawing.
	 * @param axes axis system used to draw the curve.
	 */
	private void plotLine( Graphics g, AxisSystem axes)
	{
		if ( lineType == NO_LINE )
			return;
		if ( lineColor != null )
		{
			if ( !lineColor.equals( g.getColor()) )
				g.setColor( lineColor);
		}
		int n = 0;
		Point oldP = axes.coordinatesToPoint( points.xValueAt(n), points.yValueAt(n));
		while ( ++n < points.size() )
		{
			Point p = axes.coordinatesToPoint( points.xValueAt(n), points.yValueAt(n));
			g.drawLine( oldP.x, oldP.y, p.x, p.y);
			oldP = p;
		}
	}
	/**
	 * Draws each point of the curve.
	 * @param g graphics context used to perform the drawing.
	 * @param axes axis system used to draw the curve.
	 */
	private void plotPoints( Graphics g, AxisSystem axes)
	{
		if ( symbolDrawer == null )
			return;
		if ( pointColor != null )
		{
			if ( !pointColor.equals( g.getColor()) )
				g.setColor( pointColor);
		}
		Point p;
		double f;
		for ( int n = 0; n < points.size(); n++)
		{
			f = points.yValueAt(n);
			if ( !Double.isNaN( f) && !Double.isInfinite( f))
			{
				p = axes.coordinatesToPoint( points.xValueAt(n), f);
				symbolDrawer.plotSymbol( g, p.x, p.y, symbolSize);
			}	
		}
	}
	/**
	 * Defines the color of the line drawn between each point.
	 * @param color color used to draw each point.
	 */
	public void setLineColor( Color color)
	{
		lineColor = color;
	}
	/**
	 * Defines the type of line drawn between each point.
	 * @param type line identifier (use the predefined symbol types).
	 */
	public void setLineType( int type)
	{
		lineType = type;
	}
	/**
	 * Defines the mouse listener for the curve, that is the object in charge of 
	 * processing mouse clicks on each point.
	 * The same mouse listener can be used for several curve, each curve can be
	 * identified by a different parameter.
	 * The mouse listener must implement the CurveMouseClickListener interface.
	 * @see CurveMouseClickListener
	 * @param aListener a mouse listener object.
	 * @param param an object used to identify the curve.
	 */
	public void setMouseListener( CurveMouseClickListener aListener, Object param)
	{
		listener = aListener;
		listenerParameter = param;
	}
	/**
	 * Defines the color of the symbol drawn at each point.
	 * @param color color used to draw each point.
	 */
	public void setSymbolColor( Color color)
	{
		pointColor = color;
	}
	/**
	 * Defines the size of the symbol drawn at each point.
	 * @param size symbol size in pixels.
	 */
	public void setSymbolSize( int size)
	{
		symbolSize = size;
	}
	/**
	 * Defines the type of the symbol drawn at each point.
	 * @param type symbol identifier (use the predefined symbol types).
	 */
	public void setSymbolType( int type)
	{
		switch ( type)
		{
		case CROSS:
			symbolDrawer = new CrossSymbolDrawer();
			break;
		case XCROSS:
			symbolDrawer = new XCrossSymbolDrawer();
			break;
		case CIRCLE:
			symbolDrawer = new CircleSymbolDrawer();
			break;
		case FILLED_CIRCLE:
			symbolDrawer = new FilledCircleSymbolDrawer();
			break;
		case SQUARE:
			symbolDrawer = new SquareSymbolDrawer();
			break;
		case FILLED_SQUARE:
			symbolDrawer = new FilledSquareSymbolDrawer();
			break;
		case DIAMOND:
			symbolDrawer = new DiamondSymbolDrawer();
			break;
		case FILLED_DIAMOND:
			symbolDrawer = new FilledDiamondSymbolDrawer();
			break;
		case TRIANGLE:
			symbolDrawer = new TriangleSymbolDrawer();
			break;
		case FILLED_TRIANGLE:
			symbolDrawer = new FilledTriangleSymbolDrawer();
			break;
		case DOWN_TRIANGLE:
			symbolDrawer = new DownTriangleSymbolDrawer();
			break;
		case FILLED_DOWN_TRIANGLE:
			symbolDrawer = new FilledDownTriangleSymbolDrawer();
			break;
		default:
			symbolDrawer = null;
			break;
		}
	}
	/**
	 * Returns null so that the default tracking window display provided by the scatterplot is used.
	 * @see Scatterplot
	 * @param x pixel x position of the mouse.
	 * @param y pixel y position of the mouse.
	 * @param axes the axis system.
	 */
	public String trackingWindowText( int x, int y, AxisSystem axes)
	{
		return null;
	}
}
\end{verbatim}