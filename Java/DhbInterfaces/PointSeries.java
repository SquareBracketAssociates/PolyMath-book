\begin{verbatim}
package DhbInterfaces;

/**
 * PointSeries is an interface used by many classes of the package numericalMethods.
 *
 * A PointSeries has the responsibility of handling mathematical
 * points in 2-dimensional space.
 * It is a BRIDGE to a vector containing the points.
 *
 * @author Didier H. Besset
 */
public interface PointSeries
{

/**
 * Returns the number of points in the series.
 */
public int size();
/**
 * Returns the x coordinate of the point at the given index.
 * @param index the index of the point.
 * @return x coordinate
 */
public double xValueAt( int index);
/**
 * Returns the y coordinate of the point at the given index.
 * @param index the index of the point.
 * @return y coordinate
 */
public double yValueAt( int index);
}
\end{verbatim}