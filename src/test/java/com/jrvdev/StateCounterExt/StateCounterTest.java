package com.jrvdev.StateCounterExt;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import javax.swing.KeyStroke;


public class StateCounterTest {
    @Test
    public void parameterless_constructor_still_has_name() {
        StateCounter c  = new StateCounter();
        
        assertNotNull(c);
        assertEquals(null, c.getName());
    }

}
