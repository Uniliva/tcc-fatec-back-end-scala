package services

import models.Usuario


object UsuarioService {

  def logar(usuarioJson: String): Option[Usuario] = {
    val usuarioLogin = Usuario.fromJson(usuarioJson)
    Usuario.findBy("email" -> usuarioLogin.email, "senha" -> usuarioLogin.senha)
  }

  def novo(usuarioJson: String): Option[Usuario] = Some(Usuario.fromJson(usuarioJson).create)

  def buscaTodos(): Option[List[Usuario]] = Some(Usuario.toList)

  def buscaPorId(id: Long): Option[Usuario] = Usuario.find(id)

  def buscaPorEmail(email: String): Option[Usuario] = Usuario.findBy("email" -> email)

  def buscaPorCpf(cpf: String): Option[Usuario] = Usuario.findBy("cpf" -> cpf)

  def atualizar(usuarioJson: String): Option[Usuario] = {
    val usuarioSalvo = Usuario.fromJson(usuarioJson)
    Some(usuarioSalvo.update)
  }

  def remove(id: Long): Boolean = Usuario.find(id).head.delete

}
