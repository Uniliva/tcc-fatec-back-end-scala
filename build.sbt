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
  "org.eclipse.jetty" % "jetty-webapp" % "9.4.8.v20171121" % "container",
  "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided",
  "org.scalatra" %% "scalatra-json"  % ScalatraVersion,
  "org.json4s" %% "json4s-jackson"   % "3.5.2",
  "org.json4s" %% "json4s-ext" % "3.5.2",
  "mysql" % "mysql-connector-java" % "6.0.6",
  "com.github.aselab" %% "scala-activerecord" % "0.4.0"
)


enablePlugins(ScalatraPlugin)
enablePlugins(JettyPlugin)
containerPort in Jetty := 8060   //para mudar a porta


enablePlugins(JavaAppPackaging)

javaOptions in Universal ++= Seq(
  // -J params will be added as jvm parameters
  "-J-Xmx64m",
  "-J-Xms64m",

  // others will be added as app parameters
  "-Dproperty=true",
  "-port=8080",

  // you can access any build setting/task here
  s"-version=${version.value}"
)