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
public interface IDataLoader {


//    void loadData(String path, int frameSize, long position);


    double[] getFullFileInArray();
 


    /**
     * @return the fileType
     */
    FileType getFileType();

    double getMeanValue();

    int getDiscretizationFromFileAnp(String path);
void loadData(String path);

    /**
     * @return the array
     */
    double[] getFullData();

    /**
     * @return the max
     */
    double getMax();

    /**
     * @return the min
     */
    double getMin();

    double[] formatDataForAudio(double[] data);

    /**
     * @return the maxEnergy
     */
    double getMaxEnergy();

    /**
     * @return the minEnergy
     */
    double getMinEnergy();

    /**
     * @param maxEnergy the maxEnergy to set
     */
    void setMaxEnergy(double maxEnergy);

    /**
     * @param minEnergy the minEnergy to set
     */
    void setMinEnergy(double minEnergy);

    public double getMeanEnergy();

    /**
     * @param meanEnergy the meanEnergy to set
     */
    void setMeanEnergy(double meanEnergy);

}
