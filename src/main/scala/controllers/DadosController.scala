package controllers

import com.fasterxml.jackson.databind.JsonMappingException
import com.github.aselab.activerecord.dsl._
import org.json4s._
import org.scalatra._
import services.DadosServices
import services.DadosServices.DadoNovo

import scala.util.Try

class DadosController extends ControllerBase {
  /*
   {
    "temperaturaAtual": 20,
    "dataAtual": "2004-09-04T18:06:22Z",
    "sensorID": 1
}
  */
  post("/novo") {
    Try {
      logger.info("Adicionando novo dado do sensor.")
      val dado = parsedBody.extract[DadoNovo]
      DadosServices.novo(dado)
        .map(dados => Ok("dado" -> dados.asJson)).orNull
    }
  }

  get("/todos") {
    Try {
      DadosServices.buscaTodos()
        .map(dados => Ok("dados" -> dados.asJson)).orNull
    }
  }

  get("/sensor/:sensorId/quantidade/:qtd") {
    Try {
      logger.info("Buscando os dados de um sensor.")
      val sensor = params("sensorId").toLong
      val qtd = params("qtd").toInt
      DadosServices.porSensorQuantidade(sensor, qtd).map(dados => Ok("dados" -> dados.asJson)).orNull
    }
  }


  get("/id/:id") {
    try {
      logger.info("Buscando dados pelo Id.")
      val id = params("id").toLong
      DadosServices.buscaPorId(id).map(dado => Ok("dado" -> dado.asJson))
    }
  }
}
