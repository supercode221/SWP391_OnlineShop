package controller.guest;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet for handling Cloudflare Turnstile verification.
 */
public class ReCaptcha extends HttpServlet {

    // Load configuration for Turnstile secret key from properties file
    private final ResourceBundle bundle = ResourceBundle.getBundle("config.cloudflare_recaptcha");

    // Fixed JSP path to display the Turnstile form and results
    private final String fixedForwardFrontend = "public/cloudflare_recaptcha.jsp";

    /**
     * Processes GET requests.
     */
    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        // get data
        String siteKey = bundle.getString("sitekey");

        // no process
        // set data
        request.setAttribute("sitekey", siteKey);
        
        // Forward to the fixed JSP frontend
        request.getRequestDispatcher(this.fixedForwardFrontend).forward(request, response);
    }

    /**
     * Processes POST requests.
     */
    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // Get the token and IP address from the request
        String token = request.getParameter("cf-turnstile-response");
        String ip = request.getRemoteAddr();

        // Initialize the API endpoint and HTTP connection
        URL url = new URL("https://challenges.cloudflare.com/turnstile/v0/siteverify");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        // Prepare the POST data
        String postData = "secret=" + bundle.getString("secretkey")
                + "&response=" + token
                + "&remoteip=" + ip;

        // Send the POST data
        try (OutputStream os = connection.getOutputStream()) {
            os.write(postData.getBytes());
            os.flush();
        }

        // Read the API response
        StringBuilder jsonResponse = new StringBuilder();
        try (Scanner scanner = new Scanner(connection.getInputStream())) {
            while (scanner.hasNext()) {
                jsonResponse.append(scanner.nextLine());
            }
        }

        // Parse the JSON response and check success
        String result = jsonResponse.toString();
        boolean isSuccess = result.contains("\"success\":true");

        // Forward
        if (isSuccess) {
            response.sendRedirect("homecontroller");
            
        } else {
            response.sendError(401);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processGetRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processPostRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet for handling Cloudflare Turnstile verification.";
    }

    /**
     * Custom logging function for better debugging.
     */
    private void log(Level level, String msg, Throwable e) {
        Logger.getLogger(this.getClass().getName()).log(level, msg, e);
    }
}
