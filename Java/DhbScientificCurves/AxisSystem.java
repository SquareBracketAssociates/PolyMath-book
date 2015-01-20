\begin{verbatim}
package DhbScientificCurves;


import java.awt.Graphics;
import java.awt.Point;
/**
 * An AxisSystem defines a system of axes to draw 2-dimensional curves.
 * Definition of the axis system should be left to the scatterplot.
 * End-user of the class should only the facilities performing
 * coordinate transformations.
 *
 *
 * @version 1.0 23 Jul 1998
 * @author Didier H. Besset
 */
public class AxisSystem
{
	/**
	 * Scale of the x axis.
	 */
	protected PlottingScale xScale;
	/**
	 * Scale of the y axis.
	 */
	protected PlottingScale yScale;
	/**
	 * Origin of the axes in pixel.
	 */
	private Point axisOrigin;
	/**
	 * Flag used to mark an horizontal x axis.
	 * Default: true
	 */
	private boolean xAxisHorizontal;


	/**
	 * Constructor method for horizontal x Axis.
	 * @param xS scale of the x axis.
	 * @param yS scale of the y axis.
	 */
	public AxisSystem( PlottingScale xS, PlottingScale yS)
	{
		this( xS, yS, true);
	}
	/**
	 * General constructor method.
	 * @param xS scale of the x axis.
	 * @param yS scale of the y axis.
	 * @param horizontal true if the x axis in horizontal.
	 */
	public AxisSystem( PlottingScale xS, PlottingScale yS, boolean horizontal)
	{
		xScale = xS;
		yScale = yS;
		xAxisHorizontal = horizontal;
	}
	/**
	 * Compute the point reprensenting the specified 2-dimensional coordinates in pixel coordinates.
	 * @param x 2-dimensional x coordinate.
	 * @param y 2-dimensional y coordinate.
	 * @return corresponding point in pixel.
	 */
	public Point coordinatesToPoint( double x, double y)
	{
		return xAxisHorizontal ? new Point( xScale.valueToPixels(x, axisOrigin.x), yScale.valueToPixels(y, axisOrigin.y))
							   : new Point( yScale.valueToPixels(y, axisOrigin.x), xScale.valueToPixels(x, axisOrigin.y));
	}
	/**
	 * Draws the axes.
	 * @param g graphics context used to perform the drawing.
	 * @param tickmarkSize size of the tick marks in pixels.
	 * @param labels true if the tickmark labels must be drawn.
	 */
	public void drawAxes( Graphics g, int tickmarkSize, boolean labels)
	{
		if ( xAxisHorizontal )
		{
			xScale.drawHorizontalAxis( g, axisOrigin, tickmarkSize, yScale.getInvertedAxis(), labels);
			yScale.drawVerticalAxis( g, axisOrigin, tickmarkSize, !xScale.getInvertedAxis(), labels);
		}
		else
		{
			xScale.drawVerticalAxis( g, axisOrigin, tickmarkSize, !yScale.getInvertedAxis(), labels);
			yScale.drawHorizontalAxis( g, axisOrigin, tickmarkSize, xScale.getInvertedAxis(), labels);
		}
	}
	/**
	 * Draws a y axis at the extremity of the x axis.
	 * @param g graphics context used to perform the drawing.
	 * @param tickmarkSize size of the tick marks in pixels.
	 * @param labels true if the tickmark labels must be drawn.
	 */
	public void drawSecondaryAxis( Graphics g, int tickmarkSize, boolean labels)
	{
		if ( xAxisHorizontal )
		{
			Point origin = new Point( xScale.getInvertedAxis() ? axisOrigin.x - xScale.getAxisLength()
															   : axisOrigin.x + xScale.getAxisLength(),
									  axisOrigin.y);
			yScale.drawVerticalAxis( g, origin, tickmarkSize, xScale.getInvertedAxis(), labels);
		}
		else
		{
			Point origin = new Point( axisOrigin.x,
									  xScale.getInvertedAxis() ? axisOrigin.y - xScale.getAxisLength()
															   : axisOrigin.y + xScale.getAxisLength());
			yScale.drawHorizontalAxis( g, origin, tickmarkSize, !yScale.getInvertedAxis(), labels);
		}
	}
	/**
	 * Compute the 2-dimensional coordinates reprensented by the specified point in pixel coordinates.
	 * @param x pixel x coordinate.
	 * @param y pixel y coordinate.
	 * @return a 2-dimensional double array containing the coordinates (0: x, 1: y).
	 */
	public double[] pointToCoordinates( int x, int y)
	{
		double[] answer = new double[2];
		if ( xAxisHorizontal )
		{
			answer[0] = xScale.pixelsToValue( x, axisOrigin.x);
			answer[1] = yScale.pixelsToValue( y, axisOrigin.y);
		}
		else
		{
			answer[0] = xScale.pixelsToValue( y, axisOrigin.y);
			answer[1] = yScale.pixelsToValue( x, axisOrigin.x);
		}
		return answer;
	}
	/**
	 * Compute the 2-dimensional coordinates reprensented by the specified point in pixel coordinates.
	 * @param p a point in pixel.
	 * @return a 2-dimensional double array containing the coordinates (0: x, 1: y).
	 */
	public double[] pointToCoordinates( Point p)
	{
		return pointToCoordinates( p.x, p.y);
	}
	/**
	 * Defines the dimensions of the axes.
	 * @param xL length of the x axis in pixel.
	 * @param yL length of the y axis in pixel.
	 */
	public void setLengths( int xL, int yL)
	{
		if ( xAxisHorizontal)
		{
			xScale.setAxisLength( xL);
			yScale.setAxisLength( yL);
		}
		else
		{
			xScale.setAxisLength( yL);
			yScale.setAxisLength( xL);
		}
	}
	/**
	 * Defines the common origin of the axes.
	 * @param pt axes origin in pixel coordinates.
	 */
	public void setOrigin( Point pt)
	{
		axisOrigin = pt;
	}
}
\end{verbatim}