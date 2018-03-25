
import org.scalatra._
import javax.servlet.ServletContext

import com.github.aselab.activerecord._
import dsl._
import controllers.{DadosController, UsuarioController}
import models.{Tabelas, Usuario}

class ScalatraBootstrap extends LifeCycle {
  //definindo qual o bando de dados
  System.setProperty("run.mode", "local")
  override def init(context: ServletContext) {
    //inicializando as tabelas do banco de dados
    Tabelas.initialize
    context.mount(new UsuarioController,  "/usuario/*")
    context.mount(new DadosController,  "/dados/*")
  }

  override def destroy(context: ServletContext): Unit = {
    Tabelas.cleanup
  }
}
