/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class CollectionsTest {
    
    public CollectionsTest() {
    }
    
    
     @Test
     public void testHashMapsKey() {
         Map<DummyObject,String> map=new HashMap<>();
         DummyObject key1=new DummyObject(1,4);
         DummyObject key2=new DummyObject(1,4);
         DummyObject key3=new DummyObject(3,2);
         System.out.println(map.put(key1,"Key1"));
         key1.a=4;
         System.out.println(map.put(key2, "Ke2"));
         System.out.println(key1.hashCode()+" "+key2.hashCode()+key3.hashCode());
                  System.out.println(map.put(key3, "Ke3"));
                  System.out.println(map);
                                    System.out.println(map.size());
                                    
         System.out.println(key1+" "+key3+" "+key1.compareTo(key3));


     }
     
     @Test
     public void typeErasureTest(){
         List<String> list=new ArrayList<>();
         list.add("New String");
         list.add("Some string");
//         list=(List<Object>)list;
//         list.add(new DummyObject(1, 2));
     }
}
