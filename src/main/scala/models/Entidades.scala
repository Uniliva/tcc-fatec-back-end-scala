package models

import java.util.Date

import com.github.aselab.activerecord._
import dsl._

case class Usuario(nome:String, @Unique email:String, senha:String, isAdmin:Boolean = false ) extends ActiveRecord{
  val grupoId:Long = 0
  lazy val grupo = belongsTo[Grupo]
}

case class Grupo(nome:String)extends ActiveRecord{
  lazy val usuarios = hasMany[Usuario]
  lazy val usuariosAdmin = hasMany[Usuario](conditions = Map("isAdmin" -> true))
}

case class Dados(temperaturaAtual:Double, dataAtual:Date) extends ActiveRecord{
  val sensorID:Long=0
  //relacionamento
  lazy val sensor = belongsTo[Sensor]
}

case class Estabelecimento( nome:String, endereco:String) extends ActiveRecord

case class Sensor( decricao:String, temperaturaMin:Double, temperaturaMax:Double) extends ActiveRecord{
  lazy val dados = hasMany[Dados]
}



object Usuario extends ActiveRecordCompanion[Usuario]

object Grupo extends ActiveRecordCompanion[Grupo]

object Dados extends ActiveRecordCompanion[Dados]

object Estabelecimento extends ActiveRecordCompanion[Estabelecimento]

object Sensor extends ActiveRecordCompanion[Sensor]
