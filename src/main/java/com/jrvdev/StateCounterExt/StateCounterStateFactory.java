package com.jrvdev.StateCounterExt;

public class StateCounterStateFactory implements IStateCounterStateFactory {

    public IStateCounterState createNew(String id, String name, String imagePath) {
        return new StateCounterState( id, name, imagePath );
    }
}
