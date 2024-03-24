- [프로젝트 소개](#pub-sub)
- [와이어프레임](#와이어프레임)
- [ERD](#ERD)
- [API 명세](#API-명세)
- [브랜치 전략](#브랜치-전략)

<br>

# pub-sub
팬과 크리에이터들을 위한 월정액 기반 멤버십 플랫폼

<br>

## 와이어프레임
### 메인 화면
![main](https://github.com/f-lab-edu/pub-sub/assets/65343417/245f3afe-f979-4946-9938-f505571142dc)

### 프로필 화면
![profile](https://github.com/f-lab-edu/pub-sub/assets/65343417/948b9de5-8552-43c7-8df9-4e5b44cfff08)

<br>

## ERD
(완성되면 이미지 교체 예정)
https://www.erdcloud.com/d/brDjdwe2aGKvc33gu
![pub-sub-erd](https://github.com/f-lab-edu/pub-sub/assets/65343417/74f11dcf-2b2c-4a83-a2ee-a13be8d89d17)

<br>

## API 명세
### 로그인/회원가입
|  | 메서드 | Endpoint |
| --- | --- | --- |
| 로그인 | POST | /signin/google |
| 로그아웃 | POST | /signout |
| 회원가입 | POST | /signup |

### 유저
|  | 메서드 | Endpoint |
| --- | --- | --- |
| 프로필 조회 | GET | /{username} |
| 프로필 수정 | PATCH | /accounts/edit |
| 회원탈퇴 | DELETE | /accounts/delete |
| 구독중인 멤버십 조회 | GET | /accounts/subscriptions |

### 멤버십
|  | 메서드 | Endpoint |
| --- | --- | --- |
| 멤버십 생성 | POST | /subscriptions |
| 멤버십 구독 | POST | /subscriptions/subscribe |
| 멤버십 취소 | POST | /subscriptions/unsubscribe |

### 게시물
|  | 메서드 | Endpoint | 쿼리 파라미터 |
| --- | --- | --- | --- |
| 메인화면 게시물 조회 | GET | / |  |
| 게시물 작성 | POST | /posts |  |
| 특정 게시물 조회 | GET | /posts | postId |
| 게시물 수정 | PUT | /posts/{postId} |  |
| 게시물 삭제 | DELETE | /posts/{postId} |  |
| 게시물 좋아요 | POST | /likes/like |  |
| 게시물 좋아요 취소 | POST | /likes/remove-like |  |

### 댓글
|  | 메서드 | Endpoint |
| --- | --- | --- |
| 댓글 작성 | POST | /comments |
| 댓글 수정 | PUT | /comments/{commentId} |
| 댓글 삭제 | DELETE | /comments/{commentId} |

<br>

## 브랜치 전략
GitHub-Flow 기반

| 브랜치명 | 구분 | 설명 |
| --- | --- | --- |
| `main` | 메인 | 배포를 위한 브랜치 |
| `feature` | 보조 | 기능 단위 개발 브랜치 |
| `fix` | 보조 | 오류 해결을 위한 브랜치 |
| `docs` | 보조 | 문서 작업을 위한 브랜치 |
| `refactor` | 보조 | 리팩토링을 위한 브랜치 |
| `chore` | 보조 | 기타 작업을 위한 브랜치 |
