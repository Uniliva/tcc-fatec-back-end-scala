package controllers

import com.github.aselab.activerecord.RecordInvalidException
import com.typesafe.scalalogging.LazyLogging
import models.{Dados, Sensor, Usuario}
import org.scalatra.{CorsSupport, MatchedRoute, ScalatraServlet}
import com.github.aselab.activerecord.dsl._
import services.UsuarioService
import org.json4s._
import org.scalatra._
import org.scalatra.json.JacksonJsonSupport
import services.UsuarioService.{UsuarioLogin, UsuarioNovo}

class UsuarioController extends ScalatraServlet with LazyLogging with CorsSupport with JacksonJsonSupport {
  override protected implicit def jsonFormats: Formats = DefaultFormats

  before() {
    contentType = formats("json")
  }

  options("/*") {
    response.setHeader(
      "Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers")
    )
  }

  post("/login") {
    try {
      logger.info("Login iniciado.")
      val usuarioLogin = parsedBody.extract[UsuarioLogin]
      UsuarioService.logar(usuarioLogin) match {
        case None => NotFound("msg" -> "usuario ou senha invalidos")
        case usuario => Ok("usuario-logado" -> usuario.head.asJson)
      }
    } catch {
      case e: Exception => {
        logger.warn("Erro no login.")
        BadRequest("msg" -> "Requisição inválida")
      }
    }
  }

  /*--------------------------------------------------------------------------
   {
    "nome": "Teste",
    "isAdmin": true,
    "email": "como@umoniotr.com.br",
    "senha": "1234"
}
   */
  post("/novo") {
    try {
      logger.info("Adicionando Usuario.")
      val usuario = parsedBody.extract[UsuarioNovo]
      UsuarioService.novo(usuario) match {
        case None => InternalServerError("msg" -> "Erro ao criar usuario")
        case usuario => Ok("novo-usuario" -> usuario.head.asJson)
      }
    } catch {
      case e: RecordInvalidException => BadRequest("msg" -> "E-mail ja cadastrado")
      case e: NoSuchElementException => BadRequest("msg" -> "Grupo invalido")
      case e: Exception =>BadRequest("msg" -> "Requisição inválida")
    }
  }

  get("/todos") {
    logger.info("Buscando todos os  usuarios.")
    UsuarioService.buscaTodos() match {
      case None => NotFound("msg" -> "Erro ao buscar usuario")
      case usuario => Ok("usuarios" -> usuario.head.asJson)
    }
  }

  get("/id/:id") {
    try {
      logger.info("Buscando usuario pelo ID.")
      val id = params("id").toLong
      UsuarioService.buscaPorId(id) match {
        case None => NotFound("msg" -> "Usuario não encontrado")
        case usuario => Ok("usuario" -> usuario.head.asJson)
      }
    } catch {
      case e: NumberFormatException => BadRequest("msg" -> "Id informado é invalido")
      case x: Exception => InternalServerError("msg" -> "Erro ao Buscar usuario pelo id")
    }
  }

  get("/email/:email") {
    try {
      logger.info("Buscando usaurio pelo Email.")
      val email = params("email")
      UsuarioService.buscaPorEmail(email) match {
        case None => NotFound("msg" -> "Usuario não encontrado")
        case usuario => Ok("usuario" -> usuario.head.asJson)
      }
    } catch {
      case x: Exception => InternalServerError("msg" -> "Erro ao Buscar usuario pelo email")
    }
  }

  post("/atualizar"){
    try {
      logger.info("Atualizando usuario.")
      val usuario = parsedBody.extract[UsuarioNovo]
      UsuarioService.atualizar(usuario) match {
        case None => NotFound("msg" -> "Erro ao atualizar usuario")
        case usuario => Ok("usuario-atualizado" -> usuario.head.asJson)
      }
    }catch {
        case x: Exception => InternalServerError("msg" -> "Erro ao Buscar usuario pelo email")
      }
  }

  delete("/id/:id") {
    try {
      logger.info("Removendo o usuario.")
      val id = params("id").toLong
      UsuarioService.delete(id) match {
        case Some(true) => Ok("usuario" -> "Usuario Removido com sucesso")
      }
    } catch {
      case e: NumberFormatException => BadRequest("msg" -> "Id informado é invalido")
      case x: Exception => InternalServerError("msg" -> "Erro ao remover usuario pelo id")
    }
  }

}
