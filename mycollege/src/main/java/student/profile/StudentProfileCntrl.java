package student.profile;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet implementation class StudentProfileCntrl
 */
@WebServlet("/student/profile/studentProfileCntrl")
public class StudentProfileCntrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentProfileCntrl() {
        super();
        // TODO Auto-generated constructor stub
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String page= request.getParameter("page");
		String opr = request.getParameter("opr");
		int pageNo = (null==request.getParameter("pageNo")?0:Integer.parseInt(request.getParameter("pageNo")));
		int limit= (null==request.getParameter("pageNo")?0:Integer.parseInt(request.getParameter("limit")));
		
		RequestDispatcher rd;
		StudentProfileDBService studentProfileDBService =new StudentProfileDBService();
		StudentProfile studentProfile =new StudentProfile();
		//Action for close buttons
		String homeURL=(null==request.getSession().getAttribute("homeURL")?"":(String)request.getSession().getAttribute("homeURL"));		
		if(page.equals("studentProfileDashboard"))
		{
			request.getSession().setAttribute("homePage",page);
			homeURL="studentProfileCntrl?page="+page+"&opr=showAll&pageNo="+pageNo+"&limit="+limit;
			request.getSession().setAttribute("homeURL",homeURL);
			
			if(opr.equals("showAll")) 
			{
				ArrayList<StudentProfile> studentProfileList =new ArrayList<StudentProfile>();
				
				if(pageNo==0)
				studentProfileList = studentProfileDBService.getStudentProfileList();
				else { //pagination
					int totalPages= studentProfileDBService.getTotalPages(limit);
					studentProfileList = studentProfileDBService.getStudentProfileList(pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("studentProfileList",studentProfileList);
				rd = request.getRequestDispatcher("studentProfileDashboard.jsp");
				rd.forward(request, response);
			} 
			else if(opr.equals("addNew")) //CREATE
			{
				studentProfile.setDefaultValues();
				studentProfile.displayValues();
				request.setAttribute("studentProfile",studentProfile);
				rd = request.getRequestDispatcher("addNewStudentProfile.jsp");
				rd.forward(request, response);
			}
			else if(opr.equals("edit")) //UPDATE
			{
				int id = Integer.parseInt(request.getParameter("id"));
				studentProfile = studentProfileDBService.getStudentProfile(id);
				request.setAttribute("studentProfile",studentProfile);
				rd = request.getRequestDispatcher("updateStudentProfile.jsp");
				rd.forward(request, response);
			}
			//Begin: modified by Dr PNH on 06-12-2022
			else if(opr.equals("editNext")) //Save and Next
			{
				int id = Integer.parseInt(request.getParameter("id"));
				studentProfile = studentProfileDBService.getStudentProfile(id);
				request.setAttribute("studentProfile",studentProfile);
				rd = request.getRequestDispatcher("updateNextStudentProfile.jsp");
				rd.forward(request, response);
			}
			else if(opr.equals("saveShowNext")) //Save, show & next
			{
				studentProfile.setDefaultValues();
				studentProfile.displayValues();
				request.setAttribute("studentProfile",studentProfile);
				
				ArrayList<StudentProfile> studentProfileList =new ArrayList<StudentProfile>();
				
				if(pageNo==0)
				studentProfileList = studentProfileDBService.getStudentProfileList();
				else { //pagination
					int totalPages= studentProfileDBService.getTotalPages(limit);
					studentProfileList = studentProfileDBService.getStudentProfileList(pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("studentProfileList",studentProfileList);
				rd = request.getRequestDispatcher("saveShowNextStudentProfile.jsp");
				rd.forward(request, response);
			}
			//End: modified by Dr PNH on 06-12-2022
			else if(opr.equals("delete")) //DELETE
			{
				int id = Integer.parseInt(request.getParameter("id"));
				studentProfile.setId(id);
				studentProfileDBService.deleteStudentProfile(id);
				request.setAttribute("studentProfile",studentProfile);
				rd = request.getRequestDispatcher("deleteStudentProfileSuccess.jsp");
				rd.forward(request, response);
			}
			else if(opr.equals("view")) //READ
			{
				int id = Integer.parseInt(request.getParameter("id"));
				studentProfile = studentProfileDBService.getStudentProfile(id);
				request.setAttribute("studentProfile",studentProfile);
				rd = request.getRequestDispatcher("viewStudentProfile.jsp");
				rd.forward(request, response);
			}
			
		}
		else if(page.equals("addNewStudentProfile")) 
		{
			if(opr.equals("save"))
			{
				studentProfile.setRequestParam(request);
				studentProfile.displayValues();
				studentProfileDBService.createStudentProfile(studentProfile);
				request.setAttribute("studentProfile",studentProfile);
				if(pageNo!=0) {//pagination
					int totalPages= studentProfileDBService.getTotalPages(limit);
					homeURL="studentProfileCntrl?page=studentProfileDashboard&opr=showAll&pageNo="+totalPages+"&limit="+limit;
					request.getSession().setAttribute("homeURL", homeURL);
				}
				rd = request.getRequestDispatcher("addNewStudentProfileSuccess.jsp");
				rd.forward(request, response);
			}
		}
		//Begin: modified by Dr PNH on 06-12-2022
		else if(page.equals("updateNextStudentProfile")) 
		{
			if(opr.equals("update"))
			{
				studentProfile.setRequestParam(request);
				studentProfileDBService.updateStudentProfile(studentProfile);
				request.setAttribute("studentProfile",studentProfile);
				request.getSession().setAttribute("msg", "Record saved successfully");
				response.sendRedirect("studentProfileCntrl?page=studentProfileDashboard&opr=editNext&pageNo=0&limit=0&id=10");			}
		}
		else if(page.equals("saveShowNextStudentProfile")) 
		{
			request.getSession().setAttribute("homePage",page);
			homeURL="studentProfileCntrl?page=studentProfileDashboard&opr=saveShowNext&id=10&pageNo=0&limit=0";
			request.getSession().setAttribute("homeURL",homeURL);
			if(opr.equals("addNew")) //save new record
			{
				studentProfile.setRequestParam(request);
				studentProfile.displayValues();
				studentProfileDBService.createStudentProfile(studentProfile);
				request.setAttribute("studentProfile",studentProfile);
				if(pageNo!=0) {//pagination
					int totalPages= studentProfileDBService.getTotalPages(limit);
					homeURL="studentProfileCntrl?page=studentProfileDashboard&opr=showAll&pageNo="+totalPages+"&limit="+limit;
					request.getSession().setAttribute("homeURL", homeURL);
				}
				request.getSession().setAttribute("msg", "Record saved successfully");
				response.sendRedirect("studentProfileCntrl?page=studentProfileDashboard&opr=saveShowNext&id=10&pageNo=0&limit=0");
				//rd = request.getRequestDispatcher("studentProfileCntrl?page=studentProfileDashboard&opr=saveShowNext&id=10&pageNo=0&limit=0");
				//rd.forward(request, response);
			}
			else if(opr.equals("edit"))
			{
				int id = Integer.parseInt(request.getParameter("id"));
				studentProfile = studentProfileDBService.getStudentProfile(id);
				request.setAttribute("studentProfile",studentProfile);
				
				ArrayList<StudentProfile> studentProfileList =new ArrayList<StudentProfile>();
				if(pageNo==0)
				studentProfileList = studentProfileDBService.getStudentProfileList();
				else { //pagination
					int totalPages= studentProfileDBService.getTotalPages(limit);
					studentProfileList = studentProfileDBService.getStudentProfileList(pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("studentProfileList",studentProfileList);
				rd = request.getRequestDispatcher("saveShowNextStudentProfile.jsp");
				rd.forward(request, response);
			}
			else if(opr.equals("update"))
			{
				studentProfile.setRequestParam(request);
				studentProfileDBService.updateStudentProfile(studentProfile);
				request.setAttribute("studentProfile",studentProfile);
				request.getSession().setAttribute("msg", "Record updated successfully");
				response.sendRedirect("studentProfileCntrl?page=studentProfileDashboard&opr=saveShowNext&id=10&pageNo=0&limit=0");
			}
			else if(opr.equals("delete"))
			{
					int id = Integer.parseInt(request.getParameter("id"));
					studentProfile.setId(id);
					studentProfileDBService.deleteStudentProfile(id);
					request.setAttribute("studentProfile",studentProfile);
					request.getSession().setAttribute("msg", "Record deleted successfully");
					response.sendRedirect("studentProfileCntrl?page=studentProfileDashboard&opr=saveShowNext&id=10&pageNo=0&limit=0");		
			}
			else if(opr.equals("reset")||opr.equals("cancel"))
			{
					response.sendRedirect("studentProfileCntrl?page=studentProfileDashboard&opr=saveShowNext&id=10&pageNo=0&limit=0");		
			}
			
		}
		//End: modified by Dr PNH on 06-12-2022
		else if(page.equals("updateStudentProfile")) 
		{
			if(opr.equals("update"))
			{
				studentProfile.setRequestParam(request);
				studentProfileDBService.updateStudentProfile(studentProfile);
				request.setAttribute("studentProfile",studentProfile);
				rd = request.getRequestDispatcher("updateStudentProfileSuccess.jsp");
				rd.forward(request, response);
			}
		}
		else if(page.equals("viewStudentProfile")) 
		{
			if(opr.equals("print")) 
			{
				int id = Integer.parseInt(request.getParameter("id"));
				studentProfile = studentProfileDBService.getStudentProfile(id);
				request.setAttribute("studentProfile",studentProfile);
				rd = request.getRequestDispatcher("printStudentProfile.jsp");
				rd.forward(request, response);
			}
		}
		else if(page.equals("searchStudentProfile"))
		{
			request.getSession().setAttribute("homePage",page);
			homeURL="studentProfileCntrl?page="+page+"&opr=showAll&pageNo="+pageNo+"&limit="+limit;
			request.getSession().setAttribute("homeURL",homeURL);
			if(opr.equals("search")) 
			{
				studentProfile.setRequestParam(request);
				studentProfile.displayValues();
				request.getSession().setAttribute("studentProfileSearch",studentProfile);
				request.setAttribute("opr","search");
				ArrayList<StudentProfile> studentProfileList =new ArrayList<StudentProfile>();
				if(pageNo==0)
				studentProfileList = studentProfileDBService.getStudentProfileList(studentProfile);
				else { //pagination
					int totalPages=0;
					if(limit==0)
					totalPages=0;
					else
					totalPages=studentProfileDBService.getTotalPages(studentProfile,limit);
					pageNo=1;
					studentProfileList = studentProfileDBService.getStudentProfileList(studentProfile,pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("studentProfileList",studentProfileList);
				rd = request.getRequestDispatcher("searchStudentProfile.jsp?pageNo="+pageNo+"&limit="+limit);
				rd.forward(request, response);
			}
//begin:code for download/print button
			else if(opr.equals("downloadPrint")) 
			{
				studentProfile.setRequestParam(request);
				studentProfile.displayValues();
				request.getSession().setAttribute("studentProfileSearch",studentProfile);
				request.setAttribute("opr","studentProfile");
				ArrayList<StudentProfile> studentProfileList =new ArrayList<StudentProfile>();
				if(pageNo==0)
				studentProfileList = studentProfileDBService.getStudentProfileList(studentProfile);
				else { //pagination
					int totalPages=0;
					if(limit==0)
					totalPages=0;
					else
					totalPages=studentProfileDBService.getTotalPages(studentProfile,limit);
					pageNo=1;
					studentProfileList = studentProfileDBService.getStudentProfileList(studentProfile,pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("studentProfileList",studentProfileList);
				rd = request.getRequestDispatcher("searchStudentProfileDownloadPrint.jsp?pageNo="+pageNo+"&limit="+limit);
				rd.forward(request, response);
			}
			//end:code for download/print button
			
			else if(opr.equals("showAll")) 
			{
				studentProfile=(StudentProfile)request.getSession().getAttribute("studentProfileSearch");
				ArrayList<StudentProfile> studentProfileList =new ArrayList<StudentProfile>();
				if(pageNo==0)
				studentProfileList = studentProfileDBService.getStudentProfileList(studentProfile);
				else { //pagination
					int totalPages= studentProfileDBService.getTotalPages(studentProfile,limit);
					studentProfileList = studentProfileDBService.getStudentProfileList(studentProfile,pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("studentProfileList",studentProfileList);
				rd = request.getRequestDispatcher("searchStudentProfile.jsp?pageNo="+pageNo+"&limit="+limit);
				rd.forward(request, response);
			}
			else if(opr.equals("searchNext")||opr.equals("searchPrev")||opr.equals("searchFirst")||opr.equals("searchLast")) 
			{
				request.setAttribute("opr","search");
				studentProfile=(StudentProfile)request.getSession().getAttribute("studentProfileSearch");
				ArrayList<StudentProfile> studentProfileList =new ArrayList<StudentProfile>();
				if(pageNo==0)
				studentProfileList = studentProfileDBService.getStudentProfileList(studentProfile);
				else { //pagination
					int totalPages= studentProfileDBService.getTotalPages(studentProfile,limit);
					studentProfileList = studentProfileDBService.getStudentProfileList(studentProfile,pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("studentProfileList",studentProfileList);
				rd = request.getRequestDispatcher("searchStudentProfile.jsp?pageNo="+pageNo+"&limit="+limit);
				rd.forward(request, response);
			}
			else if(opr.equals("showNone"))
			{
				studentProfile.setDefaultValues();
				studentProfile.displayValues();
				request.getSession().setAttribute("studentProfileSearch",studentProfile);
				request.setAttribute("opr","showNone");
				rd = request.getRequestDispatcher("searchStudentProfile.jsp?pageNo="+pageNo+"&limit="+limit);
				rd.forward(request, response);
			}
			else if(opr.equals("edit")) 
			{
				int id = Integer.parseInt(request.getParameter("id"));
				studentProfile = studentProfileDBService.getStudentProfile(id);
				request.setAttribute("studentProfile",studentProfile);
				rd = request.getRequestDispatcher("updateStudentProfile.jsp");
				rd.forward(request, response);
			}
			else if(opr.equals("delete")) 
			{
				int id = Integer.parseInt(request.getParameter("id"));
				studentProfile.setId(id);
				studentProfileDBService.deleteStudentProfile(id);
				request.setAttribute("studentProfile",studentProfile);
				rd = request.getRequestDispatcher("deleteStudentProfileSuccess.jsp");
				rd.forward(request, response);
			}
			else if(opr.equals("view")) 
			{
 			int id = Integer.parseInt(request.getParameter("id"));
				studentProfile = studentProfileDBService.getStudentProfile(id);
				request.setAttribute("studentProfile",studentProfile);
				rd = request.getRequestDispatcher("viewStudentProfile.jsp");
				rd.forward(request, response);
			}
		}
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public static void main(String[] args) throws URISyntaxException {
		URI uri = new URI("page=updateStudentProfile&opr=close&homePage=studentProfileDashboard");
		String v = uri.getQuery();
		
	}
}
