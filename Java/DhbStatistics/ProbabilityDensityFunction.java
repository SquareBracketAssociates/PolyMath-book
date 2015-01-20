\begin{verbatim}
package DhbStatistics;

import DhbFunctionEvaluation.DhbMath;
import DhbIterations.NewtonZeroFinder;
import DhbInterfaces.ParametrizedOneVariableFunction;
import java.util.Random;
/**
 * Subclasses of this class represent probability density function.
 * The value of the funtion f (x) represents the probability that a
 * continuous random variable takes values in the interval [x, x+dx[.
 * A norm is defined for the case where the function is overlayed over
 * a set of experimental points or a histogram.
 * 
 * @author Didier H. Besset
 */
public abstract class ProbabilityDensityFunction
                            implements ParametrizedOneVariableFunction
{
    /**
     * Random generator needed if random numbers are needed
     * (lazy initialization used).
     */
    private Random generator = null;

/**
 * Compute an approximation of the gradient.
 * @return double[]
 * @param x double
 */
public double[] approximateValueAndGradient( double x)
{
    double temp, delta;
    double[] params = parameters();
    double[] answer = new double[ params.length + 1];
    answer[0] = value( x);
    for ( int i = 0; i < params.length; i++ )
    {
        temp = params[i];
        delta = Math.abs( temp) > DhbMath.defaultNumericalPrecision()
                        ? 0.0001 * temp : 0.0001;
        params[i] += delta;
        setParameters( params);
        answer[i+1] = ( value(x) - answer[0]) / delta;
        params[i] = temp;
    }
    setParameters( params);
    return answer;
}
/**
 * @return double average of the distribution.
 */
public abstract double average ( );
/**
 * Returns the probability of finding a random variable smaller than
 * or equal to x.
 * This method assumes that the probability density is 0 for x < 0.
 * If this is not the case, the subclass must implement this method.
 * @return integral of the probability density function from 0 to x.
 * @param x double upper limit of intergral.
 */
public abstract double distributionValue ( double x);
/**
 * Returns the probability of finding a random variable between x1 and x2.
 * Computing is made using the method distributionValue(x).
 * This method should be used by distributions whose distributionValue
 * is computed using a method overiding the default one.
 * @return double integral of the probability density function from x1 to x2.
 * @param x1 double lower limit of intergral.
 * @param x2 double upper limit of intergral.
 */
public double distributionValue ( double x1, double x2)
{
    return distributionValue(x2) - distributionValue(x1);
}
/**
 * @return java.util.Random a random number generator.
 */
protected Random generator( )
{
    if ( generator == null)
        generator = new Random();
    return generator;
}
/**
 * @return double the value for which the distribution function is
 * equal to x.
 * @param x double value of the distribution function.
 * @exception java.lang.IllegalArgumentException
 *                            if the argument is not between 0 and 1.
 */
public double inverseDistributionValue ( double x) throws IllegalArgumentException
{
    if ( x < 0 || x > 1)
        throw new IllegalArgumentException( "argument must be between 0 and 1");
    return privateInverseDistributionValue( x);
}
/**
 * @return double kurtosis of the distribution.
 */
public double kurtosis( )
{
    return Double.NaN;
}
/**
 * @return java.lang.String the name of the distribution.
 */
public abstract String name ( );
/**
 * This method assumes that the range of the argument has been checked.
 * Computation is made using the Newton zero finder.
 * @return double the value for which the distribution function
 *                                                    is equal to x.
 * @param x double value of the distribution function.
 */
protected double privateInverseDistributionValue ( double x)
{
    OffsetDistributionFunction distribution = new OffsetDistributionFunction( this, x);
    NewtonZeroFinder zeroFinder = new NewtonZeroFinder( distribution, this, average());
    zeroFinder.setDesiredPrecision( DhbMath.defaultNumericalPrecision());
    zeroFinder.evaluate();
    return zeroFinder.getResult();
}
/**
 * @return double a random number distributed according to the receiver.
 */
public double random ( )
{
    return privateInverseDistributionValue( generator().nextDouble());
}
/**
 * Set the seed of the random generator used by thr receiver.
 * @param seed long
 */
public void setSeed(long seed)
{
    generator().setSeed( seed);
    return;
}
/**
 * @return double skewness of the distribution.
 */
public double skewness( )
{
    return Double.NaN;
}
/**
 * NOTE: subclass MUST implement one of the two method variance
 *         or standardDeviation.
 * @return double standard deviation of the distribution from the variance.
 */
public double standardDeviation( )
{
    return Math.sqrt( variance());
}
/**
 * @return java.lang.String name and parameters of the distribution.
 */
public String toString()
{
    StringBuffer sb = new StringBuffer();
    sb.append( name());
    char[] separator = { '(', ' '};
    double[] parameters = parameters();
    for ( int i = 0; i < parameters.length; i++)
    {
        sb.append( separator);
        sb.append( parameters[i]);
        separator[0] = ',';
    }
    sb.append(')');
    return sb.toString();
}
/**
 * Evaluate the distribution and the gradient of the distribution with respect
 * to the parameters.
 * @return double[]    0: distribution's value, 1,2,...,n distribution's gradient
 * @param x double
 */
public double[]valueAndGradient( double x)
{
    return approximateValueAndGradient( x);
}
/**
 * NOTE: subclass MUST implement one of the two method variance
 *         or standardDeviation.
 * @return double variance of the distribution from the standard deviation.
 */
public double variance ( )
{
    double v = standardDeviation();
    return v*v;
}
}
\end{verbatim}