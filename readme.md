# Chess Challenge #

## Algorithm 

The idea is simple: find unique board configurations putting pieces consequently according to priority and count every single one if it's safe for every chess piece in set.
 
If put Queen onto empty 7x7 board, it lefts 19-25 unsafe positions for other pieces. 
Accordingly: Rook: 13; Bishop: 7 - 13; Knight: 2 - 8; King: 4 - 8. 
In this way, the amount of following iterations reduces drastically. That makes time of execution short. 

## Input parameters:
- _m_ - amount of rows (required)
- _n_ - amount of columns (required)
- _pieces_ - string representation of chess pieces. (required)
- _step_ - step of logs. By default: 1. (optional)
- _priority_ - string representation of sequence of computation by type of chess piece. By default: QRBKN (optional) 

## Next steps:
- since the sequence of combination is radial, iterations might be calculated in parallel

## Tests and coverage 

In order to run tests with coverage allow coverage in build.sbt (set coverageEnabled := true) and use following commands:
`$ sbt clean coverage test`
`$ sbt coverageReport`

*Output:*

```
[info] Written HTML coverage report [../target/scala-2.11/scoverage-report/index.html]
[info] Statement coverage.: 100.00%
[info] Branch coverage....: 100.00%
[info] Coverage reports completed
[info] Coverage is above minimum [100.00% > 50.0%]
[info] All done. Coverage was [100.00%]

```
NOTE: ChessChallengeApp class was excluded from the coverage since it's threaten as helper class without core logic.

## Run the challenge:

`sbt "run 7 7 KKQQBBN 1000000 QRBKN"`

*Output:*

```
RomanZlyUkr:chessChallenge romeo$ sbt "run 7 7 KKQQBBN 1000000"
 [info] Loading global plugins from /Users/romeo/.sbt/0.13/plugins
 [info] Set current project to ChessChallenge (in build file:/Users/romeo/Repository/chessChallenge/)
 [info] Running melnyk.co.ChessChallengeApp 7 7 KKQQBBN 1000000
 Dimension of the chess board: 7 x 7
 Pieces: N -> 1, Q -> 2, B -> 2, K -> 2
 Step of log: one per 1000000
 Priority of computation:  -> Q -> R -> B -> K -> N
 Configuration # 1
  K  -  -  -  -  -  - 
  -  -  -  -  Q  -  - 
  N  B  -  -  -  -  - 
  -  -  -  K  -  -  - 
  -  -  -  -  -  B  - 
  -  -  -  -  -  -  - 
  -  -  Q  -  -  -  - 
 
 Configuration # 1000001
  -  -  -  -  -  -  - 
  N  -  -  -  -  -  - 
  -  -  -  -  -  Q  - 
  -  -  -  B  -  -  - 
  -  -  -  -  -  -  Q 
  B  -  -  -  K  -  - 
  -  -  K  -  -  -  - 
 
 Configuration # 2000001
  -  -  -  -  Q  -  - 
  K  -  -  -  -  -  - 
  -  -  -  Q  -  -  - 
  K  -  -  -  -  -  - 
  -  -  -  -  -  -  B 
  -  -  -  -  -  -  - 
  N  B  -  -  -  -  - 
 
 Configuration # 3000001
  -  -  K  -  N  -  - 
  -  -  -  -  -  -  - 
  -  Q  -  -  -  -  - 
  -  -  -  -  -  -  B 
  -  -  -  -  -  -  - 
  -  -  B  -  -  K  - 
  Q  -  -  -  -  -  - 
 
 Found: 3063828 solutions
 Elapsed time: 12395 milliseconds ms
 [success] Total time: 13 s, completed 30 січ. 2017 19:51:22
```
