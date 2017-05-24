package com.jrvdev.StateCounterExt;

import javax.swing.KeyStroke;

import com.jrvdev.StateDataStructure.IStateMachine;

// is class accepts the outside classes it needs on its constructor. As such it is unit-testable. The StateCounter is a façade over
// this class, making StateCounter the "application root" for dependency injection.
public class StateCounterComposite {
    
    IStateMachine< String, String, IStateCounterState > _stateMachine;

    
    private IStateCounterParserFactory _parserFactory;
    private IStateCounterStateFactory _stateFactory;
    
    public StateCounterComposite( 
            IStateCounterParserFactory parserFactory, 
            IStateCounterStateFactory stateFactory, 
            IStateMachine< String, String, IStateCounterState > stateMachine ) {
        if ( parserFactory == null ) throw new NullPointerException("argument parserFactory");
        if ( stateFactory == null ) throw new NullPointerException("argument stateFactory");
        if ( stateMachine == null ) throw new NullPointerException("argument stateMachine");
    }

}
