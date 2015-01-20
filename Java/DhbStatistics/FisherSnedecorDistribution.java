\begin{verbatim}
package DhbStatistics;

import DhbFunctionEvaluation.GammaFunction;
import DhbIterations.IncompleteBetaFunction;
import DhbScientificCurves.Histogram;
/**
 * Fisher-Snedecor distribution
 * (distribution used to perform the F-test).
 *
 * @author Didier H. Besset
 */
public final class FisherSnedecorDistribution
                                extends ProbabilityDensityFunction
{
    /**
     * First degree of freedom.
     */
    protected int dof1;
    /**
     * Second degree of freedom.
     */
    private int dof2;
    /**
     * Norm (stored for efficiency).
     */
    private double norm;
    /**
     * Function used to compute the distribution.
     */
    private IncompleteBetaFunction incompleteBetaFunction = null;
    /**
     * Auxiliary distributions for random number generation.
     */
    private ChiSquareDistribution chiSquareDistribution1 = null;
    private ChiSquareDistribution chiSquareDistribution2 = null;
/**
 * Create a new instance of the Fisher-Snedecor distribution with
 *                                            given degrees of freedom.
 * @param n1 int    first degree of freedom
 * @param n2 int    second degree of freedom
 * @exception java.lang.IllegalArgumentException
 *            one of the specified degrees of freedom is non-positive.
 */
public FisherSnedecorDistribution( int n1, int n2)
                                    throws IllegalArgumentException
{
    if ( n1 <= 0 )
        throw new IllegalArgumentException(
                        "First degree of freedom must be positive");
    if ( n2 <= 0 )
        throw new IllegalArgumentException(
                        "Second degree of freedom must be positive");
    defineParameters( n1, n2);
}
/**
 * Create an instance of the receiver with parameters estimated from
 * the given histogram using best guesses. This method can be used to
 * find the initial values for a fit.
 * @param h Histogram
 * @exception java.lang.IllegalArgumentException
 *                            when no suitable parameter can be found.
 */
public FisherSnedecorDistribution( Histogram h) throws IllegalArgumentException
{
    if ( h.getMinimum() < 0 )
        throw new IllegalArgumentException(
"Fisher-Snedecor distribution is only defined for non-negative values");
    int n2 = (int) Math.round(2 / (1 - 1 / h.average()));
    if ( n2 <= 0 )
        throw new IllegalArgumentException(
    "Fisher-Snedecor distribution has positive degrees of freedom");
    double a = 1 - ( n2 - 2) * ( n2 - 4) * h.variance() / (2 * 2 * n2);
    int n1 = (int) Math.round( 0.7 * ( n2 - 2) / a);
    if ( n1 <= 0 )
        throw new IllegalArgumentException(
    "Fisher-Snedecor distribution has positive degrees of freedom");
    defineParameters( n1, n2);
}
/**
 * @return double average of the distribution.
 */
public double average()
{
    return dof2 > 2
            ? dof2 / ( dof2 - 2)
            : Double.NaN;
}
/**
 * @return double
 * @param x double
 * @exception java.lang.IllegalArgumentException
 *                        if the argument is outside the expected range.
 */
public double confidenceLevel( double x)
                                    throws IllegalArgumentException
{
    return x < 0 ? Double.NaN
                 : distributionValue( x) * 100;
}
/**
 * Assigns new degrees of freedom to the receiver.
 * Compute the norm of the distribution after a change of parameters.
 * @param n1 int    first degree of freedom
 * @param n2 int    second degree of freedom
 */
public void defineParameters ( int n1, int n2)
{
    dof1 = n1;
    dof2 = n2;
    double nn1 = 0.5 * n1;
    double nn2 = 0.5 * n2;
    norm = nn1 * Math.log( n1) + nn2 * Math.log( n2) 
                                - GammaFunction.logBeta( nn1, nn2);
    incompleteBetaFunction = null;
    chiSquareDistribution1 = null;
    chiSquareDistribution2 = null;
}
/**
 * Returns the probability of finding a random variable smaller
 * than or equal to x.
 * @return integral of the probability density function from 0 to x.
 * @param x double upper limit of integral.
 */
public double distributionValue ( double x)
{
    return incompleteBetaFunction().value( dof2 / ( x * dof1 + dof2));
}
private IncompleteBetaFunction incompleteBetaFunction()
{
    if ( incompleteBetaFunction == null )
        incompleteBetaFunction = new IncompleteBetaFunction(
                                            0.5 * dof1, 0.5 * dof2);
    return incompleteBetaFunction;
}
/**
 * @return java.lang.String    name of the distribution.
 */
public String name ( )
{
    return "Fisher-Snedecor distribution";
}
/**
 * @return double[] an array containing the parameters of 
 *                                                the distribution.
 */
public double[] parameters ( )
{
    double[] answer = new double[2];
    answer[0] = dof1;
    answer[1] = dof2;
    return answer;
}
/**
 * @return double a random number distributed according to the receiver.
 */
public double random( )
{
    if ( chiSquareDistribution1 == null )
    {
        chiSquareDistribution1 = new ChiSquareDistribution( dof1);
        chiSquareDistribution2 = new ChiSquareDistribution( dof2);
    }
    return chiSquareDistribution1.random() * dof2
            / ( chiSquareDistribution2.random() * dof1);
}
/**
 * This function cannot be fitted because the parameters are integers.
 * @param p double[]    assigns the parameters
 */
public void setParameters( double[] params)
{
    defineParameters( Math.round( (float) params[0]), 
                                    Math.round( (float) params[1]));
}
/**
 * @return java.lang.String
 */
public String toString()
{
    StringBuffer sb = new StringBuffer();
    sb.append("Fisher-Snedecor distribution (");
    sb.append(dof1);
    sb.append(',');
    sb.append(dof2);
    sb.append(')');
    return sb.toString();
}
/**
 * @return double probability density function
 * @param x double random variable
 */
public double value( double x)
{
    return x > 0
            ? Math.exp( norm + Math.log( x) * ( dof1 / 2 - 1) 
                            - Math.log( x * dof1 + dof2) 
                                            * ( ( dof1 + dof2) / 2))
            : 0;
}
/**
 * @return double variance of the distribution.
 */
public double variance ( )
{
    return dof2 > 4
            ? dof2 * dof2 * 2 * ( dof1 + dof2 + 2) 
                                / ( dof1 * ( dof2 - 2) * ( dof2 - 4))
            : Double.NaN;
}
}
\end{verbatim}