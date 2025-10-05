package za.co.shyftit.capitectransactionaggregator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class CapitecTransactionAggregatorApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(CapitecTransactionAggregatorApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(CapitecTransactionAggregatorApplication.class, args);
	}

}
