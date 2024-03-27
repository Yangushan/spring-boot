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

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * created by yangushan
 * 2024/3/25 20:03
 */
@Deprecated // 已经优化为了OnCondition的方式,WebServiceOnCondition
public class TomcatCondition implements Condition {
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		// 这里的逻辑就是要判断系统中是否导入了tomcat的包，所以这里只要判断是否有tomcat相关的类被加载出来就可以了
		try {
			context.getClassLoader().loadClass("org.apache.catalina.startup.Tomcat");
			return true;
		}
		catch (ClassNotFoundException e) {
			// 报错表示没有引入这个包，那么就直接返回false
			return false;
		}
	}
}
