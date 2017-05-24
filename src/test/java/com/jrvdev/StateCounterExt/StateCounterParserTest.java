package com.jrvdev.StateCounterExt;

import java.awt.Rectangle;
import java.lang.NullPointerException;
import java.util.Dictionary;
import java.util.Map;

import javax.swing.KeyStroke;

import static org.junit.Assert.*;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.*;
import com.jrvdev.StateDataStructure.IStateMachine;
import com.jrvdev.StateDataStructure.StateMachine;

import VASSAL.tools.imageop.ScaledImagePainter;


public class StateCounterParserTest {
    @Test(expected = NullPointerException.class)
    public void throwOnNullFactory() {
        StateCounterParser p = new StateCounterParser( "{}", null );
    }
    
    @Test
    public void worksWithNullInitialization() {
        IStateCounterStateFactory mockFactory = (IStateCounterStateFactory) mock( IStateCounterStateFactory.class );
        IStateMachine<String, String, IStateCounterState> mockStateMachine = (IStateMachine<String, String, IStateCounterState>) mock( IStateMachine.class );
        // pass this in for argument; don't need it in this case as we expect the method is not called
        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
        
        StateCounterParser p = new StateCounterParser( null, mockFactory );
        
        p.InitializeStateMachine(mockStateMachine);
        // This is an example for a specific method. the second test makes this first test redundant. I
        // included it anyway as a sample.
        verify( mockStateMachine, never()).setState(argument.capture());
        // in this test case we expect no method calls
        verifyZeroInteractions(mockStateMachine);
    }
    
    @Test
    public void worksWithEmptyStringInitialization() {
        IStateCounterStateFactory mockFactory = (IStateCounterStateFactory) mock( IStateCounterStateFactory.class );
        IStateMachine<String, String, IStateCounterState> mockStateMachine = (IStateMachine<String, String, IStateCounterState>) mock( IStateMachine.class );
        
        StateCounterParser p = new StateCounterParser( "", mockFactory );
        
        p.InitializeStateMachine(mockStateMachine);
        // in this test case we expect no method calls
        verifyZeroInteractions(mockStateMachine);
    }
    
    @Test
    public void emptyMachine() {
        IStateCounterStateFactory mockFactory = (IStateCounterStateFactory) mock( IStateCounterStateFactory.class );
        String anEmptyMachine = "{\"states\" : [], \"keyToCommandMap\" : []}";
        IStateMachine<String, String, IStateCounterState> mockStateMachine = (IStateMachine<String, String, IStateCounterState>) mock( IStateMachine.class );
        
        StateCounterParser p = new StateCounterParser( anEmptyMachine, mockFactory );
        
        p.InitializeStateMachine(mockStateMachine);
        // in this test case we expect no method calls
        verifyZeroInteractions(mockStateMachine);
    }
    
    @Test
    public void oneState() {
        IStateCounterStateFactory mockFactory = (IStateCounterStateFactory) mock( IStateCounterStateFactory.class );
        String oneState = "{\"states\" : [ {  \"id\" : \"4-5-8\", \"name\" : \"4-5-8 E Sq\", \"imagePath\" : \"ru/ru458S\", \"transitions\" : [] } ], \"keyToCommandMap\" : [] }";
        IStateMachine<String, String, IStateCounterState> mockStateMachine = (IStateMachine<String, String, IStateCounterState>) mock( IStateMachine.class );
        ArgumentCaptor<StateCounterState> argument = ArgumentCaptor.forClass(StateCounterState.class);

        StateCounterParser p = new StateCounterParser( oneState, mockFactory );
        
        p.InitializeStateMachine(mockStateMachine);

        verify( mockStateMachine, times(1)).addState(argument.capture());
    }
    
    @Test
    public void multipleStates() {
        IStateCounterStateFactory mockFactory = (IStateCounterStateFactory) mock( IStateCounterStateFactory.class );
        String stateCounterInitialization = "{\"states\" : [ {  \"id\" : \"4-5-8\", \"name\" : \"4-5-8 E Sq\", \"imagePath\" : \"ru/ru458S\", \"transitions\" : [] }, {  \"id\" : \"4-4-7\", \"name\" : \"4-4-7 1 Sq\", \"imagePath\" : \"ru/ru447S\", \"transitions\" : [] }, {  \"id\" : \"4-2-6\", \"name\" : \"4-2-6 C Sq\", \"imagePath\" : \"ru/ru426S\", \"transitions\" : [] } ], \"keyToCommandMap\" : [] }";
        IStateMachine<String, String, IStateCounterState> mockStateMachine = (IStateMachine<String, String, IStateCounterState>) mock( IStateMachine.class );
        ArgumentCaptor<StateCounterState> argument = ArgumentCaptor.forClass(StateCounterState.class);

        StateCounterParser p = new StateCounterParser( stateCounterInitialization, mockFactory );
        
        p.InitializeStateMachine(mockStateMachine);

        verify( mockStateMachine, times(3)).addState(argument.capture());
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
        IStateCounterStateFactory testFactory = new TestStateCounterStateFactory();
        String stateCounterInitialization = "{\"states\" : [ {  \"id\" : \"4-5-8\", \"name\" : \"4-5-8 E Sq\", \"imagePath\" : \"ru/ru458S\", \"transitions\" : [ { \"command\" : \"ELR\", \"toState\" : \"4-4-7\" } ] }, {  \"id\" : \"4-4-7\", \"name\" : \"4-4-7 1 Sq\", \"imagePath\" : \"ru/ru447S\", \"transitions\" : [ { \"command\" : \"ELR\", \"toState\" : \"4-2-6\" }, { \"command\" : \"BattleHarden\", \"toState\" : \"4-5-8\" } ] }, {  \"id\" : \"4-2-6\", \"name\" : \"4-2-6 C Sq\", \"imagePath\" : \"ru/ru426S\", \"transitions\" : [] } ], \"keyToCommandMap\" : [] }";
        IStateMachine<String, String, IStateCounterState> stateMachine = new StateMachine<String, String, IStateCounterState>();

        StateCounterParser p = new StateCounterParser( stateCounterInitialization, testFactory );
        
        p.InitializeStateMachine(stateMachine);

        assertEquals( "4-5-8", stateMachine.getCurrentState().getStateId() );
        stateMachine.transition("ELR");
        assertEquals( "4-4-7", stateMachine.getCurrentState().getStateId());
        stateMachine.transition("BattleHarden");
        assertEquals( "4-5-8", stateMachine.getCurrentState().getStateId());
        stateMachine.transition("ELR");
        assertEquals( "4-4-7", stateMachine.getCurrentState().getStateId());
        stateMachine.transition("ELR");
        assertEquals( "4-2-6", stateMachine.getCurrentState().getStateId());
    }
    
    @Test
    public void testEmptyKeyCommandTranslationInitialization() {
        IStateCounterStateFactory testFactory = new TestStateCounterStateFactory();
        String stateCounterInitialization = "{\"states\" : [ {  \"id\" : \"4-5-8\", \"name\" : \"4-5-8 E Sq\", \"imagePath\" : \"ru/ru458S\", \"transitions\" : [ { \"command\" : \"ELR\", \"toState\" : \"4-4-7\" } ] }, {  \"id\" : \"4-4-7\", \"name\" : \"4-4-7 1 Sq\", \"imagePath\" : \"ru/ru447S\", \"transitions\" : [ { \"command\" : \"ELR\", \"toState\" : \"4-2-6\" }, { \"command\" : \"BattleHarden\", \"toState\" : \"4-5-8\" } ] }, {  \"id\" : \"4-2-6\", \"name\" : \"4-2-6 C Sq\", \"imagePath\" : \"ru/ru426S\", \"transitions\" : [] } ], \"keyToCommandMap\" : [] }";

        StateCounterParser p = new StateCounterParser( stateCounterInitialization, testFactory );
        
        Map<KeyStroke, String> map = p.getKeyCommandTranslation();
        
        assertTrue( map.isEmpty());
        
    }

    @Test
    public void testKeyCommandTranslationInitialization() {
        IStateCounterStateFactory testFactory = new TestStateCounterStateFactory();
        String stateCounterInitialization = "{\"states\" : [ {  \"id\" : \"4-5-8\", \"name\" : \"4-5-8 E Sq\", \"imagePath\" : \"ru/ru458S\", \"transitions\" : [ { \"command\" : \"ELR\", \"toState\" : \"4-4-7\" } ] }, {  \"id\" : \"4-4-7\", \"name\" : \"4-4-7 1 Sq\", \"imagePath\" : \"ru/ru447S\", \"transitions\" : [ { \"command\" : \"ELR\", \"toState\" : \"4-2-6\" }, { \"command\" : \"BattleHarden\", \"toState\" : \"4-5-8\" } ] }, {  \"id\" : \"4-2-6\", \"name\" : \"4-2-6 C Sq\", \"imagePath\" : \"ru/ru426S\", \"transitions\" : [] } ], \"keyToCommandMap\" : [ { \"keyStroke\" : \"ctrl pressed E\", \"command\" : \"ELR\" }, { \"keyStroke\" : \"ctrl pressed Q\", \"command\" : \"BattleHarden\" }] }";

        StateCounterParser p = new StateCounterParser( stateCounterInitialization, testFactory );
        
        Map<KeyStroke, String> map = p.getKeyCommandTranslation();
        
        //System.out.println("KeyStroke.toString() = " + KeyStroke.getKeyStroke("ctrl pressed E").toString());
        // map returns a null on a key it can't find
        //System.out.println(map.get(KeyStroke.getKeyStroke("ctrl pressed E")));
        
        assertFalse( map.isEmpty());
        
        assertEquals( "ELR", map.get(KeyStroke.getKeyStroke("ctrl pressed E")) );
        assertEquals( "BattleHarden", map.get(KeyStroke.getKeyStroke("ctrl pressed Q")) );
        
    }

    // passed in a bad (not JSON-parsable) string?

}
