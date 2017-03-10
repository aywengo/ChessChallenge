name := "ChessChallenge"
version := "1.0"
scalaVersion := "2.11.8"

libraryDependencies ++=
  Seq(
    "org.scalactic" %% "scalactic" % "3.0.1",
    "org.scalatest" %% "scalatest" % "3.0.1" % "test",
    "org.scalacheck" %% "scalacheck" % "1.13.4" % "test")

scalacOptions in Test ++= Seq("-Yrangepos")

coverageMinimum := 50

coverageFailOnMinimum := false

coverageHighlighting := true

coverageEnabled := false

publishArtifact in Test := false

parallelExecution in Test := false

coverageExcludedPackages := "melnyk.co.ChessChallengeApp;"