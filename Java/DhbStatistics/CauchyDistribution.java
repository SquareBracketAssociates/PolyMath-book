\begin{verbatim}
package DhbStatistics;

import DhbScientificCurves.Histogram;
import DhbInterfaces.ParametrizedOneVariableFunction;
/**
 * Cauchy distribution
 *
 * @author Didier H. Besset
 */
public final class CauchyDistribution
                            extends ProbabilityDensityFunction
{
    /**
     * Center of the distribution.
     */
    private double mu;
    /**
     * Scale of the distribution.
     */
    private double beta;

/**
 * Create an instance centered at 0 with width 1.
 */
public CauchyDistribution ( )
{
    this( 0, 1);
}
/**
 * @param middle double middle point of the distribution.
 * @param width double width of the distribution.
 */
public CauchyDistribution ( double middle, double width)
{
    mu = middle;
    beta = width;
}
/**
 * Create an instance of the receiver with parameters estimated from
 * the given histogram using best guesses. This method can be used to
 * find the initial values for a fit.
 * @param h Histogram
 */
public CauchyDistribution( Histogram h)
{
    this( h.average(),
          4 * h.variance() /Math.sqrt(Math.PI *( h.getMinimum()
                      * h.getMinimum() +h.getMaximum() * h.getMaximum()))
          );
}
/**
 * @return double average of the distribution.
 */
public double average()
{
    return mu;
}
/**
 * Returns the probability of finding a random variable smaller
 * than or equal to x.
 * @return integral of the probability density function from -infinity to x.
 * @param x double upper limit of integral.
 */
public double distributionValue ( double x)
{
    return Math.atan( ( x - mu) / beta) / Math.PI + 0.5;
}
/**
 * @return java.lang.String     name of the distribution
 */
public String name ( )
{
    return "Cauchy distribution";
}
/**
 * @return double[] an array containing the parameters of 
 *                                                the distribution.
 */
public double[] parameters ( )
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
protected double privateInverseDistributionValue ( double x)
{
    return Math.tan( (x - 0.5) * Math.PI) * beta + mu;
}
/**
 * @param center double
 */
public void setBeta( double width)
{
    beta = width;
}
/**
 * @param center double
 */
public void setMu( double center)
{
    mu = center;
}
/**
 * @param p double[]    assigns the parameters
 */
public void setParameters( double[] params)
{
    setMu( params[0]);
    setBeta( params[1]);
}
/**
 * @return NaN since the standard deviation of the distribution is
 *                                                        not defined.
 */
public double standardDeviation( )
{
    return Double.NaN;
}
/**
 * This method was created in VisualAge.
 * @return java.lang.String
 */
public String toString()
{
    StringBuffer sb = new StringBuffer();
    java.text.DecimalFormat fmt = new java.text.DecimalFormat(
                                                    "####0.00000");
    sb.append("Cauchy distribution (");
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
    double dev = x - mu;
    return beta / ( Math.PI *( beta * beta + dev * dev));
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
    double r = 1 / ( y * y + beta * beta);
    answer[1] = 2 * answer[0] * y * r;
    answer[2] = answer[0] * ( 1 / beta - 2 * beta * r);
    return answer;
}
}
\end{verbatim}