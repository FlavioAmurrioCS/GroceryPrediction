// package org.jfree.chart.demo;

// import org.jfree.chart.ChartFactory;
// import org.jfree.chart.ChartPanel;
// import org.jfree.chart.JFreeChart;
// import org.jfree.chart.plot.PlotOrientation;
// import org.jfree.data.xy.XYSeries;
// import org.jfree.data.xy.XYSeriesCollection;
// import org.jfree.ui.ApplicationFrame;
// import org.jfree.ui.RefineryUtilities;

// public class XYGraph extends ApplicationFrame {

//     public XYGraph(String windowName, String lineName, String graphName, List<Double> xList, List<Double> yList,
//             String xLabel, String yLabel) {
//         super(windowName);
//         XYSeries series = new XYSeries(lineName);
//         for (int i = 0; i < xList.size(); i++) {
//             series.add(xList.get(i), yList.get(i));
//         }
//         XYSeriesCollection data = new XYSeriesCollection(series);
//         JFreeChart chart = ChartFactory.createXYLineChart(graphName, xLabel, yLabel, data, PlotOrientation.VERTICAL,
//                 true, true, false);
//         final ChartPanel chartPanel = new ChartPanel(chart);
//         chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
//         setContentPane(chartPanel);
//     }

//     public static void graph(String windowName, String lineName, String graphName, List<Double> xList,
//             List<Double> yList, String xLabel, String yLabel) {
//         final XYGraph demo = new XYGraph(windowName, lineName, graphName, xList, yList, xLabel, yLabel);
//         demo.pack();
//         RefineryUtilities.centerFrameOnScreen(demo);
//         demo.setVisible(true);
//     }
// }