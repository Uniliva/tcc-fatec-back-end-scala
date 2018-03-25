package services


import models.Dados
import org.joda.time.DateTime

object DadosServices {
  def novo(dado: DadoNovo): Option[Dados] = Some(Dados(dado.temperaturaAtual, dado.dataAtual).create)

  def buscaTodos(): Option[List[Dados]] = Some(Dados.toList)

  class  DadoNovo(var temperaturaAtual:Float, var dataAtual:DateTime, sensorId:Long =0)
}
