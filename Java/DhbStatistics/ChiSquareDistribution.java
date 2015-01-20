\begin{verbatim}
package DhbStatistics;

import DhbScientificCurves.Histogram;
/**
 * Chi square distribution.
 * (as special case of the gamma distribution)
 *
 * @author Didier H. Besset
 */
public final class ChiSquareDistribution extends GammaDistribution
{

/**
 * Create a new instance as Gamma( n/2, 2).
 * @param n int degrees of freedom of the receiver.
 */
public ChiSquareDistribution ( int n)
{
    super( 0.5 * n, 2.0);
}
/**
 * Create an instance of the receiver with parameters estimated from
 * the given histogram using best guesses. This method can be used to
 * find the initial values for a fit.
 * @param h Histogram
 * @exception java.lang.IllegalArgumentException
 *                            when no suitable parameter can be found.
 */
public ChiSquareDistribution( Histogram h) throws IllegalArgumentException
{
    if ( h.getMinimum() < 0 )
        throw new IllegalArgumentException(
            "Chi square distribution is only defined for non-negative values");
    int dof = (int) Math.round( h.average());
    if ( dof <= 0 )
        throw new IllegalArgumentException(
            "Chi square distribution is only defined for positive degrees of freedom");
    setDegreesOfFreedom( dof);
}
/**
 * @return double
 * @param x double
 * @exception java.lang.IllegalArgumentException
 *                        if the argument is outside the expected range.
 */
public double confidenceLevel( double x)
                                    throws IllegalArgumentException
{
    return x < 0
            ? Double.NaN
            : ( 1 - distributionValue( x)) * 100;
}
/**
 * @return java.lang.String name of the distribution.
 */
public String name()
{
    return "Chi square distribution";
}
/**
 * @return double[] an array containing the parameters of 
 *                                                the distribution.
 */
public double[] parameters()
{
    double[] answer = new double[1];
    answer[0] = alpha * 2;
    return answer;
}
/**
 * @param n int
 */
public void setDegreesOfFreedom( int n)
{
    super.defineParameters( 0.5 * n, 2.0);
}
/**
 * Note: for fitting, non-integer degree of dreedom is allowed
 * @param params double[]    assigns the parameters
 */
public void setParameters( double[] params)
{
    defineParameters( params[0] * 0.5, 2);
}
/**
 * @return java.lang.String
 */
public String toString()
{
    StringBuffer sb = new StringBuffer();
    java.text.DecimalFormat fmt = new java.text.DecimalFormat("0.00");
    sb.append("Chi square distribution (");
    sb.append(fmt.format(alpha * 2));
    sb.append(')');
    return sb.toString();
}
}
\end{verbatim}