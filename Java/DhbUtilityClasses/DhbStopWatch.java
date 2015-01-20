package DhbUtilityClasses;


import java.util.Date;
/**
 * This class implements facility to measure code execution speed
 * 
 */
public class DhbStopWatch
{
	/**
	* Elapsed time (only valid after a stop)
	*/
	private long elapsed;
/**
 * (c) Copyrights Didier BESSET, 1999, all rights reserved.
 */
public DhbStopWatch()
{
	super();
}
/**
 * Returns the elasped time in milliseconds.
 * @return the elasped time in milliseconds.
 */
public long elapsedTime ( )
{
	return elapsed;
}
/**
 * Starts the stop watch.
 */
public long error ( )
{
	start();
	long start = elapsed;
	while ( ( elapsed = new Date().getTime()) == start);
	return elapsed - start;
}
/**
 * Starts the stop watch.
 */
public void start ( )
{
	long current = new Date().getTime();
	while ( ( elapsed = new Date().getTime()) == current);
}
/**
 * Stops the stop watch.
 */
public void stop ( )
{
	elapsed = new Date().getTime() - elapsed;
}
/**
 * Generates a string representation of the elapsed time.
 */
public String toString ( )
{
	long executionTime = elapsed * 1000;
		StringBuffer sb = new StringBuffer();
		if ( executionTime < 1000 )
		{
			sb.append( Long.toString( executionTime));
			sb.append( " microsec.");
		}
		else if ( executionTime < 1000000 )
		{
			sb.append( Long.toString( executionTime / 1000));
			sb.append( " msec.");
		}
		else if ( executionTime < 60000000)
		{
			double sec = ( (double) executionTime) / 1000000.0;
			sb.append( Double.toString( sec));
			sb.append( " sec.");
		}
		else
		{
			long sec = executionTime / 1000000;
			if ( sec < 3600)
			{
				sb.append( Long.toString( sec / 60));
				sb.append(" min. ");
				sb.append(Long.toString( sec % 60));
				sb.append( " sec.");
			}
			else
			{
				sb.append( Long.toString( sec / 3600));
				sb.append(" hours ");
				sb.append(Long.toString( sec % 3600));
				sb.append( " min.");
			}
		}	
		return sb.toString();
}
}