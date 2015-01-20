\begin{verbatim}
package DhbScientificCurves;


/**
 * ScaledCurve is an association between a curve definition and an axis system.
 * The instance variable are directly available to all classes of the package.
 *
 */
class ScaledCurve
{
	/**
	 * Curve or histogram definition.
	 */
	protected HistogramOrCurveDefinition curve;
	/**
	 * Name of axis system.
	 */
	protected String scaleName;


	/**
	 * Constructor method.
	 */
	public ScaledCurve( HistogramOrCurveDefinition c, String name)
	{
		curve = c;
		scaleName = name;
	}
}
\end{verbatim}