\begin{verbatim}
package DhbStatistics;

import DhbScientificCurves.Histogram;
import DhbInterfaces.ParametrizedOneVariableFunction;
/**
 * Triangular distribution.
 *
 * @author Didier H. Besset
 */
public final class TriangularDistribution
                                    extends ProbabilityDensityFunction
{
    /**
     * Low limit.
     */
    private double a;
    /**
     * High limit.
     */
    private double b;
    /**
     * peak location.
     */
    private double c;

/**
 * Constructor method.
 * @param low double    low limit
 * @param high double    high limit
 * @param peak double    peak of the distribution
 * @exception java.lang.IllegalArgumentException
 *                        if the limits are inverted or
 *                        if the peak is outside the limits.
 */
public TriangularDistribution ( double low, double high, double peak) throws IllegalArgumentException
{
    if ( low >= high )
        throw new IllegalArgumentException(
                    "Limits of distribution are equal or reversed");
    if ( peak < low || peak > high )
        throw new IllegalArgumentException(
                    "Peak of distribution lies outside the limits");
    a = low;
    b = high;
    c = peak;
}
/**
 * Create an instance of the receiver with parameters estimated from
 * the given histogram using best guesses. This method can be used to
 * find the initial values for a fit.
 * @param h DhbScientificCurves.Histogram
 */
public TriangularDistribution( Histogram h)
{
    b = h.standardDeviation() * 1.73205080756888; // sqrt(12)/2
    c = h.average();
    a = c - b;
    b += c;
}
/**
 * @return double average of the distribution.
 */
public double average()
{
    return ( a + b + c) / 3;
}
/**
 * Returns the probability of finding a random variable smaller
 * than or equal to x.
 * @return integral of the probability density function from a to x.
 * @param x double upper limit of integral.
 */
public double distributionValue ( double x)
{
    if ( x < a )
        return 0;
    else if ( x < c )
        return ( x - a) * ( x - a) / ( ( b - a) * ( c - a));
    else if ( x < b )
        return 1 - ( b - x) * ( b - x) / ( ( b - a) * ( b - c));
    else
        return 1;
}
/**
 * @return java.lang.String     name of the distribution
 */
public String name()
{
    return "Triangular distribution";
}
/**
 * @return double[] an array containing the parameters of 
 *                                                the distribution.
 */
public double[] parameters()
{
    double[] answer = new double[3];
    answer[0] = a;
    answer[1] = b;
    answer[2] = c;
    return answer;
}
/**
 * This method assumes that the range of the argument has been checked.
 * @return double the value for which the distribution function
 *                                                    is equal to x.
 * @param x double value of the distribution function.
 */
protected double privateInverseDistributionValue ( double x)
{
    return ( x < ( c - a) / ( b - a) )
                    ? Math.sqrt( x * ( b - a) * ( c - a)) + a
                    : b - Math.sqrt( (1 - x) * (b - a) * ( b - c));
}
/**
 * @param p double[]    assigns the parameters
 */
public void setParameters( double[] params)
{
    a = params[0];
    b = params[1];
    c = params[2];
}
/**
 * @return double probability density function
 * @param x double random variable
 */
public double value( double x)
{
    if ( x < a )
        return 0;
    else if ( x < c )
        return 2 * (x - a) / ( (b - a) * ( c - a));
    else if ( x < b )
        return 2 * (b - x) / ( (b - a) * ( b - c));
    else
        return 0;
}
/**
 * @return double variance of the distribution
 */
public double variance()
{
    return ( a * a + b * b + c * c - a * b - a * c - b * c) / 18;
}
}
\end{verbatim}