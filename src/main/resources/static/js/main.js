// credit: https://2freeclear.wordpress.com/2014/08/09/create-nested-html-tables-from-complex-json/
function tableCreator(e, t) {
   function i(e, t) {
      var n = "";
      var r = "";
      var s = "";
      $.each(t[0], function(e, t) {
         s += "<th>" + e + "</th>"
      });
      $.each(t, function(e, t) {
         r += "<tr>";
         $.each(t, function(e, t) {
            var n = 1 + Math.floor(Math.random() * 90 + 10);
            var s = $.isPlainObject(t);
            var o = [];
            if (s) {
               o = $.makeArray(t)
            }
            if ($.isArray(t) && t.length > 0) {
               r += "<td><div><a href='#" + n + "' data-toggle='collapse' data-parent='#msgReport'><span class='glyphicon glyphicon-plus'></span></a><div id='" + n + "' class='panel-collapse collapse'>" + i(e, t) + "</div></div></td>"
            } else {
               if (o.length > 0) {
                  r += "<td><div><a href='#" + n + "' data-toggle='collapse' data-parent='#msgReport'><span class='glyphicon glyphicon-plus'></span></a><div id='" + n + "' class='panel-collapse collapse'>" + i(e, o) + "</div></div></td>"
               } else {
                  r += "<td>" + t + "</td>"
               }
            }
         });
         r += "</tr>"
      });
      n += "<table class='table table-bordered table-hover table-condensed'><thead>" + s + "</thead><tbody>" + r + "</tbody></table>";
      return n
   }
   $(t).empty();
   $(t).append("<table id='parentTable' class='table table-bordered table-hover table-condensed'><thead></thead><tbody></tbody></table>");
   var n = "";
   var r = "";
   $.each(e, function(e, t) {
      n += "<th>" + e + "</th>";
      var s = 1 + Math.floor(Math.random() * 90 + 10);
      var o = $.isPlainObject(t);
      var u = [];
      if (o) {
         u = $.makeArray(t)
      }
      if ($.isArray(t) && t.length > 0) {
         r += "<td><div id='accordion'><a href='#" + s + "' data-toggle='collapse' data-parent='#msgReport'><span class='glyphicon glyphicon-plus'></span></a><div id='" + s + "' class='panel-collapse collapse'>" + i(e, t) + "</div></div></td>"
      } else {
         if (u.length > 0) {
            r += "<td><div id='accordion'><a href='#" + s + "' data-toggle='collapse' data-parent='#msgReport'><span class='glyphicon glyphicon-plus'></span></a><div id='" + s + "' class='panel-collapse collapse'>" + i(e, u) + "</div></div></td>"
         } else {
            r += "<td>" + t + "</td>"
         }
      }
   });
   $("#parentTable thead").append("<tr>" + n + "</tr>");
   $("#parentTable tbody").append("<tr>" + r + "</tr>");
   $(".glyphicon ").on("click", function() {
      var e = $(this).attr("class");
      switch (e) {
         case "glyphicon glyphicon-plus":
            $(this).removeClass("glyphicon-plus").addClass("glyphicon-minus");
            break;
         case "glyphicon glyphicon-minus":
            $(this).removeClass("glyphicon-minus").addClass("glyphicon-plus");
            break;
         default:
      }
   })
}
