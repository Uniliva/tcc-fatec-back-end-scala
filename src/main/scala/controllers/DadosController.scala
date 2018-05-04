package controllers

import java.util.Calendar
import java.util.Formatter.DateTime

import base.Support.ControllerBase
import com.fasterxml.jackson.databind.JsonMappingException
import com.github.aselab.activerecord.dsl._
import models.Dados
import org.json4s._
import org.scalatra._
import services.{DadosServices, EstabelecimentoService, SensorService}
import services.DadosServices.{DadoNovo, DadoSensor}

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
/*
  post("/sensor") {
    Try {
      logger.info("Adicionando novo dado do sensor.")
      val dado = parsedBody.extract[DadoSensor]
      val dados = new DadoNovo(dado.temperaturaAtual,new DateTime("2018-04-24T09:04:22Z"), 1, dado.temEnergia )
      DadosServices.novo(dado)
        .map(dados => Ok("dado" -> dados.asJson)).orNull
    }
  }*/

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
      val dados = DadosServices.porSensorQuantidade(sensor, qtd)
      Ok("dados" -> dados.map(dados => dados.asJson) )
    }
  }

  get("/sensor/graficos/:sensorId/quantidade/:qtd") {
    Try {
      logger.info("Buscando os dados dos graficos do sensor.")
      val id = params("sensorId").toLong
      val qtd = params("qtd").toInt
      val sensor = SensorService.buscaPorId(id)
      val estabelecimentoID = sensor.map(s => s.estabelecimentoID).head
      val estabelecimento = EstabelecimentoService.buscaEstabelecimentosSensorPorId(estabelecimentoID).map(e => Map("nome" -> e.nome, "endereco" -> e.endereco, "telefone" -> e.telefone, "email" -> e.email,"sensor" -> sensor.map(s => s.asJson) ))
      val sensores = DadosServices.porSensorQuantidade(id, qtd)
      var totalTemEnergia: Int = 0
      var totalNaoTemEnergia: Int = 0
      sensores.map( dado => {
            if (dado.temEnergia) {
              totalTemEnergia += 1;
            } else {
              totalNaoTemEnergia += 1;
            }
          })

      var retorno = Map(
        "estabelecimento" -> estabelecimento,
        "energia"-> Map("tem-energia" -> totalTemEnergia, "falha-eletrica"-> totalNaoTemEnergia),
        "dados"-> sensores.map(dados => dados.asJson)
      )
      Ok(retorno)
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
