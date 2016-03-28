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

		return new RequestParams((List<String>) map.get("fields"),
				(List<String>) map.get("sort"),
				(List<String>) map.get("group"),
				(String) map.get("where"),
				(Integer) map.get("skip"),
				(Integer) map.get("take"));
	}

}