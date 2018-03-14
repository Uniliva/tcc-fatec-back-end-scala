
import org.scalatra._
import javax.servlet.ServletContext

import controllers.loginController

class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext) {
    context.mount(new loginController,  "/*")
  }
}
