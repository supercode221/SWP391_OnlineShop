<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add New Product</title>
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

            .divide-horizontal {
                background-color: #ccc;
                height: 2px;
                width: auto;
                margin-bottom: 30px;
            }

            .divide-vertical {
                background-color: #ccc;
                height: auto;
                width: 2px;
            }

            .header-section {
                display: flex;
                align-items: center;
                justify-content: center;
                gap: 20px;
                margin-bottom: 15px;
                position: sticky;
                top: 10px;
                z-index: 1010;
                background-color: white;
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0 0 10px #ccc;
            }

            /* General Styling */
            .product-detail-section {
                display: flex;
                gap: 20px;
                padding: 30px;
                /*background: #f9f9f9;*/
                box-shadow: inset 0 0 10px #ccc;
                border: 1px solid #ddd;
                border-radius: 8px;
            }

            /* Left Section - Overall Information */
            .overall-informations {
                width: 50%;
                display: flex;
                flex-direction: column;
                gap: 20px;
                padding: 10px;
            }

            .overall-information-text textarea,
            .overall-information-text input,
            .overall-information-text select {
                width: 100%;
                padding: 5px;
                margin-top: 5px;
                border: solid 1px #ccc;
                padding: 10px;
            }

            /* Right Section - Detailed Attributes */
            .detail-attributes {
                width: 50%;
                display: flex;
                flex-direction: column;
                gap: 20px;
                padding: 10px;
            }

            .tag-badge-exist, .tag-badge-addable {
                display: flex;
                gap: 10px;
            }

            table {
                width: 100%;
                border-collapse: collapse;
            }

            table th, table td {
                padding: 10px;
                border: 1px solid #ddd;
                text-align: center;
            }

            .add-attributes button, .add-sub-image button , .save-overall-information button{
                margin-top: 10px;
                padding: 5px 15px;
                background-color: black;
                color: white;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                font-size: 17px;
                transition: ease-in-out 0.3s;
            }

            .add-attributes button:hover , .add-sub-image button:hover, .save-overall-information button:hover{
                box-shadow: inset 0 0 0 1px black;
                background-color: white;
                color: black;
                transform: scale(1.1);
            }

            /* Divider */
            .divide-vertical {
                width: 2px;
                background: #ccc;
                margin: 0 10px;
            }

            /* Styling for the overall container */
            .product-subimage-wrapper {
                width: 100%;
                display: flex;
                flex-direction: column;
                gap: 10px;
            }

            /* Grid container for subimages */
            .subimage-container {
                display: grid;
                grid-template-columns: repeat(5, 1fr); /* 5 columns per row */
                gap: 10px;
                max-height: calc(100px * 4 + 30px); /* Height of 4 rows including gaps */
                overflow-y: auto; /* Allow vertical scrolling for >4 rows */
                padding: 5px;
                border: 1px solid #ddd;
                border-radius: 8px;
                background-color: #f9f9f9;
            }

            /* Individual subimage item */
            .subimage-grid {
                position: relative; /* To position the remove button */
                display: flex;
                flex-direction: column;
                align-items: center;
                gap: 5px;
                border: 1px solid #ccc;
                border-radius: 5px;
                padding: 10px;
                background-color: #fff;
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            }

            .subimage-grid img {
                width: 100px;
                height: 100px;
                object-fit: cover;
                border-radius: 5px;
                border: 1px solid #ddd;
            }

            .subimage-grid input {
                width: 100%;
                font-size: 12px;
            }

            /* Styling for the remove (X) button */
            .remove-button {
                position: absolute;
                top: 5px;
                right: 5px;
                background: #ff5c5c;
                color: white;
                border: none;
                border-radius: 50%;
                width: 20px;
                height: 20px;
                font-size: 14px;
                line-height: 18px;
                cursor: pointer;
                display: flex;
                justify-content: center;
                align-items: center;
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
                transition: transform 0.2s ease-in-out;
            }

            .remove-button:hover {
                transform: scale(1.1);
                background: #ff2d2d;
            }

            .save-attr{
                border: none;
                background-color: #00c070;
                color: white;
                border-radius: 10px;
                padding: 3px 10px;
                transition: ease-in-out 0.2s;
            }

            .save-attr:hover {
                box-shadow: inset 0 0 0 1px #00c070;
                background: white;
                color: #00c070;
            }

            .remove-attr{
                border: none;
                background-color: red;
                color: white;
                border-radius: 10px;
                padding: 3px 10px;
                transition: ease-in-out 0.2s;
            }

            .remove-attr:hover {
                box-shadow: inset 0 0 0 1px red;
                background: white;
                color: red;
            }

            /* General styling for the table */
            .attributes-table {
                width: 100%;
                border-collapse: collapse;
                font-family: Arial, sans-serif;
                font-size: 14px;
                text-align: left;
                margin-top: 10px;
                background-color: white;
                border-radius: 8px;
                overflow: hidden;
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            }

            /* Table headers */
            .attributes-table thead tr {
                background-color: black;
                color: white;
            }

            .attributes-table th {
                padding: 10px;
                font-weight: bold;
                border-bottom: 2px solid #ddd;
                font-size: 19px;
            }

            /* Table body rows */
            .attributes-table td {
                padding: 10px;
                border-bottom: 1px solid #ddd;
                vertical-align: middle;
                font-size: 17px;
            }

            /* Color display box */
            .color-display {
                width: 20px;
                height: 20px;
                border-radius: 4px;
                display: inline-block;
            }

            .badge {
                display: flex; /* Enable flexbox layout */
                align-items: center; /* Center align items vertically */
                justify-content: space-between; /* Spread text and button to opposite ends */
                padding: 5px 10px;
                border-radius: 4px;
                margin-right: 5px;
                color: #fff;
                font-size: 14px;
                position: relative;
            }

            .remove-tag-btn {
                border: none;
                background: none;
                color: #fff;
                font-size: 15px;
                cursor: pointer;
                padding: 0;
                margin-left: 10px; /* Add spacing between text and button */
                line-height: 1;
            }

            .add-tag-btn {
                border: none;
                background: none;
                color: #fff;
                font-size: 15px;
                cursor: pointer;
                padding: 0;
                margin-left: 10px; /* Add spacing between text and button */
                line-height: 1;
            }

            .thumbnail-box img {
                margin-top: 10px;
                border-radius: 5px;
                box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
                transform: scale(1);
                transition: transform 0.3s ease;
            }

            .thumbnail-box img:hover{
                transform: scale(1.05);
            }

            .image-upload {
                width: 300px;
                height: 300px;
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
        </style>
    </head>

    <body>
        <!-- Header import section -->
        <div class="content" id="content">
            <jsp:include page="../base-component/header.jsp" />
        </div>

        <jsp:include page="../base-component/sidebar.jsp" />
        <div class="content1" id="content1">
            <div class="header-section">
                <a href="javascript:void(0);" onclick="history.back()"><i class="fa-solid fa-arrow-left fa-xl"></i></a>
                <div class="product-name">
                    <h4>Add new product</h4>
                </div>
            </div>

            <div class="divide-horizontal"></div>

            <div class="product-detail-section" id="product-section">
                <!-- Overall Information -->
                <div class="overall-informations">
                    <h3 class="text-center"><b>Overall Information</b></h3>
                    <div class="overall-information-text">
                        <h5 class="mb-3"><b>Up-Coming ID:</b> ${pid}</h5>
                        <h5 class="mb-3"><b>Name:</b> <input id="product-name" type="text" value="" oninput="displaySaveBtn()"></h5>
                        <h5 class="mb-3"><b>Description:</b>
                            <textarea id="product-des" oninput="displaySaveBtn()"></textarea>
                        </h5>
                        <h5 class="mb-3"><b>Price:</b> <input 
                                id="product-price" 
                                type="text" 
                                value="<fmt:formatNumber value='' pattern='###,###,###' />" 
                                oninput="formatInput(this)" 
                                />
                        </h5>
                        <h5 class="mb-3"><b>Category:</b>
                            <select id="cate" onchange="displaySaveBtn()">
                                <c:forEach items="${cateList}" var="c">
                                    <option data-cate-id="${c.id}">${c.name}</option>
                                </c:forEach>
                            </select>
                        </h5>
                        <h5 class="mb-3"><b>Status:</b>
                            <select id="status" onchange="displaySaveBtn()" disabled="">
                                <option>Hidden</option>
                            </select>
                        </h5>
                    </div>
                    <div class="overall-information-image">
                        <div class="product-thumbnail mb-5">
                            <h5 class="mb-3"><b>Thumbnail</b></h5>
                            <div class="thumbnail-box text-center">
                                <input id="thumbnailMainInput" type="file" accept="image/*" style="display: none;">
                                <img class="image-upload" id="thumbnailMain" width="300">
                            </div>
                        </div>
                        <div class="save-overall-information text-end" style="display: none;">
                            <button onclick="saveChanges()">Save</button>
                        </div>
                        <div class="product-subimage-wrapper">
                            <h5><b>Sub-Image</b></h5>
                            <div class="subimage-container" id="subimage-container">

                            </div>
                            <div class="add-sub-image text-end">
                                <input id="addSubImage" type="file" accept="image/*" style="display: none;">
                                <button onclick="addImage()">Add Sub Image</button>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Divider Line -->
                <div class="divide-vertical"></div>

                <!-- Detailed Attributes -->
                <div class="detail-attributes">
                    <h3 class="text-center"><b>Detailed Attributes</b></h3>
                    <div class="tag-section">
                        <h4 class="mb-3"><b>Tags:</b></h4>
                        <div class="tag-wrapper" style="margin-left: 30px;">
                            <h5 class="mb-3"><b>Exist</b></h5>
                            <div class="tag-badge-exist mb-3" id="tag-exist">

                            </div>

                            <h5 class="mb-3"><b>Addable</b></h5>
                            <div class="tag-badge-addable">
                                <c:forEach items="${tagList}" var="tagAddable">
                                    <span class="badge" style="background-color: ${tagAddable.colorCode};">
                                        ${tagAddable.name}
                                        <button <c:if test="${tagAddable.name == 'NewArrivals'}">id="default"</c:if> type="button" class="add-tag-btn" onclick="addTag(this, ${tagAddable.id})"><b>+</b></button>
                                        </span>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                    <div class="attributes-section">
                        <h3 class="mb-3"><b>Attributes</b></h3>
                        <table class="attributes-table">
                            <thead>
                                <tr>
                                    <th>Color</th>
                                    <th>Size</th>
                                    <th>Quantity</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>

                            </tbody>
                        </table>
                        <div class="add-attributes text-end">
                            <button onclick="showAddSubAttrModal()">Add</button>
                        </div>
                        <div class="add-subattr-modal" style="display: none;" id="add-attr-modal">
                            <div class="modal-content">
                                <div class="modal-body">
                                    <form id="addSubAttrForm" onsubmit="addSubAttr(event)">
                                        <div class="form-group mb-3">
                                            <label for="color" class="form-label"><b>Color: </b></label>
                                            <select id="color" class="form-select" aria-label="Default select example" onchange="updatePreviewColor(this)">
                                                <option selected>Choose color</option>
                                                <c:forEach items="${colorList}" var="color">
                                                    <option value="${color.colorCode}" data-id="${color.id}" data-name="${color.name}">
                                                        ${color.name}
                                                    </option>
                                                </c:forEach>
                                            </select>
                                            <div class="color-preview-add mt-3" style="width: auto; height: 20px; border-radius: 10px;"></div>
                                        </div>
                                        <div class="form-group mb-3">
                                            <label for="size" class="form-label"><b>Size: </b></label>
                                            <select id="size" class="form-select" aria-label="Default select example" onselect="updatePreviewColor(this)">
                                                <option selected>Choose size</option>
                                                <c:forEach items="${sizeList}" var="size">
                                                    <option value="${size.id}">${size.size}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label for="quantity" class="form-label"><b>Quantity: </b></label>
                                            <input class="form-control" type="number" id="quantity" name="quantity" min="1" required>
                                        </div>
                                        <div class="modal-footer text-end gap-3 mt-3">
                                            <button type="submit">Submit</button>
                                            <button type="button" onclick="closeAddSubAttrModal()">Cancel</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div id="success-popup-attribute" class="success-popup">
                <span class="tick-animation">✔</span>
                <p>Save attribute successfully</p>
            </div>

            <div id="delete-popup-attribute" class="success-popup">
                <span class="tick-animation">✔</span>
                <p>Delete attribute successfully</p>
            </div>

            <div id="success-popup-tag" class="success-popup">
                <span class="tick-animation">✔</span>
                <p>Add tag successfully</p>
            </div>

            <div id="delete-popup-tag" class="success-popup">
                <span class="tick-animation">✔</span>
                <p>Delete tag successfully</p>
            </div>

            <div id="success-popup-save" class="success-popup">
                <span class="tick-animation">✔</span>
                <p>Save information successfully</p>
            </div>

            <div id="success-popup-thumbnail" class="success-popup">
                <span class="tick-animation">✔</span>
                <p>Save thumbnail successfully</p>
            </div>

            <div id="success-popup-subImage" class="success-popup">
                <span class="tick-animation">✔</span>
                <p>Save Sub-Image successfully</p>
            </div>

            <div id="add-popup-subImage" class="success-popup">
                <span class="tick-animation">✔</span>
                <p>Add Sub-Image successfully</p>
            </div>

            <div id="add-popup-attr" class="success-popup">
                <span class="tick-animation">✔</span>
                <p>Add Attribute successfully</p>
            </div>
        </div>

        <script>
            let saveCount = 0;
            let addTagCount = 0; // Your condition

            // Prevent reload shortcuts
            document.addEventListener('keydown', (event) => {
                if (addTagCount === 0 && saveCount !== 0) {
                    if (event.key === 'F5' || (event.ctrlKey && event.key === 'r') || (event.metaKey && event.key === 'r')) {
                        event.preventDefault();
                        alert('You cannot reload the page until you add a tag.');
                    }
                }
            });

            document.addEventListener('contextmenu', (event) => {
                if (addTagCount === 0 && saveCount !== 0) {
                    event.preventDefault();
                    alert('You cannot use the context menu until you add a tag.');
                }
            });

            function confirmLeave() {
                return confirm('CAUTION! Add product without a tag will cause something bad. Are you sure you want to proceed?');
            }

            window.addEventListener('beforeunload', (event) => {
                if (addTagCount === 0 && saveCount !== 0) {
                    if (!confirmLeave()) {
                        event.preventDefault();
                        event.returnValue = ''; // Necessary for older browsers
                    }
                }
            });

            document.addEventListener('click', (event) => {
                if (addTagCount === 0 && event.target.tagName === 'A' && saveCount !== 0) {
                    event.preventDefault();
                    alert('You cannot leave this page until you add a tag.');
                }
            });


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
                var activeSideBar = $("#product-list");
                activeSideBar.addClass("active-custom");

//                function handleImageUpload(imgElement, inputElement) {
//                    imgElement.addEventListener('click', () => {
//                        console.log("clicked");
//                        inputElement.click();
//                    });
//
//                    let subImage;
//
//                    inputElement.addEventListener('change', function () {
//                        const file = this.files[0];
//                        if (file) {
//                            if (file.size > 1024 * 1024) {
//                                alert('File size exceeds 1MB');
//                                this.value = '';
//                                return;
//                            }
//
//                            if (!file.type.startsWith('image/')) {
//                                alert('Only image files are allowed!');
//                                this.value = '';
//                                return;
//                            }
//
//                            const reader = new FileReader();
//                            reader.onload = function (e) {
//                                imgElement.src = e.target.result;
//                                subImage = e.target.result;
//                                const subImageId = imgElement.id.replace("subImage", "");
//
//                                $.ajax({
//                                    url: '${pageContext.request.contextPath}/productdetailmanager',
//                                    type: 'POST',
//                                    data: {
//                                        action: "saveSubImage",
//                                        subImageId: subImageId,
//                                        subImage: subImage,
//                                        pid: ${product.productId}
//                                    },
//                                    success: function (response) {
//                                        $('#success-popup-subImage').fadeIn();
//                                        setTimeout(function () {
//                                            $('#success-popup-subImage').fadeOut();
//                                        }, 800);
//                                    }
//                                });
//                            };
//                            reader.readAsDataURL(file);
//                        }
//                    });
//                }
//
//                document.querySelectorAll("img").forEach(img => {
//                    const inputId = img.id + "Input";
//                    const inputElement = document.getElementById(inputId);
//
//                    if (inputElement) {
//                        handleImageUpload(img, inputElement);
//                    }
//                });
//
                function handleImageUploadThumbnail(imgElement, inputElement) {
                    imgElement.addEventListener('click', () => {
                        displaySaveBtn();
                        console.log("clicked");
                        inputElement.click();
                    });

                    let thumbnail;

                    inputElement.addEventListener('change', function () {
                        const file = this.files[0];
                        if (file) {
                            if (file.size > 1024 * 1024) {
                                alert('File size exceeds 1MB');
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
                                thumbnail = e.target.result;
                            };
                            reader.readAsDataURL(file);
                        }
                    });
                }


                handleImageUploadThumbnail(
                        document.getElementById("thumbnailMain"),
                        document.getElementById("thumbnailMainInput")
                        );

            });

            function updatePreviewColor(select) {
                // Get the selected option
                const selectedOption = select.options[select.selectedIndex];

                // Get the color code from the value of the option
                const colorCode = selectedOption.value;

                // Find the preview element
                const previewElement = document.querySelector('.color-preview-add');

                // Update the preview element's background color
                if (colorCode && colorCode !== 'Choose color') {
                    previewElement.style.backgroundColor = colorCode;
                    previewElement.style.border = '1px solid #ddd';
                } else {
                    // Reset preview if no color is selected
                    previewElement.style.backgroundColor = 'transparent';
                    previewElement.style.border = 'none';
                }
            }
//
            function showAddSubAttrModal() {
                if (saveCount === 0) {
                    alert('Please add product first!');
                    return;
                }

                document.getElementById("add-attr-modal").style.display = "block";
            }

            function closeAddSubAttrModal() {
                document.getElementById("add-attr-modal").style.display = "none";
            }
//
            function addSubAttr(event) {
                event.preventDefault();

                // Gather form data
                const colorSelect = document.getElementById("color");
                const selectedColorOption = colorSelect.options[colorSelect.selectedIndex];
                const colorName = selectedColorOption.getAttribute("data-name");
                const colorId = selectedColorOption.getAttribute("data-id");
                const colorCode = selectedColorOption.value; // Color code is the value of the option

                const sizeSelect = document.getElementById("size");
                const selectedSizeOption = sizeSelect.options[sizeSelect.selectedIndex];
                const size = selectedSizeOption.textContent; // Text inside the <option>
                const sizeId = selectedSizeOption.getAttribute("value");

                const quantity = document.getElementById("quantity").value;

                // Validation
                if (!colorName || colorName === "Choose color") {
                    alert("Please select a valid color.");
                    return;
                }
                if (!size || size === "Choose size") {
                    alert("Please select a valid size.");
                    return;
                }
                if (!quantity || quantity <= 0) {
                    alert("Please enter a valid quantity.");
                    return;
                }

                let isValid = true;

                // Check if the combination of Color and Size already exists
                const rows = document.querySelectorAll(".attributes-table tbody tr");
                for (let row of rows) {
                    const existingColor = row.querySelector("td:nth-child(1)").textContent.trim();
                    const existingSize = row.querySelector("td:nth-child(2)").textContent.trim();

                    if (existingColor === colorName && existingSize === size) {
                        alert(`The combination of Color: "` + colorName + `" and Size: "` + size + `" already exists.`);
                        isValid = false;
                        return;
                    }
                }

                if (isValid) {
                    $.ajax({
                        url: '${pageContext.request.contextPath}/productdetailmanager',
                        type: 'POST',
                        data: {
                            action: 'addAttr',
                            pid: ${pid},
                            cid: colorId,
                            sid: sizeId,
                            quantity: quantity
                        },
                        success: function (response) {
                            $('#add-popup-attr').fadeIn();
                            setTimeout(function () {
                                $('#add-popup-attr').fadeOut();
                            }, 800);

                            const tableBody = document.querySelector(".attributes-table tbody");
                            const newRow = document.createElement("tr");
                            newRow.innerHTML = `
        <td>
            ` + colorName + `
            <div class="color-display" style="width: 50px; height: 20px; background-color:` + colorCode + `; border: 1px solid #ddd; display: inline-block; margin-left: 5px;"></div>
        </td>
        <td>` + size + `</td>
        <td><input type="number" min="1" required value="` + quantity + `" oninput="displaySaveAttrBtn(this)"></td>
        <td>
            <div class="action-attr d-flex gap-3 justify-content-center">
                <button class="save-attr" onclick="saveAttributeChanges(this)" style="display: none;">Save</button>
                <button class="remove-attr" onclick="deleteAttribute(this)">&times;</button>
            </div>
        </td>
    `;

                            // Append the new row to the table
                            tableBody.appendChild(newRow);

                            // Reset form and close modal
                            document.getElementById("addSubAttrForm").reset();
                            closeAddSubAttrModal();
                        }
                    });
                }

                console.log("Color: " + colorName + ", " + colorId + ", " + colorCode);
                console.log("Size: " + size + ", " + sizeId);
                console.log("Quantity: " + quantity);
            }


            function addImage() {
                if (saveCount === 0) {
                    alert('Please add product first!');
                    return;
                }

                const input = $('#addSubImage');

                // Trigger the file input click
                input.click();

                // Unbind any existing 'change' event handlers and bind a new one
                input.off('change').on('change', function () {
                    const file = this.files[0];
                    if (file) {
                        // Validate file size
                        if (file.size > 1024 * 1024) {
                            alert('File size exceeds 1MB');
                            this.value = ''; // Clear the file input
                            return;
                        }

                        // Validate file type
                        if (!file.type.startsWith('image/')) {
                            alert('Only image files are allowed!');
                            this.value = ''; // Clear the file input
                            return;
                        }

                        // Read the file and display it
                        const reader = new FileReader();
                        reader.onload = function (e) {
                            const addImage = e.target.result;

                            $.ajax({
                                url: '${pageContext.request.contextPath}/productdetailmanager',
                                type: 'POST',
                                data: {
                                    action: "addSubImage",
                                    addImage: addImage,
                                    pid: ${pid}
                                },
                                success: function (response) {
                                    $('#add-popup-subImage').fadeIn();
                                    setTimeout(function () {
                                        $('#add-popup-subImage').fadeOut();
                                    }, 800);

                                    // Create the main div
                                    const $subImageDiv = $('<div>', {class: 'subimage-grid', id: 'sub-image'});

                                    // Create the image element
                                    const $imgElement = $('<img>', {
                                        id: 'subImage',
                                        src: addImage,
                                        alt: 'Subimage Preview'
                                    });

                                    // Append the image to the main div
                                    $subImageDiv.append($imgElement);

                                    // Append the div to the container
                                    $('#subimage-container').append($subImageDiv);
                                }
                            });
                        };

                        reader.readAsDataURL(file);
                    }
                });
            }

            function saveAttributeChanges(button, sid, cid) {
                let quantity = $(button).closest("tr").find("input").val();
                console.log(quantity);

                $.ajax({
                    url: '${pageContext.request.contextPath}/productdetailmanager',
                    type: 'POST',
                    data: {
                        action: "saveAttribute",
                        sid: sid,
                        cid: cid,
                        quantity: quantity,
                        pid: ${pid}
                    },
                    success: function (response) {
                        $('#success-popup-attribute').fadeIn();
                        setTimeout(function () {
                            $('#success-popup-attribute').fadeOut();
                        }, 800);

                        let buttonToHide = $(button).closest("tr").find("button.save-attr");
                        hideSaveAttrBtn(buttonToHide);
                    }
                });
            }

            function deleteAttribute(button, sid, cid) {
                let trCount = $('tbody').find('tr').length;

                if (trCount <= 1) {
                    alert("The product must have at least one attribute.");
                    return;
                }

                if (confirm('Are you sure you want to delete this attribute?')) {
                    $.ajax({
                        url: '${pageContext.request.contextPath}/productdetailmanager',
                        type: 'POST',
                        data: {
                            action: "deleteAttribute",
                            sid: sid,
                            cid: cid,
                            pid: ${pid}
                        },
                        success: function (response) {
                            $('#delete-popup-attribute').fadeIn();
                            setTimeout(function () {
                                $('#delete-popup-attribute').fadeOut();
                            }, 800);

                            $(button).closest('tr').remove();
                        }
                    });
                }
            }

            function addTag(button, tagId) {
                if (saveCount === 0) {
                    alert('Please add product first!');
                    return;
                }

                if (confirm('Are you sure you want to add this tag ?')) {
                    $.ajax({
                        url: '${pageContext.request.contextPath}/productdetailmanager',
                        type: 'POST',
                        data: {
                            action: "addTag",
                            tagId: tagId,
                            pid: ${pid}
                        },
                        success: function (response) {
                            $('#success-popup-tag').fadeIn();
                            setTimeout(function () {
                                $('#success-popup-tag').fadeOut();
                            }, 800);
                            let span = $(button).closest('span');
                            let tagExistSection = $('#tag-exist');
                            $(button).text('x');
                            $(button).attr('onclick', `removeTag(this, ` + tagId + `)`);
                            span.remove();
                            tagExistSection.append(span);
                            addTagCount++;
                        }
                    });
                }
            }

            function removeTag(button, tagId) {
                let tagExistSection = $('#tag-exist');
                let tagCount = tagExistSection.find('span').length;
                if (tagCount <= 1) {
                    alert("The product must have at least one tag.");
                    return;
                }

                if (confirm('Are you sure you want to remove this tag ?')) {
                    $.ajax({
                        url: '${pageContext.request.contextPath}/productdetailmanager',
                        type: 'POST',
                        data: {
                            action: "deleteTag",
                            tagId: tagId,
                            pid: ${pid}
                        },
                        success: function (response) {
                            $('#delete-popup-tag').fadeIn();
                            setTimeout(function () {
                                $('#delete-popup-tag').fadeOut();
                            }, 800);
                            let span = $(button).closest('span');
                            let tagAddableSection = $('.tag-badge-addable');
                            $(button).text('+');
                            $(button).attr('onclick', `addTag(this, ` + tagId + `)`);
                            span.remove();
                            tagAddableSection.append(span);
                        }
                    });
                }
            }
//
//            function displaySaveAttrBtn(input) {
//                let button = $(input).closest("tr").find("button.save-attr");
//                $(button).fadeIn();
//            }
//
            function hideSaveAttrBtn(button) {
                $(button).fadeOut();
            }

            function displaySaveBtn() {
                if (saveCount > 0) {
                    return;
                }
                $('.save-overall-information').fadeIn();
            }

            function hideSaveBtn() {
                $('.save-overall-information').fadeOut();
            }

            function formatInput(input) {
                // Remove non-numeric characters except for digits
                let value = input.value.replace(/[^0-9]/g, '');
                // Format with commas (###,###,###)
                input.value = new Intl.NumberFormat().format(value);
                displaySaveBtn();
            }
//
            function saveChanges() {
                // Gather form data
                let thumbnail = $('#thumbnailMain').attr('src');
                let productPrice = $('#product-price').val();
                let productName = $('#product-name').val();
                let productDes = $('#product-des').val();
                let cateId = $('#cate').find(":selected").attr('data-cate-id');
                let status = $('#status').val();

                // Convert product price to an integer
                let productTruePrice = parseInt(productPrice.replace(/,/g, ''), 10);

                if (!productName || productName.trim() === '') {
                    alert("Product name cannot be empty. Please enter a valid name.");
                    return;
                }

                if (!productDes || productDes.trim() === '') {
                    alert("Product description cannot be empty. Please provide a valid description.");
                    return;
                }

                if (isNaN(productTruePrice) || productTruePrice <= 10000) {
                    alert("Product price must be greater than 10,000.");
                    return;
                }

                // Validation checks
                if (!thumbnail || thumbnail.trim() === '') {
                    alert("Thumbnail cannot be empty. Please upload a valid image.");
                    return;
                }

                $.ajax({
                    url: '${pageContext.request.contextPath}/productlistmanager',
                    type: 'POST',
                    data: {
                        action: "add",
                        pid: ${pid},
                        cateId: cateId,
                        status: status,
                        name: productName,
                        des: productDes,
                        price: productTruePrice,
                        thumbnail: thumbnail
                    },
                    success: function (response) {
                        $('#add-popup-subImage').fadeIn();
                        setTimeout(function () {
                            $('#add-popup-subImage').fadeOut();
                        }, 800);

                        let defaultTag = $('#default');

                        saveCount++;
                        addTag(defaultTag, 5);
                        hideSaveBtn();
                    }
                });

                console.log("ID: " + ${pid} + ", CateId: " + cateId + ", Status: " + status + ", Name: " + productName + ", Product Description: " + productDes + ", Price: " + productTruePrice);
            }

        </script>
    </body>
</html>

</body>
</html>
