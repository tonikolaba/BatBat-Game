package al.tonikolaba.main;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author N.Kolaba
 *
 */

@Configuration
@ComponentScan
@EnableAutoConfiguration
@RunWith(JUnitPlatform.class)
@DisplayName("Spring Boot Load")
public class BatBatSpringTests {

	@Test
	void contextLoads() {
	}
}
