package com.jrvdev.StateCounterExt;

import static org.junit.Assert.*;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.*;



public class StateCounterParserFactoryTest {
    
    @Test(expected=NullPointerException.class)
    public void testThrowsOnNullStateFactory() {

        IStateCounterStateFactory mockStateFactory = (IStateCounterStateFactory) mock(IStateCounterStateFactory.class);
        IStateMachineFactory mockMachineFactory = (IStateMachineFactory) mock(IStateMachineFactory.class);
        StateCounterParserFactory f = new StateCounterParserFactory(null, mockMachineFactory);
    }

    @Test(expected=NullPointerException.class)
    public void testThrowsOnNullMachineFactory() {

        IStateCounterStateFactory mockStateFactory = (IStateCounterStateFactory) mock(IStateCounterStateFactory.class);
        IStateMachineFactory mockMachineFactory = (IStateMachineFactory) mock(IStateMachineFactory.class);
        StateCounterParserFactory f = new StateCounterParserFactory(mockStateFactory, null);
    }

    @Test
    public void createsOk() {

        IStateCounterStateFactory mockStateFactory = (IStateCounterStateFactory) mock(IStateCounterStateFactory.class);
        IStateMachineFactory mockMachineFactory = (IStateMachineFactory) mock(IStateMachineFactory.class);
        StateCounterParserFactory f = new StateCounterParserFactory(mockStateFactory, mockMachineFactory);
        assertNotNull( f );
    }

    @Test
    public void factoryViable() {

        IStateCounterStateFactory mockStateFactory = (IStateCounterStateFactory) mock(IStateCounterStateFactory.class);
        IStateMachineFactory mockMachineFactory = (IStateMachineFactory) mock(IStateMachineFactory.class);
        StateCounterParserFactory f = new StateCounterParserFactory(mockStateFactory, mockMachineFactory);
        assertNotNull( f.createNew("") );
    }
}
