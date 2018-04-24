package controllers.Support

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.JsonMappingException
import com.github.aselab.activerecord.RecordInvalidException
import com.typesafe.scalalogging.LazyLogging
import org.json4s.MappingException
import org.scalatra.{BadRequest, InternalServerError}
import org.scalatra.servlet.ServletBase

import scala.util.Try
import scala.util.Success
import scala.util.Failure

trait TrySupport extends ServletBase {


  override protected def renderResponse(actionResult: Any) = {

    actionResult match {
      case t: Try[_] => handleTry(t)
      case a => super.renderResponse(a)
    }
  }

  private[this] def handleTry(result: Try[_]) = {

    result match {
      case Success(response) => renderResponse(response)
      case Failure(error) => renderResponse(errorHandler(error))
    }
  }

  private def errorHandler(error: Throwable) = {
    error match {
      case e: NumberFormatException => BadRequest(MsgError("Numero informado é inválidos", e.getMessage))
      case e: NoSuchElementException => BadRequest(MsgError( "Erro nenhum elemento encontrado",e.getMessage))
      case e: RecordInvalidException => BadRequest(MsgError("Item já cadastrado", e.getMessage))
      case e: MappingException => BadRequest(MsgError("Valores Informados invalidos", e.getMessage))
      case e: JsonParseException => InternalServerError(MsgError("Não foi possivel parsear, verifique se esta passado os dados corretamente!", e.getMessage))
      case e: Exception => InternalServerError(MsgError("Erro interno !", e.getMessage))
    }

  }

  case class MsgError(var msg: String, var error: String)
}

