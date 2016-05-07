/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.spbspu.model;

import java.util.Random;

/**
 *
 * @author MuGund-U
 */
public class TestData {

    public double[][] getPolyharmSignal(double ampl, double freq, double noiseAmpl) {
        Random noise = new Random();
        int N = 1024;
        double[][] signal = new double[8192][N];
        for (int i = 0; i < signal.length; i++) {
            for (int j = 0; j < signal[0].length; j++) {
                {
                    signal[i][j] = ampl * Math.sin(2 * Math.PI * freq * j * 1 / N)
                            + ampl / 2 * Math.sin(2 * Math.PI * freq / 2 * j * 1 / N)
                            + ampl / 4 * Math.sin(2 * Math.PI * freq / 4 * j * 1 / N)
                            + noiseAmpl * 2 * (0.5 - noise.nextDouble());
                    //System.out.println(signal[i][j]);
                }
            }
        }
        return signal;
    }

    /**
     *
     * @param ampl
     * @param freq
     * @param length
     * @param discretization
     * @return
     */
    public double[] get1DPolyharmSignal(double ampl, double freq, int length, int discretization) {
        double[] signal = new double[length];
        for (int i = 0; i < signal.length; i++) {
            signal[i] = ampl * Math.sin(2 * Math.PI * freq * i * 1 / discretization)
                    + ampl / 2 * Math.sin(2 * Math.PI * freq / 2 * i * 1 / discretization)
                    + ampl / 4 * Math.sin(2 * Math.PI * freq / 4 * i * 1 / discretization);
        }
        return signal;
    }
    
    public double[] get1DSimpleSignal(double ampl, double freq, int length, int discretization) {
        double[] signal = new double[length];
        for (int i = 0; i < signal.length; i++) {
            signal[i] = ampl * Math.sin(2 * Math.PI * freq * i * 1.0 / discretization);                    
        }
        return signal;
    }
    
        public double[] get1DSimpleSignalGrowingFrequency(double ampl, double freq, int length, int discretization) {
        double[] signal = new double[length];
        for (int i = 0; i < signal.length; i++) {
            signal[i] = ampl * Math.sin(2 * Math.PI * freq * i*i * 1.0 / discretization);                    
        }
        return signal;
    }
        /**
         * Частота увеличивается вдвое после середины массива 
         * @param ampl
         * @param freq
         * @param length
         * @param discretization
         * @return 
         */
        public double[] get1DSimpleSignalChangedFrequency(double ampl, double freq, int length, int discretization) {
        double[] signal = new double[length];
        for (int i = 0; i < signal.length/2; i++) {
            signal[i] = ampl * Math.sin(2 * Math.PI * freq * i * 1.0 / discretization);                    
        }
        for (int i = signal.length/2; i < signal.length; i++) {
            signal[i] = ampl * Math.sin(2 * Math.PI * freq*2 * i * 1.0 / discretization);                    
        }
        return signal;
    }
        
        

    /**
     *
     * @param ampl
     * @param freq
     * @param length
     * @param discretization
     * @return
     */
    public double[][] get2DSignal(double ampl, double freq, int length, int discretization) {
        double[][] signal = new double[8192][length];
        for (int i = 0; i < signal.length; i++) {
            for (int j = 0; j < signal[0].length; j++) {
                signal[i][j] = ampl / 4 *
                        Math.sin(2 * Math.PI * freq / 4 * j * 1 / discretization)+
                        + ampl * Math.sin(2 * Math.PI * freq * j * 1 / discretization)+
                        + ampl / 2 * Math.sin(2 * Math.PI * freq / 2 * j * 1 / discretization);
            }
        }
        return signal;
    }
}
