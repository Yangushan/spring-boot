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

package org.yangushan.listener;

import java.time.Duration;

import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * 这是SpringBoot帮我们实现的一个listener扩展点，如果需要对Spring相关事件的监听
 * 需要实现这个listener，然后还需要在项目中META-INF下面增加spring.factories配置
 * created by yangushan
 * 2024/3/26 12:33
 */
public class MySpringApplicationRunListener implements SpringApplicationRunListener {

	private SpringApplication application;
	private String[] args;

	/**
	 * 这里需要注意的是，当Spring帮我们new这个对象的时候，不会去调用默认的构造方法，会去找有SpringApplication的构造方法
	 * 可以根据报错提示对应创建不同的构造方法
	 * @param application
	 * @param args
	 */
	public MySpringApplicationRunListener(SpringApplication application, String[] args) {
		this.application = application;
		this.args = args;
	}

	@Override
	public void starting(ConfigurableBootstrapContext bootstrapContext) {
		System.out.println("MySpringApplicationRunListener starting");
	}

	@Override
	public void environmentPrepared(ConfigurableBootstrapContext bootstrapContext, ConfigurableEnvironment environment) {
		SpringApplicationRunListener.super.environmentPrepared(bootstrapContext, environment);
	}

	@Override
	public void contextPrepared(ConfigurableApplicationContext context) {
		SpringApplicationRunListener.super.contextPrepared(context);
	}

	@Override
	public void contextLoaded(ConfigurableApplicationContext context) {
		SpringApplicationRunListener.super.contextLoaded(context);
	}

	@Override
	public void started(ConfigurableApplicationContext context, Duration timeTaken) {
		SpringApplicationRunListener.super.started(context, timeTaken);
	}

	@Override
	public void started(ConfigurableApplicationContext context) {
		SpringApplicationRunListener.super.started(context);
	}

	@Override
	public void ready(ConfigurableApplicationContext context, Duration timeTaken) {
		SpringApplicationRunListener.super.ready(context, timeTaken);
	}

	@Override
	public void running(ConfigurableApplicationContext context) {
		SpringApplicationRunListener.super.running(context);
	}

	@Override
	public void failed(ConfigurableApplicationContext context, Throwable exception) {
		SpringApplicationRunListener.super.failed(context, exception);
	}
}
