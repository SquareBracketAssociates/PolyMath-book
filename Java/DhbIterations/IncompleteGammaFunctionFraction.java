\begin{verbatim}
package DhbIterations;

/**
 * Continued fraction for the incompleteGamma function
 *
 * @author Didier H. Besset
 */
public class IncompleteGammaFunctionFraction extends ContinuedFraction
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
 * Constructor method.
 * @param a double
 */
public IncompleteGammaFunctionFraction ( double a)
{
    alpha = a;
}
/**
 * Compute the pair numerator/denominator for iteration n.
 * @param n int
 */
protected void computeFactorsAt(int n)
{
    sum += 2;
    factors[0] = ( alpha - n) * n;
    factors[1] = sum;
    return;
}
protected double initialValue()
{
    sum = x - alpha + 1;
    return sum;
}
}
\end{verbatim}