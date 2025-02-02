// 언어별 JSON 파일 (간략화)
const lang = {
    en: {
        title:
            "Survey Questionnaire on the Self-diagnosis of Students' Health Conditions",
        statement_0:
            "This questionnaire will assess the health conditions of students to prevent COVID-19.",
        statement_1: "Please answer the survey honestly.",
        warnimg:
            "If you lie or falsely answer the questionnaire, according to law regulating pandemic and virus article 18 clause 3 and article 79 clause 1, you may face legal penalties. Also according to Yonsei University rules regarding students penalty article 5 clause 4, you may face heavy disciplinary measures.",
        Q1: "Does the student have fever?",
        A1_0: "Less than 37.5℃",
        A1_1: "More than 38℃ or feeling feverish",
        Q2:
            "Does the student show any symptoms suspected to be those of COVID-19? (Select all that apply)",
        A2_0: "Cough",
        A2_1: "Shortness of breath",
        A2_2: "Chills",
        A2_3: "Muscle pain",
        A2_4: "Headache",
        A2_5: "Sore throat",
        A2_6: "Loss of taste and smell",
        Q3:
            "Has the student traveled abroad recently (within the last 14 days)?",
        Q4:
            "Has any of your cohabiting family members traveled abroad recently (within the last 14 days)?",
        Q5:
            "Are there any cohabiting family members who are in self-quarantine?",
        A_Y: "Yes",
        A_N: "No",
        Num: "Num",
        QnA: "Question & Answer",
        Done: "Done",
        pos_title: "<Access QR Code>",
        pos: "As a result of the self-diagnosis questionnaire for the prevention of COVID-19, there are no items corresponding to the suspected symptoms. It's possible to attend school.",
        neg_title: "<Suspension Notice for On-campus Classes>",
        neg1: "1. Since your child currently requires protection inside your home due to his/her health condition, please prohibit him/her from returning to school until further notice for the healthy school life of our children.",
        neg2: "2. Even if the child does not return to school, he/she will be considered to have attended. Please provide protection to ensure that the student observes the precautions at home and takes sufficient rest until his/her fever and respiratory symptoms are gone (cough, sore throat, etc.).",
        neg3: "3. If high fever of above 38℃ persists, or if symptoms worsen, please contact the call center (☏ 1339, area code +120) or the relevant local health center and follow the instructions such as visiting a screening center and receiving medical treatment."
    },
    ko: {
        title: "학생건강 자가진단",
        statement_0:
            "이 설문지는 코로나19 감염예방을 위한 학생의 건강 상태를 확인하는 내용입니다.",
        statement_1: "설문에 성실하게 응답하여 주시기 바랍니다.",
        warnimg:
            "만약 답변을 허위로 작성할 경우 [감염병의 예방 및 관리에 관한 법률] 제18조 3항 및 제79조 1항에 의하여 처벌 받을 수 있으며, [연세대학교 미래캠퍼스 학생상벌에 관한 시행세칙] 제5조 4항에 의거하여 중징계를 받을 수 있음을 알려드립니다.",
        Q1: "몸에 열이 있나요?",
        A1_0: "37.5℃ 미만",
        A1_1: "37.5℃ 이상 및 발열감",
        Q2:
            "코로나19가 의심되는 증상이 있나요? (해당사항 모두 선택) (단, 기저질환 등으로 코로나19와 관계없이 평소에 다음 증상이 계속되는 경우는 제외)",
        A2_0: "기침",
        A2_1: "호흡곤란",
        A2_2: "오한",
        A2_3: "근육통",
        A2_4: "두통",
        A2_5: "인후통",
        A2_6: "후각, 미각 소실",
        Q3: "학생이 최근(14일 이내)해외여행을 다녀온 사실이 있나요?",
        Q4:
            "동거가족 중 최근(14일 이내) 해외여행을 다녀온 사실이 있나요? (단, 국제선 항공기 및 선박 승무원등 직업특성상 매번 해외 입출국하고 의심증상이 없는 경우는 제외)",
        Q5: "동거가족 중 현재 자가격리 중인 가족이 있나요?",
        A_Y: "예",
        A_N: "아니오",
        Num: "문항",
        QnA: "질문 및 응답",
        Done: "완료",
        pos_title: "<출입용 QR 코드>",
        pos: "코로나19 예방을 위한 자가진단 설문결과 의심 증상에 해당되는 항목이 없어 등교가 가능함을 안내드립니다.",
        neg_title: "<등교 중지 안내문>",
        neg1: "1. 현재 건강상태는 가정 내에서 자가경리가 필요한 상황이므로 건강한 학교생활을 위해 잠시 등교하지 않도록 협조하여 주시기 바랍니다.",
        neg2: "2. 등교하지 않아도 학교에서는 출석으로 인정합니다. 학생의 발열, 호흡기증상(기침, 목아픔 등)이 없어질 때까지 가정에서 예방수칙을 준수하고 충분한 휴식을 취할 수 있도록 보호해 주시기 바랍니다.",
        neg3: "3. 38℃이상의 고열이 지속되거나 증상이 심해질 경우 교내콜센터(☏ 033-760-0119) 또는 건강관리센터를 방문 후 진료받기 등 안내에 따라 주시기 바랍니다."
    },
    zh: {
        title: "学生健康状态居家诊断问卷作答内容",
        statement_0:
            "该问卷是为了预防新型冠状病毒肺炎而对学生健康状态进行确认的内容，",
        statement_1: "请诚实作答。",
        warnimg:
            "虚报问卷者按照【感染病预防管理法律】第18条3项和第79条1项被处分，依据【延世大学MIRAE校区学生奖罚规定】第5条4项，会被从重处罚。",
        Q1: "学生身体是否发热？",
        A1_0: "不到37.5℃",
        A1_1: "38℃以上或有发热感",
        Q2: "学生是否有新型冠状病毒肺炎疑似症状？",
        A2_0: "咳嗽",
        A2_1: "呼吸困难",
        A2_2: "发冷",
        A2_3: "肌肉疼痛",
        A2_4: "头痛",
        A2_5: "咽喉痛",
        A2_6: "味觉、嗅觉麻痹",
        Q3: "学生最近（14天以内）是否有海外旅行事实？",
        Q4: "共同居住的家人中，最近（14天以内）是否有海外旅行事实？",
        Q5: "共同居住的家人中有居家隔离者吗？",
        A_Y: "有",
        A_N: "没有",
        Num: "数",
        QnA: "提问及回答",
        Done: "完",
        pos_title: "<访问代码>",
        pos: "为预防新型冠状病毒肺炎而实施的居家诊断问卷结果显示，无疑似症状相应项目，因此可返校，特此通知。",
        neg_title: "<停止返校通知>",
        neg1: "1. 目前，子女的健康状态处于需要在家进行保护的状况，为确保孩子们的健康学校生活，请暂时不要返校，敬请配合。",
        neg2: "2. 即使不返校，学校也将认可为出勤。截止到学生的发热、呼吸系统症状（咳嗽、喉咙痛等）消失时为止，请对孩子予以保护，以使其在家遵守预防守则并进行充分休息。",
        neg3: "3. 持续38℃以上高烧或症状变严重时，请咨询呼叫中心（☏1339、区号+120）或辖区内保健所，并到访筛查诊疗所后遵从接受诊疗等指示。"
    }
};

// ** 현재 브라우저의 언어 가져오기 **
function getLanguage() {
    if (getCookie("lang") != null)
        return getCookie("lang")
    return navigator.language || navigator.userLanguage;
}

// 언어별 적용
function init(localeStr) {
    document.getElementById("locale").textContent = localeStr;
}

// 초기 작업
const currentLang = getLanguage();
// init(currentLang);
render(currentLang.substr(0, 2));

// 언어별 렌더링
function render(locale) {
    const $lang = document.querySelectorAll("[data-lang]");
    $lang.forEach((el) => {
        const code = el.dataset.lang;
        el.textContent = lang[locale][code];
    });
}

var setCookie = function (name, value, exp) {
    var date = new Date();
    date.setTime(date.getTime() + exp * 24 * 60 * 60 * 1000);
    document.cookie = name + '=' + value + ';expires=' + date.toUTCString() + ';path=/';
};

// 버튼 이벤트
document.getElementById("btn-en").addEventListener("click", (e) => {
    render("en");
    setCookie("lang", "en", 1);
});
document.getElementById("btn-ko").addEventListener("click", (e) => {
    render("ko");
    setCookie("lang", "ko", 1);
});
document.getElementById("btn-zh").addEventListener("click", (e) => {
    render("zh");
    setCookie("lang", "zh", 1);
});

function getCookie(cookieName){
    var cookieValue=null;
    if(document.cookie){
        var array=document.cookie.split((escape(cookieName)+'='));
        if(array.length >= 2){
            var arraySub=array[1].split(';');
            cookieValue=unescape(arraySub[0]);
        }
    }
    return cookieValue;
}

function deleteCookie(cookieName){
    var temp=getCookie(cookieName);
    if(temp){
        setCookie(cookieName,temp,(new Date(1)));
    }
}
