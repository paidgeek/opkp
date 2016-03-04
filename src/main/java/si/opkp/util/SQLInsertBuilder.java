package si.opkp.util;

import java.util.ArrayList;
import java.util.List;

public class SQLInsertBuilder {

   private StringBuilder query;
   private List<Pair<String, Object>> values;

   public SQLInsertBuilder(String table) {
      query = new StringBuilder();
      values = new ArrayList<>();

      query.append("INSERT INTO ");
      query.append(table);
   }

   public SQLInsertBuilder insert(String column, Object value) {
      values.add(new Pair<>(column, value));

      return this;
   }

   public String build() {
      query.append("(");

      for (int i = 0; i < values.size(); i++) {
         String column = values.get(i).getFirst();

         query.append("`");
         query.append(column);
         query.append("`");

         if (i < values.size() - 1) {
            query.append(", ");
         }
      }

      query.append(")\nVALUES(");

      for (int i = 0; i < values.size(); i++) {
         Object value = values.get(i).getSecond();

         if (value instanceof String) {
            query.append("'");
            query.append(value);
            query.append("'");
         } else {
            query.append(value);
         }

         if (i < values.size() - 1) {
            query.append(", ");
         }
      }

      query.append(")\n");

      return query.toString();
   }

}
