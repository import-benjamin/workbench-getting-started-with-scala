name := "workbench-getting-started-with-scala"

version := "0.1"

scalaVersion := "2.13.3"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.0" % "test"
libraryDependencies += "org.json4s" %% "json4s-native" % "3.6.9"

testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-u", "junit")
