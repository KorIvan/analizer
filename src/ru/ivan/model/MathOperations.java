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
            toFFT[i] = (signalIn[i] * 0.5 * (1.0 - Math.cos(2.0 * Math.PI * i / (size - 1))));

        }
        return toFFT;
    }

    @Override
    public double[] HammingWindow(double[] signalIn, int size) {
        double[] toFFT = new double[size];
        for (int i = 0; i < size; i++) {
            toFFT[i] = (signalIn[i] * (0.53836 - 0.46164 * Math.cos(2.0 * Math.PI * i / (size - 1))));
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
        double mean = 0;
        double temp = 0;
        for (int i = 0; i < data.length; i++) {
            temp += Math.pow(data[i], 2);
        }
        mean = Math.pow(temp / data.length, 0.5);
        return mean;
    }
 public double[] convolve(double[] d1, double[] d2) {
        double[] result = new double[d1.length + d2.length - 1];
        for (int i = 0; i < result.length; i++) {
            double temp = 0;
            for (int j = 0; j < d1.length; ++j) {
                int nextIndex = i - j;
                if (nextIndex < 0) {
                    break;
                }
                if (nextIndex >= d2.length) {
                    continue;
                }
                temp += d1[j] * d2[nextIndex];
            }
            result[i] = temp;
        }
        return result;
    }
 
    public double[] lpf(double fCut, double dt, int m) {
        double[] d = {0.35577019, 0.2436983, 0.07211497, 0.00630165};
        double[] w = new double[m + 1];
        w[0] = 2 * fCut * dt;
        double fact = Math.PI * w[0];
        for (int i = 1; i <= m; i++) {
            w[i] = Math.sin(fact * i) / (Math.PI * i);
        }
        w[m] /= 2;
        double sum;
        double fact1;
        double sumg = w[0];
        for (int i = 1; i <= m; i++) {
            sum = d[0];
            fact1 = Math.PI * i / m;
            for (int k = 1; k <= 3; k++) {
                sum += 2 * d[k] * Math.cos(Math.PI * i * k / m);
            }
            w[i] *= sum;
            sumg += 2 * w[i];
        }
        for (int i = 0; i <= m; i++) {
            w[i] /= sumg;
        }
        //берем все элементы кроме первого в обрwатном порядке
        double[] wR = new double[w.length];
        for (int i = 0; i < w.length; i++) {
            wR[i] = w[w.length - 1 - i];
        }
        double[] h = new double[2 * w.length - 1];
        for (int i = 0; i < w.length; i++) {
            h[i] = wR[i];
            h[w.length + i - 1] = w[i];
        }
        return h;
    }
}
