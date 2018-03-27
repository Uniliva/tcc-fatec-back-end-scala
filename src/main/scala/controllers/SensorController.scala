package controllers

import com.github.aselab.activerecord.RecordInvalidException
import com.typesafe.scalalogging.LazyLogging
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.{CorsSupport, ScalatraServlet}
import org.scalatra.json.JacksonJsonSupport
import com.github.aselab.activerecord.dsl._
import models.Sensor
import org.json4s._
import org.scalatra._
import services.SensorService
import services.SensorService.SensorNovo

class SensorController extends ScalatraServlet with LazyLogging with CorsSupport with JacksonJsonSupport {
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
      logger.info("Adicionando novo Sensor.")
      val sensor = parsedBody.extract[SensorNovo]
      SensorService.novo(sensor) match {
        case Left(e) => InternalServerError("msg" -> "Erro ao criar sensor")
        case Right(sensor) => Ok("novo-estabelecimento" -> sensor.asJson)
      }
    } catch {
      case e: RecordInvalidException => BadRequest("msg" -> "Codigo ja cadastrado")
      case e: Exception => BadRequest("msg" -> "Requisição inválida")
    }
  }

  get("/todos") {
    logger.info("Buscando todos os  sensores.")
    SensorService.buscaTodos() match {
      case None => NotFound("msg" -> "Erro ao buscar sensor")
      case sensor => Ok("sensores" -> sensor.head.asJson)
    }
  }

  get("/id/:id") {
    try {
      logger.info("Buscando sensor pelo ID.")
      val id = params("id").toLong
      SensorService.buscaPorId(id) match {
        case None => NotFound("msg" -> "Sensor não encontrado")
        case sensor => Ok("sensor" -> sensor.head.asJson)
      }
    } catch {
      case e: NoSuchElementException => BadRequest("msg" -> "Sensor ou sensor invalido")
      case e: NumberFormatException => BadRequest("msg" -> "Id informado é invalido")
      case x: Exception => InternalServerError("msg" -> "Erro interno")
    }
  }

  

  post("/atualizar") {
    try {
      logger.info("Atualizando Sensor.")
      val sensor = parsedBody.extract[SensorNovo]
      SensorService.atualizar(sensor) match {
        case Left(e) => NotFound("msg" -> "Erro ao atualizar sensor")
        case Right(sensor) => Ok("sensor-atualizado" -> sensor.asJson)
      }
    } catch {
      case x: Exception => InternalServerError("msg" -> "Erro interno")
    }
  }

  delete("/id/:id") {
    try {
      logger.info("Removendo o sensor.")
      val id = params("id").toLong
      SensorService.delete(id) match {
        case true => Ok("msg" -> "Sensor removido com sucesso")
        case false => InternalServerError("msg" -> "Erro ao remover sensor pelo id")
      }
    } catch {
      case e: NumberFormatException => BadRequest("msg" -> "Id informado é invalido")
      case e: NoSuchElementException => NotFound("msg" -> "Sensor invalido")
      case x: Exception => InternalServerError("msg" -> "Erro ao remover sensor pelo id")
    }
  }


}
