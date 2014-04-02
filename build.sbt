name := "spray-skeleton"

version := "0.0.0"

scalaVersion  := "2.10.3"

scalacOptions ++= Seq("-feature", "-deprecation")

resolvers ++= Seq(
  "sonatype-releases"   at "https://oss.sonatype.org/content/repositories/releases/",
  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
  "Sonatype Snapshots"  at "http://oss.sonatype.org/content/repositories/snapshots/",
  "spray"               at "http://repo.spray.io/"
)

libraryDependencies ++= Seq(
  "com.typesafe.akka"       %% "akka-remote"            % "2.2.3",
  "com.typesafe.akka"       %% "akka-slf4j"             % "2.2.3",
  "ch.qos.logback"          %  "logback-classic"        % "1.0.7",
  "io.spray"                %  "spray-can"              % "1.2.0",
  "io.spray"                %  "spray-routing"          % "1.2.0"
)
