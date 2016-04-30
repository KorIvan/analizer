/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ivan.presenter;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JPanel;
import jwave.Transform;
import jwave.transforms.FastWaveletTransform;
import jwave.transforms.wavelets.haar.Haar1;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.Range;
import org.jfree.data.xy.CategoryTableXYDataset;

import ru.ivan.model.*;
import ru.ivan.viewer.IDataView;

/**
 *
 * @author MuGund-U
 */
public class DataPresenter implements IDataPresenter {

    private IDataLoader _model;
    private IDataView _viewer;
    private ISpectrogramPanel _spectrogram;
    private boolean enabledStart;
    private double maxValueInFFTGraph = 0;
    private int size;
    private double maxValueSpec;
    private StdAudio _audio;
    private IMathOperations _math;
    private double min;
    private double max;
    private final static int LIMIT_CONST = 1;

    public DataPresenter(IDataView viewer) {
        _model = new DataLoader();
        _viewer = viewer;
        _audio = new StdAudio();
        _math = new MathOperations();
    }

    @Override
    public void loadFile() {
        _model.loadData(_viewer.getFilePath());

        //Установка максимального значения в слайдере
        int maxValue = (int) (_model.getFullData().length - _viewer.getFrameWidth());
        System.out.println(_model.getFullData().length + " " + _viewer.getFrameWidth() + "!!!!!!!!!!!!!");
        System.out.println(_model.getDiscretizationFromFileAnp(_viewer.getFilePath()));
        System.out.println(_viewer.getFilePath());
        _viewer.setFrameWidthInSeconds(getFrameWidthInSeconds());
        _viewer.setSampleMaxValue(maxValue);
        System.out.println("Slider max value " + maxValue + "; file length" + _model.getFullData().length);
    }

    public int calculateTime() {

        return (int) (_model.getFullData().length / _viewer.getDiscretization());
    }

    @Override
    public float getFrameWidthInSeconds() {
        float frame = _viewer.getFrameWidth() * Float.BYTES;
        return frame / (_viewer.getDiscretization() * Float.BYTES);
    }

    @Override
    public void changeFrame() {
//        int frame = _viewer.getFrameWidth();
//        long positionInBytes = _viewer.getFramePosition() * size;
//        _model.loadIntoFrame(frame, positionInBytes);
    }

    /**
     * @return the enabledStart
     */
    public boolean isEnabledStart() {
        return enabledStart;
    }

    /**
     * @param enabledStart the enabledStart to set
     */
    public void setEnabledStart(String path) {
        if (path.toLowerCase().endsWith(".ana") || path.toLowerCase().endsWith(".dat")) {
            this.enabledStart = true;
        }
    }

    public float timePositionInFile() {
        float position = (float) _viewer.getFramePosition() / _viewer.getDiscretization();
        return position;
    }

    @Override
    public void createWav() {
        System.out.println(_viewer.getFilePath());
        System.out.println(_viewer.getDiscretization());
        System.out.println(_model.getFullFileInArray());
        String filename = _viewer.getFilePath();
        filename += ".wav";
        System.out.println(filename);
        _audio.save(filename, _model.formatDataForAudio(_model.getFullFileInArray()), _viewer.getDiscretization());
    }

    @Override
    public Clip getWav() {
        String filename = _viewer.getFilePath() + ".wav";
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
            clip.open(_audio.getAudioStream(filename));
        } catch (LineUnavailableException | IOException ex) {
            Logger.getLogger(DataPresenter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return clip;
    }

    @Override
    public void stopWav() {
        _audio.close();
    }

    @Override
    public int setDiscretization(String path) {
        String file = path.substring(0, path.lastIndexOf(".")) + ".anp";
        return _model.getDiscretizationFromFileAnp(file);
    }

    public double[][] doShortFourierTransform() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JPanel drawSpectrogram() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return new JPanel();
    }

    @Override
    public void setSizeOfCount() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double[][] doPreciseShortFourierTransform() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public double[] getFullData() {
        return _model.getFullData();
    }

    public Range getMinMaxRange() {
        return new Range(_model.getMin(), _model.getMax());
    }

    @Override
    public Image drawPreciseSpectrogram(double limit) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double[] getFrameData(int position, int frame) {
        return Arrays.copyOfRange(_model.getFullData(), position, position + frame);
    }

    @Override
    public double[] getFrameEnergy(int position, int frame, int window) {
        //!!!
        //double[] scaled = _math.scaleToShort(Arrays.copyOfRange(_model.getFullData(), position, position + frame), _model.getMin(), _model.getMax());
        // return _math.calculateEnergy(scaled, window);
        return _math.calculateEnergy(Arrays.copyOfRange(_model.getFullData(), position, position + frame), window);

    }

    @Override
    public double[] getFullFrameEnergy(int window) {
//!!!        
//double[] energy = _math.calculateEnergy(_math.scaleToShort(_model.getFullData(), _model.getMin(), _model.getMax()), window);
        double[] energy = _math.calculateEnergy(_model.getFullData(), window);

        double[] minAndMax = _math.searchMinMax(energy);
        double meanEenrgy = _math.calculateMeanEnergy(energy);
        _model.setMeanEnergy(meanEenrgy);
        _model.setMaxEnergy(minAndMax[1]);
        _model.setMinEnergy(minAndMax[0]);
        return energy;
    }

    @Override
    public Image getSpectrogram(int position, int frame, int window, double limit) {
        double[][] temp = doSFTForSpectrogram(position, frame, window);
        double[][] data = new double[temp.length][(int) (temp[0].length * limit)];

//       System.arraycopy(temp, 0, data, 0, data[0].length);
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                data[i][j] = temp[i][j];
            }
        }
        System.out.println("length must be " + (int) (temp[0].length * limit));
        System.out.println("temp length " + temp[0].length + "temp.length " + temp.length);

        System.out.println("data[0] length " + data[0].length + " data.length " + data.length);
        _spectrogram = new SpectrogramPanel(data);
        return _spectrogram.getScaledSpectrogram();
    }

    @Override
    public BufferedImage getBufferedImageSpectrogram(int position, int frame, int window) {
        _spectrogram = new SpectrogramPanel(doSFTForSpectrogram(position, frame, window));
        return _spectrogram.getSpectrogram();
    }

    private double[][] doWaveletTransformFullSpectrogram(int window) {
        Transform t = new Transform(new FastWaveletTransform(new Haar1()));
        int position = 0;
        int frame = _model.getFullData().length;
        double[][] dataForSpectrogram = new double[frame / window * 2][];
        for (int j = 0; j < dataForSpectrogram.length; j++) {
            double[] dataSource = Arrays.copyOfRange(_model.getFullData(), position, position + window);
            if (dataSource.length % 2 != 0) {
                break;
            }
            dataForSpectrogram[j] = t.forward(_math.HammingWindow(dataSource, dataSource.length));
            position += window / 2;
        }
        return dataForSpectrogram;
    }

    private double[][] doSFTForSpectrogram(int position, int frame, int window) {
        String windowFunction = _viewer.getWindowFunction();
        double[][] dataForSpectrogram = new double[frame / window * 2][];
        for (int j = 0; j < dataForSpectrogram.length; j++) {
            double[] dataSource = Arrays.copyOfRange(_model.getFullData(), position, position + window);
            dataForSpectrogram[j] = null;
            if (windowFunction.equalsIgnoreCase("Hann")) {
                dataForSpectrogram[j] = _math.transformFourier(_math.HanningWindow(dataSource, dataSource.length));
            } else if (windowFunction.equalsIgnoreCase("Hamming")) {
                dataForSpectrogram[j] = _math.transformFourier(_math.HammingWindow(dataSource, dataSource.length));
            }
            position += window / 2;
        }
        return dataForSpectrogram;
    }

    private double[][] doSFTForFullSpectrogram(int window) {
        int position = 0;
        int frame = _model.getFullData().length;
        double[][] dataForSpectrogram = new double[frame / window * 2][];
        for (int j = 0; j < dataForSpectrogram.length; j++) {
            double[] dataSource = Arrays.copyOfRange(_model.getFullData(), position, position + window);
            if (dataSource.length % 2 != 0) {
                break;
            }
            dataForSpectrogram[j] = _math.transformFourier(_math.HanningWindow(dataSource, dataSource.length));
            position += window / 2;
        }
        return dataForSpectrogram;
    }

    @Override
    public double[] getFourierTransform(int position, int frame, int window) {
        return _math.transformFourier(getFrameData(position, frame));
    }

    @Override
    public Image drawFullSpectrogram(double frequencyLimit, int window, int limit) {
        double[][] transformed = null;
        if (_viewer.getTransformationType().equals("fourier")) {
            transformed = doSFTForFullSpectrogram(window);
        } else if (_viewer.getTransformationType().equals("wavelet")) {
            transformed = doWaveletTransformFullSpectrogram(window);
            System.out.println("Wavelet starnarn!!1");
            System.out.println(transformed);
            System.out.println("!!! " + transformed.length);
        }
        double[][] data = new double[transformed.length][(int) (transformed[0].length * frequencyLimit)];
//        System.out.println("length must be "+(int) (transformed[0].length * frequencyLimit));
//        System.out.println("temp length "+transformed[0].length+"temp.length "+transformed.length);
//        System.out.println("data[0] length "+data[0].length+" data.length "+data.length);
        double limitDouble = 1.0 * limit / LIMIT_CONST;
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                if (transformed[i][j] >= limitDouble) {
                    data[i][j] = limitDouble;
                } else {
                    data[i][j] = transformed[i][j];
                }
            }
        }
        timePositionInFile();
        SpectrogramPanel spectrogram = new SpectrogramPanel(data);
//        SpectrogramPanel spectrogram = new SpectrogramPanel(data);
//        _spectrogram = new SpectrogramPanel(data);
        return spectrogram.getScaledSpectrogram();
    }

    @Override
    public Range getMinMaxRangeEnergy() {
        return new Range(_model.getMinEnergy(), _model.getMaxEnergy());

    }

    @Override
    public Range getMeanRangeEnergy() {

        return new Range(_model.getMinEnergy(), _model.getMeanEnergy());
    }

    public double getDataMin() {
        return _model.getMin() * LIMIT_CONST;
    }

    public double getDataMax() {
        return _model.getMax() * LIMIT_CONST;
    }

    @Override
    public void showTestData() {
        double freq1 = 200;
        double freq2 = 400;
        double freq3 = 800;
        double ampl1 = 2;
        double ampl2 = 1.2;
        double ampl3 = 1.5;
        Random noise = new Random();
        int N = 5000;
        double[] signal = new double[32768];
        for (int i = 0; i < signal.length; i++) {

            signal[i] = ampl1 * Math.sin(2 * Math.PI * freq1 * i * 1.0 / N)
                    + ampl2 * Math.sin(2 * Math.PI * freq2 * i * 1.0 / N)
                    + ampl3 * Math.sin(2 * Math.PI * freq3 * i * 1.0 / N);
//                    + noiseAmpl * 2 * (0.5 - noise.nextDouble());
            //System.out.println(signal[i][j]);

        }
        _model.setFullData(signal);

    }

    @Override
    public void showTestData2() {
double freq1 = 200;
        double freq2 = 400;
        double freq3 = 800;
        double ampl1 = 2;
        double ampl2 = 1.2;
        double ampl3 = 1.2;
        Random noise = new Random();
        int N = 5000;
        double[] signal = new double[32768];
        double v1=0;
        double v2=0;
        double v3=ampl3;
        
        for (int i = 0; i < signal.length; i++) {
            double a1=v1;
            double a2=v2;
            double a3=v3;
            double f3=freq3;
            if (v1>=ampl1)
                a1=ampl1;
            if (v2>=ampl2)
                a2=ampl2;
            if (v3<=0)
                a3=0;
            if (freq3<=450)
                f3=450;
            signal[i] = a1 * Math.sin(2 * Math.PI * freq1 * i * 1.0 / N)
                    + a2 * Math.sin(2 * Math.PI * freq2 * i * 1.0 / N)
                    + a3 * Math.sin(2 * Math.PI * f3 * i * 1.0 / N);
            v1+=0.00001;v2+=0.00005;v3-=0.00004;freq3-=0.01;
        }
        _model.setFullData(signal);
    }

}
