package services

import com.github.aselab.activerecord.ActiveRecord
import com.github.aselab.activerecord.validations.Errors
import models.{Estabelecimento, Sensor}
import com.github.aselab.activerecord.dsl._
import services.SensorService.SensorNovo

object EstabelecimentoService {
  def novo(estabelecimento: EstabelecimentoNovo): Option[Estabelecimento] = Some(Estabelecimento(estabelecimento.nome, estabelecimento.endereco, estabelecimento.telefone, estabelecimento.email).create)

  //def buscaTodos(): Option[List[Estabelecimento]] = Some(Estabelecimento.toList)
 def buscaTodos(): Option[List[EstabelecimentoFull]] ={

    var x =Estabelecimento.all.map(
      (estabelecimento) => EstabelecimentoFull(estabelecimento.id, estabelecimento.nome, estabelecimento.endereco, estabelecimento.telefone, estabelecimento.email, estabelecimento.sensores.map( x => {
        SensorNovo(x.codigo, x.decricao: String, x.temperaturaMin, x.temperaturaMax,  x.id)
      }).toList)
    ).toList
    Some(x)

  }

  def buscaEstabelecimentosSensorPorId(id: Long): Option[Estabelecimento] = Estabelecimento.find(id)

  def buscaEstabelecimentosPorId(id: Long): Option[EstabelecimentoFull] ={
    var x =Estabelecimento.find(id).map(
      (estabelecimento) => EstabelecimentoFull(estabelecimento.id, estabelecimento.nome, estabelecimento.endereco,estabelecimento.telefone, estabelecimento.email, estabelecimento.sensores.map( x => {
      SensorNovo(x.codigo, x.decricao: String, x.temperaturaMin, x.temperaturaMax, x.estabelecimentoID,  x.id)
      }).toList)
    ).head
    Some(x)
  }

  def buscaSensoresPorId(id: Long):Option[List[Sensor]] = Some(Estabelecimento.find(id).head.sensores.toList)


  //ajustar depois
  def atualizar(estabelecimento: EstabelecimentoNovo):Option[ Estabelecimento] = {
    println(estabelecimento.id)
    var estabelecimentoSalvo = Estabelecimento.find(estabelecimento.id).head
    estabelecimentoSalvo.nome = estabelecimento.nome
    estabelecimentoSalvo.endereco = estabelecimento.endereco
    estabelecimentoSalvo.email = estabelecimento.email
    estabelecimentoSalvo.telefone = estabelecimento.telefone
    Some(estabelecimentoSalvo.update)
  }

  def delete(id: Long): Boolean = Estabelecimento.find(id).head.delete

  def adicionarSensor(estabelecimentoID: Long, sensorID: Long): Option[Sensor] = {
    val sensor = Sensor.find(sensorID).head
    val estabelecimento = Estabelecimento.find(estabelecimentoID).head
    Some(estabelecimento.sensores << sensor)
  }

  def removerSensor(estabelecimentoID: Long, sensorID: Long): Option[Sensor]  = {
    val sensor = Sensor.find(sensorID).head
    val estabelecimento = Estabelecimento.find(estabelecimentoID).head
    Option(estabelecimento.sensores.remove(sensor).head)
  }

  case class EstabelecimentoNovo(nome: String, endereco: String, telefone: String, email: String, id: Long = 0)
  case class EstabelecimentoFull(id: Long, nome: String, endereco: String, telefone: String, email: String , sensores: List[SensorNovo])

}
