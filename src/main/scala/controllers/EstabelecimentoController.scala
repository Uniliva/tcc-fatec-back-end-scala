package controllers

import com.github.aselab.activerecord.dsl._
import org.json4s._
import org.scalatra._
import services.EstabelecimentoService
import services.EstabelecimentoService.EstabelecimentoNovo

import scala.util.Try

class EstabelecimentoController extends ControllerBase {
  post("/novo") {
    try {
      logger.info("Adicionando estabelecimento.")
      val usuario = parsedBody.extract[EstabelecimentoNovo]
      EstabelecimentoService.novo(usuario).map(estabelecimento => Ok("novo-estabelecimento" -> estabelecimento.asJson)).orNull
    }
  }

  get("/todos") {
    try {
      logger.info("Buscando todos os  estabelecimentos.")
      EstabelecimentoService.buscaTodos().map(estabelecimento => Ok("estabelecimentos" -> estabelecimento.head.asJson)).orNull
    }
  }

  get("/id/:id") {
    try {
      logger.info("Buscando estabelecimento pelo ID.")
      val id = params("id").toLong
      EstabelecimentoService.buscaEstabelecimentosPorId(id).map(estabelecimento => Ok("estabelecimento" -> estabelecimento.asJson)).orNull
    }
  }


  get("/sensores/:id") {
    try {
      logger.info("Buscando sensores de um estabelecimento pelo ID.")
      val id = params("id").toLong
      EstabelecimentoService.buscaSensoresPorId(id).map(sensores => Ok("Sensores-do-estabelecimento" -> sensores.asJson)).orNull
    }
  }

  post("/atualizar") {
    try {
      logger.info("Atualizando estabelecimento.")
      val estabelecimento = parsedBody.extract[EstabelecimentoNovo]
      EstabelecimentoService.atualizar(estabelecimento).map(estabelecimento => Ok("estabelecimento-atualizado" -> estabelecimento.asJson)).orNull
    }
  }

  post("/remove-sensor/:idEstabelecimento/sensor/:idSensor") {
    Try {
      logger.info("Removendo sensor de um estabelecimento.")
      val idEstabelecimento = params("idEstabelecimento").toLong
      val idSensor = params("idSensor").toLong
      EstabelecimentoService.removerSensor(idEstabelecimento, idSensor).map(sensor => Ok("sensor-removido" -> sensor.asJson)).orNull
    }
  }

  post("/adicionar-sensor/:idEstabelecimento/sensor/:idSensor") {
    Try {
      logger.info("Adicionando sensor a um estabelecimento.")
      val idEstabelecimento = params("idEstabelecimento").toLong
      val idSensor = params("idSensor").toLong
      EstabelecimentoService.adicionarSensor(idEstabelecimento, idSensor).map(sensor => Ok("sensor-adicionado" -> sensor.asJson)).orNull
    }
  }

  delete("/id/:id") {
    Try {
      logger.info("Removendo o estabelecimento.")
      val id = params("id").toLong
      if (EstabelecimentoService.delete(id)) {
        Ok("msg" -> "Estabelecimento removido com sucesso")
      } else {
        InternalServerError("msg" -> "Erro ao remover estabelecimento pelo id")
      }
    }
  }
}
