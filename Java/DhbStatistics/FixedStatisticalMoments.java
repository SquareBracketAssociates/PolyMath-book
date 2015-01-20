\begin{verbatim}
package DhbStatistics;

/**
 * Statistical moments for a fixed set (1-4th order)
 *
 * @author Didier H. Besset
 */
public class FixedStatisticalMoments extends StatisticalMoments
{
/**
 * Constructor method.
 */
public FixedStatisticalMoments()
{
    super();
}
/**
 * Quick implementation of statistical moment accumulation up to order 4.
 * @param x double
 */
public void accumulate ( double x)
{
    double n = moments[0];
    double n1 = n + 1;
    double n2 = n * n;
    double delta = ( moments[1] - x) / n1;
    double d2 = delta * delta;
    double d3 = delta * d2;
    double r1 = (double) n / (double) n1;
    moments[4] += 4 * delta * moments[3] + 6 * d2 * moments[2]
                                            + (1 + n * n2) * d2 * d2;
    moments[4] *= r1;
    moments[3] += 3 * delta * moments[2] + (1 - n2) * d3;
    moments[3] *= r1;
    moments[2] += (1 + n) * d2;
    moments[2] *= r1;
    moments[1] -= delta;
    moments[0] = n1;
    return;
}
}
\end{verbatim}