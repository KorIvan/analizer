/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ivan.model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;
import java.nio.BufferUnderflowException;

/**
 *
 * @author Иван Корнелюк
 */
public class DataLoader implements IDataLoader {

    public DataLoader() {
    }

        private static final int DAT_HEADER=800;
   private double[] array;
    private FileType fileType;
    private double min;
    private double max;
    private double meanValue;
    /**
     * Буфер вывода данных
     */
    private ByteBuffer readerBuffer;
    /**
     * *
     * Размер буфера readerBuffer
     */
    final private int capacityBR = 4;


    /**
     * Константа для масштабирования значений из файла .ana в диапазон short
     */
    private final int s = 32767 * 2;
    /**
     * Частота дискретизации
     */
    private double rate;
    /**
     * Окно отображаемых данных
     */
    private float[] frame;
    /**
     * Буфер отображаемых данных
     */
    private ByteBuffer bufferFrame;
    /**
     * Размер фрейма
     */
    private int frameSize;
    
    private double minEnergy;
    private double maxEnergy;
    /**
     * Среднее квадратическое энергии
     */
    private double meanEnergy;

    @Override
    public int getDiscretizationFromFileAnp(String path) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException ex) {
//            Logger.getLogger(DataLoader.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Файл не найден");
            return 0;
        }
        int discretization=0;
        StringBuilder sb = new StringBuilder();
        String line=null;
        try {

            try {
                line = br.readLine();
                while (line != null&&!line.contains("FRQ")) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                    line = br.readLine();
                }
                String everything = sb.toString();
            } catch (IOException ex) {
                Logger.getLogger(DataLoader.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(DataLoader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try{
        discretization=Integer.parseInt(line.substring(line.indexOf("Q")+2, line.indexOf(".")));
        System.out.println(line.substring(line.indexOf("Q")+2, line.indexOf(".")));
            System.out.println(line);
        }
        
        catch (NullPointerException e){
            System.out.println("Отсутствует строка с указанием частоты.");
            return 0;
        }
        return discretization;

    }


   
//    @Override
//    public void loadData(String path, int frameSize, long position) {
//        long start = System.currentTimeMillis();
//        if (path.toLowerCase().endsWith(".ana")) {
//            loadAnaData(path);
//            fileType = "ana";
//        }
//        if (path.toLowerCase().endsWith(".dat")) {
//            loadDatData(path);
//            fileType = "dat";
//        }
//        long end = System.currentTimeMillis();
//        System.out.println("Время выполнения загрузки файла " + (end - start));
//        start = System.currentTimeMillis();
//        if (path.toLowerCase().endsWith(".ana")) {
//            loadIntoFrame(frameSize, position);
//        }
//        if (path.toLowerCase().endsWith(".dat")) {
//            loadIntoFrameDat(frameSize, position);
//        }
//        end = System.currentTimeMillis();
//        System.out.println("Время выполнения данных в фрейм " + (end - start));
//    }





    @Override
    public double[] getFullFileInArray() {
        return array;
    }
    /**
     * Всё содержимое записи
     */
 

    public double[] loadFullFile() {
        return null;

    }

    public void loadData(String path) {
        FileInputStream file = null;
        try {
            file = new FileInputStream(path);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DataLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        switch(path.substring(path.lastIndexOf(".")+1).toUpperCase()){
                case "ANA": fileType=FileType.ANA;
                    break;
                case "DAT":fileType=FileType.DAT;
                    break;
                }
        FileChannel channel = file.getChannel();
        ByteBuffer readerBuffer = null;
        switch (fileType) {
            case ANA:
                readerBuffer = ByteBuffer.allocate(4);
                break;
            case DAT:
                readerBuffer = ByteBuffer.allocate(2);
                break;
        }
        readerBuffer.order(ByteOrder.LITTLE_ENDIAN);
        try {
            if (channel.size() == 0) {
                System.out.println("Пустой файл");
            }
        } catch (IOException ex) {
            Logger.getLogger(DataLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        float value = 1;
        min = Short.MAX_VALUE;
        max = 0;
        try {
            array = new double[(int) (channel.size() / readerBuffer.capacity())];
        } catch (IOException ex) {
            Logger.getLogger(DataLoader.class.getName()).log(Level.SEVERE, null, ex);
        }

        int i = 0;
        double temp = 0;
        try {
            switch (fileType) {
                case ANA:
                    channel.position(0);
                    break;
                case DAT:
                    channel.position(DAT_HEADER);
                    break;
            }
            while (channel.read(readerBuffer) > 0) {
                readerBuffer.flip();
                while (readerBuffer.hasRemaining()) {
                    switch (fileType) {
                        case ANA:
                            value = readerBuffer.getFloat();
                            break;
                        case DAT:
                            try{
                            value = readerBuffer.getShort();}
                            catch(BufferUnderflowException e){
                                System.out.println("Buffer under flow");
                            }
                            break;
                    }
                    temp += Math.abs(value);
                    array[i] = value;
                    if (value > getMax()) {
                        max = value;
                    } else if (value < getMin()) {
                        min = value;
                    }
                }
                i++;
                readerBuffer.clear();
            }
            meanValue = temp / array.length;
            channel.position(0);
        } catch (IOException ex) {
            Logger.getLogger(DataLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }




    /**
     * @return the array
     */
    public double[] getFullData() {
        return array;
    }

    /**
     * @return the fileType
     */
    @Override
    public FileType getFileType() {
        return fileType;
    }


    /**
     * @return the min
     */
    public double getMin() {
        return min;
    }

    /**
     * @return the max
     */
    public double getMax() {
        return max;
    }

    /**
     * @return the meanValue
     */
    public double getMeanValue() {
        return meanValue;
    }
     public double[] formatDataForAudio(double[] data){
         double[] formated=new double[data.length];
         for (int i=0; i<formated.length;i++){
             formated[i]=data[i]/max;
//             System.out.println(formated[i]);
         }
         return formated;
     }

    /**
     * @return the minEnergy
     */
    public double getMinEnergy() {
        return minEnergy;
    }

    /**
     * @param minEnergy the minEnergy to set
     */
    public void setMinEnergy(double minEnergy) {
        this.minEnergy = minEnergy;
    }

    /**
     * @return the maxEnergy
     */
    public double getMaxEnergy() {
        return maxEnergy;
    }

    /**
     * @param maxEnergy the maxEnergy to set
     */
    public void setMaxEnergy(double maxEnergy) {
        this.maxEnergy = maxEnergy;
    }

    /**
     * @return the meanEnergy
     */
    public double getMeanEnergy() {
        return meanEnergy;
    }

    /**
     * @param meanEnergy the meanEnergy to set
     */
    public void setMeanEnergy(double meanEnergy) {
        this.meanEnergy = meanEnergy;
    }

    @Override
    public void setFullData(double[] signal) {
        array=signal;
    }

    
}
