import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

@WebServlet("/Main")
public class Main extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	static final long serialVersionUID = 1L;

	public Main() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String userID = request.getParameter("userID");
		String authFailMessage = "Your authentication has failed, please try again. ";
		String failcolor = "red";
		String authSuccMessage = "Your authentication is successful. ";
		String succcolor = "green";
		String succVisible = "100";
		String failVisible = "0";
		String button_param = request.getParameter("submit");

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm?user=root&password=root");
			Statement stmt = con.createStatement();
			try {
				stmt.execute(
						"CREATE TABLE IF NOT EXISTS accountinfo( userID INT NOT NULL, Name varchar(30) NOT NULL, Balance float NOT NULL)");
			} catch (Exception e) {
			}

			final String queryCheck = "SELECT name, balance from accountinfo WHERE userID = '" + userID + "'";
			ResultSet rs = stmt.executeQuery(queryCheck);

			if (button_param.equals("Submit")) {
				if (rs.next()) {
					request.getSession().setAttribute("message", authSuccMessage);
					request.getSession().setAttribute("messagecolor", succcolor);
					request.getSession().setAttribute("Visible", succVisible);

					String username = rs.getString("name");
					request.setAttribute("username", username);
					Float balance = Float.parseFloat(rs.getString("balance"));
					request.setAttribute("updatedbalance", balance);
					request.getRequestDispatcher("Main.jsp").forward(request, response);
					System.out.println(rs.getString(2));

				} else {
					request.getSession().setAttribute("Visible", failVisible);
					request.getSession().setAttribute("message", authFailMessage);
					request.getSession().setAttribute("messagecolor", failcolor);
					response.sendRedirect("Main.jsp");

				}
			}
			rs.close();
		} catch (Exception ex) {
			System.out.println(ex);
			System.exit(0);
		}
		System.out.println("Program terminated with no error.");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String userID = request.getParameter("userID");
		float withdrawAmt = 0;
		float depositAmt = 0;
		String withdrawFail = "There is insufficient funds, please try a smaller amount";
		String withdrawSucc = "Amount Withdrawn!";
		String depositSucc = "Amount deposited!";
		String withdepFail = "Please enter a valid number";
		String messageSucc = "green";
		String messageFail = "red";

		try {
			String btn = request.getParameter("action");

			Class.forName("com.mysql.jdbc.Driver").newInstance();

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm?user=root&password=root");
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			con.setAutoCommit(false);
			String withAmt = (request.getParameter("WithdrawAmount"));
			String depAmt = (request.getParameter("DepositAmount"));

			if (("Deposit".equals(btn)) && (Integer.valueOf(depAmt) >= 0) || (depAmt)!=null) {
				depositAmt = Float.parseFloat(depAmt);

				String s = "UPDATE accountinfo SET Balance=Balance+" + (depositAmt) + " WHERE userID = '" + userID
						+ "'";

				stmt.addBatch(s);
				stmt.executeBatch();
				con.commit();
				ResultSet rs = stmt.executeQuery("Select * from accountinfo WHERE userId='" + userID + "'");
				rs.last();
				System.out.println("Amount deposited:" + depositAmt);
				System.out.println("Current balance: " + (rs.getString("balance")));
				String username = rs.getString("name");
				request.setAttribute("username", username);
				Float balance = Float.parseFloat(rs.getString("balance"));
				request.setAttribute("updatedbalance", balance);
				request.getSession().setAttribute("DepositSucc", depositAmt);
				request.getSession().setAttribute("withdrawDeposit", depositSucc);
				request.getSession().setAttribute("wd", messageSucc);
				request.getRequestDispatcher("Main.jsp").forward(request, response);

			} else if (("Withdraw".equals(btn)&& (Integer.valueOf(withAmt) >= 0) || (withAmt)!=null)) {
				withdrawAmt = Float.parseFloat(withAmt);
				ResultSet rs1 = stmt.executeQuery("Select * from accountinfo WHERE userId='" + userID + "'");
				rs1.last();
				float oldbalance = Float.parseFloat(rs1.getString("balance"));
				System.out.println("Old balance: " + oldbalance);
				rs1.close();
				stmt.clearBatch();
				if (oldbalance > withdrawAmt) {
					String s = "UPDATE accountinfo SET Balance=Balance-" + (withdrawAmt) + " WHERE userID = '" + userID
							+ "' ";

					stmt.addBatch(s);
					stmt.executeBatch();
					con.commit();
					ResultSet rs = stmt.executeQuery("Select * from accountinfo WHERE userId='" + userID + "'");
					rs.last();
					System.out.println("Amount withdrawn:" + withdrawAmt);
					System.out.println("Current balance: " + (rs.getString("balance")));
					String username = rs.getString("name");
					request.setAttribute("username", username);
					Float balance = Float.parseFloat(rs.getString("balance"));
					request.setAttribute("updatedbalance", balance);
					request.getSession().setAttribute("withdrawDeposit", withdrawSucc);
					request.getSession().setAttribute("wd", messageSucc);
					request.getRequestDispatcher("Main.jsp").forward(request, response);

				} else {
					ResultSet rs = stmt.executeQuery("Select * from accountinfo WHERE userId='" + userID + "'");
					rs.last();
					String username = rs.getString("name");
					request.setAttribute("username", username);
					Float balance = Float.parseFloat(rs.getString("balance"));
					request.setAttribute("updatedbalance", balance);
					request.getSession().setAttribute("withdrawDeposit", withdrawFail);
					request.getSession().setAttribute("wd", messageFail);
					request.getRequestDispatcher("Main.jsp").forward(request, response);
				}
			} else if((Integer.valueOf(withAmt) < 0) || (withAmt)=="" ||(Integer.valueOf(depAmt) < 0) || (depAmt)=="") {
				ResultSet rs = stmt.executeQuery("Select * from accountinfo WHERE userId='" + userID + "'");
				rs.last();
				String username = rs.getString("name");
				request.setAttribute("username", username);
				Float balance = Float.parseFloat(rs.getString("balance"));
				request.setAttribute("updatedbalance", balance);
				request.getSession().setAttribute("withdrawDeposit", withdepFail);
				request.getSession().setAttribute("wd", messageFail);
				request.getRequestDispatcher("Main.jsp").forward(request, response);
			}
		} catch (Exception ex) {
			System.out.println("error" + ex);
			System.exit(0);
		}
		System.out.println("Program terminated with no error.Post");

	}
}
