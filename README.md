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
1. ターミナルを立ち上げ、\demoapi フォルダでAPIを起動する
```cmd
Small-amount_short-term_insurance>cd .\demoapi && .\gradlew bootRun
```
2. 別ターミナルを立ち上げ、\demo フォルダで画面を起動する
```cmd
Small-amount_short-term_insurance>cd .\demo && .\gradlew bootRun
```
3. [http://localhost:8080/contract/product](http://localhost:8080/contract/product)をブラウザで開く

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
