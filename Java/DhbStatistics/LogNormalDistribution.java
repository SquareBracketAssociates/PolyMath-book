\begin{verbatim}
package DhbStatistics;

import DhbScientificCurves.Histogram;
import DhbInterfaces.ParametrizedOneVariableFunction;
/**
 * Log normal distribution
 *
 * @author Didier H. Besset
 * 
 */
public final class LogNormalDistribution
                    extends ProbabilityDensityWithUnknownDistribution
{
    /**
     * Normal distribution with the same parameters.
     */
    private NormalDistribution normalDistr;

/**
 * Defines a Log Normal distribution with known parameters.
 * @param mu double
 * @param sigma double
 * @exception java.lang.IllegalArgumentException
 *                                        when sigma is non-positive
 */
public LogNormalDistribution ( double mu, double sigma)
                                    throws IllegalArgumentException
{
    normalDistr = new NormalDistribution( mu, sigma);
}
/**
 * Create an instance of the receiver with parameters estimated from
 * the given histogram using best guesses. This method can be used to
 * find the initial values for a fit.
 * @param h DhbScientificCurves.Histogram
 * @exception java.lang.IllegalArgumentException
 *                            when no suitable parameter can be found.
 */
public LogNormalDistribution( Histogram h)
                                    throws IllegalArgumentException
{
    if ( h.getMinimum() < 0 )
        throw new IllegalArgumentException(
    "Log normal distribution is only defined for non-negative values");
    double average = h.average();
    if ( average <= 0 )
        throw new IllegalArgumentException(
    "Log normal distribution is only defined for positive average");
    double variance = h.variance();
    double sigma2 = Math.log( variance / (average * average) + 1);
    if ( sigma2 <= 0 )
        throw new IllegalArgumentException(
    "Log normal distribution is only defined for positive sigma");
    normalDistr = new NormalDistribution(  Math.log( average),
                                                Math.sqrt( sigma2));
}
/**
 * @return double average of the distribution.
 */
public double average ( )
{
    return Math.exp( normalDistr.variance() / 2 
                                            + normalDistr.average());
}
/**
 * @return double    the lowest value of the random variable
 */
protected double lowValue()
{
    return 0;
}
/**
 * @return java.lang.String     name of the distribution
 */
public String name ( )
{
    return "Log normal distribution";
}
/**
 * @return double[] an array containing the parameters of 
 *                                                the distribution.
 */
public double[] parameters ( )
{
    return normalDistr.parameters();
}
/**
 * @return double a random number distributed according to the receiver.
 */
public double random( )
{
    return Math.exp( normalDistr.random());
}
/**
 * @param m double
 */
public void setMu( double mu)
{
    normalDistr.setAverage( mu);
}
/**
 * @param p double[]    assigns the parameters
 */
public void setParameters( double[] params)
{
    setMu( params[0]);
    setSigma( params[1]);
}
/**
 * @param sigma double
 */
public void setSigma( double sigma)
{
    normalDistr.setStandardDeviation( sigma);
}
/**
 * @return java.lang.String
 */
public String toString()
{
    StringBuffer sb = new StringBuffer();
    java.text.DecimalFormat fmt = new java.text.DecimalFormat(
                                                    "####0.00000");
    sb.append("Log normal distribution (");
    sb.append(fmt.format(normalDistr.average()));
    sb.append(',');
    sb.append(fmt.format(normalDistr.standardDeviation()));
    sb.append(')');
    return sb.toString();
}
/**
 * @return double probability density function
 * @param x double random variable
 */
public double value ( double x)
{
    return x > 0 ? normalDistr.value( Math.log(x)) / x : 0;
}
/**
 * @return double variance of the distribution.
 */
public double variance ( )
{
    double variance = normalDistr.variance();
    return Math.exp( variance + 2 * normalDistr.average())
                                        * ( Math.exp( variance) - 1);
}
}
\end{verbatim}