\begin{verbatim}
package DhbOptimizing;

import DhbMatrixAlgebra.DhbIllegalDimension;
import DhbMatrixAlgebra.DhbVector;
/**
 * Simplex optimizer of many-variable functions.
 *
 * @author Didier H. Besset
 */
public class SimplexOptimizer extends MultiVariableOptimizer
{
    /**
     * Best value found so far.
     */
    private OptimizingVector[] simplex;
/**
 * Constructor method.
 * @param func DhbInterfaces.ManyVariableFunction
 * @param pointCreator DhbOptimizing.OptimizingPointFactory
 * @param initialValue double[]
 */
public SimplexOptimizer(DhbInterfaces.ManyVariableFunction func, 
        OptimizingPointFactory pointCreator, double[] initialValue)
{
    super(func, pointCreator, initialValue);
}
/**
 * Add a new best point to the simplex
 * @param v DhbOptimizing.OptimizingVector
 */
private void addBestPoint( OptimizingVector v)
{
    int n = simplex.length;
    while ( --n > 0 )
        simplex[n] = simplex[n-1];
    simplex[0] = v;
}
/**
 * @return boolean    true if a better point was found
 * @param g DhbVector    summit whose median is contracted 
 * @exception DhbIllegalDimension if dimension of initial value is 0.
 */
private boolean addContraction(DhbVector g)
                                        throws DhbIllegalDimension
{
    g.accumulate( simplex[result.length].getPosition());
    g.scaledBy( 0.5);
    OptimizingVector contractedPoint = 
                                    pointFactory.createVector( g, f);
    if ( contractedPoint.betterThan( simplex[0]) )
    {
        addBestPoint( contractedPoint);
        return true;
    }
    else
        return false;
}
/**
 * @return boolean    true if a better point was found
 * @exception DhbIllegalDimension if dimension of initial value is 0.
 */
private boolean addReflection( DhbVector centerOfgravity)
                                        throws DhbIllegalDimension
{
    DhbVector reflectedVector = centerOfgravity.product( 2);
    reflectedVector.accumulateNegated( 
                                simplex[result.length].getPosition());
    OptimizingVector reflectedPoint = 
                    pointFactory.createVector( reflectedVector, f);
    if ( reflectedPoint.betterThan( simplex[0]) )
    {
        reflectedVector.scaledBy( 2);
        reflectedVector.accumulateNegated( centerOfgravity);
        OptimizingVector expandedPoint = 
                    pointFactory.createVector( reflectedVector, f);
        if ( expandedPoint.betterThan( reflectedPoint) )
            addBestPoint( expandedPoint);
        else
            addBestPoint( reflectedPoint);
        return true;
    }
    else
        return false;
}
/**
 * @return DhbVector    center of gravity of best points of simplex,
 *                                                    except worst one
 */
private DhbVector centerOfGravity() throws DhbIllegalDimension
{
    DhbVector g = new DhbVector( result.length);
    for ( int i = 0; i < result.length; i++ )
        g.accumulate( simplex[i].getPosition());
    g.scaledBy( 1.0 / result.length);
    return g;
}
/**
 * @return double    maximum simplex extent in each direction
 */
private double computePrecision()
{
    int i, j;
    double[] position = simplex[0].getPosition();
    double[] min = new double[ position.length];
    double[] max = new double[ position.length];
    for ( i = 0; i < position.length; i++ )
    {
        min[i] = position[i];
        max[i] = position[i];
    }
    for ( j = 1; j < simplex.length; j++ )
    {
        position = simplex[j].getPosition();
        for ( i = 0; i < position.length; i++ )
        {
            min[i] = Math.min( min[i], position[i]);
            max[i] = Math.max( max[i], position[i]);
        }
    }
    double eps = 0;
    for ( i = 1; i < position.length; i++ )
        eps = Math.max( eps, relativePrecision( max[i]-min[i], result[i]));
    return eps;
}
/**
 * Reduce the simplex from the best point.
 */
private void contractSimplex()
{
    double[] bestPoint = simplex[0].getPosition();
    for ( int i = 1; i < simplex.length; i++)
        simplex[i].contractFrom( bestPoint);
    sortPoints( simplex);
}
/**
 * Here precision is the largest extent of the simplex.
 */
public double evaluateIteration()
{
    try { 
        double bestValue = simplex[0].getValue();
        DhbVector g = centerOfGravity();
        if ( !addReflection( g) )
        {
            if ( !addContraction( g) )
                contractSimplex();
        }
        result = simplex[0].getPosition();
        return computePrecision();
    } catch ( DhbIllegalDimension e) { return 1;};
}
/**
 * Create a Simplex by finding the optimum in each direction
 * starting from the initial value..
 */
public void initializeIterations()
{
    double [] v = new double[ result.length];
    for ( int i = 0; i < result.length; i++ )
        v[i] = 0;
    VectorProjectedFunction projection = 
                        new VectorProjectedFunction( f, result, v);
    OneVariableFunctionOptimizer unidimensionalFinder = 
        new OneVariableFunctionOptimizer( projection, pointFactory);
    unidimensionalFinder.setDesiredPrecision( getDesiredPrecision());
    simplex = new OptimizingVector[result.length+1];
    try {
        for ( int i = 0; i < result.length; i++ )
            {
                v[i] = 1;
                projection.setDirection( v);
                v[i] = 0;
                unidimensionalFinder.setInitialValue( 0);
                unidimensionalFinder.evaluate();
                simplex[i] = pointFactory.createVector( 
                                projection.argumentAt( 
                                unidimensionalFinder.getResult()), f);
            }
    } catch ( DhbIllegalDimension e) { };
    simplex[result.length] = pointFactory.createVector( result, f);
    sortPoints( simplex);
}
/**
 * Returns a String that represents the value of this object.
 * @return a string representation of the receiver
 */
public String toString()
{
    StringBuffer sb = new StringBuffer();
    sb.append( simplex[0]);
    for ( int i = 1; i < simplex.length; i++ )
    {
        sb.append( '\n');
        sb.append( simplex[i]);
    }
    return sb.toString();
}
}
\end{verbatim}