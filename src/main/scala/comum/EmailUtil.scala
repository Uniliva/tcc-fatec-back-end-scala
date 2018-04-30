package comum

import com.typesafe.scalalogging.LazyLogging
import courier._
import courier.Defaults._
import comum.Util
import javax.mail.internet.InternetAddress

import scala.util.Try

object EmailUtil extends LazyLogging {
  val mailer = Mailer("smtp.gmail.com", 587)
    .auth(true)
    .as(Util.getEnv("mail.email") ,Util.getEnv("mail.senha"))
    .startTtls(true)()

val email = new InternetAddress(Util.getEnv("mail.email"))

  def enviar(emailTo: String,assunto: String, msg: String) {
      println(this.email)
      println(mailer)
      mailer(Envelope.from(email).to(new InternetAddress(emailTo)).subject(assunto).content(Multipart().html("<h1>Alerta U-Monitor</h1> <p>" + msg + "</p>"))).andThen{
        case x:Exception => logger.warn("Erro"+x.getMessage )
        case x => logger.warn("Erro sem erro" )
      }

  }


}
