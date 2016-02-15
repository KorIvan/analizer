/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ivan.model;

/**
 *
 * @author float
 */
public interface IMathOperations {

    /**
     * Возвращает массив с двумя значениями: [0] - найденный минимум, [1] -
     * максимум
     */
    double[] searchMinMax(double[] input);

    double[][] transpose(double[][] array2D);


    double[] HanningWindow(double[] signalIn, int size);

    double[] transformFourier(double[] SFT);

    //    @Override
    //    public short[] convertToShort(float[] arrayToConvert) {
    //        short[] output = new short[arrayToConvert.length];
    //        for (int i = 0; i < output.length; i++) {
    //            output[i] = (short) (s * (arrayToConvert[i] - min) / (max - min) - s / 2);
    //            //System.out.println(output[i]);
    //        }
    //        return output;
    //    }
    //
    //    @Override
    //    public short[] convertToShort(double[] arrayToConvert) {
    //        short[] output = new short[arrayToConvert.length];
    //        for (int i = 0; i < output.length; i++) {
    //            output[i] = (short) (s * (arrayToConvert[i] - min) / (max - min) - s / 2);
    //            //System.out.println(output[i]);
    //        }
    //        return output;
    //    }

    double[] calculateEnergy(double[] input,int window);

    public double[] scaleToShort(double[] data, double min, double max);
    public double scaleValue(double value, double min, double max);

    public double calculateMeanEnergy(double[] data);
    
}