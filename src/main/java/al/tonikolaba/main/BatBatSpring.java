package al.tonikolaba.main;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class BatBatSpring {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = new SpringApplicationBuilder(BatBatSpring.class).headless(false)
				.run(args);
		@SuppressWarnings("unused")
		BatBatGame appFrame = context.getBean(BatBatGame.class);
	}
}
