package com.jrvdev.StateCounterExt;

import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;

import java.lang.NullPointerException;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.KeyStroke;

import org.json.simple.JSONArray;
import com.jrvdev.StateDataStructure.IStateMachine;

public class StateCounterParser implements IStateCounterParser {
    
    private String _stateCounterInitialization;
    private IStateCounterStateFactory _stateCounterStateFactory;
    private IStateMachineFactory _stateMachineFactory;
    private JSONParser _jsonParser = new JSONParser();
    private JSONObject _stateCounterJSONObject;
    
    public StateCounterParser( String stateCounterInitialization, IStateCounterStateFactory stateCounterStateFactory, IStateMachineFactory stateMachineFactory ) {
        _stateCounterInitialization = stateCounterInitialization;
        if ( stateCounterStateFactory == null ) throw new NullPointerException("argument stateCounterStateFactory");
        _stateCounterStateFactory = stateCounterStateFactory;
        if ( stateMachineFactory == null ) throw new NullPointerException("argument stateMachineFactory");
        _stateMachineFactory = stateMachineFactory;
    }
    
    private void ParseStates( JSONArray statesJSONArray, IStateMachine<String, String, IStateCounterState> theMachine ) {
        Iterator<JSONObject> iterator = (Iterator<JSONObject>) statesJSONArray.iterator();
        while (iterator.hasNext()) {
            JSONObject stateJSONObject = iterator.next();
            IStateCounterState newState = _stateCounterStateFactory.createNew(  
                    (String)stateJSONObject.get("id"), 
                    (String)stateJSONObject.get("name"), 
                    (String)stateJSONObject.get("imagePath") 
                    );
            theMachine.addState(newState);
            ParseTransitions( stateJSONObject, newState, theMachine );
        }
    }
    
    private void ParseTransitions( JSONObject stateJSONObject, IStateCounterState newState, IStateMachine<String, String, IStateCounterState> theMachine ) {
        JSONArray transitionsJSONArray = (JSONArray) stateJSONObject.get("transitions");
        Iterator<JSONObject> iterator = (Iterator<JSONObject>) transitionsJSONArray.iterator();
        while (iterator.hasNext()) {
            JSONObject transitionJSONObject = iterator.next();
            theMachine.addTransition( transitionJSONObject.get("command").toString(), newState.getStateId(), transitionJSONObject.get("toState").toString());
        }
    }
    
    private JSONObject GetStateCounterJSONObject() {
        if ( this._stateCounterJSONObject == null ) {
            try {
                JSONParser p = new JSONParser();
                _stateCounterJSONObject = (JSONObject) p.parse(_stateCounterInitialization);
            }
            catch ( Exception ex ) {
                // convert parsing errors to the initialization for an empty state machine
                // hopefully parsing errors will not be real parsing errors but things like null or empty string
                System.out.println( "got exception " + ex.getClass().getName());
                _stateCounterJSONObject = new JSONObject();
                _stateCounterJSONObject.put("states", new JSONArray());
                _stateCounterJSONObject.put("keyToCommandMap", new JSONArray());
            }
        }
        return _stateCounterJSONObject;
    }
    
    public IStateMachine<String, String, IStateCounterState> getStateMachine() {
        IStateMachine<String, String, IStateCounterState> theMachine = _stateMachineFactory.createNew();
        ParseStates( (JSONArray) GetStateCounterJSONObject().get("states"), theMachine );

        return theMachine;
    }
    
    public Map<KeyStroke, String> getKeyCommandTranslation() {
        Map<KeyStroke, String> theMap = new HashMap<KeyStroke, String>();

        JSONArray keyToCommandMapJSONArray = (JSONArray) GetStateCounterJSONObject().get("keyToCommandMap");
        Iterator<JSONObject> iterator = (Iterator<JSONObject>) keyToCommandMapJSONArray.iterator();
        while (iterator.hasNext()) {
            JSONObject keyToCommandMappingJSONObject = iterator.next();
            
            theMap.put(KeyStroke.getKeyStroke(keyToCommandMappingJSONObject.get("keyStroke").toString()), keyToCommandMappingJSONObject.get("command").toString());
        }
        
        return theMap;
    }
}
