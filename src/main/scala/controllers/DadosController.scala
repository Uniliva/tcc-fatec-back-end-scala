package controllers

import com.github.aselab.activerecord.RecordInvalidException
import com.typesafe.scalalogging.LazyLogging
import models.{Dados,  Sensor, Usuario}
import org.scalatra.{CorsSupport, MatchedRoute, ScalatraServlet}
import com.github.aselab.activerecord.dsl._
import services.{UsuarioService}
import org.json4s._
import org.scalatra._
import org.scalatra.json.JacksonJsonSupport
import services.DadosServices
import services.DadosServices.DadoNovo

class DadosController  extends ScalatraServlet with LazyLogging with CorsSupport with JacksonJsonSupport {
  override protected implicit def jsonFormats: Formats = DefaultFormats

  before() {
    contentType = formats("json")
  }

  options("/*") {
    response.setHeader(
      "Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers")
    )
  }

  post("/novo") {
   // try {
      val dado = parsedBody.extract[DadoNovo]
      DadosServices.novo(dado) match {
        case None => InternalServerError("msg" -> "Erro ao criar Dados")
        case dados => Ok("dado" -> dados.head.asJson)
      }
  /*  } catch {
      case e: NoSuchElementException => BadRequest("msg" -> "Dados invalido")
      case e: Exception =>BadRequest("msg" -> "Requisição inválida")
    }*/
  }
  get("/todos") {
    DadosServices.buscaTodos() match {
      case None => NotFound("msg" -> "Erro ao buscar usuario")
      case dados => Ok("dados" -> dados.head.asJson)
    }
  }

}
