package si.opkp.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Pojo implements JsonSerializable {

	private Map<String, Object> properties;

	public Pojo() {
		properties = new HashMap<>();
	}

	public Pojo(Map<String, Object> properties) {
		this.properties = properties;
	}

	public Object getProperty(String name) {
		return properties.get(name);
	}

	public void setProperty(String name, Object value) {
		properties.put(name, value);
	}

	@Override
	public void serialize(JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
		jsonGenerator.writeStartObject();

		for (Map.Entry<String, Object> e : properties.entrySet()) {
			jsonGenerator.writeObjectField(e.getKey(), e.getValue());
		}

		jsonGenerator.writeEndObject();
	}

	@Override
	public void serializeWithType(JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException {
		throw new NotImplementedException();
	}

	public static class Builder {

		private Map<String, Object> properties;

		public Builder() {
			properties = new HashMap<>();
		}

		public Builder setProperty(String name, Object value) {
			properties.put(name, value);

			return this;
		}

		public Pojo build() {
			return new Pojo(properties);
		}

	}

}
