package com.jrvdev.StateCounterExt;

import java.awt.Rectangle;
import com.jrvdev.StateDataStructure.IStateDataStructureState;
import VASSAL.tools.imageop.ScaledImagePainter;

public interface IStateCounterState extends IStateDataStructureState< String > {
    ScaledImagePainter getScaledImagePainter();
    String getStateId();
    Rectangle getSize();
    String getName();
}
