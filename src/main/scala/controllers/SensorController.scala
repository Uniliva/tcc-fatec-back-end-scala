package controllers

import base.ControllerBase
import com.github.aselab.activerecord.dsl._
import org.json4s._
import org.scalatra._
import services.SensorService
import services.SensorService.SensorNovo

import scala.util.Try

class SensorController extends ControllerBase {
  post("/novo") {
    Try {
      logger.info("Adicionando novo Sensor.")
      val sensor = parsedBody.extract[SensorNovo]
      SensorService.novo(sensor).map(sensor => Ok("novo-sensor" -> sensor.asJson)).orNull
    }
  }

  get("/todos") {
    Try {
      logger.info("Buscando todos os  sensores.")
      SensorService.buscaTodos().map(sensor => Ok("sensores" -> sensor)).orNull
    }
  }

  get("/id/:id") {
    Try {
      logger.info("Buscando sensor pelo ID.")
      val id = params("id").toLong
      Ok("sensor" -> SensorService.buscaPorId(id).asJson)
    }
  }

  post("/atualizar") {
    Try {
      logger.info("Atualizando Sensor.")
      val sensor = parsedBody.extract[SensorNovo]
      SensorService.atualizar(sensor).map(sensor => Ok("sensor-atualizado" -> sensor.asJson)).orNull
    }
  }

  delete("/id/:id") {
    Try {
      logger.info("Removendo o sensor.")
      val id = params("id").toLong
      if (SensorService.delete(id)) {
        Ok("msg" -> "Sensor removido com sucesso")
      } else {
        InternalServerError("msg" -> "Erro ao remover sensor pelo id")
      }
    }
  }


}
