/**
 * 
 */
package com.manning.sbia.ch11;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;

/**
 * @author acogoluegnes
 */
public class LaunchEnterpriseIntegrationServer {
	
	public static final String PICKUP_DIR = System.getProperty("java.io.tmpdir")
		+File.separator+"sbia";
	
	public static void main(String[] args) throws Exception {
		File pickUpDir = new File(PICKUP_DIR);
		if(pickUpDir.exists()) {
			FileUtils.cleanDirectory(pickUpDir);
		}
		System.setProperty("product.import.pickup.dir", PICKUP_DIR);
		Server server = new Server();
		Connector connector = new ServerConnector(server);
		server.addConnector(connector);

//		WebAppContext wac = new WebAppContext();
//		wac.setContextPath("/enterpriseintegration");
//		wac.setWar("./src/main/webapp");
//		server.setHandler(wac);
		server.setStopAtShutdown(true);
		server.start();
		
		System.out.println("**** enterprise integration server launched!");
	}
	
}
