package services

import models.Usuario

object UsuarioService {

  def logar(usuario:UsuarioLogin ):Option[Usuario]={
    Usuario.findBy("email"-> usuario.email, "senha" -> usuario.senha)
  }
  case class UsuarioLogin(email:String, senha:String)
}
