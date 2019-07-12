$(function () {
    'use strict';

    var client;
    var fromElement = $('#from');
    var connectElement = $('#connect');
    var disconnectElement = $('#disconnect');

    function showMessage(message) {
        $('#messages').append('<tr>' +
            '<td>' + message.from + '</td>' +
            '<td>' + message.capturedTemplateVariable + '</td>' +
            '<td>' + message.message + '</td>' +
            '<td>' + message.time + '</td>' +
            '</tr>');
    }

    function setConnected(connected) {
        connectElement.prop("disabled", connected);
        disconnectElement.prop("disabled", !connected);
        fromElement.prop('disabled', connected);
        var text = $('#text');
        text.prop('disabled', !connected);
        if (connected) {
            $("#conversation").show();
            text.focus();
        } else $("#conversation").hide();
        $("#messages").html("");
    }

    $("form").on('submit', function (e) {
        e.preventDefault();
    });

    fromElement.on('blur change keyup', function (ev) {
        $('#connect').prop('disabled', $(this).val().length === 0);
    });
    $('#connect, #disconnect, #text').prop('disabled', true);

    connectElement.click(function () {
        client = Stomp.over(new SockJS('/chat'));
        client.connect({}, function (frame) {
            setConnected(true);
            client.subscribe('/topic/messages', function (message) {
                showMessage(JSON.parse(message.body));
            });
        });
    });

    disconnectElement.click(function () {
        if (client != null) {
            client.disconnect();
            setConnected(false);
        }
        client = null;
    });

    $('#send').click(function () {
        var capturedTemplateVariable = $('#capturedTemplateVariable').val();
        var textElement = $('#text');
        client.send("/app/chat/" + capturedTemplateVariable, {}, JSON.stringify({
            from: $("#from").val(),
            text: textElement.val()
        }));
        textElement.val("");
    });
});
