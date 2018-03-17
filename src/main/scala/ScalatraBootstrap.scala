
import org.scalatra._
import javax.servlet.ServletContext

import com.github.aselab.activerecord._
import dsl._

import controllers.UsuarioController
import models.{Grupo, Tabelas, Usuario}

class ScalatraBootstrap extends LifeCycle {
  //definindo qual o bando de dados
  System.setProperty("run.mode", "local")
  override def init(context: ServletContext) {
    //inicializando as tabelas do banco de dados
    Tabelas.initialize
    context.mount(new UsuarioController,  "/usuario/*")
  }

  override def destroy(context: ServletContext): Unit = {
    Tabelas.cleanup
  }
}
