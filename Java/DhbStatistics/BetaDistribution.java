\begin{verbatim}
package DhbStatistics;

import DhbIterations.IncompleteBetaFunction;
import DhbFunctionEvaluation.GammaFunction;
import DhbScientificCurves.Histogram;
import DhbInterfaces.ParametrizedOneVariableFunction;
/**
 * Beta distribution
 *
 * @author Didier H. Besset
 */
public final class BetaDistribution
                            extends ProbabilityDensityFunction
{
    /**
     * First shape parameter of the distribution.
     */
    protected double alpha1;
    /**
     * Second shape parameter of the distribution.
     */
    private double alpha2;
    /**
     * Norm of the distribution (cached for efficiency).
     */
    private double norm;
    /**
     * Gamma distribution for alpha1 used for random generation (cached for efficiency).
     */
    private GammaDistribution gamma1;
    /**
     * Gamma distribution for alpha2 used for random generation (cached for efficiency).
     */
    private GammaDistribution gamma2;
    /**
     * Incomplete beta function for the distribution (cached for efficiency).
     */
    private IncompleteBetaFunction incompleteBetaFunction;
/**
 * Create a new instance of the Beta distribution with given shape and scale.
 * @param shape1 double first shape parameter of the distribution (alpha1).
 * @param shape2 double second shape parameter of the distribution (alpha2).
 * @exception java.lang.IllegalArgumentException
 *                        if the parameters of the distribution are illegal.
 */
public BetaDistribution ( double shape1, double shape2) throws IllegalArgumentException
{
    if ( shape1 <= 0 )
        throw new IllegalArgumentException(
                            "First shape parameter must be positive");
    if ( shape2 <= 0 )
        throw new IllegalArgumentException(
                            "Second shape parameter must be positive");
    defineParameters( shape1, shape2);
}
/**
 * Create an instance of the receiver with parameters estimated from
 * the given histogram using best guesses. This method can be used to
 * find the initial values for a fit.
 * @param h DhbScientificCurves.Histogram
 * @exception java.lang.IllegalArgumentException
 *                            when no suitable parameter can be found.
 */
public BetaDistribution( Histogram h) throws IllegalArgumentException
{
    if ( h.getMinimum() < 0 || h.getMaximum() > 1)
        throw new IllegalArgumentException(
                    "Beta distribution is only defined over [0,1]");
    double average = h.average();
    double variance = h.variance();
    double a = ( ( 1 - average) / variance - 1) * average;
    if ( a <= 0 )
        throw new IllegalArgumentException("Negative shape parameter");
    double b = ( 1 /average - 1) * a;
    if ( b <= 0 )
        throw new IllegalArgumentException("Negative shape parameter");
    defineParameters( a, b);
}
/**
 * @return double average of the distribution.
 */
public double average()
{
    return alpha1 / ( alpha1 + alpha2);
}
/**
 * Assigns new values to the parameters.
 * This method assumes that the parameters have been already checked.
 */
private void defineParameters ( double shape1, double shape2)
{
    alpha1 = shape1;
    alpha2 = shape2;
    norm = GammaFunction.logBeta( alpha1, alpha2);
    gamma1 = null;
    gamma2 = null;
    incompleteBetaFunction = null;
    return;
}
private void defineRandomGenerator ( )
{
    gamma1 = new GammaDistribution( alpha1, 1.0);
    gamma2 = new GammaDistribution( alpha2, 1.0);
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
    return incompleteBetaFunction().value(x);
}
/**
 * @return DhbIterations.IncompleteBetaFunction
 */
private IncompleteBetaFunction incompleteBetaFunction()
{
    if ( incompleteBetaFunction == null )
        incompleteBetaFunction = new IncompleteBetaFunction( alpha1,
                                                            alpha2);
    return incompleteBetaFunction;
}
/**
 * @return double kurtosis of the distribution.
 */
public double kurtosis( )
{
    double s = alpha1 + alpha2;
    return 3 * ( alpha1 + alpha2 + 1) * ( 2 * s * s + 
                            ( alpha1 + alpha2 - 6) * alpha1 * alpha2)
                    / ( ( alpha1 + alpha2 + 2) *
                            ( alpha1 + alpha2 + 3) * alpha1 * alpha2)
                        - 3;
}
/**
 * @return java.lang.String name of the distribution.
 */
public String name()
{
    return "Beta distribution";
}
/**
 * @return double[] an array containing the parameters of 
 *                                                the distribution.
 */
public double[] parameters()
{
    double[] answer = new double[2];
    answer[0] = alpha1;
    answer[1] = alpha2;
    return answer;
}
/**
 * @return double a random number distributed according to the receiver.
 */
public double random ( )
{
    if ( gamma1 == null )
        defineRandomGenerator();
    double y1 = gamma1.random();
    return y1 / ( y1 + gamma2.random());
}
/**
 * @param a1 double
 */
public void setAlpha1( double a1)
{
    defineParameters( a1, alpha2);
}
/**
 * @param a2 double
 */
public void setAlpha2( double a2)
{
    defineParameters( alpha1, a2);
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
    return 2 * Math.sqrt( alpha1 + alpha2 + 1) * (alpha2 - alpha1)
                    / ( Math.sqrt( alpha1 * alpha2)
                                            * ( alpha1 + alpha2 + 2));
}
/**
 * @return java.lang.String
 */
public String toString()
{
    StringBuffer sb = new StringBuffer();
    java.text.DecimalFormat fmt = new java.text.DecimalFormat("0.00000");
    sb.append("Beta distribution (");
    sb.append(fmt.format(alpha1));
    sb.append(',');
    sb.append(fmt.format(alpha2));
    sb.append(')');
    return sb.toString();
}
/**
 * @return double probability density function
 * @param x double random variable
 */
public double value( double x)
{
    return Math.exp( Math.log( x) * ( alpha1 - 1)
                        + Math.log( 1 - x) * ( alpha2 - 1) - norm);
}
/**
 * @return double variance of the distribution.
 */
public double variance( )
{
    double s = alpha1 + alpha2;
    return alpha1 * alpha2 / ( s * s * ( alpha1 + alpha2 + 1));
}
}
\end{verbatim}