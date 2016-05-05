/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.BorderLayout;
import java.awt.Label;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.CategoryTableXYDataset;
import org.junit.After;
import org.junit.Test;
import ru.ivan.model.IMathOperations;
import ru.ivan.model.MathOperations;
import ru.ivan.model.TestData;

/**
 *
 * @author float
 */
public class LowPassFilterTest {

    public LowPassFilterTest() {
    }
        TestData testData = new TestData();
        int discretization = 5000;
        int framePosition = 0;
        int frameWidth = 32768;
        IMathOperations math=new MathOperations();
        
    @Test
    public void test() throws InterruptedException {
        CategoryTableXYDataset serie = new CategoryTableXYDataset();
        serie.setNotify(false);
        double step = 1.0 / discretization;
        double startPosition = step * framePosition;
        //100 А - 100 Гц, 50 А - 50 Гц, 25 А- 25 Гц
        double[] data = math.convolve(math.HammingWindow(testData.get1DSignal(100, 200, frameWidth, discretization), frameWidth), math.lpf(60, step,1024 ));
//        double[] data = math.convolve(testData.get1DSignal(100, 200, frameWidth, discretization), math.lpf(70, step, 128));

//        double[] data = math.convolve(testData.get1DSignal(100, 200, 32768, 10000), math.lpf(70, 1./10000, 32));

//        double[] data = testData.get1DSignal(100, 200, frameWidth, discretization);

//        double[] data = math.lpf(70, step,128);
        
for (int i = 0; i < data.length; i++) {
            serie.add(startPosition, data[i], "");
            startPosition += step;
        }
        JFreeChart chart = ChartFactory.createXYLineChart("", "t,c", "g, м/c^2", serie);
        chart.removeLegend();
        chart.setAntiAlias(false);

        XYPlot plot = chart.getXYPlot();
        //plot.setRangeGridlinePaint(Color.BLACK);
        org.jfree.chart.axis.ValueAxis yAxis = plot.getRangeAxis();
        org.jfree.chart.axis.ValueAxis xAxis = plot.getDomainAxis();
        double start = framePosition * 1.0 / discretization;
        double max = start
                + frameWidth * 1.0 / discretization;
        xAxis.setRange(start, max);
        ChartPanel chartPanel = new ChartPanel(chart);

        JPanel p = new JPanel(new BorderLayout());

        p.removeAll();
        p.add(chartPanel);
        p.validate();
        //1. Create the frame.
        JFrame frame = new JFrame("FrameDemo");

//2. Optional: What happens when the frame closes?
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//3. Create components and put them in the frame.
//...create emptyLabel...
        frame.getContentPane().add(new Label("olol"), BorderLayout.CENTER);
        frame.getContentPane().add(p, BorderLayout.CENTER);

//4. Size the frame.
        frame.pack();

//5. Show it.
        frame.setVisible(true);
    }

    @After
    public void tearDown() throws InterruptedException {
        while (true) {
            Thread.sleep(100);
        }
    }
}
