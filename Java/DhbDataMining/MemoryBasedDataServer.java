\begin{verbatim}
package DhbDataMining;

import DhbMatrixAlgebra.DhbVector;
/**
 * Data server containing data in memory for simulation purposes.
 * 
 * @author Didier H. Besset
 */
public class MemoryBasedDataServer extends AbstractDataServer
{
    private int index;
    private DhbVector[] dataPoints;
/**
 * Constructor method (for internal use only)
 */
protected MemoryBasedDataServer()
{
    super();
}
/**
 * @param points DhbVector[] supplied data points
 * (must not be changed after creation)
 */
public MemoryBasedDataServer( DhbVector[] points)
{
    dataPoints = points;
}
/**
 * Nothing to do
 */
public void close()
{
}
/**
 * Nothing to do
 */
public void open()
{
}
/**
 * @return DhbMatrixAlgebra.DhbVector    next data point
 * @exception java.io.EOFException no more data.
 */
public DhbVector read() throws java.io.EOFException
{
    if( index >= dataPoints.length )
        throw new java.io.EOFException();
    return dataPoints[index++];
}
/**
 * Data index is reset
 */
public void reset()
{
    index = 0;
}
}
\end{verbatim}