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

package org.yangushan.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * created by yangushan
 * 2024/3/26 12:31
 */
@RestController()
@RequestMapping("/user")
public class UserController {

	// spring的org.springframework.boot.env.RandomValuePropertySourceEnvironmentPostProcessor，会帮我们进行解析
	@Value("${random.int,100}")
	private Integer r;

	@GetMapping("/test")
	public String test() {
		return "test";
	}

	@GetMapping("/random")
	public Integer random() {
		return this.r;
	}

}
