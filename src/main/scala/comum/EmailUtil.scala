package comum

import com.typesafe.scalalogging.LazyLogging
import courier._
import Defaults._
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.util.{Failure, Success}

object EmailUtil extends LazyLogging {
  val mailer = Mailer("smtp.gmail.com", 587)
    .auth(true)
    .as(Util.getEnv("mail.email") ,Util.getEnv("mail.senha"))
    .startTtls(true)()

  def enviar(emailTo: String,assunto: String, msg: String) {
    logger.info("Enviando e-mail de alerta para "+emailTo)
    println(Util.getEnv("mail.email")+ " - " +Util.getEnv("mail.senha"))
     val future =  mailer(Envelope.from(Util.getEnv("mail.email").addr)
       .to(emailTo.addr)
       .subject(assunto)
       .content(Multipart().html("<h1>Alerta U-Monitor</h1> <p>" + msg + "</p>")))
       .andThen({
         case Success(e) =>  logger.info("email enviado com sucesso")
         case Failure(erro) => logger.error("erro ao enviar e-mail "+erro)
     })
    Await.ready(future, 5.seconds)
  }


}
