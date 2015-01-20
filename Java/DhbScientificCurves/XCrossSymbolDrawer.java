\begin{verbatim}
package DhbScientificCurves;


import java.awt.Graphics;
/**
 * Class used to draw crosses as point symbol in curves.
 * 
 */
public class XCrossSymbolDrawer extends AbstractSymbolDrawer
{

/**
 * Symbol ploting method.
 * @param g graphics context used to perform the drawing.
 * @param xPosition x position of symbol
 * @param yPosition y position of symbol
 * @param size of symbol
 */
public void plotSymbol(java.awt.Graphics g, int x, int y, int size)
{
	g.drawLine( x - size / 2, y - size / 2, x + size / 2, y + size / 2);
	g.drawLine( x - size / 2, y + size / 2, x + size / 2, y - size / 2);
}
}
\end{verbatim}