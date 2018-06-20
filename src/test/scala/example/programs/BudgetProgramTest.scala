package example.programs

import java.util.UUID

import cats.Id
import cats.data.Writer
import cats.implicits._
import example.algebras.BudgetAlg
import example.entities._
import org.scalatest.{FlatSpec, Matchers}

class BudgetProgramTest extends FlatSpec with Matchers {

  object TestInterpreter extends BudgetAlg[Id] {
    override def addBudget(budget: Budget): Id[Unit] = ()
    override def getBudget(id: BudgetId): Id[Budget] = ???

    override def update(id: BudgetId, cost: BigDecimal): Id[Budget] = ???
  }

  // Trivial test
  it should "add a budget" in {
    val dummyBudget = Budget(
      BudgetId(UUID.randomUUID()),
      PayeeId(UUID.randomUUID()),
      Activity("Visit"),
      Status("Done"),
      BigDecimal.apply(200)
    )
    new BudgetProgram[Id](TestInterpreter).insertBudget(dummyBudget) shouldBe ()
  }
//
  // Less trivial test
  // Ensure program is actually correct
  type Log[A] = Writer[List[String], A]
  object LoggingInterpreter extends BudgetAlg[Log] {
    override def addBudget(budget: Budget): Log[Unit] = Writer(List("I've added a record"), ())
    override def getBudget(id: BudgetId): Log[Budget] = ???

    override def update(id: BudgetId, cost: BigDecimal): Log[Budget] = ???
  }
//
  it should "only add stuff once" in {
    val dummyBudget = Budget(BudgetId(UUID.randomUUID()), PayeeId(UUID.randomUUID()), Activity("Visit"), Status("Done"), BigDecimal.apply(200))
    val result: Log[Unit] =
      new BudgetProgram[Log](LoggingInterpreter).insertBudget(dummyBudget)

    result.value shouldBe ()
    result.written shouldBe List("I've added a record")
  }
}
