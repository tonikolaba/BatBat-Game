/** Copyright to N.Kolaba
All rights reserved ©.
**/

package al.artofsoul.batbatgame.main;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BatBat implements CommandLineRunner {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(BatBat.class, args);
		SpringApplication app = new SpringApplication(BatBat.class);
		app.run(args);
	}

	// access command line arguments
	@Override
	public void run(String... args) throws Exception {
		// LOGGER.info("----------BatBat Game----------");
		System.out.println("Spring boot standalone application is working...");
		System.out.println("java -jar target/BatBat-Game.jar");
		// do something
	}
}
