\begin{verbatim}
package DhbInterpolation;

import DhbInterfaces.OneVariableFunction;
import DhbInterfaces.PointSeries;
/**
 * A SplineInterpolator can be used to interpolate values between
 * a series of 2-dimensional points. The interpolation function is
 * a cubic spline with first derivatives defined at the end points.
 * If the first derivatives are not defined for the end points,
 * a so-called natural spline is used with second derivatives at
 * the end points set to zero.
 *
 * @author Didier H. Besset
 */
public class SplineInterpolator extends NewtonInterpolator
                        implements DhbInterfaces.OneVariableFunction 
{
    /**
     * First derivative at first point.
     */
    private double startPointDerivative = Double.NaN;
    /**
     * First derivative at last point.
     */
    private double endPointDerivative = Double.NaN;
/**
 * This method creates a new instance of a spline interpolator over
 * a given set of points. The points must be sorted by strictly
 * increasing x values.
 * @param pts DhbInterfaces.PointSeries contains the points sampling
 *                 the function to interpolate.
 * @exception java.lang.IllegalArgumentException points are not sorted
 *                 in increasing x values.
 */
public SplineInterpolator ( PointSeries pts)
                                    throws IllegalArgumentException
{
    super( pts);
    for ( int i = 1; i < pts.size(); i++ )
    {
        if ( pts.xValueAt( i - 1) >= pts.xValueAt( i) )
            throw new IllegalArgumentException
                    ( "Points must be sorted in increasing x value");
    }
}
private void computeSecondDerivatives( )
{
    int n = points.size();
    double w, s;
    double[] u = new double[ n - 1];
    coefficients = new double[ n];
    if ( Double.isNaN( startPointDerivative) )
        coefficients[0] = u[0] = 0;
    else
    {
        coefficients[0] = -0.5;
        u[0] = 3.0 / ( points.xValueAt( 1) - points.xValueAt( 0))
                    * ( ( points.yValueAt( 1) - points.yValueAt( 0))
                        / ( points.xValueAt( 1) - points.xValueAt( 0))
                                - startPointDerivative);
    }
    for ( int i = 1; i < n - 1; i ++ )
    {
        double invStep2 = 1 / ( points.xValueAt( i + 1)
                                        - points.xValueAt( i - 1));
        s = ( points.xValueAt( i) - points.xValueAt( i - 1)) * invStep2;
        w = 1 / ( s * coefficients[ i - 1] + 2);
        coefficients[ i] = ( s - 1) * w;
        u[i] = ( 6 * invStep2 * ( 
                    ( points.yValueAt( i + 1) - points.yValueAt( i))
                    / ( points.xValueAt( i + 1) - points.xValueAt( i))
                    - ( points.yValueAt( i) - points.yValueAt( i - 1))
                    / ( points.xValueAt( i) - points.xValueAt( i - 1))
                        ) - s * u[i - 1]) * w;
    }
    if ( Double.isNaN( endPointDerivative) )
        w = s = 0;
    else
    {
        w = -0.5;
        s = 3.0 / ( points.xValueAt( n - 1) - points.xValueAt( n - 2))
                    * ( endPointDerivative - ( points.yValueAt( n - 1
                        ) - points.yValueAt( n - 2))
                    / ( points.xValueAt( n - 1) -
                                            points.xValueAt( n - 2)));
    }
    coefficients[ n - 1] = ( s - w * u[ n - 2])
                                    / ( w * coefficients[ n - 2] + 1);
    for ( int i = n - 2; i >= 0; i--)
        coefficients[i] = coefficients[i] * coefficients[i + 1] + u[i];
    return;
}
/**
 * Computes the interpolated y value for a given x value.
 * @param aNumber x value.
 * @return interpolated y value.
 */
public double value(double x)
{
    if ( coefficients == null )
        computeSecondDerivatives();
    int n1 = 0;
    int n2 = points.size() - 1;
    while ( n2 - n1 > 1 )
    {
        int n = (n1 + n2) / 2;
        if ( points.xValueAt( n) > x )
            n2 = n;
        else
            n1 = n;
    }
    double step = points.xValueAt( n2) - points.xValueAt( n1);
    double a = ( points.xValueAt( n2) - x) / step;
    double b = ( x - points.xValueAt( n1)) / step;
    return a * points.yValueAt( n1) + b * points.yValueAt( n2)
                + ( a * ( a * a - 1) * coefficients[n1]
                        + b * ( b * b - 1) * coefficients[n2])
                                                    * step * step / 6;
}
}
\end{verbatim}