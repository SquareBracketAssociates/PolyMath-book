\begin{verbatim}
package DhbEstimation;

import DhbIterations.IterativeProcess;
import DhbMatrixAlgebra.DhbIllegalDimension;
import DhbMatrixAlgebra.DhbNonSymmetricComponents;
import DhbMatrixAlgebra.LUPDecomposition;
import DhbMatrixAlgebra.SymmetricMatrix;
import DhbStatistics.ChiSquareDistribution;
import DhbInterfaces.ParametrizedOneVariableFunction;
import DhbScientificCurves.Histogram;
import DhbStatistics.ScaledProbabilityDensityFunction;
import DhbStatistics.ProbabilityDensityFunction;
/**
 * Non-linear least square fit
 *
 * @author Didier H. Besset
 */
public class LeastSquareFit extends IterativeProcess
{
    protected ParametrizedOneVariableFunction result;
    private WeightedPoint[] points;
    protected double[][] systemMatrix;
    protected double[] systemConstants;
    private LUPDecomposition systemLUP;
    private SymmetricMatrix errorMatrix;
    private double chiSquare;
    private int degreeOfFreedom;
/**
 * Default constructor method (internal use only)
 */
protected LeastSquareFit()
{
}
/**
 * Constructor method
 * @param n int
 */
public LeastSquareFit(WeightedPoint[] pts,
                                ParametrizedOneVariableFunction f)
{
    points = pts;
    result = f;
    initializeSystem( result.parameters().length);
}
/**
 * Constructor method
 * @param histogram Histogram
 * @param distr ProbabilityDensityFunction
 */
public LeastSquareFit(Histogram histogram,
                                    ProbabilityDensityFunction distr)
{
    points = new WeightedPoint[histogram.size()];
    for ( int i = 0; i < points.length; i++)
        points[i] = histogram.weightedPointAt(i);
    result = new ScaledProbabilityDensityFunction( distr, histogram);
    initializeSystem( result.parameters().length);
}
/**
 * @param wp DhbEstimation.WeightedPoint
 */
protected void accumulate( WeightedPoint wp)
{
    double[] fg = result.valueAndGradient( wp.xValue());
    for( int i = 0; i < systemConstants.length; i++ )
    {
        systemConstants[i] += ( wp.yValue() - fg[0])
                                * fg[i+1] * wp.weight();
        for( int j = 0; j <= i; j++ )
            systemMatrix[i][j] += fg[i+1] * fg[j+1] * wp.weight();
    }
}
/**
 * Append the name of the fit to the supplied string buffer
 * @param sb java.lang.StringBuffer
 */
protected void appendFitName( StringBuffer sb)
{
    sb.append("Least square fit with ");
}
/**
 * Append the results of the fit to the supplied string buffer
 * @param sb java.lang.StringBuffer
 */
private void appendFitResults( StringBuffer sb)
{
    java.text.DecimalFormat fmt = 
                        new java.text.DecimalFormat("###0.00000");
    java.text.DecimalFormat corFmt = 
                                new java.text.DecimalFormat("0.000");
    sb.append('\n');
    sb.append("\tcompleted in ");
    sb.append( getIterations());
    sb.append(" iterations\n");
    sb.append("\tParams\tErrors\tCorrelation");
    double[][] comp = errorMatrix().toComponents();
    double[] params = result.parameters();
    double[] errors = new double[comp.length];
    char separator;
    for ( int i = 0; i < comp.length; i++ )
    {
        sb.append("\n\t");
        sb.append( fmt.format(params[i]));
        errors[i] = Math.sqrt(comp[i][i]);
        sb.append("\t+-");
        sb.append( fmt.format(errors[i]));
        separator = '\t';
        for ( int j = 0; j < i; j++ )
        {
            sb.append(separator);
            sb.append(' ');
            sb.append(corFmt.format(comp[i][j] /
                                        (errors[i] * errors[j])));
        }
    }
    appendNormalization( sb);
    sb.append("\n\tChi square =");
    sb.append( fmt.format(chiSquare()));
    sb.append("\tDegree of freedom =");
    sb.append( degreeOfFreedom());
    sb.append("\tConfidence level =");
    sb.append( corFmt.format(confidenceLevel()));
}
/**
 * This method does nothing (compatibility with maximum likelihood fit)
 * @param sb java.lang.StringBuffer
 */
protected void appendNormalization( StringBuffer sb) { }
/**
 * @return double
 */
public double chiSquare()
{
    if ( Double.isNaN( chiSquare) )
        computeChiSquare();
    return chiSquare;
}
/**
 * @return double[] changes on parameters
 */
protected double[] computeChanges()
{
    return systemLUP.solve( systemConstants);
}
/**
 * Compute the chi^2 of the fit function.
 */
private void computeChiSquare()
{
    chiSquare = 0;
    for( int i = 0; i < getDataSetSize(); i++ )
        chiSquare += weightedPointAt(i).chi2Contribution( result);
}
/**
 * @return DhbMatrixAlgebra.SymmetricMatrix
 */
private void computeErrorMatrix()
{
    double [][] components = systemLUP.inverseMatrixComponents();
    LUPDecomposition.symmetrizeComponents( components);
    try { errorMatrix = SymmetricMatrix.fromComponents( components);
          systemLUP = null;
        }
        catch ( DhbNonSymmetricComponents e) {}
        catch ( DhbIllegalDimension ex) {};
}
private void computeSystem()
{
    resetSystem();
    for ( int i = 0; i < getDataSetSize(); i++ )
        accumulate( weightedPointAt(i));
    symmetrizeMatrix();
}
/**
 * @return double    confidence level of the fit.
 */
public double confidenceLevel()
{
    return (new ChiSquareDistribution(
                    degreeOfFreedom())).confidenceLevel( chiSquare());
}
/**
 * @return long    the degree of freedom of the fit.
 */
public int degreeOfFreedom()
{
    if ( degreeOfFreedom < 0 )
        degreeOfFreedom = getDataSetSize() -
                                        result.parameters().length;
    return degreeOfFreedom;
}
/**
 * @return DhbMatrixAlgebra.SymmetricMatrix    the error matrix of the fit.
 */
public SymmetricMatrix errorMatrix()
{
    if ( errorMatrix == null )
        computeErrorMatrix();
    return errorMatrix;
}
/**
 * @return double
 */
public double evaluateIteration()
{
    double[] parameters = result.parameters();
    computeSystem();
    try { systemLUP = new LUPDecomposition( systemMatrix);}
        catch( DhbIllegalDimension e) {};
    double[] changes = computeChanges();
    double eps = 0;
    for ( int i = 0; i < parameters.length; i++ )
    {
        parameters[i] += changes[i];
        eps = Math.max( eps, Math.abs(
                    relativePrecision( changes[i], parameters[i])));
    }
    result.setParameters( parameters);
    return eps;
}
public void finalizeIterations()
{
    systemMatrix = null;
    systemConstants = null;
    errorMatrix = null;
    chiSquare = Double.NaN;
    degreeOfFreedom = -1;
}
/**
 * @return int    number of data points.
 */
protected int getDataSetSize()
{
    return points.length;
}
/**
 * @return ParametrizedOneVariableFunction    the fitted function
 */
public ParametrizedOneVariableFunction getResult()
{
    return result;
}
/**
 * @param n int
 */
protected void initializeSystem( int n)
{
    systemConstants = new double[ n];
    systemMatrix = new double[n][n];
}
protected void resetSystem( )
{
    for( int i = 0; i < systemConstants.length; i++ )
    {
        systemConstants[i] = 0;
        for( int j = 0; j <= i; j++ )
            systemMatrix[i][j] = 0;
    }
}
private void symmetrizeMatrix( )
{
    for( int i = 0; i < systemConstants.length; i++ )
    {
        for( int j = 0; j < i; j++ )
            systemMatrix[j][i] = systemMatrix[i][j];
    }
}
/**
 * @return java.lang.String
 */
public String toString()
{
    StringBuffer sb = new StringBuffer();
    appendFitName(sb);
    sb.append( getResult());
    if ( hasConverged() )
        appendFitResults(sb);
    return sb.toString();
}
/**
 * @return DhbEstimation.WeightedPoint n-th weighted data point
 * @param n int
 */
protected WeightedPoint weightedPointAt( int n)
{
    return points[n];
}
}
\end{verbatim}