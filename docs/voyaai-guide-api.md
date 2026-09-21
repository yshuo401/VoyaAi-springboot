# 攻略模块接口契约

后端地址以当前环境为准，例如本地 `http://localhost:8080`。管理端接口使用若依管理员 Token；小程序公开读取接口不需要 Token。所有 JSON 成功响应均为若依标准：`{"code":200,"msg":"操作成功","data":...}`。管理端分页接口是标准 `TableDataInfo`：`code/msg/total/rows`。

## 管理端接口

权限前缀为 `voyaai:guide`。执行 `sql/voyaai_guide_menu.sql` 后，给角色授权对应菜单即可。

| 方法 | URL | 权限 | 用途 |
|---|---|---|---|
| GET | `/voyaai/guide/list` | `voyaai:guide:list` | 分页查询 |
| GET | `/voyaai/guide/{id}` | `voyaai:guide:query` 或 `voyaai:guide:edit` | 查询详情 |
| POST | `/voyaai/guide` | `voyaai:guide:add` | 新增 |
| PUT | `/voyaai/guide` | `voyaai:guide:edit` | 修改 |
| PUT | `/voyaai/guide/changeStatus` | `voyaai:guide:edit` | 草稿、发布、下架 |
| DELETE | `/voyaai/guide/{ids}` | `voyaai:guide:remove` | 逻辑删除，多个 ID 用逗号分隔 |
| POST | `/voyaai/guide/export` | `voyaai:guide:export` | 导出 Excel |

### 分页查询参数

`pageNum`、`pageSize`、`name`（标题模糊搜索）、`cityId`、`guideType`、`publishStatus`（`0`草稿、`1`已发布、`2`已下架）、`beginCreateTime`、`endCreateTime`。返回：

```json
{"code":200,"msg":"查询成功","total":1,"rows":[{"id":1,"cityId":10,"cityName":"成都","title":"成都三日游","publishStatus":"1","tagNames":"美食,亲子"}]}
```

### 新增/修改 JSON

新增不要传 `id`；修改必须传 `id`。字段如下：

```json
{
  "id": 1,
  "cityId": 10,
  "title": "成都三日游",
  "coverImage": "/profile/upload/guide/cover.jpg",
  "summary": "适合第一次到成都的自由行路线",
  "content": "## 第一天\n抵达后游览市区……",
  "guideType": "自由行",
  "days": 3,
  "budgetMin": 1200.00,
  "budgetMax": 2200.00,
  "publishStatus": "0",
  "sort": 10,
  "remark": "后台备注",
  "tagIds": [1, 3]
}
```

`cityId` 必须对应启用城市；标题在同一城市内不能重复；`budgetMin <= budgetMax`；正文必填。`tagIds` 可为空数组，标签必须是启用且未删除的标签。

状态修改 JSON：`{"id":1,"publishStatus":"1"}`。切换为 `1` 时后端自动写入 `publishTime`；切换为草稿或下架时清空发布时间。删除如果存在收藏、点赞或评论，会返回 HTTP 409，前端应提示先下架。

## 小程序公开接口

以下接口不需要登录，后端只返回已发布、城市/省份/国家均启用的攻略。小程序请求头可以不带 Authorization。

### 可用标签

`GET /app/voyaai/tags?type=guide`

`type` 可选；返回 `data` 为标签数组：`[{"id":1,"name":"美食","type":"guide","sort":10}]`。新增或修改攻略时，将数组中的 `id` 放入 `tagIds`。

### 分页列表

`GET /app/voyaai/guides`

查询参数：`pageNum`（默认1）、`pageSize`（默认10，最大50）、`cityId`、`keyword`、`guideType`、`sort`（`recommend`默认推荐、`latest`最新、`hot`最热）。

成功响应中的 `data`：

```json
{
  "rows": [{
    "id": 1,
    "cityId": 10,
    "cityName": "成都",
    "title": "成都三日游",
    "coverImage": "/profile/upload/guide/cover.jpg",
    "summary": "适合第一次到成都的自由行路线",
    "guideType": "自由行",
    "days": 3,
    "budgetMin": 1200.00,
    "budgetMax": 2200.00,
    "publishTime": "2026-09-21 12:00:00",
    "viewCount": 100,
    "likeCount": 12,
    "favoriteCount": 8,
    "tagNames": "美食,亲子"
  }],
  "total": 1
}
```

### 详情

`GET /app/voyaai/guides/{id}`

返回 `data` 为完整攻略对象，额外包含 `content` 和 `tagIds`。攻略不存在、未发布或所属地区停用时返回 HTTP 404。

## 错误约定

- `400`：参数格式或业务校验失败。
- `401`：需要登录的管理端接口未带有效管理员 Token。
- `403`：管理员没有对应 `voyaai:guide:*` 权限。
- `404`：攻略、城市或公开资源不存在。
- `409`：标题重复，或者删除时存在收藏、点赞、评论关联。
