package services

import com.github.aselab.activerecord.validations.Errors
import models.{Estabelecimento, Sensor}
import com.github.aselab.activerecord.dsl._

object EstabelecimentoService {
  def novo(estabelecimento: EstabelecimentoNovo): Option[Estabelecimento] = Some(Estabelecimento(estabelecimento.nome, estabelecimento.endereco).create)

  def buscaTodos(): Option[List[Estabelecimento]] = Some(Estabelecimento.toList)


  def buscaEstabelecimentosPorId(id: Long): Option[Estabelecimento] = Estabelecimento.find(id)

  def buscaSensoresPorId(id: Long): Either[Errors,List[Sensor]] = Right(Estabelecimento.find(id).head.sensores.toList)


  //ajustar depois
  def atualizar(estabelecimento: EstabelecimentoNovo): Either[Errors, Estabelecimento] = {
    var estabelecimentoSalvo = Estabelecimento.find(estabelecimento.id).head
    estabelecimentoSalvo.nome = estabelecimento.nome

    estabelecimentoSalvo.endereco = estabelecimento.endereco
    estabelecimentoSalvo.saveEither
  }

  def delete(id: Long): Boolean = Estabelecimento.find(id).head.delete

  def adicionarSensor(estabelecimentoID: Long, sensorID: Long): Either[Errors, Sensor] = {
    val sensor = Sensor.find(sensorID).head
    val estabelecimento = Estabelecimento.find(estabelecimentoID).head
    Right(estabelecimento.sensores << sensor)
  }

  def removerSensor(estabelecimentoID: Long, sensorID: Long): Either[Errors, Sensor]  = {
    val sensor = Sensor.find(sensorID).head
    val estabelecimento = Estabelecimento.find(estabelecimentoID).head
    Right(estabelecimento.sensores.remove(sensor).head)
  }

  case class EstabelecimentoNovo(nome: String, endereco: String, id: Long = 0)

}
