package com.jrvdev.StateCounterExt;

import java.util.Map;

import javax.swing.KeyStroke;

import com.jrvdev.StateDataStructure.IStateMachine;

public interface IStateCounterParser {
    void InitializeStateMachine( IStateMachine<String, String, IStateCounterState> theMachine );
    Map<KeyStroke, String> getKeyCommandTranslation();
}
