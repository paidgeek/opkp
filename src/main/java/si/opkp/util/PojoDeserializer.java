package si.opkp.util;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;

import java.io.*;
import java.util.*;

public class PojoDeserializer extends JsonDeserializer<Pojo> {

	@Override
	public Pojo deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
		HashMap map = jsonParser.readValuesAs(HashMap.class).next();

		return new Pojo(map);
	}

}
