package models

import com.github.aselab.activerecord._
import com.github.aselab.activerecord.dsl._

object Tabelas extends ActiveRecordTables{
  val usuario = table[Usuario]("usuariosTB")
  val dados = table[Dados]("DadosTB")
  val estabelecimento = table[Estabelecimento]("estabelecimentoTB")
  val sensor = table[Sensor]("sensorTB")
}
