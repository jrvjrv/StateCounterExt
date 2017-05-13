package com.jrvdev.StateCounterExt;

import org.apache.commons.lang3.StringUtils;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Area;

import java.util.ArrayList;

import javax.swing.KeyStroke;

import com.jrvdev.StateDataStructure.StateMachine;
import com.jrvdev.StateDataStructure.IStateDataStructureState;

import VASSAL.build.module.documentation.HelpFile;
import VASSAL.command.ChangeTracker;
//import VASSAL.build.module.properties.List;
import VASSAL.command.Command;
import VASSAL.counters.Decorator;
import VASSAL.counters.EditablePiece;
import VASSAL.counters.GamePiece;
import VASSAL.counters.KeyCommand;
import VASSAL.counters.PieceEditor;
import VASSAL.tools.SequenceEncoder;
import VASSAL.tools.image.ImageUtils;
import VASSAL.tools.imageop.ScaledImagePainter;

// TODO: Embellishment implements TranslatablePiece rather than EditablePiece.
// TODO: TranslatablePiece adds internationalization, which can be deferred until later.
public class StateCounter extends Decorator implements EditablePiece {
   
    
    StateMachine< String, KeyStroke, StateCounterState > _stateMachine = new StateMachine< String, KeyStroke, StateCounterState >();
    
    private class StateCounterState implements IStateDataStructureState< String >{
        private String _stateId;
        private String _stateName;
        private ScaledImagePainter _imagePainter;
        private Rectangle _size;
        private int _xOff = 0, _yOff = 0;
        
        public StateCounterState( String id, String name, String imagePath ) {
            this._stateId = id;
            this._stateName = name;
            _imagePainter = new ScaledImagePainter();
            _imagePainter.setImageName(imagePath);
        }
        
        public ScaledImagePainter getScaledImagePainter() {
            return _imagePainter;
        }
        
        public String getStateId() {
            return _stateId;
        }

        
        public Rectangle getSize() {
            // TODO hacked thing to get it to work
            if ( _size == null ) {
                _size = ImageUtils.getBounds(_imagePainter.getImageSize());
                _size.translate(_xOff, _yOff);
            }
            
            return _size;
        }
        
        public String getName() {
            return _stateName;
        }
    }
    
    public static final String ID = "StateCounter;";
    // use the value that's inherited
    // protected GamePiece piece;
    
    private String _initializationInfo;
    
    //private KeyStroke _elrKeyStroke;
    //private KeyStroke _battleHardenKeyStroke;

    private void hardCodeInitialize() {
        System.out.println("hardCodeInitialize begin");
        // ctrl-E
        KeyStroke elrKeyStroke = KeyStroke.getKeyStroke( 69, 130 );
        // ctrl-Q
        KeyStroke battleHardenKeyStroke = KeyStroke.getKeyStroke( 81, 130 );
        // ctrl-V
        KeyStroke squadHalfsquadKeyStroke = KeyStroke.getKeyStroke(86, 130);
        // ctrl-F
        KeyStroke breakUnbreakKeyStroke = KeyStroke.getKeyStroke(70, 130);
        
        _stateMachine
            .addState( new StateCounterState( "4-5-8", "4-5-8 E Sq", "ru/ru458S"))
            .addState( new StateCounterState( "4-4-7", "4-4-7 1 Sq", "ru/ru447S"))
            .addState( new StateCounterState( "4-2-6", "4-2-6 C Sq", "ru/ru426S"))
            .addState( new StateCounterState( "5-2-7", "5-2-7 [1] Sq", "ru/ru527S"))
            .addState( new StateCounterState( "6-2-8", "6-2-8 [E] Sq", "ru/ru628S"))
            .addState( new StateCounterState( "2-4-8", "2-4-8 [E] Hs", "ru/ru248H"))
            .addState( new StateCounterState( "2-3-7", "2-3-7 1 Hs", "ru/ru237H"))
            .addState( new StateCounterState( "2-2-6", "2-2-6 C Hs", "ru/ru226H"))
            .addState( new StateCounterState( "2-2-7", "2-2-7 [1] Hs", "ru/ru227H"))
            .addState( new StateCounterState( "3-2-8", "3-2-8 [E] Hs", "ru/ru328H"))

            .addState( new StateCounterState( "broken 4-5-8", "4-5-8 E Sq", "ru/rus8b"))
            .addState( new StateCounterState( "broken 4-4-7", "4-4-7 1 Sq", "ru/rus7b"))
            .addState( new StateCounterState( "broken 4-2-6", "4-2-6 C Sq", "ru/rus5b"))
            .addState( new StateCounterState( "broken 5-2-7", "5-2-7 [1] Sq", "ru/rus7b"))
            .addState( new StateCounterState( "broken 6-2-8", "6-2-8 [E] Sq", "ru/rus8b"))
            .addState( new StateCounterState( "broken 2-4-8", "2-4-8 [E] Hs", "ru/ruh7b"))
            .addState( new StateCounterState( "broken 2-3-7", "2-3-7 1 Hs", "ru/ruh6b"))
            .addState( new StateCounterState( "broken 2-2-6", "2-2-6 C Hs", "ru/ruh4b"))
            .addState( new StateCounterState( "broken 2-2-7", "2-2-7 [1] Hs", "ru/ruh6b"))
            .addState( new StateCounterState( "broken 3-2-8", "3-2-8 [E] Hs", "ru/ruh7b"));

        _stateMachine.addTransition(elrKeyStroke, "4-5-8", "4-4-7" )
            .addTransition(elrKeyStroke, "4-4-7", "4-2-6" )
            .addTransition(elrKeyStroke, "6-2-8", "5-2-7" )
            .addTransition(elrKeyStroke, "5-2-7", "4-2-6" )
            .addTransition(elrKeyStroke, "2-4-8", "2-3-7" )
            .addTransition(elrKeyStroke, "2-3-7", "2-2-6" )
            .addTransition(elrKeyStroke, "3-2-8", "2-2-7" )
            .addTransition(elrKeyStroke, "2-2-7", "2-2-6" );
        
        _stateMachine
            .addTransition(battleHardenKeyStroke, "4-2-6", "5-2-7" )
            .addTransition(battleHardenKeyStroke, "5-2-7", "6-2-8" )
            .addTransition(battleHardenKeyStroke, "4-4-7", "4-5-8" )
            .addTransition(battleHardenKeyStroke, "2-2-6", "2-2-7" )
            .addTransition(battleHardenKeyStroke, "2-2-7", "3-2-8" )
            .addTransition(battleHardenKeyStroke, "2-3-7", "2-4-8" );

        _stateMachine
            .addTransition(squadHalfsquadKeyStroke, "4-5-8", "2-4-8" )
            .addTransition(squadHalfsquadKeyStroke, "4-4-7", "2-3-7" )
            .addTransition(squadHalfsquadKeyStroke, "4-2-6", "2-2-6" )
            .addTransition(squadHalfsquadKeyStroke, "6-2-8", "3-2-8" )
            .addTransition(squadHalfsquadKeyStroke, "5-2-7", "2-2-7" )
    
            .addTransition(squadHalfsquadKeyStroke, "2-4-8", "4-5-8" )
            .addTransition(squadHalfsquadKeyStroke, "2-3-7", "4-4-7" )
            .addTransition(squadHalfsquadKeyStroke, "2-2-6", "4-2-6" )
            .addTransition(squadHalfsquadKeyStroke, "3-2-8", "6-2-8" )
            .addTransition(squadHalfsquadKeyStroke, "2-2-7", "5-2-7" )
          
            .addTransition(squadHalfsquadKeyStroke, "broken 4-5-8", "broken 2-4-8" )
            .addTransition(squadHalfsquadKeyStroke, "broken 4-4-7", "broken 2-3-7" )
            .addTransition(squadHalfsquadKeyStroke, "broken 4-2-6", "broken 2-2-6" )
            .addTransition(squadHalfsquadKeyStroke, "broken 6-2-8", "broken 3-2-8" )
            .addTransition(squadHalfsquadKeyStroke, "broken 5-2-7", "broken 2-2-7" )
  
            .addTransition(squadHalfsquadKeyStroke, "broken 2-4-8", "broken 4-5-8" )
            .addTransition(squadHalfsquadKeyStroke, "broken 2-3-7", "broken 4-4-7" )
            .addTransition(squadHalfsquadKeyStroke, "broken 2-2-6", "broken 4-2-6" )
            .addTransition(squadHalfsquadKeyStroke, "broken 3-2-8", "broken 6-2-8" )
            .addTransition(squadHalfsquadKeyStroke, "broken 2-2-7", "broken 5-2-7" );

        _stateMachine
            .addTransition(breakUnbreakKeyStroke, "4-5-8", "broken 4-5-8" )
            .addTransition(breakUnbreakKeyStroke, "4-4-7", "broken 4-4-7" )
            .addTransition(breakUnbreakKeyStroke, "4-2-6", "broken 4-2-6" )
            .addTransition(breakUnbreakKeyStroke, "6-2-8", "broken 6-2-8" )
            .addTransition(breakUnbreakKeyStroke, "5-2-7", "broken 5-2-7" )
    
            .addTransition(breakUnbreakKeyStroke, "2-4-8", "broken 2-4-8" )
            .addTransition(breakUnbreakKeyStroke, "2-3-7", "broken 2-3-7" )
            .addTransition(breakUnbreakKeyStroke, "2-2-6", "broken 2-2-6" )
            .addTransition(breakUnbreakKeyStroke, "3-2-8", "broken 3-2-8" )
            .addTransition(breakUnbreakKeyStroke, "2-2-7", "broken 2-2-7" )

            .addTransition(breakUnbreakKeyStroke, "broken 4-5-8", "4-5-8")
            .addTransition(breakUnbreakKeyStroke, "broken 4-4-7", "4-4-7")
            .addTransition(breakUnbreakKeyStroke, "broken 4-2-6", "4-2-6")
            .addTransition(breakUnbreakKeyStroke, "broken 6-2-8", "6-2-8")
            .addTransition(breakUnbreakKeyStroke, "broken 5-2-7", "5-2-7")
                                                                         
            .addTransition(breakUnbreakKeyStroke, "broken 2-4-8", "2-4-8")
            .addTransition(breakUnbreakKeyStroke, "broken 2-3-7", "2-3-7")
            .addTransition(breakUnbreakKeyStroke, "broken 2-2-6", "2-2-6")
            .addTransition(breakUnbreakKeyStroke, "broken 3-2-8", "3-2-8")
            .addTransition(breakUnbreakKeyStroke, "broken 2-2-7", "2-2-7");
        System.out.println("hardCodeInitialize end");
}
    
    public StateCounter() {
        this( ID + "{\"name\":\"Hard-coded in StateCounter\"}", null );
    }
    
    public StateCounter( String initializationInfo, GamePiece piece ) {
        
        // this calls through decorator rather than setting the value directly. 
        this.setInner( piece );
        this.mySetType( initializationInfo );
    }

    @Override
    public void draw(Graphics g, int x, int y, Component obs, double zoom) {
        // TODO hacked thing to get it to work
        final Rectangle r = getCurrentImageBounds();

        piece.draw(g, x, y, obs, zoom);
        //_states.get(_currentState).getScaledImagePainter().draw(g, x + (int)(zoom*r.x), y + (int)(zoom*r.y), zoom, obs);
        _stateMachine.getCurrentState().getScaledImagePainter().draw(g, x + (int)(zoom*r.x), y + (int)(zoom*r.y), zoom, obs);
    }
    
    private Rectangle getCurrentImageBounds() {
        // TODO figure out what states can start in
        // if ( _states != null && _states.size() > 0 ) {
        //     return _states.get(_currentState).getSize();
        if ( !missingOrEmptyStateMachine() ) {
            return _stateMachine.getCurrentState().getSize();
        }
        else {
            return new Rectangle();
        }
    }
    
    private boolean missingOrEmptyStateMachine() {
        return ( _stateMachine == null ) || ( _stateMachine.getCurrentState() == null ); 
    }


    @Override
    public Rectangle boundingBox() {
        // TODO taken from embellishment
        final Rectangle r = piece.boundingBox();
        r.add(getCurrentImageBounds());
        return r;
    }

    @Override
    public Shape getShape() {
        // TODO Hacked, arbitrary implementation
        //return piece.getShape();
        final Shape innerShape = piece.getShape();
        
        final Rectangle r = getCurrentImageBounds();
        
        // If the label is completely enclosed in the current counter shape, then we can just return
        // the current shape
        if (innerShape.contains(r.x, r.y, r.width, r.height)) {
            return innerShape;
        }
        else {
            final Area a = new Area(innerShape);
        
            a.add(new Area(r));
            return a;
        }
    }
    
    @Override
    public String getLocalizedName() {
        return getName(true);
    }

    @Override
    public String getName() {
        return getName( false );
    }
    
    public String getName( boolean localized ) {
        
        if ( !missingOrEmptyStateMachine() ) {
            return _stateMachine.getCurrentState().getName();
        }
        return null;
    }

    @Override
    
    // shows up when adding a new piece on list of available traits.
    public String getDescription() {
        // TODO Hacked, arbitrary implementation
        String name = getName( true );
        String description = "State Counter"; 
        if ( !StringUtils.isBlank( name ) ) {
            description += " [" + name + "]";
        }
        return description;
    }

    @Override
    public void mySetType(String type) {
        this._initializationInfo = type;
        hardCodeInitialize();
    }

    @Override
    public HelpFile getHelpFile() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void mySetState(String newState) {
        // TODO Auto-generated method stub
        //this._initializationInfo = newState;
        // TODO hard-code initialization
        //hardCodeInitialize();
        final SequenceEncoder.Decoder st = new SequenceEncoder.Decoder(newState, ';');
        _stateMachine.setState( st.nextToken("4-5-8") );

    }

    @Override
    public String myGetState() {
        final SequenceEncoder se = new SequenceEncoder(';');
        if ( !missingOrEmptyStateMachine() ) {
            return se.append(String.valueOf(_stateMachine.getCurrentState().getStateId())).getValue();
        }

        return null;
    }

    @Override
    public String myGetType() {
        // TODO: this should 
        return this._initializationInfo;
    }

    @Override
    protected KeyCommand[] myGetKeyCommands() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Command myKeyEvent(KeyStroke stroke) {
        // TODO hard-coded
        final ChangeTracker tracker = new ChangeTracker(this);
        _stateMachine.transition( stroke );
        return tracker.isChanged() ? tracker.getChangeCommand() : null;
    }
    
    @Override
    public PieceEditor getEditor() {
        // TODO Dummy implementation
        return super.getEditor();
    }

}
