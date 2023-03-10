package transmute

// Macros.scala
import scala.quoted.*
import scala.collection.immutable.LazyList.cons

object internal:
  opaque type Into[From, To] = From
import internal.Into

extension [From](self: From) //
  def into[To]: Into[From, To] = throw new Error(s"`into` should never be called at runtime!")

extension [From, To](inline self: Into[From, To]) //
  // 1. The macro def
  inline def transform: To = ${ transformImpl[From, To]('self) }

// 2. The macro implementation
def transformImpl[From: Type, To: Type](expr: Expr[Into[From, To]])(using Quotes): Expr[To] =
  import quotes.reflect.*

  expr match
    case '{ transmute.into[From]($fromExpr)[To] } =>
      // - How can we call the `apply` method of the companion object of the `To` type
      // - How can we pass arguments to that `apply` method
      // √ How can we "select" the fields of our `From` type
      // √ How do we figure out the fields?
      //   - for each field of `To`, select that field from `From`
      // User($fromExpr.name, $fromExpr.age)
      // User.apply(person.name, person.age)
      val fromFields = getFields[From]
      val projections = fromFields.map { field =>
        projectField(fromExpr, field)
      }
      report.errorAndAbort(s"INTO: ${fromExpr.show}\nprojections:\n${projections.map(_.show).mkString("\n")}")
    case _ =>
      report.errorAndAbort(s"EXPR: ${expr.show}")

// Symbol - encyclopedia for compile-time information
def getFields[A: Type](using Quotes): List[quotes.reflect.Symbol] =
  import quotes.reflect.*
  TypeRepr.of[A].typeSymbol.caseFields

def projectField[A: Type](using Quotes)(
    expr: Expr[A],
    fieldSymbol: quotes.reflect.Symbol
): Expr[Any] =
  import quotes.reflect.*
  Select(expr.asTerm, fieldSymbol).asExpr

