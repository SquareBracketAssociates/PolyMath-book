\begin{verbatim}
package DhbInterfaces;

/**
 * ParametrizedOneVariableFunction is an interface for mathematical
 * functions of one variable depending on several parameters,
 * that is functions of the form f(x;p), where p is a vector.
 *
 * @author Didier H. Besset
 */
public interface ParametrizedOneVariableFunction
                                        extends OneVariableFunction
{
/**
 * @return double[]    array containing the parameters
 */
double[] parameters();
/**
 * @param p double[]    assigns the parameters
 */
void setParameters( double[] p);
/**
 * Evaluate the function and the gradient of the function with respect
 * to the parameters.
 * @return double[]    0: function's value, 1,2,...,n function's gradient
 * @param x double
 */
double[] valueAndGradient( double x);
}
\end{verbatim}