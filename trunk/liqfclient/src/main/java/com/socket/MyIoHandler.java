package com.socket;

import java.util.Date;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyIoHandler extends IoHandlerAdapter {
	// ��������ʹ�õ�SLF4J��Ϊ��־���棬����Ϊʲô�ں���˵����
	private final static Logger log = LoggerFactory
			.getLogger(MyIoHandler.class);

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		String str = message.toString();
		Date date = new Date();
		log.info(date.toString() + " ::The message received is [" + str + "]");
		if (str.endsWith("quit")) {
			session.close(true);
			return;
		}
	}
}
