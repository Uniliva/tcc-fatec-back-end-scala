# tcc-fatec-back-end-scala


##Descrição

Este é  repositório da API do Projeto de TCC do curso de Redes da Fatec Osasco.
Esta e a versão 2.x  foi desenvolvido utilizando Scala. Por ser uma linguagem multiparadigma, funcional e orientada a objetos, de alto rendimento.

### Tecnologias utilizadas:
**[scalatra](http://scalatra.org/ "scalatra")** - micro-framework para desenvolviemnto de APIs web simples em Scala.


**[scala-activerecord](https://github.com/aselab/scala-activerecord "scala-activerecord")** -  é uma biblioteca ORM para o Scala.


**[Mysql](https://aws.amazon.com/pt/rds/ "Mysql") **– Foi criado um Amazon Relational Database Service (RDS) com o mysql pra o projeto.


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