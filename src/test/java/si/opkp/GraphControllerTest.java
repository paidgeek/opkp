package si.opkp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import si.opkp.controller.Router;
import si.opkp.util.Pojo;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
		@ContextConfiguration(name = "testContext", classes = TestData.class)
})
public class GraphControllerTest extends BaseTest {

	@Autowired
	private Router router;

	@Test
	public void singleNode() {
		ResponseEntity<Pojo> response = router.get("graph", "A", new RequestParamsBuilder()
				.take(10)
				.build());

		assertEquals(HttpStatus.OK, response.getStatusCode());

		List<Pojo> result = ((List<Pojo>) response.getBody()
																.getProperty("result"));
		Pojo meta = (Pojo) response.getBody()
											.getProperty("meta");

		assertEquals(10, meta.getLong("count"));
		assertEquals(200, meta.getLong("total"));
	}

}
