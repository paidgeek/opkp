package si.opkp.batch;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import si.opkp.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Command {

   private String name;
   private String controller;
   private String model;
   private String query;
   private List<String> columns;
   private List<Long> limit;
   private List<String> sort;
   private List<String> keywords;
   private List<String> dependencies;

   @JsonCreator
   public Command(@JsonProperty("name") String name,
                  @JsonProperty("controller") String controller,
                  @JsonProperty("model") String model,
                  @JsonProperty("params") HashMap<String, Object> params,
                  @JsonProperty("dependencies") List<String> dependencies) {
      this.name = name == null ? "" : name;
      this.controller = controller == null ? "" : controller;
      this.model = model == null ? "" : model;
      this.dependencies = dependencies == null ? new ArrayList<>() : dependencies;

      query = (String) params.get("q");
      columns = (ArrayList) params.getOrDefault("columns", Util.stringList("*"));
      keywords = (ArrayList) params.getOrDefault("keywords", new ArrayList<String>());
      limit = (ArrayList) params.get("limit");
      sort = (ArrayList) params.get("sort");
   }

   public String getName() {
      return name;
   }

   public String getController() {
      return controller;
   }

   public String getModel() {
      return model;
   }

   public List<Long> getLimit() {
      return limit;
   }

   public List<String> getColumns() {
      return columns;
   }

   public List<String> getSort() {
      return sort;
   }

   public String getQuery() {
      return query;
   }

   public List<String> getKeywords() {
      return keywords;
   }

   public List<String> getDependencies() {
      return dependencies;
   }

}
