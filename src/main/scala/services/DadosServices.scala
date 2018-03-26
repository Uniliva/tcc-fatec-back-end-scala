package services

import models.{Dados, Sensor}
import org.joda.time.DateTime
import com.github.aselab.activerecord.dsl._

object DadosServices {
  def porSensorQuantidade(sensor: Long, qtd: Int): Option[List[Dados]] = Some(Dados.where(_.sensorID === sensor).orderBy(_.dataAtual desc).limit(qtd).toList)

  def novo(dado: DadoNovo): Option[Dados] = {
    val novo = Dados(dado.temperaturaAtual, dado.dataAtual)
    novo.sensorID = dado.sensorId
    novo.save
    Some(novo)
  }
  def buscaTodos(): Option[List[Dados]] = Some(Dados.toList)

  def buscaPorId(id: Long): Option[Dados] = Dados.find(id)

  class DadoNovo(var temperaturaAtual: Double, var dataAtual: DateTime,var sensorId: Long = 0)

}
