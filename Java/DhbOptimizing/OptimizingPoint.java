\begin{verbatim}
package DhbOptimizing;

import DhbInterfaces.OneVariableFunction;
/**
 * Point & function holder used in optimizing one-variable functions.
 *
 * @author Didier H. Besset
 */
public abstract class OptimizingPoint
{
    /**
     * Value of the function to optimize.
     */
    private double value;
    /**
     * Position at which the value was evaluated.
     */
    private double position;
/**
 * Constructor method
 * @param x double    position at which the goal function is evaluated.
 * @param f OneVariableFunction    function to optimize.
 */
public OptimizingPoint( double x, OneVariableFunction f)
{
    position = x;
    value = f.value(x);
}
/**
 * @return boolean    true if the receiver is "better" than 
 *                                                the supplied point
 * @param point OptimizingPoint
 */
public abstract boolean betterThan( OptimizingPoint entity);
/**
 * @return double    the receiver's position
 */
public double getPosition()
{
    return position;
}
/**
 * @return double    the value of the function at the receiver's
 *                                                            position
 */
public double getValue()
{
    return value;
}
/**
 * (used by method toString).
 * @return java.lang.String
 */
protected abstract String printedKey();
/**
 * @return java.lang.String
 */
public String toString()
{
    StringBuffer sb = new StringBuffer();
    sb.append( value);
    sb.append(printedKey());
    sb.append( position);
    return sb.toString();
}
}
\end{verbatim}