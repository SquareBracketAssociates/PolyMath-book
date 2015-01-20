\begin{verbatim}
package DhbFunctionEvaluation;


import DhbInterfaces.OneVariableFunction;
/**
 * Evaluate an approximation of the derivative of a given function.
 *
 * @author Didier H. Besset
 */
public final class FunctionDerivative implements OneVariableFunction
{
    /**
     * Function for which the derivative is computed.
     */
    private OneVariableFunction f;
    /**
     * Relative interval variation to compute derivative.
     */
     private double relativePrecision = 0.0001;


/**
* Constructor method.
* @param func the function for which the derivative is computed.
*/
public FunctionDerivative( OneVariableFunction func)
{
    this( func, 0.000001);
}
/**
* Constructor method.
* @param func the function for which the derivative is computed.
* @param precision the relative step used to compute the derivative.
*/
public FunctionDerivative( OneVariableFunction func, double precision)
{
    f = func;
    relativePrecision = precision;
}
/**
 * Returns the value of the function's derivative
 * for the specified variable value.
 */
public double value( double x)
{
    double x1 = x == 0 ? relativePrecision
                       : x * ( 1 + relativePrecision);
    double x2 = 2 * x - x1;
    return (f.value(x1) - f.value(x2)) / (x1 - x2);
}
}
\end{verbatim}