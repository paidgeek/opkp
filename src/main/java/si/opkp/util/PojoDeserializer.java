package si.opkp.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.HashMap;

public class PojoDeserializer extends JsonDeserializer<Pojo> {

	@Override
	public Pojo deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
		HashMap map = jsonParser.readValuesAs(HashMap.class).next();

		return new Pojo(map);
	}

}
