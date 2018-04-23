package controllers

import com.typesafe.scalalogging.LazyLogging
import org.scalatra.ScalatraServlet

abstract class ControllerBase extends ControllerHandling with ScalatraServlet with LazyLogging  {

}
