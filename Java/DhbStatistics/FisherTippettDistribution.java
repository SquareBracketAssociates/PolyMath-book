\begin{verbatim}
package DhbStatistics;

import DhbFunctionEvaluation.DhbMath;
import DhbScientificCurves.Histogram;
import DhbInterfaces.ParametrizedOneVariableFunction;
/**
 * Fisher-Tippett distribution
 *
 * @author Didier H. Besset
 */
public final class FisherTippettDistribution
                            extends ProbabilityDensityFunction
{
    /**
     * Center of the distribution.
     */
    protected double alpha;
    /**
     * Scale parameter of the distribution.
     */
    private double beta;
/**
 * Constructor method
 * @param center double
 * @param scale double
 * @exception java.lang.IllegalArgumentException if the scale parameter is non-positive.
 */
public FisherTippettDistribution( double center, double scale) throws IllegalArgumentException
{
    if ( scale <= 0 )
        throw new IllegalArgumentException("Scale parameter must be positive");
    alpha = center;
    beta = scale;
}
/**
 * Create an instance of the receiver with parameters estimated from the
 * given histogram using best guesses. This method can be used to
 * find the initial values for a fit.
 * @param h DhbScientificCurves.Histogram
 * @exception java.lang.IllegalArgumentException when no suitable parameter can be found.
 */
public FisherTippettDistribution( Histogram h) throws IllegalAccessException
{
    double beta = h.standardDeviation();
    if ( beta < 0 )
        throw new IllegalArgumentException("Histogram has vanishing standard deviation");
    beta *= Math.sqrt(6)/Math.PI;
    defineParameters( h.average() - 0.5772156649 * beta, beta);
}
/**
 * @return double average of the distribution.
 */
public double average()
{
    return 0.5772156649 * beta + alpha;
}
/**
 * @param center double
 * @param scale double
 */
public void defineParameters ( double center, double scale)
{
    alpha = center;
    beta = scale;
}
/**
 * Returns the probability of finding a random variable smaller
 * than or equal to x.
 * @return integral of the probability density function from -infinity to x.
 * @param x double upper limit of integral.
 */
public double distributionValue ( double x)
{
    double y = ( x - alpha) / beta;
    if ( y < -DhbMath.getLargestExponentialArgument() )
        return 0;
    y = Math.exp( -y);
    if ( y > DhbMath.getLargestExponentialArgument() )
        return 1;
    return Math.exp( -y);
}
/**
 * @return double kurtosis of the distribution.
 */
public double kurtosis( )
{
    return 2.4;
}
/**
 * @return java.lang.String    name of the distribution
 */
public String name ( )
{
    return "Fisher-Tippett distribution";
}
/**
 * @return double[] an array containing the parameters of 
 *                                                the distribution.
 */
public double[] parameters ( )
{
    double[] answer = new double[2];
    answer[0] = alpha;
    answer[1] = beta;
    return answer;
}
/**
 * @return double a random number distributed according to the receiver.
 */
public double random( )
{
    double t;
    while ( ( t = -Math.log( generator().nextDouble())) == 0 );
    return alpha - beta * Math.log( t);
}
/**
 * @param p double[]    assigns the parameters
 */
public void setParameters( double[] params)
{
    defineParameters ( params[0], params[1]);
}
/**
 * @return double skewness of the distribution.
 */
public double skewness( )
{
    return 1.3;
}
/**
 * @return double standard deviation of the distribution
 */
public double standardDeviation( )
{
    return Math.PI * beta / Math.sqrt( 6);
}
/**
 * @return java.lang.String
 */
public String toString()
{
    StringBuffer sb = new StringBuffer();
    java.text.DecimalFormat fmt = new java.text.DecimalFormat("####0.00000");
    sb.append("Fisher-Tippett distribution (");
    sb.append(fmt.format(alpha));
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
    double y = ( x - alpha) / beta;
    if ( y > DhbMath.getLargestExponentialArgument() )
        return 0;
    y += Math.exp( -y);
    if ( y > DhbMath.getLargestExponentialArgument() )
        return 0;
    return Math.exp( -y) / beta;
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
    double y = ( x - alpha) / beta;
    double dy = Math.exp( -y) - 1;
    double r = -1 / beta;
    answer[1] = dy * answer[0] * r;
    answer[2] = answer[0] * ( y * dy + 1) * r;
    return answer;
}
}
\end{verbatim}