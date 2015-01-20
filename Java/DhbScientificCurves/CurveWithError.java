\begin{verbatim}
package DhbScientificCurves;

/**
 * This type was created in VisualAge.
 */
public class CurveWithError extends Curve implements DhbInterfaces.PointSeriesWithErrors {
/**
 * CurveWithError constructor comment.
 */
public CurveWithError() {
	super();
}
/**
 * @param x double
 * @param y double
 */
public void addPoint(double x, double y)
{
	addWeightedPoint( x, y, 1);
}
/**
 * @param x double
 * @param y double
 * @param error double	standard deviation on y
 */
public void addPoint(double x, double y, double error)
{
	addWeightedPoint( x, y, 1. / ( error * error));
}
/**
 * @param x double
 * @param y double
 * @param weight double	statistical weight of point
 */
public void addWeightedPoint(double x, double y, double weight)
{
	double point[] = new double[3];
	point[0] = x;
	point[1] = y;
	point[2] = weight;
	points.addElement( point);
}
/**
 * weightAt method comment.
 */
public double weightAt(int index)
{
	return ((double[]) points.elementAt( index))[3];
}
}
\end{verbatim}