package Model;

import java.sql.SQLException;
import java.util.List;

import DAO.CourseDaoImplementation;
import DAO.StudentDaoImplementation;

public class AdminStudentModel {

	private StudentDaoImplementation stdDao = new StudentDaoImplementation();
	private CourseDaoImplementation coursesDao = new CourseDaoImplementation();

	public Object[][] getAcceptedStudentsInfo(String id) throws SQLException {
		return stdDao.getAcceptedStudentsInfo(id);
	}

	public Object[][] getStudents() throws SQLException{
		return stdDao.getStudentsWithId();
	}

	public boolean updateStudent(String[] studentinfo) throws SQLException
	{
		return stdDao.updateStudent(studentinfo);
	}

	public boolean addStudentToCourse(String studentId, String courseCode) throws SQLException
	{
		return stdDao.addStudentToCourse(studentId, courseCode);
	}

	public boolean deleteStudentFromCourse(String studentId, String courseCode) throws SQLException
	{
		return stdDao.deleteStudentFromCourse(studentId, courseCode);
	}

	public List<Course> getCoursesList(String major) throws SQLException
	{
		return coursesDao.getMajorCourses(major);
	}

	
}
