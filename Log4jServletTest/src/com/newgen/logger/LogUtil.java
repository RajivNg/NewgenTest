package com.newgen.logger;

import java.io.File;
import java.util.HashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;


public class LogUtil {

	static Logger logger = LogManager.getLogger(LogUtil.class);
	

	public static void printLog(String msg, String level, String sResourceName) {

		try {
			System.out.println("Inside PrintLog::");
			LoggerContext context = (LoggerContext) LogManager.getContext(false);
			System.out.println("1");
			File file = new File("D:\\LoggerTest\\log4j2.xml");
			System.out.println("2");
			HashMap<String, String> msgMap = new HashMap<>();
			System.out.println("3");
			
			if (level.equalsIgnoreCase("info")) {
				logger.info(new CustomMessage(msgMap));
			}
			if (level.equalsIgnoreCase("debug")) {
				System.out.println("Into Debug::"+msgMap);
				logger.debug(new CustomMessage(msgMap));
			}
			if (level.equalsIgnoreCase("error")) {
				logger.error(new CustomMessage(msgMap));
			}
		} catch (Exception ex) {
			System.out.println("Into Exception::");
			System.out.println("Exception in printLog method:- " + ex.getMessage());
		}
	}
}