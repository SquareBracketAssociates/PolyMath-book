\begin{verbatim}
package DhbStatistics;

import DhbScientificCurves.Histogram;
/**
 * Distribution constructed on a histogram.
 *
 * @author Didier H. Besset
 */
public class HistogrammedDistribution extends ProbabilityDensityFunction
{
    Histogram histogram;
/**
 * @return double average of the histogram.
 */
public double average()
{
    return histogram.average();
}
/**
 * Returns the probability of finding a random variable smaller
 * than or equal to x.
 * @return integral of the probability density function from -infinity to x.
 * @param x double upper limit of integral.
 */
public double distributionValue(double x)
{
    if ( x < histogram.getMinimum())
        return 0;
    else if ( x < histogram.getMaximum())
        return histogram.getCountsUpTo( x) / histogram.totalCount();
    else
        return 1;
}
/**
 * @return double
 * @param x1 double
 * @param x2 double
 */
public double distributionValue ( double x1, double x2)
{
    return histogram.getCountsBetween(Math.max(x1,
                                            histogram.getMinimum()),
                                        Math.min(x2,
                                            histogram.getMaximum()))
                                / histogram.totalCount();
}
/**
 * @return double kurtosis of the histogram.
 */
public double kurtosis()
{
    return histogram.kurtosis();
}
/**
 * @return java.lang.String name of the distribution.
 */
public String name()
{
    return "Experimental distribution";
}
/**
 * NOTE: this method is a dummy because the distribution
 * cannot be fitted.
 * @return double[] an array containing the parameters of 
 *                                                the distribution.
 */
public double[] parameters()
{
    return new double[0];
}
/**
 * This method is a dummy method, needed for the compiler because
 * the superclass requires implementation of the
 *                         interface ParametrizedOneVariableFunction.
 * Histogrammed distributions cannot be fitted.
 * 
 */
public void setParameters( double[] params)
{
}
/**
 * @return double skewness of the histogram.
 */
public double skewness()
{
    return histogram.skewness();
}
/**
 * @return double probability density function
 * @param x double random variable
 */
public double value( double x)
{
    return ( x >= histogram.getMinimum()
                                    || x < histogram.getMaximum() )
                ? histogram.getBinContent(x)
                                / (histogram.totalCount()
                                            + histogram.getBinWidth())
                : 0;
}
/**
 * @return double variance of the histogram.
 */
public double variance()
{
    return histogram.variance();
}
}
\end{verbatim}