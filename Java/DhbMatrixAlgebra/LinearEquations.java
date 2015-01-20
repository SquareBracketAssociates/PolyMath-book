\begin{verbatim}
package DhbMatrixAlgebra;

/**
 * Class representing a system of linear equations.
 *
 * @author Didier H. Besset
 */
public class LinearEquations
{
/**
 * components is a matrix build from the system's matrix and
 * the constant vector
 */
    private double[][] rows;
/**
 * Array containing the solution vectors.
 */
    private DhbVector[] solutions;

/**
 * Construct a system of linear equation Ax = y1, y2,....
 * @param m double[][]
 * @param c double[][]
 * @exception DhbMatrixAlgebra.DhbIllegalDimension
 *                                if the system's matrix is not square
 *                                if constant dimension does not match
 *                                            that of the matrix
 */
public LinearEquations ( double[][] m, double[][] c)
                                            throws DhbIllegalDimension
{
    int n = m.length;
    if ( m[0].length != n )
        throw new DhbIllegalDimension("Illegal system: a"+n+" by "
                    +m[0].length+" matrix is not a square matrix");
    if ( c[0].length != n )
        throw new DhbIllegalDimension("Illegal system: a "+n+" by "+n
                            +" matrix cannot build a system with a "
                                +c[0].length+"-dimensional vector");
    rows = new double[n][n+c.length];
    for ( int i = 0; i < n; i++)
    {
        for ( int j = 0; j < n; j++)
            rows[i][j] = m[i][j];
        for ( int j = 0; j < c.length; j++)
        rows[i][n+j] = c[j][i];
    }    
}
/**
 * Construct a system of linear equation Ax = y.
 * @param m double[][]        components of the system's matrix
 * @param c double[]    components of the constant vector
 * @exception DhbMatrixAlgebra.DhbIllegalDimension
 *                                if the system's matrix is not square
 *                                if constant dimension does not match
 *                                            that of the matrix
 */
public LinearEquations ( double[][] m, double[] c)
                                            throws DhbIllegalDimension
{
    int n = m.length;
    if ( m[0].length != n )
        throw new DhbIllegalDimension("Illegal system: a"+n+" by "
                    +m[0].length+" matrix is not a square matrix");
    if ( c.length != n )
        throw new DhbIllegalDimension("Illegal system: a "+n+" by "+n
                            +" matrix cannot build a system with a "
                                    +c.length+"-dimensional vector");
    rows = new double[n][n+1];
    for ( int i = 0; i < n; i++)
    {
        for ( int j = 0; j < n; j++)
            rows[i][j] = m[i][j];
        rows[i][n] = c[i];
    }    
}
/**
 * Construct a system of linear equation Ax = y.
 * @param a MatrixAlgebra.Matrix    matrix A
 * @param y MatrixAlgebra.DhbVector    vector y
 * @exception MatrixAlgebra.DhbIllegalDimension
 *                                if the system's matrix is not square
 *                                if vector dimension does not match
 *                                            that of the matrix
 */
public LinearEquations ( Matrix a, DhbVector y)
                                        throws DhbIllegalDimension
{
    this( a.components, y.components);
}
/**
 * Computes the solution for constant vector p applying
 * backsubstitution.
 * @param p int
 * @exception java.lang.ArithmeticException if one diagonal element
 *                                    of the triangle matrix is zero.
 */
private void backSubstitution ( int p) throws ArithmeticException
{
    int n = rows.length;
    double [] answer = new double[n];
    double x;
    for ( int i = n - 1; i >= 0; i--)
    {
        x = rows[i][n+p];
        for ( int j = i + 1; j < n; j++)
            x -= answer[j] * rows[i][j];
        answer[i] = x / rows[i][i];
    }
    solutions[p] = new DhbVector( answer);
    return;
}
/**
 * Finds the position of the largest pivot at step p.
 * @return int
 * @param p int    step of pivoting.
 */
private int largestPivot ( int p)
{
    double pivot = Math.abs( rows[p][p]);
    int answer = p;
    double x;
    for ( int i = p + 1; i < rows.length; i++)
    {
        x = Math.abs(rows[i][p]);
        if ( x > pivot )
        {
            answer = i;
            pivot = x;
        }    
    }    
    return answer;
}
/**
 * Perform pivot operation at location p.
 * @param p int
 * @exception java.lang.ArithmeticException if the pivot element
 *                                                            is zero.
 */
private void pivot ( int p) throws ArithmeticException
{
    double inversePivot = 1 / rows[p][p];
    double r;
    int n = rows.length;
    int m = rows[0].length;
    for ( int i = p + 1; i < n; i++)
    {
        r = inversePivot * rows[i][p];
        for ( int j = p; j < m; j++)
            rows[i][j] -= rows[p][j] * r;
    }    
    return;
}
/**
 * Perform optimum pivot operation at location p.
 * @param p int
 */
private void pivotingStep ( int p)
{
    swapRows( p, largestPivot( p));
    pivot(p);
    return;
}
/**
 * @return DhbVector        solution for the 1st constant vector
 */
public DhbVector solution ( ) throws ArithmeticException
{
    return solution( 0);
}
/**
 * Return the vector solution of constants indexed by p.
 * @return DHBmatrixAlgebra.DhbVector
 * @param p int    index of the constant vector fed into the system.
 * @exception java.lang.ArithmeticException
 *                                    if the system cannot be solved.
 */
public DhbVector solution ( int p) throws ArithmeticException
{
    if ( solutions == null )
        solve();
    if ( solutions[p] == null )
        backSubstitution( p);
    return solutions[p];
}
/**
 * @exception java.lang.ArithmeticException
 *                                    if the system cannot be solved.
 */
private void solve ( ) throws ArithmeticException
{
    int n = rows.length;
    for ( int i = 0; i < n; i++)
        pivotingStep( i);
    solutions = new DhbVector[rows[0].length-n];
}
/**
 * Swaps rows p and q.
 * @param p int
 * @param q int
 */
private void swapRows ( int p, int q)
{
    if ( p != q)
    {
        double temp;
        int m = rows[p].length;
        for (int j = 0; j < m; j++)
        {
            temp = rows[p][j];
            rows[p][j] = rows[q][j];
            rows[q][j] = temp;
        }    
    }    
    return;
}
/**
 * Returns a string representation of the system.
 * @return java.lang.String
 */
public String toString()
{
    StringBuffer sb = new StringBuffer();
    char[] separator = { '[', ' '};
    int n = rows.length;
    int m = rows[0].length;
    for ( int i = 0; i < n; i++)
    {
        separator[0] = '(';
        for ( int j = 0; j < n; j++)
        {
            sb.append( separator);
            sb.append( rows[i][j]);
            separator[0] = ',';
        }
        separator[0] = ':';
        for ( int j = n; j < m; j++)
        {
            sb.append( separator);
            sb.append( rows[i][j]);
            separator[0] = ',';
        }
    sb.append(')');
    sb.append('\n');
    }
    return sb.toString();
}
}
\end{verbatim}