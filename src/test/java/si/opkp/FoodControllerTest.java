package si.opkp;

import org.junit.*;
import org.junit.runner.*;
import org.springframework.boot.test.*;
import org.springframework.context.annotation.*;
import org.springframework.test.context.junit4.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ImportResource("beans.xml")
public class FoodControllerTest {

	@Before
	public void setup() {
	}

	@Test
	public void simple() throws Exception {
	}

}
