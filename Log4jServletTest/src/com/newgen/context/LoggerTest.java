package com.newgen.context;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newgen.logger.LogMessages;
import com.newgen.logger.LogUtil;


/**
 * Servlet implementation class LoggerTest
 */
@WebServlet("/test")
public class LoggerTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
        
	//static Logger logger = LogManager.getLogger(LoggerTest.class);
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		
        System.out.println("Logger Implemented!!");
        LogUtil.printLog("Error in WMConnect:- " , "debug", "callWMConnect");
        String html = "<html><h2>Log4j has been initialized successfully!</h2></html>";
        response.getWriter().println(html);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//ContextListener.contextInitialized();
		
		System.out.println("Logger Implemented!!");
		LogMessages.setLogFiles();
		LogMessages.statusLog.info("CloseWorkitem Parameter: ");
        //LogUtil.printLog("Error in WMConnect:- " , "debug", "callWMConnect");
        String html = "<html><h2>Log4j has been initialized successfully!</h2></html>";
        response.getWriter().println(html);
		
	}

}
