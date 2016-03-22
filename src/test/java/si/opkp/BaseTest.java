package si.opkp;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringApplicationConfiguration(classes = {Application.class})
@ContextHierarchy({
		@ContextConfiguration(name = "testContext", classes = Application.class)
})
@WebAppConfiguration
public abstract class BaseTest {

}
