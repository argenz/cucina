package servlet;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.EdizioneDTO;
import entity.Edizione;
import service.EdizioneService;
import service.EdizioneServiceImpl;

@WebServlet("/ModificaEdizione")
public class ModificaEdizioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String messaggio = "";

		if (request.getParameter("edizione") != null) {
			try {
				EdizioneService edS = new EdizioneServiceImpl();

				int edi = Integer.parseInt(request.getParameter("edizione"));

				EdizioneDTO edizione = edS.visualizzaEdizione(edi);
				Edizione ed = edizione.getEdizione();
				request.setAttribute("idCorso", ed.getIdCorso());
				request.setAttribute("dataDiInizio", ed.getDataInizio());
				request.setAttribute("durata", ed.getDurata());
				request.setAttribute("aula", ed.getAula());
				request.setAttribute("docente", ed.getDocente());
				request.setAttribute("codice", ed.getCodice());		
				RequestDispatcher rdright = request.getRequestDispatcher("/WEB-INF/jsp/modificaEdizione.jsp");
				request.setAttribute("messaggio", messaggio);
				rdright.forward(request, response);
				return;
			} catch (Exception e) {
				messaggio = e.getMessage();
			}

		}

		int codice = Integer.parseInt(request.getParameter("codice"));
		int idCorso = Integer.parseInt(request.getParameter("idCorso"));
		Date dataDiInizio = Date.valueOf(request.getParameter("dataDiInizio"));
		int durata = Integer.parseInt(request.getParameter("durata"));
		String aula = request.getParameter("aula");
		String docente = request.getParameter("docente");
		Edizione ed = new Edizione(codice, idCorso, dataDiInizio, durata, aula, docente);
		try {
			new EdizioneServiceImpl().modificaEdizione(ed);
			messaggio = "Edizione modificata con successo";
		} catch (Exception e) {
			messaggio = e.getMessage();
		} finally {
			request.setAttribute("messaggio", messaggio);
			RequestDispatcher rdright = request.getRequestDispatcher("/WEB-INF/jsp/adminpage.jsp");
			rdright.forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}