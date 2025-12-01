// MainApp.scala
object MainApp {

  def main(args: Array[String]): Unit = {
    println("Hotel Bookings Data Analysis")
    println("=" * 60)

    // Load data - adjust the path according to your project structure
    val filePath = "src/main/resources/Hotel_Dataset.csv"
    val bookings = DataUtils.loadHotelData(filePath)

    println(s"Loaded ${bookings.size} booking records")
    println()

    // Execute all analyses
    Question1Analyzer.analyze(bookings)
    Question2Analyzer.analyze(bookings)
    Question3Analyzer.analyze(bookings)

    // Display polymorphism explanation
    displayPolymorphismInfo()
  }

  def displayPolymorphismInfo(): Unit = {
    println("=" * 60)
    println("POLYMORPHISM IN COLLECTION API")
    println("=" * 60)
    println("""
TYPES OF POLYMORPHISM USED:

1. PARAMETRIC POLYMORPHISM (Generics):
   - List[HotelBooking], Map[String, Int] - type-safe collections
   - Methods work with any type: groupBy[A], map[B]

2. SUBTYPE POLYMORPHISM:
   - HotelBooking extends Analyzable trait
   - All collections implement Traversable traits

3. HIGHER-ORDER FUNCTIONS:
   - map, filter, groupBy take functions as parameters
   - Enables behavior parameterization

BENEFITS DEMONSTRATED:
- Type Safety: Compile-time checking prevents errors
- Code Reuse: Same operations work across different data types
- Flexibility: Easy to modify analysis logic
- Composition: Operations can be chained (groupBy -> mapValues -> maxBy)

LIMITATIONS:
- Complexity with nested generics
- Performance overhead from abstraction
- Learning curve for functional patterns
""")
  }
}