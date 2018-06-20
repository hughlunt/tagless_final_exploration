package example.programs

import java.util.UUID

import cats.Monad
import cats.syntax.flatMap._
import cats.syntax.functor._
import example.algebras.{BudgetAlg, CtaAlg}
import example.entities.{Budget, Cta, Invoice}

import scala.language.higherKinds

trait InvoiceAlg[F[_]] {
  def fetchInvoice(id: UUID): F[Invoice]

  def updateState(invoice: Invoice, state: String): F[Invoice]

  def addInvoice(invoice: Invoice): F[Unit]
}

class CtaProgram[F[_]: Monad](ctaAlg: CtaAlg[F], budgetAlg: BudgetAlg[F], invoiceAlg: InvoiceAlg[F]) {
  import ctaAlg._
  import budgetAlg._

  def insertCtaWithBudget(cta: Cta, budget: Budget): F[Unit] = for {
    _ <- addCta(cta)
    _ <- addBudget(budget)
  } yield ()

  import invoiceAlg._
  def updateInvoiceState(id:UUID, state: String): F[Invoice] = for {
    invoice <- fetchInvoice(id)
    updateInvoice <- updateState(invoice, state)
    _ <- addInvoice(updateInvoice)
  } yield updateInvoice
}
