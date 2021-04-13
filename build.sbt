name := "workbench-getting-started-with-scala"

version := "0.1"

organization := "io.gitlab.benjaminboboul.scala"

description := "Collection of snippets to learn and test scala"

scalaVersion := "2.13.5"
coverageEnabled := true

scalacOptions += "-feature"
scalacOptions += "-deprecation"

Test / testOptions += Tests.Argument(TestFrameworks.ScalaTest, "-oDF")

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.5" % "test"
libraryDependencies += "org.json4s" %% "json4s-native" % "3.6.11"
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.6.14"
libraryDependencies += "com.typesafe.akka" %% "akka-actor-testkit-typed" % "2.6.14" % Test
libraryDependencies += "com.typesafe.akka" %% "akka-http-core" % "10.2.4"
libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.6.14"
libraryDependencies += "com.typesafe.akka" %% "akka-stream-testkit" % "2.6.14" % Test

// Add junit report generation
testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-u", "target/unit-tests-reports")
