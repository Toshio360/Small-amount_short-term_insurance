### Spring DIコンテナ学習用のdemo
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
4. 別のターミナルを開き、demoプロジェクトフォルダで画面を起動する
```batch
cd .\demo && .\gradlew bootRun
```
5. [http://localhost:8080/contract/product](http://localhost:8080/contract/product)をブラウザで開く
