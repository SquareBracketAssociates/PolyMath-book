\begin{verbatim}
package DhbScientificCurves;


import java.awt.Graphics;
/**
 * The subclasses of this class are used to draw curve symbols.
 * 
 */
abstract class AbstractSymbolDrawer
{

/**
 * Symbol ploting method.
 */
public abstract void plotSymbol ( Graphics g, int x, int y, int size);
}
\end{verbatim}