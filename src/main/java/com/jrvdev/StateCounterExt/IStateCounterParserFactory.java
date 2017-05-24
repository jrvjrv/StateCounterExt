package com.jrvdev.StateCounterExt;

public interface IStateCounterParserFactory {
    IStateCounterParser createNew( String stateCounterInitialization, IStateCounterStateFactory stateCounterStateFactory );
}
