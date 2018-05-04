package base

import com.typesafe.scalalogging.LazyLogging
import org.scalatra.ScalatraServlet

abstract class ControllerBase extends ScalatraServlet  with ControllerHandling with TrySupport{

}
