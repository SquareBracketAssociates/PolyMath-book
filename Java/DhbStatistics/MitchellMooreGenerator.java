\begin{verbatim}
package DhbStatistics;

import java.util.Random;
/**
 * MitchellMoore random number generator
 *
 * @author Didier H. Besset
 */
public class MitchellMooreGenerator
{
    /**
     * List of previously generated numbers
     */
    private double[] randoms;
    /**
     * Index of last generated number
     */
    int highIndex;
    /**
     * Index of number to add to last number
     */
    int lowIndex;
/**
 * Default constructor.
 */
public MitchellMooreGenerator()
{
    this(55,24);
}
/**
 * Constructor method.
 * @param seeds double[]
 * @param index int
 */
public MitchellMooreGenerator( double[] seeds, int index)
{
    highIndex = seeds.length;
    randoms = new double[ highIndex];
    System.arraycopy( seeds, 0, randoms, 0, --highIndex);
    lowIndex = index - 1;
}
/**
 * Constructor method.
 * @param indexH int    high index
 * @param indexL int    low index
 */
public MitchellMooreGenerator( int indexH, int indexL)
{
    Random generator = new Random();
    randoms = new double[indexH];
    for ( int i = 0; i < indexH; i++)
        randoms[i] = generator.nextDouble();
    highIndex = indexH - 1;
    lowIndex = indexL - 1;
}
/**
 * @return double    the next random number
 */
public double nextDouble()
{
    double x = randoms[ highIndex--] + randoms[ lowIndex--];
    if( highIndex < 0)
        highIndex = randoms.length - 1;
    if( lowIndex < 0)
        lowIndex = randoms.length - 1;
    return ( randoms[highIndex] = x < 1.0 ? x : x - 1);
}
/**
 * @return long    returns a long integer between 0 and n-1
 * @param n long
 */
public long nextInteger( long n)
{
    return (long) (n * nextDouble());
}
}
\end{verbatim}