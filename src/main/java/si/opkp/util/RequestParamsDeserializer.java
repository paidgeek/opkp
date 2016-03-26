package si.opkp.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import si.opkp.query.Field;

public class RequestParamsDeserializer extends JsonDeserializer<RequestParams> {

	public RequestParamsDeserializer() {
	}

	@Override
	public RequestParams deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
		Map<String, Object> map = jsonParser.readValuesAs(HashMap.class)
														.next();

		RequestParams params = new RequestParams();

		if (map.containsKey("fields")) {
			List<Field> fields = ((List<String>) map.get("fields")).stream()
																					 .map(field -> new Field(field))
																					 .collect(Collectors.toList());

			params.setFields(Util.listToArray(fields));
		}

		if (map.containsKey("sort")) {
			List<Field> sort = ((List<String>) map.get("sort")).stream()
																				.map(field -> new Field(field))
																				.collect(Collectors.toList());

			params.setSort(Util.listToArray(sort));
		}

		if (map.containsKey("group")) {
			List<Field> group = ((List<String>) map.get("group")).stream()
																				  .map(field -> new Field(field))
																				  .collect(Collectors.toList());
			params.setGroup(Util.listToArray(group));
		}

		return params;
	}

}
