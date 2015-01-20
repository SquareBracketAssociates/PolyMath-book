\begin{verbatim}
package DhbIterations;

/**
 * Series for the incompleteGamma function
 *
 * @author Didier H. Besset
 */
public class IncompleteGammaFunctionSeries extends InifiniteSeries
{
    /**
     * Series parameter.
     */
    private double alpha;
    /**
     * Auxiliary sum.
     */
    private double sum;

/**
 * Constructor method
 * @param a double    series parameter
 */
public IncompleteGammaFunctionSeries ( double a)
{
    alpha = a;
}
/**
 * Computes the n-th term of the series and stores it in lastTerm.
 * @param n int
 */
protected void computeTermAt(int n)
{
    sum += 1;
    lastTerm *= x / sum;
    return;
}
/**
 * initializes the series and return the 0-th term.
 */
protected double initialValue()
{
    lastTerm = 1 / alpha;
    sum = alpha;
    return lastTerm;
}
}
\end{verbatim}