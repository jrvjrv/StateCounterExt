package com.jrvdev.StateCounterExt;

import static org.junit.Assert.*;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.jrvdev.StateDataStructure.IStateMachine;

import static org.mockito.Mockito.*;


public class StateCounterCompositeTest {
    @Test 
    public void constructsOk() {
        IStateCounterParserFactory mockParserFactory = (IStateCounterParserFactory) mock(IStateCounterParserFactory.class);
        StateCounterComposite c = new StateCounterComposite( mockParserFactory );
        
        assertNotNull( c );
    }
    
    @Test(expected = NullPointerException.class)
    public void throwOnNullParserFactory() {
        StateCounterComposite c = new StateCounterComposite( null );
    }

    @Test(expected = NullPointerException.class)
    public void throwOnNullStateFactory() {
        IStateCounterParserFactory mockParserFactory = (IStateCounterParserFactory) mock(IStateCounterParserFactory.class);
        IStateMachine< String, String, IStateCounterState > mockStateMachine = (IStateMachine< String, String, IStateCounterState >) mock(IStateMachine.class);
        StateCounterComposite c = new StateCounterComposite( mockParserFactory, null, mockStateMachine );
    }

    @Test(expected = NullPointerException.class)
    public void throwOnNullStateMachine() {
        IStateCounterParserFactory mockParserFactory = (IStateCounterParserFactory) mock(IStateCounterParserFactory.class);
        IStateCounterStateFactory mockStateFactory = (IStateCounterStateFactory) mock(IStateCounterStateFactory.class);
        StateCounterComposite c = new StateCounterComposite( mockParserFactory, mockStateFactory, null );
    }

}
