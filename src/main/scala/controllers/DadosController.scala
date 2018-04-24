package controllers

import com.github.aselab.activerecord.dsl._
import org.json4s._
import org.scalatra._
import services.DadosServices
import services.DadosServices.DadoNovo

class DadosController extends ControllerBase {
  /*
   {
    "temperaturaAtual": 20,
    "dataAtual": "2004-09-04T18:06:22Z",
    "sensorID": 1
}
  */
  post("/novo") {
    try {
      logger.info("Adicionando novo dado do sensor.")
      val dado = parsedBody.extract[DadoNovo]
      DadosServices.novo(dado) match {
        case None => InternalServerError("msg" -> "Erro ao criar Dados")
        case dados => Ok("dado" -> dados.head.asJson)
      }
    } catch {
      case e: NoSuchElementException => BadRequest("msg" -> "Dados invalido")
      case e: Exception => BadRequest("msg" -> "Requisição inválida")
    }
  }
  /*
  get("/todos") {
    try {
      DadosServices.buscaTodos() match {
        case None => NotFound("msg" -> "Erro ao buscar dados")
        case dados => Ok("dados" -> dados.head.asJson)
      }
    } catch {
      case x: Exception => InternalServerError("msg" -> "Erro ao Buscar dado do sensor pelo id")
    }
  }*/

  get("/sensor/:sensorId/quantidade/:qtd") {
    try {
      logger.info("Buscando os dados de um sensor.")
      val sensor = params("sensorId").toLong
      val qtd = params("qtd").toInt

      DadosServices.porSensorQuantidade(sensor, qtd) match {
        case dados => Ok("dados" -> dados.head.asJson)
      }
    } catch {
      case e: NumberFormatException => BadRequest("msg" -> "Valores informados invalidos")
      case x: Exception => InternalServerError("msg" -> s"Erro ao Buscar dado do sensor")
    }
  }


  get("/id/:id") {
    try {
      logger.info("Buscando dados pelo Id.")
      val id = params("id").toLong
      DadosServices.buscaPorId(id) match {
        case None => NotFound("msg" -> "Dado não encontrado")
        case dado => Ok("dado" -> dado.head.asJson)
      }
    } catch {
      case e: NumberFormatException => BadRequest("msg" -> "Id informado é invalido")
      case x: Exception => InternalServerError("msg" -> "Erro ao Buscar dado do sensor pelo id")
    }
  }


}
