\begin{verbatim}
package DhbInterpolation;

import DhbInterfaces.PointSeries;
/**
 * A NewtonInterpolator can be used to interpolate values between
 * a series of 2-dimensional points. The interpolation function is
 * the Langrange interpolation polynomial of a degree equal to
 * the number of points in the series minus one. The coefficients
 * of the polynomial are stored, speeding up interpolation for a
 * series of values.
 *
 * @author Didier H. Besset
 */
public class NewtonInterpolator extends LagrangeInterpolator
{
    /**
     * Polynomial coefficient (modified Horner expansion).
     */
    protected double coefficients[];


/**
 * Constructor method.
 * @param pts interfaces.PointSeries
 */
public NewtonInterpolator( PointSeries pts) {
    super(pts);
}
/**
 * Computes the coefficients of the interpolation polynomial.
 */
private void computeCoefficients()
{
    int size = points.size();
    int n;
    int k;
    int k1;
    int kn;
    coefficients = new double[size];
    for ( n = 0; n < size; n++)
        coefficients[n] = points.yValueAt(n);
    size -= 1;
    for ( n = 0; n < size; n++)
    {
        for ( k = size; k > n; k--)
        {
            k1 = k - 1;
            kn = k - (n + 1);
            coefficients[k] = ( coefficients[k] - coefficients[k1])
                                / ( points.xValueAt(k)
                                            - points.xValueAt(kn));
        }
    }
}
/**
 * Forces a new computation of the coefficients. This method must be
 * called whenever the series of points defining the interpolator is
 * modified.
 */
public void resetCoefficients()
{
    coefficients = null;
}
/**
 * Computes the interpolated y value for a given x value.
 * @param aNumber x value.
 * @return interpolated y value.
 */
public double value( double aNumber)
{
    if ( coefficients == null)
        computeCoefficients();
    int size = coefficients.length;
    double answer = coefficients[--size];
    while ( --size >= 0 )
        answer = answer * ( aNumber - points.xValueAt(size))
                                                + coefficients[size];
    return answer;
}
}
\end{verbatim}