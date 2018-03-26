package services

import models.Estabelecimento

object EstabelecimentoService {
  def novo(estabelecimento: EstabelecimentoNovo): Option[Estabelecimento] = Some(Estabelecimento(estabelecimento.nome, estabelecimento.endereco).create)

  def buscaTodos(): Option[List[Estabelecimento]] = Some(Estabelecimento.toList)

  def buscaPorId(id: Long): Option[Estabelecimento] = Estabelecimento.find(id)


  //ajustar depois
  def atualizar(estabelecimento: EstabelecimentoNovo): Option[Estabelecimento] = {
    var estabelecimentoSalvo = Estabelecimento.find(estabelecimento.id).head
    estabelecimentoSalvo.nome = estabelecimento.nome
    estabelecimentoSalvo.endereco = estabelecimento.endereco
    Some(estabelecimentoSalvo)
  }

  def delete(id: Long): Option[Boolean] = Some(Estabelecimento.find(id).head.delete)

  case class EstabelecimentoNovo(nome: String, endereco: String, id: Long = 0)

}
