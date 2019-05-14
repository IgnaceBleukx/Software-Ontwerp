package sqlparser;

public class test {

	public static void main(String[] args){
		String query = "SELECT movie.title AS title FROM movies AS movie WHERE movie.imdb_score > 7";
		SQLParser s = new SQLParser(query);
		System.out.println(s.parseQuery());
	}
	
}
