package com.socket;

import java.nio.charset.Charset;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineDecoder;
import org.apache.mina.filter.codec.textline.TextLineEncoder;

/**
 * 
 * @author jill
 *
 */
public class MyCodecFactory implements ProtocolCodecFactory {

    private final TextLineEncoder encoder;
    private final TextLineDecoder decoder;

    /**
     * Creates a new instance with the current default {@link Charset}.
     */
    public MyCodecFactory() {
        this(Charset.defaultCharset());
    }

    /**
     * Creates a new instance with the specified {@link Charset}.  The
     * encoder uses a UNIX {@link LineDelimiter} and the decoder uses
     * the AUTO {@link LineDelimiter}.
     *
     * @param charset
     *  The charset to use in the encoding and decoding
     */
    public MyCodecFactory(Charset charset) {
        encoder = new TextLineEncoder(charset, LineDelimiter.UNIX);
        decoder = new TextLineDecoder(charset, LineDelimiter.AUTO);
    }
    
    
    public ProtocolEncoder getEncoder(IoSession session) {
        return encoder;
    }

    public ProtocolDecoder getDecoder(IoSession session) {
        return decoder;
    }
}