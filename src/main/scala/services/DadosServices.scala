package services

import models.{Dados, Sensor}
import org.joda.time.DateTime
import com.github.aselab.activerecord.dsl._

object DadosServices {
  def porSensorQuantidade(sensor: Long, qtd: Int): List[Dados] = Dados.where(_.sensorID === sensor).orderBy(_.dataAtual desc).limit(qtd).toList

  def novo(dado: DadoNovo): Option[Dados] = {
    val novo = Dados(dado.temperaturaAtual, dado.dataAtual, dado.temEnergia)
    novo.sensorID = dado.sensorId
    novo.save
    Some(novo)
  }
  def buscaTodos(): Option[List[Dados]] = Some(Dados.toList)

  def buscaPorId(id: Long): Option[Dados] = Dados.find(id)

  class DadoNovo(var temperaturaAtual: Double, var dataAtual: DateTime,var sensorId: Long = 0, var temEnergia:Boolean = false)
  class DadoSensor(var temperaturaAtual: Double, var temEnergia:Boolean = false)
  class DadoSensor2(var T: Double, var E:Boolean = false)

}
