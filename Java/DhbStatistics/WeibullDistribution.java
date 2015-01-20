\begin{verbatim}
package DhbStatistics;

import DhbScientificCurves.Histogram;
import DhbFunctionEvaluation.GammaFunction;
import DhbInterfaces.ParametrizedOneVariableFunction;
/**
 * Weibull distribution.
 *
 * @author Didier H. Besset
 */
public final class WeibullDistribution
                                    extends ProbabilityDensityFunction
{
    /**
     * Shape parameter of the distribution.
     */
    protected double alpha;
    /**
     * Scale parameter of the distribution.
     */
    private double beta;
    /**
     * Norm of the distribution (cached for efficiency).
     */
    private double norm;

/**
 * Create a new instance of the Weibull distribution with given shape and scale.
 * @param shape double shape parameter of the distribution (alpha).
 * @param scale double scale parameter of the distribution (beta).
 * @exception java.lang.IllegalArgumentException
 *                            if any of the parameters is non-positive.
 */
public WeibullDistribution ( double shape, double scale)
                                    throws IllegalArgumentException
{
    if ( shape <= 0 )
        throw new IllegalArgumentException(
                                "Shape parameter must be positive");
    if ( scale <= 0 )
        throw new IllegalArgumentException(
                                "Scale parameter must be positive");
    defineParameters( shape, scale);
}
/**
 * Create an instance of the receiver with parameters estimated from
 * the given histogram using best guesses. This method can be used to
 * find the initial values for a fit.
 * @param h DhbScientificCurves.Histogram
 * @exception java.lang.IllegalArgumentException
 *                            when no suitable parameter can be found.
 */
public WeibullDistribution( Histogram h)
                                    throws IllegalArgumentException
{
    if ( h.getMinimum() < 0 )
        throw new IllegalArgumentException(
    "Weibull distribution is only defined for non-negative values");
    double average = h.average();
    if ( average <= 0 )
        throw new IllegalArgumentException(
        "Weibull distribution must have a non-negative average");
    double xMin = ( h.getMinimum() + average) * 0.5;
    double accMin = Math.log( -Math.log( 1 - h.getCountsUpTo(xMin)
                                                / h.totalCount()));
    double xMax = ( h.getMaximum() + average) * 0.5;
    double accMax = Math.log( -Math.log( 1 - h.getCountsUpTo(xMax) 
                                                / h.totalCount()));
    double delta = accMax - accMin;
    xMin = Math.log( xMin);
    xMax = Math.log( xMax);
    defineParameters( delta / ( xMax - xMin),
                        Math.exp( ( accMax * xMin - accMin * xMax)
                                                            / delta));
}
/**
 * @return double average of the distribution.
 */
public double average()
{
    return GammaFunction.gamma( 1 / alpha) * beta / alpha;
}
/**
 * Assigns new values to the parameters.
 * This method assumes that the parameters have been already checked.
 */
public void defineParameters ( double shape, double scale)
{
    alpha = shape;
    beta = scale;
    norm = alpha / Math.pow( beta, alpha);
    return;
}
/**
 * Returns the probability of finding a random variable smaller
 * than or equal to x.
 * @return integral of the probability density function from 0 to x.
 * @param x double upper limit of integral.
 */
public double distributionValue ( double x)
{
    return 1.0 - Math.exp(-Math.pow( x / beta, alpha));
}
/**
 * @return java.lang.String the name of the distribution.
 */
public String name()
{
    return "Weibull distribution";
}
/**
 * @return double[] an array containing the parameters of 
 *                                                the distribution.
 */
public double[] parameters()
{
    double[] answer = new double[2];
    answer[0] = alpha;
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
    return Math.pow( -Math.log( 1 - x), 1. / alpha) * beta;
}
/**
 * @param p double[]    assigns the parameters
 */
public void setParameters( double[] params)
{
    defineParameters( params[0], params[1]);
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
    sb.append("Weibull distribution (");
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
    return norm * Math.pow( x, alpha - 1) 
                            * Math.exp( -Math.pow( x / beta, alpha));
}
/**
 * @return double variance of the distribution.
 */
public double variance ( )
{
    double s = GammaFunction.gamma( 1 / alpha);
    return beta * beta *( 2 * GammaFunction.gamma( 2 / alpha)
                                            - s * s / alpha) / alpha;
}
}
\end{verbatim}