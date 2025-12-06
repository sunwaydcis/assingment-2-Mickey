// MainApp.scala
object MainApp {

  /**
   * Main entry point demonstrating proper collection workflow:
   * 1. File discovery → 2. Data loading → 3. Analysis pipeline
   * Uses Option/validation patterns common in Scala collections.
   */
  def main(args: Array[String]): Unit = {
    println("🏨 HOTEL BOOKING DATA ANALYSIS")
    println("=" * 70)

    // Using utility method to find file (returns Option-like behavior)
    val file = DataUtils.findDatasetFile()

    // Early validation pattern - prevents processing invalid data
    if (!file.exists()) {
      println("❌ Error: Hotel_Dataset.csv not found!")
      println("\n💡 Please place Hotel_Dataset.csv in the project root folder")
      return
    }

    println(s"✅ Found dataset: ${file.getAbsolutePath}")


    println("\n📂 Loading data...")
    // Loads data into List[HotelBooking] - fundamental collection type
    val bookings = DataUtils.loadHotelData(file.getAbsolutePath)

    // isEmpty check for safe processing
    if (bookings.isEmpty) {
      println("❌ Failed to load booking data. Cannot proceed.")
      return
    }

    // Delegates to analysis method with immutable collection
    runAnalysis(bookings)
  }

  /**
   * Showcases advanced collection operations in a clean, readable manner.
   * Each statistical line demonstrates a different collection transformation:
   * - map + toSet + size = unique count
   * - map + sum = aggregation
   * All operations maintain immutability and are side-effect free.
   */

  private def runAnalysis(bookings: List[HotelBooking]): Unit = {
    // Multiple collection transformations in concise expressions
    println(s"\n✅ Successfully loaded ${bookings.size} booking records")
    println(s"📊 Dataset Statistics:")

    // map→toSet→size pattern for unique counting
    println(s"   • Unique hotels: ${bookings.map(_.hotelName).toSet.size}")
    // Reused pattern for different field
    println(s"   • Unique origin countries: ${bookings.map(_.originCountry).toSet.size}")
    // map→sum pattern with formatted output
    println(f"   • Total revenue: $$${bookings.map(_.bookingPrice).sum}%.2f")
    // Similar aggregation for visitor count
    println(f"   • Total visitors: ${bookings.map(_.noOfPeople).sum}")

    // Delegates to specialized analyzers - modular collection processing
    Question1Analyzer.analyze(bookings)
    Question2Analyzer.analyze(bookings)
    Question3Analyzer.analyze(bookings)

    // Show polymorphism explanation
    showPolymorphismExplanation()
  }

  private def showPolymorphismExplanation(): Unit = {
    println("\n" + "=" * 70)
    println("POLYMORPHISM IN COLLECTION API - EXPLANATION")
    println("=" * 70)

    println("""
TYPES OF POLYMORPHISM DEMONSTRATED:

1. PARAMETRIC POLYMORPHISM (Generics):
   • List[HotelBooking] - Type-safe collection that can hold HotelBooking objects
   • Map[String, Int] - Generic key-value pairs
   • Methods like groupBy[A], map[B], filter work with any data type

2. SUBTYPE POLYMORPHISM:
   • All Scala collections (List, Map, Set) implement common traits like Traversable
   • This allows uniform interfaces across different collection types

3. HIGHER-ORDER FUNCTIONS:
   • Functions that take other functions as parameters
   • Examples: bookings.map(_.hotelName), bookings.filter(_.bookingPrice > 100)

CONCRETE EXAMPLES FROM THIS PROGRAM:

1. bookings.groupBy(_.originCountry)
   • Returns: Map[String, List[HotelBooking]]
   • Polymorphism: groupBy works on any collection type and any element type

2. .mapValues(_.size)
   • Transforms each value in the Map without changing keys
   • Returns: Map[String, Int]

3. .maxBy(_._2)
   • Higher-order function that finds maximum based on a criterion
   • Uses lambda expression: _._2 means "second element of tuple"

4. bookings.map(_.bookingPrice).sum
   • map transforms HotelBooking → Double
   • sum works on any numeric collection

BENEFITS OF THIS APPROACH:

• TYPE SAFETY: Compile-time checking prevents runtime errors
• CODE REUSABILITY: Same patterns work with different data types
• CONCISENESS: Fewer lines of code than imperative alternatives
• READABILITY: Declarative style clearly expresses intent
• COMPOSABILITY: Operations can be chained together

LIMITATIONS:

• LEARNING CURVE: Functional programming concepts take time to master
• DEBUGGING DIFFICULTY: Long chains can be hard to debug
• PERFORMANCE OVERHEAD: Some abstractions have runtime costs
• MEMORY USAGE: Intermediate collections in chains use more memory

WHY THIS APPROACH IS EFFECTIVE:

The use of polymorphic collection operations allows us to answer
complex business questions in just a few lines of code, making the
analysis both efficient and maintainable.
""")
  }
}