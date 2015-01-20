\begin{verbatim}
package DhbOptimizing;

import DhbInterfaces.OneVariableFunction;
import DhbIterations.FunctionalIterator;
/**
 * Optimizer of one-variable functions
 * (uses golden search algorithm).
 *
 * @author Didier H. Besset
 */
public class OneVariableFunctionOptimizer extends FunctionalIterator
{
    private static double goldenSection = ( 3 - Math.sqrt(5)) / 2;
    /**
     * Best points found so far.
     */
    private OptimizingPoint[] bestPoints = null;
    /**
     * Optimizing strategy (minimum or maximum).
     */
    private OptimizingPointFactory pointFactory;
/**
 * Constructor method
 * @param func OneVariableFunction
 * @param pointCreator OptimizingPointFactory    a factory to create
 *                                                    strategy points
 */
public OneVariableFunctionOptimizer(OneVariableFunction func,
                                OptimizingPointFactory pointCreator)
{
    super(func);
    pointFactory = pointCreator;
}
/**
 * @return double    the relative precision on the result
 */
private double computePrecision()
{
    return relativePrecision( Math.abs( bestPoints[2].getPosition() 
                                        - bestPoints[1].getPosition()),
                              Math.abs( bestPoints[0].getPosition()));
}
/**
 * @return double    current precision of result
 */
public double evaluateIteration()
{
    if ( bestPoints[2].getPosition() - bestPoints[1].getPosition()
                > bestPoints[1].getPosition() - bestPoints[0].getPosition() )
        reducePoints(2);
    else
        reducePoints(0);
    result = bestPoints[1].getPosition();
    return computePrecision();
}
public void initializeIterations()
{
    OptimizingBracketFinder bracketFinder = 
                        new OptimizingBracketFinder( f, pointFactory);
    bracketFinder.setInitialValue( result);
    bracketFinder.evaluate();
    bestPoints = bracketFinder.getBestPoints();
}
/**
 * Apply bisection on points 1 and n
 * @param n int    index of worst point of bisected interval
 */
private void reducePoints( int n)
{
    double x = bestPoints[1].getPosition();
    x += goldenSection * ( bestPoints[n].getPosition() - x);
    OptimizingPoint newPoint = pointFactory.createPoint( x, f);
    if ( newPoint.betterThan( bestPoints[1]) )
    {
        bestPoints[2-n] = bestPoints[1];
        bestPoints[1] = newPoint;
    }
    else
        bestPoints[n] = newPoint;
}
/**
 * @return java.lang.String
 */
public String toString()
{
    StringBuffer sb = new StringBuffer();
    sb.append( getIterations());
    sb.append( " iterations, precision = ");
    sb.append( getPrecision());
    for ( int i = 0 ; i < bestPoints.length; i++ )
    {
        sb.append( '\n');
        sb.append( bestPoints[i]);
    }
    return sb.toString();
}
}
\end{verbatim}