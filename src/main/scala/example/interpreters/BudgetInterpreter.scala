package example.interpreters

import java.util.UUID

import cats.data.EitherT
import cats.implicits._
import example.algebras.BudgetAlg
import example.entities
import example.entities._

import scala.concurrent.{ExecutionContext, Future}

class BudgetInterpreter (implicit ec: ExecutionContext) extends BudgetAlg[FEither] {
  override def addBudget(budget: Budget): FEither[Unit] =
   // This part goes to database and saves budget
    EitherT.right(Future.successful(()))

  override def getBudget(id: BudgetId): FEither[Budget] =
  // Actually call DB
    EitherT.fromEither(Right(
      Budget(BudgetId(UUID.randomUUID()), PayeeId(UUID.randomUUID()), Activity("Visit"), Status("Done"), BigDecimal.apply(200))
    ))

  override def update(id: BudgetId, cost: BigDecimal): FEither[Budget] = ???
}
