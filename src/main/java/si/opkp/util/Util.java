package si.opkp.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Util {

   private Util() {
   }

   public static List<String> parseStringList(String stringList) {
      if (stringList == null || stringList.isEmpty()) {
         return null;
      }

      return Arrays.asList(stringList.split(","));
   }

   public static List<Long> parseLongList(String integerList) {
      if (integerList == null || integerList.isEmpty()) {
         return null;
      }

      List<Long> list = new ArrayList<>();
      String[] ints = integerList.split(",");

      for (int i = 0; i < ints.length; i++) {
         list.add(Long.parseLong(ints[i]));
      }

      return list;
   }

   public static <T> T[] listToArray(List<T> list) {
      T[] array = (T[]) new Object[list.size()];

      list.toArray(array);

      return array;
   }

   public static List<Integer> integerList(int... array) {
      List<Integer> list = new ArrayList<>();

      for (int i = 0; i < array.length; i++) {
         list.add(array[i]);
      }

      return list;
   }

   public static List<String> stringList(String... array) {
      List<String> list = new ArrayList<>();

      for (int i = 0; i < array.length; i++) {
         list.add(array[i]);
      }

      return list;
   }

   public static Pojo createError(String message) {
      Pojo error = new Pojo();

      error.setProperty("message", message);

      return error;
   }

   public static ResponseEntity<Pojo> responseError(HttpStatus httpStatus) {
      return responseError("undefined error", httpStatus);
   }

   public static ResponseEntity<Pojo> responseError(String message, HttpStatus httpStatus) {
      return new ResponseEntity(createError(message), httpStatus);
   }

}
