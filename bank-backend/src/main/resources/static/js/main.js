/**
 * Created by z00382545 on 10/20/16.
 */

(function ($) {
    $.toggleShowPassword = function (options) {
        var settings = $.extend({
            field: "#password",
            control: "#toggle_show_password",
        }, options);

        var control = $(settings.control);
        var field = $(settings.field)

        control.bind('click', function () {
            if (control.is(':checked'))
            {
                field.attr('type', 'text');
            } else
            {
                field.attr('type', 'password');
            }
        })
    };

    $.transferDisplay = function () {
        $("#transferFrom").change(function () {
            if ($("#transferFrom").val() == 'Primary')
            {
                $('#transferTo').val('Savings');
            } else if ($("#transferFrom").val() == 'Savings')
            {
                $('#transferTo').val('Primary');
            }
        });

        $("#transferTo").change(function () {
            if ($("#transferTo").val() == 'Primary')
            {
                $('#transferFrom').val('Savings');
            } else if ($("#transferTo").val() == 'Savings')
            {
                $('#transferFrom').val('Primary');
            }
        });
    };


}(jQuery));

$(document).ready(function () {
    var confirm = function () {
        bootbox.confirm({
            title: "Appointment Confirmation",
            message: "Do you really want to schedule this appointment?",
            buttons: {
                cancel: {
                    label: '<i class="fa fa-times"></i> Cancel'
                },
                confirm: {
                    label: '<i class="fa fa-check"></i> Confirm'
                }
            },
            callback: function (result) {
                if (result == true)
                {
                    $('#appointmentForm').submit();
                } else
                {
                    console.log("Scheduling cancelled.");
                }
            }
        });
    };

    $.toggleShowPassword({
        field: '#password',
        control: "#showPassword"
    });

    $.transferDisplay();


    $('.dateString').pickadate({
        format: 'yyyy-mm-dd',
        selectMonths: true, // Creates a dropdown to control month
        selectYears: 15, // Creates a dropdown of 15 years to control year,
        today: 'Today',
        clear: 'Clear',
        close: 'Ok',
        closeOnSelect: false // Close upon selecting a date,
    });

    $('.timeString').pickatime({
        format: 'hh:mm',
        default: 'now', // Set default time: 'now', '1:30AM', '16:30'
        fromnow: 0,       // set default time to * milliseconds from now (using with default = 'now')
        twelvehour: false, // Use AM/PM or 24-hour format
        donetext: 'OK', // text for done-button
        cleartext: 'Clear', // text for clear-button
        canceltext: 'Cancel', // Text for cancel-button
        autoclose: false, // automatic close timepicker
        ampmclickable: true, // make AM PM clickable
        aftershow: function () {
        } //Function for after opening timepicker
    });

    $('#submitAppointment').click(function () {
        confirm();
    });
});

$(document).ready(function () {
    Materialize.updateTextFields();
});

$(document).ready(function() {
    $('select').material_select();
});

document.addEventListener("DOMContentLoaded", function(){
    $('.preloader-background').delay(1700).fadeOut('slow');

    $('.preloader-wrapper')
        .delay(1700)
        .fadeOut();
});