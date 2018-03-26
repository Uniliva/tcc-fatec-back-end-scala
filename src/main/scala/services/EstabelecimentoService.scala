package services

import com.github.aselab.activerecord.validations.Errors
import models.{Estabelecimento, Sensor}

object EstabelecimentoService {
  def novo(estabelecimento: EstabelecimentoNovo): Option[Estabelecimento] = Some(Estabelecimento(estabelecimento.nome, estabelecimento.endereco).create)

  def buscaTodos(): Option[List[Estabelecimento]] = Some(Estabelecimento.toList)

  def buscaPorId(id: Long): Option[Estabelecimento] = Estabelecimento.find(id)


  //ajustar depois
  def atualizar(estabelecimento: EstabelecimentoNovo): Either[Errors,Estabelecimento] = {
    var estabelecimentoSalvo = Estabelecimento.find(estabelecimento.id).head
    estabelecimentoSalvo.nome = estabelecimento.nome

    estabelecimentoSalvo.endereco = estabelecimento.endereco
   estabelecimentoSalvo.saveEither
  }

  def delete(id: Long): Boolean = Estabelecimento.find(id).head.delete

  def adicionarSensor(estabelecimentoID: Long,sensor: Sensor):Sensor={
    val estabelecimento = Estabelecimento.find(estabelecimentoID).head
    estabelecimento.sensores << sensor
  }

  case class EstabelecimentoNovo(nome: String, endereco: String, id: Long = 0)

}
