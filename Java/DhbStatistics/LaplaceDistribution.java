\begin{verbatim}
package DhbStatistics;

import DhbScientificCurves.Histogram;
import DhbInterfaces.ParametrizedOneVariableFunction;
/**
 * Laplace distribution.
 *
 * @author Didier H. Besset
 */
public final class LaplaceDistribution
                                    extends ProbabilityDensityFunction
{
    /**
     * Average of the distribution.
     */
    private double mu;
    /**
     * Scale of the distribution.
     */
    private double beta;
/**
 * Constructor method.
 * @param center double
 * @param scale double
 * @exception java.lang.IllegalArgumentException
 *                            when the scale parameter is non-positive
 */
public LaplaceDistribution( double center, double scale)
                                    throws IllegalArgumentException
{
    if ( scale <= 0 )
        throw new IllegalArgumentException(
                                "Scale parameter must be positive");
    mu = center;
    beta = scale;
}
/**
 * Create an instance of the receiver with parameters estimated from
 * the given histogram using best guesses. This method can be used to
 * find the initial values for a fit.
 * @param h DhbScientificCurves.Histogram
 */
public LaplaceDistribution( Histogram h)
{
    this( h.average(), Math.sqrt( 0.5 * h.variance()));
}
/**
 * @return double average of the distribution.
 */
public double average()
{
    return mu;
}
/**
 * @param center double
 * @param scale double
 */
public void defineParameters ( double center, double scale)
{
    mu = center;
    beta = scale;
}
/**
 * Returns the probability of finding a random variable smaller
 * than or equal to x.
 * @return integral of the probability density function from -infinity to x.
 * @param x double upper limit of integral.
 */
public double distributionValue(double x)
{
    return x > mu
            ? 1 - Math.exp( -( x - mu) / beta) / 2
            : Math.exp( -( x - mu) / beta) / 2;
}
/**
 * @return double kurtosis of the distribution.
 */
public double kurtosis ( )
{
    return 3;
}
/**
 * @return java.lang.String name of the distribution.
 */
public String name() {
    return null;
}
/**
 * @return double[] an array containing the parameters of 
 *                                                the distribution.
 */
public double[] parameters()
{
    double[] answer = new double[2];
    answer[0] = mu;
    answer[1] = beta;
    return answer;
}
/**
 * This method assumes that the range of the argument has been checked.
 * @return double the value for which the distribution function
 *                                                    is equal to x.
 * @param x double value of the distribution function.
 */
public double privateInverseDistributionValue ( double x)
{
    return x < 0.5
            ? mu + beta * Math.log( 2 * x)
            : mu - beta * Math.log( 2 - 2 * x);
}
/**
 * @return double a random number distributed according to the receiver.
 */
public double random( )
{
    double r = -beta * Math.log(generator().nextDouble());
    return generator().nextDouble() > 0.5 ? mu + r : mu - r;
}
/**
 * @param p double[]    assigns the parameters
 */
public void setParameters( double[] params)
{
    defineParameters( params[0], params[1]);
}
/**
 * @return double skewness of the distribution.
 */
public double skewness( )
{
    return 0;
}
/**
 * @return double standard deviation of the distribution
 */
public double standardDeviation( )
{
    return beta / Math.sqrt( 2);
}
/**
 * @return java.lang.String
 */
public String toString()
{
    StringBuffer sb = new StringBuffer();
    java.text.DecimalFormat fmt = new java.text.DecimalFormat(
                                                    "####0.00000");
    sb.append("Laplace distribution (");
    sb.append(fmt.format(mu));
    sb.append(',');
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
    return Math.exp( -Math.abs( x - mu) / beta) / ( 2 * beta);
}
/**
 * Evaluate the distribution and the gradient of the distribution with respect
 * to the parameters.
 * @return double[]    0: distribution's value, 1,2,...,n distribution's gradient
 * @param x double
 */
public double[] valueAndGradient( double x)
{
    double[] answer = new double[3];
    answer[0] = value( x);
    double y = x - mu;
    if ( y >= 0 )
    {
        answer[1] = answer[0] / beta;
        answer[2] = (y / beta - 1) * answer[0] / beta;
    }
    else
    {
        answer[1] = - answer[0] / beta;
        answer[2] = - (y / beta + 1) * answer[0] / beta;
    }
    return answer;
}
}
\end{verbatim}