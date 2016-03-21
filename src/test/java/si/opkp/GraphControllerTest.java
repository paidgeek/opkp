package si.opkp;

import org.junit.*;
import org.junit.runner.*;
import org.springframework.boot.test.*;
import org.springframework.context.annotation.*;
import org.springframework.test.context.junit4.*;
import org.springframework.test.context.web.WebAppConfiguration;

import si.opkp.controller.GraphController;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class GraphControllerTest {

	private GraphController graph;

	@Before
	public void setup() {
		graph = GraphController.getInstance();
	}

	@Test
	public void simple() throws Exception {
	}

}
