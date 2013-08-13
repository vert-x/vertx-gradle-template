package com.mycompany.myproject.test.integration.groovy

import org.vertx.groovy.platform.Verticle

import static org.vertx.testtools.VertxAssert.*


class GroovySomeVerticle extends Verticle {

  def start() {
    // initialize(vertx);

    // You can also assert from other verticles!!
    assertEquals("foo", "foo");

    println "In Groovy some verticle"

    // And complete tests from other verticles!!
    testComplete();

  }
}
