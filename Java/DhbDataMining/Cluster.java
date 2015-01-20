\begin{verbatim}
package DhbDataMining;

import DhbMatrixAlgebra.DhbVector;
/**
 * Abstract cluster.
 * 
 * @author Didier H. Besset
 */
public abstract class Cluster
{
    protected long previousSampleSize = 0;
/**
 * Default constructor method.
 */
public Cluster() {
}
/**
 * Constructor method.
 */
public Cluster(DhbVector v)
{
    initialize(v);
}
/**
 * @param Object    data point
 */
public abstract void accumulate(DhbVector dataPoint);
/**
 * @param Object    data point
 */
public abstract double distanceTo(DhbVector dataPoint);
/**
 * @return long    number of data points taken from or added to the receiver
 */
public long getChanges()
{
    return Math.abs( getSampleSize() - previousSampleSize);
}
/**
 * @return long    number of data points accumulated in the receiver
 */
public abstract long getSampleSize();
/**
 * @param v DhbMatrixAlgebra.DhbVector
 */
public abstract void initialize( DhbVector v);
/**
 * @return boolean    true if no data was accumulated in the receiver
 */
public boolean isEmpty()
{
    return getSampleSize() == 0;
}
/**
 * @return boolean    true if the receiver should be dropped from
 *                                                     the cluster finder
 * @param finder DhbDataMining.ClusterFinder
 */
public boolean isInsignificantIn( ClusterFinder finder)
{
    return getSampleSize() <= finder.minimumClusterSize();
}
/**
 * @return boolean    true if the cluster is in an undefined state.
 */
public abstract boolean isUndefined( );
public void reset()
{
    previousSampleSize = getSampleSize();
}
}
\end{verbatim}