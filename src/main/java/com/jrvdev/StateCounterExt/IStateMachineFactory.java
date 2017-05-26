package com.jrvdev.StateCounterExt;

import com.jrvdev.StateDataStructure.IStateMachine;

public interface IStateMachineFactory {
    IStateMachine< String, String, IStateCounterState > createNew();
}
