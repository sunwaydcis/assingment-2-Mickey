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


  }
}