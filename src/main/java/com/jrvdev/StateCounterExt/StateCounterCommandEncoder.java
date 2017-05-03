package com.jrvdev.StateCounterExt;

import VASL.counters.StateCounter;
import VASSAL.command.AddPiece;
import VASSAL.command.Command;
import VASSAL.command.NullCommand;
import VASSAL.counters.Decorator;
import VASSAL.counters.GamePiece;
import VASSAL.tools.SequenceEncoder;

public class StateCounterCommandEncoder extends VASL.build.module.ASLCommandEncoder { // VASSAL.build.module.BasicCommandEncoder {
    @Override
    public Decorator createDecorator(String type, GamePiece inner) {
        //else if (type.startsWith(StateCounter.ID)) {
        if (type.startsWith(StateCounter.ID)) {
            return new StateCounter(type,inner);
        }
        else {
            return super.createDecorator(type, inner);
        }
    }

    public static final String SC_ADD = "SC" + ADD; //$NON-NLS-1$
    private static final char PARAM_SEPARATOR = ADD.substring(ADD.length() - 1 ).toCharArray()[0];

    @Override
    public Command decode(String command) {
        if (command.length() == 0) {
        return new NullCommand();
      }
      SequenceEncoder.Decoder st;
      if (command.startsWith(SC_ADD)) {
        command = command.substring(SC_ADD.length());
        st = new SequenceEncoder.Decoder(command, PARAM_SEPARATOR);
        String id = unwrapNull(st.nextToken());
        String type = st.nextToken();
        String state = st.nextToken();
        GamePiece p = createPiece(type);
        if (p == null) {
          return null;
        }
        else {
          p.setId(id);
          return new AddPiece(p, state);
        }
      }
      
      return null;

    }
    
    private String unwrapNull(String s) {
        return "null".equals(s) ? null : s; //$NON-NLS-1$
    }

}
