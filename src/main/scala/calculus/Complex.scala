package calculus

class Complex(x:Double, y:Double){
  def getPos: (Double, Double) = (x, y)
  def getReal: Double = x
  def getIm: Double = y

  def getModulus: Double = math.sqrt(math.pow(x, 2)+math.pow(y,2))
  def getArgument: Double = math.atan2(x, y)
  def getExp: String = getModulus.toString + "*exp(i*"+getArgument+")"
}
