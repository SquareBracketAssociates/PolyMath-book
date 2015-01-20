\begin{verbatim}
package DhbDataMining;

import DhbMatrixAlgebra.DhbVector;
/**
 * Abstract data server for data mining.
 * 
 * @author Didier H. Besset
 */
public abstract class AbstractDataServer
{
/**
 * Constructor method.
 */
public AbstractDataServer() {
    super();
}
/**
 * Closes the stream of data.
 */
public abstract void close();
/**
 * Opens the stream of data.
 */
public abstract void open();
/**
 * @return DhbVector    next data point found on the stream
 * @exception java.io.EOFException when no more data point can be found.
 */
public abstract DhbVector read() throws java.io.EOFException;
/**
 * Rewind the stream of data.
 */
public abstract void reset();
}
\end{verbatim}