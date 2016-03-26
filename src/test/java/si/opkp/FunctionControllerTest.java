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

import si.opkp.controller.FunctionController;
import si.opkp.util.Pojo;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
		@ContextConfiguration(name = "testContext", classes = TestData.class)
})
public class FunctionControllerTest extends BaseTest {

	@Autowired
	private FunctionController functionController;

	@Test
	public void simple() {
		ResponseEntity<Pojo> response = functionController.call("ping", null);

		assertEquals(HttpStatus.OK, response.getStatusCode());

		Pojo result = ((List<Pojo>) response.getBody()
														.getProperty("result")).get(0);
		Pojo meta = (Pojo) response.getBody()
											.getProperty("meta");

		assertEquals(1, meta.getLong("count"));
		assertEquals("pong", result.getString("ping"));
	}

}
