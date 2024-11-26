package controller.customer;

import codebase.ImageSaveHandler.ImageSaver;
import entity.codebaseOld.User;
import dal.codebase.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "EditProfileController", urlPatterns = {"/EditProfileController"})
public class EditProfileController extends HttpServlet {

    private static final String userAvatarImageDirectoryPath = "asset\\Image\\UserAvatarImage";
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserDAO dao = new UserDAO();
        HttpSession session = request.getSession();

        int id = (int) session.getAttribute("uid");

        User user = dao.getUserByUserID(id);
        
        if (user == null) {
            response.sendRedirect("/freezegroup2/customer/Login.jsp");
            return;
        }

        int userID = user.getId();
        String avatarImage = request.getParameter("avatarBase64");
        String first_name = request.getParameter("firstName");
        String last_name = request.getParameter("lastName");
        String phone_number = request.getParameter("phoneNumber");
        int roleId = 1;
        
        // process image
        String buildPath = getServletContext().getRealPath("");
        String staticPath = buildPath.replace("\\build", "");
        
        buildPath += userAvatarImageDirectoryPath;
        staticPath += userAvatarImageDirectoryPath;
        
        String fileName = "user_" + id + "_avatar";
        
        ImageSaver.saveBase64toImageFile(avatarImage, buildPath, fileName);
        String realImageFilePath = ImageSaver.saveBase64toImageFile(avatarImage, staticPath, fileName);

        // Cập nhật thông tin người dùng
        int update = dao.updatePatial(new User(userID, first_name, last_name, phone_number, realImageFilePath, -1, roleId));

        // Kiểm tra kết quả cập nhật
        if (update > 0) {        
            // Thiết lập thông báo thành công vào phiên
            session.setAttribute("successMessage", "Profile updated successfully.");
            response.sendRedirect("userprofilecontroller"); // Chuyển hướng đến UserProfile
        } else {
            // Nếu cập nhật thất bại, thiết lập thông báo lỗi
            request.setAttribute("errorMessage", "Profile update failed.");
            request.getRequestDispatcher("customer/UserProfile.jsp").forward(request, response);
        }
    }
}
