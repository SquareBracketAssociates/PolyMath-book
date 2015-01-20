\begin{verbatim}
package DhbDataMining;

import DhbMatrixAlgebra.DhbVector;
import DhbMatrixAlgebra.DhbIllegalDimension;
/**
 * Cluster using Euclidean distance.
 * 
 * @author Didier H. Besset
 */
public class EuclideanCluster extends Cluster
{
    private DhbVector center;
    private VectorAccumulator accumulator;
/**
 * Default constructor method.
 */
public EuclideanCluster()
{
    super();
}
/**
 * Constructor method.
 * @param DhbVector center of the receiver
 */
public EuclideanCluster(DhbVector dataPoint) 
{
    super( dataPoint);
}
/**
 * @param dataPoint DhbVector    data point
 */
public void accumulate(DhbVector dataPoint)
{
    accumulator.accumulate( dataPoint);
}
/**
 * @param dataPoint DhbVector    data point
 * @return DhbVector    square of the Euclidian distance from the data
 *                                point to the center of the receiver.
 */
public double distanceTo( DhbVector dataPoint)
{
    try{ DhbVector v = dataPoint.subtract( center);
         return v.product( v);}
        catch(DhbIllegalDimension e) { return Double.NaN;}
}
/**
 * @return long    number of data points accumulated in the receiver
 */
public long getSampleSize()
{
    return accumulator.getCount();
}
/**
 * @param v DhbVector    center for the receiver
 */
public void initialize(DhbVector dataPoint) 
{
    center = dataPoint;
    accumulator = new VectorAccumulator( dataPoint.dimension());
}
/**
 * @return boolean    true if the cluster is in an undefined state.
 */
public boolean isUndefined()
{
    return center == null;
}
public void reset()
{
    super.reset();
    center = accumulator.averageVector();
    accumulator.reset();
}
/**
 * @return java.lang.String
 */
public String toString()
{
    StringBuffer sb = new StringBuffer();
    sb.append( previousSampleSize);
    sb.append( ' ');
    sb.append( center);
    return sb.toString();
}
}
\end{verbatim}