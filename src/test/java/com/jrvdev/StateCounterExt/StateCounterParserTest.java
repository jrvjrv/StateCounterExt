package com.jrvdev.StateCounterExt;

import java.awt.Rectangle;
import java.lang.NullPointerException;
import java.util.Dictionary;
import java.util.Map;

import javax.swing.KeyStroke;

import static org.junit.Assert.*;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

import com.jrvdev.StateDataStructure.IStateMachine;
import com.jrvdev.StateDataStructure.StateMachine;

import VASSAL.tools.imageop.ScaledImagePainter;


public class StateCounterParserTest {
    @Test(expected = NullPointerException.class)
    public void throwOnNullStateFactory() {
        IStateCounterStateFactory mockStateFactory = (IStateCounterStateFactory) mock( IStateCounterStateFactory.class);
        IStateMachineFactory mockMachineFactory = (IStateMachineFactory) mock(IStateMachineFactory.class);
        StateCounterParser p = new StateCounterParser( "{}", null, mockMachineFactory );
    }

    @Test(expected = NullPointerException.class)
    public void throwOnNullMachineFactory() {
        IStateCounterStateFactory mockStateFactory = (IStateCounterStateFactory) mock( IStateCounterStateFactory.class);
        IStateMachineFactory mockMachineFactory = (IStateMachineFactory) mock(IStateMachineFactory.class);
        StateCounterParser p = new StateCounterParser( "{}", mockStateFactory, null );
    }
    
    @Test
    public void worksWithNullInitialization() {
        IStateCounterStateFactory mockStateFactory = (IStateCounterStateFactory) mock( IStateCounterStateFactory.class);
        IStateMachineFactory mockMachineFactory = (IStateMachineFactory) mock(IStateMachineFactory.class);

        StateCounterParser p = new StateCounterParser( null, mockStateFactory, new StateMachineFactory() );
        
        IStateMachine<String, String, IStateCounterState> stateMachine = p.getStateMachine();
        
        assertNull( stateMachine.getCurrentState());
    }
    
    @Test
    public void worksWithEmptyStringInitialization() {
        IStateCounterStateFactory mockStateFactory = (IStateCounterStateFactory) mock( IStateCounterStateFactory.class);
        IStateMachineFactory mockMachineFactory = (IStateMachineFactory) mock(IStateMachineFactory.class);

        StateCounterParser p = new StateCounterParser( "", mockStateFactory, new StateMachineFactory() );
        
        IStateMachine<String, String, IStateCounterState> stateMachine = p.getStateMachine();
        
        assertNull( stateMachine.getCurrentState());
    }
    
    @Test
    public void emptyMachine() {
        IStateCounterStateFactory mockStateFactory = (IStateCounterStateFactory) mock( IStateCounterStateFactory.class );
        String anEmptyMachine = "{\"states\" : [], \"keyToCommandMap\" : []}";
        
        StateCounterParser p = new StateCounterParser( anEmptyMachine, mockStateFactory, new StateMachineFactory() );
        
        IStateMachine<String, String, IStateCounterState> stateMachine = p.getStateMachine();
        
        assertNull( stateMachine.getCurrentState());
    }
    
    @Test
    public void oneState() {
        IStateCounterStateFactory mockStateFactory = (IStateCounterStateFactory) mock( IStateCounterStateFactory.class );
        IStateMachineFactory mockMachineFactory = (IStateMachineFactory) mock(IStateMachineFactory.class);
        IStateMachine<String, String, IStateCounterState> mockStateMachine = (IStateMachine<String, String, IStateCounterState>) mock( IStateMachine.class );
        
        when(mockMachineFactory.createNew()).thenReturn(mockStateMachine);

        String oneState = "{\"states\" : [ {  \"id\" : \"4-5-8\", \"name\" : \"4-5-8 E Sq\", \"imagePath\" : \"ru/ru458S\", \"transitions\" : [] } ], \"keyToCommandMap\" : [] }";
        ArgumentCaptor<StateCounterState> argument = ArgumentCaptor.forClass(StateCounterState.class);

        StateCounterParser p = new StateCounterParser( oneState, mockStateFactory, mockMachineFactory );
        
        IStateMachine<String, String, IStateCounterState> stateMachine = p.getStateMachine();
        

        verify( mockMachineFactory, times(1)).createNew();
        verify( mockStateMachine, times(1)).addState(argument.capture());
        verify( mockStateMachine, never()).addTransition(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
    }
    
    @Test
    public void multipleStates() {
        IStateCounterStateFactory mockStateFactory = (IStateCounterStateFactory) mock( IStateCounterStateFactory.class );
        IStateMachineFactory mockMachineFactory = (IStateMachineFactory) mock(IStateMachineFactory.class);
        IStateMachine<String, String, IStateCounterState> mockStateMachine = (IStateMachine<String, String, IStateCounterState>) mock( IStateMachine.class );
        
        when(mockMachineFactory.createNew()).thenReturn(mockStateMachine);
        
        String stateCounterInitialization = "{\"states\" : [ {  \"id\" : \"4-5-8\", \"name\" : \"4-5-8 E Sq\", \"imagePath\" : \"ru/ru458S\", \"transitions\" : [] }, {  \"id\" : \"4-4-7\", \"name\" : \"4-4-7 1 Sq\", \"imagePath\" : \"ru/ru447S\", \"transitions\" : [] }, {  \"id\" : \"4-2-6\", \"name\" : \"4-2-6 C Sq\", \"imagePath\" : \"ru/ru426S\", \"transitions\" : [] } ], \"keyToCommandMap\" : [] }";
        ArgumentCaptor<StateCounterState> argument = ArgumentCaptor.forClass(StateCounterState.class);

        StateCounterParser p = new StateCounterParser( stateCounterInitialization, mockStateFactory, mockMachineFactory );
        
        IStateMachine<String, String, IStateCounterState> stateMachine = p.getStateMachine();

        verify( mockMachineFactory, times(1)).createNew();
        verify( mockStateMachine, times(3)).addState(argument.capture());
        verify( mockStateMachine, never()).addTransition(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
    }
    
    private class TestStateCounterState implements IStateCounterState {
        
        private String _id, _name, _imagePath;
        
        public TestStateCounterState( String id, String name, String imagePath ) {
            _id = id;
            _name = name;
            _imagePath = imagePath;
        }

        public ScaledImagePainter getScaledImagePainter() {
            // TODO Auto-generated method stub
            return null;
        }

        public String getStateId() {
            return _id;
        }

        public Rectangle getSize() {
            // TODO Auto-generated method stub
            return null;
        }

        public String getName() {
            return _name;
        }
    }
    
    private class TestStateCounterStateFactory implements IStateCounterStateFactory {

        public IStateCounterState createNew(String id, String name, String imagePath) {
            return new TestStateCounterState( id, name, imagePath );
        }
        
    }
    
    @Test
    public void multipleStatesWithTransitions() {
        IStateCounterStateFactory mockStateFactory = new TestStateCounterStateFactory();
        IStateMachineFactory mockMachineFactory = (IStateMachineFactory) mock(IStateMachineFactory.class);
        IStateMachine<String, String, IStateCounterState> stateMachine = new StateMachine<String, String, IStateCounterState>();
        
        when(mockMachineFactory.createNew()).thenReturn(stateMachine);

        IStateCounterStateFactory testFactory = new TestStateCounterStateFactory();

        String stateCounterInitialization = "{\"states\" : [ {  \"id\" : \"4-5-8\", \"name\" : \"4-5-8 E Sq\", \"imagePath\" : \"ru/ru458S\", \"transitions\" : [ { \"command\" : \"ELR\", \"toState\" : \"4-4-7\" } ] }, {  \"id\" : \"4-4-7\", \"name\" : \"4-4-7 1 Sq\", \"imagePath\" : \"ru/ru447S\", \"transitions\" : [ { \"command\" : \"ELR\", \"toState\" : \"4-2-6\" }, { \"command\" : \"BattleHarden\", \"toState\" : \"4-5-8\" } ] }, {  \"id\" : \"4-2-6\", \"name\" : \"4-2-6 C Sq\", \"imagePath\" : \"ru/ru426S\", \"transitions\" : [] } ], \"keyToCommandMap\" : [] }";

        StateCounterParser p = new StateCounterParser( stateCounterInitialization, mockStateFactory, mockMachineFactory );
        
        IStateMachine<String, String, IStateCounterState> returnedStateMachine = p.getStateMachine(); 

        assertEquals( "4-5-8", returnedStateMachine.getCurrentState().getStateId() );
        stateMachine.transition("ELR");
        assertEquals( "4-4-7", returnedStateMachine.getCurrentState().getStateId());
        stateMachine.transition("BattleHarden");
        assertEquals( "4-5-8", returnedStateMachine.getCurrentState().getStateId());
        stateMachine.transition("ELR");
        assertEquals( "4-4-7", returnedStateMachine.getCurrentState().getStateId());
        stateMachine.transition("ELR");
        assertEquals( "4-2-6", returnedStateMachine.getCurrentState().getStateId());
    }
    
    @Test
    public void testEmptyKeyCommandTranslationInitialization() {
//        IStateCounterStateFactory testFactory = new TestStateCounterStateFactory();
//        String stateCounterInitialization = "{\"states\" : [ {  \"id\" : \"4-5-8\", \"name\" : \"4-5-8 E Sq\", \"imagePath\" : \"ru/ru458S\", \"transitions\" : [ { \"command\" : \"ELR\", \"toState\" : \"4-4-7\" } ] }, {  \"id\" : \"4-4-7\", \"name\" : \"4-4-7 1 Sq\", \"imagePath\" : \"ru/ru447S\", \"transitions\" : [ { \"command\" : \"ELR\", \"toState\" : \"4-2-6\" }, { \"command\" : \"BattleHarden\", \"toState\" : \"4-5-8\" } ] }, {  \"id\" : \"4-2-6\", \"name\" : \"4-2-6 C Sq\", \"imagePath\" : \"ru/ru426S\", \"transitions\" : [] } ], \"keyToCommandMap\" : [] }";
//
//        StateCounterParser p = new StateCounterParser( stateCounterInitialization, testFactory );
//        
//        Map<KeyStroke, String> map = p.getKeyCommandTranslation();
//        
//        assertTrue( map.isEmpty());
        
    }

    @Test
    public void testKeyCommandTranslationInitialization() {
//        IStateCounterStateFactory testFactory = new TestStateCounterStateFactory();
//        String stateCounterInitialization = "{\"states\" : [ {  \"id\" : \"4-5-8\", \"name\" : \"4-5-8 E Sq\", \"imagePath\" : \"ru/ru458S\", \"transitions\" : [ { \"command\" : \"ELR\", \"toState\" : \"4-4-7\" } ] }, {  \"id\" : \"4-4-7\", \"name\" : \"4-4-7 1 Sq\", \"imagePath\" : \"ru/ru447S\", \"transitions\" : [ { \"command\" : \"ELR\", \"toState\" : \"4-2-6\" }, { \"command\" : \"BattleHarden\", \"toState\" : \"4-5-8\" } ] }, {  \"id\" : \"4-2-6\", \"name\" : \"4-2-6 C Sq\", \"imagePath\" : \"ru/ru426S\", \"transitions\" : [] } ], \"keyToCommandMap\" : [ { \"keyStroke\" : \"ctrl pressed E\", \"command\" : \"ELR\" }, { \"keyStroke\" : \"ctrl pressed Q\", \"command\" : \"BattleHarden\" }] }";
//
//        StateCounterParser p = new StateCounterParser( stateCounterInitialization, testFactory );
//        
//        Map<KeyStroke, String> map = p.getKeyCommandTranslation();
//        
//        //System.out.println("KeyStroke.toString() = " + KeyStroke.getKeyStroke("ctrl pressed E").toString());
//        // map returns a null on a key it can't find
//        //System.out.println(map.get(KeyStroke.getKeyStroke("ctrl pressed E")));
//        
//        assertFalse( map.isEmpty());
//        
//        assertEquals( "ELR", map.get(KeyStroke.getKeyStroke("ctrl pressed E")) );
//        assertEquals( "BattleHarden", map.get(KeyStroke.getKeyStroke("ctrl pressed Q")) );
        
    }

    // passed in a bad (not JSON-parsable) string?

}
