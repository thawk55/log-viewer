package tyler.hawkins;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("tyler.hawkins.controllers")
public class LogViewerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogViewerApplication.class, args);
	}
}
