\begin{verbatim}
package DhbDataMining;

import DhbIterations.IterativeProcess;
import DhbMatrixAlgebra.DhbVector;
/**
 * Implements k-cluster algorithm
 * 
 * @author Didier H. Besset
 */
public class ClusterFinder extends IterativeProcess
{
    private Cluster[] clusters;
    private AbstractDataServer server;
    private double minimumRelativeClusterSize = 0;
    private long dataSetSize;
/**
 * Constructor method
 * @param Cluster[] clusterArray    initial clusters
 * @param server AbstractDataServer    server for the data points
 */
public ClusterFinder(Cluster[] clusterArray,
                                    AbstractDataServer clusterServer)
{
    clusters = clusterArray;
    server = clusterServer;
}
/**
 * Constructor method
 * @param numberOfCluster int    maximum number of clusters foreseen
 * @param server AbstractDataServer    server for the data points
 */
public ClusterFinder(int numberOfCluster,
                                    AbstractDataServer clusterServer)
{
    clusters = new Cluster[numberOfCluster];
    server = clusterServer;
}
/**
 * Accumulate all data points into the nearest cluster.
 */
private void accumulateData()
{
    dataSetSize = 0;
    try {
        while (true)
        {
            DhbVector dataPoint = server.read();
            nearestCluster( dataPoint).accumulate( dataPoint);
            dataSetSize += 1;
        }
    } catch ( java.io.EOFException e) {}
}
/**
 * @return int    number of data points which changed clusters since
 *                                                the last iteration
 */
private int collectChangesAndResetClusters()
{
    int n = 0;
    int emptyClusters = 0;
    for ( int i = 0; i < clusters.length; i++ )
    {
        n += clusters[i].getChanges();
        if ( clusters[i].isInsignificantIn( this) )
        {
            emptyClusters += 1;
            clusters[i] = null;
        }
        else
            clusters[i].reset();
    }
    if ( emptyClusters > 0 )
        removeEmptyClusters( emptyClusters);
    return n / 2;
}
/**
 * Perform one iteration step.
 * @return double    number of data points which changed clusters
 *                                            since the last iteration
 */
public double evaluateIteration()
{
    server.reset();
    accumulateData();
    return collectChangesAndResetClusters();
}
/**
 * Closes the data point server
 */
public void finalizeIterations()
{
    server.close();
}
/**
 * @return DhbDataMining.Cluster[] clusters contained in the receiver
 */
public Cluster[] getClusters()
{
    return clusters;
}
/**
 * Opens the data stream and creates the initial clusters.
 */
public void initializeIterations()
{
    server.open();
    try {
        for ( int i = 0; i < clusters.length; i++ )
            {
                if ( clusters[i].isUndefined() )
                    clusters[i].initialize(server.read());
            }
        } catch (java.io.EOFException e){};
}
/**
 * @return long    minimum cluster size to be considered in the next iteration
 */
public long minimumClusterSize()
{
    return Math.round( minimumRelativeClusterSize * dataSetSize);
}
/**
 * @param dataPoint ClusterData    
 * @return Cluster    nearest to the data point
 */
private Cluster nearestCluster( DhbVector dataPoint)
{
    int index = 0;
    int nearestIndex = index;
    double closestDistance = clusters[index].distanceTo( dataPoint);
    double distance;
    while ( ++index < clusters.length )
    {
        distance = clusters[index].distanceTo( dataPoint);
        if ( distance < closestDistance )
        {
            closestDistance = distance;
            nearestIndex = index;
        }
    }
    return clusters[nearestIndex];
}
/**
 * Removes empty clusters. The array of clusters is reconstructed.
 * @param n int    number of empty clusters
 */
private void removeEmptyClusters( int n)
{
    Cluster[] newClusters = new Cluster[ clusters.length - n];
    int index = 0;
    for ( int i = 0; i < clusters.length; i++ )
    {
        if ( clusters[i] != null )
            newClusters[index++] = clusters[i];
    }
    clusters = newClusters;
}
/**
 * @return DhbDataMining.Cluster[] clusters contained in the receiver
 */
public void setClusters( Cluster[] clusterArray)
{
    clusters = clusterArray;
}
/**
 * @param r double    the minimum relative size of a cluster to be kept
 *                                                in the next iteration
 * @exception java.lang.IllegalArgumentException
 *                                        argument cannot be negative.
 */
public void setMinimumRelativeClusterSize( double r)
                                    throws IllegalArgumentException
{
    if ( r < 0 )
        throw new IllegalArgumentException(
                        "Relative cluster size cannot be negative");
    minimumRelativeClusterSize = r;
}
/**
 * @return java.lang.String
 */
public String toString()
{
    StringBuffer sb = new StringBuffer();
    sb.append( "Iterations: "+getIterations());
    for ( int i = 0; i < clusters.length; i++)
    {
        sb.append( '\n');
        sb.append( clusters[i]);
    }
    return sb.toString();
}
}
\end{verbatim}