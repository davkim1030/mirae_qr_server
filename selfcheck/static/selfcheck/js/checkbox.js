function allunCheckFunc( obj ) {
    $("[name=Q2]").prop("checked", false );

    if($("[name=Q2]:checked").length==0){
        $("[name=Q2_n]").prop("checked", true );
    }
}

/* 체크박스 체크시 전체선택 체크 여부 */
function oneCheckFunc( obj )
{
var allObj = $("[name=Q2_n]");
var objName = $(obj).attr("name");

if( $(obj).prop("checked") )
{
    checkBoxLength = $("[name="+ objName +"]").length;
    checkedLength = $("[name="+ objName +"]:checked").length;

    if( checkBoxLength == checkedLength ) {
        allObj.prop("checked", false);
    }
     else {
        allObj.prop("checked", false);
    }
}
else
{
    allObj.prop("checked", false);
}

if($("[name=Q2]:checked").length==0){
    allObj.prop("checked", true);
}

}

$(function(){
$("[name=Q2_n]").click(function(){
    allunCheckFunc( this );
});
$("[name=Q2]").each(function(){
    $(this).click(function(){
        oneCheckFunc( $(this) );
    });
});
});