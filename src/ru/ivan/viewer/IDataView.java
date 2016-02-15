/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ivan.viewer;

import java.awt.Component;
import java.awt.Image;

/**
 *
 * @author MuGund-U
 */
public interface IDataView {

    String getFilePath();

    int getFrameWidth();

    int getFramePosition();

    int getDiscretization();

    void setSampleMaxValue(int maxValue);

    void drawGraphOfSignal(Component comp);

    void setFrameWidthInSeconds(float width);

    void setSpectrogram(Image spectrogramImage);

    int getWindowWidth();



    void drawGraphOfEnergySpectrum(Component comp);

    void drawGraphOfEnergy(Component comp);

    void stopTimer();

    void drawFullGraphAndSpectrOfSignal(Component comp);

    boolean getAutoscale();

    String getTypeOfScaling();

    void setSliderSpectrogram(double maxValue);
    
}
