\begin{verbatim}
package DhbIterations;

import DhbInterfaces.OneVariableFunction;
/**
 * Trapeze integration method
 *
 * @author Didier H. Besset
 */
public class TrapezeIntegrator extends FunctionalIterator
{
    /**
     * Low integral bound.
     */
    private double from;
    /**
     * High integral bound.
     */
    private double to;
    /**
     * Sum
     */
    protected double sum;
    /**
     * Interval partition.
     */
    private double step;
/**
 * Constructor
 * @param func DhbInterfaces.OneVariableFunction
 * @param from double
 * @param to double
 */
public TrapezeIntegrator(OneVariableFunction f, double from, double to)
{
    super( f);
    setInterval( from, to);
    setMaximumIterations( 13);
}
public double evaluateIteration()
{
    double oldResult = result;
    result = highOrderSum();
    return relativePrecision( Math.abs( result - oldResult));
}
/**
 * @return double
 */
protected double highOrderSum()
{
    double x = from + 0.5 * step;
    double newSum = 0;
    while ( x < to )
    {
        newSum += f.value(x);
        x += step;
    }
    sum = ( step * newSum + sum) * 0.5;
    step *= 0.5;
    return sum;
}
public void initializeIterations()
{
    step = to - from;
    sum = ( f.value(from) + f.value(to)) * step * 0.5;
    result = sum;
}
/**
* Defines integration interval.
* @param double a low integral bound.
* @param double b high integral bound.
*/
public void setInterval( double a, double b)
{
    from = a;
    to = b;
}
}
\end{verbatim}