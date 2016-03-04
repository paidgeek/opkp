package si.opkp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import si.opkp.batch.Batch;
import si.opkp.batch.Command;
import si.opkp.model.Database;
import si.opkp.util.Pojo;
import si.opkp.util.Util;
import si.opkp.util.Validator;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/v1/batch")
@CrossOrigin
public class BatchController {

   @Autowired
   private Database db;

   @RequestMapping(method = RequestMethod.POST)
   public ResponseEntity<Pojo> post(@RequestBody Batch batch) {
      String errorMessage = Validator.validate(batch);

      if (errorMessage != null) {
         return Util.responseError(errorMessage, HttpStatus.BAD_REQUEST);
      }

      try {
         Pojo result = new Pojo();
         List<Command> commands = batch.sortedCommands();
         Set<String> failed = new HashSet<>();

         for (int i = 0; i < commands.size(); i++) {
            Command command = commands.get(i);
            Optional<String> failedDependency = command.getDependencies()
                  .stream()
                  .filter(failed::contains)
                  .findAny();

            if (failedDependency.isPresent()) {
               failed.add(command.getName());

               result.setProperty(command.getName(), Util.createError(
                     String.format("command '%s' was terminated, because '%s' failed",
                           command.getName(),
                           failedDependency.get())));

               continue;
            }

            String controller = command.getController();
            String model = command.getModel();

            ResponseEntity<Pojo> response;

            if (controller.equals("path")) {
               response = PathController.getInstance()
                     .perform(model,
                           command.getColumns(),
                           command.getQuery(),
                           command.getSort(),
                           command.getLimit());
            } else if (controller.equals("get")) {
               response = SearchController.getInstance()
                     .perform(model,
                           command.getColumns(),
                           command.getKeywords(),
                           command.getLimit());
            } else {
               failed.add(command.getName());
               result.setProperty(command.getName(), Util.createError("invalid controller"));

               continue;
            }

            if (response.getStatusCode() != HttpStatus.OK) {
               failed.add(command.getName());
               result.setProperty(command.getName(), response.getBody());

               continue;
            }

            result.setProperty(command.getName(), response.getBody());
         }

         return ResponseEntity.ok(result);
      } catch (Exception e) {
         e.printStackTrace();

         return Util.responseError(HttpStatus.BAD_REQUEST);
      }
   }

}
