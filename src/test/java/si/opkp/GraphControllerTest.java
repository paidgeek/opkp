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

import si.opkp.controller.GraphController;
import si.opkp.util.Pojo;
import si.opkp.util.RequestParams;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
		@ContextConfiguration(name = "testContext", classes = TestData.class)
})
public class GraphControllerTest extends BaseTest {

	@Autowired
	private GraphController graphController;

	@Test
	public void fullQuery() {
		ResponseEntity<Pojo> response = graphController.perform("A", new RequestParams.Builder()
				.columns("VALUE")
				.query("VALUE~'%A%'")
				.sort("VALUE")
				.limit(0, 20)
				.build());

		assertEquals(HttpStatus.OK, response.getStatusCode());

		Pojo body = response.getBody();
		List<Pojo> result = (List<Pojo>) body.getProperty("result");
		Pojo meta = (Pojo) body.getProperty("meta");

		assertEquals(52, (long) meta.getLong("total"));
		assertEquals(20, (long) meta.getLong("count"));

		for (Pojo entry : result) {
			assertThat(entry.getString("VALUE"), containsString("A"));
		}
	}

}
