object MainApp {
  /**
   * Main entry point demonstrating sophisticated collection workflow.
   * Shows mastery of file I/O, data validation, and pipeline processing.*
   * Collection Pipeline: 1. File Discovery → 2. Data Loading → 3. Analysis
   */
  def main(args: Array[String]): Unit = {
    println("🏨 HOTEL BOOKING DATA ANALYSIS")
    println("=" * 70)

    // File discovery with utility method (returns File object)
    val file = DataUtils.findDatasetFile()
    // Early validation pattern - prevents processing invalid files
    if (!file.exists()) {
      println("❌ Error: Hotel_Dataset.csv not found!")
      println("\n💡 Please place Hotel_Dataset.csv in the project root folder")
      return // Early return prevents unnecessary processing
    }

    println(s"✅ Found dataset: ${file.getAbsolutePath}")

    // Load the data using collection-based parsing
    println("\n📂 Loading data...")
    val bookings = DataUtils.loadHotelData(file.getAbsolutePath)

    // Collection state validation using isEmpty
    if (bookings.isEmpty) {
      println("❌ Failed to load booking data. Cannot proceed.")
      return // Early return for empty collections
    }
    // Delegate to analysis function with immutable collection
    runAnalysis(bookings)
  }

  /**
   * Analysis function showcasing sophisticated collection operations.
   * Each statistical line demonstrates a different collection transformation pattern.
   *
   * Collection Operations Demonstrated:
   * 1. Unique counting: map → toSet → size
   * 2. Numerical summation: map → sum
   * 3. Formatted output: string interpolation with formatting
   */
  private def runAnalysis(bookings: List[HotelBooking]): Unit = {
    // Collection size access with clear messaging
    println(s"\n✅ Successfully loaded ${bookings.size} booking records")
    println(s"📊 Dataset Statistics:")
    // Unique hotel count using map→toSet→size pattern
    println(s"   • Unique hotels: ${bookings.map(_.hotelName).toSet.size}")
    // Reuse of pattern for different field
    println(s"   • Unique origin countries: ${bookings.map(_.originCountry).toSet.size}")
    // Aggregation with formatted currency output
    println(f"   • Total revenue: $$${bookings.map(_.bookingPrice).sum}%.2f")
    // Numerical summation for visitor count
    println(f"   • Total visitors: ${bookings.map(_.noOfPeople).sum}")

    // Delegate to specialized analyzers - modular collection processing
    Question1Analyzer.analyze(bookings)
    Question2Analyzer.analyze(bookings)
    Question3Analyzer.analyze(bookings)
  }


}