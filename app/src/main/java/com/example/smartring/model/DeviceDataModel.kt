package com.example.smartring.model

data class DeviceDataModel(
    val name: String, // 이름
    val version: String, // 프로토콜 버전
    val screenType: Int, // 화면 유형
    val screenWidth: Int, // 화면 너비
    val screenHeight: Int, // 화면 높이
    val p_num: Int, // 미업로드된 걸음 수 데이터 수
    val p_delta_num: Int, // 미업로드된 걸음 시간 데이터 수
    val goal: Int, // 걸음 목표
    val sex: Int, // 성별
    val height: Int, // 키
    val weight: Int, // 몸무게
    val age: Int, // 나이
    val s_num: Int, // 미업로드된 수면 데이터 수
    val s_delta_num: Int, // 미업로드된 수면 시간 데이터 수
    val start_time: String, // 시작 시간
    val end_time: String, // 종료 시간
    val SIT: Int, // 오래 앉아 있음 알림 시간
    val heart_rate: Int, // 미업로드된 심박수 데이터 수
    val alarmNum: Int, // 알람 개수
    val battery_capacity: Int, // 배터리 용량
    val bt_address: String, // 블루투스 주소
    val software_version: String, // 소프트웨어 버전
    val sim: Int, // SIM 지원 여부
    val barcode: String?, // 장치 바코드
    val project_code: String?, // 프로젝트 식별 코드
    val raise_hand_screen_bright: String, // 손 들기 화면 밝기 설정
    val screen_stays_on: Int, // 화면 항상 켜짐 설정
    val time_format: Int, // 시간 형식
    val do_not_disturb_mode: Int, // 방해 금지 모드
    val turn_on_vibration: Int, // 진동 모드
)
