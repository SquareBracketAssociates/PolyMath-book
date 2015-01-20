\begin{verbatim}
package DhbOptimizing;

/**
 * Multi-strategy optimizer of many-variable functions.
 *
 * @author Didier H. Besset
 */
public class MultiVariableGeneralOptimizer extends MultiVariableOptimizer
{
    /**
     * Initial range for random search.
     */
    protected double[] range;
/**
 * Constructor method.
 * @param func DhbInterfaces.ManyVariableFunction
 * @param pointCreator DhbOptimizing.OptimizingPointFactory
 * @param initialValue double[]
 */
public MultiVariableGeneralOptimizer(DhbInterfaces.ManyVariableFunction func,
                    OptimizingPointFactory pointCreator, double[] initialValue)
{
    super(func, pointCreator, initialValue);
}
public double evaluateIteration()
{
    HillClimbingOptimizer finder = new HillClimbingOptimizer( f, pointFactory, 
                                                                        result);
    finder.setDesiredPrecision( getDesiredPrecision());
    finder.setMaximumIterations(  getMaximumIterations());
    finder.evaluate();
    result = finder.getResult();
    return finder.getPrecision();
}
public void initializeIterations()
{
    if ( range != null )
        performGeneticOptimization();
    performSimplexOptimization();
}
private void performGeneticOptimization()
{
    VectorChromosomeManager manager = new VectorChromosomeManager();
    manager.setRange( range);
    manager.setOrigin( result);
    VectorGeneticOptimizer finder = new VectorGeneticOptimizer( f, pointFactory, manager);
    finder.evaluate();
    result = finder.getResult();
}
private void performSimplexOptimization()
{
    SimplexOptimizer finder = new SimplexOptimizer( f, pointFactory, result);
    finder.setDesiredPrecision( Math.sqrt( getDesiredPrecision()));
    finder.setMaximumIterations(  getMaximumIterations());
    finder.evaluate();
    result = finder.getResult();
}
/**
 * @param x double    component of the origin of the hypercube
 *                constraining the domain of definition of the function
 */
public void setOrigin( double[] x)
{
    result = x;
}
/**
 * @param x double    components of the lengths of the hypercube
 *                constraining the domain of definition of the function
 */
public void setRange( double[] x)
{
    range = x;
}
}
\end{verbatim}