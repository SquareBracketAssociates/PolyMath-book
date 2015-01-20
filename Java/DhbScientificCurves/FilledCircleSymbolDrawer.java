\begin{verbatim}
package DhbScientificCurves;


import java.awt.Graphics;
/**
 * Class used to draw filled circles as point symbol in curves.
 * 
 */
public class FilledCircleSymbolDrawer extends AbstractSymbolDrawer
{

/**
 * Symbol ploting method.
 * @param g graphics context used to perform the drawing.
 * @param xPosition x position of symbol
 * @param yPosition y position of symbol
 * @param size of symbol
 */
public void plotSymbol ( Graphics g, int x, int y, int size)
{
	g.fillOval( x - size / 2, y - size / 2, size, size);
}
}
\end{verbatim}