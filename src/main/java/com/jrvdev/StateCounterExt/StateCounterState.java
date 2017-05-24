package com.jrvdev.StateCounterExt;

import java.awt.Rectangle;

import VASSAL.tools.image.ImageUtils;
import VASSAL.tools.imageop.ScaledImagePainter;

public class StateCounterState implements IStateCounterState {
    private String _stateId;
    private String _stateName;
    private ScaledImagePainter _imagePainter;
    private Rectangle _size;
    private int _xOff = 0, _yOff = 0;
    
    public StateCounterState( String id, String name, String imagePath ) {
        this._stateId = id;
        this._stateName = name;
        _imagePainter = new ScaledImagePainter();
        _imagePainter.setImageName(imagePath);
    }
    
    public ScaledImagePainter getScaledImagePainter() {
        return _imagePainter;
    }
    
    public String getStateId() {
        return _stateId;
    }

    
    public Rectangle getSize() {
        // TODO hacked thing to get it to work
        if ( _size == null ) {
            _size = ImageUtils.getBounds(_imagePainter.getImageSize());
            _size.translate(_xOff, _yOff);
        }
        
        return _size;
    }
    
    public String getName() {
        return _stateName;
    }
}
