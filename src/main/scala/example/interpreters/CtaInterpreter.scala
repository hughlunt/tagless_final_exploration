package example.interpreters

import cats.data.EitherT
import cats.implicits._
import example.algebras.CtaAlg
import example.entities
import example.entities.{Error, FEither}

import scala.concurrent.{ExecutionContext, Future}

class CtaInterpreter (implicit ec: ExecutionContext) extends CtaAlg[FEither] {
  override def addCta(cta: entities.Cta): FEither[Unit] =
//    EitherT.left(Future.successful(Error("Oh no, can't add CTA")))
    EitherT.right(Future.successful(()))
}
