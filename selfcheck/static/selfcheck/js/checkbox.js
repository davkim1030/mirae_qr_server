function allunCheckFunc( obj ) {
    q2List = document.getElementsByName("q2")
    qn = document.getElementsByName("q2_n")[0]
    q2List.forEach(e => {
        e.checked = false
        e.setAttribute("checked", "")
    })
    qn.checked = true
    qn.setAttribute("checked", "checked")
}

function oneCheckFunc( obj )
{
    var allObj = document.getElementsByName("q2_n")[0]
    var checkList = document.getElementsByName("q2")
    var checkCnt = 0

    if (!obj.checked) {
        for (let i = 0; i < checkList.length; i++)
            if (checkList[i])
                checkCnt++
        if (checkCnt === 1){
            allObj.checked = true
            allObj.setAttribute("checked", "checked")
        }
        obj.checked = false
        obj.setAttribute("checked", "")
    } else {
        if (allObj.checked){
            allObj.checked = false
            allObj.setAttribute("checked", "")
        }
        obj.checked = true
        obj.setAttribute("checked", "checked")
        console.log(obj)
    }
}

document.getElementsByName("q2_n")[0].addEventListener(
    "click", e => { allunCheckFunc(e) }
    )
document.getElementsByName("q2").forEach(e => {
        e.addEventListener("click", obj => {
            oneCheckFunc(e) })
    })
