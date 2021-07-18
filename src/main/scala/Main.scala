// @main def hello: Unit = 
//   println("Hello world!")
//   println(msg)
// def msg = "I was compiled by Scala 3. :)"
import OurZio.*

object Main extends App:
  val program = 
    for 
      _ <- console.putStrLn("-" * 100)
      _ <- console.putStrLn("What is your name?")
      name <- ZIO.succeed("Kevin")
      // _ <- ZIO.effect(throw RuntimeException("boom"))
      _ <- console.putStrLn(s"hello $name")
      _ <- console.putStrLn("-" * 100)
    yield () 
  Runtime.default.unsafeRunSync(program)