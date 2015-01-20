package DhbUtilityClasses;


import java.awt.TextField;
/**
 * Abstract class to build text fields returning a value.
 * 
 */
public abstract class ValuedField extends TextField
{
	protected boolean minimumDefined = false;
	protected boolean maximumDefined = false;



	/**
	 * General constructor method
	 * @param defaultValue the string to put in the field.
	 * @param size the size of te text field in columns.
	 */
	public ValuedField( String defaultValue, int size)
	{
		super( defaultValue, size);
	}
	/**
	 * General error recovery when illegal input is performed:
	 * ring the bell, select the entire text and setthe foucs on the text field.
	 */
	public void standardErrorRecovery()
	{
		char [] bell = {'\u0007'};
		System.out.println(new String( bell));
		requestFocus();
		selectAll();
	}
}