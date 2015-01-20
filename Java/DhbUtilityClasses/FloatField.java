package DhbUtilityClasses;


/**
 * A FloatField is text field used to enter float values.
 * Optional checking maybe provided.
 *
 * @version 1.0 22 Jun 1998
 * @author Didier H. Besset
*/
public class FloatField extends ValuedField
{
	/**
	 * An optional minimum for the field's value.
	 */
	private float minimum;
	/**
	 * An optional maximum for the field's value.
	 */
	private float maximum;



	/**
	 * Full Constructor method.
	 * @param defaultValue value displayed in the text field.
	 * @param size number of columns of the text field. 
	 * @param low minimum allowed value. 
	 * @param high maximum allowed value. 
	 */
	public FloatField( float defaultValue, int size, float low, float high)
	{
		super( "" + defaultValue, size);
		setMinimum( low);
		setMaximum( high);
	}
	/**
	 * Simple Constructor method.
	 * @param size number of columns of the text field.
	 */
	public FloatField( int size)
	{
		super("", size);
	}
	/**
	 * Decodes and retrieves the value which has been entered in the text field. 
	 * @return the decoded value. 
	 */
	public float getValue() throws NumberFormatException
	{
		float value;

		value = Float.valueOf( getText().trim()).floatValue();
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
	public void setMaximum( float low)
	{
		maximum = low;
		maximumDefined = true;
	}
	/**
	 * Defines the minimum value which may be entered in the text field. 
	 * @param low minimum allowed value. 
	 */
	public void setMinimum( float low)
	{
		minimum = low;
		minimumDefined = true;
	}
}