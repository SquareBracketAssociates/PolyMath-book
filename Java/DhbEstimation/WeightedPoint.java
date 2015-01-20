\begin{verbatim}
package DhbEstimation;

import DhbInterfaces.OneVariableFunction;
import DhbStatistics.StatisticalMoments;
/**
 * Point with error used in chi-square test and least square fits
 *
 * @author Didier H. Besset
 */
public class WeightedPoint
{
    private double xValue;
    private double yValue;
    private double weight;
    private double error = Double.NaN;
/**
 * Constructor method.
 * @param x double
 * @param y double
 */
public WeightedPoint( double x, double y)
{
    this( x, y, 1);
}
/**
 * Constructor method.
 * @param x double
 * @param y double
 * @param w double
 */
public WeightedPoint( double x, double y, double w)
{
    xValue = x;
    yValue = y;
    weight = w;
}
/**
 * Constructor method.
 * @param x double
 * @param n int    a Histogram bin content
 */
public WeightedPoint( double x, int n)
{
    this( x, n, 1.0 / Math.max(n,1));
}
/**
 * Constructor method.
 * @param x double
 * @param m DhbStatistics.StatisticalMoments
 */
public WeightedPoint( double x, StatisticalMoments m)
{
    this( x, m.average());
    setError( m.errorOnAverage());
}
/**
 * @return double    contribution to chi^2 sum against
 *                                                a theoretical function
 * @param wp WeightedPoint
 */
public double chi2Contribution( WeightedPoint wp)
{
    double residue = yValue - wp.yValue();
    return residue * residue / ( 1 / wp.weight() + 1 / weight);
}
/**
 * @return double    contribution to chi^2 sum against
 *                                                a theoretical function
 * @param f DhbInterfaces.OneVariableFunction
 */
public double chi2Contribution( OneVariableFunction f)
{
    double residue = yValue - f.value( xValue);
    return residue * residue * weight;
}
/**
 * @return double    error of the receiver
 */
public double error()
{
    if( Double.isNaN( error) )
        error = 1 / Math.sqrt( weight);
    return error;
}
/**
 * @param e double error on the point
 */
public void setError( double e)
{
    error = e;
    weight = 1 / ( e * e);
}
/**
 * @return java.lang.String
 */
public String toString()
{
    StringBuffer sb = new StringBuffer();
    sb.append( '(');
    sb.append( xValue);
    sb.append( ',');
    sb.append( yValue);
    sb.append( "+-");
    sb.append( error());
    sb.append( ')');
    return sb.toString();
}
/**
 * @return double    weight of the receiver
 */
public double weight() {
    return weight;
}
/**
 * @return double    x value of the receiver
 */
public double xValue() {
    return xValue;
}
/**
 * @return double    y value of the receiver
 */
public double yValue() {
    return yValue;
}
}
\end{verbatim}