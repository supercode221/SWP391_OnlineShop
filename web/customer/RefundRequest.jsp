<%@ page contentType="text/html;charset=UTF-8" language="java" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>User Profile</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/CSS/footer.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/CSS/header.css" />
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <link rel="icon" href="${pageContext.request.contextPath}/asset/Image/Favicon/icon2.gif" type="image/gif">

        <style>
            /* Layout styling */
            body, html {
                height: 100%;
                margin: 0;
                display: flex;
                flex-direction: column;
                /*overflow-y: hidden;*/
            }

            * a {
                text-decoration: none;
                color: black;
            }

            .main-user-profile {
                margin: 48px auto;
                display: flex;
                /*justify-content: space-around;*/
                gap: 20px;
            }

            .side {
                position: absolute; /* Set initially to absolute */
                top: 295px; /* Initial top position */
                left: 57px;
                width: 200px;
                z-index: 1000;
                background-color: white;
                box-shadow: 0 0 5px #ccc;
                padding: 20px;
                border-radius: 10px;
            }

            .side.fixed {
                position: fixed;
                top: 120px; /* Adjust fixed position for when scrolling */
            }

            .side a{
                padding: 5px 20px;
                font-weight: bold;
                border: solid 1px black;
                margin-bottom: 10px;
                border-radius: 10px;
                transition: ease-in-out 0.2s;
                transform: scale(1);
            }

            .side a:hover{
                background-color: black;
                color: white;
                transform: scale(1.005);
            }

            .selected {
                background-color: black;
                color: white;
            }

            .user-profile {
                background-color: white;
                box-shadow: 0 0 5px #ccc;
                padding: 20px;
                border-radius: 10px;
                /*height: 300px;*/
            }

            form {
                display: flex;
                align-items: center;
                /*height: 60vh;*/
                justify-content: space-around;
                /*height: 260px;*/
            }

            .user-information .user-input {
                border: solid 1px #ccc;
                padding: 3px 10px;
                width: 400px;
                margin-left: 20px;
                border-radius: 6px;
            }

            .user-information select {
                border: solid 1px #ccc;
                padding: 3px 10px;
                width: 400px;
                margin-left: 20px;
                border-radius: 6px;
            }

            .file-input {
                padding: 3px 10px;
                margin-left: 13px;
                border-radius: 6px;
            }

            .save-btn {
                display: none;
                margin-top: 20px;
            }

            .save-btn button {
                border: none;
                background-color: black;
                color: white;
                border-radius: 10px;
                padding: 5px 20px;
                font-weight: bold;
                transition: ease-in-out 0.2s;
            }

            .save-btn button:hover {
                background-color: white;
                color: black;
                box-shadow: inset 0 0 0 1px black;
            }

            .toggle-sidebar-btn {
                display: none;
            }

            #card {
                border: none;
                padding: 5px 10px;
                border-radius: 5px;
                background-color: #33be05;
                color: white;
                transition: ease-in-out 0.2s;
            }

            #card:hover {
                background-color: white;
                color: #33be05;
                box-shadow: inset 0 0 0 1px #33be05;
            }

            #number {
                border: none;
                padding: 5px 10px;
                border-radius: 5px;
                background-color: #33bcff;
                color: white;
                transition: ease-in-out 0.2s;
            }

            #number:hover {
                background-color: white;
                color: #33bcff;
                box-shadow: inset 0 0 0 1px #33bcff;
            }

            #card.active {
                background-color: #f7e5a2;
                color: #000;
                box-shadow: inset 0 0 0 2px #000;
            }

            #number.active {
                background-color: #f7e5a2;
                color: #000;
                box-shadow: inset 0 0 0 2px #000;
            }

            /* Refund Form Styles */
            .main {
                display: flex;
                flex-direction: column;
                align-items: center;
                background-color: #ffffff;
                padding: 20px;
                border: 1px solid #ddd;
                border-radius: 10px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                margin-bottom: 20px;
            }

            label {
                display: block;
                font-weight: bold;
                margin-bottom: 5px;
            }

            input[type="text"],
            select {
                width: 100%;
                max-width: 300px;
                padding: 10px;
                margin-bottom: 15px;
                border: 1px solid #ccc;
                border-radius: 5px;
                font-size: 14px;
                box-sizing: border-box;
            }

            input[type="text"]:disabled {
                background-color: #e9ecef;
                cursor: not-allowed;
            }

            .bank-logo img {
                margin-top: 10px;
                border-radius: 10px;
                border: 1px solid #ddd;
            }

            /* Table Styles for Card Transfer */
            table {
                width: 100%;
                max-width: 600px;
                border-collapse: collapse;
                margin-top: 15px;
            }

            th,
            td {
                padding: 10px;
                text-align: left;
            }

            th {
                font-size: 14px;
                color: #555;
            }

            td {
                font-size: 14px;
            }

            table input[type="text"] {
                width: calc(100% - 20px);
            }

            /* Responsive Design */
            @media (max-width: 768px) {
                .header-refund {
                    flex-direction: column;
                }

                .header-refund button {
                    width: 100%;
                    margin: 5px 0;
                }

                table {
                    max-width: 100%;
                }

                input[type="text"],
                select {
                    max-width: 100%;
                }
            }
        </style>
    </head>

    <body>
        <jsp:include page="../base-component/header.jsp" />

        <%
            String successMessage = (String) session.getAttribute("successMessage");
            if (successMessage != null) {
        %>
        <script>
            Swal.fire({
                icon: 'success',
                title: 'Success',
                text: '<%= successMessage %>',
                confirmButtonText: 'OK'
            });
        </script>
        <%
            session.removeAttribute("successMessage");
            }
        %>

        <!-- Main layout container -->
        <div class="container main-user-profile">
            <!-- Sidebar -->
            <div class="side">
                <a href="userprofilecontroller" class="dropdown-item">User Profile</a>
                <c:if test="${sessionScope.roleId == 1}"><a href="billlist" class="dropdown-item">My Orders</a></c:if>
                <c:if test="${sessionScope.roleId == 1}"><a href="refund" class="dropdown-item selected">Refund Request</a></c:if>
                    <a href="changepassword" class="dropdown-item">Change Password</a>
                <c:if test="${sessionScope.roleId == 1}"><a href="addresses" class="dropdown-item">Addresses</a></c:if>
                    <a href="LogOut" class="dropdown-item">Logout</a>
                </div>

                <!-- User Profile Form Content -->
                <div style="height: 500px;" class="user-profile container-fluid">
                    <div class="header-refund">
                        <button id="card">Refund through bank transfer</button>
                        <button id="number">Refund through card</button>
                    </div>
                    <center>
                        <div id="form">
                            <div class="number-refund container-fluid" style="display: none;">
                                <h4>Bank Transfer</h4>
                                <div class="main">
                                    <div class="select-bank">
                                        <table>
                                            <tr>
                                                <th><label for="bank-transfer-select">Bank:</label></th>
                                                <th><label for="bank-transfer-number">Account number:</label></th>
                                                <th><label for="bank-transfer-customer-name">Name:</label></th>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <select id="bank-transfer-select">
                                                        <option value="">Select a bank</option>
                                                    </select>
                                                </td>
                                                <td>
                                                    <input id="bank-transfer-number" type="text" name="number" placeholder="Enter account number" onchange="fetchCusName(this)">
                                                </td>
                                                <td>
                                                    <div class="name-customer-ajax">
                                                        <input id="bank-transfer-customer-name" type="text" disabled="">
                                                    </div>
                                                </td>
                                        </table>
                                    </div>
                                </div>
                                <div class="bank-logo">
                                    <img id="bank-transfer-logo" src="" alt="Bank Logo" style="display: none;" width="300"/>
                                </div>
                            </div>
                            <div class="card-refund container-fluid" style="display: none;">
                                <h4>Card Transfer</h4>
                                <div class="main">
                                    <div class="select-bank">
                                        <table>
                                            <tr>
                                                <th><label for="card-transfer-select">Bank:  </label></th>
                                                <th><label for="card-transfer-number">Account number:  </label></th>
                                                <th><label for="card-transfer-customer-name">Name:  </label></th>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <select id="card-transfer-select">
                                                        <option value="">Select a bank</option>
                                                    </select>
                                                </td>
                                                <td>
                                                    <input id="card-transfer-number" type="text" name="number" placeholder="Enter card number" onchange="fetchCusName(this)">
                                                </td>
                                                <td>
                                                    <div class="name-customer-ajax">
                                                        <input id="card-transfer-customer-name" type="text" disabled="">
                                                    </div>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                </div>
                                <div class="bank-logo">
                                    <img id="card-transfer-logo" src="" alt="Bank Logo" style="display: none;" width="300"/>
                                </div>
                            </div>
                        </div>
                        <button style="display: none;" id="submit-button" class="w-25 mt-3 btn btn-primary" type="button" onclick="submitForm()">Submit</button>
                    </center>
                </div>
            </div>

        <jsp:include page="../base-component/footer.jsp" />

        <script>

            let account;
            let bankCode;
            let name;

            function submitForm() {
                if (!name) {
                    alert('Please input the right bank account!');
                    return;
                }
                $.ajax({
                    url: 'refund', // Replace with your API endpoint
                    type: 'POST',
                    data: {
                        bank: bankCode,
                        account: account,
                        name: name
                    },
                    success: function (response) {
                        alert('request submited successfully!');
                        return;
                    },
                    error: function (xhr, status, error) {
                        console.error("Request Fail!", status, error);
                        let cusInput = $(input).closest('tr').find('.name-customer-ajax input');
                        cusInput.val("Error fetching name."); // Graceful error handling
                    }
                });
            }

            $(document).ready(function () {
                const bankApiUrl = "https://api.vietqr.io/v2/banks";

                // Function to populate bank dropdown and handle logo updates
                function setupBankTransfer(selectId, logoId) {
                    if (!selectId || !logoId) {
                        console.error("Missing selectId or logoId.");
                        return;
                    }

                    var $select = $("#" + selectId);
                    var $logo = $("#" + logoId);

                    if (!$select.length || !$logo.length) {
                        console.error("Elements with IDs '" + selectId + "' or '" + logoId + "' not found.");
                        return;
                    }

                    // Fetch and populate bank options
                    $.getJSON(bankApiUrl, function (data) {
                        if (data && data.data) {
                            var banks = data.data;
                            banks.forEach(function (bank) {
                                if (bank.code && bank.short_name && bank.logo) {
                                    $select.append(
                                            '<option data-bank-code=' + bank.code + ' value="' + bank.logo + '">' + bank.short_name + '</option>'
                                            );
                                }
                            });

                            // Event listener for updating the logo on selection
                            $select.on("change", function () {
                                var selectedLogo = $(this).val();
                                $logo.attr("src", selectedLogo ? selectedLogo : "").toggle(!!selectedLogo);
                            });
                        } else {
                            console.error("Unexpected API response structure.");
                        }
                    }).fail(function () {
                        console.error("Failed to fetch bank data.");
                    });
                }

                // Initialize bank and card transfer dropdowns
                setupBankTransfer("bank-transfer-select", "bank-transfer-logo");
                setupBankTransfer("card-transfer-select", "card-transfer-logo");
            });

            $('#card').on('click', function () {
                $('#card').toggleClass("active");
                $('#number').removeClass("active");
                $('.card-refund').fadeOut();
                $('#submit-button').fadeOut();
                setTimeout(function () {
                    $('.number-refund').fadeToggle();
                    $('#submit-button').fadeToggle();
                }, 500);
            });

            $('#number').on('click', function () {
                $('#number').toggleClass("active");
                $('#card').removeClass("active");
                $('.number-refund').fadeOut();

                $('#submit-button').fadeOut();
                setTimeout(function () {
                    $('.card-refund').fadeToggle();
                    $('#submit-button').fadeToggle();
                }, 500);
            });

            function fetchCusName(input) {
                // Get the account number from the input field
                let account = $(input).val().trim();
                // Get the selected bank code
                let bankCode = $(input).closest('.select-bank').find('select :selected').attr('data-bank-code');

                console.log("Account: " + account);
                console.log("Bank Code: " + bankCode);
                account = account;
                bankCode = bankCode;

                // Validate inputs
                if (!account) {
                    alert("Please enter an account number.");
                    return;
                }
                if (!bankCode) {
                    alert("Please select a bank.");
                    return;
                }

                // Make the API call
                $.ajax({
                    url: 'https://api.httzip.com/api/bank/id-lookup-prod', // Replace with your API endpoint
                    type: 'POST',
                    headers: {
                        "x-api-key": "b9b0c947-5c26-4d09-8252-6b570c7bd655key",
                        "x-api-secret": "d2f41732-c5f4-45f7-81c8-110f704aa56dsecret"
                    },
                    data: {
                        "bank": bankCode,
                        "account": account
                    },
                    success: function (response) {
                        // Locate the customer name input within the same row
                        let cusInput = $(input).closest('tr').find('.name-customer-ajax input');

                        if (response && response.success && response.data && response.data.ownerName) {
                            // Update the input field value with the fetched owner name
                            cusInput.val(response.data.ownerName);
                            name = response.data.ownerName;
                        } else {
                            console.error("Unexpected response or missing ownerName:", response);
                            cusInput.val("Owner name not found."); // Graceful handling for missing ownerName
                        }
                    },
                    error: function (xhr, status, error) {
                        console.error("API request failed:", status, error);
                        let cusInput = $(input).closest('tr').find('.name-customer-ajax input');
                        cusInput.val("Error fetching name."); // Graceful error handling
                    }
                });
            }
        </script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
