$(function(){

    const appendToDo = function(data){
//        var todoCode = '<a href="#" class="todo-link" data-id="' +
//            data.id + '">' + data.name + '</a><br>';
//        $('#todo-list')
//            .append('<div>' + todoCode + '</div>');
        var todoCode2 = '<li>' + data.name + '<span class="close">×</span></li>';

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

    //Getting book
    $(document).on('click', '.todo-link', function(){
        var link = $(this);
        var todoId = link.data('id');
        $.ajax({
            method: "GET",
            url: '/list/' + todoId,
            success: function(response)
            {
                var code = '<span>Год выпуска:' + response.year + '</span>';
                link.parent().append(code);
            },
            error: function(response)
            {
                if(response.status == 404) {
                    alert('Книга не найдена!');
                }
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

    // Add a "checked" symbol when clicking on a list item
    var list = document.querySelector('ul');
    list.addEventListener('click', function(ev) {
      if (ev.target.tagName === 'LI') {
        ev.target.classList.toggle('checked');
      }
    }, false);

});