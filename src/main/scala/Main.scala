import OurZio.*
import javax.management.RuntimeErrorException

object Main extends App:
  val program =
    for
      _ <- console.putStrLn("-" * 100)
      _ <- console.putStrLn("What is your name?")
      name <- ZIO.succeed("Kevin")
      _ <- console.putStrLn(s"hello $name")
      _ <- console.putStrLn("-" * 100)
    yield ()
  Runtime.default.unsafeRunSync(program)

// error <- ZIO
//   .effect(throw RuntimeException("boom"))
//   .mapError(_.getMessage)
//   .catchAll(_ => ZIO.succeed("just kidding"))
// _ <- ZIO.fail(throw RuntimeErrorException)
