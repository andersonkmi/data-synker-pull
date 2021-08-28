package org.codecraftlabs.octo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
public class OctoApiApplicationTests {

	// todo: Re-enable when db container is running locally
	@Disabled
	void contextLoads() {
	}

}
