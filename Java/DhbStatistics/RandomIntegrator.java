\begin{verbatim}
package DhbStatistics;


import DhbInterfaces.ManyVariableFunction;
/**
 * Computes the integral of a multi-variable function using Monte Carlo approximation.
 *
 * @version 1.0 31 Jul 1998
 * @author Didier H. Besset
 */
public class RandomIntegrator
{
	/**
	 * Function to integrate.
	 */
	private ManyVariableFunction f;
	/**
	 * Low integral bound.
	 */
	private double[] a;
	/**
	 * Integral range.
	 */
	private double[] range;

	/**
	* Constructor method. from and to must have the same dimension,
	* otherwise an ArrayStoreException is thrown by the system
	* @param func a function of one variable.
	* @param from low limit of integral (array is copied).
	* @param to high limit of integral (array is copied).
	*/
public RandomIntegrator ( ManyVariableFunction func, double[] from, double[] to)
{
	f = func;
	int n = from.length;
	a = new double[n];
	range = new double[n];
	for ( int i = 0; i < n; i++)
	{
		a[i] = from[i];
		range[i] = to[i] - from[i];
	}
}
/**
 * Compute the integral.
 * @param totalCount number of Monte Carlo trials.
 * @param upperLimit value larger than the maximum of the function over the integral range.
 * @return integral value
 */
public double getResult ( int totalCount, double upperLimit)
{
	int n = a.length;
	double[] x = new double[ n];
	int hits = 0;
	for ( int count = 0; count < totalCount; count ++)
	{
		for ( int i = 0; i < n; i++ )
			x[i] = a[i] + Math.random() * range[i];
		if ( Math.random() * upperLimit <= f.value( x) )
			hits++;
	}
	double answer = upperLimit * (double) hits / (double) totalCount;
	for ( int i = 0; i < n; i++ )
		answer *= range[i];
	return 	answer;
}
}
\end{verbatim}