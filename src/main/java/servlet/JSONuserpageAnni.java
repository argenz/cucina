package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.CalendarioDAO;
import dao.CalendarioDAOImpl;
import entity.Edizione;

@WebServlet("/JSONuserpageAnni")
public class JSONuserpageAnni extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Set<String> anni = new TreeSet<>(); //set

		try (CalendarioDAO daoC = new CalendarioDAOImpl()) {
			ArrayList<Edizione> edizioni = daoC.select();
			for (Edizione edit : edizioni) {

				Date date = edit.getDataInizio();
				DateFormat dateFormat = new SimpleDateFormat("yyyy");
				String strDate = dateFormat.format(date); 

				anni.add(strDate);
			}
			
			

			String JsonYears = new Gson().toJson(anni);

			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			out.print(JsonYears);
			out.flush();
			return;

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
