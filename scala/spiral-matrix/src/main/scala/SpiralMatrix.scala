object SpiralMatrix {
  def spiralMatrix(size: Int): List[List[Int]] = {
    def generateMatrix(rows: Int, cols: Int, value: Int = 1): List[List[Int]] = {
      if (cols == 0) List()
      else (value until value + cols).toList :: generateMatrix(
        cols,
        rows - 1,
        value + cols
      ).reverse.transpose
    }

    generateMatrix(size, size)
  }
}
