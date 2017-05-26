package com.jrvdev.StateCounterExt;

import com.jrvdev.StateDataStructure.IStateMachine;
import com.jrvdev.StateDataStructure.StateMachine;

public class StateMachineFactory implements IStateMachineFactory {

    public IStateMachine<String, String, IStateCounterState> createNew() {
        return new StateMachine<String, String, IStateCounterState>();
    }

}
