package com.shotacon.wx.util;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class PC {

	public static void main(String[] args) throws Exception {

		
		JSch js = new JSch();

//		js.addIdentity("shotacon");

		Session session = js.getSession("root", "47.95.117.48", 22);
		session.setPassword("Sunyue74110");

		session.setConfig("StrictHostKeyChecking", "no");

		session.connect();

		ChannelExec ce = (ChannelExec) session.openChannel("exec");
		ce.setCommand("ifconfig");
		ce.setInputStream(null);
		ce.setErrStream(System.err);
		ce.setOutputStream(System.out);
		ce.connect();

		while (!ce.isClosed())
			Thread.sleep(1000);

		ce.disconnect();
		session.disconnect();

	}
}
