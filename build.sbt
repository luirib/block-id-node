name := """idNet"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

resolvers += Resolver.mavenLocal

scalaVersion := "2.11.8"

libraryDependencies += javaJpa

libraryDependencies += "org.mockito" % "mockito-core" % "2.1.0"

libraryDependencies += javaWs % "test"

libraryDependencies += "org.hibernate" % "hibernate-core" % "5.2.5.Final"

libraryDependencies += "org.jgroups" % "jgroups" % "2.10.1.GA"

libraryDependencies += "com.signicat" % "bigchaindbconnector" % "0.0.1-SNAPSHOT"

libraryDependencies += "com.nimbusds" % "nimbus-jose-jwt" % "4.16.1"

libraryDependencies += "com.mashape.unirest" % "unirest-java" % "1.3.0"

