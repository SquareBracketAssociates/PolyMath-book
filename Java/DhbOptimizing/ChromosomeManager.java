\begin{verbatim}
package DhbOptimizing;

import java.util.Random;
/**
 * Abstract chromosome manager.
 * (genetic algorithm)
 *
 * @author Didier H. Besset
 */
public abstract class ChromosomeManager
{
    /**
     * Population size.
     */
    private int populationSize = 100;
    /**
     * Rate of mutation. 
     */
    private double rateOfMutation = 0.1;
    /**
     * Rate of crossover. 
     */
    private double rateOfCrossover = 0.1;
    /**
     * Random generator. 
     */
    private Random generator = new Random();
/**
 * Constructor method.
 */
public ChromosomeManager()
{
    super();
}
/**
 * Constructor method.
 * @param n int
 * @param mRate double
 * @param cRate double
 */
public ChromosomeManager(int n, double mRate, double cRate)
{
    populationSize = n;
    rateOfMutation = mRate;
    rateOfCrossover = cRate;
}
/**
 * @param x java.lang.Object
 */
public abstract void addCloneOf( Object x);
/**
 * @param x java.lang.Object
 */
public abstract void addCrossoversOf( Object x, Object y);
/**
 * @param x java.lang.Object
 */
public abstract void addMutationOf( Object x);
public abstract void addRandomChromosome();
/**
 * @return int    the current size of the population
 */
public abstract int getCurrentPopulationSize();
/**
 * @return int    desired population size.
 */
public int getPopulationSize( )
{
    return populationSize;
}
/**
 * @return java.lang.Object (must be casted into the proper type
 *                                of chromosome)
 * @param n int
 */
public abstract Object individualAt( int n);
/**
 * @return boolean    true if the new generation is complete
 */
public boolean isFullyPopulated()
{
    return getCurrentPopulationSize() >= populationSize;
}
/**
 * @return double    a random number (delegated to the generator)
 */
public double nextDouble()
{
    return generator.nextDouble();
}
/**
 * @param x java.lang.Object
 * @param y java.lang.Object
 */
public void process( Object x, Object y)
{
    double roll = generator.nextDouble();
    if ( roll < rateOfCrossover )
        addCrossoversOf( x, y);
    else if ( roll < rateOfCrossover + rateOfMutation )
    {
        addMutationOf( x);
        addMutationOf( y);
    }
    else
    {
        addCloneOf( x);
        addCloneOf( y);
    }
}
/**
 * Create a population of random chromosomes.
 */
public void randomizePopulation()
{
    reset();
    while ( !isFullyPopulated() )
        addRandomChromosome();
}
/**
 * Reset the population of the receiver.
 */
public abstract void reset();
/**
 * @param n int    desired population size.
 */
public void setPopulationSize( int n)
{
    populationSize = n;
}
/**
 * @param n int    desired rate of crossover
 */
public void setRateOfCrossover( int cRate)
{
    rateOfCrossover = cRate;
}
/**
 * @param n int    desired rate of mutation
 */
public void setRateOfMutation( int mRate)
{
    rateOfMutation = mRate;
}
}
\end{verbatim}