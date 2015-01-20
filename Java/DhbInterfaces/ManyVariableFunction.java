\begin{verbatim}
package DhbInterfaces;

/**
 * ManyVariableFunction is an interface for mathematical functions
 * of many variables, that is functions of the form:
 *                 f(X) where X is a vector.
 *
 * @author Didier H. Besset
 */
public interface ManyVariableFunction
{

/**
 * Returns the value of the function for the specified vector.
 */
public double value ( double[] x);
}
\end{verbatim}