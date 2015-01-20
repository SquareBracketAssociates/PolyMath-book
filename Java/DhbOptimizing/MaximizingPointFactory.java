\begin{verbatim}
package DhbOptimizing;

/**
 * Factory of point/vector & function holders for maximizing functions.
 *
 * @author Didier H. Besset
 */
public class MaximizingPointFactory extends OptimizingPointFactory {
/**
 * Constructor method.
 */
public MaximizingPointFactory() {
    super();
}
/**
 * @return OptimizingPoint    an maximizing point strategy.
 */
public OptimizingPoint createPoint(double x, 
                                DhbInterfaces.OneVariableFunction f)
{
    return new MaximizingPoint( x, f);
}
/**
 * @return OptimizingVector    an maximizing vector strategy.
 */
public OptimizingVector createVector(double[] v, 
                                DhbInterfaces.ManyVariableFunction f)
{
    return new MaximizingVector( v, f);
}
}
\end{verbatim}