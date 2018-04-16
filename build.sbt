organization := "com.fatec.osasco"

name := "umonitor"

version := "1.0"

scalaVersion := "2.12.4"

val ScalatraVersion = "2.6.+"

resolvers += Classpaths.typesafeReleases

libraryDependencies ++= Seq(
  "org.scalatra" %% "scalatra" % ScalatraVersion,
  "org.scalatra" %% "scalatra-scalatest" % ScalatraVersion % "test",
  "ch.qos.logback" % "logback-classic" % "1.2.3" % "runtime",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.7.2",
  "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided",
  "org.scalatra" %% "scalatra-json"  % ScalatraVersion,
  "org.json4s" %% "json4s-jackson"   % "3.5.2",
  "org.json4s" %% "json4s-ext" % "3.5.2",
  "mysql" % "mysql-connector-java" % "6.0.6",
  "com.github.aselab" %% "scala-activerecord" % "0.4.0",
  "org.eclipse.jetty" % "jetty-webapp" % "9.1.5.v20140505" % "compile;container",
  "org.eclipse.jetty" % "jetty-plus" % "9.1.5.v20140505" % "compile;container"
)
enablePlugins(ScalatraPlugin)
enablePlugins(JettyPlugin)
containerPort in Jetty := 8060   //para mudar a porta


enablePlugins(JavaAppPackaging)

