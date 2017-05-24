package com.jrvdev.StateCounterExt;

public interface IStateCounterStateFactory {
    IStateCounterState createNew( String id, String name, String imagePath );
}
