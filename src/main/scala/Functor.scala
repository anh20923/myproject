import scala.util.{Success, Failure, Try}

object FunctorExampleV3 extends App {

  val numberList: List[Int] = List(7, 14, 21, 28)
  val optionalValue: Option[Int] = Some(12)
  val tryValue: Try[Int] = Success(42)

  // Performing operations using a custom transform function:
  val modifiedList = numberList.map(num => num * 4)
  val modifiedOption = optionalValue.map(num => num * 4)
  val modifiedTry = tryValue.map(num => num * 4)

  println("Without applying functors:")
  println(modifiedList)
  println(modifiedOption)
  println(modifiedTry)

  // Applying transformations using separate functions:
  def scaleList(numbersList: List[Int]): List[Int] = numbersList.map(_ * 4)
  def scaleOption(maybeInt: Option[Int]): Option[Int] = maybeInt.map(_ * 4)
  def scaleTry(possiblyInt: Try[Int]): Try[Int] = possiblyInt.map(_ * 4)

  println("Using transformation functions:")
  println(scaleList(numberList))
  println(scaleOption(optionalValue))
  println(scaleTry(tryValue))

  // Functor trait definition with a modified approach
  trait Functor[Container[_]] {
    def applyTransformation[X, Y](container: Container[X])(f: X => Y): Container[Y]
  }

  // List Functor Implementation with a custom map method
  val listFunctor = new Functor[List] {
    def applyTransformation[X, Y](items: List[X])(f: X => Y): List[Y] = items.map(f)
  }

  // Option Functor Implementation with pattern matching
  val optionFunctor = new Functor[Option] {
    def applyTransformation[X, Y](opt: Option[X])(f: X => Y): Option[Y] = opt match {
      case Some(value) => Some(f(value))
      case None => None
    }
  }

  //Functor Implementation using pattern matching
  val tryFunctor = new Functor[Try] {
    def applyTransformation[X, Y](result: Try[X])(f: X => Y): Try[Y] = result match {
      case Success(value) => Success(f(value))
      case Failure(e) => Failure(e)
    }
  }

  val scale = (value: Int) => value * 4

  println("Using functors:")
  println(listFunctor.applyTransformation(numberList)(scale))
  println(optionFunctor.applyTransformation(optionalValue)(scale))
  println(tryFunctor.applyTransformation(tryValue)(scale))
}
