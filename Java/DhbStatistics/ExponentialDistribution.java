\begin{verbatim}
package DhbStatistics;

import DhbScientificCurves.Histogram;
import DhbInterfaces.ParametrizedOneVariableFunction;
/**
 * Exponential distribution.
 * 
 * @author Didier H. Besset
 */
public final class ExponentialDistribution
                            extends ProbabilityDensityFunction
{
    /**
     * Exponential term.
     */
    private double beta;

/**
 * General constructor method.
 * @param exponential fall-off
 * @exception java.lang.IllegalArgumentException
 *                                    if the fall-off is non-positive.
 */
public ExponentialDistribution ( double fallOff)
                                    throws IllegalArgumentException
{
    if ( fallOff <= 0 )
        throw new IllegalArgumentException(
                            "Exponential fall-off must be positive");
    beta = fallOff;
}
/**
 * Create an instance of the receiver with parameters estimated from
 * the given histogram using best guesses. This method can be used to
 * find the initial values for a fit.
 * @param h DhbScientificCurves.Histogram
 * @exception java.lang.IllegalArgumentException
 *                            when no suitable parameter can be found.
 */
public ExponentialDistribution( Histogram h)
                                    throws IllegalArgumentException
{
    if ( h.getMinimum() < 0 )
        throw new IllegalArgumentException(
    "Exponential distribution is only defined for non-negative values");
    double average = h.average();
    if ( h.average() < 0 )
        throw new IllegalArgumentException(
        "Exponential distribution is only defined for positive scale");
    setScale( average);
}
/**
 * @return double average of the distribution.
 */
public double average ( )
{
    return beta;
}
/**
 * Returns the probability of finding a random variable smaller
 * than or equal to x.
 * @return integral of the probability density function from 0 to x.
 * @param x double upper limit of integral.
 */
public double distributionValue ( double x)
{
    return 1 - Math.exp( -x / beta);
}
/**
 * @return double kurtosis of the distribution.
 */
public double kurtosis ( )
{
    return 6;
}
/**
 * @return java.lang.String     name of the distribution
 */
public String name ( )
{
    return "Exponential distribution";
}
/**
 * @return double[] an array containing the parameters of
 *                                                    the distribution.
 */
public double[] parameters ( )
{
    double[] answer = new double[1];
    answer[0] = beta;
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
    return -Math.log( 1 - x) * beta;
}
/**
 * @return double a random number distributed according to the receiver.
 */
public double random( )
{
    return -beta * Math.log(generator().nextDouble());
}
/**
 * @param p double[]    assigns the parameters
 */
public void setParameters( double[] params)
{
    setScale( params[0]);
}
/**
 * @param falloff double
 */
public void setScale( double falloff)
{
    beta = falloff;
}
/**
 * @return double skewness of the distribution.
 */
public double skewness( )
{
    return 2;
}
/**
 * @return double standard deviation of the distribution
 */
public double standardDeviation( )
{
    return beta;
}
/**
 * @return java.lang.String
 */
public String toString()
{
    StringBuffer sb = new StringBuffer();
    java.text.DecimalFormat fmt = new java.text.DecimalFormat(
                                                        "###0.00000");
    sb.append("Exponential distribution (");
    sb.append(fmt.format(beta));
    sb.append(')');
    return sb.toString();
}
/**
 * @return double probability density function
 * @param x double random variable
 */
public double value( double x)
{
    return Math.exp( -x / beta) / beta;
}
/**
 * Evaluate the distribution and the gradient of the distribution with respect
 * to the parameters.
 * @return double[]    0: distribution's value, 1,2,...,n distribution's gradient
 * @param x double
 */
public double[] valueAndGradient( double x)
{
    double[] answer = new double[2];
    answer[0] = value(x);
    answer[1] = ( x / beta - 1) * answer[0] / beta;
    return answer;
}
}
\end{verbatim}