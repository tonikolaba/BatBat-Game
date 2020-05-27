package al.tonikolaba.main;

import java.awt.EventQueue;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "al.tonikolaba.main")
//@Controller
@EnableAutoConfiguration
public class BatBatSpring {
	/**
	 * 
	 */

	public static void main(String[] args) {

		ConfigurableApplicationContext context = new SpringApplicationBuilder(BatBatSpring.class).headless(false)
				.run(args);

		EventQueue.invokeLater(() -> {
			@SuppressWarnings("unused")
			BatBatGame window = context.getBean(BatBatGame.class);
			// window.setVisible(true);
		});
	}
	/*
	 * @GetMapping("/") public String dashboard() { return "index"; }
	 */

}
