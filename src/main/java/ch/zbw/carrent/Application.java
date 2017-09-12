package ch.zbw.carrent;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.internal.org.objectweb.asm.TypeReference;
import ch.zbw.model.*;

import java.util.Arrays;
import java.util.List;
import jdk.internal.org.objectweb.asm.*;


@TestPropertySource(properties = "debug=true")
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	
	@Bean
	CommandLineRunner init(KundeRepository accountRepository) {
		return (evt) -> Arrays.asList(
				"hans, wurst".split(","))
				.forEach(
						a -> {
							//Kunde k = accountRepository.save(new Kunde(a, "test", "street1", 9545, "Wängi"));
						});
	}

}
