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

/**
 *
 * @author float
 */
public class TestANPFile {
        IDataLoader loader = new DataLoader();

    public TestANPFile() {
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
     public void testGetDiscretizationFromAnp() {
         String path="/home/float/Documents/ZETLab/signals/110n1_9880528_093854/sig0001.ana";
         loader.loadData(path);
         System.out.println(loader.getFullData().length);
         loader.getDiscretizationFromFileAnp(path);
     }
}
