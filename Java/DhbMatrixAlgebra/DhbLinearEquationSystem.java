\begin{verbatim}

package DhbMatrixAlgebra;

/**
 * Class representing a system of linear equations.
 * Please contact didier@ieee.org for further information.
 * (c) Copyrights, 1998 Didier BESSET, all rights reserved
 * 
 */
public class DhbLinearEquationSystem
{
/**
 * components is a matrix build from the system's matrix and the constant vector
 */
	private double[][] components;
/**
 * Array containing the solution vectors.
 */
	private boolean[] solutions;



/**
 * This method was created by a SmartGuide.
 * @param m double[][]
 * @param c double[][]
 * @exception DhbMatrixAlgebra.DhbIllegalDimension The exception description.
 */
public DhbLinearEquationSystem( double[][] m, double[][] c) throws DhbIllegalDimension
{
	int n = m.length;
	if ( m[0].length != n )
		throw new DhbIllegalDimension("Illegal system: a"+n+" by "+m[0].length+" matrix is not a square matrix");
	if ( c[0].length != n )
		throw new DhbIllegalDimension("Illegal system: a "+n+" by "+n+" matrix cannot build a system with a "+c.length+"-dimensional vector");
	components = new double[n][n+c.length];
	for ( int i = 0; i < n; i++)
	{
		for ( int j = 0; j < n; j++)
			components[i][j] = m[i][j];
		for ( int j = 0; j < c.length; j++)
		components[i][n+j] = c[j][i];
	}	
}
/**
/**
 * Construct a system of linear equation Ax = y.
 * @param m double[][]		components of the system's matrix
 * @param c double[]	components of the constant vector
 * @exception DhbMatrixAlgebra.DhbIllegalDimension The exception description.
 */
public DhbLinearEquationSystem( double[][] m, double[] c) throws DhbIllegalDimension
{
	int n = m.length;
	if ( m[0].length != n )
		throw new DhbIllegalDimension("Illegal system: a"+n+" by "+m[0].length+" matrix is not a square matrix");
	if ( c.length != n )
		throw new DhbIllegalDimension("Illegal system: a "+n+" by "+n+" matrix cannot build a system with a "+c.length+"-dimensional vector");
	components = new double[n][n+1];
	for ( int i = 0; i < n; i++)
	{
		for ( int j = 0; j < n; j++)
			components[i][j] = m[i][j];
		components[i][n] = c[i];
	}	
}
/**
 * Computes the solution for constant vector p applying backsubstitution.
 * @param p int
 * @exception java.lang.ArithmeticException if one diagonal element of the triangle matrix is zero.
 */
private void backSubstitution ( int p) throws ArithmeticException
{
	int n = components.length;
	double x;
	for ( int i = n - 1; i >= 0; i--)
	{
		x = components[i][n+p];
		for ( int j = i + 1; j < n; j++)
			x -= components[j][n+p] * components[i][j];
		components[i][n+p] = x / components[i][i];
	}
	solutions[p] = true;
	return;
}
/**
 * Finds the position of the largest pivot at step p.
 * @return int
 * @param p int	step of pivoting.
 */
private int largestPivot ( int p)
{
	double pivot = Math.abs( components[p][p]);
	int answer = p;
	double x;
	for ( int i = p + 1; i < components.length; i++)
	{
		x = Math.abs(components[i][p]);
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
 * @exception java.lang.ArithmeticException if the pivot element is zero.
 */
private void pivot ( int p) throws ArithmeticException
{
	double inversePivot = 1 / components[p][p];
	double r;
	int n = components.length;
	int m = components[0].length;
	for ( int i = p + 1; i < n; i++)
	{
		r = inversePivot * components[i][p];
		for ( int j = p; j < m; j++)
			components[i][j] -= components[p][j] * r;
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
 * @return double[]		solution for the 1st constant vector
 */
public double[] solution ( ) throws ArithmeticException
{
	return solution( 0);
}
/**
 * Return the vector solution of constants indexed by p.
 * @return double[]
 * @param p int	index of the constant vector fed into the system.
 * @exception java.lang.ArithmeticException if the system cannot be solved.
 */
public double[] solution ( int p) throws ArithmeticException
{
	if ( solutions == null )
		solve();
	if ( !solutions[p] )
		backSubstitution( p);
	int n = components.length;
	double [] answer = new double[n];
	for ( int i = n - 1; i >= 0; i--)
		answer[i] = components[i][n+p];
	return answer;
}
/**
 * This method was created by a SmartGuide.
 * @exception java.lang.ArithmeticException The exception description.
 */
private void solve ( ) throws ArithmeticException
{
	int n = components.length;
	for ( int i = 0; i < n; i++)
		pivotingStep( i);
	solutions = new boolean[components[0].length-n];
	for ( int i = 0; i < solutions.length; i++)
		solutions[i] = false;
	return;
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
		int m = components[p].length;
		for (int j = 0; j < m; j++)
		{
			temp = components[p][j];
			components[p][j] = components[q][j];
			components[q][j] = temp;
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
	int n = components.length;
	int m = components[0].length;
	for ( int i = 0; i < n; i++)
	{
		separator[0] = '(';
		for ( int j = 0; j < n; j++)
		{
			sb.append( separator);
			sb.append( components[i][j]);
			separator[0] = ',';
		}
		separator[0] = ':';
		for ( int j = n; j < m; j++)
		{
			sb.append( separator);
			sb.append( components[i][j]);
			separator[0] = ',';
		}
	sb.append(')');
	sb.append('\n');
	}
	return sb.toString();
}
}
\end{verbatim}