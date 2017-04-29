name := "ChessChallenge"
version := "1.0"
scalaVersion := "2.12.2"

libraryDependencies ++=
  Seq(
    "org.scalactic" %% "scalactic" % "3.0.1",
    "org.scalatest" %% "scalatest" % "3.0.1" % "test",
    "org.scalacheck" %% "scalacheck" % "1.13.4" % "test"
  )

scalacOptions in Test ++= Seq("-Yrangepos")

coverageMinimum := 50

coverageFailOnMinimum := false

coverageHighlighting := true

coverageEnabled := false

publishArtifact in Test := false

parallelExecution in Test := false

coverageExcludedPackages := "melnyk.co.ChessChallengeApp;"

// workaround for running latest scalafmt 0.7.0-RC1
def latestScalafmt = "0.7.0-RC1"
commands += Command.args("scalafmt", "Run scalafmt cli.") {
  case (state, args) =>
    val Right(scalafmt) =
      org.scalafmt.bootstrap.ScalafmtBootstrap.fromVersion(latestScalafmt)
    scalafmt.main("--non-interactive" +: args.toArray)
    state
}
