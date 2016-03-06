package si.opkp.util;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.*;
import com.fasterxml.jackson.databind.jsontype.*;
import sun.reflect.generics.reflectiveObjects.*;

import java.io.*;
import java.util.*;

@JsonDeserialize(using = PojoDeserializer.class)
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

	public Integer getInteger(String name) {
		return (Integer) properties.get(name);
	}

	public Long getLong(String name) {
		return (Long) properties.get(name);
	}

	public String getString(String name) {
		return (String) properties.get(name);
	}

	public Boolean getBoolean(String name) {
		return (Boolean) properties.get(name);
	}

	public void setProperty(String name, Object value) {
		properties.put(name, value);
	}

	public void removeProperty(String name) {
		properties.remove(name);
	}

	public Map<String, Object> getProperties() {
		return properties;
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
