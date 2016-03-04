package si.opkp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import si.opkp.model.Database;
import si.opkp.util.Pojo;
import si.opkp.util.Util;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequestMapping("/v1/search")
@CrossOrigin
public class SearchController {

   private static SearchController instance;

   public static SearchController getInstance() {
      return instance;
   }

   @Autowired
   private Database db;

   @PostConstruct
   private void init() {
      instance = this;
   }

   @RequestMapping(value = "/{model}", method = RequestMethod.GET)
   public ResponseEntity<Pojo> get(@PathVariable("model") String model,
                                   @RequestParam(name = "columns", required = false, defaultValue = "*") String columnList,
                                   @RequestParam(name = "keywords", required = true) String keywordList,
                                   @RequestParam(name = "limit", required = false) String limitList) {
      List<String> columns = Util.parseStringList(columnList);
      List<String> keywords = Util.parseStringList(keywordList);
      List<Long> limit = Util.parseLongList(limitList);

      return perform(model, columns, keywords, limit);
   }

   public ResponseEntity<Pojo> perform(String model, List<String> columns, List<String> keywords, List<Long> limit) {
      String kw = String.join(" ", keywords);

      List<Pojo> objects;
      long offset = 0, count = Long.MAX_VALUE, total = 0;

      if (limit != null) {
         if (limit.size() == 1) {
            offset = limit.get(0);
         }

         if (limit.size() == 2) {
            count = limit.get(1);
         }
      }

      // TODO clean up
      if (model.equals("fir_food")) {
         objects = db.queryObjects("CALL search_foods(?, ?, ?)", kw, offset, count);
      } else {
         return Util.responseError("invalid model", HttpStatus.BAD_REQUEST);
      }

      if (!objects.isEmpty()) {
         total = objects.get(0).getLong("total");
      }

      if (!(columns.size() == 1 && columns.contains("*"))) {
         for (int i = 0; i < objects.size(); i++) {
            Pojo pojo = objects.get(i);

            pojo.getProperties().entrySet().removeIf(e -> !columns.contains(e.getKey()));
         }
      }

      Pojo result = new Pojo();
      Pojo meta = new Pojo();

      meta.setProperty("count", objects.size());
      meta.setProperty("total", total);

      result.setProperty("meta", meta);
      result.setProperty("result", objects);

      return ResponseEntity.ok(result);
   }

}
