package tyler.hawkins;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@ComponentScan("tyler.hawkins.controllers")
public class LogViewerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogViewerApplication.class, args);
	}
}
