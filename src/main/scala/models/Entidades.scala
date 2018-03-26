package models

import java.util.Date

import com.github.aselab.activerecord._
import dsl._
import org.joda.time.DateTime

case class Usuario(var nome: String, @Unique var email: String, var senha: String, var isAdmin: Boolean = false) extends ActiveRecord


case class Dados(var temperaturaAtual: Double, var dataAtual: DateTime) extends ActiveRecord {
  val sensorID: Long = 0
  //relacionamento
  lazy val sensor = belongsTo[Sensor]
}

case class Estabelecimento(var nome: String, var endereco: String) extends ActiveRecord

case class Sensor(decricao: String, temperaturaMin: Double, temperaturaMax: Double) extends ActiveRecord {
  lazy val dados = hasMany[Dados]
}


object Usuario extends ActiveRecordCompanion[Usuario]

object Dados extends ActiveRecordCompanion[Dados]

object Estabelecimento extends ActiveRecordCompanion[Estabelecimento]

object Sensor extends ActiveRecordCompanion[Sensor]
