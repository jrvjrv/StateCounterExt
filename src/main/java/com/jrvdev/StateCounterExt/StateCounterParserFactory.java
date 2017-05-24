package com.jrvdev.StateCounterExt;

public class StateCounterParserFactory implements IStateCounterParserFactory {

    public IStateCounterParser createNew(String stateCounterInitialization,
            IStateCounterStateFactory stateCounterStateFactory) {
        return new StateCounterParser( stateCounterInitialization, stateCounterStateFactory );
    }

}
