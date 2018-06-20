package example.programs

import cats.Monad
import cats.syntax.flatMap._
import cats.syntax.functor._
import example.algebras.BudgetAlg
import example.entities.Budget

import scala.language.higherKinds

class BudgetProgram[F[_]: Monad](alg: BudgetAlg[F]) {
  import alg._

  def insertBudget(budget: Budget): F[Unit] =
    for {
      _ <- addBudget(budget)
//      _ <- addBudget(budget)
    } yield ()

}
