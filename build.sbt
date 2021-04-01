name := "workbench-getting-started-with-scala"

version := "0.1"

organization := "io.gitlab.benjaminboboul.scala"

description := "Collection of snippets to learn and test scala"

scalaVersion := "2.13.5"
coverageEnabled := true

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.5" % "test"
libraryDependencies += "org.json4s" %% "json4s-native" % "3.6.11"
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.6.12"
libraryDependencies += "com.typesafe.akka" %% "akka-http-core" % "10.2.4"

// Add junit report generation
testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-u", "target/unit-tests-reports")
