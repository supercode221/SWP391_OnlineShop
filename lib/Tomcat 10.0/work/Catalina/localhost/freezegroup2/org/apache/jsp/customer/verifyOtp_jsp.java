/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/10.1.17
 * Generated at: 2024-10-02 10:31:41 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.customer;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.jsp.*;

public final class verifyOtp_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports,
                 org.apache.jasper.runtime.JspSourceDirectives {

  private static final jakarta.servlet.jsp.JspFactory _jspxFactory =
          jakarta.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("jakarta.servlet");
    _jspx_imports_packages.add("jakarta.servlet.http");
    _jspx_imports_packages.add("jakarta.servlet.jsp");
    _jspx_imports_classes = null;
  }

  private volatile jakarta.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public boolean getErrorOnELNotFound() {
    return false;
  }

  public jakarta.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final jakarta.servlet.http.HttpServletRequest request, final jakarta.servlet.http.HttpServletResponse response)
      throws java.io.IOException, jakarta.servlet.ServletException {

    if (!jakarta.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      final java.lang.String _jspx_method = request.getMethod();
      if ("OPTIONS".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        return;
      }
      if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET, POST or HEAD. Jasper also permits OPTIONS");
        return;
      }
    }

    final jakarta.servlet.jsp.PageContext pageContext;
    jakarta.servlet.http.HttpSession session = null;
    final jakarta.servlet.ServletContext application;
    final jakarta.servlet.ServletConfig config;
    jakarta.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    jakarta.servlet.jsp.JspWriter _jspx_out = null;
    jakarta.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write('\r');
      out.write('\n');
 String message = (String) request.getParameter("message"); 
      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html lang=\"en\">\r\n");
      out.write("    <head>\r\n");
      out.write("        <meta charset=\"UTF-8\">\r\n");
      out.write("        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n");
      out.write("        <link rel=\"icon\" href=\"home-guest/favicon.png\">\r\n");
      out.write("        <title>Enter OTP</title>\r\n");
      out.write("        <style>\r\n");
      out.write("            body {\r\n");
      out.write("                background: linear-gradient(135deg, #30BD36, #5A84E6);\r\n");
      out.write("                font-family: sans-serif;\r\n");
      out.write("                margin: 0;\r\n");
      out.write("                display: flex;\r\n");
      out.write("                justify-content: center;\r\n");
      out.write("                align-items: center;\r\n");
      out.write("                height: 100vh;\r\n");
      out.write("            }\r\n");
      out.write("\r\n");
      out.write("            .enter-otp {\r\n");
      out.write("\r\n");
      out.write("                background-color: white;\r\n");
      out.write("                width: fit-content;\r\n");
      out.write("                text-align: center;\r\n");
      out.write("                padding: 20px;\r\n");
      out.write("                box-shadow: 0 0 10px rgba(255, 255, 255, 0.2);\r\n");
      out.write("            }\r\n");
      out.write("\r\n");
      out.write("            .enter-otp img {\r\n");
      out.write("                width: 150px;\r\n");
      out.write("                margin-top: 20px;\r\n");
      out.write("            }\r\n");
      out.write("\r\n");
      out.write("            .otp-text {\r\n");
      out.write("                color: red;\r\n");
      out.write("                font-size: 15px;\r\n");
      out.write("                margin-top: 10px;\r\n");
      out.write("            }\r\n");
      out.write("\r\n");
      out.write("            .text-notice {\r\n");
      out.write("                color: #343a40;\r\n");
      out.write("                font-size: 20px;\r\n");
      out.write("                margin-top: 10px;\r\n");
      out.write("            }\r\n");
      out.write("\r\n");
      out.write("            .otp-value input {\r\n");
      out.write("                width: 200px;\r\n");
      out.write("                padding: 8px;\r\n");
      out.write("                margin-top: 20px;\r\n");
      out.write("                border: 1px solid #ccc;\r\n");
      out.write("                border-radius: 4px;\r\n");
      out.write("                box-sizing: border-box;\r\n");
      out.write("                font-size: 16px;\r\n");
      out.write("            }\r\n");
      out.write("\r\n");
      out.write("            .reset-password input {\r\n");
      out.write("                background-color: #30BD36;\r\n");
      out.write("                color: white;\r\n");
      out.write("                border: none;\r\n");
      out.write("                border-radius: 6px;\r\n");
      out.write("                padding: 10px 20px;\r\n");
      out.write("                cursor: pointer;\r\n");
      out.write("                font-size: 16px;\r\n");
      out.write("                margin-top: 20px;\r\n");
      out.write("            }\r\n");
      out.write("        </style>\r\n");
      out.write("    </head>\r\n");
      out.write("    <body>\r\n");
      out.write("        <div class=\"enter-otp\">\r\n");
      out.write("            <div class=\"text-notice\">Enter OTP</div>\r\n");
      out.write("            <img src=\"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRWxaKG6HL-_z88M5D0-zeXZjQHqN33XNtYmA&usqp=CAU\" alt=\"Response Image\">\r\n");
      out.write("            <div class=\"text-notice\">Check your email for the OTP</div>\r\n");
      out.write("            <div class=\"otp-text\">\r\n");
      out.write("                ");
 if(message != null) { 
      out.write("\r\n");
      out.write("                <h3 id=\"message\" style=\"color: #FF0E0E; margin-top: 20px;\">");
      out.print( message );
      out.write("</h3> \r\n");
      out.write("                ");
 } 
      out.write("\r\n");
      out.write("            </div>\r\n");
      out.write("            <form id=\"register-form\" action=\"ResetPasswordServlet\" class=\"form\" method=\"post\">\r\n");
      out.write("                <div class=\"otp-value\">\r\n");
      out.write("                    <input id=\"opt\" name=\"otp_code\" placeholder=\"Enter OTP\" type=\"text\" required>\r\n");
      out.write("                    <input type=\"hidden\" name=\"flag\" value=\"verify_otp\">\r\n");
      out.write("                </div>\r\n");
      out.write("                <div class=\"reset-password\">\r\n");
      out.write("                    <input name=\"recover-submit\" value=\"Submit\" type=\"submit\">\r\n");
      out.write("                </div>\r\n");
      out.write("            </form>\r\n");
      out.write("        </div>\r\n");
      out.write("    </body>\r\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof jakarta.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}