# tcc-fatec-back-end-scala


## Descrição

Este é o repositório da API do Projeto de TCC do curso de Redes da Fatec Osasco.
Esta é a versão 2.x foi desenvolvido em Scala (a versão 1.x foi desenvolvida com [spring boot](https://github.com/Uniliva/tcc-fatec-back-end "spring boot")), por ser uma linguagem multiparadigma, funcional e orientada a objetos, de alto rendimento.

### Tecnologias utilizadas:
**[scalatra](http://scalatra.org/ "scalatra")** - micro-framework para desenvolvimento web simples em Scala.


**[scala-activerecord](https://github.com/aselab/scala-activerecord "scala-activerecord")** -  é uma biblioteca ORM para o Scala.


**[Mysql](https://aws.amazon.com/pt/rds/ "Mysql")**– Foi criado um Relational Database Service (RDS) na Amazon AWS, com o mysql para o projeto, as tabelas são geradas automaticamente pela API, na primeira execução.


## Funcionamento

![Estrutura do projeto](https://github.com/Uniliva/Projeto-tcc-fatec/blob/master/documentos/Estrutura.png)

A API é responsável por receber os dados do [equipamento](https://github.com/Uniliva/tcc-fatec-arduino "equipamento"), notificar caso haja falha elétrica e gravar na base de dados. Além disso a API, provem os dados para o [Portal](https://github.com/Uniliva/tcc-fatec-front-end-angular2 "Portal"). Como mostra a imagem acima.

## Download & Run

Para baixar e rodar o projeto use:
```
git clone https://github.com/Uniliva/tcc-fatec-back-end-scala.git
cd  tcc-fatec-back-end-scala
sbt
jetty:start
```