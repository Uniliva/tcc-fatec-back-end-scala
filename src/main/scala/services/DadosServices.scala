package services

import com.github.aselab.activerecord.dsl._
import comum.EmailUtil
import models.Dados
import org.joda.time.DateTime

object DadosServices {

  def porSensorEQuantidade(sensor: Long, qtd: Int): List[Dados] = Dados.where(_.sensorID === sensor).orderBy(_.dataAtual desc).limit(qtd).toList

  def novo(dado: DadosHelper): Option[Dados] = {
    if (!dado.E) { //não tem energia
      EmailUtil.enviar(dado)
    }
    val dadosSalvos = Dados(dado.T, new DateTime(), dado.E)
    dadosSalvos.sensorID = dado.idSensor
    Some(dadosSalvos.create)
  }

  def buscaTodos(): Option[List[Dados]] = Some(Dados.toList)

  def buscaPorId(id: Long): Option[Dados] = Dados.find(id)

  def getDadosSensorLoja(id: Long, qtd: Int): Map[String, Equals] = {
    val sensor = SensorService.buscaPorId(id)
    val estabelecimento = EstabelecimentoService.buscaEstabelecimentosSensorPorId(sensor.estabelecimentoID)
      .map(e => Map("nome" -> e.nome, "endereco" -> e.endereco, "telefone" -> e.telefone, "email" -> e.email, "sensor" -> sensor.asJson))
    val dadosSensores = DadosServices.porSensorEQuantidade(id, qtd)

    val temEnergia = dadosSensores.filter(_.temEnergia).length

    Map(
      "estabelecimento" -> estabelecimento,
      "energia" -> Map("tem-energia" -> temEnergia, "falha-eletrica" -> (dadosSensores.length - temEnergia)),
      "dados" -> dadosSensores.map(dados => dados.asJson)
    )
  }

  case class DadosHelper(T: Double, E: Boolean, var idSensor: Long = 0)

}
