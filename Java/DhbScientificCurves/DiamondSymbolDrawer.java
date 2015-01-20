\begin{verbatim}
package DhbScientificCurves;


import java.awt.Graphics;
import java.awt.Polygon;
/**
 * Class used to draw diamonds as point symbol in curves.
 * 
 */
public class DiamondSymbolDrawer extends AbstractSymbolDrawer
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
	int h = size / 2;
	Polygon p = new Polygon();
	p.addPoint( x, y - h);
	p.addPoint( x - h, y);
	p.addPoint( x, y + h);
	p.addPoint( x + h, y);
	p.addPoint( x, y - h);
	g.drawPolygon( p);
}
}
\end{verbatim}