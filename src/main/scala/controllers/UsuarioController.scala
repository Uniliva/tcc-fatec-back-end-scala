package controllers

import base.ControllerBase
import com.github.aselab.activerecord.dsl._
import org.json4s._
import org.scalatra._
import services.UsuarioService

import scala.util.Try

class UsuarioController extends ControllerBase {
  post("/login") {
    Try {
      logger.info("Login iniciado.")
      UsuarioService.logar(request.body).
        map(usuario => Ok("usuario-logado" -> usuario.asJson)).
        getOrElse(Unauthorized("msg" -> "usuario ou senha invalidos"))
    }
  }

  post("/novo") {
    Try {
      logger.info("Adicionando Usuario.")
      UsuarioService.novo(request.body).map(usuario => Ok("novo-usuario" -> usuario.asJson)).orNull
    }
  }

  get("/todos") {
    Try {
      logger.info("Buscando todos os  usuarios.")
      UsuarioService.buscaTodos().map(usuario => Ok("usuarios" -> usuario.asJson)).orNull
    }
  }

  get("/id/:id") {
    Try {
      logger.info("Buscando usuario pelo ID.")
      val id = params("id").toLong
      UsuarioService.buscaPorId(id).map(usuario => Ok("usuario" -> usuario.asJson)).orNull
    }
  }

  get("/email/:email") {
    Try {
      logger.info("Buscando usaurio pelo Email.")
      val email = params("email")
      UsuarioService.buscaPorEmail(email).map(usuario => Ok("usuario" -> usuario.asJson)).orNull
    }
  }

  post("/atualizar") {
    Try {
      logger.info("Atualizando usuario.")
      UsuarioService.atualizar(request.body).map(usuario => Ok("usuario-atualizado" -> usuario.asJson)).orNull
    }
  }

  delete("/id/:id") {
    Try {
      logger.info("Removendo o usuario.")
      val id = params("id").toLong
      if (UsuarioService.remove(id)) {
        Ok("msg" -> "Usuario Removido com sucesso")
      } else {
        InternalServerError("msg" -> "Erro ao remover usuario pelo id")
      }
    }
  }


}
