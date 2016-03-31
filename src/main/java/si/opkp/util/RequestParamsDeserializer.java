package si.opkp.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestParamsDeserializer extends JsonDeserializer<RequestParams> {

	public RequestParamsDeserializer() {
	}

	@Override
	public RequestParams deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
		Map<String, Object> map = jsonParser.readValuesAs(HashMap.class)
														.next();

		long skip = 0;
		long take = 0;

		if (map.get("skip") instanceof Integer) {
			skip = ((Integer) map.get("skip"));
		} else if (map.get("skip") instanceof Long) {
			skip = ((Long) map.get("skip"));
		}

		if (map.get("take") instanceof Integer) {
			take = (Integer) map.get("take");
		} else if (map.get("take") instanceof Long) {
			take = ((Long) map.get("take"));
		}

		return new RequestParams((List<String>) map.get("fields"),
				(List<String>) map.get("sort"),
				(List<String>) map.get("group"),
				(String) map.get("where"),
				skip,
				take);
	}

}