/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import jwave.Transform;
import jwave.transforms.FastWaveletTransform;
import jwave.transforms.wavelets.haar.Haar1;
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
public class WaveletTestRisingFrequency {

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
    public void spectrogramByWavelet() throws IOException {
//        double[][] data = testData.get2DSignal(100, 200, frameWidth, discretization);
        double frequency = 500;
        int length = 32768;
        int discretization = 5000;
        double ampl = 10;
        double[] data = testData.get1DSimpleSignalGrowingFrequency(ampl, frequency, length, discretization);
        // retrieve image
        
        specPanel = new SpectrogramPanel(doWaveletTransformTest(math.HammingWindow(data, data.length), 256));

        //1. Create the frame.
        JFrame frame = new JFrame("Freq=" + frequency + ", Discret=" + discretization + ", Length=" + length + ", Ampl=" + ampl);

//2. Optional: What happens when the frame closes?
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//3. Create components and put them in the frame.
//...create emptyLabel...
        JLabel label = new JLabel("olol");
        Image spectrogram = specPanel.getSpectrogram().getScaledInstance(100, 100, Image.SCALE_FAST);
        BufferedImage bi =specPanel.getSpectrogram();
        File outputfile = new File("/home/float/Programs/"+frame.getTitle());
        ImageIO.write(bi, "png", outputfile);
        label.setIcon(new ImageIcon(spectrogram));
        frame.getContentPane().add(label, BorderLayout.CENTER);

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
