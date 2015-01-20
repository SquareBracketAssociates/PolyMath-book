\begin{verbatim}
package DhbOptimizing;

import DhbMatrixAlgebra.DhbVector;
/**
 * Chromosome manager for vector chromosomes.
 * (genetic algorithm)
 *
 * @author Didier H. Besset
 */
public class VectorChromosomeManager extends ChromosomeManager
{
    /**
     * Population.
     */
    private DhbVector[] population;
    /**
     * Current population size.
     */
    private int fillIndex;
    /**
     * Origin of values.
     */
    private DhbVector origin;
    /**
     * Range of values.
     */
    private DhbVector range;
/**
 * Default constructor method.
 */
public VectorChromosomeManager() {
    super();
}
/**
 * Constructor method.
 * @param n int
 * @param mRate double
 * @param cRate double
 */
public VectorChromosomeManager(int n, double mRate, double cRate) {
    super(n, mRate, cRate);
}
/**
 * @param x DhbVector
 */
public void addCloneOf(Object x)
{
    double[] v = ((DhbVector) x).toComponents();
    try { population[fillIndex++] = new DhbVector( v);}
        catch( NegativeArraySizeException e) {};
}
/**
 * @param x DhbVector
 * @param y DhbVector
 */
public void addCrossoversOf(Object x, Object y)
{
    double[] v = ((DhbVector) x).toComponents();
    double[] w = ((DhbVector) x).toComponents();
    int n = (int) ( nextDouble() * ( origin.dimension() - 1));
    double temp;
    for ( int i = 0; i < n; i++ )
    {
        temp = v[i];
        v[i] = w[i];
        w[i] = temp;
    }
    try { population[fillIndex++] = new DhbVector( v);
          population[fillIndex++] = new DhbVector( w);
        } catch( NegativeArraySizeException e) {};
}
/**
 * @param x DhbVector
 */
public void addMutationOf(Object x)
{
    double[] v = ((DhbVector) x).toComponents();
    int i = (int) ( nextDouble() * origin.dimension());
    v[i] = randomComponent(i);
    try { population[fillIndex++] = new DhbVector( v);}
        catch( NegativeArraySizeException e) {};
}
public void addRandomChromosome()
{
    double[] v = new double[origin.dimension()];
    for ( int i = 0; i < origin.dimension(); i++ )
        v[i] = randomComponent(i);
    try { population[fillIndex++] = new DhbVector( v);}
        catch( NegativeArraySizeException e) {};
}
/**
 * @return int    the current size of the population
 */
public int getCurrentPopulationSize()
{
    return fillIndex;
}
/**
 * @return Vector    vector at given index
 * @param n int
 */
public Object individualAt( int n)
{
    return population[n];
}
/**
 * @return double
 * @param n int
 */
private double randomComponent(int n)
{
    return origin.component(n) + nextDouble() * range.component(n);
}
/**
 * Allocated memory for a new generation.
 */
public void reset()
{
    population = new DhbVector[getPopulationSize()];
    fillIndex = 0;
}
/**
 * @param x double    component of the origin of the hypercube
 *    constraining the domain of definition of the function to optimize
 * @exception java.lang.NegativeArraySizeException
 *                        when the size of the array is 0
 */
public void setOrigin( double[] x) throws NegativeArraySizeException
{
    setOrigin(  new DhbVector( x));
}
/**
 * @param v DhbVector     origin of the hypercube
 *    constraining the domain of definition of the function to optimize
 */
public void setOrigin( DhbVector v)
{
    origin = v;
}
/**
 * @param x double    components of the lengths of the hypercube
 *    constraining the domain of definition of the function to optimize
 * @exception java.lang.NegativeArraySizeException
 *                        when the size of the array is 0
 */
public void setRange( double[] x) throws NegativeArraySizeException
{
    setRange(  new DhbVector( x));
}
/**
 * @param v DhbVector    lengths of the hypercube
 *    constraining the domain of definition of the function to optimize
 */
public void setRange( DhbVector v)
{
    range = v;
}
}
\end{verbatim}