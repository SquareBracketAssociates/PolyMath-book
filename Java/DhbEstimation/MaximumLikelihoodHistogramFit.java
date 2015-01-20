\begin{verbatim}
package DhbEstimation;

import DhbInterfaces.ParametrizedOneVariableFunction;
import DhbScientificCurves.Histogram;
import DhbStatistics.ScaledProbabilityDensityFunction;
import DhbStatistics.ProbabilityDensityFunction;
/**
 * Maximum likelihood fit
 *
 * @author Didier H. Besset
 */
public class MaximumLikelihoodHistogramFit extends LeastSquareFit 
{
/**
 * Histogram containing the data
 */
    private Histogram histogram;
/**
 * Estimated total count in histogram
 */
    private double count;
/**
 * Estimated error on total count in histogram
 */
    private double countError;
/**
 * Constructor method.
 * @param pts Histogram
 * @param f DhbInterfaces.ParametrizedOneVariableFunction
 */
public MaximumLikelihoodHistogramFit(Histogram hist,
                                        ProbabilityDensityFunction f)
{
    histogram = hist;
    result = new ScaledProbabilityDensityFunction ( f, hist);
    initializeSystem( f.parameters().length);
}
/**
 * @param wp DhbEstimation.WeightedPoint
 */
protected void accumulate( WeightedPoint wp)
{
    double[] fg = result.valueAndGradient( wp.xValue());
    if ( fg[0] == 0 )
        return;
    double invProb = 1  / fg[0];
    double temp = wp.yValue() * invProb;
    for( int i = 0; i < systemConstants.length; i++ )
    {
        systemConstants[i] += fg[i+1] * temp;
        for( int j = 0; j <= i; j++ )
            systemMatrix[i][j] += fg[i+1] * fg[j+1] * temp * invProb;
    }
}
/**
 * Append the name of the fit to the supplied string buffer
 * @param sb java.lang.StringBuffer
 */
protected void appendFitName( StringBuffer sb)
{
    sb.append("Maximum likelihood fit with ");
}
/**
 * Append the normalization and its error to the fit results
 * @param sb java.lang.StringBuffer
 */
protected void appendNormalization( StringBuffer sb)
{
    java.text.DecimalFormat fmt = new java.text.DecimalFormat("###0.0");
    sb.append("\n\t");
    sb.append( fmt.format( count));
    sb.append("\t+-");
    sb.append( fmt.format( countError));
}
/**
 * Computes the changes in the parameters:
 * since the normalization is not fitted, the change to the
 * normalization (last parameter) is set to zero.
 */
protected double[] computeChanges()
{
    double[] changes = super.computeChanges();
    double[] answer = new double[changes.length+1];
    for ( int i = 0; i < changes.length; i++ )
        answer[i] = changes[i];
    answer[changes.length] = 0;
    return answer;
}
/**
 * Computes the estimated normalization and variance on it.
 */
private void computeNormalization()
{
    double numerator = 0;
    double denominator = 0;
    double temp;
    WeightedPoint wp;
    for ( int i = 0; i < getDataSetSize(); i++ )
    {
        wp = weightedPointAt(i);
        temp = result.value( wp.xValue());
        if ( temp != 0 )
            {
                numerator += wp.yValue() * wp.yValue() / temp;
                denominator += temp;
            }
    }
    count = Math.sqrt( numerator / denominator);
    countError = Math.sqrt( 0.25 * numerator / count);
}
public void finalizeIterations()
{
    computeNormalization();
    getDistribution().setCount( count);
    super.finalizeIterations();
}
/**
 * @return int    number of data points.
 */
protected int getDataSetSize()
{
    return histogram.size();
}
/**
 * @return ScaledProbabilityDensityFunction    the fitted function
 */
public ScaledProbabilityDensityFunction getDistribution()
{
    return (ScaledProbabilityDensityFunction) getResult();
}
public void initializeIterations()
{
    getDistribution().setCount( 1);
    count = histogram.totalCount();
}
/**
 * @return DhbEstimation.WeightedPoint n-th weighted data point
 * @param n int
 */
protected WeightedPoint weightedPointAt( int n)
{
    return histogram.weightedPointAt(n);
}
}
\end{verbatim}