/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
public class LogarithmicScaleTest {

    public LogarithmicScaleTest() {
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
    public void tearDown() throws InterruptedException {
        while (true) {
            Thread.sleep(1000);
        }
    }

    @Test
    public void drawLogScaleTest() {
        int specHeigth = 700;
        float limit = 4096;
        int frameWidth = 256;
        BufferedImage scaleForFrame = new BufferedImage(100, specHeigth, BufferedImage.TYPE_INT_RGB);
        Graphics g = scaleForFrame.getGraphics();
        g.drawLine(5, 0, 5, specHeigth);
        Integer[] yAxis = new Integer[specHeigth / 8];
        for (int i = 0; i < yAxis.length; i++) {
            yAxis[i] = (int) (Math.pow(2,i) * (limit / 1024));
//            yAxis[i] = (int) (Math.pow(2, i) );

        }
        int j = 0;
       for (int i = specHeigth; i > 0; i -= specHeigth / 10) {
            g.drawString(yAxis[j].toString(), 7, i - 2);
            g.drawLine(0, i, 40, i);
            j++;
        }
        JFrame frame = new JFrame("LogScale");

//2. Optional: What happens when the frame closes?
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//3. Create components and put them in the frame.
//...create emptyLabel...
        JLabel label = new JLabel("olol");

        label.setIcon(new ImageIcon(scaleForFrame));
        frame.getContentPane().add(label, BorderLayout.CENTER);

//4. Size the frame.
        frame.pack();

//5. Show it.
        frame.setVisible(true);
    }
}
