package services
import com.github.aselab.activerecord.dsl._
import models.{Grupo, Usuario}

object GrupoService {
  def novo(nome: String): Option[Grupo] = Some(Grupo(nome).create)

  def buscaTodos(): Option[List[Grupo]] = Some(Grupo.toList)

  def buscaPorId(id: Long): Option[Grupo] = Grupo.find(id)

  def atualizar(id:Long,novoNome: String): Option[Grupo] = {
    Grupo.find(id).head match {
      case grupo => Some(grupo.copy(nome = novoNome).update)
    }
  }

}
