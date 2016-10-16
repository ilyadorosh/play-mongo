name := "PlayMongo"

version := "1.0"

lazy val `playmongo` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq( jdbc , cache , ws   , specs2 % Test,
  "org.reactivemongo" %% "play2-reactivemongo" % "0.11.14"
)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"


//resolvers += "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

//libraryDependencies ++= Seq(
//  "org.reactivemongo" %% "play2-reactivemongo" % "0.12.0-SNAPSHOT"
//)
