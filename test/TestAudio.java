/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import ru.ivan.model.DataLoader;
import ru.ivan.model.IDataLoader;
import ru.ivan.model.StdAudio;

/**
 *
 * @author float
 */
public class TestAudio {

    StdAudio audio = new StdAudio();
    IDataLoader loader = new DataLoader();

    public TestAudio() {
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
    public void createWav() {
        String pathDAT = "/home/float/Documents/test/110n1_9880528_092628.DAT";
        String pathANA = "/home/float/Documents/test/sig0001.ana";
        loader.loadData(pathANA);
        Integer disk=loader.getDiscretizationFromFileAnp(pathANA);
disk=25000;
        System.out.println(disk);
        long t1=System.currentTimeMillis();
        audio.save(pathANA + ".wav", loader.getFullData(), disk);
        
        System.out.println((System.currentTimeMillis()-t1));
    }

    @Test
    public void createTestWav() {
        int discret = 50000;
        double[] arr = new double[500000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 10*Math.sin(20000*2 * Math.PI * i/discret);
            //+10*Math.sin(100*2 * Math.PI * i/discret);
        }
        String path = "/home/float/Documents/test/test";
        audio.save(path+".wav", arr, 2*discret);
    }
}