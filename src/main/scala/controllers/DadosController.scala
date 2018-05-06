package controllers

import base.ControllerBase
import com.fasterxml.jackson.databind.JsonMappingException
import com.github.aselab.activerecord.dsl._
import comum._
import models.Dados
import org.json4s._
import org.scalatra._
import services.{DadosServices, EstabelecimentoService, SensorService}
import services.DadosServices.{DadoNovo, DadoSensor}
import org.joda.time.DateTime

import scala.util.Try

class DadosController extends ControllerBase {

  post("/sensor/:codigo") {
    Try {
      EmailUtil.enviar("uniliva@hotmail.com","Teste","Isso é um teste")
      val codigo = params("codigo")
      logger.info("Adicionando novo dado do sensor: "+ codigo)
      val sensor = SensorService.buscaPorCodigo(codigo)
      val dadoEquip = parsedBody.extract[DadoSensor]
      val dadosSesnsor = new DadoNovo(dadoEquip.temperaturaAtual,new DateTime(), sensor.id, dadoEquip.temEnergia)
      DadosServices.novo(dadosSesnsor)
        .map(dados => Ok("dado" -> dados.asJson)).orNull
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
