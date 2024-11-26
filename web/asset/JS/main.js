(function ($) {
    "use strict";

    /*==================================================================
     [ Focus input ]*/
    $('.input100').each(function () {
        $(this).on('blur', function () {
            if ($(this).val().trim() != "") {
                $(this).addClass('has-val');
            } else {
                $(this).removeClass('has-val');
            }
        })
    });

    /*==================================================================
     [ Validate ]*/
    var input = $('.validate-input .input100');

    $('.validate-form').on('submit', function () {
        var check = true;

        // Validate First Name
        if (!validate($('input[name="fname"]'))) {
            alert("First Name is required");
            showValidate($('input[name="fname"]'));
            return false; // Stop here if invalid
        }

        // Validate Last Name
        if (!validate($('input[name="lname"]'))) {
            alert("Last Name is required");
            showValidate($('input[name="lname"]'));
            return false; // Stop here if invalid
        }

        // Validate Email
        if (!validate($('input[name="email"]'))) {
            alert("Valid Email is required");
            showValidate($('input[name="email"]'));
            return false; // Stop here if invalid
        }

        // Validate Password
        var password = $('#password').val().trim();
        if (!validatePassword(password)) {
            alert("Password must contain at least 6 characters, 1 uppercase letter, 1 uppercase letter and 1 number.");
            showValidate($('#password'));
            return false; // Stop here if invalid
        }

        // Validate Re-Password
        var rePassword = $('#repassword').val().trim();
        if (rePassword === '') {
            alert("Re-Password is required");
            showValidate($('#repassword'));
            return false; // Stop here if invalid
        }

        // Check if Passwords match
        if (password !== rePassword) {
            alert("Passwords do not match!");
            showValidate($('#repassword'));
            return false; // Stop here if invalid
        }

        return check;
    });

    $('.validate-form .input100').each(function () {
        $(this).focus(function () {
            hideValidate(this);
        });
    });

    // Validate email and other fields
    function validate(input) {
        if ($(input).attr('type') == 'email' || $(input).attr('name') == 'email') {
            // Email validation regex
            if ($(input).val().trim().match(/^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{2,5}|[0-9]{1,3})(\]?)$/) == null) {
                return false;
            }
        } else {
            if ($(input).val().trim() == '') {
                return false;
            }
        }
        return true;
    }

    // Validate password complexity
    function validatePassword(password) {
        // Password must contain at least 6 characters, 1 uppercase letter, 1 uppercase letter and 1 number.
        // old regex
//        var regex = /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[@$!#%*?&])[A-Za-z\d@$!%*?&]{6,}$/;
        // new regex
        var regex = /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)[A-Za-z\d@$!%#*?&]{6,}$/;
        return regex.test(password);
    }

    function showValidate(input) {
        var thisAlert = $(input).parent();
        $(thisAlert).addClass('alert-validate');
    }

    function hideValidate(input) {
        var thisAlert = $(input).parent();
        $(thisAlert).removeClass('alert-validate');
    }

})(jQuery);
