\begin{verbatim}
package DhbScientificCurves;

import java.util.Vector;
import DhbInterfaces.PointSeries;
/**
 * A Curve is a series of points. A point is implemented as an array
 * of two doubles. The points are stored in a vector so that points
 * can be added or removed.
 *
 * @author Didier H. Besset
 */
public class Curve implements PointSeries
{
    /**
     * Vector containing the points.
     */
    protected Vector points;


/**
 * Constructor method. Initializes the vector.
 */
public Curve()
{
    points = new Vector();
}
/**
 * Adds a point to the curve defined by its 2-dimensional coordinates.
 * @param x double x-coordinate of the point
 * @param y double y-coordinate of the point
 */
public void addPoint( double x, double y)
{
    double point[] = new double[2];
    point[0] = x;
    point[1] = y;
    points.addElement( point);
}
/**
 * Removes the point at the specified index.
 * @param int index of the point to remove
 */
public void removePointAt( int index)
{
    points.removeElementAt( index);
}
/**
 * @return int the number of points in the curve.
 */
public int size()
{
    return points.size();
}
/**
 * @return double the x coordinate of the point at the given index.
 * @param int index the index of the point.
 */
public double xValueAt( int index)
{
    return ((double[]) points.elementAt( index))[0];
}
/**
 * @return double the y coordinate of the point at the given index.
 * @param int index the index of the point.
 */
public double yValueAt( int index)
{
    return ((double[]) points.elementAt( index))[1];
}
}
\end{verbatim}