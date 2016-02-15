/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ivan.model;

/**
 *
 * @author MuGund-U
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageObserver;
import java.awt.image.ReplicateScaleFilter;
import java.util.Arrays;

import javax.swing.JPanel;

public class SpectrogramPanel extends JPanel implements ISpectrogramPanel {

    private BufferedImage spectrogram;

    private Image scaledSpectrogram;

    private float zoom = 1.0f;
    private float vzoom = 1.0f;

    private double offsetFactor;

    private double[][] data;

    private int width;
    private int height;

    private ColorMap cmap = ColorMap.getJet(64);
    //private static float minZoom = .1f;

    private double minVal;
    private double maxVal;

    public SpectrogramPanel(double[][] dat) {
        data = dat;
        width = dat.length;
        height = dat[0].length;
        computeSpectrogram();
    }

    public SpectrogramPanel(double[][] dat, double maxValue) {
        data = dat;
        width = dat.length;
        height = dat[0].length;
        changeSpectrogram(maxValue);
    }
    public SpectrogramPanel(SpectrogramPanel p, double maxValue) {
        data = p.data;
        width = p.data.length;
        height = p.data[0].length;
        changeSpectrogram(maxValue);
    }

    private void computeSpectrogram() {
        try {
            // prepare the data:

            setMaxVal(0);
            setMinVal(Integer.MAX_VALUE);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {

                    if (data[x][y] > getMaxVal()) {
                        setMaxVal(data[x][y]);
                                              //  System.out.println(data[x][y]);

                    }
                    if (data[x][y] < minVal) {

                        setMinVal(data[x][y]);
                    }

                }
            }
            double minIntensity = Math.abs(minVal);
            double maxIntensity = getMaxVal() + minIntensity;

            int maxYIndex = height - 1;
            Dimension d = new Dimension(width, height);

            setMinimumSize(d);
            setMaximumSize(d);
            setPreferredSize(d);

            /* Create the image for displaying the data.
             */
            spectrogram = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            /* Set scaleFactor so that the maximum value, after removing
             * the offset, will be 0xff.
             */
            double scaleFactor = ((0x3f + offsetFactor) / maxIntensity);

            for (int i = 0; i < width; i++) {
                for (int j = maxYIndex; j >= 0; j--) {

                    /* Adjust the grey value to make a value of 0 to mean
                     * white and a value of 0xff to mean black.
                     */
                    int grey = (int) ((data[i][j] + minIntensity) * scaleFactor - offsetFactor);

                    // use grey as an index into the colormap;
                    getSpectrogram().setRGB(i, maxYIndex - j, cmap.getColor(grey));
                }
            }

            ImageFilter scaleFilter = new ReplicateScaleFilter((int) (zoom * width), (int) (vzoom * height));
            scaledSpectrogram = createImage(new FilteredImageSource(getSpectrogram().getSource(), scaleFilter));
            Dimension sz = getSize();
            repaint(0, 0, 0, sz.width - 1, sz.height - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeSpectrogram(double maxValue) {
        try {
            setMaxVal(maxValue);
            setMinVal(Integer.MAX_VALUE);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    
                    if (data[x][y] < minVal) {

                        setMinVal(data[x][y]);
                    }

                }
            }
            double minIntensity = Math.abs(minVal);
            double maxIntensity = maxValue + minIntensity;

            int maxYIndex = height - 1;
            Dimension d = new Dimension(width, height);

            setMinimumSize(d);
            setMaximumSize(d);
            setPreferredSize(d);

            /* Create the image for displaying the data.
             */
            spectrogram = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            /* Set scaleFactor so that the maximum value, after removing
             * the offset, will be 0xff.
             */
            double scaleFactor = ((0x3f + offsetFactor) / maxIntensity);

            for (int i = 0; i < width; i++) {
                for (int j = maxYIndex; j >= 0; j--) {

                    /* Adjust the grey value to make a value of 0 to mean
                     * white and a value of 0xff to mean black.
                     */
                    int grey = (int) ((data[i][j] + minIntensity) * scaleFactor - offsetFactor);

                    // use grey as an index into the colormap;
                    System.out.println(cmap.toString());
                    getSpectrogram().setRGB(i, maxYIndex - j, cmap.getColor(grey));
                }
            }

            ImageFilter scaleFilter = new ReplicateScaleFilter((int) (zoom * width), (int) (vzoom * height));
            scaledSpectrogram = createImage(new FilteredImageSource(getSpectrogram().getSource(), scaleFilter));
            Dimension sz = getSize();
            repaint(0, 0, 0, sz.width - 1, sz.height - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setOffsetFactor(double offsetFactor) {
        this.offsetFactor = offsetFactor;
        computeSpectrogram();
    }

    protected void vzoomSet(float vzoom) {
        this.vzoom = vzoom;
        zoom();
    }

    protected void hzoomSet(float zoom) {
        zoomSet(zoom);
    }

    protected void zoomSet(float zoom) {
        this.zoom = zoom;
        zoom();
    }

    private void zoom() {
        if (getSpectrogram() != null) {

        }
        {
            int width = getSpectrogram().getWidth();
            int height = getSpectrogram().getHeight();

            // do the zooming
            width = (int) (zoom * width);
            height = (int) (vzoom * height);

            ImageFilter scaleFilter = new ReplicateScaleFilter(width, height);
            scaledSpectrogram = createImage(new FilteredImageSource(getSpectrogram().getSource(), scaleFilter));

            // so ScrollPane gets notified of the new size:
            setPreferredSize(new Dimension(width, height));
            revalidate();

            repaint();
        }
    }

    @Override
    public float getVZoom() {
        return vzoom;
    }

    @Override
    public float getHZoom() {
        return zoom;
    }

    @Override
    public SpectrogramPanel getColorBar() {
        int barWidth = 20;

        double[][] cb = new double[barWidth][cmap.size];

        for (int x = 0; x < cb.length; x++) {

            for (int y = 0; y < cb[x].length; y++) {

                cb[x][y] = y;
            }
        }

        return new SpectrogramPanel(cb);
    }

    @Override
    public double getData(int x, int y) {
        return data[x][y];
    }

    @Override
    public int getDataWidth() {
        return width;
    }

    @Override
    public int getDataHeight() {
        return height;
    }

    @Override
    public void paint(Graphics g) {
        Dimension sz = getSize();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, sz.width - 1, sz.height - 1);

        if (getSpectrogram() != null) {
            g.drawImage(getScaledSpectrogram(), 0, 0, (ImageObserver) null);
        }
    }

    /**
     * @return the spectrogram
     */
    public BufferedImage getSpectrogram() {
        return spectrogram;
    }

    /**
     * @return the scaledSpectrogram
     */
    @Override
    public Image getScaledSpectrogram() {
        return scaledSpectrogram;
    }

    /**
     * @param minVal the minVal to set
     */
    public void setMinVal(double minVal) {
        this.minVal = minVal;
    }

    /**
     * @param maxVal the maxVal to set
     */
    public void setMaxVal(double maxVal) {
        this.maxVal = maxVal;
    }

    /**
     * @return the maxVal
     */
    public double getMaxVal() {
        return maxVal;
    }
    public double getMinVal(){
        return minVal;
    }

    public boolean spectrogramExists() {
        if (!scaledSpectrogram.equals(null)) {
            return true;
        }
        return false;
    }
}
