/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.spbspu.model;

import javax.sound.sampled.AudioInputStream;

/**
 *
 * @author float
 */
public interface IAudio {

    
    /***
     * Создание аудиозаписи файла анализатора в папке с бинарной записью
     * @param filename
     * @param input
     * @param sampleRate 
     */
    void save(String filename, double[] input, int sampleRate);
   
    /***
     * Указание аудиозаписи
     * @param path
     * @return 
     */
    AudioInputStream getAudioStream(String path);
    
     /***
     * Остановка и закрытие потока аудиоданных
     */
    void close();
}
