\begin{verbatim}
package DhbIterations;

/**
 * InifiniteSeries
 *
 * @author Didier H. Besset
 */
public abstract class InifiniteSeries extends IterativeProcess
{
    /**
     * Best approximation of the sum.
     */
    private double result;
    /**
     * Series argument.
     */
    protected double x;
    /**
     * Value of the last term.
     */
    protected double lastTerm;

/**
 * Computes the n-th term of the series and stores it in lastTerm.
 * @param n int
 */
protected abstract void computeTermAt ( int n);
public double evaluateIteration()
{
    computeTermAt( getIterations());
    result += lastTerm;
    return relativePrecision( Math.abs( lastTerm), Math.abs( result));
}
/**
 * @return double
 */
public double getResult ( )
{
    return result;
}
/**
 * Set the initial value for the sum.
 */
public void initializeIterations()
{
    result = initialValue();
}
/**
 * @return double        the 0-th term of the series
 */
protected abstract double initialValue ( );
/**
 * @param r double    the value of the series argument.
 */
public void setArgument ( double r)
{
    x = r;
    return;
}
}
\end{verbatim}