/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import jwave.Transform;
import jwave.transforms.FastWaveletTransform;
import jwave.transforms.wavelets.Wavelet;
import jwave.transforms.wavelets.daubechies.Daubechies10;
import jwave.transforms.wavelets.haar.Haar1;
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
public class WaveletTest {
     @Test
     public void waveletTest() {
             Transform t = new Transform( new FastWaveletTransform(new Haar1()) );
             double[ ] arrTime = { 1., 7., 1., 1., 8., 1., 1., 1. ,1., 7., 1., 1., 8., 1., 1., 1.};
             double [] res=t.forward(arrTime);
             for (int i =0; i<res.length;i++){
                 System.out.println(res[i]);
             }
     }
}
