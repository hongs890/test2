package member;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet({"/member/main.do",
	"/member/regist.do",
	"/member/find_by_id.do",
	"/member/update.do",
	"/member/delete.do",
	"/member/login.do",
	"/member/logout.do",
	"/member/list.do",
	"/member/find_by.do",
	"/member/count.do"})
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String directory,view;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("GET 방식으로 접근함");
		this.init(request);
		try {
			this.dispatch(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("POST방식으로 접근함");
		this.init();
		MemberService service = MemberServiceImpl.getInstanceImpl();
		String name ="";
		switch (this.view) {
		case "login":
			name = service.login(this.init(request));
			request.setAttribute("result", name);
			if (!name.equals("")) {
				this.directory = "global";
				this.view = "main";
			}
			break;
		case "regist":
			service.regist(this.init(request));
			this.view = "login";
			break;
		}	
		try {
			this.dispatch(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public MemberBean init(HttpServletRequest request) throws SecurityException{
		String sPath = request.getServletPath();
		String[] arr = sPath.split("/");
		this.directory = arr[1];
		this.view = arr[2].substring(0, arr[2].indexOf("."));
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String name = request.getParameter("name");
		String ssn = request.getParameter("ssn");
		String email = request.getParameter("email");
		MemberBean member = new MemberBean();
		System.out.println("ID : "+id+" PW : "+pw);
		member.setId(id);
		member.setPw(pw);
		member.setName(name);
		member.setSsn(ssn);
		member.setEmail(email);
		member.setRegDate();
	
		return member;
	}
	public void dispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		RequestDispatcher dis = request.getRequestDispatcher("/WEB-INF/"+this.directory+"/"+this.view+".jsp");
		dis.forward(request, response);
	}
}
