package example

import java.util.UUID

import cats.implicits._
import example.entities._
import example.interpreters.{BudgetInterpreter, CtaInterpreter}
import example.programs.CtaProgram

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

object Main extends App {
  val budget =
    Budget(BudgetId(UUID.randomUUID()),
      PayeeId(UUID.randomUUID()),
      Activity("Visit"),
      Status("Done"),
      BigDecimal.apply(200)
    )
  val cta = Cta(
    StudyId(UUID.randomUUID()),
    SiteId(UUID.randomUUID()),
    BudgetId(UUID.randomUUID())
  )

  val result = new CtaProgram(
    new CtaInterpreter(),
    new BudgetInterpreter()
  )
    .insertCtaWithBudget(cta, budget)
    .fold(_.msg, _ => "All Ok")

  Await.result(result, Duration.Inf)
  println(s"Result is: ${result.toString}")
}
