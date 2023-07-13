import java.util.HashMap;
import java.util.Map;

public class RentalInfo {
  private static final String Regular_movie = "regular";
  
    private static final Map<String, Double> RENTAL_RATES = createRentalRates();

    public String statement(Customer customer) {
        Map<String, Movie> movies = createMovies();
        double totalAmount = 0;
        int frequentEnterPoints = 0;

        StringBuilder result = new StringBuilder("Rental Record for " + customer.getName() + "\n");
        for (MovieRental rental : customer.getRentals()) {
            Movie movie = movies.get(rental.getMovieId());
            double thisAmount = calculateAmount(movie, rental);

            frequentEnterPoints += calculateFrequentEnterPoints(movie, rental);
            result.append("\t").append(movie.getTitle()).append("\t").append(thisAmount).append("\n");
            totalAmount += thisAmount;
        }

        result.append("Amount owed is ").append(totalAmount).append("\n");
        result.append("You earned ").append(frequentEnterPoints).append(" frequent points\n");

        return result.toString();
    }

    private static Map<String, Movie> createMovies() {
        Map<String, Movie> movies = new HashMap<>();
        movies.put("F001", new Movie("You've Got Mail", Regular_movie));
        movies.put("F002", new Movie("Matrix", "regular"));
        movies.put("F003", new Movie("Cars", "childrens"));
        movies.put("F004", new Movie("Fast & Furious X", "new"));
        return movies;
    }

    private static Map<String, Double> createRentalRates() {
        Map<String, Double> rentalRates = new HashMap<>();
        rentalRates.put(Regular_movie, 2.0);
        rentalRates.put("new", 3.0);
        rentalRates.put("childrens", 1.5);
        return rentalRates;
    }

    private static double calculateAmount(Movie movie, MovieRental rental) {
        double amount = 0;

        if (movie.getCode().equals(Regular_movie)) {
            amount = RENTAL_RATES.get(Regular_movie);
            if (rental.getDays() > 2) {
                amount += (rental.getDays() - 2) * 1.5;
            }
        } else {
            amount = RENTAL_RATES.getOrDefault(movie.getCode(), 0.0) * rental.getDays();
        }

        return amount;
    }

    private static int calculateFrequentEnterPoints(Movie movie, MovieRental rental) {
        int points = 1;

        if (movie.getCode().equals("new") && rental.getDays() > 2) {
            points++;
        }

        return points;
    }
}