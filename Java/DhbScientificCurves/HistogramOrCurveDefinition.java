\begin{verbatim}
package DhbScientificCurves;


import java.awt.Graphics;
/**
 * HistogramOrCurveDefinition is an interface used by the class Scatterplot.
 * @see Scatterplot
 *
 * A HistogramOrCurveDefinition has the responsibility of handling the drawing
 * of curves or histograms on the scatterplot.
 *
 * @version 1.0 23 Jul 1998
 * @author Didier H. Besset
 */
public interface HistogramOrCurveDefinition
{


	/**
	 * Returns the range of values to be plotted.
	 * @return An array of 4 double values as follows
	 * index 0: minimum of X range
	 *       1: maximum of X range
	 *       2: minimum of Y range
	 *       3: maximum of Y range
	 */
	public double[] getRange( );
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
	public boolean handleMouseClick( int x, int y, AxisSystem axes);
	/**
	 * Draws the histogram or curve on the specified axis system.
	 * @see AxisSystem
	 * @param g the graphics context used for drawing.
	 * @param axes the axis system.
	 */
	public void plotCurve( Graphics g, AxisSystem axes);
	/**
	 * Returns a text to be displayed in the tracking window for a given mouse location.
	 * The supplied axis system is the axis system used to draw the curve.
	 * @see AxisSystem
	 * @param x pixel x position of the mouse.
	 * @param y pixel y position of the mouse.
	 * @param axes the axis system.
	 * @return text to display in tracking window.
	 */
	public String trackingWindowText( int x, int y, AxisSystem axes);
}
\end{verbatim}