// Question2Analyzer.scala
object Question2Analyzer {

  def analyze(bookings: List[HotelBooking]): Unit = {
    println("\n" + "=" * 70)
    println("QUESTION 2: Which hotel offers the most economical option?")
    println("=" * 70)

    if (bookings.isEmpty) {
      println("❌ No data available")
      return
    }

    // Group bookings by hotel name
    val bookingsByHotel = bookings.groupBy(_.hotelName)

    // Calculate statistics for each hotel
    val hotelStats = bookingsByHotel.map { case (hotelName, hotelBookings) =>
      val avgPrice = hotelBookings.map(_.bookingPrice).sum / hotelBookings.size
      val avgDiscount = hotelBookings.map(_.discountValue).sum / hotelBookings.size
      val avgMargin = hotelBookings.map(_.profitMargin).sum / hotelBookings.size
      val bookingCount = hotelBookings.size

      (hotelName, avgPrice, avgDiscount, avgMargin, bookingCount)
    }

    // Filter hotels with at least 2 bookings for meaningful averages
    val validHotels = hotelStats.filter(_._5 >= 2).toList

    if (validHotels.isEmpty) {
      println("❌ No hotels with sufficient booking data")
      return
    }

    // 2a. Most economical by booking price (lowest average price)
    val cheapestHotel = validHotels.minBy(_._2)
    println(s"\n📌 2a) Most economical by BOOKING PRICE:")
    println(s"   Hotel: ${cheapestHotel._1}")
    println(f"   Average Price: $$${cheapestHotel._2}%.2f")
    println(s"   Based on ${cheapestHotel._5} bookings")

    // 2b. Most economical by discount (highest average discount)
    val highestDiscountHotel = validHotels.maxBy(_._3)
    println(s"\n📌 2b) Most economical by DISCOUNT:")
    println(s"   Hotel: ${highestDiscountHotel._1}")
    println(f"   Average Discount: ${highestDiscountHotel._3}%.1f%%")
    val effectivePrice = highestDiscountHotel._2 * (1 - highestDiscountHotel._3/100)
    println(f"   Effective Price after discount: $$$effectivePrice%.2f")

    // 2c. Most economical by profit margin (lowest profit margin)
    val lowestMarginHotel = validHotels.minBy(_._4)
    println(s"\n📌 2c) Most economical by PROFIT MARGIN:")
    println(s"   Hotel: ${lowestMarginHotel._1}")
    println(f"   Average Profit Margin: ${lowestMarginHotel._4}%.1f%%")
    println(s"   (Lower profit margin often means better value for customers)")

    // Show summary table of top 5 economical hotels
    println("\n📋 SUMMARY: Top 5 Most Economical Hotels (by effective price):")
    val economicalHotels = validHotels.map { case (name, price, discount, margin, count) =>
      val effectivePrice = price * (1 - discount/100)
      (name, effectivePrice, price, discount, margin, count)
    }

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