package com.fatec.osasco

import org.scalatra.test.scalatest._

class umonitorInitTests extends ScalatraFunSuite {

  addServlet(classOf[umonitorInit], "/*")

  test("GET / on umonitorInit should return status 200"){
    get("/"){
      status should equal (200)
    }
  }

}
