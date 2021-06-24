/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acumengroup.greekmain.util.operation;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 * Created by Arcadia
 */
@Deprecated
public class GreekHashtable extends Hashtable {

    protected Vector keysOrder;

    public GreekHashtable() {
        super();
        keysOrder = new Vector();
    }

    public GreekHashtable(int initialCapacity) {
        super(initialCapacity);
        keysOrder = new Vector(initialCapacity);
    }

    public void clear() {
        super.clear();
        keysOrder.clear();
    }

    public Object put(Object key, Object value) {
        if (!keysOrder.contains(key)) {
            keysOrder.addElement(key);
        }
        return super.put(key, value);
    }

    public Enumeration keys() {
        return keysOrder.elements();
    }

    public Object remove(Object key) {
        if (keysOrder.indexOf(key) != -1) {
            keysOrder.removeElement(key);
        }
        return super.remove(key);
    }

    public Object getElementAtIndex(int index) {
        return super.get(keysOrder.elementAt(index));
    }
}