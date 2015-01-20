\begin{verbatim}
package DhbDataMining;

import DhbMatrixAlgebra.DhbVector;
/**
 * Statistical average for vectors
 * 
 * @author Didier H. Besset
 */
public class VectorAccumulator
{
    protected long count = 0;
    protected double[] average;
/**
 * Default constructor method.
 */
public VectorAccumulator(int n)
{
    this(n,1);
    reset();
}
/**
 * Constructor method.
 * @param n int
 * @param dummy int
 */
protected VectorAccumulator(int n, int dummy)
{
    average = new double[n];
}
/**
 * @param v double[]    values to accumulate in the receiver
 */
public void accumulate( double[] v)
{
    count += 1;
    for ( int i = 0; i < average.length; i++ )
        average[i] -= (average[i] - v[i]) / count;
}
/**
 * @param v DhbVector    vector of values to accumulate in the receiver
 */
public void accumulate( DhbVector v)
{
    accumulate( v.toComponents());
}
/**
 * @return DhbVector    vector containing the average
 */
public DhbVector averageVector()
{
    try { return new DhbVector( average);}
        catch( NegativeArraySizeException e){ return null;}
}
/**
 * @return long    number of accumulated data points.
 */
public long getCount()
{
    return count;
}
/**
 * @param sb java.lang.StringBuffer
 */
protected void printOn( StringBuffer sb)
{
    sb.append("Counts: "+count);
    char separator = '\n';
    for ( int i = 0; i < average.length; i++)
    {
        sb.append( separator);
        sb.append( average[i]);
        separator = ' ';
    }
}
public void reset()
{
    count = 0;
    for ( int i = 0; i < average.length; i++ )
        average[i] = 0;
}
/**
 * @return java.lang.String
 */
public String toString()
{
    StringBuffer sb = new StringBuffer();
    printOn( sb);
    return sb.toString();
}
}
\end{verbatim}