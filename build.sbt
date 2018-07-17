import scala.io

name := "pyspark-cassandra"

version := io.Source.fromFile("version.txt").mkString.trim

organization := "TargetHolding"

scalaVersion := "2.10.5"

credentials += Credentials(Path.userHome / ".ivy2" / ".sbtcredentials")

licenses += "Apache-2.0" -> url("http://opensource.org/licenses/Apache-2.0") 

libraryDependencies ++= Seq(
	"com.datastax.spark" %% "spark-cassandra-connector-java" % "1.6.0-M1"
)

spName := "TargetHolding/pyspark-cassandra"

sparkVersion := "1.5.1"

sparkComponents ++= Seq("streaming", "sql")

javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

assemblyOption in assembly := (assemblyOption in assembly).value.copy(
	includeScala = false
)
spIgnoreProvided := true

ivyScala := ivyScala.value map {
  _.copy(overrideScalaVersion = true)
}

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", "MANIFEST.MF") => MergeStrategy.discard
  case PathList("META-INF", xs@_*) => MergeStrategy.last
  case PathList("pyspark_cassandra", "__pycache__", xs@_*) => MergeStrategy.discard
  case PathList("pyspark_cassandra", "__pycache__") => MergeStrategy.discard  
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}

unmanagedResourceDirectories in Compile += { baseDirectory.value / "python" }
