/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.spbspu.model;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 *
 * @author MuGund-U
 */
public interface ISpectrogramPanel {

    SpectrogramPanel getColorBar();

    double getData(int x, int y);

    int getDataHeight();

    int getDataWidth();

    float getHZoom();

    float getVZoom();

    void paint(Graphics g);

    void setOffsetFactor(double offsetFactor);

    /**
     * @return the scaledSpectrogram
     */
    Image getScaledSpectrogram();

    void changeSpectrogram(double maxValue);

    /**
     * @return the maxVal
     */
    double getMaxVal();

    boolean spectrogramExists();

    /**
     * @return the spectrogram
     */
    BufferedImage getSpectrogram();

    double getMinVal();
    
}
