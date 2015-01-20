\begin{verbatim}
package DhbIterations;

import DhbFunctionEvaluation.DhbMath;
import DhbFunctionEvaluation.GammaFunction;
import DhbInterfaces.OneVariableFunction;

/**
 * Incomplete Beta function
 *
 * @author Didier H. Besset
 */
public class IncompleteBetaFunction implements OneVariableFunction
{
    /**
     * Function parameters.
     */
    private double alpha1;
    private double alpha2;
    /**
     * Constant to be computed once only.
     */
    private double logNorm;
    /**
     * Continued fractions.
     */
    private IncompleteBetaFunctionFraction fraction;
    private IncompleteBetaFunctionFraction inverseFraction;

/**
 * Constructor method.
 * @param a1 double
 * @param a2 double
 */
public IncompleteBetaFunction ( double a1, double a2)
{
    alpha1 = a1;
    alpha2 = a2;
    logNorm = GammaFunction.logGamma( alpha1 + alpha2)
                            - GammaFunction.logGamma( alpha1)
                            - GammaFunction.logGamma( alpha2);
}
/**
 * @return double
 * @param x double
 */
private double evaluateFraction ( double x)
{
    if ( fraction == null )
    {
        fraction = new IncompleteBetaFunctionFraction( alpha1, alpha2);
        fraction.setDesiredPrecision( DhbMath.defaultNumericalPrecision());
    }    
    fraction.setArgument( x);
    fraction.evaluate();
    return fraction.getResult();
}
/**
 * @return double
 * @param x double
 */
private double evaluateInverseFraction ( double x)
{
    if ( fraction == null )
    {
        fraction = new IncompleteBetaFunctionFraction( alpha2, alpha1);
        fraction.setDesiredPrecision( DhbMath.defaultNumericalPrecision());
    }    
    fraction.setArgument( x);
    fraction.evaluate();
    return fraction.getResult();
}
public double value(double x)
{
    if ( x == 0 )
        return 0;
    if ( x == 1 )
        return 1;
    double norm = Math.exp( alpha1 * Math.log(x)
                                + alpha2 * Math.log(1 - x) + logNorm);
    return ( alpha1 + alpha2 + 2) * x < ( alpha1 + 1)
                    ? norm / ( evaluateFraction( x) * alpha1)
                    : 1 - norm / ( evaluateInverseFraction(1 - x)
                                                            * alpha2);
}
}
\end{verbatim}