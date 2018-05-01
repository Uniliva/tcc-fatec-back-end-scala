package models

import com.github.aselab.activerecord._
import com.github.aselab.activerecord.dsl._
import dsl._

object Tabelas extends ActiveRecordTables{
  val usuarios = table[Usuario]("usuariosTB")
  val dados = table[Dados]("dadosTB")
  val estabelecimento = table[Estabelecimento]("estabelecimentoTB")
  val sensor = table[Sensor]("sensorTB")
}
