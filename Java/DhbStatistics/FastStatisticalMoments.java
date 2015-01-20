\begin{verbatim}
package DhbStatistics;

/**
 * Fast StatisticalMonents (at the cost of accuracy)
 * 
 * @author Didier H. Besset
 */
public class FastStatisticalMoments extends StatisticalMoments 
{
/**
 * Default constructor method.
 */
public FastStatisticalMoments()
{
    super();
}
/**
 * Constructor method.
 * @param n int
 */
public FastStatisticalMoments(int n)
{
    super(n);
}
/**
 * Accumulate a random variable.
 * @param x value of the random variable.
 */
public void accumulate( double x)
{
    double value = 1.0;
    for ( int n=0; n < moments.length; n++)
    {
        moments[n] += value;
        value *= x;
    }
}
/**
 * @return double average.
 */
public double average()
{
    return moments[1] / moments[0];
}
/**
 * The kurtosis measures the sharpness of the distribution near the maximum.
 * Note: The kurtosis of the Normal distribution is 0 by definition.
 * @return double kurtosis.
 */
public double kurtosis() throws ArithmeticException
{
    if ( moments[0] < 4 )
        return Double.NaN;
    double x1 = average();
    double x2 = moments[2] / moments[0];
    double x3 = moments[3] / moments[0];
    double x4 = moments[4] / moments[0];
    double xSquared = x1 * x1;
    double m4 = x4 - (4 * x1 * x3) + 3 * xSquared
                                                * ( 2 * x2- xSquared);
    double kFact = moments[0] * (moments[0] + 1)
                            / ( (moments[0] - 1) * (moments[0] - 2)
                                                * (moments[0] - 3));
    double kConst = 3 * (moments[0] - 1) * (moments[0] - 1)
                            / ( (moments[0] - 2) * (moments[0] - 3));
    x4 = variance();
    x4 *= x4;
    return kFact * (m4 * moments[0] / x4) - kConst;
}
/**
 * @return double skewness.
 */
public double skewness() throws ArithmeticException
{
    if ( moments[0] < 3 )
        return Double.NaN;
    double x1 = average();
    double x2 = moments[2] / moments[0];
    double x3 = moments[3] / moments[0];
    double m3 = x3 + x1 * ( 2 * x1 * x1 - 3 * x2);
    x1 = standardDeviation();
    x2 = x1 * x1;
    x2 *= x1;
    return m3 * moments[0] * moments[0] / ( x2 * ( moments[0] - 1) 
                                                * ( moments[0] - 2));
}
/**
 * Unnormalized central moment of 2nd order
 * (needed to compute the t-test).
 * @return double
 */
public double unnormalizedVariance()
{
    double average = average();
    return moments[2] - average * average * moments[0];
}
/**
 * Note: the variance includes the Bessel correction factor.
 * @return double variance.
 */
public double variance() throws ArithmeticException
{
    if ( moments[0] < 2 )
        return Double.NaN;
    double average = average();
    return ( moments[0] / ( moments[0] - 1) )
                    * (moments[2] / moments[0] - average * average);
}
}
\end{verbatim}