/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.List;
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
public class ListsCase {

    public ListsCase() {
    }

    @Test
    public void testArrayList() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i + " num");
        }
        System.out.println("Size is "+list.size());
        int remove=5;
        System.out.println(String.format("Element %d is to be removed.", remove));
        list.remove(remove);
        System.out.println(String.format("Now element number %d is %s", remove,list.get(remove)));
        System.out.println(list.add("num 10")+""+list.add("num 11"));
        System.out.println("Size is "+list.size());

    }
}
