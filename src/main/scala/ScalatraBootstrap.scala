
import org.scalatra._
import javax.servlet.ServletContext

import controllers.loginController
import models.Tabelas

class ScalatraBootstrap extends LifeCycle {
  //definindo qual o bando de dados
  System.setProperty("run.mode", "local")
  override def init(context: ServletContext) {
    //inicializando as tabelas do banco de dados
    Tabelas.initialize
    context.mount(new loginController,  "/*")
  }

  override def destroy(context: ServletContext): Unit = {
    Tabelas.cleanup
  }
}
