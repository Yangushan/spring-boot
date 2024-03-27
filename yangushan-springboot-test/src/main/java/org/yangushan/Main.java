package org.yangushan;


import org.yangushan.configuration.MyAutoSelectImport;
import org.yangushan.configuration.MyTypeExcludeFilter;
import org.yangushan.entity.User;
import org.yangushan.mapper.UserMapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
@SpringBootApplication
//@Import(MyConfiguration.class) // 改为使用下面的方式
@Import(MyAutoSelectImport.class)
// @MapperScan // 这里为什么不建议加入这个参数，是因为mybatis spring bootstarter里面已经扫描过当前包下面的所有mapper了
// 如果加入了则会多扫描一次，但是那个包里面设置的mapper必须加上@Mapper注解才可以
// 而我们原生的@MapperScan不需要加入注解，扫描到包下面所有的接口都会当作是mapper，这就会造成原生mapper上的使用误解了
// 所以最好是可以不加入这个注解，然后我们在使用我们的mapper的时候都加上注解
public class Main {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Main.class);
		User user = (User) context.getBean("user");
		// 这里会输出main
		System.out.println(user.getName());

		UserMapper userMapper = (UserMapper) context.getBean("userMapper");
		System.out.println(userMapper.selectTest());

		// 使用@Bean的方式定义bean: 这里应该报错说找不到bean，但是依旧有数据，说明我们的MyTypeExcludeFilter没有生效
		// 改为ApplicationContextInitializer的方式之后生效，会正常报错
//		System.out.println(context.getBean("orderController"));

	}

	// 如果这里定义一个bean和我们MyConfiguration里面就冲突了，但是为什么会报错？

	/**
	 * 我们明明设置了conditional为什么失效了？这和Spring的加载顺序有关系
	 * Spring在解析当前配置类的时候，解析到scan之类的，这个时候只会加载到beanDefinition，并不会生成对应的bean
	 * 然后就会去解析这个对应的import，发现了Import里面有bean，生成对应的bean，但是其实这个时候我们的bean还没创建好，所以conditional的条件不生效
	 * 所以会报错，所以这个时候有一个selectimport的子类就派上用场了，延迟加载子类
	 * @return
	 */
	@Bean
	public User user() {
		User user = new User();
		user.setName("main");
		return user;
	}

	/**
	 * 这个bean没有生效的原因和上面是一样的，因为Spring加载bean的顺序问题
	 * 因为先判断了@ComponentScan上面的注解的时候，我们的MyTypeExcludeFilter bean还没识别到
	 * 所以这里要让她提前生效的话，就需要使用到org.springframework.context.ApplicationContextInitializer
	 * 但是这种方式需要声明spring.factories
	 * @return
	 */
//	@Bean
	public MyTypeExcludeFilter myTypeExcludeFilter() {
		return new MyTypeExcludeFilter();
	}

}