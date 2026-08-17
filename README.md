[Figma](https://www.figma.com/design/D7hAqYKLQ8PhVHVMllPtq9/TO-DO-LIST?node-id=0-1&p=f&t=cTnKWksFIh6Rt37n-0)

## 파일 설명
- MainActivity.kt: 앱의 시작점(NavGraph 호출)
- navigation
  - AppNavigation.kt: 화면 이동 관리
- pages
  - auth: 인증 영역
    - LoginScreen.kt: 로그인 화면
    - SignUpScreen.kt: 회원가입 화면
  - main: 메인 영역
    - MainFrameScreen.kt: 밑의 네비게이션 탭 관리
    - home
      - HomeScreen.kt: 탭1-홈 화면
      - ScheduleEditScreen.kt: 하위-일정 추가/수정 화면
    - calendar
      - CalendarScreen.kt: 탭2-달력 화면
    - mypage
      - MyPageScreen.kt: 탭3-마이페이지 화면
      - ChangeNicknameScreen.kt: 하위-닉네임 변경 화면
      - ChangePasswordScreen.kt: 하위-비밀번호 변경 화면
    - notification
      - TodoAlarmReceiver.kt: 예약된 시간이 됐으니 이제 알림을 보내라고 NotificationHelper.kt에게 전달하는 역할(알림을 만드는 곳은 x)
     
※ ___Screen.kt 안에서 UI + 기능 한번에 구현할 수 있지만, 만약 코드가 너무 길어진다면 화면 역할과 기능 역할을 분리해서 각각 만들어도 상관 없습니다.<br>
※ 단, 기능 역할 분리할 시 ___ViewModel.kt로 파일 이름 통일해주세요!<br>
ex) LoginScreen.kt: 화면 보여주고 사용자 입력을 받는 역할, LoginViewModel.kt: 서버와 통신하고, 아이디/비번 검증 등 복잡한 기능 담당
