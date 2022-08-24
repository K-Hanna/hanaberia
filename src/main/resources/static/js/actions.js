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

//menu
 $('.item ul li a').click(function() {
   document.getElementById("menu-bar").classList.toggle("change");
   document.getElementById("nav").classList.toggle("change-item");
   document.getElementById("menu-bg").classList.toggle("change-bg");
 });

function menuOnClick() {
  document.getElementById("menu-bar").classList.toggle("change");
  document.getElementById("nav").classList.toggle("change-item");
  document.getElementById("menu-bg").classList.toggle("change-bg");
}