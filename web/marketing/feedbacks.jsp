<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Feedback Manager</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <link rel="stylesheet" href="https://unpkg.com/swiper/swiper-bundle.min.css"/>
        <link rel="icon" href="${pageContext.request.contextPath}/asset/Image/Favicon/icon2.gif" type="image/gif">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/CSS/header.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/CSS/sidebar.css"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <style>
            * a {
                text-decoration: none;
                color: black;
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

            <!-- Filter and Search Section -->
            <div class="row mb-3">
                <div class="col-md-3">
                    <input type="text" id="searchInput" class="form-control" placeholder="Search Customer ">
                </div>
                <div class="col-md-2">
                    <select id="statusFilter" class="form-control">
                        <option value="">All Status</option>
                        <option value="Show">Show</option>
                        <option value="Hide">Hide</option>
                    </select>
                </div>
                <div class="col-md-2">
                    <select id="starFilter" class="form-control">
                        <option value="">All Stars</option>
                        <option value="5">5 Stars</option>
                        <option value="4">4 Stars</option>
                        <option value="3">3 Stars</option>
                        <option value="2">2 Stars</option>
                        <option value="1">1 Star</option>
                    </select>
                </div>
                <div class="col-md-3 text-end">
                    <button class="btn btn-dark" onclick="applyFilters()">Search</button>
                </div>
            </div>

            <!-- Feedback Table -->
            <div class="table-responsive" id="all-table">
                <table class="table table-bordered table-hover">
                    <thead class="table-dark">
                        <tr>
                            <th onclick="sortFeedbacks('fullName')">Full Name</th>
                            <th onclick="sortFeedbacks('orderId')">Order ID</th>
                            <th onclick="sortFeedbacks('category')">Category</th>
                            <th onclick="sortFeedbacks('productName')">Product Name</th>
                            <th onclick="sortFeedbacks('star')">Rated Star</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody id="feedbackContainer"></tbody>
                </table>
            </div>

            <!-- Pagination and Load More -->
            <div class="text-center mt-4">
                <button id="loadMoreBtn" class="btn btn-secondary" onclick="loadFeedbacks(offset)">Load More</button>
            </div>
        </div>

        <script>
            const toggleSidebarBtn = document.getElementById('toggleSidebarBtn');
            const sidebar = document.getElementById('sidebar');
            const content = document.getElementById('content');
            const content1 = document.getElementById('content1');
            const navbar = document.getElementById('navbar');

            $(document).ready(function () {
                var activeSideBar = $("#feedback-list");
                activeSideBar.addClass("active-custom");
            });

            toggleSidebarBtn.addEventListener('click', () => {
                sidebar.classList.toggle('collapsed');
                content.classList.toggle('collapsed');
                content1.classList.toggle('collapsed');
                navbar.classList.toggle('collapsed');
                toggleSidebarBtn.classList.toggle('collapsed');
            });
            let offset = 0;
            let limit = 2; // Set the limit for feedbacks per load
            let sortBy = "fullName";
            let sortOrder = "ASC";

            // Load initial feedbacks
            loadFeedbacks(0);

            function loadFeedbacks(newOffset) {
                if (newOffset !== 0)
                    offset = newOffset; // Update offset only if it's not the initial load

                $.ajax({
                    url: 'feedbackservlet',
                    method: 'POST',
                    data: {
                        offset: offset,
                        limit: limit,
                        status: $('#statusFilter').val(),
                        product: $('#productFilter').val(),
                        star: $('#starFilter').val(),
                        search: $('#searchInput').val(),
                        sortBy: sortBy,
                        sortOrder: sortOrder
                    },
                    success: function (response) {
                        // If this is the first load (filter/search), clear the feedback container
                        console.log(response);
                        if (newOffset === 0) {
                            $('#feedbackContainer').empty();
                            offset = 0; // Reset offset to 0 for new filter/search
                        }
                        // Append the new feedbacks
                        var haveItem = false;
                        response.forEach(function (feedback) {
                            var feedbackHtml = "<tr>" +
                                    "<td>" + feedback.userName + "</td>" +
                                    "<td>" + feedback.orderId + "</td>" +
                                    "<td>" + feedback.product.name + "</td>" +
                                    "<td>" + feedback.productName + "</td>" +
                                    "<td>" + feedback.star + " Stars</td>" +
                                    "<td>" +
                                    "<a href='feedbackdetail?feedbackId=" + feedback.FeedbackId + "' class='btn btn-outline-info btn-sm'>View</a>" +
                                    "</td>" +
                                    "</tr>";
                            $('#feedbackContainer').append(feedbackHtml);
                            haveItem = true;
                        });

                        if (haveItem === false) {
                            $('#feedbackContainer').empty().append('<div class="alert alert-warning w-100">No Feedback Match!</div>');
                        }

                        // Increment offset for the next load only if new feedbacks are loaded
                        if (response.length > 0) {
                            offset += limit;
                        }

                        // Optionally hide the Load More button if no more feedbacks are available
                        if (response.length < limit) {
                            $('#loadMoreBtn').hide(); // Hide the button if no more feedbacks
                        } else {
                            $('#loadMoreBtn').show(); // Show the button if there are more feedbacks
                        }
                    },
                    error: function () {
                        alert('Error loading feedbacks.');
                    }
                });
            }

            function applyFilters() {
                // Reset offset and reload feedbacks when filters are applied
                offset = 0; // Reset offset when applying filters
                loadFeedbacks(0); // Load feedbacks based on the new filters
            }

            function sortFeedbacks(column) {
                // Toggle sort order
                sortOrder = (sortBy === column && sortOrder === "ASC") ? "DESC" : "ASC";
                sortBy = column;
                applyFilters(); // Reload feedbacks with the new sorting
            }

            function deleteFeedback(feedbackId) {
                // Implement delete functionality here
                $.ajax({
                    url: 'FeedbackServlet',
                    method: 'DELETE',
                    data: {feedbackId: feedbackId},
                    success: function () {
                        applyFilters(); // Reload feedbacks after delete
                    },
                    error: function () {
                        alert('Error deleting feedback.');
                    }
                });
            }

        </script>
    </body>
</html>
