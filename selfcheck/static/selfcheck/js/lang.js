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
    A2_1: "Shortness of breatht",
    A2_2: "Chills",
    A2_3: "Muscle pain",
    A2_4: "Headache",
    A2_5: "Sore throa",
    A2_6: "Loss of taste and smell",
    Q3: "Has the student traveled abroad recently (within the last 14 days)?",
    Q4:
      "Has any of your cohabiting family members traveled abroad recently (within the last 14 days)?",
    Q5: "Are there any cohabiting family members who are in self-quarantine?",
    A_Y: "Yes",
    A_N: "No",
    Num: "Num",
    QnA: "Question & Answer",
    Done: "Done",
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
  },
};

// ** 현재 브라우저의 언어 가져오기 **
function getLanguage() {
  return navigator.language || navigator.userLanguage;
}

// 언어별 적용
function init(localeStr) {
  document.getElementById("locale").textContent = localeStr;
}

// 초기 작업
const currentLang = getLanguage();
init(currentLang);
render(currentLang.substr(0, 2));

// 언어별 렌더링
function render(locale) {
  const $lang = document.querySelectorAll("[data-lang]");
  $lang.forEach((el) => {
    const code = el.dataset.lang;
    el.textContent = lang[locale][code];
  });
}

// 버튼 이벤트
document.getElementById("btn-en").addEventListener("click", (e) => {
  render("en");
});
document.getElementById("btn-ko").addEventListener("click", (e) => {
  render("ko");
});
document.getElementById("btn-zh").addEventListener("click", (e) => {
  render("zh");
});
