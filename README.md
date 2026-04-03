### Spring DIコンテナ学習用のdemo
1. swaggerプロジェクトフォルダでノードをインストールする
```batch
cd .\swagger && winget install OpenJS.NodeJS.LTS
```
2. Stoplight Studioをインストールする
```batch
npm install --save-dev @stoplight
```
3. prism mock をポート8089で起動する
```batch
npx prism mock src/main/resources/static/openapi/contract-api.yaml --port 8089 --hostname 0.0.0.0
```
4. demoプロジェクトフォルダで画面を起動する
```batch
.\gradlew bootRun
```
5. [http://localhost:8080/contract/product](http://localhost:8080/contract/product)をブラウザで開く
