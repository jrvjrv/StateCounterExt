package com.jrvdev.StateCounterExt;

public class StateCounterParserFactory implements IStateCounterParserFactory {
    
    private IStateCounterStateFactory _stateCounterStateFactory;
    private IStateMachineFactory _stateMachineFactory;
    
    public StateCounterParserFactory( IStateCounterStateFactory stateCounterStateFactory, IStateMachineFactory stateMachineFactory ) {
        if ( stateCounterStateFactory == null ) throw new NullPointerException("argument stateCounterStateFactory");
        if ( stateMachineFactory == null ) throw new NullPointerException("argument stateMachineFactory");
        
        _stateCounterStateFactory = stateCounterStateFactory;
        _stateMachineFactory = stateMachineFactory;
    }

    public IStateCounterParser createNew(String stateCounterInitialization ) {
        return new StateCounterParser( stateCounterInitialization, _stateCounterStateFactory, _stateMachineFactory );
    }

}
