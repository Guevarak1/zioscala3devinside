object OurZio:
  final case class ZIO[+E, +A](thunk: () => Either[E, A]):
    //variance
    def flatMap[E1 >: E, B](azb: A => ZIO[E1, B]): ZIO[E1, B] =
      ZIO { () =>
        val errorOrA = thunk()
        val zErrorOrB = errorOrA match
          case Right(a) => azb(a)
          case Left(e)  => ZIO.fail(e)
        val errorOrB = zErrorOrB.thunk()
        errorOrB
      }

    def map[B](ab: A => B): ZIO[E, B] =
      ZIO { () =>
        val errorOrA = thunk()
        val errorOrB = errorOrA match
          case Right(a) => Right(ab(a))
          case Left(e)  => Left(e)
        errorOrB
      }

    def catchAll[E2, A1 >: A](h: E => ZIO[E2, A1]): ZIO[E2, A1] =
      ZIO { () =>
        val errorOrA = thunk()
        val zErrorOrB = errorOrA match
          case Right(a) => ZIO.succeed(a)
          case Left(e)  => h(e)
        val errorOrB = zErrorOrB.thunk()
        errorOrB
      }
    def mapError[E2](h: E => E2): ZIO[E2, A] =
      ZIO { () =>
        val errorOrA = thunk()
        val errorOrB = errorOrA match
          case Right(a) => Right(a)
          case Left(e)  => Left(h(e))
          
        errorOrB
      }
  end ZIO

  object ZIO:
    def succeed[A](a: => A): ZIO[Nothing, A] =
      ZIO(() => Right(a))
    def fail[E](e: => E): ZIO[E, Nothing] =
      ZIO(() => Left(e))
    def effect[A](a: => A): ZIO[Throwable, A] =
      ZIO { () =>
        try Right(a)
        catch ex => Left(ex)
      }

  object console:
    def putStrLn(line: => String) =
      ZIO.succeed(println(line))

    val getStrLn = ZIO.succeed(scala.io.StdIn.readLine())
  object Runtime:
    object default:
      def unsafeRunSync[E, A](zio: => ZIO[E, A]): Either[E, A] =
        zio.thunk()
