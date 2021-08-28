package org.codecraftlabs.octo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
public class OctoApiApplicationTests {

	//$ docker image build -t octo/octodb:latest .
	//$ docker container run --detach --name octodb --publish 5432:5432 -v postgres-volume:/var/lib/postgresql/data octo/octodb:latest
	@Test
	void contextLoads() {
	}

}
