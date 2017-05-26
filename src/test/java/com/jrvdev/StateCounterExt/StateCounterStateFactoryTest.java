package com.jrvdev.StateCounterExt;

import static org.junit.Assert.*;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.*;


public class StateCounterStateFactoryTest {
    
    @Test
    public void testCreation() {
        String id = "a";
        String name = "a's name";
        
        StateCounterStateFactory f = new StateCounterStateFactory();
        
        IStateCounterState s = f.createNew(id, name, "c/d" );
        
        assertEquals(id, s.getStateId());
        assertEquals(name, s.getName());
        
        // the ScaledImagePainter is not so easy to unit test.
    }

}
