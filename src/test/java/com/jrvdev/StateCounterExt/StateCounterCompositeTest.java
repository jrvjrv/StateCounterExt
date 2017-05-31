package com.jrvdev.StateCounterExt;

import static org.junit.Assert.*;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.jrvdev.StateDataStructure.IStateMachine;

import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.List;

import javax.swing.KeyStroke;


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
        c.initialize(stateCounterInitialization);
        
        assertEquals( initialStateName, c.getName() );

    }
    
    @Test
    public void multipleStatesWithTransitions() {
        String stateCounterInitialization = "{\"states\" : [ {  \"id\" : \"4-5-8\", \"name\" : \"4-5-8 E Sq\", \"imagePath\" : \"ru/ru458S\", \"transitions\" : [ { \"command\" : \"ELR\", \"toState\" : \"4-4-7\" } ] }, {  \"id\" : \"4-4-7\", \"name\" : \"4-4-7 1 Sq\", \"imagePath\" : \"ru/ru447S\", \"transitions\" : [ { \"command\" : \"ELR\", \"toState\" : \"4-2-6\" }, { \"command\" : \"BattleHarden\", \"toState\" : \"4-5-8\" } ] }, {  \"id\" : \"4-2-6\", \"name\" : \"4-2-6 C Sq\", \"imagePath\" : \"ru/ru426S\", \"transitions\" : [] } ], \"keyToCommandMap\" : [ { \"keyStroke\" : \"ctrl pressed E\", \"command\" : \"ELR\" }, { \"keyStroke\" : \"ctrl pressed Q\", \"command\" : \"BattleHarden\" } ] }";

        IStateCounterParserFactory mockParserFactory = (IStateCounterParserFactory) mock(IStateCounterParserFactory.class);
        IStateCounterParser mockParser = (IStateCounterParser) mock(IStateCounterParser.class);
        IStateMachine<String,String,IStateCounterState> mockStateMachine = (IStateMachine<String,String,IStateCounterState>) mock(IStateMachine.class);
        
        when( mockParserFactory.createNew(stateCounterInitialization) ).thenReturn(mockParser);
        when( mockParser.getStateMachine()).thenReturn(mockStateMachine);
        HashMap<KeyStroke, String > keyToCommandMap = new HashMap<KeyStroke, String>();
        keyToCommandMap.put(KeyStroke.getKeyStroke("ctrl pressed E"), "ELR");
        keyToCommandMap.put(KeyStroke.getKeyStroke("ctrl pressed Q"), "BattleHarden");
        
        when( mockParser.getKeyCommandTranslation()).thenReturn(keyToCommandMap );
        
        StateCounterComposite c = new StateCounterComposite( mockParserFactory );
        c.initialize(stateCounterInitialization);
        
        c.myKeyEvent(KeyStroke.getKeyStroke("ctrl E"));
        c.myKeyEvent(KeyStroke.getKeyStroke("ctrl Q"));
        c.myKeyEvent(KeyStroke.getKeyStroke("ctrl E"));
        c.myKeyEvent(KeyStroke.getKeyStroke("ctrl E"));

        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
        verify( mockStateMachine, times(4)).transition(argument.capture());
        List<String> arguments = argument.getAllValues();
        assertEquals("ELR", arguments.get(0));
        assertEquals("BattleHarden", arguments.get(1));
        assertEquals("ELR", arguments.get(2));
        assertEquals("ELR", arguments.get(3));
        
    }
    
}
