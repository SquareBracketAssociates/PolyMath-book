\begin{verbatim}
package DhbOptimizing;

import DhbInterfaces.OneVariableFunction;
import DhbInterfaces.ManyVariableFunction;
import DhbMatrixAlgebra.DhbVector;
import DhbMatrixAlgebra.DhbIllegalDimension;
/**
 * Projection of a many-variable function 
 *                                onto a one-dimensional direction.
 *
 * @author Didier H. Besset
 */
public class VectorProjectedFunction implements OneVariableFunction
{
    /**
     * Value of the function to optimize.
     */
    private ManyVariableFunction f;
    /**
     * Origin for function evaluation.
     */
    private DhbVector origin;
    /**
     * Direction along which the function is evaluated.
     */
    private DhbVector direction;
/**
 * Constructor method.
 * @param func ManyVariableFunction    function to project
 * @param x double[]    origin of projected function
 * @param d double[]    direction of projection
 * @exception NegativeArraySizeException if dimension of x or d is 0.
 */
public VectorProjectedFunction( ManyVariableFunction func, 
                                            double[] x, double[] d)
                                throws NegativeArraySizeException
{
    f = func;
    setOrigin( x);
    setDirection( d);
}
/**
 * Constructor method.
 * @param func ManyVariableFunction    function to project
 * @param x DhbVector    origin of projected function
 * @param d DhbVector    direction of projection
 */
public VectorProjectedFunction( ManyVariableFunction func, 
                                            DhbVector x, DhbVector d)
{
    f = func;
    setOrigin( x);
    setDirection( d);
}
/**
 * @param x double[]    origin of projected function
 * @exception DhbIllegalDimension
 *                        if dimension of x is not that of the origin.
 */
public DhbVector argumentAt(double x) throws DhbIllegalDimension
{
    DhbVector v = direction.product( x);
    v.accumulate( origin);
    return v;
}
/**
 * @return DhbMatrixAlgebra.DhbVector    direction of the receiver
 */
public DhbVector getDirection()
{
    return direction;
}
/**
 * @return DhbMatrixAlgebra.DhbVector    origin of the receiver
 */
public DhbVector getOrigin()
{
    return origin;
}
/**
 * @param v DhbMatrixAlgebra.DhbVector
 * @exception NegativeArraySizeException if dimension of v is 0.
 */
public void setDirection( double[] v) throws NegativeArraySizeException
{
    direction = new DhbVector( v);
}
/**
 * @param v DhbMatrixAlgebra.DhbVector
 */
public void setDirection( DhbVector v)
{
    direction = v;
}
/**
 * @param v DhbMatrixAlgebra.DhbVector
 * @exception NegativeArraySizeException if dimension of v is 0.
 */
public void setOrigin( double[] v) throws NegativeArraySizeException
{
    origin = new DhbVector( v);
}
/**
 * @param v DhbMatrixAlgebra.DhbVector
 */
public void setOrigin( DhbVector v)
{
    origin = v;
}
/**
 * Returns a String that represents the value of this object.
 * @return a string representation of the receiver
 */
public String toString()
{
    StringBuffer sb = new StringBuffer();
    sb.append( origin);
    sb.append(" -> ");
    sb.append( direction);
    return sb.toString();
}
/**
 * @return double    value of the function
 * @param x double    distance from the origin in unit of direction. 
 */
public double value(double x)
{
    try{ return f.value( argumentAt(x).toComponents());
    } catch ( DhbIllegalDimension e) { return Double.NaN;}
}
}
\end{verbatim}