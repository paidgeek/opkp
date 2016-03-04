package si.opkp.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import si.opkp.Application;

import java.util.HashMap;
import java.util.Map;

public class RelationMap {

   private static RelationMap instance;
   private Map<String, Map<String, String>> map;

   private RelationMap() {
      try {
         instance = this;

         map = new ObjectMapper().readValue(Application.getContext()
                     .getResource("classpath:relationships.json")
                     .getInputStream(),
               HashMap.class);
      } catch (Exception e) {
         e.printStackTrace();
         System.exit(1);
      }
   }

   public static RelationMap getInstance() {
      if (instance == null) {
         synchronized (RelationMap.class) {
            if (instance == null) {
               instance = new RelationMap();

               return instance;
            }
         }
      }

      return instance;
   }

   public String getEdge(String a, String b) {
      if (map.containsKey(a)) {
         return map.get(a).get(b);
      } else if (map.containsKey(b)) {
         return map.get(b).get(a);
      }

      return null;
   }

}
