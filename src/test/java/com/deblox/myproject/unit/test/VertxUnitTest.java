package com.deblox.myproject.unit.test;

//import io.vertx.codetrans.annotations.CodeTranslate;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestOptions;
import io.vertx.ext.unit.TestSuite;
import io.vertx.ext.unit.report.ReportOptions;

/*
 * Example of asynchronous unit test written in raw vertx-unit style
 *
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class VertxUnitTest {


  public static void main(String[] args) {
    new VertxUnitTest().run();
  }

  Vertx vertx;

//  @CodeTranslate // Not yet detected
  public void run() {

    TestOptions options = new TestOptions().addReporter(new ReportOptions().setTo("console"));
    TestSuite suite = TestSuite.create("com.deblox.myproject.unit.VertxUnitTest");

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
      vertx.deployVerticle("com.deblox.myproject.PingVerticle", res -> {
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


}