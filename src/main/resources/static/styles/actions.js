// gallery
 $('.gallery ul li a').click(function() {
     var itemID = $(this).attr('href');
     $('.gallery ul').addClass('item_open');
     $(itemID).addClass('item_open');
     return false;
 });
 $('.close').click(function() {
     $('.port, .gallery ul').removeClass('item_open');
     return false;
 });

 $(".gallery ul li a").click(function() {
     $('html, body').animate({
         scrollTop: parseInt($("#top").offset().top)
     }, 400);
 });

 function menuOnClick() {
   document.getElementById("menu-bar").classList.toggle("change");
   document.getElementById("nav").classList.toggle("change");
   document.getElementById("menu-bg").classList.toggle("change-bg");
 }
