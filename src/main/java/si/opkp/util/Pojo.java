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

	public void addProperties(Map<String, Object> properties) {
		this.properties.putAll(properties);
	}

	public Object getProperty(String name) {
		return properties.get(name);
	}

	public int getInteger(String name) {
		return (Integer) properties.get(name);
	}

	public long getLong(String name) {
		Object obj = properties.get(name);

		if (obj instanceof Integer) {
			return ((long) (Integer) obj);
		}

		return (Long) obj;
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
	public int hashCode() {
		return properties.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Pojo)) {
			return false;
		}

		Pojo other = (Pojo) obj;

		for (Map.Entry<String, Object> prop : properties.entrySet()) {
			if (!other.properties.containsKey(prop.getKey())) {
				return false;
			}

			if (!other.getProperty(prop.getKey())
						 .equals(getProperty(prop.getKey()))) {
				return false;
			}
		}

		return true;
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

		private Pojo pojo;

		public Builder() {
			pojo = new Pojo();
		}

		public Builder property(String name, Object value) {
			pojo.setProperty(name, value);

			return this;
		}

		public Pojo build() {
			return pojo;
		}

	}

}
