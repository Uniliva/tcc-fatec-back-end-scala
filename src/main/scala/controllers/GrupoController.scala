package controllers

import com.github.aselab.activerecord.RecordInvalidException
import com.typesafe.scalalogging.LazyLogging
import org.json4s.{DefaultFormats, Formats}
import org.scalatra._
import org.scalatra.json.JacksonJsonSupport
import com.github.aselab.activerecord.dsl._
import services.GrupoService

class GrupoController extends ScalatraServlet with LazyLogging with CorsSupport with JacksonJsonSupport {
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
  //  try {
      val nome = parsedBody.extract[(String)]
      GrupoService.novo(nome.get("nome")) match {
        case None => InternalServerError("msg" -> "Erro ao criar grupo")
        case grupo => Ok("novo-usuario" -> grupo.head.asJson)
      }
   /* } catch {
      case e: RecordInvalidException => BadRequest("msg" -> "Grupo ja cadastrado")
      case e: Exception =>BadRequest("msg" -> "Requisição inválida")
    }*/
  }

}
