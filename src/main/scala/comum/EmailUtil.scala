package comum

import com.typesafe.scalalogging.LazyLogging
import courier._, Defaults._
import models.{Estabelecimento, Sensor}
import services.DadosServices.DadoNovo
import services.EstabelecimentoService.EstabelecimentoFull

import scala.util.{Failure, Success}

object EmailUtil extends LazyLogging {
  val mailer = Mailer("smtp.gmail.com", 587)
    .auth(true)
    .as(Util.getEnv("mail.email") ,Util.getEnv("mail.senha"))
    .startTtls(true)()

  def enviar(emailTo: String, assunto: String, est: EstabelecimentoFull, sensor: Sensor, dados: DadoNovo) {
    logger.info("Enviando e-mail de alerta para "+emailTo)
     val future =  mailer(Envelope.from(Util.getEnv("mail.email").addr)
       .to(emailTo.addr)
       .subject(assunto)
       .content(Multipart().html(montaMsg(est, sensor, dados))))
       .andThen({
         case Success(e) =>  logger.info("email enviado com sucesso")
         case Failure(erro) => logger.error("erro ao enviar e-mail "+erro)
     })
  }

  def montaMsg(est: EstabelecimentoFull, sensor: Sensor, dados: DadoNovo): String ={
   return s"""
        |<h2><span style="color: #ff0000;"><strong>Alerta de falha eletrica</strong></span></h2>
        |<hr>
        |<p>Ol&aacute;, o sistema U-monitor detectou uma falha eletrica no equipamento ${sensor.codigo} - ${sensor.decricao}.</p>
        |<p>Segue dados:</p>
        |<hr>
        |<table>
        |<tbody>
        |<tr>
        |<td><strong>Loja</strong></td>
        |<td>${est.nome}</td>
        |</tr>
        |<tr>
        |<td><strong>Endere&ccedil;o</strong></td>
        |<td>${est.endereco}</td>
        |</tr>
        |</tbody>
        |</table>
        |<hr>
        |<p>Segue dados do sensor:</p>
        |<hr>
        |<table>
        |<tbody>
        |<tr>
        |<td><strong>Codigo</strong></td>
        |<td>${sensor.codigo}</td>
        |</tr>
        |<tr>
        |<td><strong>Descri&ccedil;&atilde;o</strong></td>
        |<td>${sensor.decricao}</td>
        |</tr>
        |<tr>
        |<td><strong>T-Min</strong></td>
        |<td>${sensor.temperaturaMin}ºC</td>
        |</tr>
        |<tr>
        |<td><strong>T-Max</strong></td>
        |<td>${sensor.temperaturaMax}ºC</td>
        |</tr>
        |<tr>
        |<td><strong>T-Atual</strong></td>
        |<td>${dados.temperaturaAtual}ºC</td>
        |</tr>
        |<tr>
        |<td><strong>Energia</strong></td>
        |<td>Erro</td>
        |</tr>
        |</tbody>
        |</table>
        |<hr>
        |<p><span style="color: #ff0000;">Favor verificar a condi&ccedil;&atilde;o eletrica da loja ou do equipamento.</span></p>
        |<br>
        |<p>Att</p>
        |<p>Sistema de monitoramento U-Monitor.&nbsp;</p>
      """.stripMargin
  }

}
