$(function(){

    const appendToDo = function(data){
//        var todoCode = '<a href="#" class="todo-link" data-id="' +
//            data.id + '">' + data.name + '</a><br>';
//        $('#todo-list')
//            .append('<div>' + todoCode + '</div>');
        var todoCode2 = '<li style=" " class="todo-link" data-id="' + data.id + '">' + data.name + '<span class="close">×</span></li>';

        $('#myUL')
                    .append(todoCode2);

    };

    //Loading books on load page
    $.get('/list/', function(response)
    {
        for(i in response) {
            appendToDo(response[i]);
        }
    });

    //Show adding book form
    $('#show-add-todo-form').click(function(){
        $('#todo-form').css('display', 'flex');
    });



    //Closing adding book form
    $('#todo-form').click(function(event){
        if(event.target === this) {
            $(this).css('display', 'none');
        }
    });

    $('#todo-form-edit').click(function(event){
        if(event.target === this) {
            $(this).css('display', 'none');
        }
    });

    //Getting book
    $(document).on('click', '.todo-link', function(){
        var link = $(this);
        var todoId = link.data('id');

        $.ajax({
            method: "GET",
            url: '/list/' + todoId,
            success: function(response)
            {
               $('#todo-form-edit').css('display', 'flex');
               $('#todo-form-edit').find("input[name=name]").val(response.name);
               $('#todo-form-edit').find("input[name=description]").val(response.description);

//                var code = '<span>Описание:' + response.description + '</span>';
//                link.parent().append(code);
            },
            error: function(response)
            {
                if(response.status == 404) {
                    alert('Описание отсутствует');
                }
            }
        });
        return false;
    });

    //Edit book
    $('#save-todo-edit').click(function()
    {
        var data = $('#todo-form-edit form').serialize();
        $.ajax({
            method: "PUT",
            url: '/list/',
            data: data,
            success: function(response)
            {
                $('#todo-form').css('display', 'none');
                var todo = {};
                todo.id = response;
                var dataArray = $('#todo-form form').serializeArray();
                for(i in dataArray) {
                    todo[dataArray[i]['name']] = dataArray[i]['value'];
                }
                appendToDo(todo);
            }
        });
        return false;
    });

    //Adding book
    $('#save-todo').click(function()
    {
        var data = $('#todo-form form').serialize();
        $.ajax({
            method: "POST",
            url: '/list/',
            data: data,
            success: function(response)
            {
                $('#todo-form').css('display', 'none');
                var todo = {};
                todo.id = response;
                var dataArray = $('#todo-form form').serializeArray();
                for(i in dataArray) {
                    todo[dataArray[i]['name']] = dataArray[i]['value'];
                }
                appendToDo(todo);
            }
        });
        return false;
    });

    // Create a "close" button and append it to each list item
    var myNodelist = document.getElementsByTagName("LI");
    var i;
    for (i = 0; i < myNodelist.length; i++) {
      var span = document.createElement("SPAN");
      var txt = document.createTextNode("\u00D7");
      span.className = "close";
      span.appendChild(txt);
      myNodelist[i].appendChild(span);
    }

    // Click on a close button to hide the current list item
    var close = document.getElementsByClassName("close");
    var i;
    for (i = 0; i < close.length; i++) {
      close[i].onclick = function() {
        var div = this.parentElement;
        div.style.display = "none";
      }
    }

    // Add a "checked" symbol when clicking on a list item
    var list = document.querySelector('ul');
    list.addEventListener('click', function(ev) {
      if (ev.target.tagName === 'LI') {
        ev.target.classList.toggle('checked');
      }
    }, false);

});