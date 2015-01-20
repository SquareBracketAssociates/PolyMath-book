\begin{verbatim}
package DhbScientificCurves;


import java.awt.Graphics;
/**
 * Class used to draw crosses as point symbol in curves.
 * 
 */
public class CrossSymbolDrawer extends AbstractSymbolDrawer
{

/**
 * Symbol ploting method.
 * @param g graphics context used to perform the drawing.
 * @param xPosition x position of symbol
 * @param yPosition y position of symbol
 * @param size of symbol
 */
public void plotSymbol(java.awt.Graphics g, int x, int y, int symbolSize)
{
	g.drawLine( x, y - symbolSize / 2, x, y + symbolSize / 2);
	g.drawLine( x - symbolSize / 2, y, x + symbolSize / 2, y);
}
}
\end{verbatim}