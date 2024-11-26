<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin</title>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <link rel="icon" href="${pageContext.request.contextPath}/asset/Image/Favicon/icon2.gif" type="image/gif">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/CSS/footer.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/CSS/header.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/CSS/sidebar.css"/>
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <style>
            * a{
                text-decoration: none;
                color: black;
            }

            h1, h3, h4, h4, h5, h6, p {
                padding: 0;
                margin: 0;
            }

            body {
                background-color: #ffffff;
                font-family: Arial, sans-serif;
            }

            .in-active {
                display: none;
            }

            .success-popup {
                position: fixed;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
                background-color: black; /* Success green color */
                color: white;
                padding: 20px;
                border-radius: 10px;
                display: none; /* Initially hidden */
                text-align: center;
                z-index: 1003;
            }

            .tick-animation {
                font-size: 30px;
                animation: tick-pop 0.5s ease-in-out;
                display: block;
                margin-bottom: 10px;
                color: white;
            }

            @keyframes tick-pop {
                0% {
                    transform: scale(0);
                }
                50% {
                    transform: scale(1.3);
                }
                100% {
                    transform: scale(1);
                }
            }

            .image-upload-wrapper {
                position: relative;
                width: 100px;
                height: 100px;
                border: 2px dashed #ccc;
                display: flex;
                align-items: center;
                justify-content: center;
                cursor: pointer;
                border-radius: 5px;
                transition: border 0.3s ease;
            }

            .image-upload-wrapper:hover {
                border-color: #999;
            }

            .image-upload-wrapper:before {
                content: "+";
                font-size: 35px;
                color: #aaa;
            }

            /* Hide the actual input field */
            .image-upload-main {
                position: absolute;
                width: 100%;
                height: 100%;
                opacity: 0;
                cursor: pointer;
            }

            /* Style the image inside the upload box */
            .uploaded-image {
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                object-fit: cover;
            }

            .blogdetail-overall-information {
                display: flex;
                gap: 30px;
                justify-content: center;
            }

            select {
                border: solid 1px #ccc;
                padding: 5px 10px;
                border-radius: 10px;
            }

            .side-information {
                display: flex;
                justify-content: space-between;
            }

            .side-information p {
                color: #999999;
            }

            .blogdetail-main {
                max-width: 800px;
                margin: 20px auto; /* Center the blog */
                padding: 20px;
                background-color: #f9f9f9;
                border-radius: 10px;
                box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
            }

            .post-main-title textarea,
            .post-main-content textarea,
            .post-subtitle textarea,
            .post-subcontent textarea {
                width: 100%;
                border: none;
                background: #fff;
                border-radius: 5px;
                padding: 10px;
                font-size: 16px;
                box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.1);
                resize: none;
            }

            .post-main-title textarea {
                font-size: 24px;
                font-weight: bold;
                color: #333;
                line-height: 1.5;
                margin-bottom: 10px;
            }

            .post-main-content textarea,
            .post-subcontent textarea {
                font-size: 18px;
                color: #555;
                line-height: 1.7;
                margin-top: 20px;
            }

            .post-thumbnail img,
            .post-subImage img {
                margin-top: 10px;
                border-radius: 5px;
                box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
            }

            .side-information {
                justify-content: space-between;
                font-size: 14px;
                color: #888;
                margin-bottom: 20px;
                border-bottom: 1px solid #ddd;
                padding-bottom: 10px;
            }

            .category, .status {
                margin: 10px 0;
                font-size: 16px;
            }

            .blogdetail-overall-information label {
                font-weight: bold;
                color: #444;
            }

            .blogdetail-overall-information select {
                font-size: 14px;
                margin-left: 10px;
                color: #444;
            }

            .main-information {
                margin-top: 30px;
            }

            .success-popup {
                font-family: "Arial", sans-serif;
                font-size: 18px;
                background-color: #28a745; /* Success green */
                color: #fff;
                padding: 15px 25px;
                border-radius: 5px;
                text-align: center;
                animation: fade-in 0.5s ease;
            }

            @keyframes fade-in {
                from {
                    opacity: 0;
                }
                to {
                    opacity: 1;
                }
            }

            .post-thumbnail img:hover,
            .post-subImage img:hover {
                transform: scale(1.05);
                transition: transform 0.3s ease;
            }

            label {
                margin-right: 20px;
            }

            #saveButton {
                padding: 10px 20px;
                font-size: 16px;
                border: none;
                border-radius: 5px;
                background-color: #007bff;
                color: white;
                box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
                cursor: pointer;
            }

            #saveButton:hover {
                background-color: #0056b3;
            }

            .image-upload {
                width: 150px;
                height: 150px;
                border: 2px dashed #ccc;
                display: flex;
                align-items: center;
                justify-content: center;
                cursor: pointer;
                position: relative;
                overflow: hidden;
            }

            .image-upload::after {
                content: "+";
                font-size: 24px;
                color: #aaa;
                display: block;
                position: absolute;
            }

            .preview-image {
                width: 100%;
                /*height: 100%;*/
                display: block;
            }

            .success-popup {
                position: fixed;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
                background-color: black; /* Success green color */
                color: white;
                padding: 20px;
                border-radius: 10px;
                display: none; /* Initially hidden */
                text-align: center;
                z-index: 1003;
            }

            .tick-animation {
                font-size: 30px;
                animation: tick-pop 0.5s ease-in-out;
                display: block;
                margin-bottom: 10px;
                color: white;
            }

            @keyframes tick-pop {
                0% {
                    transform: scale(0);
                }
                50% {
                    transform: scale(1.3);
                }
                100% {
                    transform: scale(1);
                }
            }
        </style>
    </head>

    <body>
        <!-- Header import section -->
        <div class="content" id="content">
            <jsp:include page="../base-component/header.jsp" />
        </div>

        <jsp:include page="../base-component/sidebar.jsp" />
        <div class="content1" id="content1">
            <a href="javascript:void(0);" onclick="history.back()"><i class="fa-solid fa-arrow-left fa-xl"></i></a>
            <div class="blogdetail-overall-information">
                <div class="category">
                    <label for="cate"><b>Category: </b></label>
                    <select id="cate">
                        <c:forEach items="${blogCategoryList}" var="cate">
                            <option data-cate-id="${cate.id}">${cate.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="status">
                    <label for="status"><b>Status: </b></label>
                    <select id="status">
                        <c:forEach items="${statusList}" var="sta">
                            <option>${sta}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="blogdetail-main">
                <div class="main-information">
                    <div class="post-main-title">
                        <label for="maintitle"><b>Title: </b></label>
                        <textarea id="maintitle" name="mainTitle"></textarea>
                    </div>
                    <div class="post-thumbnail mb-3">
                        <label for="postThumbnailImg"><b>Thumbnail: </b></label>
                        <img class="image-upload" id="postThumbnailImg" src="#" width="300" style="cursor: pointer;">
                        <input name="thumnbail" type="file" id="postThumbnailImgInput" accept="image/*" style="display: none;">
                    </div>
                    <div class="post-main-content mb-3">
                        <label for="maincontent"><b>Main Content: </b></label>
                        <textarea id="maincontent" type="text" name="mainContent"></textarea>
                    </div>
                    <div class="post-subtitle mb-3">
                        <label for="subtitle1"><b>Sub-Title 1: </b></label>
                        <textarea id="subtitle1" type="text" name="subTitle1"></textarea>
                    </div>
                    <div class="post-subImage mb-3">
                        <label><b>Sub-Image 1: </b></label>
                        <input id="subImage1PreviewInput" name="subimage1" type="file" accept="image/*" style="display: none;">
                        <img class="image-upload" id="subImage1Preview" class="preview-image" src="#" width="300">
                    </div>
                    <div class="post-main-content mb-3">
                        <label for="content1"><b>Content 1: </b></label>
                        <textarea id="content11" type="text" name="contentsubTitle1"></textarea>
                    </div>
                    <div class="post-subtitle mb-3">
                        <label for="subtitle2"><b>Sub-Title 2: </b></label>
                        <textarea id="subtitle2" type="text" name="subTitle2"></textarea>
                    </div>
                    <div class="post-subImage mb-3">
                        <label><b>Sub-Image 2: </b></label>
                        <input id="subImage2PreviewInput" name="subimage2" type="file" accept="image/*" style="display: none;">
                        <img class="image-upload" id="subImage2Preview" class="preview-image" src="#" width="300">
                    </div>
                    <div class="post-main-content mb-3">
                        <label for="content2"><b>Content 2: </b></label>
                        <textarea id="content2" type="text" name="contentsubTitle2"></textarea>
                    </div>
                    <div class="post-subtitle mb-3">
                        <label for="subtitle3"><b>Sub-Title 3: </b></label>
                        <textarea id="subtitle3" type="text" name="subTitle3"></textarea>
                    </div>
                    <div class="post-subImage mb-3">
                        <label><b>Sub-Image 3: </b></label>
                        <input id="subImage3PreviewInput" name="subimage3" type="file" accept="image/*" style="display: none;">
                        <img class="image-upload" id="subImage3Preview" class="preview-image" src="#" width="300">
                    </div>
                    <div class="post-main-content mb-3">
                        <label for="content3"><b>Content 3: </b></label>
                        <textarea id="content3" type="text" name="contentsubTitle3"></textarea>
                    </div>
                </div>
            </div>

            <div id="success-popup-status" class="success-popup">
                <span class="tick-animation">✔</span>
                <p>Change status successfully</p>
            </div>

            <div id="success-popup-category" class="success-popup">
                <span class="tick-animation">✔</span>
                <p>Change status successfully</p>
            </div>

            <div id="success-popup-save" class="success-popup">
                <span class="tick-animation">✔</span>
                <p>Save information successfully</p>
            </div>
        </div>

        <script>
            $(document).ready(function () {
                const toggleSidebarBtn = document.getElementById('toggleSidebarBtn');
                const sidebar = document.getElementById('sidebar');
                const content = document.getElementById('content');
                const content1 = document.getElementById('content1');
                const navbar = document.getElementById('navbar');

                toggleSidebarBtn.addEventListener('click', () => {
                    sidebar.classList.toggle('collapsed');
                    content.classList.toggle('collapsed');
                    content1.classList.toggle('collapsed');
                    toggleSidebarBtn.classList.toggle('collapsed');
                });

                var activeSideBar = $("#blog-list");
                activeSideBar.addClass("active-custom");
            });

            $(document).ready(function () {
                const saveButton = $('<button id="saveButton" class="btn btn-primary" style="display: none; position: fixed; bottom: 20px; right: 20px; z-index: 1000;">Add Post</button>');
                $('body').append(saveButton);

                // Show Save Button
                function showSaveButton() {
                    saveButton.fadeIn();
                }

                // Hide Save Button
                function hideSaveButton() {
                    saveButton.fadeOut();
                }

                // Function to handle image uploads
                function handleImageUpload(imgElement, inputElement) {
                    imgElement.addEventListener('click', () => {
                        inputElement.click();
                    });

                    inputElement.addEventListener('change', function () {
                        const file = this.files[0];
                        if (file) {
                            if (file.size > 5 * 1024 * 1024) {
                                alert('File size exceeds 5MB');
                                this.value = '';
                                return;
                            }

                            if (!file.type.startsWith('image/')) {
                                alert('Only image files are allowed!');
                                this.value = '';
                                return;
                            }

                            const reader = new FileReader();
                            reader.onload = function (e) {
                                imgElement.src = e.target.result;
                                showSaveButton(); // Show Save Button when image changes
                            };
                            reader.readAsDataURL(file);
                        }
                    });
                }

                // Initialize image upload handling
                document.querySelectorAll('img').forEach((img) => {
                    const inputId = img.id + "Input";
                    const inputElement = document.getElementById(inputId);

                    if (inputElement) {
                        console.log(img.id);
                        handleImageUpload(img, inputElement);
                    }
                });

                // Track changes in text fields and textareas
                $('textarea, select, input[type="text"]').on('input change', function () {
                    showSaveButton(); // Show Save Button when any input changes
                });

                // Save Button Click Event
                saveButton.on('click', function (e) {

                    // Proceed with form submission via AJAX if validation passes
                    let mainTitle = $('#maintitle').val();
                    let thumbnail = $('#postThumbnailImg').attr('src');
                    let mainContent = $('#maincontent').val();
                    let subTitle1 = $('#subtitle1').val();
                    let subImage1;
                    let content11 = $('#content11').val();
                    let subTitle2 = $('#subtitle2').val();
                    let subImage2;
                    let content2 = $('#content2').val();
                    let subTitle3 = $('#subtitle3').val();
                    let subImage3;
                    let content3 = $('#content3').val();

                    // Assign image sources to variables
                    $('img').each(function () {
                        if ($(this).attr('src') !== '#') {
                            if ($(this).attr('id') === 'subImage1Preview') {
                                subImage1 = $(this).attr('src');
                            } else if ($(this).attr('id') === 'subImage2Preview') {
                                subImage2 = $(this).attr('src');
                            } else if ($(this).attr('id') === 'subImage3Preview') {
                                subImage3 = $(this).attr('src');
                            }
                        }
                    });

                    if (!mainTitle.trim() || !thumbnail || !mainContent) {
                        alert("Main section must not be empty");
                        return;
                    } else if (subTitle1.trim() && !content11.trim()) {
                        alert("Content for Sub-Title 1 must not be empty");
                        return;
                    } else if ((subImage1 && !content11.trim()) || (subImage1 && !subTitle1.trim())) {
                        alert("Sub-Title 1 information missing!");
                        return;
                    } else if (subTitle1.trim() && content11.trim() && !subImage1) {
                        alert("Sub-Image 1 missing!");
                        return;
                    } else if (subTitle2.trim() && !content2.trim()) {
                        alert("Content for Sub-Title 2 must not be empty");
                        return;
                    } else if ((subImage2 && !content2.trim()) || (subImage2 && !subTitle2.trim())) {
                        alert("Sub-Title 2 information missing!");
                        return;
                    } else if (subTitle3.trim() && !content3.trim()) {
                        alert("Content for Sub-Title 3 must not be empty");
                        return;
                    } else if ((subImage3 && !content3.trim()) || (subImage3 && !subTitle3.trim())) {
                        alert("Sub-Title 3 information missing!");
                        return;
                    } else if (subTitle2.trim() && !subTitle1.trim()) {
                        alert("Please input sub-information 1 first!");
                        return;
                    } else if (subTitle3.trim() && !subTitle1.trim()) {
                        alert("Please input sub-information 1 first!");
                        return;
                    } else if (subTitle3.trim() && !subTitle2.trim()) {
                        alert("Please input sub-information 2!");
                        return;
                    } else if (subTitle2.trim() && content2.trim() && !subImage2) {
                        alert("Sub-Image 2 missing!");
                        return;
                    } else if (subTitle3.trim() && content3.trim() && !subImage3) {
                        alert("Sub-Image 3 missing!");
                        return;
                    }

                    var status = $('#status').val();
                    var cateId = $('#cate').find(':selected').attr('data-cate-id');

                    // Log all collected data
                    console.log("cate: " + cateId);
                    console.log("status " + status);
                    console.log("Main Title: " + mainTitle);
                    console.log("Thumbnail: " + thumbnail);
                    console.log("Main Content: " + mainContent);
                    console.log("SubTitle1: " + subTitle1);
                    console.log("SubImage1: " + subImage1);
                    console.log("Content1: " + content11);
                    console.log("SubTitle2: " + subTitle2);
                    console.log("SubImage2: " + subImage2);
                    console.log("Content2: " + content2);
                    console.log("SubTitle3: " + subTitle3);
                    console.log("SubImage3: " + subImage3);
                    console.log("Content3: " + content3);

//                     AJAX call to submit data
                    $.ajax({
                        url: '${pageContext.request.contextPath}/addnewpost',
                        type: 'post',
                        contentType: 'application/json; charset=utf-8',
                        dataType: 'json',
                        data: JSON.stringify({
                            mainTitle: mainTitle,
                            thumbnail: thumbnail,
                            mainContent: mainContent,
                            subTitle1: subTitle1,
                            subImage1: subImage1,
                            content11: content11,
                            subTitle2: subTitle2,
                            subImage2: subImage2,
                            content2: content2,
                            subTitle3: subTitle3,
                            subImage3: subImage3,
                            content3: content3,
                            status: status,
                            cate: cateId
                        }),
                        success: function (response) {
                            $('#success-popup-save').fadeIn();
                            setTimeout(function () {
                                $('#success-popup-save').fadeOut();
                            }, 800);

                            hideSaveButton();

                            location.href = 'blogdetailmanager?postId=${blogId}';
                        },
                        error: function (xhr, status, error) {
                            console.error("Error occurred: " + status + " - " + error);
                            $('#success-popup-save').fadeIn();
                            setTimeout(function () {
                                $('#success-popup-save').fadeOut();
                            }, 800);

                            hideSaveButton();

                            setTimeout(function () {
                                location.href = 'blogdetailmanager?postId=${blogId}';
                            }, 2000);
                        }
                    });
                });
            });
        </script>
    </body>
</html>

</body>
</html>
