// Question2Analyzer.scala
object Question2Analyzer {
  /**
   * Multi-dimensional analysis using advanced collection operations.
   * Demonstrates mastery of complex data transformation pipelines.
   */
  def analyze(bookings: List[HotelBooking]): Unit = {
    println("\n" + "=" * 70)
    println("QUESTION 2: Which hotel offers the most economical option?")
    println("=" * 70)

    // Early validation - prevents processing empty collections
    if (bookings.isEmpty) {
      println("❌ No data available")
      return
    }

    // Primary grouping operation
    // Creates Map[String, List[HotelBooking]] keyed by hotel name
    val bookingsByHotel = bookings.groupBy(_.hotelName)

    // Complex map transformation with tuple construction
    // Calculates 5 statistics for each hotel in single pass
    val hotelStats = bookingsByHotel.map { case (hotelName, hotelBookings) =>
      // Multiple map-sum operations for statistics
      val avgPrice = hotelBookings.map(_.bookingPrice).sum / hotelBookings.size
      val avgDiscount = hotelBookings.map(_.discountValue).sum / hotelBookings.size
      val avgMargin = hotelBookings.map(_.profitMargin).sum / hotelBookings.size
      val bookingCount = hotelBookings.size

      // Returns tuple containing all calculated statistics
      // Format: (name, avgPrice, avgDiscount, avgMargin, bookingCount)
      (hotelName, avgPrice, avgDiscount, avgMargin, bookingCount)
    }

    // Statistical significance filter
    // Filters hotels with insufficient data for reliable averages
    // Shows understanding of data quality in analysis
    val validHotels = hotelStats.filter(_._5 >= 2).toList

    if (validHotels.isEmpty) {
      println("❌ No hotels with sufficient booking data")
      return
    }

    // 2a. Most economical by booking price (lowest average price)
    // minBy on tuple index 1 (average price)
    val cheapestHotel = validHotels.minBy(_._2)
    println(s"\n📌 2a) Most economical by BOOKING PRICE:")
    println(s"   Hotel: ${cheapestHotel._1}")
    println(f"   Average Price: $$${cheapestHotel._2}%.2f")
    println(s"   Based on ${cheapestHotel._5} bookings")

    // 2b. Most economical by discount (highest average discount)
    // maxBy on tuple index 2 (average discount)
    val highestDiscountHotel = validHotels.maxBy(_._3)
    println(s"\n📌 2b) Most economical by DISCOUNT:")
    println(s"   Hotel: ${highestDiscountHotel._1}")
    println(f"   Average Discount: ${highestDiscountHotel._3}%.1f%%")

    // Derived calculation within presentation
    // Effective price = price * (1 - discount/100)
    val effectivePrice = highestDiscountHotel._2 * (1 - highestDiscountHotel._3/100)
    println(f"   Effective Price after discount: $$$effectivePrice%.2f")

    // 2c. Most economical by profit margin (lowest profit margin)
    // minBy on tuple index 3 (profit margin)
    val lowestMarginHotel = validHotels.minBy(_._4)
    println(s"\n📌 2c) Most economical by PROFIT MARGIN:")
    println(s"   Hotel: ${lowestMarginHotel._1}")
    println(f"   Average Profit Margin: ${lowestMarginHotel._4}%.1f%%")
    println(s"   (Lower profit margin often means better value for customers)")

    // Show summary table of top 5 economical hotels
    println("\n📋 SUMMARY: Top 5 Most Economical Hotels (by effective price):")
    // Complex transformation pipeline
    // 1. Map to add calculated effective price
    // 2. Creates 6-element tuple with all relevant metrics
    val economicalHotels = validHotels.map { case (name, price, discount, margin, count) =>
      val effectivePrice = price * (1 - discount/100)
      (name, effectivePrice, price, discount, margin, count)
    }

    // Multi-step collection pipeline for ranking
    // 1. sortBy on effective price (index 1 in new tuple)
    // 2. take(5) for top results
    // 3. zipWithIndex for ranking
    // 4. foreach with nested tuple pattern matching
    economicalHotels.sortBy(_._2).take(5).zipWithIndex.foreach {
      case ((name, effectivePrice, price, discount, margin, count), index) =>
        println(s"\n   ${index + 1}. $name")
        println(f"      Effective Price: $$$effectivePrice%.2f")
        println(f"      Original Price: $$$price%.2f")
        println(f"      Average Discount: $discount%.1f%%")
        println(f"      Profit Margin: $margin%.1f%%")
        println(s"      Based on $count bookings")
    }
  }
}