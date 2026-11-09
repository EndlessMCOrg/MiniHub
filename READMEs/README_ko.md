# MiniHub - Velocity 플러그인

> **AI 생성 선언**: 이 프로젝트는 AI 지원으로 생성되었으며, 코드는 정상적인 사용, 참고 및 학습 목적으로만 사용하십시오. 다시 AI 훈련에 사용하지 마십시오!

구성된 명령을 통해 플레이어가 지정된 서버로 텔레포트할 수 있는 간단한 Velocity 프록시 서버 플러그인입니다.

## 기능 특성

- 🚀 간단한 명령으로 지정된 서버로 텔레포트. 한국어 명령을 사용할 수 있습니다!

## 설치

1. 최신 MiniHub jar 파일 다운로드
2. jar 파일을 Velocity의 `plugins` 디렉토리에 배치
3. Velocity 서버 재시작
4. 플러그인이 자동으로 구성 파일과 언어 파일을 생성합니다

## 구성

구성 파일은 `plugins/MiniHub/config.yml`에 위치합니다:

```yaml
# MiniHub 플러그인 구성 파일

# 사용할 언어
language: "ko_kr"

# 서버 명령 구성
# 형식: 서버 이름 -> 명령 목록
servers:
  lobby:
    commands: ["hub", "lobby"]
    # /hub 또는 /lobby를 사용하여 로비 서버로 텔레포트
  survival:
    commands: ["survival", "srv"]
  creative:
    commands: ["creative", "crea"]
```

## 명령 사용법

구성 설정에 따라 플레이어는 해당 명령을 사용하여 대상 서버로 텔레포트할 수 있습니다:

- `/hub` 또는 `/lobby` - 로비 서버로 텔레포트
- `/survival` 또는 `/srv` - 생존 서버로 텔레포트  
- `/creative` 또는 `/crea` - 창조 서버로 텔레포트

## 개발 정보

- **개발 언어**: Java 17
- **빌드 도구**: Maven
- **의존성 관리**: Maven
- **지원 플랫폼**: Velocity Proxy

## 변경 로그

### v1.1-SNAPSHOT
- EndlessMC Organization으로 이전(패키지, 작성자, 저장소 업데이트)
- 라이선스를 MIT로 변경
- 사용하지 않는 코드와 언어 키 제거
- `.gitignore` 추가

### v1.0-SNAPSHOT
- 초기 버전 릴리스
- 기본 명령 텔레포트 기능
- 다국어 지원
- 구성 파일 시스템

## 라이선스

이 프로젝트는 MIT 라이선스에 따라 배포됩니다. 자세한 내용은 [LICENSE](../LICENSE)를 참조하십시오.