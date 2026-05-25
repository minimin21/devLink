# DevLink DB 스키마 요약

설계 문서는 Oracle 표기(`varchar2`, `number`, `date`) 기준이었습니다.  
실제 프로젝트는 **MariaDB**를 사용하므로, DDL 작성 시 아래 **MariaDB 타입 예시**를 참고하면 됩니다.

| 설계(Oracle) | MariaDB 예시 |
|--------------|----------------|
| varchar2(n) | VARCHAR(n) |
| number(p) / number(1) | INT / TINYINT |
| date | DATE 또는 DATETIME |
| char(1) | CHAR(1) |

---

## 1. 회원 로그인 `tbl_login`

| 컬럼 | 논리명 | 타입(설계) | PK | NULL | 비고 |
|------|--------|-------------|----|------|------|
| member_id | 회원번호 | varchar2(10) | ○ | | 예: `m000000` |
| login_id | 로그인 ID | varchar2(20) | | | |
| password | 비밀번호 | varchar2(128) | | | 해시 저장 권장 |
| member_type | 회원 구분 | number(1) | | | 1: 개인, 2: 기업, 3: 관리자 |
| status | 상태 | number(1) | | | 1: 활동, 2: 탈퇴, 3: 휴면 |
| reg_date | 가입일 | date | | | yyyy/MM/dd |
| update_date | 수정일 | date | | ○ | |
| del_date | 탈퇴일 | date | | ○ | |

---

## 2. 개인 프로필 `tbl_personal`

| 컬럼 | 논리명 | 타입(설계) | PK | NULL | 비고 |
|------|--------|-------------|----|------|------|
| personal_id | 개인번호 | varchar2(10) | ○ | | 예: `per000000` |
| member_id | 회원번호 | varchar2(10) | | | FK → `tbl_login.member_id` |
| name | 이름 | varchar2(20) | | | |
| gender | 성별 | number(1) | | | 1: 남, 2: 여 |
| education | 학력 | number(1) | | | 1~5 (중이하/고등/대학/전문대/대학원 등 설계 기준) |
| school_name | 학교명 | varchar2(30) | | | |
| phone_number | 연락처 | varchar2(11) | | | 형식: `nnn-nnnn-nnnn` |
| address | 주소 | varchar2(512) | | | |
| birth_date | 생년월일 | date | | | |
| email | 이메일 | varchar2(255) | | | |
| git_url | GitHub URL | varchar2(1024) | | ○ | |
| job_status | 구직 상태 | number(1) | | | 1: 구직 중, 2: 재직, 3: 이직 고려 등 |
| career | 경력 | number(1) | | | 1~5 (무경력/0~3년/3년 이상 등 설계 기준) |
| profile_image_path | 프로필 이미지 경로 | varchar2(2048) | | ○ | |
| update_date | 수정일 | date | | ○ | |
| introduction | 자기소개 | varchar2(4000) | | | 10~2000자 등 앱 검증 |

---

## 3. 기업 정보 `tbl_company`

| 컬럼 | 논리명 | 타입(설계) | PK | NULL | 비고 |
|------|--------|-------------|----|------|------|
| company_id | 기업번호 | varchar2(10) | ○ | | 예: `cor000000` |
| member_id | 회원번호 | varchar2(10) | | | FK → `tbl_login.member_id` |
| email | 이메일 | varchar2(255) | | | |
| company_name | 기업명 | varchar2(50) | | | |
| business_number | 사업자등록번호 | varchar2(15) | | | |
| address | 주소 | varchar2(512) | | | |
| company_url | 홈페이지 | varchar2(1024) | | ○ | |
| founded_date | 설립일 | date | | | |
| phone_number | 전화 | varchar2(11) | | | |
| logo_image_path | 로고 경로 | varchar2(2048) | | ○ | |
| introduction | 기업 소개 | varchar2(4000) | | | 10~2000자 등 앱 검증 |

---

## 4. 스킬 마스터 `tbl_skill`

| 컬럼 | 논리명 | 타입(설계) | PK | 비고 |
|------|--------|-------------|-----|------|
| skill_id | 기술번호 | varchar2(10) | ○ | 예: `sk000000` |
| skill_name | 기술명 | varchar2(50) | | |
| category | 카테고리 | number(1) | | 1: 직종(예: FE/BE), 2: 스킬(예: Java, HTML) |

---

## 5. 개인–스킬 매핑 `tbl_personal_skill`

| 컬럼 | 타입(설계) | PK | 비고 |
|------|-------------|-----|------|
| personal_skill_id | varchar2(10) | ○ | 예: `ps00000` |
| personal_id | varchar2(10) | | FK → `tbl_personal` |
| skill_id | varchar2(10) | | FK → `tbl_skill` |

---

## 6. 프로젝트 모집 공고 `tbl_project_recruit`

| 컬럼 | 타입(설계) | PK | NULL | 비고 |
|------|-------------|-----|------|------|
| project_post_id | varchar2(10) | ○ | | 예: `p000000` |
| member_id | varchar2(10) | | | FK → `tbl_login` |
| project_name | varchar2(100) | | | |
| content | varchar2(4000) | | | 10~2000자 등 |
| salary | number(5) | | | |
| closing_date | date | | | 마감 |
| start_date | date | | | 개발 시작 |
| end_date | date | | | 개발 종료 |
| recruit_count | number(3) | | | 모집 인원 |
| work_type | number(1) | | | 1: 사무실, 2: 전면 원격, 3: 부분 원격, 4: 기타 |
| work_start_hour | CHAR(5) | | | `hh:mm` |
| work_over_hour | CHAR(5) | | | `hh:mm` |
| overtime_hours | number(3) | | | 1: 없음, 2: 0~10h, 3: 10~20h, 4: 20h+ |
| required_experience | number(1) | | | 1~5 (무관/0~3년/3년+/5년+/10년+ 등) |
| work_location | varchar2(20) | | | |
| file_path | varchar2(2048) | | ○ | |
| reg_date | date | | | |
| update_date | date | | ○ | |
| view_count | number(10) | | ○ | |
| status | number(1) | | | 1: 모집 중, 2: 종료 |
| delete_or | char(1) | | | 기본 `N`, `Y`: 삭제 |

---

## 7. 기업 채용 공고 `tbl_company_recruit`

| 컬럼 | 타입(설계) | PK | NULL | 비고 |
|------|-------------|-----|------|------|
| company_post_id | varchar2(10) | ○ | | 예: `c000000` |
| member_id | varchar2(10) | | | FK → `tbl_login` |
| company_description | varchar2(4000) | | | 10~2000자 등 |
| salary | number(5) | | | |
| closing_date | date | | | |
| expected_join_date | date | | | 입사 예정일 |
| recruitment_count | number(3) | | | |
| work_type | number(1) | | | 프로젝트 공고와 동일 코드 체계 |
| work_start_hour | CHAR(5) | | | |
| work_over_hour | CHAR(5) | | | |
| overtime_hours | CHAR(5) | | | 설계서에 숫자 코드(1~4) 병기 — 구현 시 통일 필요 |
| required_experience | number(1) | | | |
| work_location | varchar2(20) | | | |
| file_path | varchar2(2048) | | ○ | |
| reg_date | date | | | |
| update_date | date | | ○ | |
| view_count | number(10) | | ○ | |
| status | number(1) | | | 1: 모집 중, 2: 종료 |
| delete_or | char(1) | | | 기본 `N` |

> **주의:** `overtime_hours` 타입이 설계서마다 `number` vs `CHAR`로 다를 수 있음. DDL 확정 시 한쪽으로 맞출 것.

---

## 8. 프로젝트 공고–스킬 `tbl_project_post_skill` (추정명)

| 컬럼 | 타입(설계) | PK | 비고 |
|------|-------------|-----|------|
| project_post_skill_id | varchar2(10) | ○ | 예: `psk000000` |
| project_post_id | varchar2(10) | | FK → 프로젝트 공고 |
| skill_id | varchar2(10) | | FK → `tbl_skill` |

---

## 9. 기업 공고–스킬 `tbl_company_post_skill` (추정명)

| 컬럼 | 타입(설계) | PK | 비고 |
|------|-------------|-----|------|
| company_post_skill_id | varchar2(10) | ○ | 예: `csk000000` |
| company_post_id | varchar2(10) | | FK → 기업 공고 |
| skill_id | varchar2(10) | | FK → `tbl_skill` |

---

## 10. 프로젝트 지원

| 컬럼 | 타입(설계) | PK | NULL | 비고 |
|------|-------------|-----|------|------|
| application_id | varchar2(10) | ○ | | 예: `pa000000` |
| project_post_id | varchar2(10) | | | FK → 프로젝트 공고 |
| member_id | varchar2(10) | | | FK → `tbl_login` |
| status | number(1) | | | 1: 신청 중, 2: 승인, 3: 반려, 4: 취소 |
| status_update_date | date | | ○ | |
| applied_date | date | | | |

---

## 11. 기업 공고 지원

| 컬럼 | 타입(설계) | PK | NULL | 비고 |
|------|-------------|-----|------|------|
| application_id | varchar2(10) | ○ | | 예: `ca000000` |
| company_post_id | varchar2(10) | | | FK → 기업 공고 |
| member_id | varchar2(10) | | | FK → `tbl_login` |
| status | number(1) | | | 1: 검토 중, 2: 면접, 3: 내정, 4: 불합격, 5: 취소 |
| status_update_date | date | | ○ | |
| applied_date | date | | | |

---

## 12. 개인 프로젝트 경력 `tbl_project_history`

| 컬럼 | 타입(설계) | PK | NULL | 비고 |
|------|-------------|-----|------|------|
| history_id | varchar2(10) | ○ | | 예: `ph000000` |
| personal_id | varchar2(10) | | | FK → `tbl_personal` |
| project_name | varchar2(100) | | | |
| affiliation | number(1) | | | 1: 개인, 2: 기업 |
| affiliated_company_name | varchar2(40) | | ○ | |
| start_date | date | | | |
| end_date | date | | | |
| role | varchar2(20) | | ○ | |
| project_description | varchar2(4000) | | | 10~2000자 등 |
| git_url | varchar2(2048) | | ○ | |
| reg_date | date | | | |
| update_date | date | | ○ | |

---

## 13. 경력–스킬 `tbl_project_history_skill` (추정명)

| 컬럼 | 타입(설계) | PK | 비고 |
|------|-------------|-----|------|
| project_history_skill_id | varchar2(12) | ○ | 예: `phs000000` — **길이 12** (다른 ID는 10자인 경우가 많음) |
| history_id | varchar2(10) | | FK → `tbl_project_history` |
| skill_id | varchar2(10) | | FK → `tbl_skill` |

---

## 14. 자격증 `tbl_certification` (추정명)

| 컬럼 | 타입(설계) | PK | NULL | 비고 |
|------|-------------|-----|------|------|
| certification_id | varchar2(10) | ○ | | 예: `cer000000` |
| personal_id | varchar2(10) | | | FK → `tbl_personal` |
| certification_name | varchar2(100) | | | UI 50자 이내 권장 |
| acquired_organization | varchar2(70) | | | UI 30자 이내 권장 |
| acquired_date | date | | | |
| update_date | date | | ○ | |

---

## 15. 쪽지/DM `tbl_message` (추정명)

| 컬럼 | 타입(설계) | PK | NULL | 기본 | 비고 |
|------|-------------|-----|------|------|------|
| message_id | varchar2(10) | ○ | | | 예: `msg000000` |
| sender_id | varchar2(10) | | | | FK → `tbl_login.member_id` |
| receiver_id | varchar2(10) | | | | FK → `tbl_login.member_id` |
| title | varchar2(100) | | | | UI 50자 이내 권장 |
| content | varchar2(1000) | | | | UI 500자 이내 권장 |
| read_or | char(1) | | | `N` | N: 미읽음, Y: 읽음 |
| read_at | date | | ○ | | 읽은 시각 |
| send_at | date | | | | 발송 시각 |
| delete_or | char(1) | | | `N` | 소프트 삭제 |

---

## 16. 광고 `tbl_ad` (추정명)

| 컬럼 | 타입(설계) | PK | NULL | 비고 |
|------|-------------|-----|------|------|
| ad_id | varchar2(10) | ○ | | 예: `ad000000` |
| img_url | varchar2(2048) | | NOT NULL | |
| link_url | varchar2(2048) | | ○ | |
| reg_date | date | | | |

---

## 17. 커뮤니티 게시글 `tbl_community_post` (추정명)

| 컬럼 | 타입(설계) | PK | NULL | 기본 | 비고 |
|------|-------------|-----|------|------|------|
| community_post_id | varchar2(10) | ○ | | | 예: `C000001` |
| member_id | varchar2(10) | | | | |
| category | number(1) | | | | 1: 잡담, 2: 정보, 3: 후기, 4: Q&A, 5: 기업 PR |
| title | varchar2(100) | | | | UI 50자 이내 |
| content | varchar2(4000) | | | | 10~2000자 |
| hit | number(10) | | ○ | | 조회수 |
| file_path | varchar2(2048) | | ○ | | |
| notice_yn | char(1) | | | `N` | Y: 공지 |
| reg_date | date | | | | |
| updated_at | date | | ○ | | |
| del_yn | char(1) | | | `N` | 소프트 삭제 |

---

## 18. 커뮤니티 댓글 `tbl_reply` (추정명)

| 컬럼 | 타입(설계) | PK | NULL | 기본 | 비고 |
|------|-------------|-----|------|------|------|
| reply_id | varchar2(10) | ○ | | | 예: `rep000000` |
| community_post_id | varchar2(10) | | | | FK → 게시글 테이블 (팀에서 테이블명 확정) |
| member_id | varchar2(10) | | | | FK → `tbl_login` |
| content | varchar2(1000) | | | | 1~500자 |
| reg_date | date | | | | |
| updated_at | date | | ○ | | |
| delete_or | char(1) | | | `N` | |

---

## 변경 시 이 파일도 함께 수정

스키마를 바꾸면 **이 문서와 실제 DDL을 같이** 맞추면, `git pull` 후 Cursor에서 `@docs/db-schema.md`만 참조해도 됩니다.
