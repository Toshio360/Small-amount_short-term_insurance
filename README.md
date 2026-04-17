### Spring DIコンテナ学習用のdemo
・DIコンテナ学習用のdemo画面です<br>
・APIクライアントをOpenAPIのYamlからJavaを自動生成しています</br>
swaggerプロジェクトはYamlを置くだけのプロジェクトで、model側のbuild.gradleにOpenApi Generatorでjavaを自動生成する設定をしています

## フォルダ構成
├demo\ ←Spring Bootのdemoプロジェクト</br>
├demoapi\ ←Spring Bootのdemoapiプロジェクト</br>
├swagger\ ←OpenAPI形式のAPI定義</br>
└README.md←このファイル</br>
## 画面起動方法
1. keycloakを起動する
```cmd
bin\kc.bat start-dev
```
2. realmとclientの設定をしていない場合は設定をする、my-realmにユーザーを登録する<br>
    realm名`my-realm` client名`my-client`　Username`my-realm-user`
3. ターミナルを立ち上げ、\demoapi フォルダでAPIを起動する
```cmd
Small-amount_short-term_insurance>cd .\demoapi && .\gradlew bootRun
```
4. 別ターミナルを立ち上げ、\demo フォルダで画面を起動する
```cmd
Small-amount_short-term_insurance>cd .\demo && .\gradlew bootRun
```
5. 開発用初期データを投入する
```sql
insert into operator_user values (1,'大橋オペレータ','b@c.d','my-realm-user');

INSERT INTO product (product_id, name, description) VALUES
('P001', '入院安心ミニ医療保険', '入院・手術を中心にカバーする少額短期医療保険'),
('P002', 'がん治療サポート保険', 'がん診断後の治療費を重点的に補償'),
('P003', '日常ケガの保険', '日常生活でのケガを幅広く補償'),
('P004', 'ペット医療保険（犬・猫）', 'ペットの通院・入院・手術を補償する保険'),
('P005', '自転車事故補償保険', '自転車事故による賠償責任とケガを補償');

INSERT INTO plan (plan_id, name, coverage_amount, base_premium) VALUES
('PL01', 'ライトプラン',        500000,   800),
('PL02', 'スタンダードプラン', 1000000,  1500),
('PL03', 'プレミアムプラン',   3000000,  3000),
('PL04', 'ペット通院プラン',    200000,  1200),
('PL05', '自転車賠償プラン',   10000000, 900);

INSERT INTO product_plan (product_id, plan_id) VALUES
('P001', 'PL01'),
('P001', 'PL02'),
('P001', 'PL03'),

('P002', 'PL02'),
('P002', 'PL03'),

('P003', 'PL01'),
('P003', 'PL02'),

('P004', 'PL04'),

('P005', 'PL05');
```
5. [http://localhost:8088/](http://localhost:8088/)をブラウザで開く

## API定義をHtmlで出力する方法
```cmd
cd .\swagger && .\gradlew openApiGenerate
```
上記コマンドの実行でbuild\generated-docs\index.htmlにAPI定義が出力されます。

## API mockの起動方法
1. swaggerプロジェクトフォルダでノードをインストールする
```cmd
cd .\swagger && winget install OpenJS.NodeJS.LTS
```
2. Stoplight Prismをインストールする
```cmd
npm install --save-dev @stoplight/prism-cli
```
3. prism mockをポート8089で起動する
```cmd
npx prism mock src/main/resources/static/openapi/contract-api.yaml --port 8089 --host 0.0.0.0
```

## 🧩 共通レイアウトの使い方

本プロジェクトでは Thymeleaf の標準レイアウト機能を使用しています。

### レイアウトの基本構造
- `common/layout.html` に共通レイアウトを定義
- 各画面は以下のようにレイアウトを継承します：

```html
<html th:replace="~{common/layout :: navbar}">
    <div th:fragment="content">
        <!-- 画面固有の内容 -->
    </div>
</html>
```
- 各画面の th:fragment="content" を宣言した要素で`common/layout.html` の下記箇所のdiv要素が置き換わります。
```html
    <div class="container" th:repLace="~{::content}">
        ここがコンテンツエリアです
    </div>
```
