package controller.marketing;

import codebase.ImageSaveHandler.ImageSaver;
import codebase.random.Random;
import dal.codebase.sliderDAO;
import entity.codebaseOld.Slider;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import org.json.JSONObject;

/**
 *
 * @author Admin
 */

@WebServlet(name = "AddSlider", urlPatterns = {"/addslider"})
public class AddSliderManagerController extends HttpServlet {
    
    private final String sliderThumbnailImageDirectoryPath = "asset\\Image\\SliderThumbnailImage";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.getRequestDispatcher("/marketing/AddSlider.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Read JSON data
        StringBuilder jsonBuffer = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                jsonBuffer.append(line);
            }
        }
        
        // Parse JSON
        JSONObject jsonData = new JSONObject(jsonBuffer.toString());
        
        // process image
        String image = jsonData.getString("image");
        
        String buildPath = getServletContext().getRealPath("");
        String staticPath = buildPath.replace("\\build", "");
        
        buildPath += sliderThumbnailImageDirectoryPath;
        staticPath += sliderThumbnailImageDirectoryPath;
        
        String randomidfile = new Random().getString(16, null);
        String fileName = "thumbnail_" + randomidfile + "_image";
        
        ImageSaver.saveBase64toImageFile(image, buildPath, fileName);
        String realImageFilePath = ImageSaver.saveBase64toImageFile(image, staticPath, fileName);

        Slider slider = new Slider();
        slider.setContent(jsonData.getString("content"));
        slider.setBackLink(jsonData.getString("backlink"));
        slider.setStatus(jsonData.getString("status"));
        slider.setLink(realImageFilePath);
        
        // Save to database (pseudo-code)
        boolean isSaved = new sliderDAO().addSlider(slider); // Implement DAO logic

        response.setContentType("application/json");
        if (isSaved) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"message\": \"Slider added successfully.\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\": \"Failed to add slider.\"}");
        }
    }
}
