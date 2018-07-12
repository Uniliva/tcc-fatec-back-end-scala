package controllers

import base.ControllerBase
import com.github.aselab.activerecord.dsl._
import org.json4s._
import org.scalatra._
import services.DadosServices.DadosHelper
import services.{DadosServices, SensorService}

import scala.util.Try

class DadosController extends ControllerBase {

  post("/sensor/:codigo") {
    Try {
      val codigo = params("codigo")
      logger.info("Adicionando novo dado do sensor: " + codigo)
      val dados = parsedBody.extract[DadosHelper]
      val sensor = SensorService.buscaPorCodigo(codigo)
      dados.idSensor = sensor.id
      DadosServices.novo(dados).map(dados => Ok("dado" -> dados.asJson)).orNull
    }
  }

  get("/todos") {
    Try {
      logger.info("Buscando todos os dados: ")
      DadosServices.buscaTodos()
        .map(dados => Ok("dados" -> dados.asJson)).orNull
    }
  }

  get("/sensor/:sensorId/quantidade/:qtd") {
    Try {
      logger.info("Buscando os dados de um sensor.")
      val sensor = params("sensorId").toLong
      val qtd = params("qtd").toInt
      val dados = DadosServices.porSensorEQuantidade(sensor, qtd)
      Ok("dados" -> dados.map(dados => dados.asJson))
    }
  }

  get("/sensor/graficos/:sensorId/quantidade/:qtd") {
    Try {
      logger.info("Buscando os dados dos graficos do sensor.")
      val id = params("sensorId").toLong
      val qtd = params("qtd").toInt

      Ok(DadosServices.getDadosSensorLoja(id, qtd))
    }
  }

  get("/id/:id") {
    Try {
      logger.info("Buscando dados pelo Id.")
      val id = params("id").toLong
      DadosServices.buscaPorId(id).map(dado => Ok("dado" -> dado.asJson))
    }
  }
}
