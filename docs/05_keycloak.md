
# 5. Keycloak（認証）

## 5.1 なぜ認証が必要か

- 操作ユーザーの識別  
- セキュリティ確保  
- 監査ログのため  

---

## 5.2 OIDC の概念

```mermaid
sequenceDiagram
    participant B as Browser
    participant K as Keycloak
    participant W as WebApp

    B->>K: ログイン要求
    K->>B: ID Token / Access Token
    B->>W: Token を送信
    W->>W: 認証済みユーザーとして処理
