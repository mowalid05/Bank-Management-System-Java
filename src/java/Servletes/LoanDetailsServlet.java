package Servletes;




import Classes.Loan;
import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/loan-details")
public class LoanDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        int loanId = Integer.parseInt(req.getParameter("loanId"));

        try {
            Loan loan = Loan.getLoanById(loanId);
            if (loan == null) {
                res.sendError(HttpServletResponse.SC_NOT_FOUND, "Loan not found");
                return;
            }
            req.setAttribute("loan", loan);
            req.getRequestDispatcher("loanDetails.jsp")
               .forward(req, res);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
