/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.BorderLayout;
import java.awt.Image;
import java.util.Arrays;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import jwave.Transform;
import jwave.transforms.AncientEgyptianDecomposition;
import jwave.transforms.FastWaveletTransform;
import jwave.transforms.wavelets.daubechies.Daubechies20;
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
import model.cwt.CWT;
import model.cwt.ComplexNumber;

/**
 *
 * @author float
 */
public class CWTTest {
    
    @After
    public void tearDown() throws InterruptedException {
        while(true)
            Thread.sleep(1000);
    }
    TestData testData = new TestData();
    int discretization = 1024;
    int framePosition = 0;
    int frameWidth = 8192;
    IMathOperations math = new MathOperations();
    ISpectrogramPanel specPanel;
    
    private double[][] fromComplexToModule(ComplexNumber[][] input) {
        double[][] output = new double[input.length][input[0].length];
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < output[i].length; j++) {
                output[i][j] = Math.pow((Math.pow(input[i][j].Imaginary, 2) + Math.pow(input[i][j].Real, 2)), 0.5);
            }
        }
        return output;
    }
        private double[][] fromComplexToReal(ComplexNumber[][] input) {
        double[][] output = new double[input.length][input[0].length];
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                output[i][j] = input[i][j].Real;
            }
        }
        return output;
    }

    @Test
    public void cwtTest() {
        //        double[][] data = testData.get2DSignal(100, 200, frameWidth, discretization);
        double frequency = 400;
        int length = 32768;
        int discretization = 5000;
        double ampl = 100;
        double[] data = testData.get1DSimpleSignalGrowingFrequency(ampl, frequency, length, discretization);
//        double [][] scales=(double[][]) CWT.cWT(data, 1.0/length, CWT.Wavelet.Morlet, 5, 1.0/length, 0.25, 49).get(0);
        List<Object> cwt =CWT.cWT(data, 1.0/discretization, CWT.Wavelet.Morlet, 15,1.0/discretization, 0.25, 100);
        for (Object o : cwt) {
            System.out.println("Object number <" + cwt.indexOf(o) + "> is " + o.getClass().toString()+". Value="+o);
        }
        double[] scales =(double[]) cwt.get(1);
                double[] periods =(double[]) cwt.get(2);
                        double[] conOfInfl =(double[]) cwt.get(3);


        for (int i=0; i<scales.length;i++)
            System.out.println("Scale "+i+" is "+scales[i]);
        for (int i=0; i<periods.length;i++)
            System.out.println("Period "+i+" is "+periods[i]);
//        for (int i=0; i<conOfInfl.length;i++)
//            System.out.println("ConOfInfl "+i+" is "+conOfInfl[i]);
        specPanel = new SpectrogramPanel(fromComplexToModule((ComplexNumber[][]) cwt.get(0)));

//        specPanel = new SpectrogramPanel(doWaveletTransformTest(math.HanningWindow(data, data.length), 1024));
        //1. Create the frame.
        JFrame frame = new JFrame("Freq=" + frequency + ", Discret=" + discretization + ", Length=" + length + ", Ampl=" + ampl);

//2. Optional: What happens when the frame closes?
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
