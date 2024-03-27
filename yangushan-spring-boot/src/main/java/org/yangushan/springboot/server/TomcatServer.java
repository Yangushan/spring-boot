/*
 * Copyright 2012-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.yangushan.springboot.server;

import org.apache.catalina.Context;
import org.apache.catalina.Engine;
import org.apache.catalina.Host;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Server;
import org.apache.catalina.Service;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardEngine;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.startup.Tomcat;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * created by yangushan
 * 2024/3/25 19:59
 */
public class TomcatServer implements WebServer {
	@Override
	public void start(WebApplicationContext webApplicationContext) {
		Tomcat tomcat = new Tomcat();

		Server server = tomcat.getServer();
		Service service = server.findService("Tomcat");

		Connector connector = new Connector();
		connector.setPort(8081);

		Engine engine = new StandardEngine();
		engine.setDefaultHost("localhost");

		Host host = new StandardHost();
		host.setName("localhost");

		String contextPath = "";
		Context context = new StandardContext();
		context.setPath(contextPath);
		context.addLifecycleListener(new Tomcat.FixContextListener());

		host.addChild(context);
		engine.addChild(host);

		service.setContainer(engine);
		service.addConnector(connector);

		tomcat.addServlet(contextPath, "dispatcher", new DispatcherServlet(webApplicationContext));
		context.addServletMappingDecoded("/*", "dispatcher");

		try {
			tomcat.start();
		} catch (LifecycleException e) {
			e.printStackTrace();
		}
	}
}
