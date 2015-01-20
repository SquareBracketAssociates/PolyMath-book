package DhbUtilityClasses;


/**
 * A DoubleField is text field used to enter double values.
 * Optional checking maybe provided.
 *
 * @version 1.0 22 Jun 1998
 * @author Didier H. Besset
*/
public class DoubleField extends ValuedField
{
	/**
	 * An optional minimum for the field's value.
	 */
	private double minimum;
	/**
	 * An optional maximum for the field's value.
	 */
	private double maximum;


	/**
	 * Full Constructor method.
	 * @param defaultValue value displayed in the text field.
	 * @param size number of columns of the text field. 
	 * @param low minimum allowed value. 
	 * @param high maximum allowed value. 
	 */
	public DoubleField( double defaultValue, int size, double low, double high)
	{
		super( "" + defaultValue, size);
		setMinimum( low);
		setMaximum( high);
	}
	/**
	 * Simple Constructor method.
	 * @param size number of columns of the text field.
	 */
	public DoubleField( int size)
	{
		super("", size);
	}
	/**
	 * Decodes and retrieves the value which has been entered in the text field. 
	 * @return the decoded value. 
	 */
	public double getValue() throws NumberFormatException
	{
		double value;

		value = Double.valueOf( getText().trim()).doubleValue();
		if ( minimumDefined && value < minimum )
			throw new NumberFormatException();
		if ( maximumDefined && value > maximum )
			throw new NumberFormatException();
		return value;
	}
	/**
	 * Defines the maximum value which may be entered in the text field. 
	 * @param high maximum allowed value. 
	 */
	public void setMaximum( double high)
	{
		maximum = high;
		maximumDefined = true;
	}
	/**
	 * Defines the minimum value which may be entered in the text field. 
	 * @param low minimum allowed value. 
	 */
	public void setMinimum( double low)
	{
		minimum = low;
		minimumDefined = true;
	}
}