case class HotelBooking(
                         bookingID: String,
                         dateOfBooking: String,
                         time: String,
                         customerID: String,
                         gender: String,
                         age: Int,
                         originCountry: String,
                         state: String,
                         location: String,
                         destinationCountry: String,
                         destinationCity: String,
                         noOfPeople: Int,
                         checkInDate: String,
                         noOfDays: Int,
                         checkOutDate: String,
                         rooms: Int,
                         hotelName: String,
                         hotelRating: Double,
                         paymentMode: String,
                         bankName: String,
                         bookingPrice: Double,
                         discount: String,
                         gst: Double,
                         profitMargin: Double
                       ) {
  // Extract numeric discount from string like "15%"
  def discountValue: Double = {
    try {
      discount.replace("%", "").trim.toDouble
    } catch {
      case _: Exception => 0.0
    }
  }
}