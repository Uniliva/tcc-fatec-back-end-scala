package models

import com.github.aselab.activerecord._
import com.github.aselab.activerecord.dsl._
import org.joda.time.DateTime

case class Usuario(@Unique email: String, nome: String, @Unique cpf: String ,senha: String, telefone: String, isAdmin: Boolean = false )  extends ActiveRecord

case class Dados(temperaturaAtual: Double, var dataAtual: DateTime, temEnergia: Boolean) extends ActiveRecord {
  var sensorID: Long = 0
  lazy val sensor = belongsTo[Sensor](foreignKey = "sensorID")
}

case class Estabelecimento(var nome: String, var endereco: String, var telefone: String, var email: String) extends ActiveRecord {
  lazy val sensores = hasMany[Sensor](foreignKey = "estabelecimentoID")
}

case class Sensor(@Unique var codigo: String, var decricao: String, var temperaturaMin: Double, var temperaturaMax: Double) extends ActiveRecord {
  var estabelecimentoID: Long = 0
  lazy val estabelecimento = belongsTo[Estabelecimento](foreignKey = "estabelecimentoID")
  lazy val dados = hasMany[Dados](foreignKey  = "sensorID")
}



object Usuario extends ActiveRecordCompanion[Usuario]

object Dados extends ActiveRecordCompanion[Dados]

object Estabelecimento extends ActiveRecordCompanion[Estabelecimento]

object Sensor extends ActiveRecordCompanion[Sensor]
