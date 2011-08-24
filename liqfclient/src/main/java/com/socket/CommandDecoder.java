package com.socket;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class CommandDecoder extends CumulativeProtocolDecoder { 

    protected boolean doDecode(IoSession session, IoBuffer in, 
            ProtocolDecoderOutput out) throws Exception { 
        if (in.prefixedDataAvailable(4, Constants.MAX_COMMAND_LENGTH)) { 
            int length = in.getInt(); 
            byte[] bytes = new byte[length]; 
            in.get(bytes); 
            int commandNameLength = Constants.COMMAND_NAME_LENGTH; 
            byte[] cmdNameBytes = new byte[commandNameLength]; 
            System.arraycopy(bytes, 0, cmdNameBytes, 0, commandNameLength); 
            String cmdName = (new String(cmdNameBytes)).trim(); 
            AbstractTetrisCommand command = TetrisCommandFactory 
                .newCommand(cmdName); 
            if (command != null) { 
                byte[] cmdBodyBytes = new byte[length - commandNameLength]; 
                System.arraycopy(bytes, commandNameLength, cmdBodyBytes, 0, 
                    length - commandNameLength); 
                command.bodyFromBytes(cmdBodyBytes); 
                out.write(command); 
            } 
            return true; 
        } else { 
            return false; 
        } 
    } 
}
