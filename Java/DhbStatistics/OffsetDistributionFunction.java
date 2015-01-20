\begin{verbatim}
package DhbStatistics;

import DhbInterfaces.OneVariableFunction;
/**
 * This class is used to find the inverse distribution function of
 * a probability density function.
 * 
 * @author Didier H. Besset
 */
public final class OffsetDistributionFunction
                                        implements OneVariableFunction
{
    /**
     * Probability density function.
     */
    private ProbabilityDensityFunction probabilityDensity;
    /**
     * Value for which the inverse value is desired.
     */
    private double offset;


/**
 * Create a new instance with given parameters.
 * @param p statistics.ProbabilityDensityFunction
 * @param x double
 */
protected OffsetDistributionFunction ( ProbabilityDensityFunction p,
                                                            double x)
{
    probabilityDensity = p;
    offset = x;
}
/**
 * @return distribution function minus the offset.
 */
public double value(double x)
{
    return probabilityDensity.distributionValue( x) - offset;
}
}
\end{verbatim}