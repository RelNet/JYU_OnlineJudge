 /*显示浮动框*/
    function showCommentDiv(obj) {
        var top = $(obj).offset().top;
        var left = $(obj).offset().left + $(obj).width() - 7;
        $("#commentListDiv").css({'top':top + "px",'left':left+"px"}).show("slow");
    }

function showThis(obj) {
    $(obj).show();
}
/*隐藏浮动框*/
function hideCommentDiv() {
    $("#editCourseDiv").hide();
}
function hideThis(obj) {
    $(obj).hide();
}
//获取选中的评语
function getValue(obj) {
    var selectId = obj.value;
    var selectTitle = obj.title;
    $("#teaMarkContent").val(selectTitle);
}
