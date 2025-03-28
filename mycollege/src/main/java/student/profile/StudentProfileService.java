package student.profile;

import java.util.ArrayList;

public class StudentProfileService {

	public void displayList(ArrayList<StudentProfile> studentProfileList)
	{
		studentProfileList.forEach((studentProfile) -> print(studentProfile));
	}
	
	public void print(StudentProfile studentProfile)
	{
		studentProfile.displayValues();
	}
	
}
