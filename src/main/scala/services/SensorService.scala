package services

import com.github.aselab.activerecord.dsl._
import models.{Estabelecimento, Sensor}

object SensorService {
  /*
 SELECT A.NOME "A.NOME",
       B.VALOR "B.VALOR"
  FROM TABELA_A A
 INNER JOIN TABELA_B B ON B.CODIGO = A.CODIGO

 */
  def novo(sensor: SensorNovo): Option[Sensor] = Some(Sensor(sensor.codigo, sensor.decricao, sensor.temperaturaMin, sensor.temperaturaMax).create)

  //def buscaTodos(): Option[List[Sensor]] = Some(Sensor.toList)


  def buscaTodos(): Option[List[SensorLoja]] = {
    val x = Sensor.joins[Estabelecimento](
      // join on
      (sensor, estabelecimento) => sensor.estabelecimentoID === estabelecimento.id
    ).select(
      (sensor, estabelecimento) => SensorLoja(sensor.codigo,  sensor.id,sensor.decricao, sensor.temperaturaMin, sensor.temperaturaMax, estabelecimento.id)
    ).toList
    Some(x)
  }

  def buscaPorId(id: Long): Option[Sensor] = Sensor.find(id)

  def buscaPorCodigo(codigo: String): Sensor = Sensor.findBy("codigo",codigo).head

  def atualizar(sensor: SensorNovo): Option[Sensor] = {
    var sensorSalvo = Sensor.find(sensor.id).head
    sensorSalvo.decricao = sensor.decricao
    sensorSalvo.codigo = sensor.codigo
    sensorSalvo.temperaturaMax = sensor.temperaturaMax
    sensorSalvo.temperaturaMin = sensor.temperaturaMin
    sensorSalvo.estabelecimentoID = sensor.idEstabelecimento
    Some(sensorSalvo.update)
  }

  def delete(id: Long): Boolean = Sensor.find(id).head.delete


  case class SensorNovo(codigo: String, decricao: String, temperaturaMin: Double, temperaturaMax: Double, idEstabelecimento: Long, id: Long = 0)

  case class SensorLoja(codigo: String, id: Long, decricao: String, temperaturaMin: Double, temperaturaMax: Double,  idEstabelecimento: Long)

}
