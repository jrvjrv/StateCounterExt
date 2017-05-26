package com.jrvdev.StateCounterExt;

import static org.junit.Assert.*;
import org.junit.Test;

import com.jrvdev.StateDataStructure.IStateMachine;


public class StateMachineFactoryTest {
    
    @Test
    public void testReturnsValue() {
        StateMachineFactory f = new StateMachineFactory();
        IStateMachine<String, String, IStateCounterState> m = f.createNew();
        
        assertNotNull( m );
    }

}
