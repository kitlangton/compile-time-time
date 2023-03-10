package transmute

// Compile-Time Time!
// Data Transformation Boilerplate Reduction Tool
// prior art: Chimney DuckTape

case class Person(name: String, age: Int, isAlive: Boolean)
case class User(name: String, age: Int, isAlive: Boolean)

def notifyUser(user: User): Unit =
  println(s"Hey, ${user.name} (${user.age} years old) (alive: ${user.isAlive})!")
def person2User(person: Person): User =
  // Your idealized API
  person.into[User].transform
  // User(person.name, person.age, person.isAlive)

  // ERROR MESSAGE PREVIEW:
  // I don't know how to handle `isAlive: Boolean`, please let me know what you want.

object Main extends App:
  val person: Person = Person("Kit", 999, true)
  val user: User     = person2User(person)
  notifyUser(user)
