\begin{verbatim}
package DhbOptimizing;

import DhbMatrixAlgebra.DhbVector;
import DhbMatrixAlgebra.DhbIllegalDimension;
import DhbInterfaces.ManyVariableFunction;
/**
 * Hill climbing optimizer using Powell's algorithm.
 *
 * @author Didier H. Besset
 */
public class HillClimbingOptimizer extends MultiVariableOptimizer
{
    /**
     * One dimensional optimizer used in each direction.
     */
    private OneVariableFunctionOptimizer unidimensionalFinder;
    /**
     * Projected goal function on independent directions.
     */
    private VectorProjectedFunction[] projections;
/**
 * Constructor method.
 * @param func DhbInterfaces.ManyVariableFunction
 * @param pointCreator DhbOptimizing.OptimizingPointFactory
 */
public HillClimbingOptimizer( ManyVariableFunction func, 
                    OptimizingPointFactory pointCreator, double[] v)
{
    super(func, pointCreator, v);
}
private void adjustLastDirection( DhbVector start)
{
    try {
        int n = projections.length - 1;
        projections[n].setOrigin( result);
        DhbVector newDirection = projections[n].getOrigin()
                                                    .subtract( start);
        double norm = newDirection.norm();
        if ( norm > getDesiredPrecision() )
        {
            newDirection.scaledBy( 1 / norm);
            projections[n].setDirection( newDirection);
            unidimensionalFinder.setFunction( projections[n]);
            unidimensionalFinder.setInitialValue( 0);
            unidimensionalFinder.evaluate();
            result = projections[n].argumentAt(
                    unidimensionalFinder.getResult()).toComponents();
        }
    }  catch ( DhbIllegalDimension e){};
}
/**
 * @return double    relative precision of current result
 * @param x double[]    result at previous iteration
 */
private double computePrecision( double[] x)
{
    double eps = 0;
    for ( int i = 0; i < result.length; i++)
        eps = Math.max( eps, relativePrecision(
                            Math.abs( result[i] - x[i]), result[i]));
    return eps;
}
public double evaluateIteration()
{
    try {
        DhbVector start;
        start = new DhbVector( result);
        int n = projections.length;
        for( int i = 0; i < n; i++ )
        {
            projections[i].setOrigin( result);
            unidimensionalFinder.setFunction( projections[i]);
            unidimensionalFinder.setInitialValue( 0);
            unidimensionalFinder.evaluate();
            result = projections[i].argumentAt( 
                    unidimensionalFinder.getResult()).toComponents();
        }
        rotateDirections();
        adjustLastDirection( start);
        return computePrecision( start.toComponents());
    } catch (NegativeArraySizeException e){ return Double.NaN;}
    catch ( DhbIllegalDimension e){ return Double.NaN;};
}

public void initializeIterations()
{
    projections = new VectorProjectedFunction[ result.length];
    double [] v = new double[ result.length];
    for ( int i = 0; i < projections.length; i++ )
        v[i] = 0;
    for ( int i = 0; i < projections.length; i++ )
        {
            v[i] = 1;
            projections[i] = new VectorProjectedFunction( f, result, v);
            v[i] = 0;
        }
    unidimensionalFinder = new OneVariableFunctionOptimizer( 
                                        projections[0], pointFactory);
    unidimensionalFinder.setDesiredPrecision( getDesiredPrecision());
}
private void rotateDirections()
{
    DhbVector firstDirection = projections[0].getDirection();
    int n = projections.length;
    for ( int i = 1; i < n; i++ )
        projections[i-1].setDirection(projections[i].getDirection());
    projections[n-1].setDirection( firstDirection);
}
/**
 * Returns a String that represents the value of this object.
 * @return a string representation of the receiver
 */
public String toString()
{
    StringBuffer sb = new StringBuffer();
    sb.append( getIterations());
    sb.append( " iterations, precision = ");
    sb.append( getPrecision());
    sb.append( "\nResult:");
    for ( int i = 0; i < result.length; i++ )
    {
        sb.append(' ');
        sb.append( result[i]);
    }
    for ( int i = 0 ; i < projections.length; i++ )
    {
        sb.append( '\n');
        sb.append( projections[i]);
    }
    return sb.toString();
}
}
\end{verbatim}