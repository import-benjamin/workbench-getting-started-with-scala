name := "workbench-getting-started-with-scala"

version := "0.1"

scalaVersion := "2.13.3"
coverageEnabled := true

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.0" % "test"
libraryDependencies += "org.json4s" %% "json4s-native" % "3.6.9"
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.6.6"

// Add junit report generation
testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-u", "target/unit-tests-reports")
