import javax.swing.*;
import java.awt.*;
import org.jfree.chart.*;//{{{
import org.jfree.chart.axis.*;
import org.jfree.chart.labels.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.xy.*;
import org.jfree.chart.title.*;
import org.jfree.data.time.*;//}}}
import java.util.Date; 
import java.text.*;
import java.awt.Font;
import java.io.*;
import java.util.Calendar;

public class DataGraphView extends JFrame{
	private Thread th;
	private TimeSeries AccelSeries;//{{{
	private TimeSeries InsideTempSeries;
	private TimeSeries OutsideTempSeries;
	private TimeSeries LM60TempSeries;
	private TimeSeries BrightSeries;
	private TimeSeries WetSeries;
	private TimeSeries UnconfortSeries;
	private TimeSeries PressureSeries;//}}}
	private JPanel panel;

	public DataGraphView() {//{{{
		panel = new JPanel();
		panel.setLayout(new GridLayout(2,2));
		initChart();
		getContentPane().add(panel);
		pack();
		this.setVisible(true);
	}//}}}

	public void initChart() {//{{{
		//Define Timeseries
		AccelSeries = new TimeSeries("Accel", Millisecond.class);
		InsideTempSeries = new TimeSeries("Inside Temp", Millisecond.class);
		OutsideTempSeries = new TimeSeries("Outside Temp", Millisecond.class);
		LM60TempSeries = new TimeSeries("LM60 Temp", Millisecond.class);
		BrightSeries = new TimeSeries("Bright", Millisecond.class);
		PressureSeries = new TimeSeries("Pressure", Millisecond.class);
		WetSeries = new TimeSeries("Shitudo", Millisecond.class);
		UnconfortSeries = new TimeSeries("Disconfort", Millisecond.class);

		//Define dataSet
		TimeSeriesCollection accelset = new TimeSeriesCollection();
		TimeSeriesCollection tempset = new TimeSeriesCollection();
		TimeSeriesCollection brightset = new TimeSeriesCollection();
		TimeSeriesCollection pressset = new TimeSeriesCollection();
		TimeSeriesCollection wetset = new TimeSeriesCollection();
		TimeSeriesCollection unconfortset = new TimeSeriesCollection();


		accelset.addSeries(AccelSeries);
		tempset.addSeries(InsideTempSeries);
		tempset.addSeries(OutsideTempSeries);
		tempset.addSeries(LM60TempSeries);
		brightset.addSeries(BrightSeries);
		

		pressset.addSeries(PressureSeries);
		wetset.addSeries(WetSeries);
		unconfortset.addSeries(UnconfortSeries);

		//Define Axis
		DateAxis accelDomainAxis = new DateAxis("Time");
		accelDomainAxis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);
		ValueAxis accelRangeAxis = new NumberAxis("Accelaration");

		DateAxis tempDomainAxis = new DateAxis("Time");
		tempDomainAxis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);
		ValueAxis tempRangeAxis = new NumberAxis("Temperature");

		DateAxis brightDomainAxis = new DateAxis("Time");
		brightDomainAxis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);
		ValueAxis brightRangeAxis = new NumberAxis("Brightness");

		DateAxis pressDomainAxis = new DateAxis("Time");
		pressDomainAxis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);
		ValueAxis pressRangeAxis = new NumberAxis("Pressure");

		DateAxis wetDomainAxis = new DateAxis("Time");
		wetDomainAxis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);
		ValueAxis wetRangeAxis = new NumberAxis("Shitudo");

		DateAxis unconfortDomainAxis = new DateAxis("Time");
		unconfortDomainAxis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);
		ValueAxis unconfortRangeAxis = new NumberAxis("Disconfort");

		//Define XY Renderer
		XYItemRenderer accelrenderer = new StandardXYItemRenderer();
		accelrenderer.setToolTipGenerator(StandardXYToolTipGenerator.getTimeSeriesInstance());

		XYItemRenderer temprenderer = new StandardXYItemRenderer();
		temprenderer.setToolTipGenerator(StandardXYToolTipGenerator.getTimeSeriesInstance());
		XYItemRenderer brightrenderer = new StandardXYItemRenderer();
		brightrenderer.setToolTipGenerator(StandardXYToolTipGenerator.getTimeSeriesInstance());
		XYItemRenderer pressrenderer = new StandardXYItemRenderer();
		pressrenderer.setToolTipGenerator(StandardXYToolTipGenerator.getTimeSeriesInstance());
		XYItemRenderer wetrenderer = new StandardXYItemRenderer();
		wetrenderer.setToolTipGenerator(StandardXYToolTipGenerator.getTimeSeriesInstance());
		XYItemRenderer unconfortrenderer = new StandardXYItemRenderer();
		unconfortrenderer.setToolTipGenerator(StandardXYToolTipGenerator.getTimeSeriesInstance());

		//Define Plot
		XYPlot accelPlot = new XYPlot(accelset,accelDomainAxis,accelRangeAxis,accelrenderer);
		accelPlot.getRenderer().setToolTipGenerator(StandardXYToolTipGenerator.getTimeSeriesInstance());

		XYPlot tempPlot = new XYPlot(tempset,tempDomainAxis, tempRangeAxis, temprenderer);
		tempPlot.getRenderer().setToolTipGenerator(StandardXYToolTipGenerator.getTimeSeriesInstance());

		XYPlot brightPlot = new XYPlot(brightset, brightDomainAxis, brightRangeAxis, brightrenderer);
		brightPlot.getRenderer().setToolTipGenerator(StandardXYToolTipGenerator.getTimeSeriesInstance());

		XYPlot pressPlot = new XYPlot(pressset, pressDomainAxis, pressRangeAxis, pressrenderer);
		pressPlot.getRenderer().setToolTipGenerator(StandardXYToolTipGenerator.getTimeSeriesInstance());

		XYPlot wetPlot = new XYPlot(wetset, wetDomainAxis, wetRangeAxis, wetrenderer);
		wetPlot.getRenderer().setToolTipGenerator(StandardXYToolTipGenerator.getTimeSeriesInstance());

		XYPlot unconfortPlot = new XYPlot(unconfortset, unconfortDomainAxis, unconfortRangeAxis, unconfortrenderer);
		unconfortPlot.getRenderer().setToolTipGenerator(StandardXYToolTipGenerator.getTimeSeriesInstance());

		JFreeChart accelChart = new JFreeChart("XYZ Acceleration", JFreeChart.DEFAULT_TITLE_FONT, accelPlot, true);
		LegendTitle accelLegend = accelChart.getLegend();
		accelLegend.setItemFont(new Font("Ariel", Font.BOLD, 20));
		ChartPanel accelPanel = new ChartPanel(accelChart);


		JFreeChart TempChart = new JFreeChart("Temperature", JFreeChart.DEFAULT_TITLE_FONT, tempPlot, true);
		LegendTitle TempLegend = TempChart.getLegend();
		TempLegend.setItemFont(new Font("Ariel", Font.BOLD, 20));
		ChartPanel TempPanel = new ChartPanel(TempChart);

		JFreeChart BrightChart = new JFreeChart("Brightness", JFreeChart.DEFAULT_TITLE_FONT, brightPlot, true);
		LegendTitle BrightLegend = BrightChart.getLegend();
		BrightLegend.setItemFont(new Font("Ariel", Font.BOLD, 20));
		ChartPanel BrightPanel = new ChartPanel(BrightChart);

		JFreeChart PressChart = new JFreeChart("Pressure", JFreeChart.DEFAULT_TITLE_FONT, pressPlot, true);
		LegendTitle PressLegend = PressChart.getLegend();
		PressLegend.setItemFont(new Font("Ariel", Font.BOLD, 20));
		ChartPanel PressPanel = new ChartPanel(PressChart);

		JFreeChart WetChart = new JFreeChart("Shitudo", JFreeChart.DEFAULT_TITLE_FONT, wetPlot, true);
		LegendTitle WetLegend = WetChart.getLegend();
		WetLegend.setItemFont(new Font("Ariel", Font.BOLD, 20));
		ChartPanel WetPanel = new ChartPanel(WetChart);

		JFreeChart UnconfortChart = new JFreeChart("Unconfort", JFreeChart.DEFAULT_TITLE_FONT, unconfortPlot, true);
		LegendTitle UnconfortLegend = UnconfortChart.getLegend();
		UnconfortLegend.setItemFont(new Font("Ariel", Font.BOLD, 20));
		ChartPanel UnconfortPanel = new ChartPanel(UnconfortChart);

		panel.add(accelPanel);
		panel.add(TempPanel);
		panel.add(BrightPanel);
		panel.add(PressPanel);
		panel.add(WetPanel);
		panel.add(UnconfortPanel);
	}//}}}

	public void loadData(Object[][] data) {//{{{
		double accel,insideTemp,outsideTemp,lm60,bright,press,wet,unconfort;
		int year;
		int month;
		int day;
		int hour;
		int minutes;
		int second;
		int millisecond;
		for(int i = 0; i < data.length; i++) {
			//Substitution data
			year = Integer.parseInt(data[i][0].toString().substring(0,4));
			month = Integer.parseInt(data[i][0].toString().substring(4,6));
			day = Integer.parseInt(data[i][0].toString().substring(6,8));
			
			hour = Integer.parseInt(data[i][1].toString().substring(0,2));
			minutes = Integer.parseInt(data[i][1].toString().substring(3,5));
			second = Integer.parseInt(data[i][1].toString().substring(6,8));
			millisecond = Integer.parseInt(data[i][1].toString().substring(9,13));

			accel = Double.parseDouble(data[i][2].toString());
			insideTemp = Double.parseDouble(data[i][3].toString());
			outsideTemp = Double.parseDouble(data[i][4].toString());
			lm60 = Double.parseDouble(data[i][5].toString());
			bright = Double.parseDouble(data[i][6].toString());
			press = Double.parseDouble(data[i][7].toString());
			wet = Double.parseDouble(data[i][8].toString());
			unconfort = Double.parseDouble(data[i][9].toString());

			//Add Data to TimeSeries
			AccelSeries.add(new Millisecond(millisecond, second, minutes, hour, day, month, year),accel);
			InsideTempSeries.add(new Millisecond(millisecond, second, minutes, hour, day, month, year), insideTemp);
			OutsideTempSeries.add(new Millisecond(millisecond, second, minutes, hour, day, month, year), outsideTemp);
			LM60TempSeries.add(new Millisecond(millisecond, second, minutes, hour, day, month, year), lm60);
			BrightSeries.add(new Millisecond(millisecond,second,minutes,hour,day,month,year), bright); 
			PressureSeries.add(new Millisecond(millisecond,second,minutes,hour,day,month,year), press);
			WetSeries.add(new Millisecond(millisecond, second, minutes, hour, day, month, year), wet);
			UnconfortSeries.add(new Millisecond(millisecond, second, minutes, hour, day, month, year), unconfort);	
		}
	}//}}}
}
