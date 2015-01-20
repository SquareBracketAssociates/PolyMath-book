\begin{verbatim}
package DhbEstimation;

import DhbFunctionEvaluation.PolynomialFunction;
import DhbInterfaces.PointSeries;
import DhbMatrixAlgebra.SymmetricMatrix;
import DhbMatrixAlgebra.DhbIllegalDimension;
import DhbMatrixAlgebra.DhbNonSymmetricComponents;
/**
 * linear regression
 *
 * @author Didier H. Besset
 */
public class LinearRegression 
{
/**
 * Number of accumulated points
 */
    private int sum1;
/**
 * Sum of X
 */
    private double sumX;
/**
 * Sum of Y
 */
    private double sumY;
/**
 * Sum of XX
 */
    private double sumXX;
/**
 * Sum of XY
 */
    private double sumXY;
/**
 * Sum of YY
 */
    private double sumYY;
/**
 * Slope
 */
    private double slope;
/**
 * Intercept
 */
    private double intercept;
/**
 * Correlation coefficient
 */
    private double correlationCoefficient;
/**
 * Cnstructor method.
 */
public LinearRegression()
{
    super();
    reset();
}
/**
 * @param x double
 * @param y double
 */
public void add( double x, double y)
{
    add( x, y, 1);
}
/**
 * @param x double
 * @param y double
 * @param w double
 */
public void add( double x, double y, double w)
{
    double wx = w * x;
    double wy = w * y;
    sum1 += w;
    sumX += wx;
    sumY += wy;
    sumXX += wx * x;
    sumYY += wy * y;
    sumXY += wx * y;
    resetResults();
}
/**
 * @return DhbFunctionEvaluation.PolynomialFunction
 */
public EstimatedPolynomial asEstimatedPolynomial()
{
    return new EstimatedPolynomial( coefficients(), errorMatrix());
}
/**
 * @return DhbFunctionEvaluation.PolynomialFunction
 */
public PolynomialFunction asPolynomial()
{
    return new PolynomialFunction( coefficients());
}
/**
 * @return double[]
 */
private double[] coefficients()
{
    double[] answer = new double[2];
    answer[0] = getIntercept();
    answer[1] = getSlope();
    return answer;
}
private void computeResults()
{
    double xNorm = sumXX * sum1 - sumX * sumX;
    double xyNorm = sumXY * sum1 - sumX * sumY;
    slope = xyNorm / xNorm;
    intercept = ( sumXX * sumY - sumXY * sumX) / xNorm;
    correlationCoefficient = xyNorm / Math.sqrt( xNorm *
                                    ( sumYY * sum1 - sumY * sumY));
}
/**
 * @return DhbMatrixAlgebra.SymmetricMatrix
 */
public SymmetricMatrix errorMatrix()
{
    double[][] rows = new double[2][2];
    rows[1][1] = 1./ ( sumXX * sum1 - sumX * sumX);
    rows[0][1] = sumXX * rows[1][1];
    rows[1][0] = rows[0][1];
    rows[0][0] = sumXX * rows[1][1];
    SymmetricMatrix answer = null;
    try { try { answer = SymmetricMatrix.fromComponents( rows);
              } catch( DhbIllegalDimension e){};
        } catch( DhbNonSymmetricComponents e){};
    return answer;
}
/**
 * @return double
 */
public double getCorrelationCoefficient()
{
    if( Double.isNaN( correlationCoefficient) )
        computeResults();
    return correlationCoefficient;
}
/**
 * @return double
 */
public double getIntercept()
{
    if( Double.isNaN( intercept) )
        computeResults();
    return intercept;
}
/**
 * @return double
 */
public double getSlope()
{
    if( Double.isNaN( slope) )
        computeResults();
    return slope;
}
/**
 * @param x double
 * @param y double
 */
public void remove( double x, double y)
{
    sum1 -= 1;
    sumX -= x;
    sumY -= y;
    sumXX -= x * x;
    sumYY -= y * y;
    sumXY -= x * y;
    resetResults();
}
public void reset()
{
    sum1 = 0;
    sumX = 0;
    sumY = 0;
    sumXX = 0;
    sumYY = 0;
    sumXY = 0;
    resetResults();
}
private void resetResults()
{
    slope = Double.NaN;
    intercept = Double.NaN;
    correlationCoefficient = Double.NaN;
}
/**
 * @return double
 * @param x double
 */
public double value( double x)
{
    return x * getSlope() + getIntercept();
}
}
\end{verbatim}