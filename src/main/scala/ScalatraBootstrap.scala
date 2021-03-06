
import org.scalatra._
import javax.servlet.ServletContext
import com.github.aselab.activerecord._
import dsl._
import controllers.{DadosController, EstabelecimentoController, SensorController, UsuarioController}
import models.{Tabelas, Usuario}

class ScalatraBootstrap extends LifeCycle {
  //definindo qual o bando de dados
  System.setProperty("run.mode", "prod")
  //System.setProperty("run.mode", "local")
  override def init(context: ServletContext) {
    //inicializando as tabelas do banco de dados
    Tabelas.initialize
    context.mount(new UsuarioController,  "/usuarios/*")
    context.mount(new DadosController,  "/dados/*")
    context.mount(new EstabelecimentoController,  "/estabelecimentos/*")
    context.mount(new SensorController,  "/sensores/*")

    //context.initParameters("org.scalatra.cors.allowedOrigins")= "http://192.168.56.1:8081"
    context.initParameters("org.scalatra.cors.allowCredentials") = "false"
  }

  override def destroy(context: ServletContext): Unit = {
    Tabelas.cleanup
  }
}
