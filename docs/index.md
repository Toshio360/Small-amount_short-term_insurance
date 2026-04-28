# Spring Boot Web アプリ開発ガイド  
保険申込フローを題材に学ぶ Spring MVC / API / 認証 / サービス連携

---

## [1. Spring MVC（Model / View / Controller）](01_mvc.md)

### [1.1 MVC とは](01_mvc.md#11MVCとは)
- Model：画面に渡すデータ
- View：Thymeleaf による HTML 表示
- Controller：リクエストを受け取り、Model を View に渡す

### 1.2 Spring MVC のリクエスト処理の流れ
1. ブラウザからリクエスト  
2. Controller が受け取る  
3. Model に値を詰める  
4. View（HTML）に渡す  
5. レンダリングして返却

### 1.3 @ModelAttribute と @SessionAttributes
- @ModelAttribute：リクエストごとのデータ
- @SessionAttributes：画面遷移をまたぐデータ保持

---

## 2. Swagger（API仕様）

### 2.1 Swagger / OpenAPI とは
- API の仕様を定義する仕組み
- API の動作確認ができる UI（Swagger UI）

### 2.2 Swagger UI の使い方
- API の一覧を確認
- リクエストパラメータの確認
- 実際に API を叩いてレスポンスを確認

### 2.3 本システムで利用する API
- Product API  
- Plan API  
- Application API  

---

## 3. 画面（Thymeleaf）

### 3.1 画面構成
- トップページ
- 契約者入力
- 被保険者入力
- 確認画面
- 完了画面

### 3.2 Thymeleaf の基本構文
- `th:text`
- `th:each`
- `th:if`
- `th:field`

### 3.3 フォーム送信の流れ
1. HTML フォーム入力  
2. Controller が DTO を受け取る  
3. Model に詰めて次画面へ  
4. @SessionAttributes で値を保持

---

## 4. API サーバ（Product / Plan / Application）

### 4.1 API サーバの役割
- 画面からのリクエストを受けてデータを返す
- Product / Plan のマスタ提供
- Application の登録処理

### 4.2 API の構造
- Controller（REST）
- Service（業務ロジック）
- Repository（DBアクセス）

### 4.3 OpenAPI 生成クライアントの利用
- DefaultApi
- ApiClient
- RestTemplate の差し替え（@LoadBalanced）

---

## 5. Keycloak（認証）

### 5.1 なぜ認証が必要か
- 操作ユーザーの識別
- セキュリティ確保

### 5.2 OIDC の概念
- ID Token
- UserInfo
- Claims

### 5.3 Spring Security による認証
- SecurityConfig
- CustomOidcUser
- Controller でのユーザー取得

---

## 6. Eureka（サービスディスカバリ）

### 6.1 サービスディスカバリとは
- API サーバの場所を自動で見つける仕組み
- 外部ロードバランサ不要

### 6.2 Eureka の仕組み
- Register
- Heartbeat
- Fetch Registry

### 6.3 Spring Cloud LoadBalancer
- サービス名で API を呼び出す
- @LoadBalanced RestTemplate

---

## 7. Resilience4j（サーキットブレーカー）

### 7.1 なぜ必要か
- API 障害時の graceful degradation
- 障害連鎖の防止

### 7.2 サーキットブレーカーの状態
- Closed
- Open
- Half-Open

### 7.3 実装
- @CircuitBreaker
- fallback メソッド
- application.yml の設定

---

## 8. 動作確認

### 8.1 起動順序
1. Keycloak  
2. Eureka  
3. API サーバ  
4. Web アプリ  

### 8.2 動作確認項目
- ログインできるか
- Product / Plan が取得できるか
- 入力 → 確認 → 完了の流れが動くか
- Application が登録されるか
- サーキットブレーカーが動作するか

---

## 9. まとめ
- MVC → API → 認証 → サービス連携 → 信頼性  
  の流れで Spring の主要機能を学べる構成
- 実装しながら理解できる教材として最適化

