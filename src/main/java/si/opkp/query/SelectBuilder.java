package si.opkp.query;

import com.moybl.restql.ast.AstNode;
import com.moybl.restql.ast.Sequence;

public interface SelectBuilder {

	SelectBuilder fields(Sequence fields);

	SelectBuilder from(AstNode model);

	SelectBuilder join(AstNode model, AstNode conditionBuilder);

	SelectBuilder where(AstNode conditionBuilder);

	SelectBuilder sort(Sequence fields);

	SelectBuilder group(Sequence fields);

	SelectBuilder skip(long skip);

	SelectBuilder take(long take);

	String build();

}
