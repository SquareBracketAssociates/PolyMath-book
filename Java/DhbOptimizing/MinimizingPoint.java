\begin{verbatim}
package DhbOptimizing;

import DhbInterfaces.OneVariableFunction;
/**
 * Point & function holder used in minimizing one-variable functions.
 *
 * @author Didier H. Besset
 */
public class MinimizingPoint extends OptimizingPoint {
/**
 * Constructor method.
 * @param x double
 * @param f DhbInterfaces.OneVariableFunction
 */
public MinimizingPoint(double x, OneVariableFunction f)
{
    super(x, f);
}
/**
 * @return boolean    true if the receiver is "better" than
 *                                                the supplied point
 * @param point OptimizingPoint
 */
public boolean betterThan(OptimizingPoint point)
{
    return getValue() < point.getValue();
}
/**
 * (used by method toString).
 * @return java.lang.String
 */
protected final String printedKey()
{
    return " min@";
}
}
\end{verbatim}