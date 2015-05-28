package com.deblox.myproject.unit.util;

import com.deblox.DebloxRunner;
import static org.junit.Assert.*;

/*
 * Copyright 2013 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class Runner {

  private static final String UNIT_EXAMPLES_DIR = "deblox-vertx-template";
  private static final String UNIT_EXAMPLES_JAVA_DIR = UNIT_EXAMPLES_DIR + "/src/main/java/";
  private static final String UNIT_EXAMPLES_JS_DIR = UNIT_EXAMPLES_DIR + "/src/main/js/";
  private static final String UNIT_EXAMPLES_GROOVY_DIR = UNIT_EXAMPLES_DIR + "/src/main/groovy/";

  public static void runClustered(Class clazz) {
    DebloxRunner.runJava(UNIT_EXAMPLES_JAVA_DIR, clazz, true);
  }

  public static void run(Class clazz) {
    DebloxRunner.runJava(UNIT_EXAMPLES_JAVA_DIR, clazz, false);
  }

  // JavaScript examples

  public static void runJS(String scriptName) {
    DebloxRunner.runScript(UNIT_EXAMPLES_JS_DIR, scriptName, false);
  }

  public static void runJSExampleClustered(String scriptName) {
    DebloxRunner.runScript(UNIT_EXAMPLES_JS_DIR, scriptName, true);
  }

  static class JSVertxUnitTest {
    public static void main(String[] args) {
      Runner.runJS("io/vertx/example/unit/test/vertxunittest.js");
    }
  }

  // Groovy examples

  public static void runGroovy(String scriptName) {
    DebloxRunner.runScript(UNIT_EXAMPLES_GROOVY_DIR, scriptName, false);
  }

  public static void runGroovyExampleClustered(String scriptName) {
    DebloxRunner.runScript(UNIT_EXAMPLES_GROOVY_DIR, scriptName, true);
  }

  static class GroovyVertxUnitTest {
    public static void main(String[] args) {
      Runner.runGroovy("io/vertx/example/unit/test/vertxunittest.groovy");
    }
  }
}
