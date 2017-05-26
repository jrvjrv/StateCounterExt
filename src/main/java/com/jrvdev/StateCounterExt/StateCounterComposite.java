package com.jrvdev.StateCounterExt;

import java.util.Map;

import javax.swing.KeyStroke;

import com.jrvdev.StateDataStructure.IStateMachine;

// is class accepts the outside classes it needs on its constructor. As such it is unit-testable. The StateCounter is a façade over
// this class, making StateCounter the "application root" for dependency injection.
public class StateCounterComposite {
    
    
    private IStateCounterParserFactory _parserFactory;

    private Map<KeyStroke,String> _keyToCommandMap;
    private IStateMachine< String, String, IStateCounterState > _stateMachine;
    
    public StateCounterComposite( IStateCounterParserFactory parserFactory ) {
        if ( parserFactory == null ) throw new NullPointerException("argument parserFactory");
        _parserFactory = parserFactory;
    }
    
    private boolean missingOrEmptyStateMachine() {
        return ( _stateMachine == null ) || ( _stateMachine.getCurrentState() == null ); 
    }
    
    private void initialize( String jsonInitialization ) {
        IStateCounterParser p = _parserFactory.createNew(jsonInitialization);
        _stateMachine = p.getStateMachine();
        _keyToCommandMap = p.getKeyCommandTranslation();
    }

    public void mySetType(String type) {
        initialize( type );
    }

    public String getLocalizedName() {
        return getName(true);
    }

    public String getName() {
        return getName( false );
    }
    
    public String getName( boolean localized ) {
        
        if ( !missingOrEmptyStateMachine() ) {
            return _stateMachine.getCurrentState().getName();
        }
        return null;
    }

}
