package com.deblox;

import io.vertx.core.json.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Util {

  /*
  Config loader
   */
  static public JsonObject loadConfig(String file) throws IOException {
    System.out.println("loading file: " + file);
    try (InputStream stream = Util.class.getClass().getResourceAsStream(file)) {
      StringBuilder sb = new StringBuilder();
      BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));

      String line = reader.readLine();
      while (line != null) {
        sb.append(line).append('\n');
        line = reader.readLine();
        System.out.println(line);
      }

      return new JsonObject(sb.toString());

    } catch (IOException e) {
      System.out.println("Unable to load config file: " + file);
      e.printStackTrace();
      throw new IOException("Unable to open file: " + file );
    }

  }
}