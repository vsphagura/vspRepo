package com.vsp.project1.util;

public class LoggerTestMain {

	public LoggerTestMain() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		AppLogger logger = new AppLogger(LoggerTestMain.class);
		
		logger.debug("welcome() is executed");
	}

}
