\begin{verbatim}
package DhbIterations;

/**
 * Simpson integration method
 *
 * @author Didier H. Besset
 */
public class SimpsonIntegrator extends TrapezeIntegrator
{
/**
 * SimpsonIntegrator constructor.
 * @param f DhbInterfaces.OneVariableFunction
 * @param from double
 * @param to double
 */
public SimpsonIntegrator(DhbInterfaces.OneVariableFunction f,
                                            double from, double to)
{
    super(f, from, to);
}
/**
 * @return double
 */
public double evaluateIteration()
{
    if ( getIterations() < 2 )
    {
        highOrderSum();
        return getDesiredPrecision( );
    }
    double oldResult = result;
    double oldSum = sum;
    result = ( 4 * highOrderSum() - oldSum) / 3.0;
    return relativePrecision( Math.abs( result - oldResult));
}
}
\end{verbatim}