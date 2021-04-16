name := "workbench-getting-started-with-scala"

version := "0.1"

organization := "io.gitlab.benjaminboboul.scala"

description := "Collection of snippets to learn and test scala"

scalaVersion := "2.13.5"
coverageEnabled := true

scalacOptions += "-feature"
scalacOptions += "-deprecation"

Test / testOptions += Tests.Argument(TestFrameworks.ScalaTest, "-oDF")

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.6" % Test,
  "org.json4s" %% "json4s-jackson" % "3.6.11",
  "com.typesafe.akka" %% "akka-actor-typed" % "2.6.14",
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % "2.6.14" % Test,
  "com.typesafe.akka" %% "akka-http" % "10.2.4",
  "com.typesafe.akka" %% "akka-stream" % "2.6.14",
  "com.typesafe.akka" %% "akka-stream-testkit" % "2.6.14" % Test,
  "ch.qos.logback" % "logback-classic" % "1.2.3"
)

// Add junit report generation
testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-u", "target/unit-tests-reports")
