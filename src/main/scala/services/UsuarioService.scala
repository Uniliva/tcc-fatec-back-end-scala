package services

import com.github.aselab.activerecord.dsl._
import models.{ Usuario}


object UsuarioService {

  def logar(usuario: UsuarioLogin): Option[Usuario] = {
    Usuario.findBy("email" -> usuario.email, "senha" -> usuario.senha)
  }

  def novo(usuarioNovo: UsuarioNovo): Option[Usuario] = Some(Usuario(usuarioNovo.nome, usuarioNovo.email, usuarioNovo.senha).create)

  def buscaTodos(): Option[List[Usuario]] = Some(Usuario.toList)

  def buscaPorId(id: Long): Option[Usuario] = Usuario.find(id)

  def buscaPorEmail(email: String): Option[Usuario] = Usuario.findBy("email" -> email)

  //ajustar depois
  def atualizar(usuario: UsuarioNovo): Option[Usuario] = {
    var usuarioSalvo = Usuario.find(usuario.id).head

    usuarioSalvo.nome = usuario.nome
    usuarioSalvo.email = usuario.email
    usuarioSalvo.isAdmin = usuario.isAdmin
    usuarioSalvo.senha = usuario.senha
    usuarioSalvo.save
    Some(usuarioSalvo)
  }

  def remove(id:Long):Boolean =  Usuario.find(id).head.delete


case class UsuarioLogin(email: String, senha: String)

case class UsuarioNovo(nome: String, email: String, isAdmin: Boolean, senha: String,  id: Long = 0)

}
