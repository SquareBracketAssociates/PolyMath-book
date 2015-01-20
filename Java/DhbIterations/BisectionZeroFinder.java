\begin{verbatim}
package DhbIterations;

import DhbInterfaces.OneVariableFunction;
/**
 * Zero finding by bisection.
 *
 * @author Didier H. Besset
 */
public class BisectionZeroFinder extends FunctionalIterator
{
    /**
     * Value at which the function's value is negative.
     */
    private double xNeg;
    /**
     * Value at which the function's value is positive.
     */
    private double xPos;
/**
 * @param func DhbInterfaces.OneVariableFunction
 */
public BisectionZeroFinder(DhbInterfaces.OneVariableFunction func) {
    super(func);
}
/**
 * @param func DhbInterfaces.OneVariableFunction
 * @param x1 location at which the function yields a negative value
 * @param x2 location at which the function yields a positive value
 */
public BisectionZeroFinder( OneVariableFunction func, double x1, double x2)
                                            throws IllegalArgumentException
{
    this(func);
    setNegativeX( x1);
    setPositiveX( x2);
}
/**
 * @return double
 */
public double evaluateIteration()
{
    result = ( xPos + xNeg) * 0.5;
    if ( f.value(result) > 0 )
        xPos = result;
    else
        xNeg = result;
    return relativePrecision( Math.abs( xPos - xNeg));
}
/**
 * @param x double
 * @exception java.lang.IllegalArgumentException
 *                     if the function's value is not negative
 */
public void setNegativeX( double x) throws IllegalArgumentException
{
    if ( f.value( x) > 0 )
        throw new IllegalArgumentException( "f("+x+
                                ") is positive instead of negative");
    xNeg = x;
}
/**
 * (c) Copyrights Didier BESSET, 1999, all rights reserved.
 * @param x double
 * @exception java.lang.IllegalArgumentException
 *                     if the function's value is not positive
 */
public void setPositiveX( double x) throws IllegalArgumentException
{
    if ( f.value( x) < 0 )
        throw new IllegalArgumentException( "f("+x+
                                ") is negative instead of positive");
    xPos = x;
}
}
\end{verbatim}