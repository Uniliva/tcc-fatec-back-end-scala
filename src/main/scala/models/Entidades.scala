package models

import java.util.Date

import com.github.aselab.activerecord._
import dsl._
import org.joda.time.DateTime

case class Usuario(var nome: String, @Unique var email: String, var senha: String, var isAdmin: Boolean = false) extends ActiveRecord


case class Dados(var temperaturaAtual: Double, var dataAtual: DateTime, var temEnergia: Boolean) extends ActiveRecord {
  var sensorID: Long = 0
  //relacionamento
  lazy val sensor = belongsTo[Sensor](foreignKey = "sensorID")
}

case class Estabelecimento(var nome: String, var endereco: String, var telefone: String, var email: String) extends ActiveRecord {
  lazy val sensores = hasMany[Sensor](foreignKey = "estabelecimentoID")
}

case class Sensor(@Unique var codigo:String, var decricao: String,var temperaturaMin: Double, var temperaturaMax: Double) extends ActiveRecord {
  var estabelecimentoID: Long = 0
  lazy val estabelecimento = belongsTo[Estabelecimento](foreignKey = "estabelecimentoID")
  lazy val dados = hasMany[Dados](foreignKey = "sensorID")
}


object Usuario extends ActiveRecordCompanion[Usuario]

object Dados extends ActiveRecordCompanion[Dados]

object Estabelecimento extends ActiveRecordCompanion[Estabelecimento]

object Sensor extends ActiveRecordCompanion[Sensor]
