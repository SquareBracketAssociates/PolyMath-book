\begin{verbatim}
package DhbInterfaces;

/**
 * This is a point series where each point has an error in the y direction.
 *
 * @author Didier H. Besset
 */
public interface PointSeriesWithErrors extends PointSeries {
/**
 * @return double    weight of the point
 * @param n int
 */
double weightAt(int n);
}
\end{verbatim}