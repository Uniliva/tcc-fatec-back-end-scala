package models

import java.util.Date

import com.github.aselab.activerecord._
import dsl._

case class Usuario(nome:String,email:String,senha:String,tipo:String ) extends ActiveRecord

case class Dados(temperaturaAtual:Double, dataAtual:Date) extends ActiveRecord{
  val sensorID:Long = 0;
  lazy val sensor = belongsTo[Sensor]
}

case class Estabelecimento(var nome:String,var endereco:String) extends ActiveRecord

case class Sensor(var decricao:String,var temperaturaMin:Double,var temperaturaMax:Double) extends ActiveRecord{
  lazy val dados = hasMany[Dados]
}



object Usuario extends ActiveRecordCompanion[Usuario]

object Dados extends ActiveRecordCompanion[Dados]

object Estabelecimento extends ActiveRecordCompanion[Estabelecimento]

object Sensor extends ActiveRecordCompanion[Sensor]
