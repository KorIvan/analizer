
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author float
 */
public class DummyObject implements Comparable<DummyObject>{

    public DummyObject(int a, double b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public String toString() {
        return "DummyObject{" + "a=" + a + ", b=" + b + '}';
    }
    
    int a;
    double b;

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DummyObject other = (DummyObject) obj;
        if (this.a != other.a) {
            return false;
        }
        if (Double.doubleToLongBits(this.b) != Double.doubleToLongBits(other.b)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(DummyObject that) {
        return Integer.compare(this.a+(int)this.b, that.a+(int)that.b);
    }
    
    
}
