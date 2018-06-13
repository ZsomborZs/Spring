package core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Main entry point
 */
@SpringBootApplication
public class Application {
	public static SI_Service gateway;

	public static void main(String[] args) {
		// Spring integration
		ApplicationContext context = new ClassPathXmlApplicationContext("/si-config.xml");
		gateway = (SI_Service) context.getBean("SI_Service", SI_Service.class);

		// Spring boot
		SpringApplication.run(Application.class, args);
	}
}
