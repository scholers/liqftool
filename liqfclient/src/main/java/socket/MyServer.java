package socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class MyServer {
	private static final int PORT = 9123;
	private static final String CODING = "UTF-8";

	public static void main(String[] arges) throws IOException {
		final IoAcceptor acceptor = new NioSocketAcceptor();
		acceptor.getSessionConfig().setReadBufferSize(2048);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		acceptor.setHandler(new MyIoHandler());
		acceptor.getFilterChain().addLast(
				"codec",
				new ProtocolCodecFilter(new MyCodecFactory(Charset
						.forName(CODING))));
		acceptor.bind(new InetSocketAddress(PORT));
	}
}
