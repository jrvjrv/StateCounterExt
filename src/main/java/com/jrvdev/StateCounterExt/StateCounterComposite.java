package com.jrvdev.StateCounterExt;

import java.awt.Rectangle;
import java.util.Map;

import javax.swing.KeyStroke;

import com.jrvdev.StateDataStructure.IStateMachine;

import VASSAL.command.Command;
import VASSAL.tools.imageop.ScaledImagePainter;

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
    
    public void initialize( String jsonInitialization ) {
        IStateCounterParser p = _parserFactory.createNew(jsonInitialization);
        _stateMachine = p.getStateMachine();
        _keyToCommandMap = p.getKeyCommandTranslation();
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
    
    // return type is different from StateCounter 
    public void myKeyEvent(KeyStroke stroke) {
        String transitionCommand = (String) this._keyToCommandMap.get( stroke );
        _stateMachine.transition( transitionCommand );
    }
    
    public ScaledImagePainter getScaledImagePainter() {
        if ( this.missingOrEmptyStateMachine()) {
            return null;
        }
        return this._stateMachine.getCurrentState().getScaledImagePainter();
    }
    
    public Rectangle getCurrentImageBounds() {
        if ( !missingOrEmptyStateMachine() ) {
            return _stateMachine.getCurrentState().getSize();
        }
        else {
            return new Rectangle();
        }
    }
    
    public void mySetState(String newState) {
        _stateMachine.setState(newState);
    }
    
    public String myGetState() {
        if ( !missingOrEmptyStateMachine() ) {
            return _stateMachine.getCurrentState().getStateId();
        }
        return null;

    }

}
