package services

import com.github.aselab.activerecord.validations.Errors
import models.Sensor

object SensorService {
  def novo(sensor: SensorNovo): Either[Errors,Sensor] = Sensor(sensor.codigo,sensor.decricao,sensor.temperaturaMin,sensor.temperaturaMax).saveEither

  def buscaTodos(): Option[List[Sensor]] = Some(Sensor.toList)

  def buscaPorId(id: Long): Option[Sensor] = Sensor.find(id)

  def atualizar(sensor: SensorNovo): Either[Errors, Sensor] = {
    var sensorSalvo = Sensor.find(sensor.id).head
    sensorSalvo.decricao= sensor.decricao
    sensorSalvo.codigo= sensor.codigo
    sensorSalvo.temperaturaMax = sensor.temperaturaMax
    sensorSalvo.temperaturaMin = sensor.temperaturaMin
    sensorSalvo.saveEither
  }

  def delete(id: Long): Boolean = Sensor.find(id).head.delete


  case class SensorNovo(codigo:String, decricao: String, temperaturaMin: Double,  temperaturaMax: Double, id:Long =0)
}
