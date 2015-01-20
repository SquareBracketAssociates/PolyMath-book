\begin{verbatim}
package DhbIterations;

import DhbInterfaces.OneVariableFunction;
/**
 * Iterative process based on a one-variable function,
 * having a single numerical result.
 *
 * @author Didier H. Besset
 */
public abstract class FunctionalIterator extends IterativeProcess
{
    /**
     * Best approximation of the zero.
     */
    protected double result = Double.NaN;
    /**
     * Function for which the zero will be found.
     */
    protected OneVariableFunction f;
/**
 * Generic constructor.
 * @param func OneVariableFunction
 * @param start double
 */
public FunctionalIterator(OneVariableFunction func)
{
    setFunction( func);
}
/**
* Returns the result (assuming convergence has been attained).
*/
public double getResult( )
{
    return result;
}
/**
 * @return double
 * @param epsilon double
 */
public double relativePrecision( double epsilon)
{
    return relativePrecision( epsilon, Math.abs( result));
}
/**
 * @param func DhbInterfaces.OneVariableFunction
 */
public void setFunction( OneVariableFunction func)
{
    f = func;
}
/**
 * @param x double
 */
public void setInitialValue( double x)
{
    result = x;
}
}
\end{verbatim}