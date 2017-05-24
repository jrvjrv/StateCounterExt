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
    
    public StateCounterParser( String stateCounterInitialization, IStateCounterStateFactory stateCounterStateFactory ) {
        _stateCounterInitialization = stateCounterInitialization;
        if ( stateCounterStateFactory == null ) throw new NullPointerException("argument stateCounterStateFactory");
        _stateCounterStateFactory = stateCounterStateFactory;
    }
    
    private void ParseStates( JSONObject stateCounterJSONObject, IStateMachine<String, String, IStateCounterState> theMachine ) {
        JSONArray statesJSONArray = (JSONArray) stateCounterJSONObject.get("states");
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
    
    public void InitializeStateMachine( IStateMachine<String, String, IStateCounterState> theMachine ) {
        JSONParser jsonParser = new JSONParser();
        try {
            JSONObject stateCounterJSONObject = (JSONObject) jsonParser.parse(_stateCounterInitialization);
            //System.out.println(stateCounterJSONObject);
            ParseStates( stateCounterJSONObject, theMachine );
            //System.out.println((String) state.get("id"));
        }
        catch ( Exception ex ) {
            System.out.println( "got exception " + ex.getClass().getName());
        }
    }
    
    public Map<KeyStroke, String> getKeyCommandTranslation() {
        Map<KeyStroke, String> theMap = new HashMap<KeyStroke, String>();

        JSONParser jsonParser = new JSONParser();
        try {
            JSONObject stateCounterJSONObject = (JSONObject) jsonParser.parse(_stateCounterInitialization);
            JSONArray keyToCommandMapJSONArray = (JSONArray) stateCounterJSONObject.get("keyToCommandMap");
            Iterator<JSONObject> iterator = (Iterator<JSONObject>) keyToCommandMapJSONArray.iterator();
            while (iterator.hasNext()) {
                JSONObject keyToCommandMappingJSONObject = iterator.next();
                
                theMap.put(KeyStroke.getKeyStroke(keyToCommandMappingJSONObject.get("keyStroke").toString()), keyToCommandMappingJSONObject.get("command").toString());
            }
        }
        catch ( Exception ex ) {
            System.out.println( "got translation exception " + ex.getClass().getName());
        }
        
        return theMap;
    }
}
