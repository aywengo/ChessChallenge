# Chess Challenge #

## Input parameters:
- _m_ - amount of rows (required)
- _n_ - amount of columns (required)
- _pieces_ - string representation of chess pieces. (required)
- _step_ - step of logs. By default: 1. (optional)
- _priority_ - string representation of sequence of computation by type of chess piece. By default: QRBKN (optional) 

## Run the challenge:

`sbt "run 7 7 KKQQBBN 1000000 QRBKN"`

*Output:*

```
RomanZlyUkr:chessChallenge romeo$ sbt "run 7 7 KKQQBBN 1000000 QRBKN"
[info] Loading global plugins from /Users/romeo/.sbt/0.13/plugins
[info] Set current project to ChessChallenge (in build file:/Users/romeo/Repository/chessChallenge/)
[info] Running melnyk.co.ChessChallengeApp 7 7 KKQQBBN 1000000 QRBKN
Dimension of the chess board: 7 x 7
Pieces: N -> 1, Q -> 2, B -> 2, K -> 2
Step of log: one per 1000000
Priority of computation:  -> Q -> R -> B -> K -> N
Configuration # 1
 K  -  B  -  -  -  - 
 -  -  -  -  Q  -  - 
 -  -  -  -  -  -  Q 
 B  -  -  -  -  -  - 
 -  -  -  -  -  K  - 
 -  -  -  -  -  -  - 
 -  -  -  -  -  N  - 

Configuration # 1000001
 -  Q  -  -  -  -  - 
 -  -  -  -  -  -  - 
 B  -  -  -  -  K  - 
 -  -  -  -  -  -  - 
 -  -  -  -  -  -  Q 
 -  -  -  -  -  -  - 
 B  -  -  N  -  K  - 

Configuration # 2000001
 -  -  -  -  Q  -  - 
 -  -  -  -  -  -  K 
 -  -  -  K  -  -  - 
 B  -  -  -  -  -  - 
 -  -  -  -  -  -  B 
 N  -  -  -  -  -  - 
 -  -  -  -  -  Q  - 

Configuration # 3000001
 -  -  -  -  -  B  - 
 B  -  -  -  -  -  - 
 -  -  -  -  Q  -  - 
 -  -  -  -  -  -  Q 
 K  -  -  -  -  -  - 
 -  -  -  N  -  K  - 
 -  -  -  -  -  -  - 

Found: 3063828 solutions
Elapsed time: 23986 milliseconds ms
[success] Total time: 25 s, completed 28 СЃС–С‡. 2017 21:44:29
```
