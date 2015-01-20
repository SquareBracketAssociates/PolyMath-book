\begin{verbatim}
package DhbMatrixAlgebra;

import DhbIterations.*;
/**
 * Object used to find the largest eigen value and the corresponding
 * eigen vector of a matrix by successive approximations.
 *
 * @author Didier H. Besset
 */
public class LargestEigenvalueFinder extends IterativeProcess
{
/**
 * Eigenvalue
 */
    private double eigenvalue;
/**
 * Eigenvector
 */
    private DhbVector eigenvector;
/**
 * Eigenvector of transposed matrix
 */
    private DhbVector transposedEigenvector;
/**
 * Matrix.
 */
    private Matrix matrix;

/**
 * Constructor method.
 * @param prec double
 * @param a DhbMatrixAlgebra.Matrix
 */
public LargestEigenvalueFinder ( double prec, Matrix a)
{
    this(a);
    this.setDesiredPrecision ( prec);
}
/**
 * Constructor method.
 * @param a DhbMatrixAlgebra.Matrix
 */
public LargestEigenvalueFinder ( Matrix a) 
{
    matrix = a;
    eigenvalue = Double.NaN;
}
/**
 * Returns the eigen value found by the receiver.
 * @return double
 */
public double eigenvalue ( )
{
    return eigenvalue;
}
/**
 * Returns the normalized eigen vector found by the receiver.
 * @return DhbMatrixAlgebra.DhbVector
 */
public DhbVector eigenvector ( )
{
    return eigenvector.product( 1.0 / eigenvector.norm());
}
/**
 * Iterate matrix product in eigenvalue information.
 */
public double evaluateIteration()
{
    double oldEigenvalue = eigenvalue;
    transposedEigenvector = 
                        transposedEigenvector.secureProduct( matrix);
    transposedEigenvector = transposedEigenvector.product( 1.0 
                            / transposedEigenvector.components[0]);
    eigenvector = matrix.secureProduct( eigenvector);
    eigenvalue = eigenvector.components[0];
    eigenvector = eigenvector.product( 1.0 / eigenvalue);
    return Double.isNaN( oldEigenvalue)
                    ? 10 * getDesiredPrecision()
                    : Math.abs( eigenvalue - oldEigenvalue);
}
/**
 * Set result to undefined.
 */
public void initializeIterations()
{
    eigenvalue = Double.NaN;
    int n = matrix.columns();
    double [] eigenvectorComponents = new double[ n];
    for ( int i = 0; i < n; i++) { eigenvectorComponents [i] = 1.0;}
    eigenvector = new DhbVector( eigenvectorComponents);
    n = matrix.rows();
    eigenvectorComponents = new double[ n];
    for ( int i = 0; i < n; i++) { eigenvectorComponents [i] = 1.0;}
    transposedEigenvector = new DhbVector( eigenvectorComponents);
}
/**
 * Returns a finder to find the next largest eigen value of the receiver's matrix.
 * @return DhbMatrixAlgebra.LargestEigenvalueFinder
 */
public LargestEigenvalueFinder nextLargestEigenvalueFinder ( )
{
    double norm = 1.0 / eigenvector.secureProduct(
                                            transposedEigenvector);
    DhbVector v1 = eigenvector.product( norm);
    return new LargestEigenvalueFinder( getDesiredPrecision(),
            matrix.secureProduct(SymmetricMatrix.identityMatrix(
                v1.dimension()).secureSubtract(v1.tensorProduct(
                                            transposedEigenvector))));
}
/**
 * Returns a string representation of the receiver.
 * @return java.lang.String
 */
public String toString()
{
    StringBuffer sb = new StringBuffer();
    sb.append( eigenvalue);
    sb.append(" (");
    sb.append( eigenvector.toString());
    sb.append(')');
    return sb.toString();
}
}
\end{verbatim}