fun main() {
  println("Hi")

  val a = 2
  var b = 3

  b = 4

  println("$a + $b")

  val s1 = "hello"
  val s2 = StringBuilder("hello").toString()

  println(s1 == s2)
  println(s1 === s2)
}
