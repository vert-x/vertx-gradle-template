package com.deblox.myproject.unit.test;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.impl.LoggerFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.deblox.myproject.PingVerticle;

/*
 * Example of an asynchronous unit test written in JUnit style using vertx-unit
 *
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@RunWith(VertxUnitRunner.class)
public class PingVerticleTest {

  Vertx vertx;
  EventBus eb;
  private static final Logger logger = LoggerFactory.getLogger(PingVerticleTest.class);

  @Before
  public void before(TestContext context) {
    logger.info("@Before");
    vertx = Vertx.vertx();
    eb = vertx.eventBus();

    Async async = context.async();
    vertx.deployVerticle(PingVerticle.class.getName(), res -> {
      if (res.succeeded()) {
        async.complete();
      } else {
        context.fail();
      }
    });
  }

  @After
  public void after(TestContext context) {
    logger.info("@After");
    Async async = context.async();

    // the correct way after next release
    //vertx.close(context.assertAsyncSuccess());

    vertx.close( event -> {
      async.complete();
    });

  }

  @Test
  public void test(TestContext test) {
    Async async = test.async();
    eb.send("ping-address", "ping!", reply -> {
      if (reply.succeeded()) {
        async.complete();
      } else {
        test.fail();
      }
    });

  }
}