/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ivan.model;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

/**
 *
 * @author float
 */
public class MathOperations implements IMathOperations {

    /**
     * Константа для масштабирования значений из файла .ana в диапазон short
     */
    private final int s = 1;

    /**
     * Возвращает массив с двумя значениями: [0] - найденный минимум, [1] -
     * максимум
     */
    @Override
    public double[] searchMinMax(double[] input) {
        double[] minAndMax = new double[2];
        double min = input[0];
        double max = input[0];
        for (int i = 0; i < input.length; i++) {
            if (input[i] > max) {
                max = input[i];
            } else if (input[i] < min) {
                min = input[i];
            }
        }
        minAndMax[0] = min;
        minAndMax[1] = max;
        return minAndMax;
    }

    @Override
    public double[] transformFourier(double[] SFT) {
        FastFourierTransformer fft = new FastFourierTransformer(DftNormalization.UNITARY);
        Complex[] transformed = fft.transform(SFT, TransformType.FORWARD);
        for (int i = 0; i < SFT.length; i++) {
            SFT[i] = transformed[i].abs();
        }
        double[] output = new double[SFT.length / 2];
        for (int i = 0; i < output.length; i++) {
            output[i] = SFT[i + 2];
        }
        return output;
        //FastCosineTransformer rfft = new FastCosineTransformer(DctNormalization.STANDARD_DCT_I);
        //rfft.transform(arrDouble, TransformType.FORWARD);
    }

//    public double[] transformFourier(float[] SFT) {
//        double[] input = new double[SFT.length];
//        for (int i = 0; i < input.length; i++) {
//            input[i] = SFT[i];
//        }
//        FastFourierTransformer fft = new FastFourierTransformer(DftNormalization.UNITARY);
//        Complex[] transformed = fft.transform(input, TransformType.FORWARD);
//        for (int i = 0; i < SFT.length; i++) {
//            input[i] = transformed[i].abs();
//        }
//        double[] output = new double[SFT.length / 2];
//        for (int i = 0; i < output.length; i++) {
//            output[i] = input[i + 2];
//        }
//        return output;
//        //FastCosineTransformer rfft = new FastCosineTransformer(DctNormalization.STANDARD_DCT_I);
//        //rfft.transform(arrDouble, TransformType.FORWARD);
//    }
    @Override
    public double[] HanningWindow(double[] signalIn, int size) {
        double[] toFFT = new double[size];
        for (int i = 0; i < size; i++) {
            int j = i; // j = index into Hann window function
            toFFT[i] = (signalIn[i] * 0.5 * (1.0 - Math.cos(2.0 * Math.PI * j / size)));

        }
        return toFFT;
    }

    @Override
    public double[][] transpose(double[][] array2D) {
        double[][] output = new double[array2D[0].length][array2D.length];
        for (int i = 0; i < array2D[0].length; i++) {
            for (int j = 0; j < array2D.length; j++) {
                output[i][j] = array2D[j][i];
                //System.out.println(output[j][i]);
            }
        }
        return output;
    }

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
    @Override
    public double[] calculateEnergy(double[] input, int window) {
        System.out.println(input.length + " is input length in energy");
        double[] result = new double[2 * input.length / window - 3];
        int counter = 0;

        for (int i = 0; i < input.length; i += window / 2) {
            double temp = 0;
            for (int j = i; j < i + window; j++) {
                if (j + window > input.length) {
                    break;
                }
                temp += Math.pow(input[j] * 10, 2);
            }

            if (counter >= result.length) {
                break;
            }

            result[counter] = (temp / window);

            counter++;
        }
//        for (int i=0; i<result.length;i++)
//            System.out.println(result[i]+" [i]="+i+" "+ result.length);
        return result;
    }

    @Override
    public double[] scaleToShort(double[] data, double min, double max) {
        double[] output = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            output[i] = (s * (data[i] - min) / (max - min) - s / 2);
        }
        return output;
    }

    @Override
    public double scaleValue(double value, double min, double max) {
        return (s * (value - min) / (max - min) - s / 2);
    }

    @Override
    public double calculateMeanEnergy(double[] data) {
        double mean=0;
        double temp=0;
        for (int i=0; i<data.length;i++){
            temp+=Math.pow(data[i], 2);
        }
        mean=Math.pow(temp/data.length, 0.5);
        return mean;
    }
}
