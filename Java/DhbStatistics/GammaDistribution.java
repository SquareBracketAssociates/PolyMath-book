\begin{verbatim}
package DhbStatistics;

import DhbIterations.IncompleteGammaFunction;
import DhbFunctionEvaluation.GammaFunction;
import DhbScientificCurves.Histogram;
import DhbInterfaces.ParametrizedOneVariableFunction;
/**
 * Gamma distribution.
 *
 * @author Didier H. Besset
 */
public class GammaDistribution extends ProbabilityDensityFunction
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
     * Constants used in random number generator (cached for efficiency).
     */
    private double a;
    private double b;
    private double q;
    private double d;
    /**
     * Incomplete gamma function for the distribution (cached for efficiency).
     */
    private IncompleteGammaFunction incompleteGammaFunction;
/**
 * Constructor method (for internal use only).
 */
protected GammaDistribution() 
{
}
/**
 * Create a new instance of the Gamma distribution with given shape and scale.
 * @param shape double shape parameter of the distribution (alpha).
 * @param scale double scale parameter of the distribution (beta).
 * @exception java.lang.IllegalArgumentException The exception description.
 */
public GammaDistribution ( double shape, double scale) throws IllegalArgumentException
{
    if ( shape <= 0 )
        throw new IllegalArgumentException( "Shape parameter must be positive");
    if ( scale <= 0 )
        throw new IllegalArgumentException( "Scale parameter must be positive");
    defineParameters( shape, scale);
}
/**
 * Create an instance of the receiver with parameters estimated from the
 * given histogram using best guesses. This method can be used to
 * find the initial values for a fit.
 * @param h DhbScientificCurves.Histogram
 * @exception java.lang.IllegalArgumentException when no suitable parameter can be found.
 */
public GammaDistribution( Histogram h) throws IllegalArgumentException
{
    if ( h.getMinimum() < 0 )
        throw new IllegalArgumentException("Gamma distribution is only defined for non-negative values");
    double shape = h.average();
    if ( shape <= 0 )
        throw new IllegalArgumentException("Gamma distribution must have a non-negative shape parameter");
    double scale = h.variance() / shape;
    if ( scale <= 0 )
        throw new IllegalArgumentException("Gamma distribution must have a non-negative scale parameter");
    defineParameters( shape / scale, scale);
}
/**
 * @return double average of the distribution.
 */
public double average()
{
    return alpha * beta;
}
/**
 * Assigns new values to the parameters.
 * This method assumes that the parameters have been already checked.
 */
public void defineParameters ( double shape, double scale)
{
    alpha = shape;
    beta = scale;
    norm = Math.log( beta) * alpha + GammaFunction.logGamma( alpha);
    if ( alpha < 1)
        b = (Math.E + alpha) / Math.E;
    else if ( alpha > 1)
    {
        a = Math.sqrt( 2 * alpha - 1);
        b = alpha - Math.log( 4.0);
        q = alpha + 1 / a;
        d = 1 + Math.log( 4.5);
    }
    incompleteGammaFunction = null;
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
    return incompleteGammaFunction().value( x / beta);
}
/**
 * @return DhbIterations.IncompleteGammaFunction
 */
private IncompleteGammaFunction incompleteGammaFunction()
{
    if ( incompleteGammaFunction == null )
        incompleteGammaFunction = new IncompleteGammaFunction( alpha);
    return incompleteGammaFunction;
}
/**
 * @return double kurtosis of the distribution.
 */
public double kurtosis( )
{
    return 6 / alpha;
}
/**
 * @return java.lang.String name of the distribution.
 */
public String name()
{
    return "Gamma distribution";
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
 * @return double a random number distributed according to the receiver.
 */
public double random( )
{
    double r;
    
    if ( alpha > 1)
        r = randomForAlphaGreaterThan1();
    else if (alpha < 1)
        r = randomForAlphaLessThan1();
    else
        r = randomForAlphaEqual1();

    return r * beta;
}
/**
 * @return double
 */
private double randomForAlphaEqual1( )
{
    return -Math.log( 1 - generator().nextDouble());
}
/**
 * @return double
 */
private double randomForAlphaGreaterThan1( )
{
    double u1, u2, v, y, z, w;
    while ( true)
    {
        u1 = generator().nextDouble();
        u2 = generator().nextDouble();
        v = a * Math.log( u1 / ( 1 - u1));
        y = alpha * Math.exp( v);
        z = u1 * u1 * u2;
        w = b + q * v - y;
        if ( w + d - 4.5 * z >= 0 || w >= Math.log( z) )
            return y;
    }    
}
/**
 * @return double
 */
private double randomForAlphaLessThan1( )
{
    double p, y;
    while ( true)
    {
        p = generator().nextDouble() * b;
        if ( p > 1)
        {
            y = -Math.log( ( b - p) / alpha);
            if ( generator().nextDouble() <= Math.pow( y, alpha - 1) )
                return y;
        }
        y = Math.pow( p, 1 / alpha);
        if ( generator().nextDouble() <= Math.exp( -y) )
            return y;
    }    
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
    return 2 / Math.sqrt( alpha);
}
/**
 * @return java.lang.String
 */
public String toString()
{
    StringBuffer sb = new StringBuffer();
    java.text.DecimalFormat fmt = new java.text.DecimalFormat("####0.00000");
    sb.append("Gamma distribution (");
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
    return x > 0 ? Math.exp( Math.log( x) * ( alpha - 1) - x / beta - norm) : 0;
}
/**
 * @return double variance of the distribution.
 */
public double variance( )
{
    return alpha * beta * beta;
}
}
\end{verbatim}