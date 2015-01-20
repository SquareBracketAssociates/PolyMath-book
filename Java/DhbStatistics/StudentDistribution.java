\begin{verbatim}
package DhbStatistics;

import DhbFunctionEvaluation.GammaFunction;
import DhbIterations.IncompleteBetaFunction;
import DhbScientificCurves.Histogram;
/**
 * Student distribution
 * used in computing the t-test.
 *
 * @author Didier H. Besset
 */
public final class StudentDistribution
                                    extends ProbabilityDensityFunction
{
    /**
     * Degree of freedom.
     */
    protected int dof;
    /**
     * Norm (stored for efficiency).
     */
    private double norm;
    /**
     * Function used to compute the distribution.
     */
    private IncompleteBetaFunction incompleteBetaFunction = null;
    /**
     * Auxiliary distribution for random number generation.
     */
    private ChiSquareDistribution chiSquareDistribution = null;
/**
 * Constructor method.
 * @param n int    degree of freedom
 * @exception java.lang.IllegalArgumentException\
 *                when the specified degree of freedom is non-positive.
 */
public StudentDistribution( int n) throws IllegalArgumentException
{
    if ( n <= 0 )
        throw new IllegalArgumentException(
                                "Degree of freedom must be positive");
    defineParameters( n);
}
/**
 * Create an instance of the receiver with parameters estimated from
 * the given histogram using best guesses. This method can be used to
 * find the initial values for a fit.
 * @param h DhbScientificCurves.Histogram
 * @exception java.lang.IllegalArgumentException
 *                            when no suitable parameter can be found.
 */
public StudentDistribution( Histogram h)
{
    double variance = h.variance();
    if ( variance <= 0 )
        throw new IllegalArgumentException(
        "Student distribution is only defined for positive variance");
    defineParameters( (int) Math.max( 1, 
                                Math.round( 2 / (1 - 1 / variance))));
}
/**
 * @return double average of the distribution.
 */
public double average()
{
    return 0;
}
/**
 * @return double
 * @param x double
 * @exception java.lang.IllegalArgumentException
 *                                        if the argument is illegal.
 */
public double confidenceLevel( double x)
                                    throws IllegalArgumentException
{
    return x < 0
            ? Double.NaN
            : symmetricAcceptance( x) * 100;
}
/**
 * @param n int    degree of freedom
 */
public void defineParameters ( int n)
{
    dof = n;
    norm = -( Math.log( dof) * 0.5 
                            + GammaFunction.logBeta( dof * 0.5, 0.5));
}
/**
 * Returns the probability of finding a random variable smaller
 * than or equal to x.
 * @return integral of the probability density function from -infinity to x.
 * @param x double upper limit of integral.
 */
public double distributionValue(double x)
{
    if ( x == 0 )
        return 0.5;
    double acc = symmetricAcceptance( Math.abs( x));
    return x > 0 ? 1 + acc : 1 - acc;
}
/**
 * @return DhbIterations.IncompleteBetaFunction
 */
private IncompleteBetaFunction incompleteBetaFunction( )
{
    if ( incompleteBetaFunction == null)
        incompleteBetaFunction = new IncompleteBetaFunction( dof / 2,
                                                                0.5);
    return incompleteBetaFunction;
}
/**
 * @return double kurtosis of the distribution.
 */
public double kurtosis( )
{
    return dof > 4 ? 6 / ( dof - 4) : Double.NaN;
}
/**
 * @return java.lang.String the name of the distribution.
 */
public String name()
{
    return "Student distribution";
}
/**
 * @return double[] an array containing the parameters of 
 *                                                the distribution.
 */
public double[] parameters()
{
    double[] answer = new double[1];
    answer[0] = dof;
    return answer;
}
/**
 * @return double a random number distributed according to the receiver.
 */
public double random( )
{
    if ( chiSquareDistribution == null )
    {
        chiSquareDistribution = new ChiSquareDistribution( dof - 1);
    }
    return generator().nextGaussian() * Math.sqrt( ( dof - 1) / chiSquareDistribution.random());
}
/**
 * This distribution cannot be fitted because the parameter is an integer.
 * @param p double[]    assigns the parameters
 */
public void setParameters( double[] params)
{
    defineParameters( Math.round( (float) params[0]));
}
/**
 * @return double skewness of the distribution.
 */
public double skewness( )
{
    return 0;
}
/**
 * @return double    integral from -x to x
 * @param x double
 */
private double symmetricAcceptance( double x)
{
    return incompleteBetaFunction().value( dof / ( x * x + dof));
}
/**
 * @return double probability density function
 * @param x double random variable
 */
public double value( double x)
{
    return Math.exp( norm - Math.log( x * x / dof + 1) * ( dof + 1) / 2);
}
/**
 * @return double variance of the distribution.
 */
public double variance ( )
{
    return dof > 2 ? dof / ( dof - 2) : Double.NaN;
}
}
\end{verbatim}