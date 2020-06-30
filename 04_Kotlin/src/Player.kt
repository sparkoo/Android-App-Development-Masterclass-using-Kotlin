class Player(val name: String, private var lives: Int = 3) {
  var level = 1
  private var score = 0
  private val pico = ArrayList<String>()

  override fun toString(): String {
    return """Player(name='$name', lives=$lives, level=$level, score=$score)"""
  }


  fun add(bla: String) {
    pico.add(bla)
  }

  fun print() {
    for (eh in pico) {
      println("ja $eh")
    }
  }
}


fun main() {
  val p1 = Player("Rambo")
  println(p1.name)
  println(p1.level)
  p1.level -= 1
  println(p1.level)
  println(p1.toString())

  p1.add("eh")
  p1.add("uh")

  p1.print()
}
