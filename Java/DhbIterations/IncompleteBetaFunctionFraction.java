\begin{verbatim}
package DhbIterations;

/**
 * Incomplete Beta function fraction
 *
 * @author Didier H. Besset
 */
public class IncompleteBetaFunctionFraction extends ContinuedFraction
{
    /**
     * Fraction's parameters.
     */
    private double alpha1;
    private double alpha2;

/**
 * Constructor method.
 * @param a1 double
 * @param a2 double
 */
public IncompleteBetaFunctionFraction ( double a1, double a2)
{
    alpha1 = a1;
    alpha2 = a2;
}
/**
 * Compute the pair numerator/denominator for iteration n.
 * @param n int
 */
protected void computeFactorsAt(int n)
{
    int m = n / 2;
    int m2 = 2 * m;
    factors[0] = m2 == n
                    ? x * m * ( alpha2 - m)
                                / ( (alpha1 + m2) * (alpha1 + m2 - 1))
                    : -x * ( alpha1 + m) * (alpha1 + alpha2 + m)
                                / ( (alpha1 + m2) * (alpha1 + m2 + 1));
    return;
}
protected double initialValue()
{
    factors[1] = 1;
    return 1;
}
}
\end{verbatim}