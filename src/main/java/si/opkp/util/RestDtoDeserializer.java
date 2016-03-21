package si.opkp.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestDtoDeserializer extends JsonDeserializer<RestDto> {

	public RestDtoDeserializer() {
	}

	@Override
	public RestDto deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
		Map<String, Object> map = jsonParser.readValuesAs(HashMap.class).next();

		return new RestDto((List<String>) map.get("columns"),
				(List<String>) map.get("sort"),
				(String) map.get("query"),
				(List<Integer>) map.get("limit"),
				(List<String>) map.get("keywords"),
				(String) map.get("language"));
	}

}