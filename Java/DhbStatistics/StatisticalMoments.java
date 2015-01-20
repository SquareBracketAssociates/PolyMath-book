\begin{verbatim}
package DhbStatistics;

/**
 * A StatisticalMoments accumulates statistical moments of a random variable.
 *
 * @author Didier H. Besset
 */
public class StatisticalMoments
{
    /**
     * Vector containing the points.
     */
    protected double[] moments;
/**
 * Default constructor methods: declare space for 5 moments.
 */
public StatisticalMoments()
{
    this( 5);
}
/**
 * General constructor methods.
 * @param n number of moments to accumulate.
 */
public StatisticalMoments( int n)
{
    moments = new double[n];
    reset();
}
/**
 * @param x double    value to accumulate
 */
public void accumulate( double x)
{
    double n = moments[0];
    double n1 = n + 1;
    double delta = ( moments[1] - x) / n1;
    double[] sums = new double[ moments.length];
    sums[0] = moments[0];
    moments[0] = n1;
    sums[1] = moments[1];
    moments[1] -= delta;
    int[] pascal = new int[moments.length];
    pascal[0] = 1;
    pascal[1] = 1;
    double r1 = (double) n / (double) n1;
    double nk = -1;
    n = -n;
    double cterm = delta;
    double term;
    for ( int k = 2 ; k < moments.length; k++)
    {
        sums[k] = moments[k];
        nk = nk * n;
        cterm *= delta;
        term = (1 + nk) * cterm;
        for ( int l = k; l >= 2; l--)
        {
            pascal[l] += pascal[l-1];
            term += pascal[l] * sums[l];
            sums[l] *= delta;
        }
        pascal[1] += pascal[0];
        moments[k] = term * r1;
    }    
}
/**
 * @return double average.
 */
public double average()
{
    return moments[1];
}
/**
 * Returns the number of accumulated counts.
 * @return number of counts.
 */
public long count()
{
    return (long) moments[0];
}
/**
 * Returns the error on average. May throw divide by zero exception.
 * @return error on average.
 */
public double errorOnAverage()
{
    return Math.sqrt( variance() / moments[0]);
}
/**
 * @return double    F-test confidence level with data accumulated
 *                                            in the supplied moments.
 * @param m DhbStatistics.StatisticalMoments
 */
public double fConfidenceLevel( StatisticalMoments m)
{
    FisherSnedecorDistribution fDistr = new FisherSnedecorDistribution( (int) count(), (int) m.count());
    return fDistr.confidenceLevel( variance() / m.variance());
}
/**
 * The kurtosis measures the sharpness of the distribution near
 *                                                        the maximum.
 * Note: The kurtosis of the Normal distribution is 0 by definition.
 * @return double kurtosis or NaN.
 */
public double kurtosis() throws ArithmeticException
{
    if ( moments[0] < 4 )
        return Double.NaN;
    double kFact = ( moments[0] - 2) * ( moments[0] - 3);
    double n1 = moments[0] - 1;
    double v = variance();
    return ( moments[4] * moments[0] * moments[0] * ( moments[0] + 1)
                    / ( v * v * n1) - n1 * n1 * 3) / kFact;
}
/**
 * Reset all counters.
 */
public void reset( )
{
    for ( int n=0; n < moments.length; n++)
        moments[n] = 0;
}
/**
 * @return double skewness.
 */
public double skewness() throws ArithmeticException
{
    if ( moments[0] < 3 )
        return Double.NaN;
    double v = variance();
    return moments[3] * moments[0] * moments[0] 
                    / ( Math.sqrt(v) * v * ( moments[0] - 1)
                                                * ( moments[0] - 2));
}
/**
 * Returns the standard deviation. May throw divide by zero exception.
 * @return double standard deviation.
 */
public double standardDeviation()
{
    return Math.sqrt( variance());
}
/**
 * @return double    t-test confidence level with data accumulated
 *                                            in the supplied moments.
 * Approximation for the case where the variance of both sets may
 *                                                            differ.
 * @param m DhbStatistics.StatisticalMoments
 */
public double tApproximateConfidenceLevel( StatisticalMoments m)
{
    StudentDistribution tDistr = new StudentDistribution(
                                        (int) ( count()+m.count()-2));
    return tDistr.confidenceLevel( ( average() / standardDeviation()
                                        - m.average()
                                        / m.standardDeviation()) 
                                        / Math.sqrt(1/count()
                                                    +1/m.count()));
}
/**
 * @return double    t-test confidence level with data accumulated
 *                                            in the supplied moments.
 * The variance of both sets is assumed to be the same.
 * @param m DhbStatistics.StatisticalMoments
 */
public double tConfidenceLevel( StatisticalMoments m)
{
    int dof =  (int) ( count()+m.count()-2);
    double sbar = Math.sqrt( ( unnormalizedVariance()
                                + m.unnormalizedVariance()) / dof);
    StudentDistribution tDistr = new StudentDistribution( dof);
    return tDistr.confidenceLevel( ( average() - m.average()) 
                                    / ( sbar * Math.sqrt(1/count()
                                                    +1/m.count())));
}
/**
 * @return double
 */
public double unnormalizedVariance()
{
    return moments[2] * moments[0];
}
/**
 * Note: the variance includes the Bessel correction factor.
 * @return double variance.
 */
public double variance() throws ArithmeticException
{
    if ( moments[0] < 2 )
        return Double.NaN;
    return unnormalizedVariance() / ( moments[0] - 1);
}
}
\end{verbatim}