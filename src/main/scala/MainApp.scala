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

    if (!file.exists()) {
      println("❌ Error: Hotel_Dataset.csv not found!")
      println("\n💡 Please place Hotel_Dataset.csv in the project root folder")
      return
    }

    println(s"✅ Found dataset: ${file.getAbsolutePath}")
    
    println("\n📂 Loading data...")
    val bookings = DataUtils.loadHotelData(file.getAbsolutePath)

    if (bookings.isEmpty) {
      println("❌ Failed to load booking data. Cannot proceed.")
      return
    }

    runAnalysis(bookings)
  }

  private def runAnalysis(bookings: List[HotelBooking]): Unit = {
    println(s"\n✅ Successfully loaded ${bookings.size} booking records")
    println(s"📊 Dataset Statistics:")
    println(s"   • Unique hotels: ${bookings.map(_.hotelName).toSet.size}")
    println(s"   • Unique origin countries: ${bookings.map(_.originCountry).toSet.size}")
    println(f"   • Total revenue: $$${bookings.map(_.bookingPrice).sum}%.2f")
    println(f"   • Total visitors: ${bookings.map(_.noOfPeople).sum}")
    
    Question1Analyzer.analyze(bookings)
    Question2Analyzer.analyze(bookings)
    Question3Analyzer.analyze(bookings)
  }


}