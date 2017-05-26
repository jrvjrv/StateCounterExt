package com.jrvdev.StateCounterExt;

import java.util.Map;

import javax.swing.KeyStroke;

import com.jrvdev.StateDataStructure.IStateMachine;

public interface IStateCounterParser {
    IStateMachine<String, String, IStateCounterState> getStateMachine();
    Map<KeyStroke, String> getKeyCommandTranslation();
}
