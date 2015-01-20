\begin{verbatim}
package DhbStatistics;

import DhbFunctionEvaluation.PolynomialFunction;
import DhbScientificCurves.Histogram;
import DhbInterfaces.ParametrizedOneVariableFunction;
/**
 * Normal distribution, a.k.a. Gaussian distribution.
 *
 * @author Didier H. Besset
 */
public final class NormalDistribution
                                extends ProbabilityDensityFunction
{
/**
 * Average of the distribution.
 */
    private double mu;
/**
 * Standard deviation of the distribution.
 */
    private double sigma;
/**
 * Constant needed to compute the norm.
 */
    private static double baseNorm = Math.sqrt( 2 * Math.PI);
/**
 * Series to compute the error function.
 */
    private static PolynomialFunction errorFunctionSeries;
    static     {
        double[] coeffs = { 0.31938153, -0.356563782, 1.781477937,
                                        -1.821255978, 1.330274429};
        errorFunctionSeries = new PolynomialFunction( coeffs);
    };
/**
 * Constant needed to compute the argument to the error function series.
 */
    private static double errorFunctionConstant = 0.2316419;

/**
 * Defines a normalized Normal distribution with average 0
 *                                        and standard deviation 1.
 */
public NormalDistribution ( ) throws IllegalArgumentException
{
    this( 0, 1);
}
/**
 * Defines a Normal distribution with known average
 *                                            and standard deviation.
 * @param average of the distribution
 * @param standard deviation of the distribution
 * @exception java.lang.IllegalArgumentException
 *                        if the standard deviation is non-positive
 */
public NormalDistribution ( double average, double standardDeviation)
                                    throws IllegalArgumentException
{
    if ( standardDeviation <= 0 )
        throw new IllegalArgumentException(
                            "Standard deviation must be positive");
    mu = average;
    sigma = standardDeviation;
}
/**
 * Create an instance of the receiver with parameters estimated from
 * the given histogram using best guesses. This method can be used to
 * find the initial values for a fit.
 * @param h DhbScientificCurves.Histogram
 */
public NormalDistribution( Histogram h)
{
    this( h.average(), h.standardDeviation());
}
/**
 * @return double average of the distribution.
 */
public double average ( )
{
    return mu;
}
/**
 * Returns the probability of finding a random variable smaller
 * than or equal to x.
 * @return integral of the probability density function from -infinity to x.
 * @param x double upper limit of integral.
 */
public double distributionValue( double x)
{
    return errorFunction( ( x - mu) / sigma);
}
/**
 * @return error function for the argument.
 * @param x double
 */
public static double errorFunction ( double x)
{
    if ( x == 0 )
        return 0.5;
    else if ( x > 0 )
        return 1 - errorFunction( -x);
    double t = 1 / (1 - errorFunctionConstant * x);    
    return t * errorFunctionSeries.value( t) * normal( x);
}
/**
 * @return double kurtosis of the distribution.
 */
public double kurtosis( )
{
    return 0;
}
/**
 * @return java.lang.String     name of the distribution
 */
public String name ( )
{
    return "Normal distribution";
}
/**
 * @return the density probability function for a (0,1) normal distribution.
 * @param x double    value for which the probability is evaluated.
 */
static public double normal( double x)
{
    return Math.exp( -0.5 * x * x) / baseNorm;
}
/**
 * @return double[]    array containing mu and sigma
 */
public double[] parameters ( )
{
    double[] answer = new double[2];
    answer[0] = mu;
    answer[1] = sigma;
    return answer;
}
/**
 * @return double a random number distributed according to the receiver.
 */
public double random( )
{
    return generator().nextGaussian() * sigma + mu;
}
/**
 * @param average double
 */
public void setAverage( double average)
{
    mu = average;
}
/**
 * @param p double[]    assigns the parameters
 */
public void setParameters( double[] params)
{
    setAverage( params[0]);
    setStandardDeviation( params[1]);
}
/**
 * @param average double
 */
public void setStandardDeviation( double standardDeviation)
{
    sigma = standardDeviation;
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
    return sigma;
}
/**
 * @return java.lang.String
 */
public String toString()
{
    StringBuffer sb = new StringBuffer();
    java.text.DecimalFormat fmt = new java.text.DecimalFormat(
                                                        "0.00000");
    sb.append("Normal distribution (");
    sb.append(fmt.format(mu));
    sb.append(',');
    sb.append(fmt.format(sigma));
    sb.append(')');
    return sb.toString();
}
/**
 * @return double probability density function
 * @param x double random variable
 */
public double value( double x)
{
    return normal( ( x - mu) / sigma) / sigma;
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
    double y = ( x - mu) / sigma;
    answer[0] = normal( y) / sigma;
    answer[1] = answer[0] * y / sigma;
    answer[2] = answer[0] * ( y * y - 1) / sigma;
    return answer;
}
}
\end{verbatim}