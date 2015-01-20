\begin{verbatim}
package DhbOptimizing;

import DhbInterfaces.ManyVariableFunction;
/**
 * Vector & function holder used in optimizing many-variable functions.
 *
 * @author Didier H. Besset
 */
public abstract class OptimizingVector
{
    /**
     * Value of the function to optimize.
     */
    private double value;
    /**
     * Position at which the value was evaluated.
     */
    private double[] position;
    /**
     * Value of the function to optimize.
     */
    protected ManyVariableFunction f;
/**
 * Constructor method.
 * @param v double[]
 * @param f DhbInterfaces.OneVariableFunction
 */
public OptimizingVector(double[] v, ManyVariableFunction func)
{
    position = v;
    f = func;
    value = f.value( position);
}
/**
 * @return boolean    true if the receiver is "better" than 
 *                                                the supplied point
 * @param point OptimizingVector
 */
public abstract boolean betterThan( OptimizingVector entity);
/**
 * (used by the Simplex algorithm).
 * @param v double[]
 */
public void contractFrom( double[] v)
{
    for ( int i = 0; i < position.length; i++ )
    {
        position[i] += v[i];
        position[i] *= 0.5;
    }
    value = f.value( position);
}
/**
 * @return double    the receiver's position
 */
public double[] getPosition()
{
    return position;
}
/**
 * @return double    the value of the function
 *                                        at the receiver's position
 */
public double getValue()
{
    return value;
}
/**
 * (used by method toString)..
 * @return java.lang.String
 */
protected abstract String printedKey();
/**
 * @return java.lang.String
 */
public String toString()
{
    StringBuffer sb = new StringBuffer();
    sb.append( value);
    sb.append(printedKey());
    for ( int i = 0; i < position.length; i++ )
    {
        sb.append( ' ');
        sb.append( position[i]);
    }
    return sb.toString();
}
}
\end{verbatim}