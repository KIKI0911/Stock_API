# Stock_API

保有する株式を管理するAPI

## API仕様書
[Stock_API仕様書はこちら](https://kiki0911.github.io/Stock_API/)

## URL設計
|HTTPメソッド|URL|処理内容|
|---|---|---|
|GET|/stocks|情報取得|
|POST|/stocks|情報登録|
|PATCH|/stocks/{symbol}|情報更新|
|DELETE|/stocks/{symbol}|情報削除|

## データベース定義
|カラム名(論理名)|カラム名（物理名）|型・桁|
|---|---|---|
|ID|id|int|
|証券コード|symbol|varchar(255)|
|企業名|companyName|varchar(255)|
|株式保有数|quantity|int|
|取得価格|price|int|

## Spring Boot 設定

Spring Initializer で下記の設定をしています。
|Project|Gradle Project|
|---|---|
|Spring Boot|3.2.0|
|Language|Java|
|Packaging|Jar|
|Java|17|
