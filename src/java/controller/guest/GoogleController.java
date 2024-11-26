/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.guest;

import codebase.EmailSender.Email;
import codebase.hashFunction.SHA256_Encryptor;
import codebase.random.Random;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dal.LoginUserDAO;
import dal.MyDAO;
import dal.RegisterUserDAO;
import entity.ThanhCustomEntity;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ResourceBundle;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

/**
 *
 * @author Acer Aspire 7
 */
@WebServlet(name = "LoginGoogleController", urlPatterns = {"/logingooglecontroller"})
public class GoogleController extends HttpServlet {

    static final ResourceBundle bundle = ResourceBundle.getBundle("config.login_google");
    static final LoginUserDAO luDAO = new LoginUserDAO();
    static final RegisterUserDAO regisDAO = new RegisterUserDAO();
    HttpSession session;
    Random ran = new Random();

    public static String getToken(String code) throws ClientProtocolException, IOException {
        // call api to get token
        String response = Request.Post(bundle.getString("GOOGLE_LINK_GET_TOKEN"))
                .bodyForm(Form.form().add("client_id", bundle.getString("GOOGLE_CLIENT_ID"))
                        .add("client_secret", bundle.getString("GOOGLE_CLIENT_SECRET"))
                        .add("redirect_uri", bundle.getString("GOOGLE_REDIRECT_URI")).add("code", code)
                        .add("grant_type", bundle.getString("GOOGLE_GRANT_TYPE")).build())
                .execute().returnContent().asString();

        JsonObject jobj = new Gson().fromJson(response, JsonObject.class);
        String accessToken = jobj.get("access_token").toString().replaceAll("\"", "");
        return accessToken;
    }

    public static entity.codebaseNew.User getUserInfo(final String accessToken) throws ClientProtocolException, IOException {
        String link = bundle.getString("GOOGLE_LINK_GET_USER_INFO") + accessToken;
        String response = Request.Get(link).execute().returnContent().asString();

        entity.codebaseNew.User googleUser = new Gson().fromJson(response, entity.codebaseNew.User.class);

        return googleUser;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        session = request.getSession();
        String code = request.getParameter("code");
        String accessToken = getToken(code);

        String url = "https://people.googleapis.com/v1/people/me?personFields=names,emailAddresses,phoneNumbers,photos,genders";

        String apiResponse = Request.Get(url)
                .addHeader("Authorization", "Bearer " + accessToken)
                .execute()
                .returnContent()
                .asString();

        System.out.println(apiResponse);

        Gson gson = new Gson();
        JsonObject userInfo = gson.fromJson(apiResponse, JsonObject.class);

        String email;
        String fName;
        String lName;
        String phone;
        String avatar;
        String gender = null;

        if (userInfo.has("emailAddresses") && userInfo.getAsJsonArray("emailAddresses").size() > 0) {
            JsonObject emailAddresses = userInfo.getAsJsonArray("emailAddresses").get(0).getAsJsonObject();
            System.out.println("Email: " + emailAddresses.get("value").getAsString());
            email = emailAddresses.get("value").getAsString();
        } else {
            email = null;
        }

        if (userInfo.has("names") && userInfo.getAsJsonArray("names").size() > 0) {
            JsonObject names = userInfo.getAsJsonArray("names").get(0).getAsJsonObject();
            lName = names.get("familyName").getAsString();
            fName = names.get("givenName").getAsString();
        } else {
            lName = null;
            fName = null;
        }

        if (userInfo.has("phoneNumbers") && userInfo.getAsJsonArray("phoneNumbers").size() > 0) {
            JsonObject phoneNumbers = userInfo.getAsJsonArray("phoneNumbers").get(0).getAsJsonObject();
            phone = phoneNumbers.get("value").getAsString();
        } else {
            phone = null;
        }

        if (userInfo.has("photos") && userInfo.getAsJsonArray("photos").size() > 0) {
            JsonObject photos = userInfo.getAsJsonArray("photos").get(0).getAsJsonObject();
            avatar = photos.get("url").getAsString();
        } else {
            avatar = null;
        }

        if (userInfo.has("genders") && userInfo.getAsJsonArray("genders").size() > 0) {
            JsonObject genders = userInfo.getAsJsonArray("genders").get(0).getAsJsonObject();
            if (genders.get("value").getAsString().equalsIgnoreCase("male")) {
                gender = "M";
            }
            if (genders.get("value").getAsString().equalsIgnoreCase("female")) {
                gender = "F";
            }
        } else {
            gender = null;
        }

        if (email == null) {
            request.setAttribute("msg", "Invalid google account can not login/register!");
            request.getRequestDispatcher("public/Login.jsp").forward(request, response);
        } else {
            doAuthorizeGoogleUser(email, fName, lName, phone, gender, avatar, session, request, response);
        }
    }

    private void doAuthorizeGoogleUser(String email, String fName, String lName, String phone, String gender, String avatar,
            HttpSession session, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (luDAO.isValidEmail(email)) {
            ThanhCustomEntity.LoginUserDTO user = luDAO.getLoginUserByEmail(email);
            if ("active".equalsIgnoreCase(user.getUserStatus())) {
                MyDAO myDAO = new MyDAO();
                int roleId = user.getUserRollID();
                int productInCart = myDAO.getProductCountInCart(user.getUserID());
                session.setAttribute("uid", user.getUserID());
                session.setAttribute("username", luDAO.getUsernameByUid(user.getUserID()));
                session.setAttribute("roleId", roleId);
                session.setAttribute("productInCart", productInCart);
                session.setMaxInactiveInterval(86400);

                Cookie uEmail = new Cookie("uEmail", email);
                Cookie uPass = new Cookie("uPass", user.getUserPass());
                Cookie uRem = new Cookie("uRem", "on");
                int aWeek = 60 * 60 * 24 * 7; // 1 week
                uEmail.setMaxAge(aWeek);
                uPass.setMaxAge(aWeek);
                uRem.setMaxAge(aWeek);
                response.addCookie(uEmail);
                response.addCookie(uPass);
                response.addCookie(uRem);

                switch (roleId) {
                    case 1 -> {
                        response.sendRedirect("homecontroller");
                    }
                    case 2 -> {
                        response.sendRedirect("admindashboard");
                    }
                    case 3 -> {
                        response.sendRedirect("customers");
                    }
                    case 4 -> {
                        response.sendRedirect("ordermanager?type=all");
                    }
                    case 5 -> {
                        response.sendRedirect("marketingdashboard");
                    }
                    case 6 -> {
                        response.sendRedirect("admindashboard");
                    }
                    case 7 -> {
                        response.sendRedirect("ordermanager?type=all");
                    }
                }
            } else {
                String msg = "Your account has been banned, contact admin for more infor";
                request.setAttribute("msg", msg);
                request.getRequestDispatcher("public/Login.jsp").forward(request, response);
            }
        } else {
            String randomPass = ran.getString(16, "");
            String enCodeRandomPass = SHA256_Encryptor.sha256Hash(randomPass);

            regisDAO.registerGoogle(fName, lName, email, enCodeRandomPass, gender, phone, avatar);

            ThanhCustomEntity.LoginUserDTO user = luDAO.getLoginUserByEmail(email);

            MyDAO myDAO = new MyDAO();
            int productInCart = myDAO.getProductCountInCart(user.getUserID());
            session.setAttribute("uid", user.getUserID());
            session.setAttribute("username", luDAO.getUsernameByUid(user.getUserID()));
            session.setAttribute("roleId", user.getUserRollID());
            session.setAttribute("productInCart", productInCart);
            session.setMaxInactiveInterval(86400);

            Cookie uEmail = new Cookie("uEmail", email);
            Cookie uPass = new Cookie("uPass", enCodeRandomPass);
            Cookie uRem = new Cookie("uRem", "on");
            int aWeek = 60 * 60 * 24 * 7; // 1 week
            uEmail.setMaxAge(aWeek);
            uPass.setMaxAge(aWeek);
            uRem.setMaxAge(aWeek);
            response.addCookie(uEmail);
            response.addCookie(uPass);
            response.addCookie(uRem);

            Email.sendEmail(email, "Registration successfully!",
                    "Here is your account info:<br/><br/>"
                    + "<div class='email-container'>"
                    + "  <h1>Welcome to Freeze!</h1>"
                    + "  <p>You have successfully signed up. Here are your account details:</p>"
                    + "  <p><strong>Email:</strong> " + email + "</p>"
                    + "  <p><strong>Password:</strong> " + randomPass + "</p>"
                    + "  <a href='http://localhost:9999/freezegroup2/changepassword' class='button'>Change Password</a>"
                    + "  <div class='footer'>"
                    + "    <p>Need help? Contact us at <a href='mailto:thefreezeclothing@gmail.com'>thefreezeclothing@gmail.com</a></p>"
                    + "  </div>"
                    + "</div>");

            response.sendRedirect("homecontroller");
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
