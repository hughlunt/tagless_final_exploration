package example

import java.util.UUID

import cats.data.EitherT

import scala.concurrent.Future

package object entities {

  case class Error(msg: String) extends AnyVal
  type FEither[A] = EitherT[Future, Error, A]


  type Invoice = String

  case class StudyId(id: UUID) extends AnyVal
  case class SiteId(id: UUID) extends AnyVal
  case class PayeeId(id: UUID) extends AnyVal
  case class BudgetId(id: UUID) extends AnyVal

  case class Activity(desc: String) extends AnyVal
  case class Status(desc: String) extends AnyVal

  case class Cta(study: StudyId, site: SiteId, budgetId: BudgetId)

  case class Budget(id: BudgetId, who: PayeeId, what: Activity, when: Status, cost: BigDecimal)
}
