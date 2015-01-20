\begin{verbatim}
package DhbStatistics;


import DhbFunctionEvaluation.PolynomialFunction;
/**
 * Normal distribution, a.k.a. Gaussian distribution.
 * 
 */
public final class NormalDistribution extends ProbabilityDensityFunction
{
	private static double baseNorm = Math.sqrt( 2 * Math.PI);
	/**
	 * Series to compute the error function.
	 */
	private static PolynomialFunction errorFunctionSeries;
	/**
	 * Constant needed to compute the argument to the error function series.
	 */
	private static double errorFunctionConstant = 0.2316419;

/**
 * @return error function for the argument.
 * @param x double
 */
public static double errorFunction ( double x)
{
	if ( errorFunctionSeries == null )
	{
		double[] coeffs = { 0.31938153, -0.356563782, 1.781477937, -1.821255978, 1.330274429};
		errorFunctionSeries = new PolynomialFunction( coeffs);
	}
	if ( x > 0 )
		return 1 - errorFunction( -x);
	double t = 1 / (1 - errorFunctionConstant * x);	
	return t * errorFunctionSeries.value( t) * normal( x);
}
/**
 * @return the density probability function for a (0,1) normal distribution.
 * @param x double	value for which the probability is evaluated.
 */
static public double normal( double x)
{
	return Math.exp( -0.5 * x * x) / baseNorm;
}
}
\end{verbatim}