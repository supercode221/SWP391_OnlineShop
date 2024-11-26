<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Add New Slider</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <link rel="icon" href="${pageContext.request.contextPath}/asset/Image/Favicon/icon2.gif" type="image/gif">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/CSS/header.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/CSS/sidebar.css">
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <style>
            #preview-container {
                display: none; /* Hide the container before an image is selected */
                max-width: 100%; /* Limit the maximum width of the container */
            }

            #preview-img {
                max-width: 100%; /* Ensure the image width does not exceed the container */
                max-height: 280px; /* Limit the image height to avoid overflowing the screen */
                height: auto; /* Maintain the image aspect ratio */
                border-radius: 5px; /* Add slight rounding to the corners */
            }

        </style>
        <script>
            $(document).ready(function () {
                // active sidebar
                var activeSideBar = $("#slider-list");
                activeSideBar.addClass("active-custom");

                // 
            });

            // process sidebar
            const toggleSidebarBtn = document.getElementById('toggleSidebarBtn');
            const sidebar = document.getElementById('sidebar');
            const content = document.getElementById('content');
            const content1 = document.getElementById('content1');
            const navbar = document.getElementById('navbar');

            toggleSidebarBtn.addEventListener('click', () => {
                sidebar.classList.toggle('collapsed');
                content.classList.toggle('collapsed');
                content1.classList.toggle('collapsed');
                navbar.classList.toggle('collapsed');
                toggleSidebarBtn.classList.toggle('collapsed');
            });
        </script>

    </head>
    <body>
        <!-- Header and Sidebar -->
        <div class="content" id="content">
            <jsp:include page="../base-component/header.jsp" />
        </div>
        <jsp:include page="../base-component/sidebar.jsp" />

        <!-- Main Content -->
        <div class="content1" id="content1">
            <div class="card shadow-sm border border-dark">
                <div class="card-header bg-dark text-white">
                    <h2 class="h5 mb-0">Add New Slider</h2>
                </div>
                <div class="card-body flex">
                    <form id="addSliderForm" class="needs-validation" novalidate>
                        <div class="row">
                            <!-- Col 1 -->
                            <div class="col-md-6">
                                <div class="mb-3">
                                    <label for="content" class="form-label">Content</label>
                                    <input type="text" class="form-control" id="contentField" name="content" placeholder="Enter slider content" required>
                                    <div class="invalid-feedback">Content is required.</div>
                                </div>
                                <div class="mb-3">
                                    <label for="status" class="form-label">Status</label>
                                    <select name="status" id="status" class="form-select" required>
                                        <option value="active">Active</option>
                                        <option value="inactive">Inactive</option>
                                    </select>
                                    <div class="invalid-feedback">Status is required.</div>
                                </div>
                                <div class="mb-3">
                                    <label for="backLink" class="form-label">Back Link</label>
                                    <input type="text" class="form-control" id="backLink" name="backLink" placeholder="Enter backlink for the slider" required>
                                    <div class="invalid-feedback">Back Link is required and must be a valid URL.</div>
                                </div>
                            </div>

                            <!-- Col 2 -->
                            <div class="col-md-6">
                                <div class="mb-3">
                                    <label for="link" class="form-label">Thumbnail Image:</label>
                                    <input type="file" class="form-control" id="link" name="link" accept="image/*" required>
                                    <div class="invalid-feedback">Thumbnail Image is required.</div>
                                </div>
                                <div id="preview-container" style="display: none" class="mb-3">
                                    <label class="form-label">Image Preview:</label>
                                    <div class="border p-2 text-center">
                                        <img id="preview-img" src="" alt="Preview Image" style="max-width: 100%; height: auto; border-radius: 5px;" />
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Button container -->
                        <div class="d-flex justify-content-end gap-2 mt-3">
                            <button type="submit" class="btn btn-dark">Add Slider</button>
                            <a href="sliderlist" class="btn btn-outline-dark">Back to Slider List</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>


        <!-- Scripts -->
        <script>

            $(document).ready(function () {
                // Regex for URL validation
                const urlRegex = /^(http?|https?|ftp):\/\/[^\s/$.?#].[^\s]*$/;

                // Image size limit (200MB)
                const MAX_SIZE = 200 * 1024 * 1024; // 200MB in bytes

                // Image preview
                $("#link").change(function () {
                    const file = this.files[0];
                    const reader = new FileReader();

                    // Check file size
                    if (file && file.size > MAX_SIZE) {
                        alert("The image size exceeds the 200MB limit. Please choose a smaller file.");
                        $("#link").val(""); // Reset input
                        $("#preview-container").hide(); // Hide preview
                        return;
                    }

                    if (file) {
                        reader.onload = function (e) {
                            $("#preview-img").attr("src", e.target.result);
                            $("#preview-container").show();
                        };
                        reader.readAsDataURL(file);
                    } else {
                        $("#preview-container").hide();
                    }
                });

                // Form submit event
                $("#addSliderForm").submit(function (event) {
                    event.preventDefault();

                    // Input validation
                    const content = $("#contentField").val().trim();
                    const status = $("#status").val();
                    const backlink = $("#backLink").val().trim();
                    const imageFile = $("#link")[0].files[0];

                    if (!content) {
                        alert("Content is required.");
                        return;
                    }

                    if (!status) {
                        alert("Status is required.");
                        return;
                    }

                    if (!backlink || !urlRegex.test(backlink)) {
                        alert("Backlink is required and must be a valid URL.");
                        return;
                    }

                    if (!imageFile) {
                        alert("Thumbnail Image is required.");
                        return;
                    }

                    // Check file size again at submit
                    if (imageFile.size > MAX_SIZE) {
                        alert("The image size exceeds the 200MB limit. Please choose a smaller file.");
                        return;
                    }

                    // Convert image to Base64
                    const reader = new FileReader();
                    reader.onload = function (e) {
                        const base64Image = e.target.result;

                        // Prepare data
                        const formData = {
                            content: content,
                            status: status,
                            backlink: backlink,
                            image: base64Image,
                        };

                        // Send data using AJAX
                        $.ajax({
                            url: "addslider",
                            type: "POST",
                            contentType: "application/json",
                            data: JSON.stringify(formData),
                            success: function (response) {
                                alert("Slider added successfully!");
                                window.location.href = "sliderlist";
                            },
                            error: function (xhr, status, error) {
                                alert("An error occurred: " + xhr.responseText);
                            },
                        });
                    };

                    reader.readAsDataURL(imageFile); // Convert file to Base64
                });
            });

            // Preview Thumbnail Image
            $("#link").change(function () {
                const file = this.files[0];
                if (file) {
                    const reader = new FileReader();
                    reader.onload = function (e) {
                        $("#preview-img").attr("src", e.target.result);
                        $("#preview-container").show();
                    };
                    reader.readAsDataURL(file);
                } else {
                    $("#preview-container").hide();
                }
            });


            // Bootstrap Form Validation
            (() => {
                'use strict';
                const forms = document.querySelectorAll('.needs-validation');
                Array.from(forms).forEach(form => {
                    form.addEventListener('submit', event => {
                        if (!form.checkValidity()) {
                            event.preventDefault();
                            event.stopPropagation();
                        }
                        form.classList.add('was-validated');
                    }, false);
                });
            })();

        </script>
    </body>
</html>
