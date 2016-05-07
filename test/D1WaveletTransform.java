/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import jwave.Transform;
import jwave.exceptions.JWaveException;
import jwave.transforms.FastWaveletTransform;
import jwave.transforms.WaveletTransform;
import jwave.transforms.wavelets.haar.Haar1;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.CategoryTableXYDataset;
import org.junit.After;
import org.junit.Test;
import ru.spbspu.model.IMathOperations;
import ru.spbspu.model.ISpectrogramPanel;
import ru.spbspu.model.MathOperations;
import ru.spbspu.model.SpectrogramPanel;
import ru.spbspu.model.TestData;

/**
 *
 * @author float
 */
public class D1WaveletTransform {

    TestData testData = new TestData();
    int discretization = 1024;
    int framePosition = 0;
    int frameWidth = 8192;
    IMathOperations math = new MathOperations();
    ISpectrogramPanel specPanel;

    private double[][] doWaveletTransformTest(double[] data, int window) {
        Transform t = new Transform(new FastWaveletTransform(new Haar1()));
        
        int position = 0;
        int frame = data.length;
        double[][] dataForSpectrogram = new double[frame / window * 2][];
        for (int j = 0; j < dataForSpectrogram.length; j++) {
            double[] dataSource = Arrays.copyOfRange(data, position, position + window);
            if (dataSource.length % 2 != 0) {
                break;
            }
            dataForSpectrogram[j] = t.forward(math.HammingWindow(dataSource, dataSource.length));
            position += window / 2;
        }
        return dataForSpectrogram;
    }

     
   @Test
    public void test() throws InterruptedException, JWaveException {
        CategoryTableXYDataset serie = new CategoryTableXYDataset();
        serie.setNotify(false);
        double step = 1.0 / discretization;
        double startPosition = step * framePosition;
        //100 А - 100 Гц, 50 А - 50 Гц, 25 А- 25 Гц
          WaveletTransform t=new FastWaveletTransform((new Haar1()));
        double[] data =t.forward(testData.get1DSimpleSignal(1, 3, frameWidth, discretization),128);
//        double[] data = math.convolve(testData.get1DSignal(100, 200, frameWidth, discretization), math.lpf(70, step, 128));

//        double[] data = math.convolve(testData.get1DSignal(100, 200, 32768, 10000), math.lpf(70, 1./10000, 32));
//        double[] data = testData.get1DSignal(100, 200, frameWidth, discretization);
//        double[] data = math.lpf(70, step,128);
        for (int i = 0; i < data.length; i++) {
            serie.add(startPosition, data[i], "");
            startPosition += step;
        }
        JFreeChart chart = ChartFactory.createXYLineChart("", "t,c", "wave", serie);
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
