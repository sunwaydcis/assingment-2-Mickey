import scala.io.Source
import scala.util.{Try, Using}
import java.io.File

object DataUtils {

  def loadHotelData(filePath: String): List[HotelBooking] = {
    println(s"📂 Loading data from: $filePath")

    val file = new File(filePath)
    if (!file.exists()) {
      println(s"❌ File does not exist: $filePath")
      return List.empty
    }

    // Try different encodings
    val encodings = List("UTF-8", "ISO-8859-1", "Windows-1252", "UTF-16")

    for (encoding <- encodings) {
      println(s"   Trying encoding: $encoding")

      Try {
        Using(Source.fromFile(filePath, encoding)) { source =>
          val lines = source.getLines().toList

          if (lines.isEmpty) {
            println("❌ File is empty")
            return List.empty
          }

          println(s"✅ Successfully read ${lines.size} lines with $encoding encoding")

          // Parse data rows (skip header)
          val bookings = lines.tail.flatMap { line =>
            val fields = line.split(",").map(_.trim)

            if (fields.length >= 24) {
              Try {
                HotelBooking(
                  bookingID = fields(0),
                  dateOfBooking = fields(1),
                  time = fields(2),
                  customerID = fields(3),
                  gender = fields(4),
                  age = safeToInt(fields(5)),
                  originCountry = fields(6),
                  state = fields(7),
                  location = fields(8),
                  destinationCountry = fields(9),
                  destinationCity = fields(10),
                  noOfPeople = safeToInt(fields(11)),
                  checkInDate = fields(12),
                  noOfDays = safeToInt(fields(13)),
                  checkOutDate = fields(14),
                  rooms = safeToInt(fields(15)),
                  hotelName = fields(16),
                  hotelRating = safeToDouble(fields(17)),
                  paymentMode = fields(18),
                  bankName = fields(19),
                  bookingPrice = safeToDouble(fields(20)),
                  discount = fields(21),
                  gst = safeToDouble(fields(22)),
                  profitMargin = safeToDouble(fields(23))
                )
              }.toOption
            } else {
              None
            }
          }

          println(s"🎉 Successfully loaded ${bookings.size} booking records")
          return bookings
        }.get
      }.recover {
        case e: Exception =>
          println(s"   Failed with $encoding: ${e.getMessage.take(50)}")
      }
    }

    println("❌ Could not load data with any encoding")
    List.empty
  }

  // Simple safe parsing methods
  private def safeToInt(str: String): Int = {
    Try(str.trim.toInt).getOrElse(0)
  }

  private def safeToDouble(str: String): Double = {
    Try(str.trim.replace("%", "").toDouble).getOrElse(0.0)
  }

  // File finding function
  def findDatasetFile(): File = {
    val possiblePaths = List(
      new File("Hotel_Dataset.csv"),
      new File("./Hotel_Dataset.csv"),
      new File("src/main/resources/Hotel_Dataset.csv"),
      new File("src/main/scala/Hotel_Dataset.csv")
    )

    println("\n🔍 Searching for Hotel_Dataset.csv...")
    possiblePaths.foreach { file =>
      if (file.exists()) {
        println(s"✅ Found: ${file.getAbsolutePath}")
        return file
      }
    }

    println("\n❌ File not found in any location")
    new File("Hotel_Dataset.csv")
  }
}