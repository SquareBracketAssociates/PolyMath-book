\begin{verbatim}
package DhbOptimizing;

import DhbInterfaces.OneVariableFunction;
import DhbInterfaces.ManyVariableFunction;
import DhbMatrixAlgebra.DhbVector;
/**
 * Factory of point/vector & function holders for optimizing functions.
 *
 * @author Didier H. Besset
 */
public abstract class OptimizingPointFactory {
/**
 * Constructor method.
 */
public OptimizingPointFactory() {
    super();
}
/**
 * @return DhbOptimizing.OptimizingPoint
 * @param x double
 * @param f OneVariableFunction
 */
public abstract OptimizingPoint createPoint( double x,
                                            OneVariableFunction f);
/**
 * @return DhbOptimizing.OptimizingVector
 * @param v double[]
 * @param f ManyVariableFunction
 */
public abstract OptimizingVector createVector( double[] v,
                                            ManyVariableFunction f);
/**
 * @return DhbOptimizing.OptimizingVector
 * @param v DhbVector
 * @param f ManyVariableFunction
 */
public OptimizingVector createVector( DhbVector v,
                                DhbInterfaces.ManyVariableFunction f)
{
    return createVector( v.toComponents(), f);
}
}
\end{verbatim}