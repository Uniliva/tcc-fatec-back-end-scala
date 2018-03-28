package controllers

import com.github.aselab.activerecord.RecordInvalidException
import com.typesafe.scalalogging.LazyLogging
import org.scalatra.{CorsSupport, MatchedRoute, ScalatraServlet}
import com.github.aselab.activerecord.dsl._
import org.json4s._
import org.scalatra._
import org.scalatra.json.JacksonJsonSupport
import services.{DadosServices, EstabelecimentoService}
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
      case e: Exception => BadRequest("msg" -> "Requisição inválida")
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
      EstabelecimentoService.buscaEstabelecimentosPorId(id) match {
        case None => NotFound("msg" -> "Estabelecimento não encontrado")
        case estabelecimento => Ok("estabelecimento" -> estabelecimento.head.asJson)
      }
    } catch {
      case e: NoSuchElementException => BadRequest("msg" -> "Estabelecimento ou sensor invalido")
      case e: NumberFormatException => BadRequest("msg" -> "Id informado é invalido")
      case x: Exception => InternalServerError("msg" -> "Erro ao Buscar estabelecimento pelo id")
    }
  }


  get("/sensores/:id") {
    try {
      logger.info("Buscando sensores de um estabelecimento pelo ID.")
      val id = params("id").toLong
      EstabelecimentoService.buscaSensoresPorId(id) match {
        case Right(sensores) => Ok("Sensores-do-estabelecimento" -> sensores.asJson)
        case Left(e) => InternalServerError("msg" -> "Erro interno")
      }
    } catch {
      case e: NoSuchElementException => BadRequest("msg" -> "Estabelecimento ou sensor invalido")
      case e: NumberFormatException => BadRequest("msg" -> "Id informado é invalido")
      case x: Exception => InternalServerError("msg" -> "Erro interno")
    }
  }

  post("/atualizar") {
    try {
      logger.info("Atualizando estabelecimento.")
      val estabelecimento = parsedBody.extract[EstabelecimentoNovo]
      EstabelecimentoService.atualizar(estabelecimento) match {
        case Left(e) => NotFound("msg" -> "Erro ao atualizar estabelecimento")
        case Right(estabelecimento) => Ok("estabelecimento-atualizado" -> estabelecimento.asJson)
      }
    } catch {
      case x: Exception => InternalServerError("msg" -> "Erro ao atualizar estabelecimento")
    }
  }

  post("/remove-sensor/:idEstabelecimento/sensor/:idSensor") {
    try {
      logger.info("Removendo sensor de um estabelecimento.")
      val idEstabelecimento = params("idEstabelecimento").toLong
      val idSensor = params("idSensor").toLong
      EstabelecimentoService.removerSensor(idEstabelecimento, idSensor) match {
        case Left(e) => NotFound("msg" -> "Erro ao remover sensor estabelecimento")
        case Right(sensor) => Ok("sensor-removido" -> sensor.asJson)
      }
    } catch {
      case e: NoSuchElementException => BadRequest("msg" -> "Estabelecimento ou sensor invalido")
      case x: Exception => InternalServerError("msg" -> "Erro ao interno")
    }
  }

  post("/adicionar-sensor/:idEstabelecimento/sensor/:idSensor") {
    try {
      logger.info("Adicionando sensor a um estabelecimento.")
      val idEstabelecimento = params("idEstabelecimento").toLong
      val idSensor = params("idSensor").toLong
      EstabelecimentoService.adicionarSensor(idEstabelecimento, idSensor) match {
        case Left(e) => BadRequest("msg" -> "Erro ao adicionar sensor estabelecimento")
        case Right(sensor) => Ok("sensor-adicionado" -> sensor.asJson)
      }
    } catch {
      case e: NoSuchElementException => NotFound("msg" -> "Estabelecimento ou sensor invalido")
      case x: Exception => InternalServerError("msg" -> "Erro ao interno")
    }
  }

  delete("/id/:id") {
    try {
      logger.info("Removendo o estabelecimento.")
      val id = params("id").toLong
      EstabelecimentoService.delete(id) match {
        case true => Ok("msg" -> "Estabelecimento removido com sucesso")
        case false => InternalServerError("msg" -> "Erro ao remover estabelecimento pelo id")
      }
    } catch {
      case e: NumberFormatException => BadRequest("msg" -> "Id informado é invalido")
      case e: NoSuchElementException => NotFound("msg" -> "Estabelecimento invalido")
      case x: Exception => InternalServerError("msg" -> "Erro ao remover estabelecimento pelo id")
    }
  }


}
