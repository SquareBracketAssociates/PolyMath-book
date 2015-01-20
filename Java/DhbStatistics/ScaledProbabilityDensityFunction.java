\begin{verbatim}
package DhbStatistics;

import DhbInterfaces.ParametrizedOneVariableFunction;
import DhbScientificCurves.Histogram;
/**
 * Construct a function from a probability density function
 * for a given norm.
 * 
 * @author Didier H. Besset
 */
public class ScaledProbabilityDensityFunction
                            implements ParametrizedOneVariableFunction
{
    /**
     * Total count of the histogram.
     */
    private double count;
    /**
     * Bin width of the histogram.
     */
    private double binWidth;
    /**
     * Probability density function
     */
    private ProbabilityDensityFunction density;

/**
 * @param pdf DhbStatistics.ProbabilityDensityFunction
 * @param n long
 * @param w double
 */
public ScaledProbabilityDensityFunction(
                    ProbabilityDensityFunction pdf, long n, double w)
{
    density = pdf;
    setCount( n);
    binWidth = w;
}
/**
 * @param f statistics.ProbabilityDensity
 * @param hist curves.Histogram
 */
public ScaledProbabilityDensityFunction (
                        ProbabilityDensityFunction f, Histogram hist)
{
    this( f, hist.count(), hist.getBinWidth());
}
/**
 * The array contains the parameters of the distribution
 * and the estimated number of events.
 * @return double[] an array containing the parameters of 
 *                                                the distribution.
 */
public double[] parameters()
{
    double[] parameters = density.parameters();
    double[] answer = new double[ parameters.length + 1];
    for ( int i = 0; i < parameters.length; i++ )
        answer[i] = parameters[i];
    answer[parameters.length] = count;
    return answer;
}
/**
 * @param x double    total count in the receiver
 */
public void setCount(double x)
{
    count = x;
}
/**
 * @param n int    total count in the receiver
 */
public void setCount(int n)
{
    count = n;
}
/**
 * @param n int    total count in the receiver
 */
public void setCount(long n)
{
    count = n;
}
/**
 * @param p double[]    assigns the parameters
 */
public void setParameters( double[] params)
{
    count = params[params.length-1];
    density.setParameters( params); 
}
/**
 * @return java.lang.String
 */
public String toString()
{
    StringBuffer sb = new StringBuffer();
    sb.append("Scaled ");
    sb.append(density);
    return sb.toString();
}
/**
 * @return double the value of the function.
 * @param x double
 */
public double value( double x)
{
    return count * binWidth * density.value( x);
}
/**
 * Evaluate the function and the gradient of the function with respect
 * to the parameters.
 * @return double[]    0: function's value, 1,2,...,n function's gradient
 * @param x double
 */
public double[] valueAndGradient( double x)
{
    double[] dpg = density.valueAndGradient(x);
    double[] answer = new double[dpg.length+1];
    double r = binWidth * count;
    for ( int i = 0; i < dpg.length; i++ )
        answer[i] = dpg[i] * r;
    answer[dpg.length] = dpg[0] * binWidth;
    return answer;
}
}
\end{verbatim}