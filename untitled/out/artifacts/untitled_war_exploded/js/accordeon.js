$(document).ready(function(){
  $('#invest')
    .click(function(event){
      event.stopPropagation();
    })
    .hide();   
  $('#inv').click(function(){
    var selfClick = $('#invest').is(':visible');
    if(!selfClick) {
        $('#invest').slideToggle();
    }
    else {
        $('#invest').stop(true, true).slideToggle();
    }
  });

    $('#bonus')
        .click(function(event){
            event.stopPropagation();
        })
        .hide();
    $('#bon').click(function(){
        var selfClick = $('#bonus').is(':visible');
        if(!selfClick) {
            $('#bonus').slideToggle();
        }
        else {
            $('#bonus').stop(true, true).slideToggle();
        }
    });

    $('#freeze')
        .click(function(event){
            event.stopPropagation();
        })
        .hide();
    $('#fr').click(function(){
        var selfClick = $('#freeze').is(':visible');
        if(!selfClick) {
            $('#freeze').slideToggle();
        }
        else {
            $('#freeze').stop(true, true).slideToggle();
        }
    });
});
