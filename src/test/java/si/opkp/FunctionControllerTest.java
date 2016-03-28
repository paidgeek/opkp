package si.opkp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import si.opkp.controller.Router;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
		@ContextConfiguration(name = "testContext", classes = TestData.class)
})
public class FunctionControllerTest extends BaseTest {

	@Autowired
	private Router router;

	@Test
	public void ping() {
		/*
		ResponseEntity<Pojo> response = router.get("call", "ping", null);

		assertEquals(HttpStatus.OK, response.getStatusCode());

		List<Pojo> result = ((List<Pojo>) response.getBody()
																.getProperty("result"));
		Pojo meta = (Pojo) response.getBody()
											.getProperty("meta");

		assertEquals("pong", result.get(0)
											.getString("ping"));
		assertEquals(1, meta.getLong("count"));
		assertEquals(1, meta.getLong("total"));
		*/
	}

}
