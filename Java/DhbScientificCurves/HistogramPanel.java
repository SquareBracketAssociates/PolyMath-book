\begin{verbatim}
package DhbScientificCurves;


import DhbInterfaces.OneVariableFunction;
import java.awt.*;
/**
 * A HistogramPanel displays the histogram information.
 *
 *
 * @version 1.0 24 Jul 1998
 * @author Didier H. Besset
 */
public class HistogramPanel extends Panel
{
	/**
	 * Canvas used to draw the histogram.
	 */
	private Scatterplot histogramDisplay;
	/**
	 * Text field used to show the underflow.
	 */
	private TextField underflowDisplay;
	/**
	 * Text field used to show the overflow.
	 */
	private TextField overflowDisplay;
	/**
	 * Text field used to show the count.
	 */
	private TextField countDisplay;
	/**
	 * Text field used to show the average.
	 */
	private TextField averageDisplay;
	/**
	 * Text field used to show the standard deviation.
	 */
	private TextField standardDeviationDisplay;
	/**
	 * Text field used to show the skewness.
	 */
	private TextField skewnessDisplay;
	/**
	 * Text field used to show the kurtosis.
	 */
	private TextField kurtosisDisplay;


	/**
	 * Constructor method.
	 */
	public HistogramPanel( Histogram histogram)
	{
		this( histogram, new HistogramDefinition( histogram));
	}
/**
 * This method was created by a SmartGuide.
 * @param histogram curves.Histogram
 * @param histogramDefinition curves.HistogramDefinition
 */
public HistogramPanel ( Histogram histogram, HistogramDefinition histogramDefinition)
{
	setLayout( new BorderLayout());
	histogramDisplay = new Scatterplot();
	histogramDisplay.addCurve( histogramDefinition, "Count", false, true);
	add("Center", histogramDisplay);
	add("South", statisticsPanel( histogram));
}
/**
 * This method was created by a SmartGuide.
 * @param histogramDefinition curves.HistogramDefinition
 */
public HistogramPanel ( HistogramDefinition histogramDefinition) {
}
/**
 * Overlay a curve on the histogram's display.
 * @param curve overlay curve definition.
 */
public void overlayCurve( HistogramOrCurveDefinition curve)
{
	histogramDisplay.addCurve( curve, "Count", false, false);
	return;
}
/**
 * Overlay a function on the histogram's display.
 * @param curve overlay curve definition.
 */
public void overlayFunction ( OneVariableFunction func, int sampling)
{
	FunctionalCurveDefinition defCurve = new FunctionalCurveDefinition( func, sampling);
	defCurve.setLineColor( Color.red);
	overlayCurve( defCurve);
}
	/**
	 * Constructs the panel used to show the statistics of the histogram.
	 * @return the panel of statistics
	 */
	private Panel statisticsPanel( Histogram histogram)
	{
		Panel p = new Panel();
		p.setBackground( Color.lightGray);
		p.setLayout( new GridLayout( 3, 6, 2, 2));
		Label label = new Label("Underflow");
		label.setAlignment( Label.RIGHT);
		p.add( label);
		underflowDisplay = new TextField(""+histogram.underflow());
		underflowDisplay.setEditable( false);
		underflowDisplay.setBackground( Color.white);
		p.add( underflowDisplay);
		label = new Label("Count");
		label.setAlignment( Label.RIGHT);
		p.add( label);
		countDisplay = new TextField(""+histogram.count());
		countDisplay.setEditable( false);
		countDisplay.setBackground( Color.white);
		p.add( countDisplay);
		label = new Label("Overflow");
		label.setAlignment( Label.RIGHT);
		p.add( label);
		overflowDisplay = new TextField(""+histogram.overflow());
		overflowDisplay.setEditable( false);
		overflowDisplay.setBackground( Color.white);
		p.add( overflowDisplay);
		p.add( new Label(""));
		p.add( new Label(""));
		label = new Label("Average");
		label.setAlignment( Label.RIGHT);
		p.add( label);
		averageDisplay = new TextField(""+histogram.average());
		averageDisplay.setEditable( false);
		averageDisplay.setBackground( Color.white);
		p.add( averageDisplay);
		label = new Label("Skewness");
		label.setAlignment( Label.RIGHT);
		p.add( label);
		skewnessDisplay = new TextField(""+histogram.skewness());
		skewnessDisplay.setEditable( false);
		skewnessDisplay.setBackground( Color.white);
		p.add( skewnessDisplay);
		p.add( new Label(""));
		p.add( new Label(""));
		label = new Label("Standard deviation");
		label.setAlignment( Label.RIGHT);
		p.add( label);
		standardDeviationDisplay = new TextField(""+histogram.standardDeviation());
		standardDeviationDisplay.setEditable( false);
		standardDeviationDisplay.setBackground( Color.white);
		p.add( standardDeviationDisplay);
		label = new Label("Kurtosis");
		label.setAlignment( Label.RIGHT);
		p.add( label);
		kurtosisDisplay = new TextField(""+histogram.kurtosis());
		kurtosisDisplay.setEditable( false);
		kurtosisDisplay.setBackground( Color.white);
		p.add( kurtosisDisplay);
		return p;
	}
}
\end{verbatim}