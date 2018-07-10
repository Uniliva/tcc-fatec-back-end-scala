package base

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.JsonMappingException
import com.github.aselab.activerecord.RecordInvalidException
import com.typesafe.scalalogging.LazyLogging
import org.json4s.MappingException
import org.scalatra.{BadRequest, InternalServerError}
import org.scalatra.servlet.ServletBase
import org.squeryl.SquerylSQLException

import scala.util.Try
import scala.util.Success
import scala.util.Failure

trait TrySupport extends ServletBase with LazyLogging {


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
      case e: NumberFormatException => BadRequest(MsgError("Numero informado é inválidos", e))
      case e: NoSuchElementException => BadRequest(MsgError( "Erro nenhum elemento encontrado",e))
      case e: RecordInvalidException => BadRequest(MsgError("Item já cadastrado", e))
      case e: MappingException => BadRequest(MsgError("Valores Informados invalidos", e))
      case e: JsonParseException => InternalServerError(MsgError("Não foi possivel parsear, verifique se esta passado os dados corretamente!", e))
      case e: SquerylSQLException =>  BadRequest(MsgError("Item não cadastrado", e))
      case e: Exception => InternalServerError(MsgError("Erro interno !", e))
    }

  }

  case class Error(var msg: String, var error: String)

  def MsgError( msg: String, error: Exception): Error = {
    logger.error("Erro na aplicacao: "+ msg)
    logger.error("Erro na aplicacao: msg :"+ error.getMessage)
    Error(msg,error.getMessage)
  }
}

