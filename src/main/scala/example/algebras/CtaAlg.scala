package example.algebras

import example.entities.Cta
import scala.language.higherKinds

trait CtaAlg[F[_]] {

  def addCta(cta: Cta): F[Unit]
}
