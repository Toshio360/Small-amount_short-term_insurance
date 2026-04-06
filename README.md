### Spring DIコンテナ学習用のdemo
・DIコンテナ学習用のdemo画面です<br>
・APIクライアントをOpenAPIのYamlからJavaを自動生成しています</br>
swaggerプロジェクトはYamlを置くだけのプロジェクトで、model側のbuild.gradleにOpenApi Generatorでjavaを自動生成する設定をしています
## フォルダ構成
├demo\ ←Spring Bootのdemoプロジェクト</br>
├swagger\ ←OpenAPI形式のAPI定義</br>
└README.md←このファイル</br>
## 画面起動方法
1. ターミナルを立ち上げ、\demoapi フォルダでAPIを起動する
```batch
Small-amount_short-term_insurance>cd .\demoapi && .\gradlew bootRun
```
2. 別ターミナルを立ち上げ、\demo フォルダで画面を起動する
```batch
Small-amount_short-term_insurance>cd .\demo && .\gradlew bootRun
```
3. [http://localhost:8080/contract/product](http://localhost:8080/contract/product)をブラウザで開く

## API定義をHtmlで出力する方法
```batch
cd .\swagger && .\gradlew openApiGenerate
```
上記コマンドの実行でbuild\generated-docs\index.htmlにAPI定義が出力されます。

## API mockの起動方法
1. swaggerプロジェクトフォルダでノードをインストールする
```batch
cd .\swagger && winget install OpenJS.NodeJS.LTS
```
2. Stoplight Prismをインストールする
```batch
npm install --save-dev @stoplight/prism-cli
```
3. prism mockをポート8089で起動する
```batch
npx prism mock src/main/resources/static/openapi/contract-api.yaml --port 8089 --host 0.0.0.0
```