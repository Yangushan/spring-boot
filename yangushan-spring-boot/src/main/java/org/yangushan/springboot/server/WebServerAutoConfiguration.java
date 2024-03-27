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

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;

/**
 * 帮我们自动导入我们server bean的一个配置类，但是这里有一个问题，就是我们并不是让bean直接生效而是有一定条件的，所以需要加上对应的condition
 * created by yangushan
 * 2024/3/25 20:01
 */
public class WebServerAutoConfiguration {

	/**
	 * 由于Conditional这种方式，会产生2个不同的condition，而且如果多一个服务器类型又要多一个子类
	 * 所以这里决定抽象成另外的一种方式，OnConditionClass的方式
	 * @return
	 */


	@Bean
//	@Conditional(value = TomcatCondition.class)
	@MyWebServerOnConditional("org.apache.catalina.startup.Tomcat")
	public TomcatServer tomcatServer() {
		TomcatServer tomcatServer = new TomcatServer();
		return tomcatServer;
	}

	@Bean
//	@Conditional(value = JettyCondition.class)
	@MyWebServerOnConditional("org.eclipse.jetty.server.Server")
	public JettyServer jettyServer() {
		JettyServer jettyServer = new JettyServer();
		return jettyServer;
	}

}
