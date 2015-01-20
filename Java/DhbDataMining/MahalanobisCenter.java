\begin{verbatim}
package DhbDataMining;

import DhbMatrixAlgebra.DhbIllegalDimension;
import DhbMatrixAlgebra.DhbNonSymmetricComponents;
import DhbMatrixAlgebra.DhbVector;
import DhbMatrixAlgebra.SymmetricMatrix;
/**
 * This object is used to compute the Mahalanobis distance
 * to a set of data.
 * 
 * @author Didier H. Besset
 */
public class MahalanobisCenter
{
    private DhbVector center = null;
    private SymmetricMatrix inverseCovariance = null;
    private CovarianceAccumulator accumulator;
/**
 * Constructor method.
 * @param int dimension of the receiver
 */
public MahalanobisCenter( int dimension)
{
    accumulator = new CovarianceAccumulator( dimension);
}
/**
 * Constructor method.
 * @param DhbVector center of the receiver
 */
public MahalanobisCenter( DhbVector v)
{
    accumulator = new CovarianceAccumulator( v.dimension());
    center = v;
    inverseCovariance = SymmetricMatrix.identityMatrix(v.dimension());
}
/**
 * Accumulation is delegated to the covariance accumulator.
 * @param v DhbVector    vector of values to accumulate in the receiver
 */
public void accumulate( DhbVector v)
{
    accumulator.accumulate(v);
}
/**
 * Computes the parameters of the receiver.
 */
public void computeParameters()
{
    center = accumulator.averageVector();
    inverseCovariance = (SymmetricMatrix)
                            accumulator.covarianceMatrix().inverse();
}
/**
 * @return double    Mahalanobis distance of the data point from the
 *                                                center of the receiver.
 * @param dataPoint DhbVector    data point
 */
public double distanceTo(DhbVector dataPoint)
{
    try {
        DhbVector v = dataPoint.subtract( center);
        return v.product( inverseCovariance.product(v));
        }
    catch (DhbIllegalDimension e) { return Double.NaN;}
}
/**
 * @return long    number of data points inside the receiver
 */
public long getCount()
{
    return accumulator.getCount();
}
/**
 * Keep the center and covariance matrix.
 */
public void reset()
{
    accumulator.reset();
}
/**
 * @return java.lang.String
 */
public String toString() {
    return center.toString();
}
}
\end{verbatim}