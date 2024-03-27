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

package org.yangushan.springboot;

import java.util.Map;

import org.yangushan.springboot.server.WebServer;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

/**
 * 模拟的SpringApplication，启动类
 * created by yangushan
 * 2024/3/25 19:05
 */
public class MySpringApplication {

	public static void run(Class clazz) {


		/**
		 * 1.0 逻辑，简单的加载我们Spring的context，然后启动Tomcat，就可以自动识别我们的Mvc功能
		 * 但是这样有个缺陷，如果我们现在不想使用tomcat想要换成jetty怎么办？
		 */
		//创建一个Spring容器
//		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
//		context.register(clazz); // 配置启动类
//
//		// 启动我们内嵌的Tomcat
//		startTomcat(context);


		/**
		 * 2.0 逻辑，把tomcat的逻辑抽象成一个webServer，调用里面的start启动我们的服务就好了
		 * 但是springboot中，引入了tomcat的包就用tomcat,没有引入的话引入了jetty的包就用jetty，所以我们定义一个getServer接口
		 * 所以我们有了WebServer的bean才能拿到，所以这里还需要创建一个配置类，帮我们导入我们需要的bean
		 *
		 * 2.1 但是一开始运行报错，拿不到一个server的bean，但是我们配置了却拿不到，为什么？
		 * 因为我们是被引入到test项目的，test项目导入的包是它当前的包，并没有包含我们
		 * 所以我们这里还需要特殊导入的方式导入我们的配置文件
		 *
		 * 2.2 因为我们项目需要tomcat和Jetty所以在这个项目中两个包都需要导入，这样就会导致我们test在读取的时候同时都会引入tomcat和jetty
		 * 所以我们可以在打包做选择，默认tomcat打包进去，jetty不被引入的包一起引入，所以在pom里面增加Optional属性就可以了
		 */
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(clazz); // 配置启动类
		context.refresh();
		WebServer webServer = getServer(context);
		webServer.start(context);
	}

	private static WebServer getServer(WebApplicationContext context) {
		// 那么我们怎么能拿到Server呢？从Spring里面拿
		Map<String, WebServer> beansOfType = context.getBeansOfType(WebServer.class);
		if (beansOfType == null || beansOfType.isEmpty()) {
			throw new IllegalArgumentException("没有配置server!");
		}
		if (beansOfType.size() > 1) {
			throw new IllegalArgumentException("超过限制的server数量！");
		}
		// 返回服务
		return beansOfType.values().stream().findFirst().get();
	}

	// 1.0版本 2.0版本已经前一到TomcatServer里面了
//	private static void startTomcat(WebApplicationContext webApplicationContext) {
//		Tomcat tomcat = new Tomcat();
//
//		Server server = tomcat.getServer();
//		Service service = server.findService("Tomcat");
//
//		Connector connector = new Connector();
//		connector.setPort(8081);
//
//		Engine engine = new StandardEngine();
//		engine.setDefaultHost("localhost");
//
//		Host host = new StandardHost();
//		host.setName("localhost");
//
//		String contextPath = "";
//		Context context = new StandardContext();
//		context.setPath(contextPath);
//		context.addLifecycleListener(new Tomcat.FixContextListener());
//
//		host.addChild(context);
//		engine.addChild(host);
//
//		service.setContainer(engine);
//		service.addConnector(connector);
//
//		tomcat.addServlet(contextPath, "dispatcher", new DispatcherServlet(webApplicationContext));
//		context.addServletMappingDecoded("/*", "dispatcher");
//
//		try {
//			tomcat.start();
//		} catch (LifecycleException e) {
//			e.printStackTrace();
//		}
//	}

}
