package controllers

import com.github.aselab.activerecord.RecordInvalidException
import com.typesafe.scalalogging.LazyLogging
import org.scalatra.{CorsSupport, MatchedRoute, ScalatraServlet}
import com.github.aselab.activerecord.dsl._
import org.json4s._
import org.scalatra._
import org.scalatra.json.JacksonJsonSupport
import services.EstabelecimentoService
import services.EstabelecimentoService.EstabelecimentoNovo

class EstabelecimentoController extends ScalatraServlet with LazyLogging with CorsSupport with JacksonJsonSupport {
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
    try {
      logger.info("Adicionando estabelecimento.")
      val usuario = parsedBody.extract[EstabelecimentoNovo]
      EstabelecimentoService.novo(usuario) match {
        case None => InternalServerError("msg" -> "Erro ao criar estabelecimento")
        case estabelecimento => Ok("novo-estabelecimento" -> estabelecimento.head.asJson)
      }
    } catch {
      case e: Exception =>BadRequest("msg" -> "Requisição inválida")
    }
  }

  get("/todos") {
    logger.info("Buscando todos os  estabelecimentos.")
    EstabelecimentoService.buscaTodos() match {
      case None => NotFound("msg" -> "Erro ao buscar estabelecimento")
      case estabelecimento => Ok("estabelecimentos" -> estabelecimento.head.asJson)
    }
  }

  get("/id/:id") {
    try {
      logger.info("Buscando estabelecimento pelo ID.")
      val id = params("id").toLong
      EstabelecimentoService.buscaPorId(id) match {
        case None => NotFound("msg" -> "Estabelecimento não encontrado")
        case estabelecimento => Ok("estabelecimento" -> estabelecimento.head.asJson)
      }
    } catch {
      case e: NumberFormatException => BadRequest("msg" -> "Id informado é invalido")
      case x: Exception => InternalServerError("msg" -> "Erro ao Buscar estabelecimento pelo id")
    }
  }

  post("/atualizar"){
    try {
      logger.info("Atualizando estabelecimento.")
      val estabelecimento = parsedBody.extract[EstabelecimentoNovo]
      EstabelecimentoService.atualizar(estabelecimento) match {
        case Left(e) => NotFound("msg" -> "Erro ao atualizar estabelecimento")
        case Right(estabelecimento) => Ok("estabelecimento-atualizado" -> estabelecimento.asJson)
      }
    }catch {
      case x: Exception => InternalServerError("msg" -> "Erro ao atualizar estabelecimento")
    }
  }

  delete("/id/:id") {
    try {
      logger.info("Removendo o estabelecimento.")
      val id = params("id").toLong
      EstabelecimentoService.delete(id) match {
        case true => Ok("msg" -> "Estabelecimento Removido com sucesso")
        case false => InternalServerError("msg" -> "Erro ao remover usuario pelo id")
      }
    } catch {
      case e: NumberFormatException => BadRequest("msg" -> "Id informado é invalido")
      case e: NoSuchElementException => NotFound("msg" -> "Estabelecimento invalido")
      case x: Exception => InternalServerError("msg" -> "Erro ao remover estabelecimento pelo id")
    }
  }


}
