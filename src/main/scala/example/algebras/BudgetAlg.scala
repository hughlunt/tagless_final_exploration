package example.algebras

import example.entities.{Budget, BudgetId}
import scala.language.higherKinds

trait BudgetAlg[F[_]] {

  def addBudget(budget: Budget): F[Unit]

  def update(id: BudgetId, cost: BigDecimal): F[Budget]

  def getBudget(id: BudgetId): F[Budget]
}

































