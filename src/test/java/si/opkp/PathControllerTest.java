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

import si.opkp.controller.PathController;
import si.opkp.util.Pojo;
import si.opkp.util.RequestParams;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
		@ContextConfiguration(name = "testContext", classes = TestData.class)
})
public class PathControllerTest extends BaseTest {

	@Autowired
	private PathController pathController;

	@Test
	public void longPath() {
		ResponseEntity<Pojo> response = pathController.perform("e", "a", new RequestParams.Builder()
				.limit(0, 10)
				.build());

		assertEquals(HttpStatus.OK, response.getStatusCode());

		Pojo body = response.getBody();
		List<Pojo> result = (List<Pojo>) body.getProperty("result");
		Pojo meta = (Pojo) body.getProperty("meta");

		assertEquals(200, (long) meta.getLong("total"));
		assertEquals(10, (long) meta.getLong("count"));
	}

}
