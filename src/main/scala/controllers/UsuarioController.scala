package controllers

import com.typesafe.scalalogging.LazyLogging
import models.{Dados, Grupo, Sensor, Usuario}
import org.scalatra.{CorsSupport, MatchedRoute, ScalatraServlet}
import com.github.aselab.activerecord.dsl._
import services.UsuarioService
import org.json4s._
import org.scalatra._
import org.scalatra.json.JacksonJsonSupport
import services.UsuarioService.UsuarioLogin

class UsuarioController extends ScalatraServlet with LazyLogging with CorsSupport with JacksonJsonSupport {
  override protected implicit def jsonFormats: Formats = DefaultFormats

  before(){
    contentType=formats("json")
  }

  options("/*") {
    response.setHeader(
      "Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers")
    )
  }

  post("/login") {
    val usuarioLogin = parsedBody.extract[UsuarioLogin]
    UsuarioService.logar(usuarioLogin) match {
      case None => NotFound("msg"->"usuario ou senha invalidos")
      case usuario =>  Ok("usuario-logado"->usuario.head.asJson)
    }
  }


}


/*
  val admin = Usuario("Admin","admin@umoniotr.com.br","1234").create
    val suporte = Usuario("suporte","suporte@umoniotr.com.br","1234").create

    val gadmin = Grupo("Usuarios").create

    gadmin.usuarios << suporte
    gadmin.usuariosAdmin << admin

    println(gadmin.usuarios.toList)

    println(admin.grupo)

    println(admin.isAdmin)
 */