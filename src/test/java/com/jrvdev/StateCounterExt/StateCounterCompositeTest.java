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
    
    @Test
    public void loadsStates() {
        
        String initialStateName = "\"4-5-8 E Sq\"";
        String stateCounterInitialization = "{\"states\" : [ {  \"id\" : \"4-5-8\", \"name\" : " + initialStateName + ", \"imagePath\" : \"ru/ru458S\", \"transitions\" : [] }, {  \"id\" : \"4-4-7\", \"name\" : \"4-4-7 1 Sq\", \"imagePath\" : \"ru/ru447S\", \"transitions\" : [] }, {  \"id\" : \"4-2-6\", \"name\" : \"4-2-6 C Sq\", \"imagePath\" : \"ru/ru426S\", \"transitions\" : [] } ], \"keyToCommandMap\" : [] }";

        IStateCounterParserFactory mockParserFactory = (IStateCounterParserFactory) mock(IStateCounterParserFactory.class);
        IStateCounterParser mockParser = (IStateCounterParser) mock(IStateCounterParser.class);
        IStateMachine<String,String,IStateCounterState> mockStateMachine = (IStateMachine<String,String,IStateCounterState>) mock(IStateMachine.class);
        IStateCounterState mockStateCounterState = (IStateCounterState) mock(IStateCounterState.class);
        
        when( mockParserFactory.createNew(stateCounterInitialization) ).thenReturn(mockParser);
        when( mockParser.getStateMachine()).thenReturn(mockStateMachine);
        when( mockStateMachine.getCurrentState()).thenReturn(mockStateCounterState);
        when( mockStateCounterState.getName()).thenReturn(initialStateName);
        
        StateCounterComposite c = new StateCounterComposite( mockParserFactory );
        c.mySetType(stateCounterInitialization);
        
        assertEquals( initialStateName, c.getName() );

    }


}
