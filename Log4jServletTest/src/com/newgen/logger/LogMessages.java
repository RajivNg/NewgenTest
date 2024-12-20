package com.newgen.logger;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LogMessages {
	public static Logger errorLog = null, statusLog = null, xmlLog = null;

    public static synchronized void setLogFiles() {

        InputStream is = null;
        try {

            String LOG_FILE_PATH="D:\\Logger\\Log4j.properties";
            is = new BufferedInputStream(new FileInputStream(LOG_FILE_PATH));
            Properties ps = new Properties();
            ps.load(is);
            is.close();

            org.apache.log4j.LogManager.shutdown();

            PropertyConfigurator.configure(ps);

            errorLog = Logger.getLogger("ErrorLog");
            statusLog = Logger.getLogger("StatusLog");
            xmlLog = Logger.getLogger("XMLLog");

        } catch (Exception e) {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException te) {
                errorLog.info("Error in setting Logger : " + te);
            }
            e.printStackTrace();
        }
    }
}
