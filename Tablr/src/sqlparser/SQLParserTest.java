package sqlparser;

import static org.junit.Assert.*;
//import static org.junit.Assertions.*;

import org.junit.Test;

import static sqlparser.SQLParser.parseQuery;

class SQLParserTest {

	static void assertRoundTrip(String query) {
		assertEquals(query, parseQuery(query));
	}
	
	@Test
	void test() {
		assertRoundTrip("SELECT movie.title AS title FROM movies AS movie WHERE movie.imdb_score > 7");
		assertRoundTrip("SELECT student.name AS name, student.program AS program" + 
				" FROM enrollments AS enrollment INNER JOIN students AS student" + 
				" ON enrollment.student_id = student.student_id" + 
				" WHERE enrollment.course_id = \"SWOP\"");
		assertRoundTrip("SELECT parent.name AS parentName, child.name AS childName" + 
				" FROM persons AS parent" + 
				" INNER JOIN is_child_of AS link ON parent.id = link.parent_id" + 
				" INNER JOIN persons AS child ON link.child_id = child.id" +
				" WHERE TRUE");
	}

}
