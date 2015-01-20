\begin{verbatim}
package DhbInterpolation;

import DhbInterfaces.OneVariableFunction;
import DhbInterfaces.PointSeries;
/**
 * A LagrangeInterpolator can be used to interpolate values between
 * a series of 2-dimensional points. The interpolation function is
 * the Langrange interpolation polynomial of a degree equal to the
 * number of points in the series minus one.
 *
 * @author Didier H. Besset
 */
public class LagrangeInterpolator implements OneVariableFunction
{
    /**
     * Points containing the values.
     */
    protected PointSeries points;
/**
 * Constructor method.
 * @param pts the series of points.
 * @see PointSeries
 */
public LagrangeInterpolator(PointSeries pts)
{
    points = pts;
}
/**
 * Computes the interpolated y value for a given x value.
 * @param aNumber x value.
 * @return interpolated y value.
 */
public double value( double aNumber)
{
    double norm = 1.0;
    int size = points.size();
    double products[] = new double[size];
    for ( int i = 0; i < size; i++)
        products[i] = 1;
    double dx;
    for ( int i = 0; i < size; i++)
    {
        dx = aNumber - points.xValueAt( i);
        if ( dx == 0 )
            return points.yValueAt(i);
        norm *= dx;
        for ( int j = 0; j < size; j++)
        {
            if ( i != j)
                products[j] *= points.xValueAt(j)
                                            - points.xValueAt(i);
        }
    }
    double answer = 0.0;
    for ( int i = 0; i < size; i++)
        answer += points.yValueAt(i)
                    / (products[i] * (aNumber - points.xValueAt(i)));
    return norm * answer;
}
}
\end{verbatim}