object Question3Analyzer {

  def analyze(bookings: List[HotelBooking]): Unit = {
    println("\n" + "─" * 90)
    println("QUESTION 3: Which hotel is the most profitable?")
    println("─" * 90)

    if (bookings.isEmpty) {
      println("❌ No data available")
      return
    }
    
    val hotelProfitability = bookings
      .groupBy(_.hotelName)
      .map { case (hotelName, hotelBookings) =>
        val bookingCount = hotelBookings.size
        val totalVisitors = hotelBookings.map(_.noOfPeople).sum
        val totalRevenue = hotelBookings.map(_.bookingPrice).sum
        
        val totalProfit = hotelBookings.map { booking =>
          val discountValue = booking.discountValue
          val finalPrice = booking.bookingPrice * (1 - discountValue/100)
          finalPrice * (booking.profitMargin/100)
        }.sum
        
        val profitabilityScore = totalProfit * totalVisitors / 100

        (hotelName, totalVisitors, totalRevenue, totalProfit, profitabilityScore, bookingCount)
      }
      .filter(_._6 >= 2) 
      .toList

    if (hotelProfitability.isEmpty) {
      println("❌ No hotels with sufficient booking data")
      return
    }
    
    val mostProfitable = hotelProfitability.maxBy(_._5)

    println(s"${mostProfitable._1} is the most profitable hotel")
    println(f"Profitability Score: ${mostProfitable._5}%.2f")
    println(s"Total Visitors: ${mostProfitable._2}")
    println(f"Total Revenue: $$${mostProfitable._3}%.2f")
    println(f"Total Profit: $$${mostProfitable._4}%.2f")
    println(s"Based on ${mostProfitable._6} bookings")
    
    println("\n" + "-" * 90)
    println("TOP 3 MOST PROFITABLE HOTELS:")
    println("-" * 90)

    val top3Profitable = hotelProfitability.sortBy(-_._5).take(3)
    
    printf("%-30s %-30s %-30s\n",
      s"1. ${top3Profitable(0)._1}",
      s"2. ${top3Profitable(1)._1}",
      s"3. ${top3Profitable(2)._1}")
    
    printf("%-30s %-30s %-30s\n",
      f"   Score: ${top3Profitable(0)._5}%.2f",
      f"   Score: ${top3Profitable(1)._5}%.2f",
      f"   Score: ${top3Profitable(2)._5}%.2f")
    
    printf("%-30s %-30s %-30s\n",
      f"   Profit: $$${top3Profitable(0)._4}%.2f",
      f"   Profit: $$${top3Profitable(1)._4}%.2f",
      f"   Profit: $$${top3Profitable(2)._4}%.2f")
    
    val margin1 = if (top3Profitable(0)._3 > 0) (top3Profitable(0)._4 / top3Profitable(0)._3 * 100) else 0.0
    val margin2 = if (top3Profitable(1)._3 > 0) (top3Profitable(1)._4 / top3Profitable(1)._3 * 100) else 0.0
    val margin3 = if (top3Profitable(2)._3 > 0) (top3Profitable(2)._4 / top3Profitable(2)._3 * 100) else 0.0

    printf("%-30s %-30s %-30s\n",
      f"   Margin: ${margin1}%.1f%%",
      f"   Margin: ${margin2}%.1f%%",
      f"   Margin: ${margin3}%.1f%%")
    
    printf("%-30s %-30s %-30s\n",
      s"   Visitors: ${top3Profitable(0)._2}",
      s"   Visitors: ${top3Profitable(1)._2}",
      s"   Visitors: ${top3Profitable(2)._2}")
    
    printf("%-30s %-30s %-30s\n",
      f"   Revenue: $$${top3Profitable(0)._3}%.2f",
      f"   Revenue: $$${top3Profitable(1)._3}%.2f",
      f"   Revenue: $$${top3Profitable(2)._3}%.2f")
    
    printf("%-30s %-30s %-30s\n",
      s"   Bookings: ${top3Profitable(0)._6}",
      s"   Bookings: ${top3Profitable(1)._6}",
      s"   Bookings: ${top3Profitable(2)._6}")

    println("=" * 90)
  }
}