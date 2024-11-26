/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Filter.java to edit this template
 */
package filter;

import codebase.DataConverter.MyBase64;
import codebase.DataConverter.MyToken;
import controller.admin.UserDAO;
import dal.LoginUserDAO;
import dal.MyDAO;
import entity.ThanhCustomEntity;
import entity.codebaseNew.Role;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 *
 * @author Nguyễn Nhật Minh
 */
@WebFilter(filterName = "UserAuthorization", urlPatterns = {"/*"})
public class UserAuthorization implements Filter {

    private static final String[] saler = {
        "recaptcha",
        "error",
        "customers",
        "EditProfileController",
        "LogOut",
        "login",
        "userprofilecontroller",
        "ordermanager?type=all",
        "ordermanager",
        "changepassword"
    };

    private static final String[] salemanager = {
        "recaptcha",
        "error",
        "customers",
        "EditProfileController",
        "LogOut",
        "login",
        "userprofilecontroller",
        "admindashboard",
        "ordermanager?type=all",
        "ordermanager",
        "changepassword"
    };

    private static final String[] marketer = {
        "customers",
        "sliderdetail",
        "addslider",
        "recaptcha",
        "error",
        "new-product",
        "EditProfileController",
        "LogOut",
        "userprofilecontroller",
        "marketingdashboard",
        "productlistmanager?type=all",
        "feedbackservlet",
        "sliderlist",
        "handler",
        "search",
        "update",
        "productlistmanager",
        "changepassword",
        "feedbackdetail",
        "feedbacklist",
        "addslider",
        "sliderdetail",
        "bloglistmanager",
        "blogdetailmanager",
        "addnewpost"
//        "productlist",
    };

    private static final String[] admin = {
        "addresses",
        "error",
        "settings",
        "customers",
        "sliderdetail",
        "addslider",
        "recaptcha",
        "new-product",
        "EditProfileController",
        "LogOut",
        "userprofilecontroller",
        "admindashboard",
        "marketingdashboard",
        "ordermanager?type=all",
        "feedbackservlet",
        "sliderlist",
        "productlistmanager?type=all",
        "users",
        "handler",
        "search",
        "productlistmanager",
        "ordermanager",
        "bloglist",
        "blogdetail",
        "feedbackdetail",
        "update",
        "productlist?type=",
        "productdetail",
        "changepassword",
        "deleteslider",
        "bloglistmanager",
        "feedbacklist",
        "productlist?type=",
        "productlist",
        "blogdetailmanager",
        "addnewpost",
        "bloglistmanager?type=all"
    };

    private static final String[] shipper = {
        "recaptcha",
        "error",
        "EditProfileController",
        "LogOut",
        "userprofilecontroller",
        "ordermanager?type=all",
        "ordermanager",
        "changepassword"
    };

    private static final String[] guest = {
        "refund",
        "recaptcha",
        "error",
        "register",
        "login",
        "newcollection",
        "productlist",
        "productdetail",
        "wishlist",
        "testAdmin",
        "bloglist",
        "blogdetail",
        "feedbackservlet",
        "feedbackdetail",
        "resetpassword",
        "logingooglecontroller",
        "addtocart",
        "homecontroller",
        "blogdetail"
    };

    private static final String[] customer = {
        "recaptcha",
        "error",
        "register",
        "login",
        "newcollection2",
        "newcollection",
        "productlist",
        "productdetail",
        "cart",
        "LoadMoreCartItem",
        "DeleteCartItem",
        "userprofilecontroller",
        "EditProfileController",
        "LogOut",
        "RequestVerificationCodeServlet",
        "UpdatePasswordServlet",
        "changepassword",
        "cartcontact",
        "searchboxlogic",
        "wishlist",
        "feedback",
        "bloglist",
        "cartcompletion",
        "searchblog",
        "sliderlist",
        "sliderdetail",
        "blogdetail",
        "pay",
        "ordersuccess",
        "deleteslider",
        "addslider",
        "vnpayajax",
        "billlist",
        "rebuy",
        "billdetail",
        "success",
        "checkoutUser",
        "saveAddress",
        "logingooglecontroller",
        "homecontroller",
        "refund",
        "customer/RefundRequest.jsp"
    };

    private static final boolean debug = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    public UserAuthorization() {
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("UserAuthorization:DoBeforeProcessing");
        }

        // Write code here to process the request and/or response before
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log items on the request object,
        // such as the parameters.
        /*
	for (Enumeration en = request.getParameterNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    String values[] = request.getParameterValues(name);
	    int n = values.length;
	    StringBuffer buf = new StringBuffer();
	    buf.append(name);
	    buf.append("=");
	    for(int i=0; i < n; i++) {
	        buf.append(values[i]);
	        if (i < n-1)
	            buf.append(",");
	    }
	    log(buf.toString());
	}
         */
    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("UserAuthorization:DoAfterProcessing");
        }

        // Write code here to process the request and/or response after
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log the attributes on the
        // request object after the request has been processed. 
        /*
	for (Enumeration en = request.getAttributeNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    Object value = request.getAttribute(name);
	    log("attribute: " + name + "=" + value.toString());

	}
         */
        // For example, a filter might append something to the response.
        /*
	PrintWriter respOut = new PrintWriter(response.getWriter());
	respOut.println("<P><B>This has been appended by an intrusive filter.</B>");
         */
    }

    /**
     *
     *
     * @param servletRequest
     * @param servletResponse
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
            FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        String uri = request.getRequestURI();  // Get the URL being requested

        // Allow static resources like CSS, JS, and images to bypass the filter
        if (uri.startsWith(request.getContextPath() + "/asset/")
                || uri.startsWith(request.getContextPath() + "/static/")
                || uri.startsWith(request.getContextPath() + "/images/")) {

            // Allow static resources to proceed without filter checks
            filterChain.doFilter(servletRequest, servletResponse);
            return; // Exit the method after allowing the request
        }

        // process JWT cookie
        Cookie[] cookie_raw = request.getCookies();
        Map<String, String> cookie_map = new HashMap<>();
        if (cookie_raw != null) {
            for (Cookie c : cookie_raw) {
                cookie_map.put(c.getName(), c.getValue());
            }
        }

        String cookie_uEmail = cookie_map.get("uEmail");
        String cookie_uPass = cookie_map.get("uPass");
        String cookie_uRem = cookie_map.get("uRem");
        String token = cookie_map.get("FreezeToken");
        this.log("Request from "
                + "uEmail: " + cookie_uEmail + ", "
                + "uRem: " + cookie_uRem + ", "
                + "Token: " + token + ", "
                + "uPass: " + cookie_uPass);

        LoginUserDAO luDAO = new LoginUserDAO();
        ThanhCustomEntity.LoginUserDTO user = null;
        try {
            user = luDAO.getLoginUser(cookie_uEmail, cookie_uPass);
        } catch (Exception e) {
            this.log(e.getMessage());
            response.sendError(500);
            return;
        }
        if (user == null
                && session != null
                && session.getAttribute("uid") != null) {
            int tmp_userId = Integer.parseInt(session.getAttribute("uid").toString());
            user = luDAO.getLoginUserById(tmp_userId);
        }
        if (user != null) {
            // reset cookie
            Cookie uEmail = new Cookie("uEmail", cookie_uEmail);
            Cookie uPass = new Cookie("uPass", cookie_uPass);
            Cookie uRem = new Cookie("uRem", cookie_uRem);
            int aWeek = 60 * 60 * 24 * 7; // 1 week
            uEmail.setMaxAge(aWeek);
            uPass.setMaxAge(aWeek);
            uRem.setMaxAge(60 * 60 * 24 * 30 * 3); // 3 months, same with token
            response.addCookie(uEmail);
            response.addCookie(uPass);
            response.addCookie(uRem);

            // is active accout
            if ("active".equalsIgnoreCase(user.getUserStatus())) {
                MyDAO myDAO = new MyDAO();
                int productInCart = myDAO.getProductCountInCart(user.getUserID());
                session.setAttribute("uid", user.getUserID());
                session.setAttribute("username", luDAO.getUsernameByUid(user.getUserID()));
                session.setAttribute("roleId", user.getUserRollID());
                session.setAttribute("productInCart", productInCart);
                session.setMaxInactiveInterval(86400); //24h
            } else { // is not active (mostly cuz of banned)
                String msg = "Your account has been baned, contact admin for more infor";
                request.setAttribute("msg", msg);
                request.getRequestDispatcher("public/Login.jsp").forward(request, response);
                return;
            }

            // have cookie but firstime token
            if (token == null) {
                JSONObject header = new JSONObject();
                header.put("typ", "fake jwt");
                header.put("prj", "FREEZE");

                // gen new token
                JSONObject payload = new JSONObject();
                payload.put("uid", user.getUserID());
                payload.put("rid", user.getUserRollID());
                payload.put("ema", user.getUserEmail());
                payload.put("enp", user.getUserPass());
                payload.put("sta", user.getUserStatus());

                MyToken newToken = new MyToken(header, payload);

                String toSendToken = null;
                try {
                    toSendToken = newToken.getToken();
                } catch (Exception ex) {
                    String msg = "Exception throwed from UserAuthorization filter while"
                            + "trying to get new Token";
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, msg, ex);
                }

                if (toSendToken == null) {
                    toSendToken = newToken.getDefaultToken();
                }

                Cookie c = new Cookie("FreezeToken", toSendToken);
                c.setMaxAge(60 * 60 * 24 * 30 * 3); // 3 months token

                response.addCookie(c);

                response.sendRedirect("recaptcha");
                return;

            }
        }

        // cookie removed but token still
        if (token != null
                && cookie_uRem != null
                && "on".equals(cookie_uRem)
                && user == null) {
            MyToken myToken = new MyToken();
            boolean isValidToken = myToken.isValidToken(token);

            // not valid token
            if (!isValidToken) {
                response.sendError(401);
                return;
            }

            // valid token, get data
            String encoded_payloadString = token.split("[" + myToken.getPrimaryRegex() + "]")[1];
            String payloadString = MyBase64.decodeString(encoded_payloadString);
            JSONObject payload = new JSONObject(payloadString);
            int uId = (int) payload.get("uid");
            int roldId = (int) payload.get("rid");
            String email = payload.getString("ema");
            String encoded_password = payload.getString("enp");
            String status = payload.getString("sta");

            // test data
            ThanhCustomEntity.LoginUserDTO u = luDAO.getLoginUser(email, encoded_password);
            if (u != null
                    && (u.getUserID() + "").equals(uId + "")
                    && (u.getUserRollID() + "").equals(roldId + "")
                    && (u.getUserStatus()).equals(status)) {

                // is active accout
                if ("active".equalsIgnoreCase(u.getUserStatus())) {
                    MyDAO myDAO = new MyDAO();
                    int productInCart = myDAO.getProductCountInCart(u.getUserID());
                    session.setAttribute("uid", u.getUserID());
                    session.setAttribute("username", luDAO.getUsernameByUid(u.getUserID()));
                    session.setAttribute("roleId", u.getUserRollID());
                    session.setAttribute("productInCart", productInCart);
                    session.setMaxInactiveInterval(86400); //24h

                } else { // is not active (mostly cuz of banned)
                    String msg = "Your account has been baned, contact admin for more infor";
                    request.setAttribute("msg", msg);
                    request.getRequestDispatcher("public/Login.jsp").forward(request, response);
                    return;
                }
            }
        }

        // process index
        if (uri.startsWith(request.getContextPath() + "/") && uri.endsWith(request.getContextPath() + "/")) {
            response.sendRedirect("recaptcha");
            return;
        }

        // fixed blocker bug "null session attributes"
        Role userRole = null;
        if (session == null || session.getAttribute("uid") == null) {
            userRole = new Role()
                    .setId(-1)
                    .setName("Guest")
                    .setWeight(-1);
        } else {
            int userID = Integer.parseInt(session.getAttribute("uid").toString());
            UserDAO dao = new UserDAO();
            userRole = dao.getUserRoleByUserID(userID);
        }
        request.getSession().setAttribute("roleId", userRole.getId());

        String url = request.getRequestURI() + "?" + request.getQueryString();
        boolean legit = false;

        // user is "guest"
        if (userRole.getId() == -1) {
            for (int i = 0; i < guest.length; i++) {
                if (url.contains(guest[i])) {
                    legit = true;
                    break;
                }
            }
            if (url.contains("productlistmanager?type=all")) {
                legit = false;
            }
        }

        // user is customer
        if (userRole.getId() == 1) {
            for (int i = 0; i < customer.length; i++) {
                if (url.contains(customer[i])) {
                    legit = true;
                    break;
                }
            }
            if (url.contains("productlistmanager?type=all")) {
                legit = false;
            }
        }

        // user is admin
        if (userRole.getId() == 2) {
            for (int i = 0; i < admin.length; i++) {
                if (url.contains(admin[i])) {
                    legit = true;
                    break;
                }
            }
        }

        // user is marketer
        if (userRole.getId() == 3) {
            for (int i = 0; i < marketer.length; i++) {
                if (url.contains(marketer[i])) {
                    legit = true;
                    break;
                }
            }
        }

        // user is saler
        if (userRole.getId() == 4) {
            for (int i = 0; i < saler.length; i++) {
                if (url.contains(saler[i])) {
                    legit = true;
                    break;
                }
            }
        }

        // user is salemanager
        if (userRole.getId() == 6) {
            for (int i = 0; i < salemanager.length; i++) {
                if (url.contains(salemanager[i])) {
                    legit = true;
                    break;
                }
            }
        }

        // user is shipper
        if (userRole.getId() == 7) {
            for (int i = 0; i < shipper.length; i++) {
                if (url.contains(shipper[i])) {
                    legit = true;
                    break;
                }
            }
        }

        if (uri.startsWith(request.getContextPath() + "/LogOut")
                && uri.endsWith(request.getContextPath() + "/LogOut")) {
            request.getSession().invalidate();
            for (Cookie cookie : cookie_raw) {
                if ("FreezeToken".equals(cookie.getName())) {
                    continue;
                }
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }

        if (!legit) {
            response.sendError(401);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    /**
     * Return the filter configuration object for this filter.
     *
     * @return
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("UserAuthorization:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("UserAuthorization()");
        }
        StringBuffer sb = new StringBuffer("UserAuthorization(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}
