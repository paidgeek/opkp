package si.opkp;

import com.moybl.restql.Token;
import com.moybl.restql.ast.BinaryOperation;
import com.moybl.restql.ast.Identifier;
import com.moybl.restql.ast.Literal;
import com.moybl.restql.ast.Sequence;

import org.junit.Test;

import java.util.Arrays;

import si.opkp.query.QueryFactory;

import static junit.framework.Assert.assertEquals;

public class SQLSelectBuilderTest {

	@Test
	public void simple() {
		assertEquals("SELECT `x`, `y`\nFROM `a`\nWHERE (2 = 1)\nLIMIT 0, 100\n", QueryFactory.select()
																												 .from(new Identifier("a"))
																												 .fields(new Sequence(Arrays.asList(new Identifier("x"), new Identifier("y"))))
																												 .where(new BinaryOperation(Token.EQUAL, new Literal(2, Token.NUMBER), new Literal(1, Token.NUMBER)))
																												 .build());
	}

}
