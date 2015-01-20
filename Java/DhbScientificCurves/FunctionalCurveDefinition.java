\begin{verbatim}
package DhbScientificCurves;


import DhbInterfaces.OneVariableFunction;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Point;
/**
 * A FunctionalCurveDefinition defines the graphical representation of a function.
 * It defines the graphical rendition of the curve on a scatterplot.
 * The curve is drawn using a given color.
 * @see Scatterplot
 * @see CurveMouseClickListener
 *
 *
 * @version 1.0 06 Aug 1998
 * @author Didier H. Besset
 */
public class FunctionalCurveDefinition implements DhbScientificCurves.HistogramOrCurveDefinition
{
	/**
	 * Function to draw.
	 */
	public DhbInterfaces.OneVariableFunction f;
	/**
	 * Sampling rate in pixels.
	 */
	private int sampling;
	/**
	 * Color used to draw the function.
	 * Default is the color of the symbols if drawn or the current color of the graphic context..
	 */
	private Color lineColor;

/**
 * Generic constructor method: sampling rate is 10 pixels.
 * @param func the function to be drawn.
 */
public FunctionalCurveDefinition ( OneVariableFunction func)
{
	this( func, 10);
}
/**
 * Constructor method for given sampling rate.
 * @param func the function to be drawn.
 * @param samplingRate distance in pixels between computed points.
 */
public FunctionalCurveDefinition( OneVariableFunction func, int samplingRate) throws IllegalArgumentException
{
		if ( samplingRate <= 0 )
			throw new IllegalArgumentException( "Non-positive sampling rate: "+samplingRate);
	sampling = samplingRate;
	f = func;
}
/**
 * Functional curve definition have no range.
 * They must be plotted on an already defined axis system.
 */
public double[] getRange()
{
	return null;
}
/**
 * Mouse clicks are not handled by curves.
 */
public boolean handleMouseClick(int x, int y, AxisSystem axes)
{
	return false;
}
/**
 * plotCurve method comment.
 */
public void plotCurve(java.awt.Graphics g, AxisSystem axes)
{
	Color oldColor = g.getColor();
	if ( lineColor != null )
	{
		if ( !lineColor.equals( oldColor) )
		{
			g.setColor( lineColor);
		}	
	}
	double[] xRange = axes.xScale.samplingRange( sampling);
	double x = xRange[0];
	Point oldP;
	try { oldP = axes.coordinatesToPoint( x, f.value(x));}
		catch ( ArithmeticException e) { oldP = null;};
	while( x <= xRange[1])
	{
		try {  Point p = axes.coordinatesToPoint( x, f.value(x));
			   if ( oldP != null)
			   		g.drawLine( oldP.x, oldP.y, p.x, p.y);
				oldP = p;
			} catch ( ArithmeticException e) { oldP = null;};
		x += xRange[2];
	}
	if ( !oldColor.equals( g.getColor()) )
		g.setColor( oldColor);
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
	 * Returns null so that the default tracking window display provided by the scatterplot is used.
	 * @see Scatterplot
	 * @param x pixel x position of the mouse.
	 * @param y pixel y position of the mouse.
	 * @param axes the axis system.
 */
public String trackingWindowText(int x, int y, AxisSystem axes)
{
	return null;
}
}
\end{verbatim}