/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import ru.ivan.model.IMathOperations;
import ru.ivan.model.MathOperations;
import ru.ivan.model.SpectrogramPanel;

/**
 *
 * @author float
 */
public class TestMathOperations {

    public TestMathOperations() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testEnergyCalculation() {
        IMathOperations mo = new MathOperations();
        double[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10};
        double[] newArr = mo.calculateEnergy(arr, 4);
        for (int i = 0; i < newArr.length; i++) {
            System.out.println(newArr[i]);
        }
    }

    @Test
    public void testSpectrogram() throws FileNotFoundException, IOException {
//        double [][] arr={{1,1,1,1},{1,2,1,4},{1,4,1,7},{1,2,3,400}};
        double[][] arr = {{100, 100, 100, 100}, {200, 200, 200, 200}, {300, 300, 300, 300}, {400, 400, 400, 400}};

        SpectrogramPanel p = new SpectrogramPanel(arr);
        File file = new File("spectrogram.jpg");
        BufferedImage bi = p.getSpectrogram();
        ImageIO.write((RenderedImage) bi, "jpg", file);
        System.out.println(String.format("minVal is %f, maxVal is %f", p.getMinVal(), p.getMaxVal()));

    }

    @Test
    public void testSpectrogramWithThreshold() throws IOException {
        double[][] arr = {{100, 100, 50, 100}, {200, 200, 200, 200}, {300, 300, 300, 300}, {100, 400, 100, 400}};
        SpectrogramPanel p = new SpectrogramPanel(arr);
        File file = new File("spectrogram.jpg");
        BufferedImage bi = p.getSpectrogram();
        ImageIO.write((RenderedImage) bi, "jpg", file);
        System.out.println(String.format("minVal is %f, maxVal is %f", p.getMinVal(), p.getMaxVal()));
        
        double maxVal = 200;
        SpectrogramPanel newP=new SpectrogramPanel(p,maxVal);
        ImageIO.write(newP.getSpectrogram(), "jpg", new File("spectrogramWithThreshold"));
        System.out.println(String.format("minVal is %f, maxVal is %f", newP.getMinVal(), newP.getMaxVal()));
    }
    
    @Test
    public void testArrayCoping(){
        double [] arrDouble={4,2,3,5,7};
        double [] arrFloat=Arrays.copyOf(arrDouble, 4);
    }
}
