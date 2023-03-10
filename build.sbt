ThisBuild / scalaVersion     := "3.2.2"
ThisBuild / organization     := "io.github.kitlangton"
ThisBuild / organizationName := "kitlangton"

lazy val root = (project in file("."))
  .settings(
    name := "scala-3-compile-time-business",
    libraryDependencies ++= Seq(
      "dev.zio"     %% "zio"      % "2.0.10",
      "dev.zio"     %% "zio-test" % "2.0.10" % Test,
      "com.lihaoyi" %% "pprint"   % "0.8.1",
      "com.lihaoyi" %% "fansi"    % "0.4.0"
    ),
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
  )
