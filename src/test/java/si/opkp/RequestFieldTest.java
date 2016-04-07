package si.opkp;

import com.moybl.restql.RestQL;
import com.moybl.restql.ast.Sequence;

import org.junit.Test;

import si.opkp.util.RequestField;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

public class RequestFieldTest {

	@Test
	public void simple() {
		RequestField field = RequestField.fromAst(((Sequence) RestQL.parse("user")
																						.getElements()
																						.get(0)).getElements()
																								  .get(0));

		assertNotNull(field);
		assertEquals("user", field.getName());
	}

	@Test
	public void member() {
		RequestField field = RequestField.fromAst(((Sequence) RestQL.parse("user.name")
																						.getElements()
																						.get(0)).getElements()
																								  .get(0));

		assertNotNull(field);
		assertEquals("name", field.getName());
		assertEquals("user", field.getNode());
	}

	@Test
	public void edge() {
		RequestField field = RequestField.fromAst(((Sequence) RestQL.parse("!posts(date, content)")
																						.getElements()
																						.get(0)).getElements()
																								  .get(0));

		assertNotNull(field);
		assertEquals("posts", field.getName());
		assertTrue(field.isEdge());

		assertEquals("date", field.getFields()
										  .get(0)
										  .getName());
		assertEquals("content", field.getFields()
											  .get(1)
											  .getName());
	}

	@Test
	public void edgeDeep() {
		RequestField field = RequestField.fromAst(((Sequence) RestQL.parse("!posts(date, content, !owner(name))")
																						.getElements()
																						.get(0)).getElements()
																								  .get(0));

		assertNotNull(field);
		assertEquals("posts", field.getName());
		assertTrue(field.isEdge());

		assertEquals("date", field.getFields()
										  .get(0)
										  .getName());
		assertEquals("content", field.getFields()
											  .get(1)
											  .getName());
		assertEquals("owner", field.getFields()
											.get(2)
											.getName());

		assertTrue(field.getFields()
							 .get(2)
							 .isEdge());
		assertEquals("name", field.getFields()
										  .get(2)
										  .getFields()
										  .get(0)
										  .getName());
	}

}
