package services

import com.github.aselab.activerecord.dsl._
import models.{Grupo, Usuario}


object UsuarioService {

  def logar(usuario: UsuarioLogin): Option[Usuario] = {
    Usuario.findBy("email" -> usuario.email, "senha" -> usuario.senha)
  }

  def novo(usuarioNovo: UsuarioNovo): Option[Usuario] = {
    val grupo = Grupo.find(usuarioNovo.grupoId).head
    grupo match {
      case grupo => {
        val novo = Usuario(usuarioNovo.nome, usuarioNovo.email, usuarioNovo.senha).create
        usuarioNovo.isAdmin match {
          case true => grupo.usuariosAdmin << novo
          case false => grupo.usuarios << novo
        }
        Some(novo)
      }
    }


  }

  def buscaTodos(): Option[List[Usuario]] = Some(Usuario.toList)

  def buscaPorId(id: Long): Option[Usuario] = Usuario.find(id)

  def buscaPorEmail(email: String): Option[Usuario] = Usuario.findBy("email" -> email)

  def atualizar(usuario: UsuarioNovo): Option[Usuario] = {
    var usuarioSalvo = Usuario.find(usuario.id).head
    //remove usuario do grupo
    var grupoAntigo = Grupo.find(usuarioSalvo.grupoId).head

    removeUsuarioDoGrupo(usuarioSalvo, grupoAntigo)
    println("-Aqui")
    //atualizado
    val usuarioAtualizado = usuarioSalvo.copy(nome = usuario.nome, email = usuario.email, isAdmin = usuario.isAdmin, senha = usuario.senha).update
    //novo grupo
    println("-Aqui2")
    val novoGrupo = Grupo.find(usuarioAtualizado.grupoId).head
    //adiciona ao novo grupo
    adicionaUsuarioDoGrupo(usuarioAtualizado, novoGrupo)

    Some(usuarioAtualizado)
  }


  private def removeUsuarioDoGrupo(usuario: Usuario, grupo: Grupo): Unit = {
    usuario.isAdmin match {
      case true => grupo.usuariosAdmin.remove(usuario)
      case false => grupo.usuarios.remove(usuario)
    }
  }

  private def adicionaUsuarioDoGrupo(usuario: Usuario, grupo: Grupo): Unit = {
    usuario.isAdmin match {
      case true => grupo.usuariosAdmin << usuario
      case false => grupo.usuarios << usuario
    }
  }


  case class UsuarioLogin(email: String, senha: String)

  case class UsuarioNovo(nome: String, email: String, isAdmin: Boolean, senha: String, grupoId: Long, id: Long = 0) {

  }

}
