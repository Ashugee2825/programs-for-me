package student.profile;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
public class StudentProfileDBService {
	Connection con;
	
	
	public StudentProfileDBService()
	{
		DBConnectionDTO conDTO = new DBConnectionDTO();
		con=conDTO.getConnection();
	}
	
	public void closeConnection()
	{
		try {
			con.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
public int getTotalPages(int limit)
	{
		String query="select count(*) from student_profile";
	    int totalRecords=0;
	    int totalPages=0;
		try {
		Statement stmt = con.createStatement();
	    ResultSet rs = stmt.executeQuery(query);
	    if(rs.next()) {
	    	totalRecords= rs.getInt(1);
	    }
	    stmt.close();
	    rs.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
		totalPages=totalRecords/limit;
		if(totalRecords%limit!=0)
		{
			totalPages+=1;
		}
		return totalPages;
	}
	
	//pagination
	public int getTotalPages(StudentProfile studentProfile,int limit)
	{
		String query=getDynamicQuery2(studentProfile);
		int totalRecords=0;
	    int totalPages=0;
		try {
		Statement stmt = con.createStatement();
	    ResultSet rs = stmt.executeQuery(query);
	    if(rs.next()) {
	    	totalRecords= rs.getInt(1);
	    }
	    stmt.close();
	    rs.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
		totalPages=totalRecords/limit;
		if(totalRecords%limit!=0)
		{
			totalPages+=1;
		}
		return totalPages;
	}
	
	
	public int getStudentProfileId(StudentProfile studentProfile)
	{
		int id=0;
		String query="select id from student_profile";
String whereClause = " where "+ "name=? and age=?";
	    query+=whereClause;
		System.out.println(query);
		try {
PreparedStatement pstmt = con.prepareStatement(query);
pstmt.setString(1, studentProfile.getName());
pstmt.setInt(2, studentProfile.getAge());
	    ResultSet rs = pstmt.executeQuery();
	    if(rs.next()) {
	       	id = rs.getInt("id");
	    }
		}
	    catch (Exception e) {
	    	System.out.println(e);
		}
	    
	    return id;
	}
	public void createStudentProfile(StudentProfile studentProfile)
	{
		
String query="INSERT INTO student_profile(name,age) VALUES(?,?)";
	
    System.out.println(query);
		try {
PreparedStatement pstmt = con.prepareStatement(query);
pstmt.setString(1, studentProfile.getName());
pstmt.setInt(2, studentProfile.getAge());
	    int x = pstmt.executeUpdate();
	    }
	    catch (Exception e) {
	  
  	System.out.println(e);
		}
		int id = getStudentProfileId(studentProfile);
		studentProfile.setId(id);
	}
	public void updateStudentProfile(StudentProfile studentProfile)
	{
		
String query="update student_profile set "+"name=?,age=? where id=?";
	   
 System.out.println(query);
		try {
PreparedStatement pstmt = con.prepareStatement(query);
pstmt.setString(1, studentProfile.getName());
pstmt.setInt(2, studentProfile.getAge());
pstmt.setInt(3, studentProfile.getId());
	    int x = pstmt.executeUpdate();
	    }
	    catch (Exception e) {
	    	System.out.println(e);
		}
		
	}
	public String getValue(String code,String table) {
		
		String value="";
		String query="select value from "+table+" where code='"+code+"'";
	    System.out.println(query);
		try {
		Statement stmt = con.createStatement();
	    ResultSet rs = stmt.executeQuery(query);
	    if(rs.next()) {
	    	
	    	value=rs.getString("value");
	    }
		}
		catch (Exception e) {
			System.out.println(e);
		}
	    return value;
	}
	
	public StudentProfile getStudentProfile(int id)
	{
		StudentProfile studentProfile =new StudentProfile();
		String query="select * from student_profile where id="+id;
	    System.out.println(query);
		try {
		Statement stmt = con.createStatement();
	    ResultSet rs = stmt.executeQuery(query);
	    if(rs.next()) {
	    	
	
studentProfile.setId(rs.getInt("id")==0?0:rs.getInt("id"));
studentProfile.setName(rs.getString("name")==null?"":rs.getString("name"));
studentProfile.setAge(rs.getInt("age")==0?0:rs.getInt("age"));
	    	
	    }
		}
	    catch (Exception e) {
	    	System.out.println(e);
		}
	    
	    return studentProfile;
	}
	
	
	public ArrayList<StudentProfile> getStudentProfileList()
	{
		ArrayList<StudentProfile> studentProfileList =new ArrayList<StudentProfile>();
		String query="select * from student_profile";
	    System.out.println(query);
		try {
		Statement stmt = con.createStatement();
	    ResultSet rs = stmt.executeQuery(query);
	    while(rs.next()) {
	    	StudentProfile studentProfile =new StudentProfile();
studentProfile.setId(rs.getInt("id")==0?0:rs.getInt("id"));
studentProfile.setName(rs.getString("name")==null?"":rs.getString("name"));
studentProfile.setAge(rs.getInt("age")==0?0:rs.getInt("age"));
	    	studentProfileList.add(studentProfile);
	    }
		}
	    catch (Exception e) {
	    	System.out.println(e);
		}
	    
	    return studentProfileList;
	}
	
	public ArrayList<StudentProfile> getStudentProfileList(int pageNo,int limit)
	{
		ArrayList<StudentProfile> studentProfileList =new ArrayList<StudentProfile>();
String query="select * from student_profile limit "+limit +" offset "+limit*(pageNo-1);
	    System.out.println(query);
		try {
		Statement stmt = con.createStatement();
	    ResultSet rs = stmt.executeQuery(query);
	    while(rs.next()) {
	    	StudentProfile studentProfile =new StudentProfile();
studentProfile.setId(rs.getInt("id")==0?0:rs.getInt("id"));
studentProfile.setName(rs.getString("name")==null?"":rs.getString("name"));
studentProfile.setAge(rs.getInt("age")==0?0:rs.getInt("age"));
	    	studentProfileList.add(studentProfile);
	    }
		}
	    catch (Exception e) {
	    	System.out.println(e);
		}
	    
	    return studentProfileList;
	}
	
	public void deleteStudentProfile(int id) {
		
			String query="delete from student_profile where id="+id;
		    System.out.println(query);
				
			
		    try {
			Statement stmt = con.createStatement();
		    int x = stmt.executeUpdate(query);
		    }
		    catch (Exception e) {
		    	System.out.println(e);
			}
		
	}
	
public String getDynamicQuery(StudentProfile studentProfile)
{
String query="select * from student_profile ";
String whereClause="";
whereClause+=(studentProfile.getAge()==0?"":" age="+studentProfile.getAge());
if(!whereClause.equals(""))
query+=" where "+whereClause;
System.out.println("Search Query= "+query);
    return query;
}
public String getDynamicQuery2(StudentProfile studentProfile)
{
String query="select count(*) from student_profile ";
String whereClause="";
whereClause+=(studentProfile.getAge()==0?"":" age="+studentProfile.getAge());
if(!whereClause.equals(""))
query+=" where "+whereClause;
System.out.println("Search Query= "+query);
    return query;
}
public ArrayList<StudentProfile> getStudentProfileList(StudentProfile studentProfile)
{
ArrayList<StudentProfile> studentProfileList =new ArrayList<StudentProfile>();
String query=getDynamicQuery(studentProfile);
System.out.println("Search Query= "+query);
try {
Statement stmt = con.createStatement();
ResultSet rs = stmt.executeQuery(query);
while(rs.next()) {
StudentProfile studentProfile2 =new StudentProfile();
studentProfile2.setId(rs.getInt("id")==0?0:rs.getInt("id"));
studentProfile2.setName(rs.getString("name")==null?"":rs.getString("name"));
studentProfile2.setAge(rs.getInt("age")==0?0:rs.getInt("age"));
    	studentProfileList.add(studentProfile2);
    }
	}
    catch (Exception e) {
    	System.out.println(e);
	}
    return studentProfileList;
}
	
public ArrayList<StudentProfile> getStudentProfileList(StudentProfile studentProfile,int pageNo,int limit)
{
ArrayList<StudentProfile> studentProfileList =new ArrayList<StudentProfile>();
String query=getDynamicQuery(studentProfile);
query+= " limit "+limit +" offset "+limit*(pageNo-1);
System.out.println("Search Query= "+query);
try {
Statement stmt = con.createStatement();
ResultSet rs = stmt.executeQuery(query);
while(rs.next()) {
StudentProfile studentProfile2 =new StudentProfile();
studentProfile2.setId(rs.getInt("id")==0?0:rs.getInt("id"));
studentProfile2.setName(rs.getString("name")==null?"":rs.getString("name"));
studentProfile2.setAge(rs.getInt("age")==0?0:rs.getInt("age"));
    	studentProfileList.add(studentProfile2);
    }
	}
    catch (Exception e) {
    	System.out.println(e);
	}
    return studentProfileList;
}
	
	
	public static void main(String[] args) {
		
		StudentProfileDBService studentProfileDBService =new StudentProfileDBService();
		
		
		
		 //Test-1 : Create StudentProfile
		  
		  StudentProfile studentProfile = new StudentProfile(); studentProfile.setDefaultValues();
		  studentProfileDBService.createStudentProfile(studentProfile);
		  
		 ArrayList<StudentProfile> studentProfileList = studentProfileDBService.getStudentProfileList();
		StudentProfileService studentProfileService =new StudentProfileService();
		studentProfileService.displayList(studentProfileList);
		
	}
}
