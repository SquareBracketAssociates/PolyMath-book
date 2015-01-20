\begin{verbatim}
package DhbIterations;

import DhbInterfaces.OneVariableFunction;
import DhbInterpolation.NevilleInterpolator;
import DhbScientificCurves.Curve;
/**
 * Romberg integration method
 *
 * @author Didier H. Besset
 */
public class RombergIntegrator extends TrapezeIntegrator
{
    /**
     * Order of the interpolation.
     */
    private int order = 5;
    /**
     * Structure containing the last estimations.
     */
    private Curve estimates;
    /**
     * Neville interpolator.
     */
    private NevilleInterpolator interpolator;
/**
 * RombergIntegrator constructor.
 * @param func DhbInterfaces.OneVariableFunction
 * @param from double
 * @param to double
 */
public RombergIntegrator(DhbInterfaces.OneVariableFunction func,
                                            double from, double to)
{
    super(func, from, to);
}
/**
 * @return double
 */
public double evaluateIteration()
{
    estimates.addPoint( estimates.xValueAt(estimates.size() - 1) * 0.25,
                                                    highOrderSum());
    if ( estimates.size() < order )
        return 1;
    double[] interpolation = interpolator.valueAndError( 0);
    estimates.removePointAt( 0);
    result = interpolation[0];
    return relativePrecision( Math.abs( interpolation[1]));
}
public void initializeIterations()
{
    super.initializeIterations();
    estimates = new Curve();
    interpolator = new NevilleInterpolator( estimates);
    estimates.addPoint( 1, sum);
}
/**
 * @param n int
 */
public void setOrder( int n)
{
    order = n;
}
}
\end{verbatim}