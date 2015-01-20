\begin{verbatim}
package DhbScientificCurves;


import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import DhbGUIUtilityClasses.DhbFloatingFormat;
/**
 * A PlottingScale defines a scale used to convert double values
 * into pixels and back.
 * The scale is adjusted so that tick mark are using rounded numbers.
 * Definition of the scale should be left to the scatterplot.
 * End-user of the class should only the facilities performing
 * coordinate transformations.
 *
  *
 * @version 1.0 23 Jul 1998
 * @author Didier H. Besset
*/
public class PlottingScale
{
	/**
	 * Scale range in pixel.
	 */
	private int axisLength;
	/**
	 * Minimum spacing between tickmarks in pixels.
	 */
	private int minimumTickmarkSpacing;
	/**
	 * Actual spacing between tickmarks in pixels.
	 */
	private int tickmarkSpacing;
	/**
	 * Minimum value.
	 */
	private double minimum;
	/**
	 * Maximum value.
	 */
	private double maximum;
	/**
	 * Actual minimum value obtained after rounding to nearest tickmark.
	 */
	private double scaleMinimum;
	/**
	 * Actual scale range after rounding to nearest tickmark.
	 */
	private double scaleRange;
	/**
	 * Actual step between tickmark.
	 */
	private double tickmarkStep;
	/**
	 * Flag indicating axis inversion.
	 */
	private boolean inverted;
	/**
	 * Flag indicating that tick marks should be rounded to integer values.
	 */
	private boolean integerValued;
	/**
	 * Format used to print tick mark labels.
	 */
	private DhbFloatingFormat format = new DhbFloatingFormat();
	/**
	 * Scale values.
	 */
	private static final double scales[] = {1.25, 2, 2.5, 4, 5, 7.5, 8, 10};
	private static final double semiIntegerScales[] = {2, 2.5, 4, 5, 7.5, 8, 10};
	private static final double integerScales[] = {2, 4, 5, 8, 10};


	/**
	 * Constructor method.
	 */
	public PlottingScale()
	{
		inverted = false;
		integerValued = false;
		minimumTickmarkSpacing = 30;
		minimum = 0;
		maximum = 0;
		reset();
	}
	/**
	 * Extends the range of the scale. The range is modified only when the
	 * new parameters are extending beyond the old range.
	 * @param from possible minimum value of new range.
	 * @param to possible maximum value of new range.
	 */
	public void addRange( double from, double to) throws IllegalArgumentException
	{
		if ( minimum == maximum )
		{
			try { setRange( from, to);}
				catch ( IllegalArgumentException e) { throw e;}
		}
		else
		{
			if ( from < minimum )
			{
				minimum = from;
				if ( minimum < scaleMinimum )
					reset();
			}
			if ( maximum < to )
			{
				maximum = to;
				if ( maximum - scaleMinimum > scaleRange )
					reset();
			}
		}
	}
	/**
	 * Computes the scale.
	 */
	private void computeScale()
	{
		int n = (int) Math.floor( (double) axisLength / (double) minimumTickmarkSpacing);
		tickmarkSpacing = axisLength / n;
		int remainder = axisLength % n;
		for ( int i = n - 1; i > 1; i-- )
		{
			int r = axisLength % i;
			if ( r < remainder )
			{
				remainder = r;
				tickmarkSpacing = axisLength / i;
			}
		}
		double step = ( maximum - minimum) * tickmarkSpacing / axisLength;
		tickmarkStep = PlottingScale.roundToScale( step, integerValued);
		scaleMinimum = tickmarkStep * Math.floor( minimum / tickmarkStep);
		scaleRange = tickmarkStep * Math.ceil( ( maximum - scaleMinimum) / tickmarkStep);
	}
	/**
	 * Draws an horixontal axis with the current scale.
	 * @param g the graphic context used to perform all drawing command.
	 * @param origin a point in pixel where the axis must begin.
	 * @param tickMarkSize size of the tick marks in pixel
	 * @param bottom flag indicating whether the axis is drawn at the bottom of the pane.
	 */
	public void drawHorizontalAxis( Graphics g, Point origin, int tickmarkSize, boolean bottom)
	{
		drawHorizontalAxis( g, origin, tickmarkSize, bottom, true);
	}
	/**
	 * Draws an horixontal axis with the current scale.
	 * @param g the graphic context used to perform all drawing command.
	 * @param origin a point in pixel where the axis must begin.
	 * @param tickMarkSize size of the tick marks in pixel
	 * @param bottom flag indicating whether the axis is drawn at the bottom of the pane.
	 * @param labels flag indicating whether tickmark labels mustbe drawn.
	 */
	public void drawHorizontalAxis( Graphics g, Point origin, int tickmarkSize, boolean bottom, boolean labels)
	{
		int delta;
		int h1;
		int h2;
		Point pt;
		if ( undefined() )
			computeScale();
		if ( inverted )
		{
			h1 = origin.x - axisLength;
			pt = new Point( h1, origin.y);
			delta = pt.x + tickmarkSize * 2;
		}
		else
		{
			h1 = origin.x + axisLength;
			pt = new Point( h1, origin.y);
			delta = pt.x - tickmarkSize * 2;
		}
		g.drawLine( origin.x, origin.y, pt.x, pt.y);
		g.drawLine( pt.x, pt.y, delta, pt.y - tickmarkSize);
		g.drawLine( pt.x, pt.y, delta, pt.y + tickmarkSize);
		FontMetrics fm = g.getFontMetrics();
		if ( bottom )
		{
			h1 = origin.y + tickmarkSize;
			h2 = h1 + fm.getAscent();
		}
		else
		{
			h1 = origin.y - tickmarkSize;
			h2 = h1 - fm.getDescent();
		}
		double value = scaleMinimum;
		while ( value < scaleMinimum + scaleRange)
		{
			delta = valueToPixels( value, origin.x);
			g.drawLine( delta, origin.y, delta, h1);
			if ( labels )
			{
				String label = format.format( value);
				g.drawString( label, delta - fm.stringWidth( label) / 2, h2);
			}
			value += tickmarkStep;
		}
	}
	/**
	 * Draws an vertical axis with the current scale.
	 * @param g the graphic context used to perform all drawing command.
	 * @param origin a point in pixel where the axis must begin.
	 * @param tickMarkSize size of the tick marks in pixel
	 * @param left flag indicating whether the axis is drawn on the left of the pane.
	 */
	public void drawVerticalAxis( Graphics g, Point origin, int tickmarkSize, boolean left)
	{
		drawVerticalAxis( g, origin, tickmarkSize, left, true);
	}
	/**
	 * Draws an vertical axis with the current scale.
	 * @param g the graphic context used to perform all drawing command.
	 * @param origin a point in pixel where the axis must begin.
	 * @param tickMarkSize size of the tick marks in pixel
	 * @param left flag indicating whether the axis is drawn on the left of the pane.
	 * @param labels flag indicating whether tickmark labels mustbe drawn.
	 */
	public void drawVerticalAxis( Graphics g, Point origin, int tickmarkSize, boolean left, boolean labels)
	{
		int delta;
		int h1;
		int h3;
		int h4;
		Point pt;
		if ( undefined() )
			computeScale();
		if ( inverted )
		{
			h1 = origin.y - axisLength;
			pt = new Point( origin.x, h1);
			delta = pt.y + tickmarkSize * 2;
		}
		else
		{
			h1 = origin.y + axisLength;
			pt = new Point( origin.x, h1);
			delta = pt.y - tickmarkSize * 2;
		}
		g.drawLine( origin.x, origin.y, pt.x, pt.y);
		g.drawLine( pt.x, pt.y, pt.x - tickmarkSize, delta);
		g.drawLine( pt.x, pt.y, pt.x + tickmarkSize, delta);
		FontMetrics fm = g.getFontMetrics();
		int h2 = fm.getDescent();
		if ( left )
		{
			h1 = origin.x - tickmarkSize;
			h4 = h1 - fm.charWidth('n') / 2;
			h3 = 1;
		}
		else
		{
			h1 = origin.x + tickmarkSize;
			h4 = h1 + fm.charWidth('n') / 2;
			h3 = 0;
		}
		double value = scaleMinimum;
		while ( value < scaleMinimum + scaleRange)
		{
			delta = valueToPixels( value, origin.y);
			g.drawLine( origin.x, delta, h1, delta);
			if ( labels )
			{
				String label = format.format( value);
				g.drawString( label, h4 - h3 * fm.stringWidth( label), delta + h2);
			}
			value += tickmarkStep;
		}
	}
	/**
	 * Returns the axis length.
	 * @return axis length in pixels.
	 */
	public int getAxisLength( )
	{
		return axisLength;
	}
	/**
	 * Returns whether or not the axis is inverted.
	 * @return axis inverted flag.
	 */
	public boolean getInvertedAxis( )
	{
		return inverted;
	}
	/**
	 * Returns the width of the largest tickmark label displayed in the axis.
	 * @return width of the largest tickmark label.
	 */
	public int labelWidth( FontMetrics fm)
	{
		int w = 0;
		double value = scaleMinimum;
		while ( value < scaleMinimum + scaleRange)
		{
			w = Math.max( w, fm.stringWidth( format.format( value)));
			value += tickmarkStep;
		}
		return w;
	}
	/**
	 * Returns the value corresponding to a position specified in pixel.
	 * @param p the pixel position.
	 * @param origin origin of the axis in pixel.
	 * @return value reading of the pixel position.
	 */
	public double pixelsToValue( int p, int origin)
	{
		if ( undefined() )
			computeScale();
		return scaleMinimum + (scaleRange * (double) ( inverted ? origin - p : p - origin) / (double) axisLength);
	}
	/**
	 * Resets the scale.
	 */
	public void reset( )
	{
		tickmarkSpacing = 0;
	}
	/**
	 * Round the specified value upward to the next scale value.
	 * @param the value to be rounded.
	 * @param a fag specified whether integer scale are used, otherwise double scale is used.
	 * @return a number rounded upward to the next scale value.
	 */
	public static double roundToScale( double value, boolean integerValued)
	{
		double[] scaleValues;
		int orderOfMagnitude = (int) Math.floor( Math.log( value) / Math.log( 10.0));
		if ( integerValued )
		{
			orderOfMagnitude = Math.max( 1, orderOfMagnitude);
			if ( orderOfMagnitude == 1)
				scaleValues = integerScales;
			else if ( orderOfMagnitude == 2)
				scaleValues = semiIntegerScales;
			else
				scaleValues = scales;
		}
		else
			scaleValues = scales;
		double exponent = Math.pow( 10.0, orderOfMagnitude);
		double rValue = value / exponent;
		for ( int n = 0; n < scaleValues.length; n++)
		{
			if ( rValue <= scaleValues[n])
				return scaleValues[n] * exponent;
		}
		return exponent;	// Should never reach here
	}
/**
 * Answers an array containing a sampling range to obtain enough points across the X axis.
 * @param sampling distance in pixels between the sampling points.
 * @return range an array of 3 doubles; range[0] minimum x value, range[1] maximum x value, range[2] x step.
 */
public double[] samplingRange ( int sampling)
{
	double[] range = new double[3];
	range[0] = scaleMinimum;
	range[1] = scaleMinimum + scaleRange;
	range[2] = scaleRange * (double) sampling / (double) axisLength;
	return range;
}
	/**
	 * Defines the axis length.
	 * @param length of the axis in pixel (must be positive).
	 */
	public void setAxisLength( int length) throws IllegalArgumentException
	{
		if ( length <= 0 )
			throw new IllegalArgumentException( "Non-positive axis length: "+length);
		axisLength = length;
		reset();
	}
	/**
	 * Forces the scale to have integer value tickmarks.
	 */
	public void setIntegerScale()
	{
		setIntegerScale( true);
	}
	/**
	 * Defines the scale to have integer value tickmarks or not.
	 * @param integerScale flag indicating whether tick marks should be rounded to integer values.
	 */
	public void setIntegerScale( boolean integerScale)
	{
		integerValued = integerScale;
	}
	/**
	 * Forces the axis to be inverted.
	 */
	public void setInvertedAxis( )
	{
		inverted = true;
	}
	/**
	 * Defines the range of the scale.
	 * @param from minimum value.
	 * @param to maximum value.
	 */
	public void setRange( double from, double to) throws IllegalArgumentException
	{
		if ( from >= to )
			throw new IllegalArgumentException( "Inverted range: minimum = "+from+", maximum = "+to);
		minimum = from;
		maximum = to;
		reset();
	}
	/**
	 * Defines the minimum spacing between tickmarks. This number should be
	 * such that tickmark labels do not overlap on each other.
	 * @param spacing minimum spacing between tickmarks in pixels.
	 */
	public void setSpacing( int spacing) throws IllegalArgumentException
	{
		if ( spacing <= 0 )
			throw new IllegalArgumentException( "Non-positive tickmark spacing: "+spacing);
		minimumTickmarkSpacing = spacing;
		reset();
	}
	/**
	 * Returns whether or not the scale parameters have been computed already.
	 */
	private boolean undefined()
	{
		return tickmarkSpacing == 0;
	}
	/**
	 * Returns the pixel position corresponding to a given value.
	 * @param x the value to be positioned on the axis.
	 * @param origin origin of the axis in pixel.
	 * @return the pixel position.
	 */
	public int valueToPixels( double x, int origin)
	{
		if ( undefined() )
			computeScale();
		int answer = (int) Math.round( (double) axisLength * ( x - scaleMinimum) / scaleRange);
		return inverted ? origin - answer : origin + answer;
	}
}
\end{verbatim}