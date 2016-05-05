/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ivan.presenter;

import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.sound.sampled.Clip;
import javax.swing.JPanel;
import org.jfree.data.Range;

/**
 *
 * @author MuGund-U
 */
public interface IDataPresenter {

    void loadFile();

    void changeFrame();


    float getFrameWidthInSeconds();


    //void buildOscillogram();


//    double[][] doShortFourierTransform();

    /**
     * @param enabledStart the enabledStart to set
     */
    void setEnabledStart(String path);

    /**
     * @return the enabledStart
     */
    boolean isEnabledStart();

    JPanel drawSpectrogram();



    /**
     * Размер одного отсчета в байтах. 2 для DAT. 4 для ANA
     */
    void setSizeOfCount();

    Image drawPreciseSpectrogram(double limit);

    double[][] doPreciseShortFourierTransform();

    void createWav();

    void stopWav();

    int setDiscretization(String path);

    Clip getWav();

    double[] getFullData();
    double[] getFrameData(int position, int frame);
    Range getMinMaxRange();


    double[] getFrameEnergy(int position, int frame, int window);

    double[] getFullFrameEnergy(int window);

    double[] getFourierTransform(int position, int frame, int window);

    Image getSpectrogram(int position, int frame, int window, double limit);
//    Image getFullSpectrogram(int window);
    BufferedImage getBufferedImageSpectrogram(int position, int frame, int window);

    public Image drawFullSpectrogram(double frequencyLimit, int window, int limit);

    public Range getMinMaxRangeEnergy();

    public Range getMeanRangeEnergy();

    double getDataMax();

    double getDataMin();

    public void showTestData();

    public void showTestData2();

//    public double[] lpFilter(double[] input, double freqLimit);



}
