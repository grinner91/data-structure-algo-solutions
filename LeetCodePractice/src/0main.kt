import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

fun main() {

    println("main: ")

    val birthDate = LocalDate.parse("1988-12-31", DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    val period = Period.between(birthDate, LocalDate.now())

    println(period.years)

}
