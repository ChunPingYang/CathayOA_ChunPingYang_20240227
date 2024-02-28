### 提供一批次每日18:00呼叫API，取得外匯成交資料，並將每日的美元/台幣欄位(USD/NTD)資料與日期(yyyy-MM-dd HH:mm:ss) insert至table/collection。 ###
這裡我先使用Postman呼叫API的方式去將資料傳入MongoDB資料庫中。

![匯率資料傳入資料庫](https://github.com/ChunPingYang/CathayOA_ChunPingYang_20240227/blob/main/screenshot/FetchExchangeDataToDatabase_Postman.png)

### 提供一forex API, 從DB取出日期區間內美元/台幣的歷史資料，並針對API功能寫Unit test。日期區間僅限1年前-當下日期-1天，若日期區間不符規定，response需回error code E001，一次只查詢一種幣別，如：美元usd。###
1. 如果輸入參數不是USD，則回傳"請選擇美金”。

2. 如果輸入日期區間不符合需求，且搜尋幣別為美金，則拋出Exception。資料庫裡面有十筆資料，日期從2023/05/28至2024/02/28。

3. 如果輸入日期區間符合需求，且搜尋幣別為美金，則回傳成功如需求所示。