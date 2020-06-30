package generator

class stringCombination {
  def generate(size_span: Iterable[Int]): Iterable[String] = {
    val chars = 'a' to 'd'
    size_span.flatMap(x => List.fill(x)(chars).flatten.combinations(x).flatMap(_.permutations).map(_.mkString("")))
  }
}