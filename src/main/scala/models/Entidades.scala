package models

import java.util.Date

import com.github.aselab.activerecord._
import dsl._

case class Usuario(nome:String,email:String,senha:String,isAdmin:Boolean = false ) extends ActiveRecord{
  val grupoId:Option[Long] = None

  lazy val grupo = belongsTo[Grupo]
}

case class Grupo(nome:String)extends ActiveRecord{
  lazy val usuarios = hasMany[Usuario]
  lazy val usuariosAdmin = hasMany[Usuario](conditions = Map("isAdmin" -> true))
}

case class Dados(temperaturaAtual:Double, dataAtual:Date) extends ActiveRecord{
  val sensorID:Option[Long]=None
  //relacionamento
  lazy val sensor = belongsTo[Sensor]
}

case class Estabelecimento(var nome:String,var endereco:String) extends ActiveRecord

case class Sensor(var decricao:String,var temperaturaMin:Double,var temperaturaMax:Double) extends ActiveRecord{
  lazy val dados = hasMany[Dados]
}



object Usuario extends ActiveRecordCompanion[Usuario]

object Grupo extends ActiveRecordCompanion[Grupo]

object Dados extends ActiveRecordCompanion[Dados]

object Estabelecimento extends ActiveRecordCompanion[Estabelecimento]

object Sensor extends ActiveRecordCompanion[Sensor]
