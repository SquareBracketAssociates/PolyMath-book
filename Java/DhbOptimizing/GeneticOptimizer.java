\begin{verbatim}
package DhbOptimizing;

/**
 * Abstract genetic algorithm.
 *
 * @author Didier H. Besset
 */
public abstract class GeneticOptimizer extends MultiVariableOptimizer
{
    /**
     * Chromosome manager.
     */
    private ChromosomeManager chromosomeManager;
/**
 * Constructor method.
 * @param func DhbInterfaces.ManyVariableFunction
 * @param pointCreator DhbOptimizing.OptimizingPointFactory
 * @param chrManager ChromosomeManager
 */
public GeneticOptimizer(DhbInterfaces.ManyVariableFunction func, 
    OptimizingPointFactory pointCreator, ChromosomeManager chrManager)
{
    super(func, pointCreator, null);
    chromosomeManager = chrManager;
}
/**
 * @param x java.lang.Object
 */
public abstract void collectPoint(Object x);
/**
 * Collect points for the entire population.
 */
public void collectPoints()
{
    reset();
    for ( int i = 0; i < chromosomeManager.getPopulationSize(); i++ )
        collectPoint( chromosomeManager.individualAt(i));
}
/**
 * This method causes the receiver to exhaust the maximum number of
 * iterations. It may be overloaded by a subclass (hence "protected")
 * if a convergence criteria can be defined.
 * @return double
 */
protected double computePrecision()
{
    return 1;
}
/**
 * @return double
 */
public double evaluateIteration()
{
    double[] randomScale = randomScale();
    chromosomeManager.reset();
    while ( !chromosomeManager.isFullyPopulated() )
    {
        chromosomeManager.process(
                            individualAt( randomIndex(randomScale)),
                            individualAt( randomIndex(randomScale)));
    }
    collectPoints();
    return computePrecision();
}
/**
 * @return java.lang.Object    (must be casted into the proper type)
 * @param n int
 */
public abstract Object individualAt(int n);
/**
 * Create a random population.
 */
public void initializeIterations()
{
    initializeIterations( chromosomeManager.getPopulationSize());
    chromosomeManager.randomizePopulation();
    collectPoints();
}
/**
 * @param n int    size of the initial population
 */
public abstract void initializeIterations( int n);
/**
 * @return int    an index generated randomly
 * @param randomScale double[]    fitness scale (integral)
 */
protected int randomIndex( double[] randomScale)
{
    double roll = chromosomeManager.nextDouble();
    if ( roll < randomScale[0] )
        return 0;
    int n = 0;
    int m = randomScale.length;
    int k;
    while ( n < m - 1 )
    {
        k = ( n + m) / 2;
        if ( roll < randomScale[k] )
            m = k;
        else
            n = k;
    }
    return m;
}
/**
 * @return double[]    integral fitness scale.
 */
public abstract double[] randomScale();
public abstract void reset();
}
\end{verbatim}