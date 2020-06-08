fun main() {
  var a: String? = null
  println(a)

  a = "bla"
  println(a)


  var b: String? = null
  println(a ?: "a was null")
  println(b ?: "b was null")

  val i = 2
  val j: Int? = null

  val k = i + (j ?: 1)
  println(k)
  val z = 3
  println(z)
}