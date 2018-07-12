package comum

import com.typesafe.scalalogging.LazyLogging
import courier.Defaults._
import courier._
import models.{Dados, Estabelecimento, Sensor}
import services.DadosServices.DadosHelper
import services.SensorService

import scala.util.{Failure, Success}

object EmailUtil extends LazyLogging {
  val mailer = Mailer("smtp.gmail.com", 587)
    .auth(true)
    .as(Util.getEnv("mail.email"), Util.getEnv("mail.senha"))
    .startTtls(true)()

  def enviar(dadosSensor: DadosHelper) {
    val sensor = SensorService.buscaPorId(dadosSensor.idSensor)
   val loja = Estabelecimento.find(sensor.estabelecimentoID).head


    logger.info("Enviando e-mail de alerta para " +  loja.email)
    val future = mailer(Envelope.from(Util.getEnv("mail.email").addr)
      .to(loja.email.addr)
      .subject("Falha eletrica")
      .content(Multipart().html(montaMsg(dadosSensor, sensor, loja))))
      .andThen({
        case Success(e) => logger.info("email enviado com sucesso")
        case Failure(erro) => logger.error("erro ao enviar e-mail " + erro)
      })
  }

  def montaMsg(dados: DadosHelper, sensor: Sensor, loja:Estabelecimento): String = {
    return      s"""
         |<h2><span style="color: #ff0000;"><strong>Alerta de falha eletrica</strong></span></h2>
         |<hr>
         |<p>Ol&aacute;, o sistema U-monitor detectou uma falha eletrica no equipamento ${sensor.codigo} - ${sensor.decricao}.</p>
         |<p>Segue dados:</p>
         |<hr>
         |<table>
         |<tbody>
         |<tr>
         |<td><strong>Loja</strong></td>
         |<td>${loja.nome}</td>
         |</tr>
         |<tr>
         |<td><strong>Endere&ccedil;o</strong></td>
         |<td>${loja.endereco}</td>
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
         |<td>${dados.T}ºC</td>
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
