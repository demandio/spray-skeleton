name := "spray-skeleton"

version := "0.0.0"

scalaVersion  := "2.11.6"

scalacOptions ++= Seq("-feature", "-deprecation")

resolvers ++= Seq(
  "Sonatype Snapshots"  at "https://oss.sonatype.org/content/repositories/snapshots/",
  "Sonatype Releases"   at "https://oss.sonatype.org/content/repositories/releases/",
  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
  "spray"               at "http://repo.spray.io/"
)

libraryDependencies ++= Seq(
  "com.typesafe.akka"       %% "akka-actor"             % "2.3.11",
  "com.typesafe.akka"       %% "akka-slf4j"             % "2.3.11",
  "ch.qos.logback"          %  "logback-classic"        % "1.0.13",
  "io.spray"                %% "spray-can"              % "1.3.2",
  "io.spray"                %% "spray-routing"          % "1.3.2",
  "com.github.t3hnar"       %% "scala-bcrypt"           % "2.4",
  "com.zipfworks"           %% "sprongo"                % "1.3.1-SNAPSHOT",
  "io.spray"                %% "spray-testkit"          % "1.3.2"     % "test",
  "org.specs2"              %% "specs2"                 % "2.3.13"    % "test"
)

Revolver.settings.settings

fork := true

