import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class MovieCollection
{
    private ArrayList<Movie> movies;
    private Scanner scanner;
    private ArrayList<String> allCastMembers;
    private ArrayList<String> allGenres;

    public MovieCollection(String fileName)
    {
        importMovieList(fileName);

        // arraylists to hold cast & genre
        ArrayList<String> allCastMembers = new ArrayList<String>();
        ArrayList<String> allGenres = new ArrayList<String>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String stringCast = movies.get(i).getCast();
            String stringGenre = movies.get(i).getGenres();
            String[] cast = stringCast.split("\\|");
            String[] genre = stringGenre.split("\\|");

            for (int idx = 0; idx < cast.length; idx ++) {
                if (allCastMembers.indexOf(cast[idx]) == -1) {
                    allCastMembers.add(cast[idx]);
                }
            }

            for (int idx2 = 0; idx2 < genre.length; idx2 ++) {
                if (allGenres.indexOf(genre[idx2]) == -1) {
                    allGenres.add(genre[idx2]);
                }
            }
        }

        this.allCastMembers = allCastMembers;
        this.allGenres = allGenres;
        Collections.sort(this.allGenres);
        scanner = new Scanner(System.in);
    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        if (option.equals("t"))
        {
            searchTitles();
        }
        else if (option.equals("c"))
        {
            searchCast();
        }
        else if (option.equals("k"))
        {
            searchKeywords();
        }
        else if (option.equals("g"))
        {
            listGenres();
        }
        else if (option.equals("r"))
        {
            listHighestRated();
        }
        else if (option.equals("h"))
        {
            listHighestRevenue();
        }
        else
        {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles()
    {
        System.out.print("Enter a title search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
            {
                //add the Movie object to the results list
                results.add(movies.get(i));
            }
        }
        printMovies(results);
    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchCast()
    {
        System.out.print("Enter a cast name: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        ArrayList<String> castResults = new ArrayList<String>();

        for (int i = 0; i < allCastMembers.size(); i ++) {
            if (!(allCastMembers.get(i).toLowerCase().indexOf(searchTerm) == -1)) {
                castResults.add(allCastMembers.get(i));
            }
        }
        Collections.sort(castResults);

        for (int i = 0; i < castResults.size(); i ++) {
            System.out.println(i + 1 + ". " + castResults.get(i));
        }

        System.out.println("Which cast member would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        String selectedCastMember = castResults.get(choice - 1);

        ArrayList<Movie> movieResults = new ArrayList<Movie>();

        for (int i = 0; i < movies.size(); i ++) {
            if (!(movies.get(i).getCast().indexOf(selectedCastMember) == -1)) {
                movieResults.add(movies.get(i));
            }
        }

        printMovies(movieResults);
    }

    private void searchKeywords()
    {
        System.out.print("Enter a keyword search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String keywords = movies.get(i).getKeywords();
            keywords = keywords.toLowerCase();

            if (keywords.indexOf(searchTerm) != -1)
            {
                //add the Movie object to the results list
                results.add(movies.get(i));
            }
        }
        printMovies(results);
    }

    private void listGenres()
    {
        for (int i = 0; i < allGenres.size(); i ++) {
            System.out.println(i + 1 + ". " + allGenres.get(i));
        }

        System.out.println("Which genre would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        String selectedGenre = allGenres.get(choice - 1);

        ArrayList<Movie> movieResults = new ArrayList<Movie>();

        for (int i = 0; i < movies.size(); i ++) {
            if (!(movies.get(i).getGenres().indexOf(selectedGenre) == -1)) {
                movieResults.add(movies.get(i));
            }
        }

        printMovies(movieResults);
    }

    private void listHighestRated()
    {
        ArrayList<Movie> dupeMovies = new ArrayList<Movie>();
        ArrayList<Movie> top50 = new ArrayList<Movie>();
        for (Movie movie: movies) {
            dupeMovies.add(movie);
        }
        int index = 0;
        for (int i = 0; i < 50; i ++) {
            double max = 0;
            for (int idx = 0; idx < dupeMovies.size(); idx ++) {
                if (dupeMovies.get(idx).getUserRating() > max) {
                    max = dupeMovies.get(idx).getUserRating();
                    index = idx;
                }
            }
            top50.add(dupeMovies.get(index));
            dupeMovies.remove(index);
        }

        printMovies50(top50);
    }

    private void listHighestRevenue()
    {
        ArrayList<Movie> dupeMovies = new ArrayList<Movie>();
        ArrayList<Movie> top50 = new ArrayList<Movie>();
        for (Movie movie: movies) {
            dupeMovies.add(movie);
        }
        int index = 0;
        for (int i = 0; i < 50; i ++) {
            double max = 0;
            for (int idx = 0; idx < dupeMovies.size(); idx ++) {
                if (dupeMovies.get(idx).getRevenue() > max) {
                    max = dupeMovies.get(idx).getRevenue();
                    index = idx;
                }
            }
            top50.add(dupeMovies.get(index));
            dupeMovies.remove(index);
        }

        printMovies50(top50);
    }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }

    public void printMovies(ArrayList<Movie> results) {
        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    public void printMovies50(ArrayList<Movie> results) {

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }
}
