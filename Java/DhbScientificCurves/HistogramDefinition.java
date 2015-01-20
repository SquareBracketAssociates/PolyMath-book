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
 * A HistogramDefinition handles the plotting of a histogram onto a scatterplot.
 *
 *
 * @version 1.0 24 Jul 1998
 * @author Didier H. Besset
 */
public class HistogramDefinition implements HistogramOrCurveDefinition
{
	/**
	 * Histogram to be plotted by the receiver.
	 */
	private Histogram histogram;
	/**
	 * Color used to draw the outline of the histogram.
	 * Default is the current color of the graphic context.
	 */
	private Color lineColor;
	/**
	 * Color used to draw the inside of the histogram.
	 * Default is none.
	 */
	private Color patternColor;

	/**
	 * Constructor method.
	 * @param histogram the histogram to be plotted.
	 * @see Histogram
	 */
	public HistogramDefinition( Histogram hist)
	{
		histogram = hist;
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
		return histogram.getRange();
	}
	/**
	 * Processes the mouse click (left mouse button up) which occured
	 * at location (x,y). This location can be converted to value using
	 * the supplied axis system parameter.
	 * @see AxisSystem
	 * @param x x location of the mouse click.
	 * @param y y location of the mouse click.
	 * @param axes the axis system.
	 * @return true if the mouse click was handled, false otherwise.
	 */
	public boolean handleMouseClick( int x, int y, AxisSystem axes)
	{
		return false;
	}
	/**
	 * Draws the histogram or curve on the specified axis system.
	 * @see AxisSystem
	 * @param g the graphics context used for drawing.
	 * @param axes the axis system.
	 */
	public void plotCurve( Graphics g, AxisSystem axes)
	{
		Color oldColor = g.getColor();
		double bin;
		double value = histogram.getMinimum();
		Point pt = axes.coordinatesToPoint( value, 0);
		Polygon contour = new Polygon();
		contour.addPoint( pt.x, pt.y);
		for ( int n = 0; n < histogram.getDimension(); n++)
		{
			bin = histogram.yValueAt( n);
			pt = axes.coordinatesToPoint( value, bin);
			contour.addPoint( pt.x, pt.y);
			value += histogram.getBinWidth();
			pt = axes.coordinatesToPoint( value, bin);
			contour.addPoint( pt.x, pt.y);
		}
		pt = axes.coordinatesToPoint( value, 0);
		contour.addPoint( pt.x, pt.y);
		if ( patternColor != null )
		{
			if ( !patternColor.equals( oldColor) )
			{
				g.setColor( patternColor);
			}
			g.fillPolygon( contour);
		}	
		if ( lineColor != null )
		{
			if ( !lineColor.equals( oldColor) )
			{
				g.setColor( lineColor);
			}	
		}
		g.drawPolygon( contour);
		if ( !oldColor.equals( g.getColor()) )
			g.setColor( oldColor);
	}
	/**
	 * Defines the color of the outline.
	 * @param color color used to draw each point.
	 */
	public void setLineColor( Color color)
	{
		lineColor = color;
	}
	/**
	 * Defines the color of the inside of the histogram.
	 * @param color color used to draw each point.
	 */
	public void setPatternColor( Color color)
	{
		patternColor = color;
	}
	/**
	 * Returns a text to be displayed in the tracking window for a given mouse location.
	 * The supplied axis system is the axis system used to draw the curve.
	 * @see AxisSystem
	 * @param x pixel x position of the mouse.
	 * @param y pixel y position of the mouse.
	 * @param axes the axis system.
	 */
	public String trackingWindowText( int x, int y, AxisSystem axes)
	{
		double[] xy = axes.pointToCoordinates( x, y);
		double[] binParams = histogram.getBinParameters( xy[0]);
		if ( binParams != null )
		{
			if ( xy[1] <= binParams[2] )
			{
				return "Bin: ["+binParams[0]+", "+binParams[1]+"[\n"
						+ "Count: "+binParams[2];
			}
		}
		return null;
	}
}
\end{verbatim}