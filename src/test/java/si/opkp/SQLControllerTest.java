package si.opkp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import si.opkp.controller.SQLController;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ImportResource("beans.xml")
public class SQLControllerTest {

	@Autowired
	private SQLController sqlController;

	@Before
	public void setup() {
	}

	@Test
	public void simple() throws Exception {
	}

}
