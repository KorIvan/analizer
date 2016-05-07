/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.BorderLayout;
import java.awt.Image;
import java.util.Arrays;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import jwave.Transform;
import jwave.transforms.AncientEgyptianDecomposition;
import jwave.transforms.DiscreteFourierTransform;
import jwave.transforms.FastWaveletTransform;
import jwave.transforms.WaveletPacketTransform;
import jwave.transforms.wavelets.daubechies.Daubechies10;
import jwave.transforms.wavelets.daubechies.Daubechies14;
import jwave.transforms.wavelets.daubechies.Daubechies2;
import jwave.transforms.wavelets.daubechies.Daubechies20;
import jwave.transforms.wavelets.haar.Haar1;
import jwave.transforms.wavelets.haar.Haar1Orthogonal;
import jwave.transforms.wavelets.symlets.Symlet10;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import ru.spbspu.model.IMathOperations;
import ru.spbspu.model.ISpectrogramPanel;
import ru.spbspu.model.MathOperations;
import ru.spbspu.model.SpectrogramPanel;
import ru.spbspu.model.TestData;

/**
 *
 * @author float
 */
public class MexicanHat {

    public MexicanHat() {
    }
    TestData testData = new TestData();
    int discretization = 1024;
    int framePosition = 0;
    int frameWidth = 8192;
    IMathOperations math = new MathOperations();
    ISpectrogramPanel specPanel;

    @After
    public void tearDown() throws InterruptedException {
        while (true) {
            Thread.sleep(100);
        }
    }

    @Test
    public void mexicanhatTest() {
        //        double[][] data = testData.get2DSignal(100, 200, frameWidth, discretization);
        double frequency = 10;
        int length = 131072;
        int discretization = 500;
        double ampl = 100;
        double[] data = testData.get1DSimpleSignalChangedFrequency(ampl, frequency, length, discretization);
//        double[] data2 = testData.get1DSimpleSignal(ampl, frequency, length, discretization);
//        for (int i = 0; i < data.length; i++) {
//            data[i] += data2[i];
//        }
        specPanel = new SpectrogramPanel(doWaveletTransformTest(data, 2048));
//        specPanel = new SpectrogramPanel(doWaveletTransformTest(math.HanningWindow(data, data.length), 1024));

        //1. Create the frame.
        JFrame frame = new JFrame("Freq=" + frequency + ", Discret=" + discretization + ", Length=" + length + ", Ampl=" + ampl);

//2. Optional: What happens when the frame closes?
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//3. Create components and put them in the frame.
//...create emptyLabel...
        JLabel label = new JLabel("olol");
        Image spectrogram = specPanel.getSpectrogram().getScaledInstance(1000, 500, Image.SCALE_FAST);

        label.setIcon(new ImageIcon(spectrogram));
        frame.getContentPane().add(label, BorderLayout.CENTER);

//4. Size the frame.
        frame.pack();

//5. Show it.
        frame.setVisible(true);
    }

    private double[][] doWaveletTransformTest(double[] data, int window) {
        Transform t = new Transform(new AncientEgyptianDecomposition(
                new FastWaveletTransform(
                        new Daubechies20())));
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

    }
