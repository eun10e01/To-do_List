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
