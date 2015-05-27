package com.deblox.myproject.test.integration.java;/*
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

import io.vertx.codetrans.annotations.CodeTranslate;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestOptions;
import io.vertx.ext.unit.TestSuite;
import io.vertx.ext.unit.report.ReportOptions;

import static org.vertx.testtools.VertxAssert.*;

/**
 * Example Java integration test that deploys the module that this project builds.
 *
 * Quite often in integration tests you want to deploy the same module for all tests and you don't want tests
 * to start before the module has been deployed.
 *
 * This test demonstrates how to do that.
 */
public class ModuleIntegrationTest {

  public static void main(String[] args) {
    new VertxUnitTest().run();
  }

  Vertx vertx;


  @CodeTranslate // Not yet detected
  public void run() {

    TestOptions options = new TestOptions().addReporter(new ReportOptions().setTo("console"));
    TestSuite suite = TestSuite.create("com.deblox.myproject.PingVerticle");

    suite.before(context -> {
      vertx = Vertx.vertx();
      Async async = context.async();
      HttpServer server =
              vertx.createHttpServer().requestHandler(req -> req.response().end("foo")).listen(8080, res -> {
                if (res.succeeded()) {
                  async.complete();
                } else {
                  context.fail();
                }
              });
    });

    suite.after(context -> {
      Async async = context.async();
      vertx.close(ar -> {
        if (ar.succeeded()) {
          async.complete();
        } else if (ar.failed()) {
          context.fail();
        }
      });
    });

    // Specifying the test names seems ugly...
    suite.test("some_test1", context -> {
      // Send a request and get a response
      HttpClient client = vertx.createHttpClient();
      Async async = context.async();
      client.getNow(8080, "localhost", "/", resp -> {
        resp.bodyHandler(body -> context.assertEquals("foo", body.toString("UTF-8")));
        client.close();
        async.complete();
      });
    });
    suite.test("some_test2", context -> {
      // Deploy and undeploy a verticle
      Async async = context.async();
      vertx.deployVerticle("io.vertx.example.unit.SomeVerticle", res -> {
        if (res.succeeded()) {
          String deploymentID = res.result();
          vertx.undeploy(deploymentID, res2 -> {
            if (res2.succeeded()) {
              async.complete();
            } else {
              context.fail();
            }
          });
        } else {
          context.fail();
        }
      });
    });

    suite.run(options);

  }



//  public void testPing() {
//    container.logger().info("in testPing()");
//    vertx.eventBus().send("ping-address", "ping!", new Handler<Message<String>>() {
//      @Override
//      public void handle(Message<String> reply) {
//        assertEquals("pong!", reply.body());
//
//        /*
//        If we get here, the test is complete
//        You must always call `testComplete()` at the end. Remember that testing is *asynchronous* so
//        we cannot assume the test is complete by the time the test method has finished executing like
//        in standard synchronous tests
//        */
//        testComplete();
//      }
//    });
//  }
//
//  @Test
//  public void testSomethingElse() {
//    // Whatever
//    testComplete();
//  }
//
//
//  @Override
//  public void start() {
//    // Make sure we call initialize() - this sets up the assert stuff so assert functionality works correctly
//    initialize();
//
//    // test configuration
//    JsonObject config = new JsonObject().putArray("services", new JsonArray()
//            .add("com.deblox.myproject.PingVerticle"));
//    config.putObject("com.deblox.myproject.PingVerticle", new JsonObject().putString("foo", "bar"));
//
//
//    // Deploy the module - the System property `vertx.modulename` will contain the name of the module so you
//    // don't have to hardecode it in your tests
//    container.deployModule(System.getProperty("vertx.modulename"), config, new AsyncResultHandler<String>() {
//      @Override
//      public void handle(AsyncResult<String> asyncResult) {
//        // Deployment is asynchronous and this this handler will be called when it's complete (or failed)
//        if (asyncResult.failed()) {
//          container.logger().error(asyncResult.cause());
//        }
//        assertTrue(asyncResult.succeeded());
//        assertNotNull("deploymentID should not be null", asyncResult.result());
//        // If deployed correctly then start the tests!
//        startTests();
//      }
//    });
//  }

}
