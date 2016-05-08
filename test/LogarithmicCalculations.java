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

/**
 *
 * @author float
 */
public class LogarithmicCalculations {
    
    public LogarithmicCalculations() {
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
     public void hello() {
     int limit =2500;
     int power=(int)(Math.log(limit)/Math.log(2));
     System.out.println(power);
     System.out.println(Math.pow(2,power));
     }
}
